package com.code.ms.test.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Continent {
  private String code;
  private String name;
}
