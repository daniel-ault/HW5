/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hw5;

/**
 * Simple paint program.
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyFirstGUI extends JFrame
{
    // these instance variables are declared out here so that the event handlers can access the objects
    private JTextField tf = new JTextField();
    private Pixel[][] pixels = new Pixel[40][75];
    private JButton b1 = new JButton("Red");
    private JButton b2 = new JButton("Green");
    private JButton b3 = new JButton("Blue");
    private JButton buttonClear = new JButton("Clear");

    private Color currentColor = Color.BLACK;
    private boolean isMouseDown = false;

    public MyFirstGUI()
    {
        setTitle("The Worst Paint Program Ever");
        setSize(400, 250);
//		setResizable(false);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // to respond to presses, each button must be registered with an 
        //  action listener
        ButtonHandler bh = new ButtonHandler();
        b1.addActionListener(bh);
        b2.addActionListener(bh);
        b3.addActionListener(bh);
        buttonClear.addActionListener(bh);

        // we add GUI elements to a JPanel object, then set that JPanel as the
        //  "content pane" of the JFrame
        JPanel c = new JPanel();
        c.setLayout(new BorderLayout());

        // create the "pixel" objects, add mouse listeners to them, and add them to a container
        JPanel pixelContainer = new JPanel();
        pixelContainer.setLayout(new GridLayout(pixels.length, pixels[0].length));
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j] = new Pixel();
                pixels[i][j].addMouseListener(new MouseHandler(pixels[i][j]));
                pixelContainer.add(pixels[i][j]);
            }
        }

        // add the color buttons to a container
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(1, 4));
        buttonContainer.add(b1);
        buttonContainer.add(b2);
        buttonContainer.add(b3);
        buttonContainer.add(buttonClear);

        // add the elements to the content pane
        c.add(buttonContainer, BorderLayout.NORTH);
        c.add(pixelContainer, BorderLayout.CENTER);
        c.add(tf, BorderLayout.SOUTH);

        setContentPane(c);
        setVisible(true);
    }

    // Event handler class for mouse events.  Each instance of MouseHandler is associated
    //  with a specific Pixel object to let it know which pixel to act on.
    private class MouseHandler implements MouseListener
    {
        private Pixel thePixel;

        private MouseHandler(Pixel p)
        {
            thePixel = p;
        }

        public void mouseClicked(MouseEvent e)
        {
            thePixel.setColor(currentColor);
            thePixel.repaint();
        }

        public void mousePressed(MouseEvent e)
        {
            isMouseDown = true;
            mouseClicked(e);
        }

        public void mouseReleased(MouseEvent e)
        {
            isMouseDown = false;
        }

        public void mouseEntered(MouseEvent e)
        {
            if (isMouseDown)
                    mouseClicked(e);
        }

        public void mouseExited(MouseEvent e)
        {
        }
    }


    // Event handler class for button presses -- the actionPerformed method
    //  specifies how the button should react to action events (in this case,
    //  button presses)
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            // figure out which button fired the ActionEvent
            Object source = e.getSource();

            if (source == b1)
                currentColor = Color.RED;
            else if (source == b2)
                currentColor = Color.GREEN;
            else if (source == b3)
                currentColor = Color.BLUE;
            else if (source == buttonClear)
                clearPixels();
            tf.setText("I choose you, " + currentColor);
        }
    }
    
    public void clearPixels()
    {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j].setDefaultColor();
                pixels[i][j].repaint();
            }
        }
    }


    public static void main(String[] args)
    {
        new MyFirstGUI();
    }
}
