package operators;

import operand.Operand;

public class PlaceholderOperator extends Operator
{
    @Override
    public int priority()
    {
        return 0;
    }

    @Override
    public Operand execute(Operand operand1, Operand operand2)
    {
        return null;
    }
}
