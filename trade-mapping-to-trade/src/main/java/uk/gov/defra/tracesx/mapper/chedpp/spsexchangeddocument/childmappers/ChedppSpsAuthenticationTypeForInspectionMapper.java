package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument.childmappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.childmappers.SpsAuthenticationTypeForInspectionMapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsClause;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;

@Component
public class ChedppSpsAuthenticationTypeForInspectionMapper extends
    SpsAuthenticationTypeForInspectionMapper {

  private static final String PURPOSE = "PURPOSE";
  private static final String RE_ENTRY = "RE_ENTRY";
  private static final String PRIVATE_IMPORT = "PRIVATE_IMPORT";
  private static final String IMPORT_RE_CONFORMITY_CHECK = "IMPORT_RE_CONFORMITY_CHECK";
  private static final String TRANSFER = "TRANSFER";
  private static final String TRANSHIPMENT = "TRANSHIPMENT";
  private static final String DIRECT_TRANSIT = "DIRECT_TRANSIT";
  private static final String SIGNATORY_PERSON_EMAIL = "SIGNATORY_PERSON_EMAIL";

  @Autowired
  public ChedppSpsAuthenticationTypeForInspectionMapper() {
    purposeMap.put(PurposeGroupEnum.RE_IMPORT, createTextType(RE_ENTRY));
    purposeMap.put(PurposeGroupEnum.PRIVATE_IMPORT, createTextType(PRIVATE_IMPORT));
    purposeMap.put(PurposeGroupEnum.RE_CONFORMITY_CHECK,
        createTextType(IMPORT_RE_CONFORMITY_CHECK));
    purposeMap.put(PurposeGroupEnum.TRANSFER_TO, createTextType(TRANSFER));
    purposeMap.put(PurposeGroupEnum.TRANSHIPMENT_TO, createTextType(TRANSHIPMENT));
    purposeMap.put(PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY, createTextType(DIRECT_TRANSIT));
  }

  @Override
  public SpsAuthenticationType map(PartOne data) {
    return super.map(data)
        .withIncludedSpsClause(createIncludedSpsClauseList(data));
  }

  private List<IncludedSpsClause> createIncludedSpsClauseList(PartOne partOne) {
    return Arrays.asList(mapPurposeGroup(partOne),
        createIncludedSpsClause(partOne));
  }

  private IncludedSpsClause mapPurposeGroup(PartOne partOne) {
    return new IncludedSpsClause()
        .withId(SpsTypeConverter.getIdType(PURPOSE))
        .withContent(Collections.singletonList(
            purposeMap.get(partOne.getPurpose().getPurposeGroup())));
  }

  private IncludedSpsClause createIncludedSpsClause(PartOne partOne) {
    return new IncludedSpsClause()
        .withId(SpsTypeConverter.getIdType(SIGNATORY_PERSON_EMAIL))
        .withContent(Collections
            .singletonList(
                SpsTypeConverter.getTextType(partOne.getPersonResponsible().getEmail())));
  }
}
