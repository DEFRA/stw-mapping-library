package uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SubmittedBy;
import uk.gov.defra.tracesx.notificationschema.representation.UserInformation;

class SubmittedByMapperTest {

  private static final String USER_ID = "085be443-7d85-ee11-8179-000d3a2004d7";
  private static final String USER_NAME = "John Smith";
  private final SubmittedByMapper submittedByMapper = new SubmittedByMapper();

  @Test
  void map_ReturnsStatusCode_WhenComplete() {

    SubmittedBy expected = new SubmittedBy(USER_NAME, USER_ID);

    SubmittedBy submittedBy = submittedByMapper.map(
        new UserInformation(USER_NAME, USER_ID, null));

    assertThat(submittedBy).isEqualTo(expected);
  }
}
