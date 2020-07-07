package com.example.securityflaws.xxe;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author Alexandre Grison
 */
@Service
public class XxeService {
    public DocumentBuilder documentBuilder() {
        return documentBuilder(HashMap.empty(), HashMap.empty());
    }

    public DocumentBuilder documentBuilder(Map<String, Boolean> features) {
        return documentBuilder(features, HashMap.empty());
    }

    @SneakyThrows
    public DocumentBuilder documentBuilder(Map<String, Boolean> features, Map<String, Object> attributes) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        features.forEach(e -> Try.run(() -> dbf.setFeature(e._1, e._2)));
        attributes.forEach(e -> Try.run(() -> dbf.setAttribute(e._1, e._2)));
        return dbf.newDocumentBuilder();
    }
}
