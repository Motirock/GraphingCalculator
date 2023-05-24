package game;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class Calculator {
    private long updates = 0;

    private GamePanel gp;

    private static NumberFormat formatter = new DecimalFormat("#0.00000");
    private String filePath;

    private double viewChangeAmount = 0.0;
    private int previousMouseX = 0, previousMouseY = 0;
    private double viewCenterX = 0.0, viewCenterY = 0.0, viewScale = 1.0;
    double domainStart = viewCenterX-viewScale, domainEnd = viewCenterX+viewScale;
    double rangeStart = viewCenterY-viewScale, rangeEnd = viewCenterY+viewScale;
    boolean gaveOutPut = false;

    ArrayList<Equation> equations = new ArrayList<Equation>();

    public Calculator(GamePanel gp) {
        this.gp = gp;
        filePath = System.getProperty("user.dir");
        Equation.gp = gp;

        loadEquations();
    }

    //Be able to set windown dimensions and position
    public void loadEquations() {
        equations = new ArrayList<Equation>();
        int colorID = 0;
        int graphDeriveDegree = 0;

        try {
            File text = new File("");
            try {
                text = new File(filePath+"/src/data/input.txt");
            } catch (Exception e0) {
                try {
                    text = new File(filePath+"/data/input.txt");
                } catch (Exception e1) {
                    try {
                        text = new File(filePath+"/input.txt");
                    } catch (Exception e2) {
                        try {
                            text = new File(filePath+"input.txt");
                        } catch (Exception e3) {}
                    }
                }
            }
            Scanner saveScanner = new Scanner(text);

            System.out.println("\nStarting loading equations");
            while (saveScanner.hasNextLine()) {
                //Reading input
                String line = saveScanner.nextLine().trim();

                //Ignores everything past '#' (comments/documentation)
                if (line.contains("#")) {
                    line = line.substring(0, line.indexOf('#'));
                    continue;
                }

                //Loading equations
                if (line.startsWith("GRAPH:")) {
                    line = line.substring(6).trim().toLowerCase();
                    Equation equation = new Equation(Integer.parseInt(line), colorID);
                    equation.graphDeriveDegree = graphDeriveDegree;
                    equations.add(equation);
                    continue;
                }

                //Derivatives
                if (line.startsWith("DERIVE:")) {
                    line = line.substring(7).trim().toLowerCase();
                    int equationID = Integer.parseInt(line.substring(0, line.indexOf(",")));
                    line = line.substring(line.indexOf(",")+1).trim();
                    double x = Double.parseDouble(line.substring(0, line.indexOf(",")));
                    line = line.substring(line.indexOf(",")+1).trim();
                    int degree = Integer.parseInt(line);
                    System.out.println("The derivative of function "+equationID+" with a degree of "+degree+" at "+x+" is "+formatter.format(derivative(new Equation(equationID, 0), x, degree)));
                    continue;
                }

                //Integrals
                if (line.startsWith("INTEGRATE:")) {
                    line = line.substring(10).trim().toLowerCase();
                    int equationID = Integer.parseInt(line.substring(0, line.indexOf(",")));
                    line = line.substring(line.indexOf(",")+1).trim();
                    double a = Double.parseDouble(line.substring(0, line.indexOf(",")));
                    line = line.substring(line.indexOf(",")+1).trim();
                    double b = Double.parseDouble(line);
                    System.out.println("The integral of function "+equationID+" from "+a+" to "+b+" is "+formatter.format(integral(new Equation(equationID, 0), a, b)));
                    continue;
                }

                else if (line.startsWith("FLAG:")) {
                    line = line.substring(5).trim();

                    //Clearing flags
                    if (line.startsWith("CLEAR")) {
                        colorID = 0;
                        graphDeriveDegree = 0;
                        continue;
                    }

                    //Changing color
                    if (line.startsWith("COLOR_ID:")) {
                        line = line.substring(9).trim();
                        colorID = Integer.parseInt(line);
                        continue;
                    }

                    //Graphing derivatives
                    if (line.startsWith("GRAPH_DERIVATIVE:")) {
                        line = line.substring(17).trim();
                        graphDeriveDegree = Integer.parseInt(line);
                        continue;
                    }
                }
            }
            System.out.println("\nDone loading input file");

            saveScanner.close();
        } catch (IOException e) {
            System.out.println("Error occurred while loading");
            e.printStackTrace();
        }
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

        if (viewScale <= 0.001)
            viewScale = 0.001;

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
                double y;
                if (equation.graphDeriveDegree > 0)
                    y = derivative(equation, x, equation.graphDeriveDegree);
                else
                    y = equation.calculate(x);
                    
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

    public static float integral(Equation e, double a, double b) {
        double total = 0;
        double length = b-a;
        boolean backwards = false;
        if (length < 0) {
            backwards = true;
            length = -length;
            double temp = a;
            a = b;
            b = temp;
        }
        double totalSteps = 10_000_000*Math.pow(length, 0.8);
        if (totalSteps > 1_000_000_000)
            totalSteps = 1_000_000_000;
        double step = length/totalSteps;
        for (double i = a; i <= b; i+=step) {
            total += step*e.calculate(i);
            
        }
        if (backwards)
            return (float) -total;
        return (float) total;
    }

    public static double derivative(Equation e, double x, int degree) {
        double h = 1.0/1_000_000;
        for (int i = 1; i < degree; i++)
            h *= 10;

        if (degree < 1)
            return e.calculate(x);
        if (degree < 2) {
            return ((e.calculate(x+h))-e.calculate(x))/h;
        }

        return (derivative(e, x+h, degree-1)-derivative(e, x, degree-1))/h;
    }
}
