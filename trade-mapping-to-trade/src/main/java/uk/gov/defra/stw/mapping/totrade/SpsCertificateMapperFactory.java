package uk.gov.defra.stw.mapping.totrade;

import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum.CHEDPP;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.totrade.chedpp.ChedppSpsCertificateMapper;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.NotificationTypeEnum;

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
