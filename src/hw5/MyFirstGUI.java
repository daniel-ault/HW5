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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyFirstGUI extends JFrame
{
    // these instance variables are declared out here so that the event handlers can access the objects
    private JTextField tf = new JTextField();
    private Pixel[][] pixels = new Pixel[40][75];
    private JLabel labelRed = new JLabel("Red");
    private JLabel labelGreen = new JLabel("Green");
    private JLabel labelBlue = new JLabel("Blue");
    private JSpinner b1 = new JSpinner();    // Red spinner
    private JSpinner b2 = new JSpinner();    // Green spinner
    private JSpinner b3 = new JSpinner();    // Blue spinner
    private JButton buttonClear = new JButton("Clear");
    private JButton buttonChooseColor = new JButton("Choose Color");
    
    private Pixel panelColor = new Pixel();

    private Color currentColor = Color.BLACK;
    private boolean isMouseDown = false;

    public MyFirstGUI()
    {
        setTitle("The Worst Paint Program Ever");
        setSize(350, 300);
//		setResizable(false);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        panelColor.setColor(currentColor);

        // to respond to presses, each button must be registered with an 
        //  action listener
        ButtonHandler bh = new ButtonHandler();
        //b1.addActionListener(bh);
        //b2.addActionListener(bh);
        //b3.addActionListener(bh);
        buttonClear.addActionListener(bh);
        
        SpinnerHandler sh = new SpinnerHandler();
        b1.addChangeListener(sh);
        b2.addChangeListener(sh);
        b3.addChangeListener(sh);

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
        buttonContainer.setLayout(new GridLayout(2, 4));
        buttonContainer.add(labelRed);
        buttonContainer.add(labelGreen);
        buttonContainer.add(labelBlue);
        buttonContainer.add(panelColor);
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
    
    private void initSpinners() {
        //new SpinnerNumberModel(initial, min, max, step)
        SpinnerModel model = new SpinnerNumberModel(0, 0, 255, 1);
        
        b1.setModel(model);
        b2.setModel(model);
        b3.setModel(model);
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
            
            //if (source == buttonChooseColor)
                //setColor();
            if (source == buttonClear)
                clearPixels();
        }
    }
    
    
    private class SpinnerHandler implements ChangeListener 
    {
        @Override
        public void stateChanged(ChangeEvent e) 
        {
            checkSpinner(b1);
            checkSpinner(b2);
            checkSpinner(b3);
            
            int red = (Integer)b1.getValue();
            int green = (Integer)b2.getValue();
            int blue = (Integer)b3.getValue();
            
            currentColor = new Color(red, green, blue);
            panelColor.setColor(currentColor);
            panelColor.repaint();
            tf.setText("I choose you, " + currentColor);
        }
    }
    
    private void checkSpinner(JSpinner spinner)
    {
        if ((Integer)spinner.getValue() > 255)
            spinner.setValue((Object)(new Integer(255)));
        else if ((Integer)spinner.getValue() < 0)
            spinner.setValue((Object)(new Integer(0)));
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
