import com.laba2.XMLValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XMLValidatorTest {
    XMLValidator validator = new XMLValidator();

    @Test
    void testValidXML() {
        String xsdPath = "xsd/orangery.xsd";
        String xmlPath = "orangery.xml";

        assertTrue(validator.validateXMLSchema(xsdPath, xmlPath));
    }

    @Test
    void testInvalidXML() {
        String xsdPath = "xsd/orangery.xsd";
        String xmlPath = "invalid_orangery.xml";

        assertFalse(validator.validateXMLSchema(xsdPath, xmlPath));
    }
}