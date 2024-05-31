import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FitnessTrackerApp extends JFrame {
    private JTextField stepsField;
    private JTextField distanceField;
    private JTextField pushUpsField;
    private JTextField squatsField;
    private JTextField plankTimeField;
    private JTextField pullUpsField;
    private JButton trackButton;
    private JButton viewHistoryButton;
    private JLabel resultLabel;
    private int dailyStepsCount;
    private int totalPushUps;
    private int totalSquats;
    private int totalPlankTime;
    private int totalPullUps;
    private JTextArea historyTextArea;

    public FitnessTrackerApp() {
        setTitle("Отслеживание здоровья и фитнеса");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1020, 600); // Установка размера окна

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        stepsField = new JTextField(10);
        distanceField = new JTextField(10);
        pushUpsField = new JTextField(10);
        squatsField = new JTextField(10);
        plankTimeField = new JTextField(10);
        pullUpsField = new JTextField(10);
        trackButton = new JButton("Отследить активность");
        viewHistoryButton = new JButton("Просмотреть историю");
        resultLabel = new JLabel();
        dailyStepsCount = 0;
        totalPushUps = 0;
        totalSquats = 0;
        totalPlankTime = 0;
        totalPullUps = 0;
        historyTextArea = new JTextArea(15, 60);
        historyTextArea.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(createEntryField("Пройденные шаги:", stepsField), gbc);
        gbc.gridy++;
        inputPanel.add(createEntryField("Расстояние (км):", distanceField), gbc);
        gbc.gridy++;
        inputPanel.add(createEntryField("Отжимания:", pushUpsField), gbc);
        gbc.gridy++;
        inputPanel.add(createEntryField("Приседания:", squatsField), gbc);
        gbc.gridy++;
        inputPanel.add(createEntryField("Стойка в планке (сек):", plankTimeField), gbc);
        gbc.gridy++;
        inputPanel.add(createEntryField("Подтягивания:", pullUpsField), gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        inputPanel.add(trackButton, gbc);
        gbc.gridy++;
        inputPanel.add(viewHistoryButton, gbc);

        JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultPanel.add(resultLabel, BorderLayout.NORTH);

        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        historyPanel.add(new JLabel("История активностей:"), BorderLayout.NORTH);
        historyPanel.add(new JScrollPane(historyTextArea), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, resultPanel, historyPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        add(inputPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        trackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int steps = parseTextField(stepsField);
                double distance = parseTextField(distanceField);
                int pushUps = parseTextField(pushUpsField);
                int squats = parseTextField(squatsField);
                int plankTime = parseTextField(plankTimeField);
                int pullUps = parseTextField(pullUpsField);

                dailyStepsCount += steps;
                totalPushUps += pushUps;
                totalSquats += squats;
                totalPlankTime += plankTime;
                totalPullUps += pullUps;

                double caloriesBurned = calculateCaloriesBurned(steps, distance);

                resultLabel.setText("<html>Пройденные шаги за сутки: " + dailyStepsCount
                        + "<br>Сожжено калорий: " + caloriesBurned
                        + "<br>Отжимания: " + totalPushUps
                        + "<br>Приседания: " + totalSquats
                        + "<br>Стойка в планке: " + totalPlankTime + " сек"
                        + "<br>Подтягивания: " + totalPullUps + "</html>");

                FitnessActivity activity = new FitnessActivity(steps, distance, pushUps, squats, plankTime, pullUps, LocalDate.now());
                historyTextArea.append(activity.toString() + "\n");
            }
        });

        viewHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea historyText = new JTextArea(historyTextArea.getText());
                JScrollPane scrollPane = new JScrollPane(historyText);
                scrollPane.setPreferredSize(new Dimension(400, 300)); // Устанавливаем размер прокручиваемой области
                JOptionPane.showMessageDialog(null, scrollPane, "История активностей", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        // Установка положения окна по центру экрана
        setLocationRelativeTo(null);
    }

    private JPanel createEntryField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private int parseTextField(JTextField field) {
        String text = field.getText();
        if (text.isEmpty()) {
            return 0;
        } else {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    private double calculateCaloriesBurned(int steps, double distance) {
        double caloriesPerStep = 0.04;
        double caloriesPerDistance = 0.1;
        return (steps * caloriesPerStep) + (distance * caloriesPerDistance);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FitnessTrackerApp().setVisible(true);
            }
        });
    }

    // Класс для представления активности
    private class FitnessActivity {
        private int steps;
        private double distance;
        private int pushUps;
        private int squats;
        private int plankTime;
        private int pullUps;
        private LocalDate date;

        public FitnessActivity(int steps, double distance, int pushUps, int squats, int plankTime, int pullUps, LocalDate date) {
            this.steps = steps;
            this.distance = distance;
            this.pushUps = pushUps;
            this.squats = squats;
            this.plankTime = plankTime;
            this.pullUps = pullUps;
            this.date = date;
        }

        // Переопределение метода toString для отображения активности с датой
        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return "Дата: " + date.format(formatter) + "\n"
                    + "Пройденные шаги: " + steps + "\n"
                    + "Расстояние (км): " + distance + "\n"
                    + "Отжимания: " + pushUps + "\n"
                    + "Приседания: " + squats + "\n"
                    + "Стойка в планке (сек): " + plankTime + "\n"
                    + "Подтягивания: " + pullUps;
        }
    }
}
