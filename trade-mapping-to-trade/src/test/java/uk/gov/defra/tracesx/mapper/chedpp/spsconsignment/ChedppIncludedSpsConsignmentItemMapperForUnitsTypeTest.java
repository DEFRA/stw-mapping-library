package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.BOX;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.CASE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.COFFER;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.CONTAINER;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.DRUM;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.LOGS;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.NATURAL_WOOD_BOX_WITH_SIFT_PROOF;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.NATURAL_WOOD_ORDINARY_BOX;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.PALLET;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_BARREL;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_BARREL_WITH_REMOVABLE_HEAD;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_CASE_WITH_PALLET_BASE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_CRATE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_PACKAGE_DISPLAY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.defra.tracesx.mapper.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.tracesx.mapper.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.PackageType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsConsignmentItem;

class ChedppIncludedSpsConsignmentItemMapperForUnitsTypeTest {

  private static final int TYPE_UNITS = 1;
  private static final String TYPE_CODE_VALUE_KEY = "#{typeCodeValue}";

  private Notification notification;
  private ChedppIncludedSpsConsignmentItemMapper mapper;
  private ObjectMapper objectMapper;

  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {PackageType.BOX, BOX},
        {PackageType.CASE, CASE},
        {PackageType.COFFER, COFFER},
        {PackageType.CONTAINER, CONTAINER},
        {PackageType.DRUM, DRUM},
        {PackageType.LOGS, LOGS},
        {PackageType.NATURAL_WOOD_BOX_WITH_SIFT_PROOF, NATURAL_WOOD_BOX_WITH_SIFT_PROOF},
        {PackageType.NATURAL_WOOD_ORDINARY_BOX, NATURAL_WOOD_ORDINARY_BOX},
        {PackageType.PALLET, PALLET},
        {PackageType.WOOD_CRATE, WOOD_CRATE},
        {PackageType.WOOD_PACKAGE_DISPLAY, WOOD_PACKAGE_DISPLAY},
        {PackageType.WOOD_BARREL, WOOD_BARREL},
        {PackageType.WOOD_BARREL_WITH_REMOVABLE_HEAD, WOOD_BARREL_WITH_REMOVABLE_HEAD},
        {PackageType.WOOD_CASE_WITH_PALLET_BASE, WOOD_CASE_WITH_PALLET_BASE}
    });
  }

  @BeforeEach
  void setup() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    notification = objectMapper.readValue(notificationString, Notification.class);
    mapper = new ChedppIncludedSpsConsignmentItemMapper(new SpsPartyMapper());
  }

  @ParameterizedTest
  @MethodSource("data")
  void map_ReturnsSpsConsignmentItem_WhenAllFieldsComplete(PackageType unitsType, String typeCode) throws Exception {
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .get(1).getKeyDataPair().get(TYPE_UNITS).setData(unitsType.getValue());

    IncludedSpsConsignmentItem includedSpsConsignmentItem = mapper.map(notification);

    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemUnitsTypeComplete.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem.
        replace(TYPE_CODE_VALUE_KEY, typeCode));
  }
}
