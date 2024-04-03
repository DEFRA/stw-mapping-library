package uk.gov.defra.stw.mapping.toipaffs.chedd.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityIntention;

class CommodityIntendedForMapperTest {
  @ParameterizedTest
  @CsvSource({
      "FEEDING_STUFF, FEEDINGSTUFF",
      "OTHER, OTHER",
      "FURTHER_PROCESS, FURTHER",
      "HUMAN_CONSUMPTION, HUMAN"
  })

  void map_correctCommodityIntention(String content, CommodityIntention expected) {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(List.of(new IncludedSpsClause()
                    .withId(new IDType().withValue("GOODS_CERTIFIED_AS"))
                    .withContent(List.of(new TextType().withValue(content))))))));

    CommodityIntendedForMapper commodityIntendedForMapper = new CommodityIntendedForMapper();
    CommodityIntention commodityIntention = commodityIntendedForMapper.map(spsCertificate);

    assertThat(commodityIntention).isEqualTo(expected);
  }
}
