

import com.laba2.StAXParser;
import generated.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StAXParserTest {
    private StAXParser stAXParser;
    private Logger mockLogger;

    @BeforeEach
    void setUp() {
        stAXParser = new StAXParser();
        mockLogger = Mockito.mock(Logger.class);
        // Set the mocked logger to the StAXParser if needed, but since there's no setter, we can use Mockito for verification directly.
    }

    @Test
    void testParseXML_ValidXML_ReturnsFlowerList() {
        // Arrange
        String xmlFilePath = "src/test/resources/test_orangery.xml"; // Ensure this file exists

        // Act
        List<Orangery.Flower> flowers = stAXParser.parseXML(xmlFilePath);

        // Assert
        assertNotNull(flowers);
        assertEquals(3, flowers.size());

        // Validate first flower
        Orangery.Flower firstFlower = flowers.get(1);
        assertEquals("P001", firstFlower.getId());
        assertEquals("Rose", firstFlower.getName());
        assertEquals(SoilType.SOIL, firstFlower.getSoil());
        assertEquals("Europe", firstFlower.getOrigin());
        assertEquals("Green", firstFlower.getVisualParameters().getStemColor());
        assertEquals("Dark Green", firstFlower.getVisualParameters().getLeafColor());
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
        List<Orangery.Flower> flowers = stAXParser.parseXML(xmlFilePath);

        // Assert
        assertNotNull(flowers);
        assertTrue(flowers.isEmpty());
    }


}
