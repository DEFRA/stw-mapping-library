package uk.gov.defra.stw.mapping.toipaffs.cloning.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.CommoditiesMapperException;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

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
