package game;

import main.KeyHandler;
import main.MouseHandler;
import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.NumberFormat;
import java.text.DecimalFormat;
//TODO RENAME TO CALCULTAOR, put in static methods
public class Calculator {
    private long updates = 0;

    private GamePanel gp;

    private Scanner input;

    private static NumberFormat formatter = new DecimalFormat("#0.00000");

    private double viewChangeAmount = 0.0;
    private int previousMouseX = 0, previousMouseY = 0;
    private double viewCenterX = 0.0, viewCenterY = 0.0, viewScale = 1.0;
    double domainStart = viewCenterX-viewScale, domainEnd = viewCenterX+viewScale;
    double rangeStart = viewCenterY-viewScale, rangeEnd = viewCenterY+viewScale;
    boolean gaveOutPut = false;

    ArrayList<Equation> equations = new ArrayList<Equation>();

    public Calculator(GamePanel gp) {
        this.gp = gp;
        input = new Scanner(System.in);
        Equation.gp = gp;

        equations.add(new Equation("34*5", 0));
    }

    public void update() {
        updates++;

        int mouseXMovement = gp.mouseX-previousMouseX;
        int mouseYMovement = gp.mouseY-previousMouseY;

        viewChangeAmount += gp.mouseScrollAmount*0.01;
        viewScale += viewChangeAmount*0.8*viewScale;
        if (Math.abs(viewChangeAmount) < 0.001)
            viewChangeAmount = 0.0;
        
        if (gp.mouseLeftPressed && (mouseXMovement != 0 || mouseYMovement != 0)) {
            viewCenterX -= (mouseXMovement*0.001)*viewScale;
            viewCenterY -= (mouseYMovement*0.001/0.9*1.6)*viewScale;
        }

        //Updating domain and range of the portion shown
        domainStart = viewCenterX-viewScale;
        domainEnd = viewCenterX+viewScale;
        rangeStart = viewCenterY-viewScale;
        rangeEnd = viewCenterY+viewScale;

        //Prints coordinates of cursor when mouse is right-clicked OR up arrow is pressed
        if (gp.mouseRightPressed || gp.keyH.upArrowIsPressed) {
            System.out.println("X: "+formatter.format(domainStart+(domainEnd-domainStart)*(gp.mouseX/1600.0))+"\tY: "+formatter.format(rangeStart+(rangeEnd-rangeStart)*(gp.mouseY/900.0)));

        }

        //Reseting and updating variables
        viewChangeAmount *= 0.8;
        previousMouseX = gp.mouseX;
        previousMouseY = gp.mouseY;

        
    }

    public void draw(Graphics2D g2, double GS) {
        g2.setBackground(Color.white);

        double domainSize = domainEnd-domainStart;
        double rangeSize = rangeEnd-rangeStart;
        double domainScale = domainSize/1600;
        double rangeScale = rangeSize/900;
        int[] pointsX = new int[1601];
        int[] pointsY = new int[1601];
        int index = 0;

        g2.setColor(Color.WHITE);
        index = 0;
        for (double i = 0; i <= 1600; i += 1600) {
            double y = 0;
                
            int drawnY = (int) (y/rangeScale+rangeStart/rangeScale);
            pointsX[index] = (int) (i*GS);
            pointsY[index++] = (int) -(drawnY*GS);
        }
        g2.drawPolyline(pointsX, pointsY, index);
        index = 0;
        for (double i = 0; i <= 900; i += 900) {
            double x = 0;
                
            int drawnX = (int) (x/domainScale+domainStart/domainScale);
            pointsX[index] = (int) (i*GS);
            pointsY[index++] = (int) -(drawnX*GS);
        }
        g2.drawPolyline(pointsY, pointsX, index);

        for (Equation equation : equations) {
            g2.setColor(equation.getColor());
            index = 0;
            for (double i = 0; i <= 1600; i += 1) {
                double x = i*domainScale+domainStart;
                double y = equation.calculate(x);
                    
                //If point is undefined
                if (y-y != 0) {
                    g2.drawPolyline(pointsX, pointsY, index);
                    index = 0;
                    continue;
                }
                int drawnY = (int) (y/rangeScale+rangeStart/rangeScale);
                pointsX[index] = (int) (i*GS);
                pointsY[index++] = (int) -(drawnY*GS);
            }
            g2.drawPolyline(pointsX, pointsY, index);
        }
    }

    public static float integral(double a, double b) {
        double total = 0;
        double length = b-a;
        double step = length/10000000;
        for (double i = a; i <= b; i+=step) {
            total += step*(Math.cos(i));
            
        }
        return (float) total;
    }

    public static float integral(String s, double a, double b) {
        double total = 0;
        return (float) total;
    }

    public static float derivative(double x) {
        double h = 1.0/10_000_000;
        return (float) ((((x+h)*(x+h))-x*x)/h);
    }

    public static float derivative(String s, double x) {
        return -1.0f;
    }
}
