/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hw5;

/**
 * Class representing a single "pixel" in our simple paint program.
 */

import java.awt.*;
import javax.swing.*;

public class Pixel extends JPanel
{
    private Color color = Color.PINK;

    public void setColor(Color c)
    {
        this.color = c;
    }

    // the paintComponent method is called whenever an item is rendered on the screen
    public void paintComponent(Graphics g)
    {
        g.setColor(color);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
