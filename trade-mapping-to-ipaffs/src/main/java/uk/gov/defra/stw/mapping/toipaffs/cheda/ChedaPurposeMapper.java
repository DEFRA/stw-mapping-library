package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.DIRECT_TRANSIT;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.INTERNAL_MARKET;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.RE_ENTRY;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.TEMPORARY_ADMISSION_HORSES;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.TRANSHIPMENT;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaInternalMarketPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaReEntryPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaTemporaryHorsesPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaTranshipmentPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.cheda.purpose.ChedaTransitPurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class ChedaPurposeMapper implements Mapper<SpsCertificate, Purpose> {

  private final Map<String, Mapper<SpsCertificate, Purpose>> purposeMappers;

  @Autowired
  public ChedaPurposeMapper(
      ChedaInternalMarketPurposeMapper chedaInternalMarketPurposeMapper,
      ChedaTranshipmentPurposeMapper chedaTranshipmentPurposeMapper,
      ChedaTransitPurposeMapper chedaTransitPurposeMapper,
      ChedaReEntryPurposeMapper chedaReEntryPurposeMapper,
      ChedaTemporaryHorsesPurposeMapper chedaTemporaryHorsesPurposeMapper
  ) {
    purposeMappers = Map.of(
        INTERNAL_MARKET.name(), chedaInternalMarketPurposeMapper,
        TRANSHIPMENT.name(), chedaTranshipmentPurposeMapper,
        DIRECT_TRANSIT.name(), chedaTransitPurposeMapper,
        RE_ENTRY.name(), chedaReEntryPurposeMapper,
        TEMPORARY_ADMISSION_HORSES.name(), chedaTemporaryHorsesPurposeMapper
    );
  }

  @Override
  public Purpose map(SpsCertificate spsCertificate) throws NotificationMapperException {
    return getMapper(spsCertificate).map(spsCertificate);
  }

  private Mapper<SpsCertificate, Purpose> getMapper(SpsCertificate spsCertificate)
      throws NotificationMapperException {
    String purpose = spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication()
        .stream()
        .flatMap(spsAuthentication -> spsAuthentication.getIncludedSpsClause().stream())
        .filter(clause -> "PURPOSE".equals(clause.getId().getValue()))
        .findAny()
        .map(clause -> clause.getContent().get(0).getValue())
        .orElse(null);
    Mapper<SpsCertificate, Purpose> mapper = purposeMappers.get(purpose);
    if (mapper != null) {
      return mapper;
    } else {
      throw new NotificationMapperException("No mapper for purpose: " + purpose);
    }
  }
}
