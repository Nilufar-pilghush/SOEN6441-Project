package main.java.warzone.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.warzone.constants.WarzoneConstants;

/**
 * Contains helper methods to manage command line operation
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @since 1.0.0
 */
public class CmdUtils {
    /**
     * Splits and parses the user input into tokens based on certain delimiters.
     *
     * @param p_UserInput Raw user input.
     * @return A list containing tokens.
     */
    public static List<String> tokenizeUserInput(String p_UserInput) {
        List<String> l_InputParts = new ArrayList<>();
        if (p_UserInput.contains(WarzoneConstants.HYPHEN)) {
            String[] l_HyphenSplit = p_UserInput.split(WarzoneConstants.HYPHEN);
            for (String l_Part : l_HyphenSplit) {
                l_InputParts.add(l_Part.trim());
            }
            return l_InputParts;
        } else {
            String[] l_SpaceSplit = p_UserInput.split(WarzoneConstants.SPACE_REGEX);
            l_InputParts.addAll(Arrays.asList(l_SpaceSplit));
            return l_InputParts;
        }
    }
}
