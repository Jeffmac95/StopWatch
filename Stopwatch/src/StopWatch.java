import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopWatch implements ActionListener {
    int seconds = 0;
    int elapsedTime = 0;
    String seconds_string = String.format("%04d", seconds);
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
    JLabel label = new JLabel(seconds_string);JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");
    JButton resetButton = new JButton("Reset");
    JButton getMinutesButton = new JButton("Get Minutes");

    public StopWatch() {
        panel.setLayout(null);
        panel.setBounds(0, 0, Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        panel.setBackground(Color.DARK_GRAY);
        label.setBounds(0, 50, Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        label.setFont(new Font("Consolas", Font.PLAIN, 24));
        label.setForeground(Color.BLACK);
        startButton.setBounds(50, 100, 100, 50);
        stopButton.setBounds(150, 100, 100, 50);
        resetButton.setBounds(250, 100, 100, 50);
        getMinutesButton.setBounds(150, 500, 125, 50);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        resetButton.addActionListener(this);
        getMinutesButton.addActionListener(this);

        panel.add(label);
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(resetButton);
        panel.add(getMinutesButton);
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
        } else {
            getMinutes();
        }
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        timer.restart();
        seconds = 0;
        elapsedTime = 0;
        seconds_string = String.format("%03d", seconds);
        label.setText(seconds_string);
    }

    public void getMinutes() {
        int minutes = seconds / 60;
        int leftOverSeconds = seconds % 60;
        String convertedString = String.format("%03d minutes, and %02d seconds.", minutes, leftOverSeconds);
        label.setText(convertedString);
    }
}
