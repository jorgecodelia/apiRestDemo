package com.code.ms.test.commons;

public class Constants {

  // Api tags
  public static final String GENERAL_ENDPOINT = "/ms-test/v1";
  public static final String GET_ENDPOINT = "/albums";

  // preventive XXE attack constants
  public static final String DISALLOW_DOC_TYPE_DEC =
      "http://apache.org/xml/features/disallow-doctype-decl";
  public static final String EXTERNAL_GENERAL_ENTITIES =
      "http://xml.org/sax/features/external-general-entities";
  public static final String EXTERNAL_PARAMETER_ENTITIES =
      "http://xml.org/sax/features/external-parameter-entities";
  public static final String LOAD_EXTERNAL_DTD =
      "http://apache.org/xml/features/nonvalidating/load-external-dtd";
}
