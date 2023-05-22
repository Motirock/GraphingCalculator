package game;

public class XOperator extends Operator {
    public XOperator(char opp) {
        super(opp);
    }

    public double apply(double c, double x) {
        switch (operator) {
            case '^':
                return Math.pow(c, x);
            case '*':
                return c*x;
            case '/':
                return c/x;
            case '%':
                return c%x;
            case '+':
                return c+x;
            case '-':
                return c-x;
            default:
                return -1.0;
        }
    }
}
