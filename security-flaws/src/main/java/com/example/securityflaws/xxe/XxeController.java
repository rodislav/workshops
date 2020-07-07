package com.example.securityflaws.xxe;

import io.vavr.collection.HashMap;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;

/**
 * Disabling DTD is the primary defense you have regarding XXE attacks.
 * <p>
 * There are specific ways for preventing these kind of attacks depending if
 * you're using one of:
 * - DocumentBuilderFactory (JAXP)
 * - SAXParserFactory (DOM4J)
 * - XMLInputFactory (STAX)
 * - TransformerFactory (XSLT)
 * - Validator & SchemaFactory (XML Schema)
 * - SAXTransformerFactory (SAX)
 * - JAXB is safe by default since Java 8
 * - Spring MVC is safe by default since Spring 4.0.2+
 * @author Alexandre Grison
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/flaws/xxe")
public class XxeController {
    // Modify the extract.xml file to change the path of the local file
    // which is supposed to be extracted inside the HTTP response
    @Value("classpath:/extract.xml")
    Resource extractResource;
    @Value("classpath:/bomb.xml")
    Resource bombResource;
    final XxeService service;

    @SneakyThrows
    @GetMapping(value = "/extract", produces = "text/plain")
    public String extract() {
        Document doc = service.documentBuilder().parse(extractResource.getFile());
        return doc.getDocumentElement().getTextContent();
    }

    @SneakyThrows
    @GetMapping(value = "/extract/safe", produces = "text/plain")
    public String extractSafe() {
        DocumentBuilder db = service.documentBuilder(HashMap.of(
                // disable DTDs
                "http://apache.org/xml/features/disallow-doctype-decl", true,
                "http://apache.org/xml/features/nonvalidating/load-external-dtd", true)
        );
        return Try.of(() ->
                db.parse(extractResource.getFile()).getDocumentElement().getTextContent())
                .onFailure(e -> log.error("Could not extract from XML document", e))
                .getOrElse("Could not extract from XML document, but still safe.");
    }

    @SneakyThrows
    @GetMapping(value = "/xml-bomb", produces = "text/plain")
    public String bomb() {
        Document doc = service.documentBuilder().parse(bombResource.getFile());
        return doc.getDocumentElement().getTextContent();
    }

    @SneakyThrows
    @GetMapping(value = "/xml-bomb/safe", produces = "text/plain")
    public String bombSafe() {
        DocumentBuilder db = service.documentBuilder(
                HashMap.empty(/* Disabling DTD would also work */),
                HashMap.of(
                        // Set expension limit to 100 just to be sure
                        "http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit", "100")
        );
        return Try.of(() ->
                db.parse(bombResource.getFile()).getDocumentElement().getTextContent())
                .onFailure(e -> log.error("Could not extract from XML document", e))
                .getOrElse("Could not extract from XML document, but still safe.");
    }
}
