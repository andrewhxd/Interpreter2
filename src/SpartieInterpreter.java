public class SpartieInterpreter {
    Object run(Expression expression) {
        Object result = interpret(expression);
        return result;
    }

    Object interpret(Expression expression) {
        // Depending on the expression type from the parser, we attempt to interpret the expression
        return switch (expression) {
            case Expression.LiteralExpression literalExpression -> interpretLiteral(literalExpression);
            case Expression.ParenthesesExpression parenthesesExpression -> interpretParenthesis(parenthesesExpression);
            case Expression.UnaryExpression unaryExpression -> interpretUnary(unaryExpression);
            case Expression.BinaryExpression binaryExpression -> interpretBinary(binaryExpression);
            case null, default -> null;
        };
    }

    private Object interpretLiteral(Expression.LiteralExpression expression) {
        // This is fairly simple, just return the actual literal value. For example "some string" or 3.0
        return expression.literalValue;
    }

    private Object interpretParenthesis(Expression.ParenthesesExpression expression) {
        // Take what is inside the parenthesis and send it back to our interpreter
        return this.interpret(expression.expression);
    }

    private Object interpretUnary(Expression.UnaryExpression expression) {
        Object right = interpret(expression.right);

        // TODO: Complete interpreter. Check the unary operator type (! or -) and perform the operation
        // TODO: Before negating (-), check to make sure the type is correct using the provided validateOperand method
        switch (expression.operator.type) {
            case NOT:
                return !isTrue(right);
            case SUBTRACT:
                // Check correct type
                validateOperand(expression.operator, right);
                return -(double)right;
        }

        return null;
    }

    private Object interpretBinary(Expression.BinaryExpression expression) {
        Object left = interpret(expression.left);
        Object right = interpret(expression.right);

        // TODO: Add support for String concatenation, for example: "Jane" + "Doe"
        // TODO: Add support for String concatenation with a double that is rounded, for example "Jane is " + 12
        // TODO: Handle unique case with add operator that can be applied to Strings and Doubles
        if (expression.operator.type == TokenType.ADD) {
            // TODO: Return the correct evaluation
            if (left instanceof String || right instanceof String) {
                String leftStr = (left instanceof Number) ?
                        String.format("%.2f", ((Number)left).doubleValue()) :
                        String.valueOf(left);
                String rightStr = (right instanceof Number) ?
                        String.format("%.2f", ((Number)right).doubleValue()) :
                        String.valueOf(right);
                return leftStr + rightStr;
            }
        }

        // TODO: Test if the two are equivalent or not equivalent
        switch(expression.operator.type) {
            case EQUIVALENT:
                return isEquivalent(left, right);
            case NOT_EQUAL:
                return !isEquivalent(left, right);
        }

        // At this point, we can validate if our operands are doubles because they cannot be Strings for the other
        // operation
        validateOperands(expression.operator, left, right);
        double leftVal = (double)left;
        double rightVal = (double)right;
        // TODO: Handle binary operator for operands. Keep in mind, at this point, we know they are doubles, but you
        // TODO: still need to cast them to doubles. Use the primitive type, e.g. (double)left
        // TODO: we do not support >, >=, <, or <= on Strings
        switch(expression.operator.type) {
            case ADD:
                return leftVal + rightVal;
            case SUBTRACT:
                return leftVal - rightVal;
            case MULTIPLY:
                return leftVal * rightVal;
            case DIVIDE:
                return leftVal / rightVal;
            case GREATER_THAN:
                return leftVal > rightVal;
            case GREATER_EQUAL:
                return leftVal >= rightVal;
            case LESS_THAN:
                return leftVal < rightVal;
            case LESS_EQUAL:
                return leftVal <= rightVal;
        }

        return null;
    }

    // Helper Methods

    // TODO: Complete implementation of testing for equivalency
    private boolean isEquivalent(Object left, Object right) {
        // They are equal under the following conditions:
        // 1. They are both null
        // 2. Their values are the same
        if (left == null && right == null) {
            return true;
        }

        if (left.equals(right)) {
            return true;
        }

        return false;
    }

    // TODO: Complete implementation of isTrue.
    private boolean isTrue(Object object) {
        // We should return false if an object is null
        // If an object is of type boolean, we should return the primitive equivalent of that value
        if (object == null) {
            return false;
        }

        if (object instanceof Boolean) {
            return (Boolean) object;
        }
        return true;
    }

    // Validate the type
    private void validateOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        error("Invalid type on line " + operator.line + " : " + operator.text + operand);
    }

    private void validateOperands(Token operator, Object operand1, Object operand2) {
        if (operand1 instanceof Double && operand2 instanceof Double) return;
        error("Invalid type on line " + operator.line + " : " + operand1 + operator.text + operand2);
    }


    private void error(String message) {
        System.err.println(message);
        System.exit(2);
    }
}
