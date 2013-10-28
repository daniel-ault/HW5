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
    private static Color DEFAULT_COLOR = Color.WHITE;
    private Color color = DEFAULT_COLOR;

    public void setColor(Color c)
    {
        this.color = c;
    }
    
    public void setDefaultColor()
    {
        this.color = DEFAULT_COLOR;
    }

    // the paintComponent method is called whenever an item is rendered on the screen
    public void paintComponent(Graphics g)
    {
        g.setColor(color);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
