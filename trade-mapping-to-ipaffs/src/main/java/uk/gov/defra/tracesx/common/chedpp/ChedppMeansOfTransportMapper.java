package uk.gov.defra.tracesx.common.chedpp;

import java.util.List;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportAfterBip;
import uk.gov.defra.tracesx.trade.dto.MainCarriageSpsTransportMovement;

public interface ChedppMeansOfTransportMapper
    extends Mapper<List<MainCarriageSpsTransportMovement>, MeansOfTransportAfterBip> {

  @Override
  MeansOfTransportAfterBip map(List<MainCarriageSpsTransportMovement> data)
      throws NotificationMapperException;
}
