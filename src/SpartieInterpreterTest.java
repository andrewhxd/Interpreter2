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



}