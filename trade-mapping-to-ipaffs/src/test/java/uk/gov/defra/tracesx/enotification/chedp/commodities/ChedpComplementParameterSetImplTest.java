package uk.gov.defra.tracesx.enotification.chedp.commodities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpComplementParameterSetHelper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.CatchCertificate;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.SequenceNumeric;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;
import uk.gov.defra.tracesx.trade.dto.SpsNoteType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@ExtendWith(MockitoExtension.class)
class ChedpComplementParameterSetImplTest {

  @Mock
  private ChedpComplementParameterSetHelper helper;

  @InjectMocks
  private ChedpComplementParameterSetMapperImpl mapper;

  @BeforeEach
  void setup() {
    List<ComplementParameterSet> complementParameterSets = List.of(ComplementParameterSet.builder()
        .complementID(1)
        .build());

    when(helper.map(any())).thenReturn(complementParameterSets);
  }

  @Test
  void map_ReturnsComplementParameterSets_WhenGivenAnSpsCertificateWithCatchCertificate()
      throws NotificationMapperException {

    SpsCertificate spsCertificate = buildSpsCertificateWithCatchCertificateNote("100");

    List<ComplementParameterSet> expected = List.of(ComplementParameterSet.builder()
        .complementID(1)
        .catchCertificates(List.of(CatchCertificate.builder()
            .certificateNumber("12345")
            .weight(new BigDecimal("100"))
            .build()))
        .build());

    List<ComplementParameterSet> actual = mapper.map(spsCertificate);
    assertThat(actual).isEqualTo(expected);
    verify(helper).map(spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem());
  }

  @Test
  void map_ReturnsComplementParameterSets_WhenGivenACatchCertificateContainingDecimal()
      throws NotificationMapperException {

    SpsCertificate spsCertificate = buildSpsCertificateWithCatchCertificateNote("12.34");

    List<ComplementParameterSet> expected = List.of(ComplementParameterSet.builder()
        .complementID(1)
        .catchCertificates(List.of(CatchCertificate.builder()
            .certificateNumber("12345")
            .weight(new BigDecimal("12"))
            .build()))
        .build());

    List<ComplementParameterSet> actual = mapper.map(spsCertificate);
    assertThat(actual).isEqualTo(expected);
    verify(helper).map(spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem());
  }

  private SpsCertificate buildSpsCertificateWithCatchCertificateNote(String contentValue) {
    return new SpsCertificate()
        .withSpsConsignment(new SpsConsignment()
            .withIncludedSpsConsignmentItem(List.of(new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(new IncludedSpsTradeLineItem()
                    .withSequenceNumeric(new SequenceNumeric().withValue(1))
                    .withAdditionalInformationSpsNote(List.of(new SpsNoteType()
                        .withSubjectCode(new CodeType().withValue("CATCH_CERTIFICATE_WEIGHT_KG"))
                        .withContentCode(List.of(new CodeType().withValue("12345")))
                        .withContent(List.of(new TextType().withValue(contentValue))))))))));
  }
}
