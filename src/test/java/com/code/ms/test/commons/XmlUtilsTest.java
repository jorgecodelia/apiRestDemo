package com.code.ms.test.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@ExtendWith(MockitoExtension.class)
public class XmlUtilsTest {

  @InjectMocks private XmlUtils xmlUtils;
  @Mock private PebbleEngine pebbleEngine;
  @Mock private DocumentBuilderFactory documentBuilderFactory;

  private static final String XML_REQUEST =
      "<soap:Envelope \txmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Header></soap:Header><soap:Body><aut:login \txmlns:aut=\"http://www.ca.com/siteminder/authaz/2010/04/15/authaz.xsd\"><identityContext><userName>16345789</userName><password>password123.</password></identityContext><appId>AutenticarUsuario</appId><resource>AutenticarUsuario_ACESSO</resource><action>GET</action></aut:login></soap:Body></soap:Envelope>";
  private static final String XML_RESPONSE =
      "<?xml version='1.0' encoding='ISO-8859-1'?><S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\"><S:Body><ns2:loginResponse xmlns:ns2=\"http://www.ca.com/siteminder/authaz/2010/04/15/authaz.xsd\"><return><message>Authentication Successful</message><resultCode>LOGIN_SUCCESS</resultCode><sessionToken>QGons4fLMKy7TK/h9o0338J8txW5tQb36hy8WzfV2SHBD2rr7Gu2zgRspd8bToiFQjBPwzkOthOAFYTI1bKj9KtqDsmwlKZSpouCLsroPpS2VsjH9qxUtpKXgp6GrMHhxTgJ7GbInYgsLM0wRwBY02lcBADwEwUTZDBfm0ok5xbCsJpEVBIvltcLDJwR9+fvnvN98bWU1Hf57chHWLDKBpyoDXVMMZb92ymJ8n1tTlRX8BAP526ltI/5LH1mWD+ID93rGLmZkhebafdSeZB3Ur7iSExcRL95BcqBhmGncE0K6usYlSvR+g9maG4PqneAn6NvCdEimeXKOeTyOvzfcjdqRsVMzSnUMj0dBFPJBogEn63edMTgZpRnaN5W46Mi7/LSPON3Y8bwm2MWRti2watrjYhy6gYJBBhs4/2ggaRWkIoS6vcZfQNBYj0dWmXOs7LLR+jPrREapgzTzH36kjS1dQF9DSdz4FaOKTQ086Nl4522lt8rWbcwjrkv2tNxHDYbDhMshDCnvPdLMm6rFQlBR95TC5ml6H3XRZNL2QbtGofSD//wZtQqeDNTXwtN99o8Yvu+JDAkGFkb8GpUj7OhNmjAXXWagk3u1UGy2B96bU6FsR2k0CkLCBb++e3eDpv2jE69FDhRks2V5aaRUhexwAgTJBDSmW5jRui1vMVg+TUSMRQhR52/988ztSz7Yl/I5AJwuZGnkMsMSOmAh0Tw+g3h41z2M0iHMI8hk+y5NwDzK7tCqbOrNv/3eU2/NXYXTJeq/koPGUSiuwndq+Tmm6uINhsIKoDsQ4/VSn20bs5ubtIZbw3t1wv96G2jpBHp9hVZo4RNCvgSv5y9m2hCg8a9b+OxT+44YRy6t5wrMqfjt9SfXrbZo0RCt+FA7Kadt6CVurS75NkZ33Pnd1AkVmxTzrJUCVO4a5Qw8Rlgm/2jh5oubGW3+Rn33v78/uGMxHPGc/vhmzi8PKQvTxP7ga8hEQJUpufqYhp8mbb7R0VFnpMp2KgVbf0PNCcW4vHuzo7Bc4IzFqqHaMRtH5Vxs+wd3ulfZafRnqrWNhBCNlGnja0en54C/4tvzsTtDbi4KHeqeJYGsS3btVhTYiiC3D0gLR171hMsJ5Ri7vM0kDOFSFnI6sDYatbtF/fOGIeJqZzerAPBWQNiUPbhicyvNoaUVaM7+ALqJxs1uu/GyKiJN7bOXS0LhPa+NFRT</sessionToken><authenticationResponses><response><name>SM_AUTHDIRNAME</name><value>MultiplusCorporate</value></response><response><name>SM_TIMETOEXPIRE</name><value>7200</value></response><response><name>SM_AUTHDIRNAMESPACE</name><value>LDAP:</value></response><response><name>SM_SERVERSESSIONSPEC</name><value>ykld1rLLVI879vY7izLU8+6HWeIwuwepXMWm8GXORRcSAzxH4MMYRd22jAjwaWBEDyz/nEupHAYilboiKekT99fAQ3XyHjZC6am7p2nnJBrplBBtc+NwCYtkypGzkmz85jaF2LuEisJQbUIe3Kf+WWayzGNQZTJPRG1jvuPjVjVVgWbBPgOMTuwqZugORbFRsVvQ70araTA+zl2BY3qK80jzn2TJg5g9xRPcpqI6xhijbM/Xeqc8621Hi8U0ZikCw855A1elziEucvHykkSBNkR8A7lNv+4u+c6DdzONujKROKWrABxWDSwMoyawh8hsh+eYmhWnwHeVRkapNqxvXJzcrwCVT/AbOFGxqabNAcOS151+hdPH39xL+js73n+L4JSYzKJsEsEPWAJ6ysqGxXnGFZYPsaXU8vbvzxTTWRZKM68cf1yC2aPtvrg/5A3JtgRgC6rGL0jh3ieFwGmBkxbd5uBU0uKZHv4NFJj5P5c=</value></response><response><name>SM_REALM</name><value>AutenticarUsuario_ACESSO</value></response><response><name>SM_AUTHREASON</name><value>0</value></response><response><name>SM_TRANSACTIONID</name><value>000000000000000000000000069016ac-1d5a6-61d3afdc-b932eb70-061f4b16cf0e</value></response><response><name>SM_SERVERIDENTITYSPEC</name><value></value></response><response><name>SM_UNIVERSALID</name><value>65554383807</value></response><response><name>SMSSOZONE</name><value>SM</value></response><response><name>SM_MIGRADO</name><value></value></response><response><name>SM_AUTHDIRSERVER</name><value>dir1:30389 dir2:30389 dir3:30389,dir2:30389 dir3:30389 dir1:30389,dir3:30389 dir1:30389 dir2:30389</value></response><response><name>SM_USERDN</name><value>uid=65554383807,ou=people,ou=multiplus,ou=imcorporate,ou=net</value></response><response><name>SM_USER</name><value>16345789</value></response><response><name>SM_AUTHTYPE</name><value>Basic</value></response><response><name>SM_AUTHDIROID</name><value>0e-00046c02-2521-15a5-a3de-11d20a9890cd</value></response><response><name>SM_REALMOID</name><value>06-00028e4c-95ca-1446-855a-11a80a9830ad</value></response><response><name>SM_SESSIONDRIFT</name><value>0</value></response><response><name>SM_SERVERSESSIONID</name><value>la2XQ7pFsUacKAziQO5Fz1JO87o=</value></response></authenticationResponses></return></ns2:loginResponse></S:Body></S:Envelope>";
  private static final String XML_XXE =
      "<?xml version=\"1.0\"?><!DOCTYPE lolz [ <!ENTITY lol \"lol\"> <!ELEMENT lolz (#PCDATA)> <!ENTITY lol1 \"&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;\"> <!ENTITY lol2 \"&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;\"><!ENTITY lol3 \"&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;\"> <!ENTITY lol4 \"&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;\"> <!ENTITY lol5 \"&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;\"> <!ENTITY lol6 \"&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;\"> <!ENTITY lol7 \"&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;\"> <!ENTITY lol8 \"&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;\"> <!ENTITY lol9 \"&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;\">]><lolz>&lol9;</lolz>";
  private static final String XML_XXE_V2 =
      "<?xml version=\"1.0\"?><!DOCTYPE kaboom [<!ENTITY a \"aaaaaaaaaaaaaaaaaa...\">]><boom>&a;&a;&a;&a;&a;&a;&a;&a;&a;...</boom>";
  private static final String XML_EXPOSING_FILES =
      "<?xml version = \"1.0\" encoding = \"UTF-8\"?><!DOCTYPE example [   <! ENTITY file SYSTEM \"file: ///secrets.txt\">]><example> & file; </example>";
  private static final String XML_XXE_EXTERNAL_URL =
      "<soap:Envelope \txmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Header></soap:Header><soap:Body><aut:login \txmlns:aut=\"http://www.ca.com/siteminder/authaz/2010/04/15/authaz.xsd\"><identityContext><!ENTITY file SYSTEM \"http://169.254.169.254/latest/meta-data/iam/security-credentials/\" ><example>&file;</example><password>password123.</password></identityContext><appId>AutenticarUsuario</appId><resource>AutenticarUsuario_ACESSO</resource><action>GET</action></aut:login></soap:Body></soap:Envelope>";
  private static final String XML_CDATA_XXE =
      "<![CDATA[ <!--XXE_GENERIC_START--> <h3>Attack</h3> <p>XML External Entity (XXE) attacks can occur when an XML parser supports XML entities while processing XML received from an untrusted source.</p> <p><b>Risk 1: Expose local file content (XXE: <u>X</u>ML E<u>x</u>ternal <u>E</u>ntity)</b></p> <p> <pre> &lt;?xml version=&quot;1.0&quot; encoding=&quot;ISO-8859-1&quot;?&gt; &lt;!DOCTYPE foo [ &lt;!ENTITY xxe SYSTEM &quot;file:///etc/passwd&quot; &gt; ]&gt; &lt;foo&gt;&amp;xxe;&lt;/foo&gt;</pre> </p> <b>Risk 2: Denial of service (XEE: <u>X</u>ML <u>E</u>ntity <u>E</u>xpansion)</b> <p> <pre> &lt;?xml version=&quot;1.0&quot;?&gt; &lt;!DOCTYPE lolz [ &lt;!ENTITY lol &quot;lol&quot;&gt; &lt;!ELEMENT lolz (#PCDATA)&gt; &lt;!ENTITY lol1 &quot;&amp;lol;&amp;lol;&amp;lol;&amp;lol;&amp;lol;&amp;lol;&amp;lol;&amp;lol;&amp;lol;&amp;lol;&quot;&gt; &lt;!ENTITY lol2 &quot;&amp;lol1;&amp;lol1;&amp;lol1;&amp;lol1;&amp;lol1;&amp;lol1;&amp;lol1;&amp;lol1;&amp;lol1;&amp;lol1;&quot;&gt; &lt;!ENTITY lol3 &quot;&amp;lol2;&amp;lol2;&amp;lol2;&amp;lol2;&amp;lol2;&amp;lol2;&amp;lol2;&amp;lol2;&amp;lol2;&amp;lol2;&quot;&gt; [...] &lt;!ENTITY lol9 &quot;&amp;lol8;&amp;lol8;&amp;lol8;&amp;lol8;&amp;lol8;&amp;lol8;&amp;lol8;&amp;lol8;&amp;lol8;&amp;lol8;&quot;&gt; ]&gt; &lt;lolz&gt;&amp;lol9;&lt;/lolz&gt;</pre> </p> <h3>Solution</h3> <p> In order to avoid exposing dangerous feature of the XML parser, you can do the following change to the code. </p> <!--XXE_GENERIC_END--> <p><b>Vulnerable Code:</b></p> <p> <pre> DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); Document doc = db.parse(input);</pre> </p> <br/> <p> The following snippets show two available solutions. You can set one feature or both. </p> <p><b>Solution using \"Secure processing\" mode:</b></p> <p> This setting will protect you against Denial of Service attack and remote file access. <pre> DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true); DocumentBuilder db = dbf.newDocumentBuilder(); Document doc = db.parse(input);</pre> </p> <p><b>Solution disabling DTD:</b></p> <p> By disabling DTD, almost all XXE attacks will be prevented. <pre> DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); dbf.setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true); DocumentBuilder db = dbf.newDocumentBuilder(); Document doc = db.parse(input);</pre> </p> <br/> <p> <b>References</b><br/> <!--XXE_GENERIC_START--> <a href=\"https://cwe.mitre.org/data/definitions/611.html\">CWE-611: Improper Restriction of XML External Entity Reference ('XXE')</a><br/> <a href=\"https://www.securecoding.cert.org/confluence/pages/viewpage.action?pageId=61702260\">CERT: IDS10-J. Prevent XML external entity attacks</a><br/> <a href=\"https://www.owasp.org/index.php/XML_External_Entity_%28XXE%29_Processing\">OWASP.org: XML External Entity (XXE) Processing</a><br/> <a href=\"https://www.ws-attacks.org/index.php/XML_Entity_Expansion\">WS-Attacks.org: XML Entity Expansion</a><br/> <a href=\"https://www.ws-attacks.org/index.php/XML_External_Entity_DOS\">WS-Attacks.org: XML External Entity DOS</a><br/> <a href=\"https://www.ws-attacks.org/index.php/XML_Entity_Reference_Attack\">WS-Attacks.org: XML Entity Reference Attack</a><br/> <a href=\"https://blog.h3xstream.com/2014/06/identifying-xml-external-entity.html\">Identifying XML External Entity vulnerability (XXE)</a><br/> <!--XXE_GENERIC_END--> <a href=\"http://xerces.apache.org/xerces2-j/features.html\">Xerces2 complete features list</a> </p> ]]>";

  @Test
  public void shouldParseToXMLDocumentReturnOk() throws ParserConfigurationException {
    Document d = xmlUtils.parseToXMLDocument(XML_RESPONSE);
    assertNotNull(d);
    assertNull(d.getDoctype());
    assertNotNull(d.getDocumentElement());
    assertNotEquals(d, this.buildDocumentScenario());
    assertEquals(1, d.getChildNodes().getLength());
    assertEquals("S:Envelope", d.getChildNodes().item(0).getNodeName());
  }

  @Test
  public void shouldParseToXMLDocumentFailsWithAnEmptyXml() {
    assertThrows(ServiceException.class, () -> xmlUtils.parseToXMLDocument(""));
  }

  @Test
  public void shouldParseToXMLDocumentFailsWithAnWrongXmlFormat() {
    String badXml = "{user: someUser,password: pass123}";
    assertThrows(ServiceException.class, () -> xmlUtils.parseToXMLDocument(badXml));
  }

  @Test
  public void shouldParseToXMLDocumentFailsOnXXEAttack() {
    assertThrows(ServiceException.class, () -> xmlUtils.parseToXMLDocument(XML_XXE));
  }

  @Test
  public void shouldParseToXMLDocumentFailsOnXXEAttack2() {
    assertThrows(ServiceException.class, () -> xmlUtils.parseToXMLDocument(XML_XXE_V2));
  }

  @Test
  public void shouldParseToXMLDocumentFailsOnXXEAttackExposingLocalFiles() {
    assertThrows(ServiceException.class, () -> xmlUtils.parseToXMLDocument(XML_EXPOSING_FILES));
  }

  @Test
  public void shouldParseToXMLDocumentFailsOnXXEAttackWithExternalURL() {
    assertThrows(ServiceException.class, () -> xmlUtils.parseToXMLDocument(XML_XXE_EXTERNAL_URL));
  }

  @Test
  public void shouldParseToXMLDocumentFailsOnXXEAttackWithBadPayload() {
    assertThrows(ServiceException.class, () -> xmlUtils.parseToXMLDocument(XML_CDATA_XXE));
  }

  @Test
  public void shouldPrepareSiteMinderRequestReturnOk() throws PebbleException {

    Mockito.when(pebbleEngine.getTemplate(any()))
        .thenReturn(
            new PebbleEngine.Builder().build().getTemplate("templates/CountryInfoTemplate.pebble"));

    String xmlString = xmlUtils.prepareSiteMinderRequest();
    Document doc = xmlUtils.parseToXMLDocument(xmlString);
    assertNotNull(xmlString);
    assertNotEquals(XML_REQUEST, xmlString);
    assertNotNull(doc.getFirstChild().getNodeName());
  }

  @Test
  public void shouldPrepareSiteMinderRequestFailsWithWrongTemplate()
      throws IOException, PebbleException {
    PebbleTemplate pebbleTemplate = Mockito.mock(PebbleTemplate.class);
    Mockito.when(pebbleEngine.getTemplate(ArgumentMatchers.anyString())).thenReturn(pebbleTemplate);
    Mockito.doThrow(IOException.class).when(pebbleTemplate).evaluate(any(), anyMap());
    assertThrows(ServiceException.class, () -> xmlUtils.prepareSiteMinderRequest());
  }

  private Document buildDocumentScenario() {
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
      return builder.parse(new InputSource(new StringReader(XML_RESPONSE)));
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServiceException(e.getMessage(), "XML Parse error", e);
    }
  }
}
