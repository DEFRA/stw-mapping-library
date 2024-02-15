package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;

@Component
public class ChedppImportSpsCountryMapper implements Mapper<Purpose, SpsCountryType> {

  @Override
  public SpsCountryType map(Purpose purpose) {
    if (purpose.getPurposeGroup() == TRANSHIPMENT_TO) {
      return mapSpsCountryType(purpose.getThirdCountryTranshipment());
    } else if (purpose.getPurposeGroup() == TRANSIT_TO_3RD_COUNTRY) {
      return mapSpsCountryType(purpose.getThirdCountry());
    }
    return null;
  }

  private SpsCountryType mapSpsCountryType(String country) {
    return new SpsCountryType()
        .withId(SpsTypeConverter.getIdType(country));
  }
}
