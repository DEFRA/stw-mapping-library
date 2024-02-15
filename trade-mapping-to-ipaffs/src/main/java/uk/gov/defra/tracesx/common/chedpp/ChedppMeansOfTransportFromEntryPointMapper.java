package uk.gov.defra.tracesx.common.chedpp;

import java.util.List;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.MeansOfTransportBeforeBip;
import uk.gov.defra.tracesx.trade.dto.MainCarriageSpsTransportMovement;

public interface ChedppMeansOfTransportFromEntryPointMapper
    extends Mapper<List<MainCarriageSpsTransportMovement>, MeansOfTransportBeforeBip> {

  @Override
  MeansOfTransportBeforeBip map(List<MainCarriageSpsTransportMovement> data)
      throws NotificationMapperException;
}
