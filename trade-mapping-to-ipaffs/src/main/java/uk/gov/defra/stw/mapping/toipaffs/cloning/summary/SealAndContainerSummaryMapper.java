package uk.gov.defra.stw.mapping.toipaffs.cloning.summary;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.stw.mapping.toipaffs.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.NotificationSealsContainers;

@Component
public class SealAndContainerSummaryMapper
    implements Mapper<List<NotificationSealsContainers>, List<SealAndContainerSummary>> {

  @Override
  public List<SealAndContainerSummary> map(
      List<NotificationSealsContainers> sealsContainers) {

    List<SealAndContainerSummary> sealsContainersSummaryList = new ArrayList<>();
    if (sealsContainers != null && !sealsContainers.isEmpty()) {
      for (NotificationSealsContainers sealContainer : sealsContainers) {
        sealsContainersSummaryList.add(SealAndContainerSummary.builder()
            .sealNumber(sealContainer.getSealNumber())
            .containerNumber(sealContainer.getContainerNumber())
            .build());
      }
    }

    return sealsContainersSummaryList;
  }
}
