package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.gov.defra.tracesx.mapper.common.Mapper;
import uk.gov.defra.tracesx.notificationschema.representation.EconomicOperator;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.SpsEventType;
import uk.gov.defra.tracesx.trade.dto.SpsLocationType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class ChedppPodSpsEventMapper implements Mapper<PartOne, List<SpsEventType>> {

  private static final String SCHEME_AGENCY_NAME = "United Kingdom";
  private static final String SCHEME_ID = "pod_crn";

  @Override
  public List<SpsEventType> map(PartOne partOne) {

    List<SpsEventType> spsEventTypeList = new ArrayList<>();

    EconomicOperator pod = partOne.getPod();

    if (pod != null) {
      spsEventTypeList.add(new SpsEventType()
          .withOccurrenceSpsLocation(
              new SpsLocationType()
                  .withId(
                      new IDType()
                          .withSchemeAgencyID(pod.getAddress().getCountryISOCode())
                          .withSchemeAgencyName(SCHEME_AGENCY_NAME)
                          .withSchemeID(SCHEME_ID)
                          .withValue(pod.getApprovalNumber()))
                  .withName(
                      Arrays.asList(
                          new TextType().withValue(pod.getCompanyName()),
                          new TextType().withValue(pod.getAddress().getAddressLine1()),
                          new TextType().withValue(pod.getAddress().getPostalZipCode()),
                          new TextType().withValue(pod.getAddress().getAddressLine3())))));
    }

    return spsEventTypeList;
  }
}
