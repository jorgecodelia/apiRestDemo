package com.code.ms.test.controller;

import com.code.ms.test.business.TestService;
import com.code.ms.test.client.dto.AlbumResponse;
import com.code.ms.test.commons.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final TestService testService;
  private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

  /**
   * Method to get Albums
   *
   * @return UserAlias information (ffNumber).
   */
  @GetMapping(value = Constants.GET_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AlbumResponse> getAlbums() {
    LOGGER.info("getAlbums INIT");
    return ResponseEntity.ok(testService.getAlbums());
  }
}
