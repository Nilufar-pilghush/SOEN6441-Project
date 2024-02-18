package test.java.com.warzone;
        import main.java.warzone.Main;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;
        import java.io.ByteArrayInputStream;
        import java.io.InputStream;
/**
 * JUnit test cases for the Main class.
 * Test case to verify game exit.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class MainTest {
    /**
     * Test case to validate game running
     */
    @Test
    public void shouldAnswerWithTrue() {
        String[] l_Args = new String[0];
        String input1 = "3";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(()-> Main.main(l_Args));
    }
}
