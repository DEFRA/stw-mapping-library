package uk.gov.defra.stw.mapping.toipaffs.cloning.cheda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSIT_TO_3RD_COUNTRY;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

@ExtendWith(MockitoExtension.class)
class ChedaPurposeMapperTest {
    private SpsCertificate spsCertificate;

    @Mock
    private InternalMarketPurposeMapper internalMarketPurposeMapper;

    @InjectMocks
    private ChedaPurposeMapper mapper;

    @BeforeEach
    void setup() throws JsonProcessingException {
        ObjectMapper objectMapper = TestUtils.initObjectMapper();
        spsCertificate = JsonDeserializer
                .get(SpsCertificate.class, "cloning/cheda/cheda_trade_all_fields.json",
                    objectMapper);
    }

    @Test
    void map_ReturnsPurposeTransitToThirdCountry_WhenPurposeDirectTransit() throws NotificationMapperException {
        spsCertificate.getSpsExchangedDocument()
            .getSignatorySpsAuthentication().get(0)
            .getIncludedSpsClause().get(0).getContent().get(0).setValue("DIRECT_TRANSIT");
        spsCertificate.getSpsConsignment().getImportSpsCountry().getId().setValue("AF");
        spsCertificate.getSpsConsignment().getImportSpsCountry().getName().get(0).setValue("Afghanistan");
        Purpose expectedPurpose = Purpose.builder()
                .purposeGroup(TRANSIT_TO_3RD_COUNTRY)
                .transitThirdCountries(List.of("JO", "PL"))
                .thirdCountry("AF")
                .build();

        Purpose actualPurpose = mapper.map(spsCertificate);

        assertThat(actualPurpose).isEqualTo(expectedPurpose);
    }

    @Test
    void map_CallsInternalMarketPurposeMapper_WhenPurposeNotDirectTransit() throws NotificationMapperException {
        mapper.map(spsCertificate);

        verify(internalMarketPurposeMapper, times(1)).map(spsCertificate);
    }

    @Test
    void map_CallsInternalMarketPurposeMapper_WhenNoIdInIncludedSpsClause()
        throws NotificationMapperException {
        spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication().get(0)
            .getIncludedSpsClause().get(0).setId(null);

        mapper.map(spsCertificate);

        verify(internalMarketPurposeMapper, times(1)).map(spsCertificate);
    }

    @Test
    void map_CallsInternalMarketPurposeMapper_WhenNoPurposeInIncludedSpsClause()
        throws NotificationMapperException {
        spsCertificate.getSpsExchangedDocument().getSignatorySpsAuthentication().get(0)
            .getIncludedSpsClause().get(0).getId().setValue("TEST");

        mapper.map(spsCertificate);

        verify(internalMarketPurposeMapper, times(1)).map(spsCertificate);
    }
}
