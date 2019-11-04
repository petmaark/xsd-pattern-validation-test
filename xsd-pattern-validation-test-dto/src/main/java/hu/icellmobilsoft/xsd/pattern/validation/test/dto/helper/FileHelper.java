package hu.icellmobilsoft.xsd.pattern.validation.test.dto.helper;

import java.net.URL;

/**
 * Helper class for file handling
 *
 * @author mark.petrenyi
 */
public class FileHelper {

    /**
     * Obtain URL for xsd
     * @return
     */
    public static URL readXsd() {
        return FileHelper.class.getClassLoader().getResource("xsd/hu/icellmobilsoft/pattern/validation/test/dto/test.xsd");
    }

}
