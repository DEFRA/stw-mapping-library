package uk.gov.defra.stw.mapping.toipaffs.chedd.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.CommodityIntention;

class CommodityIntendedForMapperTest {

  private final CommodityIntendedForMapper commodityIntendedForMapper = new CommodityIntendedForMapper();
  private SpsCertificate spsCertificate;
  private CommodityIntention commodityIntention;

  @Test
  void map_commodityIntention_WhenHumanConsumption() {
    setupSpsCertificate("HUMAN_CONSUMPTION");
    assertThat(commodityIntention).isEqualTo(CommodityIntention.HUMAN);
  }

  @Test
  void map_commodityIntention_WhenFeedingStuff() {
    setupSpsCertificate("FEEDING_STUFF");
    assertThat(commodityIntention).isEqualTo(CommodityIntention.FEEDINGSTUFF);
  }

  @Test
  void map_commodityIntention_WhenOther() {
    setupSpsCertificate("OTHER");
    assertThat(commodityIntention).isEqualTo(CommodityIntention.OTHER);
  }

  @Test
  void map_commodityIntention_WhenFurtherProcess() {
    setupSpsCertificate("FURTHER_PROCESS");
    assertThat(commodityIntention).isEqualTo(CommodityIntention.FURTHER);
  }

  @Test
  void map_commodityIntention_WhenNull() {
    setupSpsCertificate("");
    assertThat(commodityIntention).isEqualTo(null);
  }



  private void setupSpsCertificate(String content) {
    spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(List.of(new IncludedSpsClause()
                    .withId(new IDType().withValue("GOODS_CERTIFIED_AS"))
                    .withContent(List.of(new TextType().withValue(content))))))));
    commodityIntention = commodityIntendedForMapper.map(spsCertificate);
  }

}
