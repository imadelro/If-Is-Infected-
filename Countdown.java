/**
The Countdown class represents a countdown timer with a specified position, size, and time index. It has a constructor that initializes the countdown with the provided position, size, and initial time index. The class also has methods to set and update the time index, and a draw method that draws the corresponding image for the current time index on the specified Graphics2D object. The countdown images are loaded from files using the ImageIO class.

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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class Countdown {
    private int x, y, w, h;
    private int timeIndex;
    private String[] filenames;
    private BufferedImage[] images;

    public Countdown(int x, int y, int w, int h, int timeIndex) {

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.timeIndex = timeIndex - 1;

        String p = "CountdownImages/";

        filenames = new String[] { p + "30seconds.png", p + "29seconds.png", p + "28seconds.png", p + "27seconds.png",
                p + "26seconds.png",
                p + "25seconds.png", p + "24seconds.png", p + "23seconds.png", p + "22seconds.png", p + "21seconds.png",
                p + "20seconds.png",
                p + "19seconds.png", p + "18seconds.png", p + "17seconds.png", p + "16seconds.png", p + "15seconds.png",
                p + "14seconds.png", p + "13seconds.png",
                p + "12seconds.png", p + "11seconds.png", p + "10seconds.png", p + "9seconds.png", p + "8seconds.png",
                p + "7seconds.png", p + "6seconds.png",
                p + "5seconds.png", p + "4seconds.png", p + "3seconds.png", p + "2seconds.png", p + "1second.png" };
        images = new BufferedImage[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            try {
                images[i] = ImageIO.read(new File(filenames[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setTimerIndex(int index) {
        this.timeIndex = index - 1;
    }

    public void updateTimeIndex(int timeIndex) {
        this.timeIndex = timeIndex;
    }

    public void draw(Graphics2D g2d) {

        g2d.drawImage(images[timeIndex], x, y, w, h, null);
    }

}
