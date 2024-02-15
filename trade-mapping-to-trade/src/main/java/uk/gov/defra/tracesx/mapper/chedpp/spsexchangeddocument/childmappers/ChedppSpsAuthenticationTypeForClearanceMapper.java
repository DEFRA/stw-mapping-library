package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument.childmappers;

import static uk.gov.defra.tracesx.mapper.common.utils.Format.localDateTime;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.childmappers.SpsAuthenticationTypeForClearanceMapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.OfficialVeterinarian;
import uk.gov.defra.tracesx.notificationschema.representation.PartTwo;
import uk.gov.defra.tracesx.trade.dto.CodeType;
import uk.gov.defra.tracesx.trade.dto.DateTime;
import uk.gov.defra.tracesx.trade.dto.DateTimeType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsClause;
import uk.gov.defra.tracesx.trade.dto.RoleCode;
import uk.gov.defra.tracesx.trade.dto.RoleCode.Value;
import uk.gov.defra.tracesx.trade.dto.SpecifiedSpsPerson;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;

@Component
public class ChedppSpsAuthenticationTypeForClearanceMapper extends
    SpsAuthenticationTypeForClearanceMapper {

  private static final String AUTHORITY = "AUTHORITY";
  private static final String SIGNATORY_PERSON_EMAIL = "SIGNATORY_PERSON_EMAIL";

  @Override
  public SpsAuthenticationType map(PartTwo data) {
    if (data.getControlAuthority() == null
        || data.getControlAuthority().getOfficialVeterinarian() == null) {
      return null;
    }

    OfficialVeterinarian officialVeterinarian = data.getControlAuthority()
        .getOfficialVeterinarian();

    return super.map(data)
        .withActualDateTime(createActualDateTime(officialVeterinarian))
        .withProviderSpsParty(createProviderSpsParty(officialVeterinarian))
        .withIncludedSpsClause(createIncludedSpsClauseList(officialVeterinarian));
  }

  private DateTimeType createActualDateTime(OfficialVeterinarian officialVeterinarian) {
    return new DateTimeType()
        .withDateTime(new DateTime()
            .withValue(localDateTime.apply(officialVeterinarian.getSigned())));
  }

  private SpsPartyType createProviderSpsParty(OfficialVeterinarian officialVeterinarian) {
    return new SpsPartyType()
        .withRoleCode(new RoleCode()
            .withValue(Value.AM))
        .withTypeCode(Collections.singletonList(new CodeType()
            .withValue(AUTHORITY)))
        .withSpecifiedSpsPerson(createSpecifiedSpsPerson(officialVeterinarian));
  }

  private SpecifiedSpsPerson createSpecifiedSpsPerson(OfficialVeterinarian officialVeterinarian) {
    return new SpecifiedSpsPerson()
        .withName(SpsTypeConverter.getTextType(
            officialVeterinarian.getFirstName() + " " + officialVeterinarian.getLastName()));
  }

  private List<IncludedSpsClause> createIncludedSpsClauseList(
      OfficialVeterinarian officialVeterinarian) {
    return Collections.singletonList(new IncludedSpsClause()
        .withId(SpsTypeConverter.getIdType(SIGNATORY_PERSON_EMAIL))
        .withContent(Collections
            .singletonList(SpsTypeConverter.getTextType(officialVeterinarian.getEmail()))));
  }
}
