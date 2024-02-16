package uk.gov.defra.stw.mapping.toipaffs.enotification.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.EconomicOperatorType;

@Component
public class EconomicOperatorMapper implements Mapper<SpsPartyType, EconomicOperator> {

  @Override
  public EconomicOperator map(SpsPartyType spsPartyType) throws NotificationMapperException {
    if (shouldMapEconomicOperator(spsPartyType)) {
      return EconomicOperator.builder()
          .id(spsPartyType.getId().getValue())
          .build();
    }

    return null;
  }

  public EconomicOperator setEconomicOperatorType(EconomicOperator economicOperator,
      EconomicOperatorType economicOperatorType) {
    if (economicOperator != null) {
      economicOperator.setType(economicOperatorType);
    }
    return economicOperator;
  }

  private boolean shouldMapEconomicOperator(SpsPartyType spsPartyType) {
    return spsPartyType != null && !isIdTypeEmpty(spsPartyType.getId());
  }

  private boolean isIdTypeEmpty(IDType idType) {
    return idType == null || StringUtils.isEmpty(idType.getValue());
  }
}
