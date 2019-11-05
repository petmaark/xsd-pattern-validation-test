package hu.icellmobilsoft.xsd.pattern.validation.test.application;

import static java.text.MessageFormat.format;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import hu.icellmobilsoft.xsd.pattern.validation.test.dto.NotBlankElement;

/**
 * @author mark.petrenyi
 * @since 2.5.0
 */
public class MainApplication {

    private static final Logger log = Logger.getLogger(MainApplication.class.getName());

    private static final String UNSAFE_XSD = "xsd/hu/icellmobilsoft/pattern/validation/test/dto/unsafe.xsd";
    private static final String WORKAROUND_XSD = "xsd/hu/icellmobilsoft/pattern/validation/test/dto/workaround.xsd";

    private static final String VALID_XML = "valid.xml";
    private static final String INVALID_XML = "invalid.xml";
    private static final String LONG_STRING_XML = "long_string.xml";

    public static void main(String[] args) {
        try {
            List<UnmarshallStatistics> statList = new ArrayList<>();

            //using unsafe.xsd for validating
            //unmarshall valid.xml
            statList.add(unmarshallXml(VALID_XML, UNSAFE_XSD));
            //unmarshall invalid.xml
            statList.add(unmarshallXml(INVALID_XML, UNSAFE_XSD));
            //unmarshall long_string.xml
            statList.add(unmarshallXml(LONG_STRING_XML, UNSAFE_XSD));

            //using workaround.xsd for validating
            //unmarshall valid.xml
            statList.add(unmarshallXml(VALID_XML, WORKAROUND_XSD));
            //unmarshall invalid.xml
            statList.add(unmarshallXml(INVALID_XML, WORKAROUND_XSD));
            //unmarshall long_string.xml
            statList.add(unmarshallXml(LONG_STRING_XML, WORKAROUND_XSD));

            //logging statistics (unmarshall-time, xml, xsd, isValid)
            logStatistics(statList);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e);
        }
    }

    private static UnmarshallStatistics unmarshallXml(String xmlInput, String xsdPath) throws JAXBException, SAXException {
        UnmarshallStatistics stats = new UnmarshallStatistics(xmlInput, xsdPath);
        //Creating unmarshaller
        JAXBContext jaxbContext = JAXBContext.newInstance(NotBlankElement.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //Obtaining and setting xsd schema for validation
        Schema schema = sf.newSchema(MainApplication.class.getClassLoader().getResource(xsdPath));
        unmarshaller.setSchema(schema);

        //reading xml
        InputStream xmlStream = MainApplication.class.getClassLoader().getResourceAsStream(xmlInput);
        log.info(format("Unmarshalling xml:[{0}], xsd:[{1}]...", xmlInput, xsdPath));
        long start = System.currentTimeMillis();
        try {
            //unmarshall (since schema is set on the unmarshaller therefore xml is validated)
            NotBlankElement unmarshalled = (NotBlankElement) unmarshaller.unmarshal(xmlStream);
            stats.setValid(true);
            log.info(format("Unmarshall finished, xml:[{0}], xsd:[{1}], unmarshalled:[{2}]", xmlInput, xsdPath, unmarshalled));
        } catch (Exception e) {
            //Unmarshall failed, possibly because of validation error
            stats.setValid(false);
            log.log(Level.SEVERE, format("Unmarshall failed, xml:[{0}], xsd:[{1}]...", xmlInput, xsdPath), e);
        } finally {
            //calculate time needed for validation
            long end = System.currentTimeMillis();
            long timeElapsed = end - start;
            log.info(format("Time elapsed: {0} ms", timeElapsed));
            stats.setTimeElapsed(timeElapsed);
        }
        return stats;
    }

    private static void logStatistics(List<UnmarshallStatistics> statList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Statistics:").append("\n");
        for (UnmarshallStatistics stat : statList) {
            sb.append(stat).append("\n");
        }
        log.info(sb.toString());
    }

    private static class UnmarshallStatistics {

        private long timeElapsed;
        private final String xmlInput;
        private final String xsdPath;
        private boolean valid;

        public UnmarshallStatistics(String xmlInput, String xsdPath) {
            this.xmlInput = xmlInput;
            this.xsdPath = xsdPath;
        }

        public String getXmlInput() {
            return xmlInput;
        }

        public String getXsdPath() {
            return xsdPath;
        }

        public long getTimeElapsed() {
            return timeElapsed;
        }

        public void setTimeElapsed(long timeElapsed) {
            this.timeElapsed = timeElapsed;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        @Override
        public String toString() {
            return "UnmarshallStatistics{" +
                   "timeElapsed=" + timeElapsed +
                   ", xmlInput='" + xmlInput + '\'' +
                   ", xsdPath='" + xsdPath + '\'' +
                   ", valid=" + valid +
                   '}';
        }
    }

}
