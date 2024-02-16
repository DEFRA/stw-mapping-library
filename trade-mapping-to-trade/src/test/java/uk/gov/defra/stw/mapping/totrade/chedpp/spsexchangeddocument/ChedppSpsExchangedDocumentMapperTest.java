package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.dto.SpsReferencedDocumentType;
import uk.gov.defra.stw.mapping.dto.StatusCode;
import uk.gov.defra.stw.mapping.dto.SubmittedBy;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.StatusCodeMapper;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.SubmittedByMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

@ExtendWith(MockitoExtension.class)
class ChedppSpsExchangedDocumentMapperTest {

  private Notification notification;
  private ChedppSpsExchangedDocumentMapper chedppSpsExchangedDocumentMapper;
  private ObjectMapper objectMapper;

  private List<SpsNoteType> spsNoteTypes;
  private List<SpsAuthenticationType> spsAuthenticationTypes;
  private List<SpsReferencedDocumentType> spsReferencedDocumentType;
  private SpsPartyType spsPartyType;
  private SubmittedBy submittedBy;

  @Mock
  private ChedppIncludedSpsNoteMapper chedppIncludedSpsNoteMapper;
  @Mock
  private ChedppSignatorySpsAuthenticationMapper chedppSignatorySpsAuthenticationMapper;
  @Mock
  private ChedppReferenceSpsReferencedDocumentMapper chedppReferenceSpsReferencedDocumentMapper;
  @Mock
  private StatusCodeMapper statusCodeMapper;
  @Mock
  private ChedppIssuerSpsPartyMapper issuerSpsPartyMapper;
  @Mock
  private SubmittedByMapper submittedByMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    chedppSpsExchangedDocumentMapper = new ChedppSpsExchangedDocumentMapper(
        chedppIncludedSpsNoteMapper,
        chedppSignatorySpsAuthenticationMapper,
        chedppReferenceSpsReferencedDocumentMapper,
        statusCodeMapper,
        issuerSpsPartyMapper,
        submittedByMapper
    );

    String includedSpsNoteString = ResourceUtil
        .readFileToString("classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeComplete.json");
    spsNoteTypes = objectMapper.readValue(includedSpsNoteString, new TypeReference<>() {
    });

    String signatorySpsAuthenticationString = ResourceUtil
        .readFileToString(
            "classpath:mapping/chedpp/spsexchangeddocument/signatorySpsAuthenticationComplete.json");
    spsAuthenticationTypes = objectMapper
        .readValue(signatorySpsAuthenticationString, new TypeReference<>() {
        });

    String referencedDocumentTypeString = ResourceUtil
        .readFileToString(
            "classpath:mapping/chedpp/spsexchangeddocument/referenceSpsReferencedDocumentComplete.json");
    spsReferencedDocumentType = objectMapper
        .readValue(referencedDocumentTypeString, new TypeReference<>() {
        });

    String issuerSpsPartyString = ResourceUtil
        .readFileToString(
            "classpath:mapping/common/spsexchangeddocument/issuerSpsPartyComplete.json");
    spsPartyType = objectMapper.readValue(issuerSpsPartyString, SpsPartyType.class);

    String submittedByString = ResourceUtil
        .readFileToString(
            "classpath:mapping/common/spsexchangeddocument/submittedBy.json");
    submittedBy = objectMapper.readValue(submittedByString, SubmittedBy.class);
  }

  @Test
  void map_ReturnsSpsExchangedDocument_WhenComplete()
      throws JsonProcessingException {
    when(chedppIncludedSpsNoteMapper.map(notification)).thenReturn(spsNoteTypes);
    when(chedppSignatorySpsAuthenticationMapper.map(notification))
        .thenReturn(spsAuthenticationTypes);
    when(chedppReferenceSpsReferencedDocumentMapper.map(notification))
        .thenReturn(spsReferencedDocumentType);
    when(statusCodeMapper.map(StatusEnum.VALIDATED)).thenReturn(new StatusCode().withValue("70"));
    when(issuerSpsPartyMapper.map(notification.getPartOne().getPersonResponsible()))
        .thenReturn(spsPartyType);
    when(submittedByMapper.map(notification.getPartOne().getSubmittedBy()))
        .thenReturn(submittedBy);

    SpsExchangedDocument spsExchangedDocument = chedppSpsExchangedDocumentMapper.map(notification);

    String actualSpsConsignment = objectMapper.writeValueAsString(spsExchangedDocument);
    String expectedSpsConsignment = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsExchangedDocumentComplete.json");
    JSONAssert.assertEquals(
        expectedSpsConsignment, actualSpsConsignment, JSONCompareMode.LENIENT);
  }
}
