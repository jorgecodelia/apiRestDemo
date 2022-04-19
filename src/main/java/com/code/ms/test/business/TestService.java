package com.code.ms.test.business;

import com.code.ms.test.client.RestClient;
import com.code.ms.test.client.SoapClient;
import com.code.ms.test.client.dto.AlbumResponse;
import com.code.ms.test.client.dto.CountryInfoResponse;
import com.code.ms.test.commons.ServiceException;
import com.code.ms.test.commons.XmlUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
@RequiredArgsConstructor
public class TestService {

  private final RestClient restClient;
  private final SoapClient soapClient;
  private final XmlUtils xmlUtils;
  private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);

  public static final String GET_TASK_EXECUTOR = "getTaskExecutor";

  /**
   * get Albums
   *
   * @return {@link AlbumResponse} .
   */
  public AlbumResponse getAlbums() {
    try {
      LOGGER.info("Init getAlbums");
      return restClient.getAlbums();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("TestService-Error", e.getMessage(), e);
    }
  }

  /**
   * get country info
   *
   * @return {@link CountryInfoResponse} .
   */
  @Async(GET_TASK_EXECUTOR)
  public CountryInfoResponse getCountryInfo() {
    try {
      LOGGER.info("Init getCountryInfo");
      String payload = xmlUtils.prepareSiteMinderRequest();
      CompletableFuture<ResponseEntity<String>> response = soapClient.getCountryInfo(payload);
      ResponseEntity<String> soapXmlResponse;
      if (response.isDone()) {
        soapXmlResponse = response.get();
        if (soapXmlResponse.getStatusCode() == HttpStatus.OK) {
          return mapToResponse(soapXmlResponse.getBody());
        }
      }
      return null;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("TestService-Error", e.getMessage(), e);
    }
  }

  private CountryInfoResponse mapToResponse(String xmlResponse) {
    Document doc = xmlUtils.parseToXMLDocument(xmlResponse);
    NodeList list =
        doc.getElementsByTagName("ListOfContinentsByNameResponse").item(0).getChildNodes();

    List<Node> nodeStream =
        IntStream.range(0, list.getLength()).mapToObj(list::item).collect(Collectors.toList());

    return null;
  }
}
