package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.RoleCode.Value;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;

@Component
public class ChedppConsignorSpsPartyMapper implements Mapper<EconomicOperator, SpsPartyType> {

  private final SpsPartyMapper spsPartyMapper;
  private static final String OPERATOR_ACTIVITY_TYPE = "operator_activity_type";
  private static final String CLASSIFICATION_SECTION_CODE = "classification_section_code";
  private static final String EXPORTER = "EXPORTER";
  private static final String CLASSIFICATION_CODE = "NON_ANIMAL_ORIGIN_FOOD_AND_FEED";

  @Autowired
  public ChedppConsignorSpsPartyMapper(
      SpsPartyMapper spsPartyMapper) {
    this.spsPartyMapper = spsPartyMapper;
  }

  @Override
  public SpsPartyType map(EconomicOperator data) {
    return spsPartyMapper.map(data)
        .withRoleCode(SpsTypeConverter.getRoleCode("Exporter", Value.EX))
        .withTypeCode(Arrays.asList(
            SpsTypeConverter.getCodeTypeWithListId(OPERATOR_ACTIVITY_TYPE, EXPORTER),
            SpsTypeConverter.getCodeTypeWithListId(CLASSIFICATION_SECTION_CODE,
                CLASSIFICATION_CODE)));
  }
}
