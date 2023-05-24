package game;

public class NestedFunctionOperator extends Operator {
    protected Equation equation;

    public NestedFunctionOperator(char opp, Equation equation) {
        super(opp);
        this.equation = equation;
    }

    public double apply(double c, double x) {
        switch (operator) {
            case '^':
                return Math.pow(c, equation.calculate(x));
            case '*':
                return c*equation.calculate(x);
            case '/':
                return c/equation.calculate(x);
            case '%':
                return c%equation.calculate(x);
            case '+':
                return c+equation.calculate(x);
            case '-':
                return c-equation.calculate(x);
            case 's':
                return c * Math.sin(equation.calculate(x));
            case 'c':
                return c * Math.cos(equation.calculate(x));
            default:
                return -1.0;
        }
    }

    public String toString() {
        return "\tNESTED OPP: "+operator+", "+equation;
    }
}
