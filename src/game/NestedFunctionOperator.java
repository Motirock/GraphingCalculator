package game;

public class NestedFunctionOperator extends Operator {
    protected Equation equation;

    public NestedFunctionOperator(char opp, Equation equation) {
        super(opp);
        this.equation = equation;
    }

    public double apply(double c, double x) {
        return c*equation.calculate(x);
    }
}
