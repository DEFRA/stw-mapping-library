package uk.gov.defra.stw.mapping.toipaffs.common;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class PurposeMapper implements Mapper<SpsAuthenticationType, Purpose> {

  private static final String PURPOSE = "PURPOSE";
  private static final String INTERNAL_MARKET = "INTERNAL_MARKET";
  private static final String TRANSFER = "TRANSFER";
  private static final String RE_ENTRY = "RE_ENTRY";

  public Purpose map(SpsAuthenticationType spsAuthenticationType)
      throws NotificationMapperException {
    if (isPurposeFor(spsAuthenticationType.getIncludedSpsClause(), INTERNAL_MARKET)) {
      return Purpose.builder().purposeGroup(IMPORT).build();
    } else if (isPurposeFor(spsAuthenticationType.getIncludedSpsClause(), TRANSFER)) {
      return Purpose.builder().purposeGroup(TRANSHIPMENT_TO).build();
    } else if (isPurposeFor(spsAuthenticationType.getIncludedSpsClause(), RE_ENTRY)) {
      return Purpose.builder().purposeGroup(RE_IMPORT).build();
    }

    return null;
  }

  private boolean isPurposeFor(List<IncludedSpsClause> includedSpsClauses,
      String value) {
    for (IncludedSpsClause includedSpsClause : includedSpsClauses) {
      for (TextType textType : includedSpsClause.getContent()) {
        IDType id = includedSpsClause.getId();
        if (id != null && id.getValue().equals(PURPOSE) && textType.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }
}
