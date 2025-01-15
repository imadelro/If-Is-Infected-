/**
The Player class represents a game character with a set of images for different states and movements. It has a constructor that initializes the player with the provided image filenames, position, size, and infection status. The class includes methods for changing the current image, moving the player horizontally and vertically while checking for boundaries and obstacles, and handling the player's infection status. Additionally, there are methods to retrieve the player's position and infection status, as well as a method to draw the player's sprite on a specified Graphics2D object based on the current image index.

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
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Player {
    private BufferedImage[] images;
    private int currentIndex;
    private int tracker = 0;
    private int startIndex = 0;
    public boolean u, d, l, r;
    private int x, y, w, h;
    private boolean isInfected;

    public Player(String[] filenames, int x, int y, int w, int h, boolean isInfected) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.isInfected = isInfected;
        if (isInfected) {
            startIndex = 12;
        } else {
            startIndex = 0;
        }

        images = new BufferedImage[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            try {
                images[i] = ImageIO.read(new File(filenames[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeImage(int index) {
        currentIndex = index;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void moveX(int x) {
        int newX = this.x + x;
        if (isWithinBounds(newX, y) && !isObstacles(newX, y)) {
            this.x = newX;
        }
    }

    public void moveY(int y) {
        int newY = this.y + y;
        if (isWithinBounds(x, newY) && !isObstacles(x, newY)) {
            this.y = newY;
        }
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= -20 && x + w <= 820 && y >= -20 && y + h <= 620;
    }

    public boolean isObstacles(int x, int y) {
        Rectangle house = new Rectangle(440, 0, 193, 250);
        Rectangle tree1 = new Rectangle(355, 0, 70, 50);
        Rectangle tree2 = new Rectangle(125, 375, 80, 50);
        Rectangle fence1 = new Rectangle(200, 245, 150, 3);
        Rectangle fence2 = new Rectangle(515, 455, 150, 3);
        return house.intersects(x, y, w, h) || tree1.intersects(x, y, w, h) || tree2.intersects(x, y, w, h)
                || fence1.intersects(x, y, w, h) || fence2.intersects(x, y, w, h);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
    }

    public void reset() {
        u = d = l = r = false;
    }

    public void increment() {
        tracker++;
        currentIndex = tracker % 4;
        if (currentIndex == 2)
            currentIndex = 0;
        if (currentIndex == 3)
            currentIndex = 2;
        if (l) {
            currentIndex += 9;
        } else if (r) {
            currentIndex += 6;
        } else if (u) {
            currentIndex += 3;
        }
    }

    public boolean getIsInfected() {
        return isInfected;
    }

    public void drawSprite(Graphics2D g2d) {
        if (isInfected) {
            startIndex = 12;
        } else {
            startIndex = 0;
        }
        g2d.drawImage(images[startIndex + currentIndex], x, y, w, h, null);
    }
}