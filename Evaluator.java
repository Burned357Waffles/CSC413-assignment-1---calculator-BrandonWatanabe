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
        // note that when we eval the expression 1 - 2 we will
        // push the 1 then the 2 and then do the subtraction operation
        // This means that the first number to be popped is the
        // second operand, not the first operand - see the following code

        Operator oldOperator = operatorStack.pop();
        Operand operand2 = operandStack.pop();
        Operand operand1 = operandStack.pop();
        operandStack.push(oldOperator.execute(operand1, operand2));
    }

    public boolean processParentheses(Operator newOperator)
    {
        if (newOperator.equals(Operator.operatorHashMap.get("("))) {
            operatorStack.push(newOperator);
            return false;
        } else if (newOperator.equals(Operator.operatorHashMap.get(")"))) {
            while (!operatorStack.peek().equals(Operator.operatorHashMap.get("("))) {
                performOperation();
            }
            operatorStack.pop();
            return false;
        } else {
            operatorStack.push(newOperator);
            return true;
        }
    }

    public int eval(String expression)
    {
        String token;

        // The 3rd argument is true to indicate that the delimiters should be used
        // as tokens, too. But, we'll need to remember to filter out spaces.
        this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);

        while (this.tokenizer.hasMoreTokens()) {
            // filter out spaces
            if (!(token = this.tokenizer.nextToken()).equals(" ")) {
                // check if token is an operand
                if (Operand.check(token)) { // if it is an operand
                    operandStack.push(new Operand(token));
                } else { // if it is not an operand
                    if (!Operator.check(token)) { // if it is not a valid token STOP
                        System.out.println("*****invalid token******");
                        System.exit(1);
                    }

                    // TODO Operator is abstract - this line will need to be fixed:
                    // ( The Operator class should contain an instance of a HashMap,
                    // and values will be instances of the Operators.  See Operator class
                    // skeleton for an example. )
                    Operator newOperator = Operator.operatorHashMap.get(token);

                    if (processParentheses(newOperator)) {
                        continue;
                    }

                    if (operandStack.size() >= 2) {
                        performOperation();

                    }
                }
            }
        }

        // Control gets here when we've picked up all of the tokens; you must add
        // code to complete the evaluation - consider how the code given here
        // will evaluate the expression 1+2*3
        // When we have no more tokens to scan, the operand stack will contain 1 2
        // and the operator stack will have + * with 2 and * on the top;
        // In order to complete the evaluation we must empty the stacks (except
        // the init operator on the operator stack); that is, we should keep
        // evaluating the operator stack until empty
        // Suggestion: create a method that takes an operator as argument and
        // then executes the while loop; also, move the stacks out of the main
        // method

        return operandStack.pop().getValue();
    }
}
