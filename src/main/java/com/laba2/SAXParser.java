package com.laba2;

import generated.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SAXParser extends DefaultHandler {

    private static final Logger logger = LoggerFactory.getLogger(SAXParser.class);
    private List<Orangery.Flower> flowers = new ArrayList<>();
    private Orangery.Flower flower;
    private VisualParametersType visualParameters;
    private GrowingTipsType growingTips;
    private StringBuilder data;

    public List<Orangery.Flower> parseXML(String xmlFilePath) {
        try {
            logger.info("SAX парсинг розпочався");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(xmlFilePath), this);
            logger.info("SAX парсинг закінчився успішно");
        } catch (Exception e) {
            logger.error("SAX парсинг закінчився з помилкою: {}", e.getMessage());
            e.printStackTrace();
        }
        Collections.sort(flowers, new FlowerComparator());
        return flowers;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data = new StringBuilder();
        if (qName.equalsIgnoreCase("Flower")) {
            flower = new Orangery.Flower();
            flower.setId(attributes.getValue("id"));
        } else if (qName.equalsIgnoreCase("VisualParameters")) {
            visualParameters = new VisualParametersType();
        } else if (qName.equalsIgnoreCase("GrowingTips")) {
            growingTips = new GrowingTipsType();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Name")) {
            flower.setName(data.toString().trim().replace("-", "_").replace(" ", "_"));
        } else if (qName.equalsIgnoreCase("Soil")) {
            flower.setSoil(SoilType.valueOf(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_"))); // Ensure proper enum name
        } else if (qName.equalsIgnoreCase("Origin")) {
            flower.setOrigin(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_"));
        } else if (qName.equalsIgnoreCase("StemColor")) {
            visualParameters.setStemColor(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_"));
        } else if (qName.equalsIgnoreCase("LeafColor")) {
            visualParameters.setLeafColor(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_"));
        } else if (qName.equalsIgnoreCase("AverageSize")) {
            visualParameters.setAverageSize(new BigDecimal(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_")));
        } else if (qName.equalsIgnoreCase("Temperature")) {
            growingTips.setTemperature(new BigDecimal(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_")));
        } else if (qName.equalsIgnoreCase("Lighting")) {
            growingTips.setLighting(LightingType.valueOf(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_"))); // Ensure proper enum name
        } else if (qName.equalsIgnoreCase("Watering")) {
            growingTips.setWatering(Integer.parseInt(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_")));
        } else if (qName.equalsIgnoreCase("Multiplying")) {
            flower.setMultiplying(MultiplyingType.valueOf(data.toString().trim().toUpperCase().replace("-", "_").replace(" ", "_"))); // Ensure proper enum name
        } else if (qName.equalsIgnoreCase("VisualParameters")) {
            flower.setVisualParameters(visualParameters);
        } else if (qName.equalsIgnoreCase("GrowingTips")) {
            flower.setGrowingTips(growingTips);
        } else if (qName.equalsIgnoreCase("Flower")) {
            flowers.add(flower);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
