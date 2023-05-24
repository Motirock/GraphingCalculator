package main;

//import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    //Game Globals
    public boolean isFullScreen = false,
    mouseLeftClicked = false, mouseLeftPressed = false, mouseRightClicked = false, mouseRightPressed = false, mouseScrolled = false;
    public int menuID = 0, 
    mouseX = 0, mouseY = 0, mouseScrollAmount;
    public enum GameState {PLAYING, INMENU, PAUSED, INGAMEMENU}
    public GameState gameState = GameState.PLAYING;

    //Settings
    public int 
    screenWidth = 1200, /* 16:9 Aspect Ratio */
    screenHeight = 675,
    FPS = 30, //Amount of graphical updates per second
    updatesPerSecond = 60, //Amount of logical updates per second
    volume = 50;
    public double GS = screenHeight/900.0; /* graphics scaling: all graphics are same relative size and position regardless of screen size */

    //Keyboard
    public KeyHandler keyH = new KeyHandler();
    //Actions representing different key presses
    Action upPressedAction = keyH.new UpPressedAction();
    Action upReleasedAction = keyH.new UpReleasedAction();
    Action downPressedAction = keyH.new DownPressedAction();
    Action downReleasedAction = keyH.new DownReleasedAction();
    Action leftPressedAction = keyH.new LeftPressedAction();
    Action leftReleasedAction = keyH.new LeftReleasedAction();
    Action rightPressedAction = keyH.new RightPressedAction();
    Action rightReleasedAction = keyH.new RightReleasedAction();
    //JComponent that the keybinds are added to
    JComponent thisWindow = Main.window.getRootPane();
    public void updateKeyBindings() {
        //Movement
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upPressed");
		thisWindow.getActionMap().put("upPressed", upPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "upReleased");
		thisWindow.getActionMap().put("upReleased", upReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downPressed");
		thisWindow.getActionMap().put("downPressed", downPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "downReleased");
		thisWindow.getActionMap().put("downReleased", downReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftPressed");
		thisWindow.getActionMap().put("leftPressed", leftPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "leftReleased");
		thisWindow.getActionMap().put("leftReleased", leftReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightPressed");
		thisWindow.getActionMap().put("rightPressed", rightPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rightReleased");
		thisWindow.getActionMap().put("rightReleased", rightReleasedAction);
    }

    //Mouse
    public MouseHandler mouseH = new MouseHandler(GS);
    game.Calculator game = new game.Calculator(this);
    Sound sound = new Sound(new ArrayList<String>());
    Thread gameThread;

    //Used in other classes where GameState can't be accessed
    public void setGameState(String thisGameState) {
        switch (thisGameState) {
            case "PLAYING":
                gameState = GameState.PLAYING;
                break;
            case "INMENU":
                gameState = GameState.INMENU;
                break;
            case "PAUSED":
                gameState = GameState.PAUSED;
                break;
            case "INGAMEMENU":
                gameState = GameState.INGAMEMENU;
                break;
        }
    }
    //Used in other classes where GameState can't be accessed
    public String getGameState() {
        switch (gameState) {
            case PLAYING:
                return "PLAYING";
            case INMENU:
                return "INMENU";
            case PAUSED:
                return "PAUSED";
            case INGAMEMENU:
                return "INGAMEMENU";
        }
        return("ERROR: FAULTY GAMESTATE");
    }

    public GamePanel() {
        //System stuff
        //getCommonImages();

        //playSF(0);
        updateKeyBindings();
        
        //Scaling for fullscreen requires additional information, so it comes last
        if (isFullScreen)
            setFullScreen();

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.addMouseWheelListener(mouseH);
        this.setFocusable(true);
    }

    public void setFullScreen() {
        //Getting the width and height of RL screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //Setting the JFrame to have the proper resolution
        screenWidth = 2560;//ge.getMaximumWindowBounds().width;
        screenHeight = 1440;//ge.getMaximumWindowBounds().height;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        GS = screenHeight/900.0;
        //Resets the mouse handler
        mouseH.GS = GS;

        //Setting the JFrame to be full screen
        gd.setFullScreenWindow(Main.window);

        update();
        repaint();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Game loop
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double drawDelta = 0;
        double updateInterval = 1000000000/updatesPerSecond;
        double updateDelta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long drawTimer = 0;
        long updateTimer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            drawDelta += (currentTime-lastTime)/drawInterval;
            updateDelta += (currentTime-lastTime)/updateInterval;
            drawTimer += (currentTime-lastTime);
            updateTimer += (currentTime-lastTime);
            lastTime = currentTime;

            //Logical updates
            if (updateDelta >= 1) {
                update();
                updateDelta--;
            }

            //Graphical updates
            if (drawDelta >= 1) {
                repaint();
                drawDelta--;
                drawCount++;
            }

            if (drawTimer >= 1000000000) {
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                drawTimer = 0;
            }
            if (updateTimer >= 1000000000) {
                updateTimer = 0;
            }
        }
    }

    //Logcial updates
    public void update() {
        //Updates mouse variables
        mouseLeftPressed = mouseH.mouseLeftPressed;
        mouseLeftClicked = mouseH.mouseLeftClicked;
        mouseRightPressed = mouseH.mouseRightPressed;
        mouseRightClicked = mouseH.mouseRightClicked;
        mouseScrolled = mouseH.mouseScrolled;
        mouseX = mouseH.mouseX;
        mouseY = mouseH.mouseY;
        mouseScrollAmount = mouseH.mouseScrollAmount;

        switch (gameState) {
            case INMENU:
            case PAUSED:
                break;
            case INGAMEMENU:
            case PLAYING:
                game.update();
                break;
        }

        //Resets variables that only last for one update
        mouseH.mouseLeftClicked = false;
        mouseH.mouseRightClicked = false;
        mouseH.mouseScrolled = false;
        mouseH.mouseScrollAmount = 0;
    }

    //Graphical updates
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //Anti aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 

        game.draw(g2, GS);        

        g2.dispose();
    }

    //All sound related stuff
    public void playSF(int i) {
        sound.setFile(i);
        setSFVolume(volume);
        sound.play();
    }
    public void setSFVolume(int SFVolume) {
        volume = SFVolume;
        if (volume > 100)
            volume = 100;
        else if (volume < 0)
            volume = 0;
        sound.setVolume(volume);
    }
}
