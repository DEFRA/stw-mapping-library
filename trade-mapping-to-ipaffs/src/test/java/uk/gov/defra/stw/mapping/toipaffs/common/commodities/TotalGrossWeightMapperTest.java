package uk.gov.defra.stw.mapping.toipaffs.common.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;

class TotalGrossWeightMapperTest {

  private final ObjectMapper objectMapper = TestUtils.initObjectMapper();

  private TotalGrossWeightMapper totalGrossWeightMapper;

  @BeforeEach
  void setup() throws JsonProcessingException {
    totalGrossWeightMapper = new TotalGrossWeightMapper();
  }

  @Test
  void map_ReturnsTotalGrossWeight_WhenSingleGrossWeight() throws JsonProcessingException {
    String json = """
        {
          "spsConsignment": {
            "includedSpsConsignmentItem": [
              {
                "includedSpsTradeLineItem": [
                  {
                    "grossWeightMeasure": {
                      "value": "1"
                    }
                  }
                ]
              }
            ]
          }
        }""";
    SpsCertificate spsCertificate = objectMapper.readValue(json, SpsCertificate.class);

    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(BigDecimal.valueOf(1.0));
  }

  @Test
  void map_ReturnsTotalGrossWeight_WhenMultipleGrossWeights() throws JsonProcessingException {
    String json = """
        {
          "spsConsignment": {
            "includedSpsConsignmentItem": [
              {
                "includedSpsTradeLineItem": [
                  {
                    "grossWeightMeasure": {
                      "value": "1"
                    }
                  },
                  {
                    "grossWeightMeasure": {
                      "value": "2"
                    }
                  }
                ]
              }
            ]
          }
        }""";
    SpsCertificate spsCertificate = objectMapper.readValue(json, SpsCertificate.class);

    BigDecimal actual = totalGrossWeightMapper.map(spsCertificate);

    assertThat(actual).isEqualTo(BigDecimal.valueOf(3.0));
  }
}
