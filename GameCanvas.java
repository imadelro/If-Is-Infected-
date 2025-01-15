/**
The GameCanvas class represents the canvas component where the game graphics are rendered. It extends the JComponent class and provides methods for setting the game result, player visibility, and timer index. It also includes a method for setting up a countdown timer and handles the painting of the game elements such as the map, players, countdown, and game result screen. The class utilizes Graphics2D to draw the various game elements on the canvas and updates the countdown timer based on the timer index.

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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCanvas extends JComponent {
    private Map map;
    private Player p1, p2, p3;
    private int result;
    private boolean playersVisible;
    private GameResultScreen screen;
    private Countdown countdown;
    private int timerIndex;
    private Timer updateTimer;

    public GameCanvas() {
        map = new Map("Map.png", 0, 0, 800, 600);
        countdown = new Countdown(330, 0, 100, 90, 30);
        doCountdown();
    }

    public void setResult(int result, boolean zombiesWinGame) {
        this.result = result;
        screen = new GameResultScreen(0, 0, 800, 600, zombiesWinGame);
    }

    public void setPlayerVisibility(Player p1, Player p2, Player p3, boolean playersVisible) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.playersVisible = playersVisible;
    }

    public void setTimerIndex(int index) {
        this.timerIndex = index;
    }

    public void setUpTimer(Countdown timer) {
        this.countdown = timer;
    }

    public void doCountdown() {
        // Create a Timer that updates the time index every second
        updateTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the time index of the countdown
                countdown.updateTimeIndex(timerIndex);

                // Repaint the canvas to update the countdown
                repaint();

                // Check if it's after 30 seconds
                if (timerIndex >= 30) {
                    updateTimer.stop(); // Stop the timer
                }
            }
        });
        updateTimer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        map.drawMap(g2d);

        if (playersVisible) {
            p1.drawSprite(g2d);
            p2.drawSprite(g2d);
            p3.drawSprite(g2d);
        }

        if (!(timerIndex >= 30))
            countdown.draw(g2d); // Draw the countdown after the map

        switch (result) {
            case 1:
                screen.outputResult(g2d);
            default:
                return;
        }
    }

}