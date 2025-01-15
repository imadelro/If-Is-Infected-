/**
The GameFrame class represents a game frame in a graphical user interface. 
It extends the JFrame class and contains various components and functionalities 
related to the game. The class sets up the game GUI, creates player sprites, handles 
user input, and communicates with a server using sockets for multiplayer functionality. 
It also includes methods for restarting the game, connecting to the server, and reading/writing 
data to the server.

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
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;

public class GameFrame extends JFrame {
    private GameCanvas gc;
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private int width, height;
    private Container cp;
    private Player p1, p2, p3, me;
    private int playerID;
    private ArrayList<Player> players;
    private Timer timer;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private int p1CurrentImageIndex, p2CurrentImageIndex, p3CurrentImageIndex;
    private boolean p1IsInfected, p2IsInfected, p3IsInfected;
    private Countdown countdown;
    private JButton exit;
    private int timeLeft;
    private int winner;

    public GameFrame(int w, int h) {

        winner = 0;
        timeLeft = 1;
        width = w;
        height = h;
        gc = new GameCanvas();

    }

    public void setUpGUI() {

        cp = this.getContentPane();
        this.setTitle("Player #" + playerID);
        cp.setPreferredSize(new Dimension(width, height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp.add(gc);
        this.pack();
        this.setVisible(true);
        p3IsInfected = true;
        exit = new JButton("restart");

        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBounds(250, 260, 178, 50);

        addButtonListener();

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (p1IsInfected && p2IsInfected && p3IsInfected) {
                    gc.setResult(1, true);
                    gc.setPlayerVisibility(p1, p2, p3, false);
                    winner = 1;
                    cp.add(exit);
                    pack();
                } else if (timeLeft == 30) {
                    gc.setResult(1, false);
                    gc.setPlayerVisibility(p1, p2, p3, false);
                    winner = 1;
                    cp.add(exit);
                    pack();
                }

            }

        };

        timer = new Timer(10, al);
        timer.start();
        setUpKeyListener();
    }

    public void addButtonListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == exit) {
                    System.exit(0);
                }
            }
        };

        exit.addActionListener(actionListener);
    }

    public void createSprites() {
        players = new ArrayList<Player>();
        p1CurrentImageIndex = 0;
        p2CurrentImageIndex = 0;
        p3CurrentImageIndex = 0;

        countdown = new Countdown(330, 0, 100, 90, timeLeft);

        p1 = new Player(new String[] { "P1HF.PNG", "P1HFL.PNG", "P1HFR.PNG", "P1HB.PNG", "P1HBL.PNG", "P1HBR.PNG",
                "P1HR.PNG", "P1HRL.PNG", "P1HRR.PNG", "P1HL.PNG", "P1HLL.PNG", "P1HLR.PNG", "P1ZF.PNG", "P1ZFL.PNG",
                "P1ZFR.PNG", "P1ZB.PNG", "P1ZBL.PNG", "P1ZBR.PNG", "P1ZR.PNG", "P1ZRL.PNG", "P1ZRR.PNG", "P1ZL.PNG",
                "P1ZLL.PNG", "P1ZLR.PNG" }, 30, 30, 40, 80, false);
        p2 = new Player(new String[] { "P2HF.PNG", "P2HFL.PNG", "P2HFR.PNG", "P2HB.PNG", "P2HBL.PNG", "P2HBR.PNG",
                "P2HR.PNG", "P2HRL.PNG", "P2HRR.PNG", "P2HL.PNG", "P2HLL.PNG", "P2HLR.PNG", "P2ZF.PNG", "P2ZFL.PNG",
                "P2ZFR.PNG", "P2ZB.PNG", "P2ZBL.PNG", "P2ZBR.PNG", "P2ZR.PNG", "P2ZRL.PNG", "P2ZRR.PNG", "P2ZL.PNG",
                "P2ZLL.PNG", "P2ZLR.PNG" }, 30, 450, 40, 80, false);
        p3 = new Player(new String[] { "P3HF.PNG", "P3HFL.PNG", "P3HFR.PNG", "P3HB.PNG", "P3HBL.PNG", "P3HBR.PNG",
                "P3HR.PNG", "P3HRL.PNG", "P3HRR.PNG", "P3HL.PNG", "P3HLL.PNG", "P3HLR.PNG", "P3ZF.PNG", "P3ZFL.PNG",
                "P3ZFR.PNG", "P3ZB.PNG", "P3ZBL.PNG", "P3ZBR.PNG", "P3ZR.PNG", "P3ZRL.PNG", "P3ZRR.PNG", "P3ZL.PNG",
                "P3ZLL.PNG", "P3ZLR.PNG" }, 710, 30, 40, 80, true);

        players.add(p1);
        players.add(p2);
        players.add(p3);

        gc.setPlayerVisibility(p1, p2, p3, true);

    }

    public void restartGame() {
        p1.setX(30);
        p1.setY(30);
        p1.setInfected(false);
        p1IsInfected = false;
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void setUpKeyListener() {
        KeyListener kl = new KeyListener() {
            int speed = 20;

            public void keyPressed(KeyEvent e) {
                // 'me' becomes whoever client is using their PlayerID
                me = players.get(playerID - 1);

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        me.reset();
                        me.l = true;
                        me.moveX(-speed);
                        break;
                    case KeyEvent.VK_D:
                        me.reset();
                        me.r = true;
                        me.moveX(speed);
                        break;
                    case KeyEvent.VK_W:
                        me.reset();
                        me.u = true;
                        me.moveY(-speed);
                        break;
                    case KeyEvent.VK_S:
                        me.reset();
                        me.d = true;
                        me.moveY(speed);
                        break;
                    default:
                        return;
                }
                me.increment();
            }

            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

        };

        cp.addKeyListener(kl);
        cp.setFocusable(true);
    }

    public void connectToServer(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
            playerID = dataIn.readInt();
            System.out.println("Successfully joined! You are Player #" + playerID);

            rfsRunnable = new ReadFromServer(dataIn);
            wtsRunnable = new WriteToServer(dataOut);
            rfsRunnable.waitForStartMsg();
        } catch (IOException ex) {
            System.out.println("IO Exception from CSC Constructor.");
        }
    }

    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    timeLeft = dataIn.readInt();
                    gc.setUpTimer(countdown);
                    gc.setTimerIndex(timeLeft);
                    if (playerID == 2) {
                        int p1x = dataIn.readInt();
                        int p1y = dataIn.readInt();
                        p1CurrentImageIndex = dataIn.readInt();
                        p1IsInfected = isTrue(p1IsInfected, dataIn.readBoolean());

                        p2IsInfected = isTrue(p2IsInfected, dataIn.readBoolean());

                        int p3x = dataIn.readInt();
                        int p3y = dataIn.readInt();
                        p3CurrentImageIndex = dataIn.readInt();

                        if (p1 != null) {
                            p1.setX(p1x);
                            p1.setY(p1y);
                            p1.changeImage(p1CurrentImageIndex);
                            p1.setInfected(p1IsInfected);
                        }

                        if (p2 != null) {
                            p2.setInfected(p2IsInfected);
                        }

                        if (p3 != null) {
                            p3.setX(p3x);
                            p3.setY(p3y);
                            p3.changeImage(p3CurrentImageIndex);
                        }

                    } else if (playerID == 1) {

                        p1IsInfected = isTrue(p1IsInfected, dataIn.readBoolean());

                        int p2x = dataIn.readInt();
                        int p2y = dataIn.readInt();
                        p2CurrentImageIndex = dataIn.readInt();
                        p2IsInfected = isTrue(p2IsInfected, dataIn.readBoolean());

                        int p3x = dataIn.readInt();
                        int p3y = dataIn.readInt();
                        p3CurrentImageIndex = dataIn.readInt();

                        if (p1 != null) {
                            p1.setInfected(p1IsInfected);
                        }

                        if (p2 != null) {
                            p2.setX(p2x);
                            p2.setY(p2y);
                            p2.changeImage(p2CurrentImageIndex);
                            p2.setInfected(p2IsInfected);
                        }
                        if (p3 != null) {
                            p3.setX(p3x);
                            p3.setY(p3y);
                            p3.changeImage(p3CurrentImageIndex);
                        }
                    } else if (playerID == 3) {
                        int p1x = dataIn.readInt();
                        int p1y = dataIn.readInt();
                        p1CurrentImageIndex = dataIn.readInt();
                        p1IsInfected = isTrue(p1IsInfected, dataIn.readBoolean());

                        int p2x = dataIn.readInt();
                        int p2y = dataIn.readInt();
                        p2CurrentImageIndex = dataIn.readInt();
                        p2IsInfected = isTrue(p2IsInfected, dataIn.readBoolean());

                        if (p1 != null) {
                            p1.setX(p1x);
                            p1.setY(p1y);
                            p1.changeImage(p1CurrentImageIndex);
                            p1.setInfected(p1IsInfected);
                        }
                        if (p2 != null) {
                            p2.setX(p2x);
                            p2.setY(p2y);
                            p2.changeImage(p2CurrentImageIndex);
                            p2.setInfected(p2IsInfected);
                        }
                    }
                    gc.repaint();
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFS run");
            }
        }

        public boolean isTrue(boolean beforeState, boolean afterState) {
            if (beforeState) {
                return true;
            } else {
                return afterState;
            }
        }

        public void waitForStartMsg() {
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from Server: " + startMsg);
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            } catch (IOException ex) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }
    }

    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
        }

        @Override
        public void run() {
            try {
                while (true) {

                    if (p1 != null) {

                        if (playerID == 1) {
                            dataOut.writeInt(p1.getX());
                            dataOut.writeInt(p1.getY());
                            dataOut.writeInt(p1.getCurrentIndex());
                            dataOut.writeBoolean(p1.getIsInfected());
                            dataOut.writeInt(winner);

                            dataOut.flush();
                        } else if (p2 != null && playerID == 2) {

                            dataOut.writeInt(p2.getX());
                            dataOut.writeInt(p2.getY());
                            dataOut.writeInt(p2.getCurrentIndex());
                            dataOut.writeBoolean(p2.getIsInfected());

                            dataOut.flush();
                        } else if (p3 != null && playerID == 3) {

                            dataOut.writeInt(p3.getX());
                            dataOut.writeInt(p3.getY());
                            dataOut.writeInt(p3.getCurrentIndex());
                            dataOut.writeBoolean(p3.getIsInfected());
                            dataOut.flush();

                        }
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted Exception from WTS run");
                    }

                }
            } catch (IOException ex) {
                System.out.println("IO Exception from WTS run");
            }

        }

    }

}