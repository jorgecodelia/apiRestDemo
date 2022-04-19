package com.code.ms.test.commons;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Utility class for XML parsing.
 *
 * @author Jorge Codelia.
 */
@Component
@RequiredArgsConstructor
public class XmlUtils {

  private final PebbleEngine pebbleEngine;
  private DocumentBuilderFactory documentBuilderFactory;
  private static final Logger LOGGER = Logger.getLogger(XmlUtils.class.getName());

  /**
   * Method to parse a string into an xml document.
   *
   * @param xmlString xmlString.
   * @return Document xml document.
   */
  public Document parseToXMLDocument(String xmlString) {
    try {
      documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilderFactory.setFeature(Constants.DISALLOW_DOC_TYPE_DEC, true);
      documentBuilderFactory.setFeature(Constants.EXTERNAL_GENERAL_ENTITIES, false);
      documentBuilderFactory.setFeature(Constants.EXTERNAL_PARAMETER_ENTITIES, false);
      documentBuilderFactory.setFeature(Constants.LOAD_EXTERNAL_DTD, false);
      documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      documentBuilderFactory.setXIncludeAware(false);
      documentBuilderFactory.setExpandEntityReferences(false);
      DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
      return builder.parse(new InputSource(new StringReader(xmlString)));
    } catch (Exception e) {
      LOGGER.severe("parseToXMLDocument");
      LOGGER.severe("convertStringToXMLDocument");
      LOGGER.severe(e.getMessage());
      throw new ServiceException(e.getMessage(), "XML Parse error", e);
    }
  }

  /**
   * Method for create the xml payload request.
   *
   * @return String xml document.
   */
  public String prepareSiteMinderRequest() {
    Writer writer = new StringWriter();
    try {
      PebbleTemplate pebbleTemplate = pebbleEngine.getTemplate("CountryInfoTemplate");
      pebbleTemplate.evaluate(writer);
    } catch (Exception e) {
      LOGGER.severe("prepareSiteMinderRequest");
      LOGGER.severe("Error creating soap xml");
      LOGGER.severe(e.getMessage());
      throw new ServiceException(e.getMessage(), "XML Parse error", e);
    }
    return writer.toString();
  }
}
