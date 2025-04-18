import com.example.Calculatrice;
import com.example.MetricsManager;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Test;
import org.junit.rules.*;

public class CalculatriceTest {

    private final Calculatrice calculatrice = new Calculatrice();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void setUpMetrics() {
        MetricsManager.initMetrics();
    }

    @AfterClass
    public static void tearDownMetrics() {
        MetricsManager.stopMetrics();
    }

    @Test
    public void testAddition() {
        // Test cas normaux
        assertEquals(5, calculatrice.addition(2, 3));
        MetricsManager.recordOperation("addition", 5);

        assertEquals(0, calculatrice.addition(0, 0));
        MetricsManager.recordOperation("addition", 0);

        assertEquals(-5, calculatrice.addition(-2, -3));
        MetricsManager.recordOperation("addition", -5);

        // Test avec nombres négatifs et positifs
        assertEquals(-1, calculatrice.addition(2, -3));
        MetricsManager.recordOperation("addition", -1);

        assertEquals(1, calculatrice.addition(-2, 3));
        MetricsManager.recordOperation("addition", 1);

        // Test avec grands nombres
        assertEquals(2000000000, calculatrice.addition(1000000000, 1000000000));
        MetricsManager.recordOperation("addition", 2000000000);
    }

    @Test
    public void testSoustraction() {
        // Test cas normaux
        assertEquals(-1, calculatrice.soustraction(2, 3));
        MetricsManager.recordOperation("soustraction", -1);

        assertEquals(0, calculatrice.soustraction(0, 0));
        MetricsManager.recordOperation("soustraction", 0);

        assertEquals(1, calculatrice.soustraction(-2, -3));
        MetricsManager.recordOperation("soustraction", 1);

        // Test avec nombres négatifs et positifs
        assertEquals(5, calculatrice.soustraction(2, -3));
        MetricsManager.recordOperation("soustraction", 5);

        assertEquals(-5, calculatrice.soustraction(-2, 3));
        MetricsManager.recordOperation("soustraction", -5);

        // Test avec grands nombres
        assertEquals(0, calculatrice.soustraction(1000000000, 1000000000));
        MetricsManager.recordOperation("soustraction", 0);
    }

    @Test
    public void testMultiplication() {
        // Test cas normaux
        assertEquals(6, calculatrice.multiplication(2, 3));
        MetricsManager.recordOperation("multiplication", 6);

        assertEquals(0, calculatrice.multiplication(0, 5));
        MetricsManager.recordOperation("multiplication", 0);

        assertEquals(0, calculatrice.multiplication(5, 0));
        MetricsManager.recordOperation("multiplication", 0);

        assertEquals(6, calculatrice.multiplication(-2, -3));
        MetricsManager.recordOperation("multiplication", 6);

        // Test avec nombres négatifs et positifs
        assertEquals(-6, calculatrice.multiplication(2, -3));
        MetricsManager.recordOperation("multiplication", -6);

        assertEquals(-6, calculatrice.multiplication(-2, 3));
        MetricsManager.recordOperation("multiplication", -6);
    }

    @Test
    public void testDivision() {
        // Test cas normaux
        assertEquals(2.0f, calculatrice.division(6, 3), 0.01f);
        MetricsManager.recordOperation("division", 2.0f);
    }
}