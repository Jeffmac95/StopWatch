// TODO: action listener for insert seconds.


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StopWatch implements ActionListener {
    int seconds = 0;
    int elapsedTime = 0;
    String seconds_string = String.format("%04d", seconds);
    public boolean isMinutes = false;
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime += 1000;
            seconds = elapsedTime / 1000;
            seconds_string = String.format("%04d", seconds);
            label.setText(seconds_string);
        }
    });

    JFrame frame = new JFrame("Stop Watch");
    ImageIcon iconImg = new ImageIcon("clockicon.png");
    JPanel panel = new JPanel();
    JLabel label = new JLabel(seconds_string);
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");
    JButton resetButton = new JButton("Reset");
    JButton getMinutesButton = new JButton("Get Minutes");
    JButton insertTimeButton = new JButton("Insert Time");
    JTextField textField = new JTextField("");
    JButton searchDateButton = new JButton("Search");
    JButton getAllTimesButton = new JButton("Get all times");

    public StopWatch() {
        panel.setLayout(null);
        panel.setBounds(0, 0, Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        panel.setBackground(Color.DARK_GRAY);
        label.setBounds(Main.FRAME_WIDTH / 2 - 150, 0, Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        label.setFont(new Font("Consolas", Font.PLAIN, 28));
        label.setForeground(Color.BLACK);
        startButton.setBounds(Main.FRAME_WIDTH / 2 - 250, 50, 100, 50);
        stopButton.setBounds(Main.FRAME_WIDTH / 2 - 50, 50, 100, 50);
        resetButton.setBounds(Main.FRAME_WIDTH / 2 + 150, 50, 100, 50);
        getMinutesButton.setBounds(Main.FRAME_WIDTH / 2 - 100, 450, 125, 50);
        insertTimeButton.setBounds(Main.FRAME_WIDTH / 2 - 100, 550, 125, 50);
        textField.setBounds(Main.FRAME_WIDTH / 2 - 150, 650, 125, 25);
        searchDateButton.setBounds(Main.FRAME_WIDTH / 2 + 50, 650, 125, 25);
        getAllTimesButton.setBounds(Main.FRAME_WIDTH / 2 - 50, 700, 125, 50);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        resetButton.addActionListener(this);
        getMinutesButton.addActionListener(this);
        insertTimeButton.addActionListener(this);
        getAllTimesButton.addActionListener(this);

        panel.add(startButton);
        panel.add(stopButton);
        panel.add(resetButton);
        panel.add(label);
        panel.add(getMinutesButton);
        panel.add(insertTimeButton);
        panel.add(textField);
        panel.add(searchDateButton);
        panel.add(getAllTimesButton);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(iconImg.getImage());
        frame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            start();
        } else if (e.getSource() == stopButton) {
            stop();
        } else if (e.getSource() == resetButton){
            reset();
        } else if (e.getSource() == getMinutesButton){
            getMinutes();
        } else if (e.getSource() == insertTimeButton) {
            insert(seconds);
        } else if (e.getSource() == getAllTimesButton) {
            read();
        }
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
        getMinutesButton.setText("Get minutes.");
    }

    public void reset() {
        timer.restart();
        seconds = 0;
        elapsedTime = 0;
        seconds_string = String.format("%03d", seconds);
        label.setText(seconds_string);
    }

    public void getMinutes() {
        isMinutes = !isMinutes;

        if (isMinutes) {
            int minutes = seconds / 60;
            int leftOverSeconds = seconds % 60;
            String convertedString = String.format("%03d minutes, and %02d seconds.", minutes, leftOverSeconds);
            label.setText(convertedString);
            getMinutesButton.setText("Show Seconds");
        } else {
            seconds_string = String.format("%03d seconds.", seconds);
            label.setText(seconds_string);
            getMinutesButton.setText("Show Minutes");
        }
    }

    public static void connect() {
        var url = "jdbc:sqlite:C:/Users/jeff_/SQLite/db/stopwatch.db";


        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("connection to SQLite good.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(int seconds){
        var url = "jdbc:sqlite:C:/Users/jeff_/SQLite/db/stopwatch.db";
        String sql = "INSERT INTO times(seconds) VALUES (?)";
        try (var conn = DriverManager.getConnection(url)) {
            var pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, seconds);
            pstmt.executeUpdate();
            System.out.println("Time inserted.");
        } catch (SQLException e) {
            System.out.println("Insertion failed." + e.getMessage());
        }
        seconds = 0;
        seconds_string = String.format("%03d", seconds);
        label.setText(seconds_string);
    }

    public void read() {
        var url = "jdbc:sqlite:C:/Users/jeff_/SQLite/db/stopwatch.db";
        String sql = "SELECT * FROM times";

        try (var conn = DriverManager.getConnection(url);
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("id: %d, seconds: %d, date: %s ",
                        rs.getInt("id"),
                        rs.getInt("seconds"),
                        rs.getString("created_at")
                        );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
