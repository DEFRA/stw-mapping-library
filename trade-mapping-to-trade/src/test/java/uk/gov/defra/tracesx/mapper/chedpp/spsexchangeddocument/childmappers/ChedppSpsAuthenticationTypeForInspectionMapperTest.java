package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument.childmappers;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.PRIVATE_IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_CONFORMITY_CHECK;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSFER_TO;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;

class ChedppSpsAuthenticationTypeForInspectionMapperTest {

  private static final String CONTENT_TYPE_KEY = "#{contentType}";

  private Notification notification;
  private ChedppSpsAuthenticationTypeForInspectionMapper chedppSpsAuthenticationTypeForInspectionMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);

    chedppSpsAuthenticationTypeForInspectionMapper = new ChedppSpsAuthenticationTypeForInspectionMapper();
  }

  @Test
  void map_ReturnsSpsAuthenticationType_WhenImportPurpose() throws JsonProcessingException {
    assertSpsAuthenticationType(IMPORT, "INTERNAL_MARKET");
  }

  @Test
  void map_ReturnsSpsAuthenticationType_WhenReImportPurpose() throws JsonProcessingException {
    assertSpsAuthenticationType(RE_IMPORT, "RE_ENTRY");
  }

  @Test
  void map_ReturnsSpsAuthenticationType_WhenPrivateImportPurpose() throws JsonProcessingException {
    assertSpsAuthenticationType(PRIVATE_IMPORT, "PRIVATE_IMPORT");
  }

  @Test
  void map_ReturnsSpsAuthenticationType_WhenReConformityCheckPurpose() throws JsonProcessingException {
    assertSpsAuthenticationType(RE_CONFORMITY_CHECK, "IMPORT_RE_CONFORMITY_CHECK");
  }

  @Test
  void map_ReturnsSpsAuthenticationType_WhenTransferToPurpose() throws JsonProcessingException {
    assertSpsAuthenticationType(TRANSFER_TO, "TRANSFER");
  }

  @Test
  void map_ReturnsSpsAuthenticationType_WhenTranshipmentToPurpose() throws JsonProcessingException {
    assertSpsAuthenticationType(TRANSHIPMENT_TO, "TRANSHIPMENT");
  }

  @Test
  void map_ReturnsSpsAuthenticationType_WhenTransitToThirdCountryPurpose() throws JsonProcessingException {
    assertSpsAuthenticationType(TRANSIT_TO_3RD_COUNTRY, "DIRECT_TRANSIT");
  }

  private void assertSpsAuthenticationType(PurposeGroupEnum purpose, String contentType) throws JsonProcessingException {
    notification.getPartOne().getPurpose().setPurposeGroup(purpose);
    SpsAuthenticationType spsAuthenticationType = chedppSpsAuthenticationTypeForInspectionMapper
        .map(notification.getPartOne());

    String actualSps = objectMapper.writeValueAsString(spsAuthenticationType);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsAuthenticationTypeForInspectionComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps
        .replace(CONTENT_TYPE_KEY, contentType));
  }
}
