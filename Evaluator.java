import operand.Operand;
import operators.*;

import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator
{
    private Stack<Operand> operandStack;
    private Stack<Operator> operatorStack;

    private StringTokenizer tokenizer;
    private static final String DELIMITERS = "+-*^/() ";

    public Evaluator()
    {
        operandStack = new Stack<>();
        operatorStack = new Stack<>();

        Operator.operatorHashMap = new HashMap<String, Operator>();
        Operator.operatorHashMap.put("+", new AdditionOperator());
        Operator.operatorHashMap.put("-", new SubtractionOperator());
        Operator.operatorHashMap.put("*", new MultiplicationOperator());
        Operator.operatorHashMap.put("/", new DivisionOperator());
        Operator.operatorHashMap.put("^", new ExponentiationOperator());
        Operator.operatorHashMap.put("(", new OpenParenthesisOperator());
        Operator.operatorHashMap.put(")", new ClosedParenthesisOperator());
    }

    public void performOperation()
    {
        Operator oldOperator = operatorStack.pop();
        Operand operand2 = operandStack.pop();
        Operand operand1 = operandStack.pop();
        operandStack.push(oldOperator.execute(operand1, operand2));
    }

    public void processNewOperator(Operator newOperator)
    {
        if (newOperator == Operator.operatorHashMap.get("(")) {
            operatorStack.push(newOperator);
        } else if (newOperator == Operator.operatorHashMap.get(")")) {
            while (operatorStack.peek() != Operator.operatorHashMap.get("(")) {
                performOperation();
            }
            operatorStack.pop();
        } else {
            while (operatorStack.peek().priority() >= newOperator.priority()) {
                performOperation();
            }
            operatorStack.push(newOperator);
        }
    }

    public int eval(String expression)
    {
        String token;

        this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);

        if (operatorStack.size() == 0) {
            operatorStack.push(new PlaceholderOperator());
        }

        while (this.tokenizer.hasMoreTokens()) {
            if (!(token = this.tokenizer.nextToken()).equals(" ")) {
                if (Operand.check(token)) {
                    operandStack.push(new Operand(token));
                } else {
                    if (!Operator.check(token)) {
                        System.out.println("*****invalid token******");
                        System.exit(1);
                    }

                    Operator newOperator = Operator.operatorHashMap.get(token);
                    processNewOperator(newOperator);
                }
            }
        }
        while (operatorStack.size() > 1) {
            performOperation();
        }

        return operandStack.pop().getValue();
    }
}
