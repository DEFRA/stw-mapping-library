package uk.gov.defra.tracesx.common.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsClause;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class PurposeMapperTest {

  private PurposeMapper mapper;
  private SpsAuthenticationType spsAuthenticationType;
  private IncludedSpsClause includedSpsClause;

  @BeforeEach
  void setup() {
    mapper = new PurposeMapper();
    spsAuthenticationType = new SpsAuthenticationType();
    includedSpsClause = new IncludedSpsClause()
        .withId(new IDType().withValue("PURPOSE"));
    spsAuthenticationType.setIncludedSpsClause(Collections.singletonList(includedSpsClause));
  }

  @Test
  void map_ReturnsImport_WhenPurposeIsInternalMarket() throws NotificationMapperException {
    includedSpsClause
        .withContent(Collections.singletonList(new TextType().withValue("INTERNAL_MARKET")));
    Purpose purpose = mapper.map(spsAuthenticationType);

    assertThat(purpose.getPurposeGroup()).isEqualTo(PurposeGroupEnum.IMPORT);
  }

  @Test
  void map_ReturnsTranshipment_WhenPurposeIsTransfer() throws NotificationMapperException {
    includedSpsClause.withContent(Collections.singletonList(new TextType().withValue("TRANSFER")));
    Purpose purpose = mapper.map(spsAuthenticationType);

    assertThat(purpose.getPurposeGroup()).isEqualTo(PurposeGroupEnum.TRANSHIPMENT_TO);
  }

  @Test
  void map_ReturnsReImport_WhenPurposeIsReEntry() throws NotificationMapperException {
    includedSpsClause.withContent(Collections.singletonList(new TextType().withValue("RE_ENTRY")));
    Purpose purpose = mapper.map(spsAuthenticationType);

    assertThat(purpose.getPurposeGroup()).isEqualTo(PurposeGroupEnum.RE_IMPORT);
  }

  @Test
  void map_ReturnsNull_WhenPurposeIsInvalid() throws NotificationMapperException {
    includedSpsClause.withContent(Collections.singletonList(new TextType().withValue("INVALID")));
    Purpose purpose = mapper.map(spsAuthenticationType);

    assertThat(purpose).isNull();
  }
}
