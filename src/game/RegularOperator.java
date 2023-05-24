package game;

public class RegularOperator extends Operator {
    protected double number;

    //If using a non x number for n
    public RegularOperator(char opp, double n) {
        super(opp);
        number = n;
    }

    public double apply(double c, double x) {
        switch (operator) {
            case '^':
                return Math.pow(c, number);
            case '*':
                return c*number;
            case '/':
                return c/number;
            case '%':
                return c%number;
            case '+':
                return c+number;
            case '-':
                return c-number;
            case 's':
                return c * Math.sin(number);
            case 'c':
                return c * Math.cos(number);
            default:
                return -1.0;
        }
    }

    public String toString() {
        return "\tREGULAR OPP: "+operator+", "+number;
    }
}
