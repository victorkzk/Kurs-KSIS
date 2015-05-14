import org.junit.Test;
import application.FormHandler;
import static org.junit.Assert.*;

public class Test0 {
    @Test
    public void testSumA() {
        FormHandler formHandler = new FormHandler();
        int n = formHandler.sum(2, 8);
        assertEquals(10, n);
    }

    @Test
    public void testSumB() {
        FormHandler formHandler = new FormHandler();
        int n = formHandler.sum(1, 0);
        assertEquals(0, n);
    }
}
