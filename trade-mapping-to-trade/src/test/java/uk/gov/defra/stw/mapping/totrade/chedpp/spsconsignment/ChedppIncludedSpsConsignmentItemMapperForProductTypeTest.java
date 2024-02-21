package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.ProductType;

class ChedppIncludedSpsConsignmentItemMapperForProductTypeTest {

  private static final int PRODUCT_TYPE = 3;
  private static final String PRODUCT_TYPE_CODE_VALUE_KEY = "#{productTypeCode}";
  private static final String PRODUCT_TYPE_DESCRIPTION_VALUE_KEY = "#{productTypeDescription}";

  private Notification notification;
  private ChedppIncludedSpsConsignmentItemMapper mapper;
  private ObjectMapper objectMapper;

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
  @EnumSource(ProductType.class)
  void map_ReturnsSpsConsignmentItem_WhenAllFieldsComplete(ProductType productType) throws Exception {
    String productTypeDescription = productType.getValue();
    String productTypeCode = productType.name();
    notification.getPartOne().getCommodities().getComplementParameterSet()
        .get(0).getKeyDataPair().get(PRODUCT_TYPE).setData(productTypeDescription);

    IncludedSpsConsignmentItem includedSpsConsignmentItem = mapper.map(notification);

    String actualSpsConsignmentItem = objectMapper.writeValueAsString(includedSpsConsignmentItem);
    String expectedSpsConsignmentItem = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/includedSpsConsignmentItemProductTypeComplete.json");
    assertThat(actualSpsConsignmentItem).isEqualTo(expectedSpsConsignmentItem
        .replace(PRODUCT_TYPE_DESCRIPTION_VALUE_KEY, productTypeDescription)
        .replace(PRODUCT_TYPE_CODE_VALUE_KEY, productTypeCode));
  }
}
