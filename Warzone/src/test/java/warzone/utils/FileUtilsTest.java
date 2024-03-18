package test.java.warzone.utils;

import main.java.warzone.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Provides JUnit test cases for the {@link FileUtils} class to validate its file operation capabilities.
 * It includes tests for checking the validity of file streams, the existence of files, and the success of listing map files. * <p>
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class FileUtilsTest {
    /**
     * Tests that a FileNotFoundException is thrown when attempting to get a stream from a non-existent file.
     * This verifies the file handling mechanism for nonexistent files.
     */
    @Test
    public void testNonExistentFileThrowsException() {
        Assertions.assertThrows(FileNotFoundException.class,
                () -> FileUtils.getStreamFromGameFile("nonexistentfile.txt"),
                "Expect FileNotFoundException for non-existent files.");
    }

    /**
     * Tests the successful completion of the listMaps operation.
     * This verifies that the operation can successfully list the maps as expected.
     */
    @Test
    public void testListMapsSuccess() throws FileNotFoundException {
        Assertions.assertTrue(FileUtils.listMaps(), "Expect listMaps to return true indicating success.");
    }
}

