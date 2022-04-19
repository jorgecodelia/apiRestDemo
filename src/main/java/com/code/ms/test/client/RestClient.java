package com.code.ms.test.client;

import com.code.ms.test.client.dto.AlbumResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign client for testing a mock api rest.
 *
 * @author Jorge Codelia.
 */
@FeignClient(name = "ms-test-client-rest", url = "${endpoint.mock-server")
public interface RestClient {

  @GetMapping(
      value = "/albums",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  AlbumResponse getAlbums();
}
