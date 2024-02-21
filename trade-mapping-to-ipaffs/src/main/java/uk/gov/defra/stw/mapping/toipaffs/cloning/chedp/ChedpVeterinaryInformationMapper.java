package uk.gov.defra.stw.mapping.toipaffs.cloning.chedp;

import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.cloning.common.ExternalReferenceMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.common.common.VeterinaryInformationMapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ExternalReference;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ExternalSystem;

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
