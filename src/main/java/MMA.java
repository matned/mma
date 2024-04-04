import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MMA {

    private Thread mmt;
    private Robot robot;
    private final int INTERVAL = 60000;

    public MMA() {
        JFrame frame = new JFrame("MMA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startBtn = new JButton("START");
        startBtn.setSize(300, 100);
        JButton stopBtn = new JButton("STOP");
        stopBtn.setSize(300, 100);

        JPanel panel = new JPanel();

        panel.setBounds(300, 200, 300, 200);
        panel.setLayout(new GridLayout(3, 1, 0, 0));
        frame.getContentPane().add(panel);

        JLabel actionLabel = new JLabel("STOPPED");

        panel.add(actionLabel);
        panel.add(startBtn);
        panel.add(stopBtn);

        frame.setSize(300, 200);
        frame.setVisible(true); // Wyświetlenie ramki przed ustawieniem ikony

        try {
            InputStream iconStream = getClass().getResourceAsStream("/images/rabbit.png");
            Image iconImage = ImageIO.read(iconStream);
            frame.setIconImage(iconImage); // Ustawienie ikony po wyświetleniu ramki
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        startBtn.addActionListener(e -> {
            start();
            actionLabel.setText("STARTED");
        });

        stopBtn.addActionListener(e -> {
            stop();
            actionLabel.setText("STOPPED");
        });

        start();
    }

    private void moveMMA() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();

        int actualX = (int) mouseLocation.getX();
        int actualY = (int) mouseLocation.getY();

        int x = actualX > screenSize.width - 5 ? actualX - 1 : actualX + 1;
        int y = actualY > screenSize.height - 5 ? actualY - 1 : actualY + 1;

        robot.mouseMove(x, y);
    }

    private void start() {
        mmt = new Thread(() -> {
            while (true) {
                moveMMA();
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mmt.start();
    }

    private void stop() {
        mmt.stop();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MMA::new);
    }
}