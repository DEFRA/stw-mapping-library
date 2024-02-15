package uk.gov.defra.tracesx.mapper.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.BLOCK;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.BOTTLE_FLASK_GLASS;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.BULK;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.CONTAINER;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.CONTAINER_NOT_SPECIFIED;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.DRUM;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.DUNNAGE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.IN_BULK;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.JAR;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.LOGS;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.LUG;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.NATURAL_WOOD_BOX_WITH_SIFT_PROOF;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.NATURAL_WOOD_ORDINARY_BOX;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.OTHER;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.PAIL;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.PERSONAL_LUGGAGE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.PLASTIC_FILM;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.PUNNET;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.REDNET;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.TANK;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.TOTE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_BARREL;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_BARREL_WITH_REMOVABLE_HEAD;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_BUNDLE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_CASE_WITH_PALLET_BASE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_CRATE;
import static uk.gov.defra.tracesx.mapper.chedpp.spsconsignment.ChedppIncludedSpsConsignmentItemMapper.WOOD_PACKAGE_DISPLAY;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.BAG;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.BALE;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.BOX;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.CAN;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.CARTON;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.CASE;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.CASK;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.COFFER;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.CRATE;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.PACKAGE;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.PALLET;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.POLYSTYRENE_BOX;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.TRAY;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.TUBE;
import static uk.gov.defra.tracesx.mapper.common.spsconsignment.IncludedSpsConsignmentItemMapper.VIAL;

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

class ChedppIncludedSpsConsignmentItemMapperForPackageTypeTest {

  private static final int TYPE_PACKAGE = 2;
  private static final String TYPE_CODE_VALUE_KEY = "#{typeCodeValue}";

  private Notification notification;
  private ChedppIncludedSpsConsignmentItemMapper mapper;
  private ObjectMapper objectMapper;

  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {PackageType.BAG, BAG},
        {PackageType.BALE, BALE},
        {PackageType.BOTTLE_FLASK_GLASS, BOTTLE_FLASK_GLASS},
        {PackageType.BOX, BOX},
        {PackageType.BULK, BULK},
        {PackageType.CAN, CAN},
        {PackageType.CARTON, CARTON},
        {PackageType.CASE, CASE},
        {PackageType.CASK, CASK},
        {PackageType.COFFER, COFFER},
        {PackageType.CONTAINER, CONTAINER},
        {PackageType.CRATE, CRATE},
        {PackageType.PACKAGE, PACKAGE},
        {PackageType.OTHER, OTHER},
        {PackageType.PALLET, PALLET},
        {PackageType.POLYSTYRENE_BOX, POLYSTYRENE_BOX},
        {PackageType.TUBE, TUBE},
        {PackageType.TRAY, TRAY},
        {PackageType.VIAL, VIAL},
        {PackageType.WOOD_BUNDLE, WOOD_BUNDLE},
        {PackageType.WOOD_CRATE, WOOD_CRATE},
        {PackageType.WOOD_BARREL, WOOD_BARREL},
        {PackageType.WOOD_CASE_WITH_PALLET_BASE, WOOD_CASE_WITH_PALLET_BASE},
        {PackageType.BLOCK, BLOCK},
        {PackageType.CONTAINER_NOT_SPECIFIED, CONTAINER_NOT_SPECIFIED},
        {PackageType.IN_BULK, IN_BULK},
        {PackageType.DRUM, DRUM},
        {PackageType.DUNNAGE, DUNNAGE},
        {PackageType.JAR, JAR},
        {PackageType.LOGS, LOGS},
        {PackageType.LUG, LUG},
        {PackageType.NATURAL_WOOD_BOX_WITH_SIFT_PROOF, NATURAL_WOOD_BOX_WITH_SIFT_PROOF},
        {PackageType.NATURAL_WOOD_ORDINARY_BOX, NATURAL_WOOD_ORDINARY_BOX},
        {PackageType.PAIL, PAIL},
        {PackageType.PERSONAL_LUGGAGE, PERSONAL_LUGGAGE},
        {PackageType.PLASTIC_FILM, PLASTIC_FILM},
        {PackageType.PUNNET, PUNNET},
        {PackageType.REDNET, REDNET},
        {PackageType.TANK, TANK},
        {PackageType.TOTE, TOTE},
        {PackageType.WOOD_PACKAGE_DISPLAY, WOOD_PACKAGE_DISPLAY},
        {PackageType.WOOD_BARREL_WITH_REMOVABLE_HEAD, WOOD_BARREL_WITH_REMOVABLE_HEAD}
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
  void map_ReturnsSpsConsignmentItem_WhenAllFieldsComplete(PackageType packageType, String PackageTypeCode) throws Exception {
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .get(0).getKeyDataPair().get(TYPE_PACKAGE).setData(packageType.getValue());

    IncludedSpsConsignmentItem includedSpsConsignmentItem = mapper.map(notification);

    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemPackageTypeComplete.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem.
        replace(TYPE_CODE_VALUE_KEY, PackageTypeCode));
  }
}
