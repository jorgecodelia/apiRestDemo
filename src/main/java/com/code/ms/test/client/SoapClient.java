package com.code.ms.test.client;

import java.util.concurrent.CompletableFuture;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client for testing a mock soap service.
 *
 * @author Jorge Codelia.
 */
@FeignClient(name = "ms-test-client-soap", url = "${endpoint.soap-server")
public interface SoapClient {

  @PostMapping(
      value = "/CountryInfoService.wso",
      consumes = MediaType.APPLICATION_XML_VALUE,
      produces = MediaType.APPLICATION_XML_VALUE)
  CompletableFuture<ResponseEntity<String>> getCountryInfo(@RequestBody String xmlStringBody);
}
