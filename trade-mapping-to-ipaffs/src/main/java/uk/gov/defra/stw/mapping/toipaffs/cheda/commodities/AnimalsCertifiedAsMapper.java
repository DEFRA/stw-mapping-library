package uk.gov.defra.stw.mapping.toipaffs.cheda.commodities;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.APPROVED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.BREEDING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.CIRCUS;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.FATTENING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.OTHER;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.PETS;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.QUARANTINE;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.REGISTERED;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.RELAYING;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification.SLAUGHTER;

import java.util.Map;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.AnimalCertification;

@Component
public class AnimalsCertifiedAsMapper implements Mapper<SpsCertificate, AnimalCertification> {

  private static final Map<String, AnimalCertification> ANIMAL_CERTIFICATION_MAP = Map.of(
      "APPROVED_BODIES", APPROVED,
      "BREEDING", BREEDING,
      "CIRCUS", CIRCUS,
      "FATTENING", FATTENING,
      "OTHER", OTHER,
      "PETS", PETS,
      "QUARANTINE", QUARANTINE,
      "REGISTERED_EQUIDAE", REGISTERED,
      "RELAYING", RELAYING,
      "SLAUGHTER", SLAUGHTER
  );

  @Override
  public AnimalCertification map(SpsCertificate spsCertificate) {
    return spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication()
        .get(0)
        .getIncludedSpsClause()
        .stream()
        .filter(clause -> clause.getId().getValue().equals("ANIMALS_CERTIFIED_AS"))
        .findAny()
        .map(IncludedSpsClause::getContent)
        .map(contents -> contents.get(0))
        .map(TextType::getValue)
        .map(ANIMAL_CERTIFICATION_MAP::get)
        .orElse(null);
  }
}
