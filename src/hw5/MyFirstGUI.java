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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyFirstGUI extends JFrame
{
    // these instance variables are declared out here so that the event handlers can access the objects
    private JTextField tf = new JTextField();
    private Pixel[][] pixels = new Pixel[40][75];
    private JSlider rSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
    private JSlider bSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
    private JSlider gSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
    private JButton clearButton = new JButton("Clear");

    private Color currentColor = Color.BLACK;
    private boolean isMouseDown = false;

    public MyFirstGUI()
    {
        setTitle("The Worst Paint Program Ever");
        setSize(400, 250);
//          setResizable(false);

        // to respond to presses, each button must be registered with an 
        //  action listener
        ButtonHandler bh = new ButtonHandler();
        clearButton.addActionListener(bh);
        
        SliderHandler ah = new SliderHandler();
        rSlider.addChangeListener(ah);
        gSlider.addChangeListener(ah);
        bSlider.addChangeListener(ah);

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
        buttonContainer.setLayout(new GridLayout(4, 1));
        buttonContainer.add(rSlider);
        buttonContainer.add(gSlider);
        buttonContainer.add(bSlider);
        buttonContainer.add(clearButton);
        
        // initialize the sliders
        initializeSlider(rSlider, "Red");
        initializeSlider(gSlider, "Green");
        initializeSlider(bSlider, "Blue");

        // add the elements to the content pane
        c.add(buttonContainer, BorderLayout.NORTH);
        c.add(pixelContainer, BorderLayout.CENTER);
        c.add(tf, BorderLayout.SOUTH);

        setContentPane(c);
        setVisible(true);
    }
    
    private void initializeSlider(JSlider slider, String name)
    {
        slider.setMajorTickSpacing(128);
        
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setName(name);
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
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // figure out which button fired the ActionEvent
            Object source = e.getSource();

            if (source == clearButton) {
                clearPixels();
            }
            tf.setText("I choose you, " + currentColor);
        }
    }
    
    private class SliderHandler implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e) {
            Object source = e.getSource();
            
            currentColor = new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue());
        }
    }
    
    public void clearPixels() {
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
