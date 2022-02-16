public class DivisionOperator extends Operator
{

    @Override
    public int priority()
    {
        return 3;
    }

    @Override
    public Operand execute(Operand operand1, Operand operand2)
    {
        return new Operand(operand1.getValue() / operand2.getValue());
    }
}
