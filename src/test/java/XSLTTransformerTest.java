

import com.laba2.StAXParser;
import com.laba2.XSLTTransformer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XSLTTransformerTest {
    private Logger mockLogger = Mockito.mock(Logger.class);

    private static final String XML_FILE_PATH = "src/main/resources/orangery.xml";
    private static final String XSLT_FILE_PATH = "src/main/resources/orangery_transform.xslt";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/transformed_orangery.xml";



    @AfterEach
    public void tearDown() {
        // Clean up the output file after each test
        File outputFile = new File(OUTPUT_FILE_PATH);
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }

    @Test
    public void testXSLTTransformation() {
        // Execute the transformation
        XSLTTransformer.main(new String[]{});

        // Verify the output file is created
        File outputFile = new File(OUTPUT_FILE_PATH);
        assertTrue(outputFile.exists(), "Output file should be created after transformation");
    }

    @Test
    public void testOutputFileContent() throws Exception {
        // Execute the transformation
        XSLTTransformer.main(new String[]{});

        // Verify the output file is created
        File outputFile = new File(OUTPUT_FILE_PATH);
        assertTrue(outputFile.exists(), "Output file should be created after transformation");

        // Read the content of the output file
        String content = new String(Files.readAllBytes(outputFile.toPath()));

        // You can add further assertions based on expected content.
        assertTrue(content.contains("<Origin>Europe</Origin>"), "Output should contain expected elements");
    }
}
