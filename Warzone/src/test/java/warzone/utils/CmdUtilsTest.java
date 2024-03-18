package test.java.warzone.utils;

        import main.java.warzone.utils.CmdUtils;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

/**
 * Provides JUnit test cases for the {@link CmdUtils} class, focusing on validating the utility's
 * ability to correctly tokenize user input. This includes tests for splitting hyphenated arguments
 * and arguments separated by spaces, ensuring that the utility functions as expected across different
 * types of user input.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class CmdUtilsTest {

    /**
     * Tests the tokenization of user input containing hyphenated arguments.
     * It verifies that the input is correctly split into the expected number of parts,
     * demonstrating the utility's handling of hyphenated arguments.
     */
    @Test
    public void testTokenizeUserInputWithHyphen() {
        String l_userInput = "editcontinent -add Asia 5";
        Assertions.assertEquals(2, CmdUtils.tokenizeUserInput(l_userInput).size());
    }
    /**
     * Tests the tokenization of user input separated by spaces.
     * This test ensures that space-separated inputs are correctly split into individual arguments,
     * reflecting the utility's capability to process commands composed of multiple space-separated components.
     */

    @Test
    public void testTokenizeUserInputWithSpace() {
        String l_userInput = "editmap hello world";
        Assertions.assertEquals(3, CmdUtils.tokenizeUserInput(l_userInput).size());
    }
}
