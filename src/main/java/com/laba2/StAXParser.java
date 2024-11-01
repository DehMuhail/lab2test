package com.laba2;

import generated.*;
import generated.VisualParametersType;
import generated.GrowingTipsType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.EndElement;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StAXParser {
    private static final Logger logger = LoggerFactory.getLogger(StAXParser.class);
    private List<Orangery.Flower> flowers = new ArrayList<>();

    public List<Orangery.Flower> parseXML(String xmlFilePath) {
        try {
            logger.info("StAX парсинг розпочався");
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream(xmlFilePath));
            Orangery.Flower flower = null;
            VisualParametersType visualParameters = null;
            GrowingTipsType growingTips = null;
            StringBuilder data = new StringBuilder();

            while (eventReader.hasNext()) {

                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("Flower")) {
                        flower = new Orangery.Flower();
                        flower.setId(startElement.getAttributeByName(new QName("id")).getValue());
                    } else if (qName.equalsIgnoreCase("VisualParameters")) {
                        visualParameters = new VisualParametersType();
                    } else if (qName.equalsIgnoreCase("GrowingTips")) {
                        growingTips = new GrowingTipsType();
                    }
                } else if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String qName = endElement.getName().getLocalPart();
                    System.out.println(flower);
                    if (qName.equalsIgnoreCase("Name")) {
                        flower.setName(data.toString().trim());
                    } else if (qName.equalsIgnoreCase("Soil")) {
                        flower.setSoil(SoilType.fromValue(data.toString().trim()));
                    } else if (qName.equalsIgnoreCase("Origin")) {
                        flower.setOrigin(data.toString().trim());
                    } else if (qName.equalsIgnoreCase("StemColor")) {
                        visualParameters.setStemColor(data.toString().trim());
                    } else if (qName.equalsIgnoreCase("LeafColor")) {
                        visualParameters.setLeafColor(data.toString().trim());
                    }else if (qName.equalsIgnoreCase("AverageSize")) {
                        String averageSize = data.toString().trim();
                        if (!averageSize.isEmpty()) {
                            visualParameters.setAverageSize(new BigDecimal(averageSize)); // Trim whitespace
                        } else {
                            logger.warn("AverageSize is empty or invalid");
                        }
                    } else if (qName.equalsIgnoreCase("Temperature")) {
                        String temperature = data.toString().trim();
                        if (!temperature.isEmpty()) {
                            growingTips.setTemperature(new BigDecimal(temperature)); // Trim whitespace
                        } else {
                            logger.warn("Temperature is empty or invalid");
                        }
                    } else if (qName.equalsIgnoreCase("Lighting")) {
                        growingTips.setLighting(LightingType.fromValue(data.toString().trim()));
                    } else if (qName.equalsIgnoreCase("Watering")) {
                        growingTips.setWatering(Integer.parseInt(data.toString().trim()));
                    } else if (qName.equalsIgnoreCase("VisualParameters")) {
                        flower.setVisualParameters(visualParameters);
                    } else if (qName.equalsIgnoreCase("GrowingTips")) {
                        flower.setGrowingTips(growingTips);
                    } else if (qName.equalsIgnoreCase("Multiplying")) {
                        flower.setMultiplying(MultiplyingType.fromValue(data.toString().trim()));
                    } else if (qName.equalsIgnoreCase("Flower")) {
                        flowers.add(flower);
                    }
                    data.setLength(0); // Очищення StringBuilder
                } else if (event.isCharacters()) {
                    data.append(event.asCharacters().getData());
                }
            }
            logger.info("StAX парсинг закінчився успішно");
        } catch (Exception e) {
            logger.error("StAX парсинг закінчився з помилкою: {}", e.getMessage());
            e.printStackTrace();
        }
        Collections.sort(flowers, new FlowerComparator()); // Сортуємо квіти
        return flowers;
    }
}
