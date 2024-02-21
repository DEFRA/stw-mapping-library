package uk.gov.defra.stw.mapping.toipaffs.cloning.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommoditySummary {
  private String commodityCode;
  private String speciesName;
  private String netWeight;
  private String packages;
  private String packageType;
  private String quantity;
  private String quantityType;
  private String description;
  private String numberOfAnimals;
}
