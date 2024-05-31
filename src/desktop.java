import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class desktop extends AbstractTableModel {
    private static final String URL = "jdbc:mysql://it.vshp.online:3306/db_9a21de";
    private static final String USER = "st_9a21de";
    private static final String PASSWORD = "2a3e555e30ab";

    private static Connection connection;
    private static Statement statement;

    private Object[][] data;
    private String[] columnNames = {"ID", "Название", "Дозировка", "Производитель", "Цена", "Количество"};

    public desktop(Object[][] data) {
        this.data = data;
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void updateData(Object[][] newData) {
        this.data = newData;
        fireTableDataChanged();
    }

    private JFrame frame;
    private JButton добавитьЛекарствоКнопка;
    private JButton добавитьПоставщикаКнопка;
    private JButton разместитьЗаказКнопка;
    private JButton списокЛекарствКнопка;
    private JButton редактироватьЛекарствоКнопка;

    public desktop() {
        frame = new JFrame("Система Управления Аптекой");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Получаем размеры экрана
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getMaximumWindowBounds();
        int screenWidth = bounds.width;
        int screenHeight = bounds.height;

        // Устанавливаем размер окна
        frame.setSize(1000, 600);

        // Вычисляем координаты для центрирования
        int x = (screenWidth - frame.getWidth()) / 2;
        int y = (screenHeight - frame.getHeight()) / 2;

        // Устанавливаем положение окна
        frame.setLocation(x, y);

        // Создание кнопок и добавление их в окно
        добавитьЛекарствоКнопка = new JButton("Добавить Лекарство");
        добавитьЛекарствоКнопка.setBounds(400, 30, 200, 30);
        добавитьЛекарствоКнопка.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                добавитьЛекарство();
            }
        });
        frame.add(добавитьЛекарствоКнопка);

        добавитьПоставщикаКнопка = new JButton("Добавить Поставщика");
        добавитьПоставщикаКнопка.setBounds(400, 70, 200, 30);
        добавитьПоставщикаКнопка.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                добавитьПоставщика();
            }
        });
        frame.add(добавитьПоставщикаКнопка);

        разместитьЗаказКнопка = new JButton("Разместить Заказ");
        разместитьЗаказКнопка.setBounds(400, 110, 200, 30);
        разместитьЗаказКнопка.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                разместитьЗаказ();
            }
        });
        frame.add(разместитьЗаказКнопка);

        списокЛекарствКнопка = new JButton("Список Лекарств");
        списокЛекарствКнопка.setBounds(400, 150, 200, 30);
        списокЛекарствКнопка.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                показатьСписокЛекарств();
            }
        });
        frame.add(списокЛекарствКнопка);

        редактироватьЛекарствоКнопка = new JButton("Редактировать Лекарство");
        редактироватьЛекарствоКнопка.setBounds(400, 190, 200, 30);
        редактироватьЛекарствоКнопка.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                редактироватьЛекарство();
            }
        });
        frame.add(редактироватьЛекарствоКнопка);

        frame.setVisible(true);

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void добавитьЛекарство() {
        String name = JOptionPane.showInputDialog("Введите название лекарства:");
        String dosage = JOptionPane.showInputDialog("Введите дозировку:");
        String manufacturer = JOptionPane.showInputDialog("Введите производителя:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Введите цену:"));
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Введите количество:"));

        try {
            String query = "INSERT INTO Лекарства (Название, Дозировка, Производитель, Цена, Количество) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, dosage);
            preparedStatement.setString(3, manufacturer);
            preparedStatement.setDouble(4, price);
            preparedStatement.setInt(5, quantity);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Лекарство добавлено успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Не удалось добавить лекарство.");
        }
    }

    private void добавитьПоставщика() {
        String name = JOptionPane.showInputDialog("Введите название поставщика:");
        String contactPerson = JOptionPane.showInputDialog("Введите контактное лицо:");
        String phoneNumber = JOptionPane.showInputDialog("Введите номер телефона:");

        try {
            String query = "INSERT INTO Поставщики (Название, Контактное_лицо, Телефон) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, contactPerson);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Поставщик добавлен успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Не удалось добавить поставщика.");
        }
    }

    private void разместитьЗаказ() {
        int medicineId = Integer.parseInt(JOptionPane.showInputDialog("Введите ID лекарства:"));
        int supplierId = Integer.parseInt(JOptionPane.showInputDialog("Введите ID поставщика:"));
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Введите количество:"));
        String orderDate = JOptionPane.showInputDialog("Введите дату заказа (ГГГГГ-ММ-ДД):");
        try {
            String query = "INSERT INTO Заказы (id_лекарства, id_поставщика, Количество, Дата_заказа) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, medicineId);
            preparedStatement.setInt(2, supplierId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setString(4, orderDate);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Заказ размещен успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Не удалось разместить заказ.");
        }
    }

    private void показатьСписокЛекарств() {
        try {
            String query = "SELECT * FROM Лекарства";
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columnCount = metaData.getColumnCount();
            Object[][] data = new Object[100][columnCount];

            int row = 0;
            while (resultSet.next()) {
                for (int col = 0; col < columnCount; col++) {
                    data[row][col] = resultSet.getObject(col + 1);
                }
                row++;
            }

            desktop model = new desktop(data);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            JOptionPane.showMessageDialog(null, scrollPane);

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Не удалось получить список лекарств.");
        }
    }

    private void редактироватьЛекарство() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Введите ID лекарства для редактирования:"));

        try {
            String query = "SELECT * FROM Лекарства WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = JOptionPane.showInputDialog("Введите новое название лекарства:", resultSet.getString("Название"));
                String dosage = JOptionPane.showInputDialog("Введите новую дозировку:", resultSet.getString("Дозировка"));
                String manufacturer = JOptionPane.showInputDialog("Введите нового производителя:", resultSet.getString("Производитель"));
                double price = Double.parseDouble(JOptionPane.showInputDialog("Введите новую цену:", resultSet.getDouble("Цена")));
                int quantity = Integer.parseInt(JOptionPane.showInputDialog("Введите новое количество:", resultSet.getInt("Количество")));

                query = "UPDATE Лекарства SET Название = ?, Дозировка = ?, Производитель = ?, Цена = ?, Количество = ? WHERE ID = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, dosage);
                preparedStatement.setString(3, manufacturer);
                preparedStatement.setDouble(4, price);
                preparedStatement.setInt(5, quantity);
                preparedStatement.setInt(6, id);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Лекарство отредактировано успешно.");
            } else {
                JOptionPane.showMessageDialog(null, "Лекарство с указанным ID не найдено.");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Не удалось отредактировать лекарство.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new desktop();
            }
        });
    }
}