package uk.gov.defra.tracesx.cloning.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class NotificationSummary {
  private final String referenceNumber;
  private final String countryOfOrigin;
  private final String purpose;
  private final CommoditiesSummary commodities;
  private final String pointOfEntry;
  private final List<SealAndContainerSummary> sealsContainers;
  private final boolean consolidatedCommodities;
}
