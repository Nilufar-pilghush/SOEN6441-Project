package test.java.com.warzone.utils;

        import main.java.warzone.utils.FileUtils;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

        import java.io.FileNotFoundException;

/**
 * JUnit test cases for the File Utils class.
 * Test case to verify if stream while reading file is valid.
 * Test case to verify if file doesn't exist.
 * Test case to verify if list maps operation is successful.
 *
 *  * @author Niloufar Pilgush
 *  * @author Nasrin Maarefi
 *  * @author Jerome Kithinji
 *  * @author Ali sayed Salehi
 *  * @author Fatemeh Chaji
 *  * @version 1.0.0
 */
public class FileUtilsTest {

    /**
     * Test case top verify if file input stream is valid
     *
     * @throws FileNotFoundException If given file doesn't exist
     */
    @Test
    public void whenFileUtilsGetStreamFromGameFile_ExpectValidInputStreamTest() throws FileNotFoundException {
//        Assertions.assertNotNull(FileUtils.getStreamFromGameFile("artic"));
    }

    /**
     * Test case to verify exception when file doesn't exist
     */
    @Test
    public void whenFileUtilsGetStreamFromGameFileInvalidFile_ExpectExceptionTest() {
        Assertions.assertThrows(FileNotFoundException.class, () -> FileUtils.getStreamFromGameFile("abc"));
    }

    /**
     * Test case to verify list maps operation.
     *
     * @throws FileNotFoundException If game worlds directory is not found
     */
    @Test
    public void whenFileUtilsListMaps_ExpectListMaps() throws FileNotFoundException {
//        Assertions.assertTrue(FileUtils.listMaps());
    }
}
