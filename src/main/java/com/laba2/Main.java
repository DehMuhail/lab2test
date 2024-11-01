package com.laba2;

import generated.Orangery;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String xmlFilePath = "src/main/resources/orangery.xml"; // Шлях до вашого XML файлу

        // Використання SAX Parser
        SAXParser saxParser = new SAXParser();
        List<Orangery.Flower> flowersSAX = saxParser.parseXML(xmlFilePath);
        // Використання DOM Parser
        DOMParser domParser = new DOMParser();
        List<Orangery.Flower> flowersDOM = domParser.parseXML(xmlFilePath);

        // Використання StAX Parser
        StAXParser staxParser = new StAXParser();
        List<Orangery.Flower> flowersStAX = staxParser.parseXML(xmlFilePath);

        // Виведення результатів
        System.out.println("Квіти (SAX): " + flowersSAX.get(0).getName());
        System.out.println("Квіти (DOM): " + flowersDOM.get(0).getSoil());
        System.out.println("Квіти (StAX): " + flowersStAX.get(0).getName());
    }
}
