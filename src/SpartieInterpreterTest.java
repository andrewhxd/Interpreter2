import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpartieInterpreterTest {
    @Test
    void testInterpreterUnary() {
        Token minusToken = new Token(TokenType.SUBTRACT, "-", 1);
        Expression.LiteralExpression literal = new Expression.LiteralExpression(5.0);
        Expression.UnaryExpression expr = new Expression.UnaryExpression(minusToken, literal);

        SpartieInterpreter spartieInterpreter = new SpartieInterpreter();
        Object result = spartieInterpreter.run(expr);
        //System.out.println(result);
        assertEquals(-5.0, result);
    }

    @Test
    void testInterpretBinary() {
        // Test String Concatenation
        Token addToken = new Token(TokenType.ADD, "+", 1);
        Expression.LiteralExpression literal1 = new Expression.LiteralExpression("Hello ");
        Expression.LiteralExpression literal2 = new Expression.LiteralExpression(100);
        Expression.BinaryExpression exp1 = new Expression.BinaryExpression(literal1, addToken, literal2);

        SpartieInterpreter spartieInterpreter = new SpartieInterpreter();
        Object result = spartieInterpreter.run(exp1);
        //System.out.println(result);
        assertEquals("Hello 100.00", result);

        // Test Equivalence
        Token eqToken = new Token(TokenType.EQUIVALENT, "==", 1);
        Token notEqToken = new Token(TokenType.NOT_EQUAL, "!=", 1);

        Expression.LiteralExpression literal3a = new Expression.LiteralExpression("Hello");
        Expression.LiteralExpression literal3b = new Expression.LiteralExpression("Hello");
        Expression.LiteralExpression literal4 = new Expression.LiteralExpression("World");

        Expression.BinaryExpression expr2 = new Expression.BinaryExpression(literal3a, eqToken, literal3b); // Hello == Hello
        Expression.BinaryExpression expr3 = new Expression.BinaryExpression(literal3a, eqToken, literal4); // Hello == World
        Expression.BinaryExpression expr4 = new Expression.BinaryExpression(literal3a, notEqToken, literal3b); // Hello != Hello
        Expression.BinaryExpression expr5 = new Expression.BinaryExpression(literal3a, notEqToken, literal4); // Hello != World

        Object result2 = spartieInterpreter.run(expr2);
        Object result3 = spartieInterpreter.run(expr3);
        Object result4 = spartieInterpreter.run(expr4);
        Object result5 = spartieInterpreter.run(expr5);

        //System.out.println(result2); Hello == Hello -> True
        assertEquals(true, result2); // Hello == Hello
        assertEquals(false, result3); // Hello == World

        assertEquals(false, result4); // Hello != Hello
        assertEquals(true, result5); // Hello != World
    }


}