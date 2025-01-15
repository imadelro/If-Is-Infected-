/**
The GameResultScreen class represents a result screen in a game, 
displaying either a zombies win or humans win image based on a boolean value. 
The class takes in the position, dimensions, and a boolean parameter to determine the outcome, 
and the outputResult method draws the corresponding image on the screen using the provided Graphics2D object.

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

import javax.swing.*;
import java.awt.*;

public class GameResultScreen {
    private int x, y, width, height;
    private boolean zombiesWinGame;
    private ImageIcon zombiesWin, humansWin;

    public GameResultScreen(int x, int y, int width, int height, boolean b) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        zombiesWin = new ImageIcon("zombiesWin.png");
        humansWin = new ImageIcon("humansWin.png");
        zombiesWinGame = b;

    }

    public void outputResult(Graphics2D g2d) {

        if (zombiesWinGame) {
            g2d.drawImage(zombiesWin.getImage(), x, y, width, height, null);
        } else
            g2d.drawImage(humansWin.getImage(), x, y, width, height, null);
    }
}
