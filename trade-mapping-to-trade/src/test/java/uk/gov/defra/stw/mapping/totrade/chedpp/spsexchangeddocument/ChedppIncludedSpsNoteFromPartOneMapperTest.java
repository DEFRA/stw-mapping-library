package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;
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
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.IncludedSpsNoteFromPartOneMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.ContactDetails;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

@ExtendWith(MockitoExtension.class)
class ChedppIncludedSpsNoteFromPartOneMapperTest {

  @Mock
  private IncludedSpsNoteFromPartOneMapper includedSpsNoteFromPartOneMapper;

  private PartOne partOne;
  private ChedppIncludedSpsNoteFromPartOneMapper chedppIncludedSpsNoteFromPartOneMapper;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    partOne = objectMapper.readValue(notificationString, Notification.class).getPartOne();

    chedppIncludedSpsNoteFromPartOneMapper = new ChedppIncludedSpsNoteFromPartOneMapper(includedSpsNoteFromPartOneMapper);

    String spsNoteTypesString = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsexchangeddocument/spsNoteTypeFromPartOneComplete.json");
    List<SpsNoteType> commonSpsNoteTypes = objectMapper.readValue(spsNoteTypesString, new TypeReference<>() {});
    when(includedSpsNoteFromPartOneMapper.map(partOne)).thenReturn(commonSpsNoteTypes);
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenComplete() throws JsonProcessingException {
    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);
    String actualSps = objectMapper.writeValueAsString(spsNoteTypes);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeFromPartOneComplete.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenNullConsignmentArrived() throws JsonProcessingException {
    partOne.setConsignmentArrived(null);
    partOne.setContactDetails(null);

    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);
    String actualSps = objectMapper.writeValueAsString(spsNoteTypes);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeFromPartOneNullConsignmentArrived.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenNullContainsWoodPackaging() throws JsonProcessingException {
    partOne.setContainsWoodPackaging(null);
    partOne.setContactDetails(null);

    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);
    String actualSps = objectMapper.writeValueAsString(spsNoteTypes);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeFromPartOneNullContainsWoodPackaging.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenNullContactDetails() throws JsonProcessingException {
    partOne.setContactDetails(null);
    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);
    String actualSps = objectMapper.writeValueAsString(spsNoteTypes);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeFromPartOneNullContactDetails.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenEmptyContactDetails() throws JsonProcessingException {
    partOne.setContactDetails(new ContactDetails());
    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);
    String actualSps = objectMapper.writeValueAsString(spsNoteTypes);
    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/chedpp/spsexchangeddocument/spsNoteTypeFromPartOneNullContactDetails.json");

    assertThat(actualSps).isEqualTo(expectedSps);
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenNullContactDetailsName() {
    partOne.getContactDetails().setName(null);
    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);

    assertMissingContactDetails(spsNoteTypes, "CONTACT_DETAILS_NAME");
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenNullContactDetailsAgent() {
    partOne.getContactDetails().setAgent(null);
    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);

    assertMissingContactDetails(spsNoteTypes, "CONTACT_DETAILS_AGENT");
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenNullContactDetailsEmail() {
    partOne.getContactDetails().setEmail(null);
    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);

    assertMissingContactDetails(spsNoteTypes, "CONTACT_DETAILS_EMAIL");
  }

  @Test
  void map_ReturnsSpsNoteTypeList_WhenNullContactDetailsTelephone() {
    partOne.getContactDetails().setTelephone(null);
    List<SpsNoteType> spsNoteTypes = chedppIncludedSpsNoteFromPartOneMapper.map(partOne);

    assertMissingContactDetails(spsNoteTypes, "CONTACT_DETAILS_TELEPHONE");
  }

  void assertMissingContactDetails(List<SpsNoteType> spsNoteTypes, String subjectCode) {
    assertThat(spsNoteTypes).doesNotContainNull();

    boolean hasContent = spsNoteTypes.stream()
        .anyMatch(spsNoteType -> spsNoteType.getSubjectCode().getValue().equals(subjectCode));
    assertThat(hasContent).isFalse();
  }
}
