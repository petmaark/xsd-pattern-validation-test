package hu.icellmobilsoft.xsd.pattern.validation.test.service.rest;

import java.io.InputStream;
import java.text.MessageFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import hu.icellmobilsoft.pattern.validation.test.dto.NotBlankElement;
import hu.icellmobilsoft.xsd.pattern.validation.test.dto.helper.FileHelper;

/**
 * @author mark.petrenyi
 * @since 2.5.0
 */
public class MainApplication {

    public static void main(String[] args) {
        try {
            unmarshallXml("test.xml");
            unmarshallXml("aaa.xml");
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    private static void unmarshallXml(String xmlInput) throws JAXBException, SAXException {
        InputStream xmlStream = MainApplication.class.getClassLoader().getResourceAsStream(xmlInput);
        JAXBContext jaxbContext = JAXBContext.newInstance(NotBlankElement.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(FileHelper.readXsd());
        unmarshaller.setSchema(schema);
        System.out.println("Unmarshalling...");
        long start = System.currentTimeMillis();
        try {
            NotBlankElement unmarshalled = (NotBlankElement) unmarshaller.unmarshal(xmlStream);
            System.out.println("Unmarshall finished.");
            System.out.println(unmarshalled);
        } catch (Exception e) {
            System.out.println("Unmarshall failed.");
            e.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println(MessageFormat.format("Time elapsed: {0} ms", end - start));
        }
    }

}
