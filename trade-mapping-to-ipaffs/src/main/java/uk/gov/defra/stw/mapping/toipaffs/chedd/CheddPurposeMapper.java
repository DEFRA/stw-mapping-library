package uk.gov.defra.stw.mapping.toipaffs.chedd;

import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.TRANSHIPMENT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.PurposeMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@Component
public class CheddPurposeMapper implements Mapper<SpsCertificate, Purpose> {
  private final PurposeMapper purposeMapper;

  public CheddPurposeMapper(PurposeMapper purposeMapper) {
    this.purposeMapper = purposeMapper;
  }

  @Override
  public Purpose map(SpsCertificate spsCertificate) throws NotificationMapperException {
    List<SpsAuthenticationType> spsAuthenticationTypeList = spsCertificate.getSpsExchangedDocument()
        .getSignatorySpsAuthentication();

    String purposeString = spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication()
        .stream()
        .flatMap(spsAuthentication -> spsAuthentication.getIncludedSpsClause().stream())
        .filter(clause -> "PURPOSE".equals(clause.getId().getValue()))
        .findAny()
        .map(clause -> clause.getContent().get(0).getValue())
        .orElse(null);
    if (TRANSHIPMENT.toString().equals(purposeString)) {
      return Purpose.builder()
          .purposeGroup(TRANSHIPMENT_TO)
          .finalBIP(retrieveFinalBip(spsCertificate))
          .build();
    } else {
      for (SpsAuthenticationType spsAuthenticationType : spsAuthenticationTypeList) {
        Purpose purpose = purposeMapper.map(spsAuthenticationType);
        if (purpose != null) {
          return purpose;
        }
      }
      throw new NotificationMapperException("Unable to map to the Purpose");
    }
  }

  private String retrieveFinalBip(SpsCertificate spsCertificate) {
    return spsCertificate
        .getSpsConsignment()
        .getTransitSpsCountry()
        .get(0)
        .getSubordinateSpsCountrySubDivision()
        .get(0)
        .getActivityAuthorizedSpsParty()
        .get(0)
        .getId()
        .getValue();
  }
}
