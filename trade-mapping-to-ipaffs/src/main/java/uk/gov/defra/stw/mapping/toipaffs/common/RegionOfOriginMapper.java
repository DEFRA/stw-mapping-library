package uk.gov.defra.stw.mapping.toipaffs.common;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;

@Component
public class RegionOfOriginMapper implements Mapper<SpsCertificate, String> {

  @Override
  public String map(SpsCertificate spsCertificate) {
    List<SpsCountrySubDivisionType> subDivisionTypeList = spsCertificate
        .getSpsConsignment().getIncludedSpsConsignmentItem().stream()
        .flatMap(item -> item.getIncludedSpsTradeLineItem().stream())
        .toList()
        .get(0)
        .getOriginSpsCountry()
        .get(0)
        .getSubordinateSpsCountrySubDivision();

    return CollectionUtils.isEmpty(subDivisionTypeList) ? null :
        subDivisionTypeList.get(0).getName().get(0).getValue();
  }
}
