package uk.gov.defra.stw.mapping.toipaffs.common.chedpp;

import java.util.List;
import uk.gov.defra.stw.mapping.dto.MainCarriageSpsTransportMovement;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;

public interface ChedppMeansOfTransportMapper
    extends Mapper<List<MainCarriageSpsTransportMovement>, MeansOfTransportAfterBip> {

  @Override
  MeansOfTransportAfterBip map(List<MainCarriageSpsTransportMovement> data)
      throws NotificationMapperException;
}
