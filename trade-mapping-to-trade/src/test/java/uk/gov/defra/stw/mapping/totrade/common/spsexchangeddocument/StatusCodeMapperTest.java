package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.AMEND;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.CANCELLED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.DELETED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.IN_PROGRESS;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.MODIFY;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.PARTIALLY_REJECTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.REJECTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SPLIT_CONSIGNMENT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.SUBMITTED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum.VALIDATED;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.defra.stw.mapping.dto.StatusCode;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.StatusEnum;

class StatusCodeMapperTest {

  public static final String STATUS_VALUE_KEY = "#{statusValue}";
  private static final String SUBMITTED_VALUE = "1";
  private static final String IN_PROGRESS_VALUE = "42";
  private static final String MODIFY_VALUE = "42";
  private static final String REJECTED_VALUE = "41";
  private static final String VALIDATED_VALUE = "70";
  private static final String CANCELLED_VALUE = "64";
  private static final String DELETED_VALUE = "55";
  private static final String AMEND_VALUE = "85";
  private static final String PARTIALLY_REJECTED_VALUE = "122";
  private static final String SPLIT_CONSIGNMENT_VALUE = "68";

  private StatusCodeMapper statusCodeMapper;
  private ObjectMapper objectMapper;

  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {SUBMITTED, SUBMITTED_VALUE},
        {IN_PROGRESS, IN_PROGRESS_VALUE},
        {MODIFY, MODIFY_VALUE},
        {REJECTED, REJECTED_VALUE},
        {VALIDATED, VALIDATED_VALUE},
        {CANCELLED, CANCELLED_VALUE},
        {DELETED, DELETED_VALUE},
        {AMEND, AMEND_VALUE},
        {PARTIALLY_REJECTED, PARTIALLY_REJECTED_VALUE},
        {SPLIT_CONSIGNMENT, SPLIT_CONSIGNMENT_VALUE}
    });
  }

  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    statusCodeMapper = new StatusCodeMapper();
  }

  @ParameterizedTest
  @MethodSource("data")
  void map_ReturnsStatusCode_WhenComplete(StatusEnum givenStatus, String expectedStatus) throws JsonProcessingException {
    StatusCode statusCode = statusCodeMapper.map(givenStatus);
    String actualSps = objectMapper.writeValueAsString(statusCode);

    String expectedSps = ResourceUtil.readFileToString(
        "classpath:mapping/common/spsexchangeddocument/statusCodeComplete.json");
    assertThat(actualSps).isEqualTo(expectedSps.replace(STATUS_VALUE_KEY, expectedStatus));
  }
}
