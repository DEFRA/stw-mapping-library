package uk.gov.defra.tracesx.cloning.cheda;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.Route;
import uk.gov.defra.tracesx.testutils.JsonDeserializer;
import uk.gov.defra.tracesx.testutils.TestUtils;
import uk.gov.defra.tracesx.trade.dto.SpsCertificate;

class ChedaRouteMapperTest {

    private SpsCertificate spsCertificate;
    private ChedaRouteMapper mapper;

    @BeforeEach
    void setup() throws JsonProcessingException {
        mapper = new ChedaRouteMapper();
        spsCertificate = JsonDeserializer.get(
            SpsCertificate.class, "cloning/cheda/cheda_trade_all_fields.json", TestUtils.initObjectMapper()
        );
    }

    @Test
    void map_ReturnsNull_WhenPurposeIsNotInternalMarket() {
        spsCertificate.getSpsExchangedDocument()
            .getSignatorySpsAuthentication().get(0)
            .getIncludedSpsClause().get(0).getContent().get(0).setValue("DIRECT_TRANSIT");

        Route actualRoute = mapper.map(spsCertificate);

        assertThat(actualRoute).isNull();
    }

    @Test
    void map_ReturnsNull_WhenPurposeInternalMarketWithNoTransitCountries() {
        spsCertificate.getSpsConsignment().setTransitSpsCountry(List.of());

        Route actualRoute = mapper.map(spsCertificate);

        assertThat(actualRoute).isNull();
    }

    @Test
    void map_ReturnsNull_WhenNoIdInIncludedSpsClause() {
        spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication().get(0)
            .getIncludedSpsClause().get(0).setId(null);

        Route actualRoute = mapper.map(spsCertificate);

        assertThat(actualRoute).isNull();
    }

    @Test
    void map_ReturnsNull_WhenNoPurposeInIncludedSpsClause() {
        spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication().get(0)
            .getIncludedSpsClause().get(0).getId().setValue("TEST");

        Route actualRoute = mapper.map(spsCertificate);

        assertThat(actualRoute).isNull();
    }

    @Test
    void map_ReturnsRouteWithTransitingStates_WhenPurposeInternalMarketWithTransitingCountries() {
        Route expectedRoute = Route.builder()
                .transitingStates(List.of("JO", "PL"))
                .build();

        Route actualRoute = mapper.map(spsCertificate);

        assertThat(actualRoute).isEqualTo(expectedRoute);
    }
}
