/**
The Map class represents a game map with a specified position, size, and image. It has a constructor that takes a filename, position coordinates, and size as parameters to initialize the map. The drawMap method accepts a Graphics2D object and draws the map image on the graphics context at the specified position and with the specified size.

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

public class Map {
    private int x, y, width, height;
    private ImageIcon mapImage;

    public Map(String filename, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        mapImage = new ImageIcon(filename);

    }

    public void drawMap(Graphics2D g2d) {

        g2d.drawImage(mapImage.getImage(), x, y, width, height, null);
    }
}
