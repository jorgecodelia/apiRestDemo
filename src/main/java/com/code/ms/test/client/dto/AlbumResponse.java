package com.code.ms.test.client.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AlbumResponse {
  private List<Album> albumList;
}
