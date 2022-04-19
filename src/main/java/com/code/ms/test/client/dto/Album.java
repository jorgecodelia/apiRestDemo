package com.code.ms.test.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Album {

  private String id;
  private String userId;
  private String title;
}
