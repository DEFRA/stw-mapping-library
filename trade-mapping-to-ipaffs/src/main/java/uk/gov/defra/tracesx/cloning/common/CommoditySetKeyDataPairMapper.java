package uk.gov.defra.tracesx.cloning.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.common.Mapper;
import uk.gov.defra.tracesx.exceptions.CommoditiesMapperException;
import uk.gov.defra.tracesx.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;

@Component
public class CommoditySetKeyDataPairMapper {

  public List<ComplementParameterSetKeyDataPair> map(
      IncludedSpsTradeLineItem includedSpsTradeLineItem,
      Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair>... keyDataPairMappers) {

    List<ComplementParameterSetKeyDataPair> complementParameterSetKeyDataPairList =
        new ArrayList<>();

    for (Mapper<IncludedSpsTradeLineItem, ComplementParameterSetKeyDataPair> mapper
        : keyDataPairMappers) {
      try {
        complementParameterSetKeyDataPairList.add(mapper.map(includedSpsTradeLineItem));
      } catch (NotificationMapperException exception) {
        throw new CommoditiesMapperException(exception.getMessage());
      }
    }

    return complementParameterSetKeyDataPairList.stream().filter(Objects::nonNull).toList();
  }
}
