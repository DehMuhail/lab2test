

import com.laba2.DOMParser;
import com.laba2.FlowerComparator;
import generated.LightingType;
import generated.MultiplyingType;
import generated.Orangery;
import generated.SoilType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DOMParserTest {
    private DOMParser domParser;

    @BeforeEach
    void setUp() {
        domParser = new DOMParser();
    }

    @Test
    void testParseXML_ValidXML_ReturnsFlowerList() {
        // Arrange
        String xmlFilePath = "src/test/resources/test_orangery.xml"; // Ensure this file exists
        // Act
        List<Orangery.Flower> flowers = domParser.parseXML(xmlFilePath);

        // Assert
        assertNotNull(flowers);
        assertEquals(3, flowers.size());

        // Validate first flower
        Collections.sort(flowers, new FlowerComparator());
        Orangery.Flower firstFlower = flowers.get(1);
        assertEquals("P001", firstFlower.getId());
        assertEquals("Rose", firstFlower.getName());
        assertEquals(SoilType.SOIL, firstFlower.getSoil());
        assertEquals("Europe".toUpperCase(), firstFlower.getOrigin());
        assertEquals("Green".toUpperCase(), firstFlower.getVisualParameters().getStemColor());
        assertEquals("Dark_Green".toUpperCase(), firstFlower.getVisualParameters().getLeafColor());
        assertEquals(new BigDecimal("1.2"), firstFlower.getVisualParameters().getAverageSize());
        assertEquals(new BigDecimal("20.5"), firstFlower.getGrowingTips().getTemperature());
        assertEquals(LightingType.LIGHT_LOVING, firstFlower.getGrowingTips().getLighting());
        assertEquals(1, firstFlower.getGrowingTips().getWatering());
        assertEquals(MultiplyingType.BY_CUTTINGS, firstFlower.getMultiplying());
    }

    @Test
    void testParseXML_InvalidXML_ReturnsEmptyList() {
        // Arrange
        String xmlFilePath = "src/test/resources/invalid_orangery.xml"; // Ensure this file exists and is invalid

        // Act
        List<Orangery.Flower> flowers = domParser.parseXML(xmlFilePath);

        // Assert
        assertNotNull(flowers);
        assertTrue(flowers.isEmpty());
    }


}
