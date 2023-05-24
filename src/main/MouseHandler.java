/*
I already wrote most functions for handling mouse input, but feel free to modify them.
NOTE: just like KeyHandler, the instance variables are public. This allows you to manipulate them more easily, but be careful.
*/

package main;

import java.awt.event.*; //TODO

import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseWheelListener, MouseMotionListener {
    public boolean mouseLeftPressed, mouseLeftClicked, mouseRightPressed, mouseRightClicked, mouseScrolled;
    public int mouseX, mouseY, mouseScrollAmount;
    double GS;  //The graphic scaling is how different the window size is from 1600x900

    public MouseHandler(double GS) {
        this.GS = GS;
    }
    
    //If any mouse button is pressed. The internal if statements handle if it is the right or left mouse button
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseLeftPressed = true;
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            mouseRightPressed = true;
        }
    }
    
    //If any mouse button is released. The internal if statements handle if it is the right or left mouse button
    @Override 
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseLeftPressed = false;
            mouseLeftClicked = true;
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            mouseRightPressed = false;
            mouseRightClicked = true;
        }
    }
    
    //Mouse NOT pressed while moving
    @Override 
    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX()/GS);
        mouseY = (int) (e.getY()/GS);
    }
    
    //Mouse pressed and moved at the same time
    @Override 
    public void mouseDragged(MouseEvent e) {
        mouseX = (int) (e.getX()/GS);
        mouseY = (int) (e.getY()/GS);
    }
    
    //Mouse scrolling and how much it scrolls. 
    //The amount scrolled and its direction depends on your system, so make sure to check using the TODO Mouse Hello World program
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseScrolled = true;
        mouseScrollAmount = e.getWheelRotation();
    }
    
    //I don't use this because it is a bit finicky. I substitute it by treating mouseReleased like mouseClicked
    @Override
    public void mouseClicked(MouseEvent arg0) {}
    
    //Mouse entering the window
    @Override
    public void mouseEntered(MouseEvent arg0) {}
    
    //Mouse exiting the window
    @Override
    public void mouseExited(MouseEvent arg0) {}
}
