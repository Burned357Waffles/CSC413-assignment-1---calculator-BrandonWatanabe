public class ExponentiationOperator extends Operator
{

    @Override
    public int priority()
    {
        return 4;
    }

    @Override
    public Operand execute(Operand operand1, Operand operand2)
    {
        return new Operand((int) Math.pow(operand1.getValue(), operand2.getValue()));
    }
}
