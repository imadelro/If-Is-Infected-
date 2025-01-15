/**
The GameStarter class represents a game starter screen GUI. It sets up a JFrame with a background image and text fields for entering an IP address and port number. It also includes an enter button to initiate the game. The class provides methods to set up the GUI, add button listeners, and retrieve the entered IP address and port number. When the enter button is clicked, it disposes of the starter screen, creates a GameFrame instance, connects to the server using the entered IP address and port number, creates game sprites, and sets up the game GUI.

@Justine Robert Capili (221318)
@Malena del Rosario (222071)
@version May 17, 2023
**/
/*
We have not discussed the Java language code in our program
with anyone other than our instructor or the teaching assistants
assigned to this course.
We have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in our program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of our program.
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameStarter {
    private JFrame frame;
    private JLayeredPane layeredPane;
    private JButton enterButton;
    private JTextField ipField, portField;
    private JLabel ipLabel, portLabel;
    private String ipaddress;
    private int portNum;
    private GameFrame gf;

    public GameStarter() {
        frame = new JFrame();
        layeredPane = new JLayeredPane();
        enterButton = new JButton("");
        ipField = new JTextField(10);
        portField = new JTextField(10);
    }

    public void setUpGUI(int width, int height) {
        frame.setTitle("Start Screen");
        layeredPane.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Set the layout manager to null

        // Add the background image as a separate component in the lowest layer
        ImageIcon backgroundImage = new ImageIcon("StartScreen.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, width, height);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Set up the components in the top layer
        ipField.setBounds(149, 219, 195, 60);
        portField.setBounds(452, 219, 195, 60);
        enterButton.setBounds(320, 387, 160, 45);

        // Remove the outline from the IP field and port field
        ipField.setBorder(new EmptyBorder(0, 0, 0, 0));
        portField.setBorder(new EmptyBorder(0, 0, 0, 0));
        enterButton.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Set the background of the IP field and port field as transparent
        ipField.setOpaque(false);
        portField.setOpaque(false);
        enterButton.setOpaque(false);
        enterButton.setContentAreaFilled(false);

        portField.setText("50100");

        // Set the font size of the JLabel
        Font labelFont = ipField.getFont();
        ipField.setFont(new Font(labelFont.getName(), Font.PLAIN, 25)); // Set the font size to 20
        portField.setFont(new Font(labelFont.getName(), Font.PLAIN, 25)); // Set the font size to 20

        // Center the cursor in the JTextField
        ipField.setHorizontalAlignment(SwingConstants.CENTER);
        portField.setHorizontalAlignment(SwingConstants.CENTER);

        layeredPane.add(ipField, Integer.valueOf(1));
        layeredPane.add(portField, Integer.valueOf(1));
        layeredPane.add(enterButton, Integer.valueOf(1));

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void addButtonListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ipaddress = ipField.getText();
                String tempPort = portField.getText();
                portNum = Integer.parseInt(tempPort);
                if (ae.getSource() == enterButton) {
                    frame.dispose();
                    gf = new GameFrame(800, 600);
                    gf.connectToServer(ipaddress, portNum);
                    gf.createSprites();
                    gf.setUpGUI();
                }
            }
        };

        enterButton.addActionListener(actionListener);
    }

    public static void main(String[] args) {
        GameStarter gs = new GameStarter();
        gs.addButtonListener();
        gs.setUpGUI(800, 600);
    }
}
