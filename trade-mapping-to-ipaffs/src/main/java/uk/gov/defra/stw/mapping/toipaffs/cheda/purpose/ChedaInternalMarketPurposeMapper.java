package uk.gov.defra.stw.mapping.toipaffs.cheda.purpose;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.ForImportOrAdmissionEnum.DEFINITIVE_IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.RE_IMPORT;

import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.InternalMarketPurpose;

@Component
public class ChedaInternalMarketPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  @Override
  public Purpose map(SpsCertificate spsCertificate) {
    Optional<String> internalMarketPurpose = spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication().stream()
        .flatMap(spsAuthentication -> spsAuthentication.getIncludedSpsClause().stream())
        .filter(clause -> "INTERNAL_MARKET_PURPOSE".equals(clause.getId().getValue()))
        .findAny()
        .map(clause -> clause.getContent().get(0).getValue());
    if (internalMarketPurpose.isPresent()) {
      return Purpose.builder()
          .purposeGroup(IMPORT)
          .internalMarketPurpose(InternalMarketPurpose.valueOf(internalMarketPurpose.get()))
          .build();
    } else {
      return Purpose.builder()
          .purposeGroup(RE_IMPORT)
          .forImportOrAdmission(DEFINITIVE_IMPORT)
          .build();
    }
  }
}
