package uk.gov.defra.tracesx.cloning.chedp;

import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.common.common.VeterinaryInformationMapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class ChedpVeterinaryInformationMapper implements
    Mapper<SpsCertificate, VeterinaryInformation> {
  
  private final VeterinaryInformationMapper veterinaryInformationMapper;
  private final ExternalReferenceMapper externalReferenceMapper;
  
  public ChedpVeterinaryInformationMapper(
      VeterinaryInformationMapper veterinaryInformationMapper,
      ExternalReferenceMapper externalReferenceMapper) {
    this.veterinaryInformationMapper = veterinaryInformationMapper;
    this.externalReferenceMapper = externalReferenceMapper;
  }
  
  @Override
  public VeterinaryInformation map(SpsCertificate data) throws NotificationMapperException {
    VeterinaryInformation veterinaryInformation = veterinaryInformationMapper
        .map(data.getSpsExchangedDocument());
    ExternalReference reference = externalReferenceMapper
        .mapExternalReference(data, ExternalSystem.ECERT).get(0);
    
    veterinaryInformation.getAccompanyingDocuments()
        .forEach(document -> document.setExternalReference(reference));
    return veterinaryInformation;
  }
}
