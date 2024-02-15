package uk.gov.defra.tracesx.cloning.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommoditiesSummary {
  private String countryOfOrigin;
  private String consignedCountry;
  private String temperature;
  private List<CommoditySummary> commodityDescriptions;
}
