package test.java.com.warzone.utils;

        import main.java.warzone.utils.CmdUtils;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the Cmd Utils class.
 * Test case to verify hyphenated user input split.
 * Test case to verify space separated user input split.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class CmdUtilsTest {

    /**
     * Test case to verify hyphenated user input split.
     */
    @Test
    public void whenGetUserInputPartsWithHyphen_ExpectHyphenatedSplitTest() {
        String l_userInput = "editcontinent -add Asia 5";
        Assertions.assertEquals(2, CmdUtils.tokenizeUserInput(l_userInput).size());
    }

    /**
     * Test case to verify space separated user input split.
     */
    @Test
    public void whenGetUserInputPartsWithSpace_ExpectSpaceSplitTest() {
        String l_userInput = "editmap hello world";
        Assertions.assertEquals(3, CmdUtils.tokenizeUserInput(l_userInput).size());
    }
}
