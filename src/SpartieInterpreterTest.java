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
        Token addToken = new Token(TokenType.ADD, "+", 1);
        Expression.LiteralExpression literal1 = new Expression.LiteralExpression("Hello ");
        Expression.LiteralExpression literal2 = new Expression.LiteralExpression(100);
        Expression.BinaryExpression expr = new Expression.BinaryExpression(literal1, addToken, literal2);

        SpartieInterpreter spartieInterpreter = new SpartieInterpreter();
        Object result = spartieInterpreter.run(expr);
        System.out.println(result);
        assertEquals("Hello 100.00", result);
    }


}