package uk.gov.defra.tracesx.mapper;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.chedpp.ChedppSpsCertificateMapper;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

@Component
public class SpsCertificateMapperFactory {

  private final Map<NotificationTypeEnum, Mapper<Notification, SpsCertificate>> mapperMap;

  @Autowired
  public SpsCertificateMapperFactory(ChedppSpsCertificateMapper chedppSpsCertificateMapper) {
    mapperMap = Map.of(CHEDPP, chedppSpsCertificateMapper);
  }

  public Mapper<Notification, SpsCertificate> getMapper(NotificationTypeEnum type) {
    return mapperMap.get(type);
  }
}
