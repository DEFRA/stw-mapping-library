package uk.gov.defra.tracesx.common.common;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;
import uk.gov.defra.tracesx.trade.dto.SpsCountrySubDivisionType;

@Component
public class RegionOfOriginMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) {
    List<SpsCountrySubDivisionType> subDivisionTypeList = spsCertificate
        .getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .collect(Collectors.toList()).get(0)
        .getOriginSpsCountry().get(0)
        .getSubordinateSpsCountrySubDivision();

    return CollectionUtils.isEmpty(subDivisionTypeList) ? null :
        subDivisionTypeList.get(0).getName().get(0).getValue();
  }
}
