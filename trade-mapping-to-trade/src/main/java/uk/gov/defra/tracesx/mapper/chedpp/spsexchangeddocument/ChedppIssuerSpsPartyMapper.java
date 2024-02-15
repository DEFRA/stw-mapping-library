package uk.gov.defra.tracesx.mapper.chedpp.spsexchangeddocument;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.mapper.common.spsexchangeddocument.IssuerSpsPartyMapper;
import uk.gov.defra.tracesx.mapper.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Party;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;

@Component
public class ChedppIssuerSpsPartyMapper implements Mapper<Party, SpsPartyType> {

  private final IssuerSpsPartyMapper issuerSpsPartyMapper;

  @Autowired
  public ChedppIssuerSpsPartyMapper(IssuerSpsPartyMapper issuerSpsPartyMapper) {
    this.issuerSpsPartyMapper = issuerSpsPartyMapper;
  }

  @Override
  public SpsPartyType map(Party data) {
    return issuerSpsPartyMapper.map(data)
        .withName(StringUtils.isNotEmpty(data.getCompanyId())
            ? SpsTypeConverter.getTextType(data.getCompanyId()) : null);
  }
}
