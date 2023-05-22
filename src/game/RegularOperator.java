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
            default:
                return -1.0;
        }
    }
}
