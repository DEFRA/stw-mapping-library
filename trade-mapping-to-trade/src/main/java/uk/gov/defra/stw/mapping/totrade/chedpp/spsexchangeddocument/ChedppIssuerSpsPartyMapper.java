package uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.totrade.common.Mapper;
import uk.gov.defra.stw.mapping.totrade.common.spsexchangeddocument.IssuerSpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.common.utils.SpsTypeConverter;
import uk.gov.defra.tracesx.notificationschema.representation.Party;

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
