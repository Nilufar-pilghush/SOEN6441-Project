package main.java.com.warzone.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.java.com.warzone.constants.WarzoneConstants;

/**
 * Contains helper methods to manage command line operation
 * @author Ali Sayed Salehi
 */
public class CmdUtils {
    /**
     * Splits and parses the user input into tokens based on certain delimiters.
     *
     * @param p_UserInput Raw user input.
     * @return A list containing tokens.
     */
    public static List<String> getUserInputParts(String p_UserInput) {
        List<String> l_Tokens = new ArrayList<>();
        if (p_UserInput.contains(WarzoneConstants.HYPHEN)) {
            String[] l_HyphenSplit = p_UserInput.split(WarzoneConstants.HYPHEN);
            for (String l_Part : l_HyphenSplit) {
                l_Tokens.add(l_Part.trim());
            }
            return l_Tokens;
        } else {
            String[] l_SpaceSplit = p_UserInput.split(WarzoneConstants.SPACE_REGEX);
            l_Tokens.addAll(Arrays.asList(l_SpaceSplit));
            return l_Tokens;
        }

    }
}
