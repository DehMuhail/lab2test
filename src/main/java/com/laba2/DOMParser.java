    package com.laba2;

    import generated.*;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.w3c.dom.Document;
    import org.w3c.dom.Element;
    import org.w3c.dom.NodeList;

    import javax.xml.parsers.DocumentBuilder;
    import javax.xml.parsers.DocumentBuilderFactory;
    import java.io.File;
    import java.math.BigDecimal;
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;

    public class DOMParser {
        private static final Logger logger = LoggerFactory.getLogger(DOMParser.class);
        private List<Orangery.Flower> flowers = new ArrayList<>();

        public List<Orangery.Flower> parseXML(String xmlFilePath) {
            try {
                logger.info("DOM парсинг розпочався");
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new File(xmlFilePath));
                document.getDocumentElement().normalize();

                NodeList flowerList = document.getElementsByTagName("Flower");
                for (int i = 0; i < flowerList.getLength(); i++) {
                    Element flowerElement = (Element) flowerList.item(i);
                    Orangery.Flower flower = new Orangery.Flower();
                    flower.setId(flowerElement.getAttribute("id"));
                    flower.setName(flowerElement.getElementsByTagName("Name").item(0).getTextContent().trim());
                    flower.setSoil(SoilType.valueOf(flowerElement.getElementsByTagName("Soil").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_")));
                    flower.setOrigin(flowerElement.getElementsByTagName("Origin").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_"));

                    // Отримуємо VisualParameters
                    Element visualParametersElement = (Element) flowerElement.getElementsByTagName("VisualParameters").item(0);
                    VisualParametersType visualParameters = new VisualParametersType();
                    visualParameters.setStemColor(visualParametersElement.getElementsByTagName("StemColor").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_"));
                    visualParameters.setLeafColor(visualParametersElement.getElementsByTagName("LeafColor").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_"));
                    visualParameters.setAverageSize(new BigDecimal(visualParametersElement.getElementsByTagName("AverageSize").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_")));
                    flower.setVisualParameters(visualParameters);

                    // Отримуємо GrowingTips
                    Element growingTipsElement = (Element) flowerElement.getElementsByTagName("GrowingTips").item(0);
                    GrowingTipsType growingTips = new GrowingTipsType();
                    growingTips.setTemperature(new BigDecimal(growingTipsElement.getElementsByTagName("Temperature").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_")));
                    growingTips.setLighting(LightingType.valueOf(growingTipsElement.getElementsByTagName("Lighting").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_")));
                    growingTips.setWatering(Integer.parseInt(growingTipsElement.getElementsByTagName("Watering").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_")));
                    flower.setGrowingTips(growingTips);

                    flower.setMultiplying(MultiplyingType.valueOf(flowerElement.getElementsByTagName("Multiplying").item(0).getTextContent().trim().toUpperCase().replace("-", "_").replace(" ", "_")));

                    flowers.add(flower);
                }
                logger.info("DOM парсинг закінчився успішно");
            } catch (Exception e) {
                logger.error("DOM парсинг закінчився з помилкою: {}", e.getMessage());
                e.printStackTrace();
            }
            Collections.sort(flowers, new FlowerComparator()); // Сортуємо квіти
            return flowers;
        }
    }
