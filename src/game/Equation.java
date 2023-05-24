package game;

import main.GamePanel;

import java.util.ArrayList;
import java.awt.Color;


public class Equation {
    public static GamePanel gp;
    private double startingNumber = 0.0;
    private Equation startingEquation;
    private boolean startingIsX = false, startingIsEquation = false;
    private ArrayList<Operator> operators = new ArrayList<Operator>();
    private Color graphColor = Color.RED;
    public int graphDeriveDegree;

    //Preset functions used for testing
    public Equation(int ID, int colorID) {
        switch (ID) {
            case 0:
                startingIsX = true;
                operators.add(new RegularOperator('^', 3));
                operators.add(new XOperator('-'));
                break;
            case 1:
                startingIsX = true;
                operators.add(new RegularOperator('^', 0.5));
                operators.add(new XOperator('+'));
                break;
            case 2:
                startingIsX = true;
                operators.add(new RegularOperator('-', 2));
                operators.add(new NestedFunctionOperator('*', new Equation(0, 0)));
                break;
            case 3:
                startingIsX = true;
                operators.add(new RegularOperator('/', 4));
                operators.add(new NestedFunctionOperator('*', new Equation(2, 0)));
                break;
            case 4:
                startingNumber = 4.5;
                operators.add(new XOperator('c'));
                break;
            case 5:
                startingIsEquation = true;
                startingEquation = new Equation(2, 0);
                operators.add(new XOperator('/'));
                operators.add(new RegularOperator('^', 2));
                operators.add(new RegularOperator('^', 0.1));
                break;
            case 6:
                startingNumber = 1.5;
                operators.add(new XOperator('s'));
                operators.add(new NestedFunctionOperator('/', new Equation(4, 0)));
                break;
        }

        switch (colorID) {
            case 0:
                graphColor = Color.RED;
                break;
            case 1:
                graphColor = Color.GREEN;
                break;
            case 2:
                graphColor = Color.BLUE;
                break;
            case 3:
                graphColor = Color.YELLOW;
                break;
            case 4:
                graphColor = Color.CYAN;
                break;
            case 5:
                graphColor = Color.PINK;
                break;
            case 6:
                graphColor = Color.WHITE;
                break;
        }
    }

    public double calculate(double x) {
        if (startingIsX)
            startingNumber = x;
        if (startingIsEquation && startingEquation != null)
            startingNumber = startingEquation.calculate(x);
        double total = startingNumber;
        for (Operator opp : operators) {
            total = opp.apply(total, x);
        }
        return total;
    }

    public Color getColor() {
        return graphColor;
    }



    //   STATIC METHODS   \\
    public static String spacing(String s) {
        String t = "";

        //Adding spaces
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            //Characters spaces put between
            if (c == '^' || c == '*' || c == '/' || c == '+' || c == '-' || c == '%' || c == '(' || c == ')') {
                t += " ";
                t += c;
                t += " ";
            }
            else {
                t += c;
            }
        }

        //Removing unessecary spaces
        ArrayList<String> letters = new ArrayList<String>();
        for (int i = 0; i < t.length(); i++)
            letters.add(t.substring(i, i+1));
        for (int i = 0; i < letters.size()-1; i++) {
            if (letters.get(i).charAt(0) == ' ' && letters.get(i+1).charAt(0) == ' ') {
                letters.remove(i--);
                continue;
            }
        }
        if (letters.get(letters.size()-1).charAt(0) != ' ')
            letters.add(" "); 
        if (letters.get(0).charAt(0) != ' ')
            letters.add(0, " "); 

        t = "";
        for (String l : letters)
            t += l;
        
        return t;
    }

    public String toString() {
        String s = "EQUATION:";
        for (Operator opp : operators)
            s += "\n"+opp;
        return s;
    }
    
}