package operators;

import operand.Operand;

import java.util.HashMap;

public abstract class Operator
{
    public static HashMap<String, Operator> operatorHashMap;

    public abstract int priority();

    public abstract Operand execute(Operand operand1, Operand operand2);

    public static boolean check(String token)
    {
        return operatorHashMap.containsKey(token);
    }
}

