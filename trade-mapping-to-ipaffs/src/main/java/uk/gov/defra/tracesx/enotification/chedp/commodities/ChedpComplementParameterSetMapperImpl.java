package uk.gov.defra.tracesx.enotification.chedp.commodities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpComplementParameterSetHelper;
import uk.gov.defra.tracesx.common.chedp.commodities.ChedpComplementParameterSetMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.CatchCertificate;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedpComplementParameterSetMapperImpl implements ChedpComplementParameterSetMapper {

  private static final String CATCH_CERTIFICATE_WEIGHT = "CATCH_CERTIFICATE_WEIGHT_KG";

  private final ChedpComplementParameterSetHelper helper;

  public ChedpComplementParameterSetMapperImpl(ChedpComplementParameterSetHelper helper) {
    this.helper = helper;
  }

  @Override
  public List<ComplementParameterSet> map(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    List<ComplementParameterSet> complementParameterSets =
        helper.map(spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem());

    spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(
            includedSpsConsignmentItem -> includedSpsConsignmentItem.getIncludedSpsTradeLineItem()
                .stream())
        .forEach(tradeLineItem -> addCatchCertificateToComplementParameterSet(tradeLineItem,
            complementParameterSets));

    return complementParameterSets;
  }

  private void addCatchCertificateToComplementParameterSet(IncludedSpsTradeLineItem tradeLineItem,
      List<ComplementParameterSet> complementParameterSets) {
    complementParameterSets.stream()
        .filter(complementParameterSet -> complementParameterSet.getComplementID()
            .equals(tradeLineItem.getSequenceNumeric().getValue()))
        .findFirst()
        .ifPresent(complementParameterSet -> complementParameterSet.setCatchCertificates(
            buildCatchCertificates(tradeLineItem)));
  }

  private List<CatchCertificate> buildCatchCertificates(IncludedSpsTradeLineItem tradeLineItem) {
    return tradeLineItem.getAdditionalInformationSpsNote().stream()
        .filter(note -> CATCH_CERTIFICATE_WEIGHT.equals(note.getSubjectCode().getValue()))
        .map(note ->
            CatchCertificate.builder()
                .certificateNumber(note.getContentCode().get(0).getValue())
                .weight(new BigDecimal(note.getContent().get(0).getValue())
                    .setScale(0, RoundingMode.FLOOR))
                .build())
        .collect(Collectors.toList());
  }
}
