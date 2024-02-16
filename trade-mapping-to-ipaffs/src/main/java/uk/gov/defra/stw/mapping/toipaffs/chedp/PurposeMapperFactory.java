package uk.gov.defra.stw.mapping.toipaffs.chedp;

import static uk.gov.defra.stw.mapping.toipaffs.enumeration.DestinationType.CUSTOMS_WAREHOUSE;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.DestinationType.FREE_ZONE;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.DestinationType.SHIP;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.DIRECT_TRANSIT;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.INTERNAL_MARKET;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.NON_CONFORMING_GOODS;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.RE_ENTRY;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.TRANSHIPMENT;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.SubjectCode.NON_CONFORMING_GOODS_DESTINATION_TYPE;
import static uk.gov.defra.stw.mapping.toipaffs.utils.SpsNoteTypeHelper.findSpsNoteByType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;

@Component
public class PurposeMapperFactory {

  private static final String PURPOSE = "PURPOSE";

  private final Map<String, PurposeMapper> purposeMappers;
  private final Map<String, PurposeMapper> nonConformingPurposeMappers;

  @Autowired
  public PurposeMapperFactory(
      TranshipmentPurposeMapper transhipmentPurposeMapper,
      CustomsWarehousePurposeMapper customsWarehousePurposeMapper,
      ReEntryPurposeMapper reEntryPurposeMapper,
      ShipPurposeMapper shipPurposeMapper,
      TransitPurposeMapper transitPurposeMapper,
      FreeZonePurposeMapper freeZonePurposeMapper,
      InternalMarketPurposeMapper internalMarketPurposeMapper) {
    purposeMappers =
        Map.of(
            DIRECT_TRANSIT.name(), transitPurposeMapper,
            TRANSHIPMENT.name(), transhipmentPurposeMapper,
            RE_ENTRY.name(), reEntryPurposeMapper,
            INTERNAL_MARKET.name(), internalMarketPurposeMapper);
    nonConformingPurposeMappers =
        Map.of(
            CUSTOMS_WAREHOUSE.name(), customsWarehousePurposeMapper,
            SHIP.name(), shipPurposeMapper,
            FREE_ZONE.name(), freeZonePurposeMapper);
  }

  protected Optional<PurposeMapper> get(SpsExchangedDocument exchangedDocument) {
    String purpose = getPurposeOfConsignment(exchangedDocument.getSignatorySpsAuthentication());

    if (NON_CONFORMING_GOODS.name().equals(purpose)) {
      String destinationType = getDestinationType(exchangedDocument.getIncludedSpsNote());
      return Optional.ofNullable(nonConformingPurposeMappers.get(destinationType));
    }

    return Optional.ofNullable(purposeMappers.get(purpose));
  }

  private String getDestinationType(List<SpsNoteType> spsNoteTypes) {
    return findSpsNoteByType(spsNoteTypes, NON_CONFORMING_GOODS_DESTINATION_TYPE.getValue())
        .map(spsNoteType -> spsNoteType.getContent().get(0).getValue())
        .orElse(StringUtils.EMPTY);
  }

  private String getPurposeOfConsignment(List<SpsAuthenticationType> spsAuthenticationTypeList) {
    return spsAuthenticationTypeList.stream()
        .flatMap(spsAuthenticationType -> spsAuthenticationType.getIncludedSpsClause().stream())
        .filter(includedSpsClause -> PURPOSE.equals(includedSpsClause.getId().getValue()))
        .findFirst()
        .filter(includedSpsClause -> !includedSpsClause.getContent().isEmpty())
        .map(includedSpsClause -> includedSpsClause.getContent().get(0).getValue())
        .orElse(StringUtils.EMPTY);
  }
}
