package game;

public class Operator {
    protected char operator;

    public Operator (char opp) {
        operator = opp;
    }

    //C is the number to modified, x is the value of x
    public double apply(double c, double x) {
        return -1.0;
    };
}