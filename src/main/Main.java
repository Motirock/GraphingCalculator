///////////////////////////////////////
//  |         |     -----     |---\  //
//  |    |    |       |       |   |  //         //Place your important
//  |    |    |       |       |---/  //         //information up here
//   \__/ \__/      -----     |      //
///////////////////////////////////////

/*
The main class doesn't actually do much, it just creates the window.
Main creates the GamePanel, which coordinates the internal workings of the application.
*/

package main;

import javax.swing.*;

public class Main{

    public static JFrame window = new JFrame();

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
        //Setting properties of window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Closes application when X is pressed
        window.setResizable(false);                             //Can't be manually resized
        window.setTitle("My Java Application");                 //Window title
        window.setFocusTraversalKeysEnabled(false);             //Disables certain keys, like tab, from having special use

        //Adding gamePanel, which actually controls the application
        GamePanel gamePanel = new GamePanel();                  
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);     //Sets the windows position to the center of the screen by default
        window.setVisible(true);                //Makes the window visible

        gamePanel.startGameThread();            //Starts execution of the game
    }
}

