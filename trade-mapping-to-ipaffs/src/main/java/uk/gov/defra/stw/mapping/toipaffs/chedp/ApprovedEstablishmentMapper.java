package uk.gov.defra.stw.mapping.toipaffs.chedp;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.AppliedSpsProcess;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.tracesx.notificationschema.representation.ApprovedEstablishment;

@Component
public class ApprovedEstablishmentMapper {

  protected List<ApprovedEstablishment> map(
      List<IncludedSpsConsignmentItem> includedSpsConsignmentItems) {

    return includedSpsConsignmentItems.stream()
        .flatMap(spsConsignmentItem -> spsConsignmentItem.getIncludedSpsTradeLineItem().stream())
        .flatMap(spsTradeLineItem -> spsTradeLineItem.getAppliedSpsProcess().stream())
        .map(this::createApprovedEstablishment)
        .collect(Collectors.toList());
  }

  private ApprovedEstablishment createApprovedEstablishment(AppliedSpsProcess appliedSpsProcess) {
    SpsPartyType spsPartyType = appliedSpsProcess.getOperatorSpsParty();
    SpsCountryType spsCountryType = appliedSpsProcess.getOperationSpsCountry();

    return ApprovedEstablishment.builder()
        .name(spsPartyType.getName().getValue())
        .country(spsCountryType.getId().getValue())
        .types(List.of(appliedSpsProcess.getTypeCode().getName()))
        .approvalNumber(spsPartyType.getId().getValue())
        .section(spsPartyType.getRoleCode().getName())
        .build();
  }
}
