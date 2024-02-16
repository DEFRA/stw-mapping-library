package uk.gov.defra.stw.mapping.totrade.common.spsconsignment;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.FunctionTypeCode;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.IpaffsRegionsUtil;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;

@Component
public class ExportSpsCountryMapper implements Mapper<Commodities, SpsCountryType> {

  private static final String HIERARCHICAL_LEVEL_CODE_VALUE = "1";
  private static final String SUBDIVISION_TYPE_CODE = "106";

  @Override
  public SpsCountryType map(Commodities commodity) {
    SpsCountryType spsCountryType = new SpsCountryType();

    if (IpaffsRegionsUtil.isUkRegion(commodity.getConsignedCountry())) {
      spsCountryType.withId(SpsTypeConverter.getIdType(IpaffsRegionsUtil.GB));
      spsCountryType.setSubordinateSpsCountrySubDivision(
          mapSubordinateSpsCountrySubDivision(
              commodity.getConsignedCountry(), SUBDIVISION_TYPE_CODE));
    } else {
      spsCountryType.withId(SpsTypeConverter.getIdType(commodity.getConsignedCountry()));
    }

    return spsCountryType;
  }

  private List<SpsCountrySubDivisionType> mapSubordinateSpsCountrySubDivision(
      String country, String functionTypeCode) {
    return Collections.singletonList(
        new SpsCountrySubDivisionType()
            .withName(
                Collections.singletonList(
                    new TextType()
                        .withValue(IpaffsRegionsUtil.getIsoRegionFromIpaffsRegion(country))))
            .withHierarchicalLevelCode(new CodeType().withValue(HIERARCHICAL_LEVEL_CODE_VALUE))
            .withFunctionTypeCode(new FunctionTypeCode().withValue(functionTypeCode)));
  }
}
