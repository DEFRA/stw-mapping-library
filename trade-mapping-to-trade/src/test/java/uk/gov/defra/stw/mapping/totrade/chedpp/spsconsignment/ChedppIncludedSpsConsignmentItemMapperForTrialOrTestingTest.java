package uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.totrade.common.spsconsignment.SpsPartyMapper;
import uk.gov.defra.tracesx.notificationschema.representation.Commodities;
import uk.gov.defra.tracesx.notificationschema.representation.CommodityComplement;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.PartOne;

class ChedppIncludedSpsConsignmentItemMapperForTrialOrTestingTest {

    private Notification notification;
    private ChedppIncludedSpsConsignmentItemMapper mapper;

    private static final String FOR_TRIAL_OR_TESTING_KEY = "for_test_and_trial";
    private static final String FOR_TRIAL_OR_TESTING_SUBJECT_CODE = "FOR_TRIAL_OR_TESTING";

    @BeforeEach
    void setup() throws Exception {
        notification = Notification.builder()
            .partOne(PartOne.builder()
                .commodities(Commodities.builder()
                    .commodityComplement(List.of(
                        CommodityComplement.builder()
                            .complementID(123)
                            .speciesID("345")
                            .build())
                    )
                    .complementParameterSet(List.of(
                        ComplementParameterSet.builder()
                            .complementID(123)
                            .speciesID("345")
                            .build())
                    )
                    .build())
                .build())
            .build();

        mapper = new ChedppIncludedSpsConsignmentItemMapper(new SpsPartyMapper());
    }

    @ParameterizedTest
    @ValueSource(strings = {"true", "false"})
    void map_ReturnsSPSConsignmentItem_whenTrialOrTestingIsSet(String value) {
        // Given
        ComplementParameterSetKeyDataPair keyDataPair = ComplementParameterSetKeyDataPair.builder()
            .key(FOR_TRIAL_OR_TESTING_KEY)
            .data(value)
            .build();

        notification.getPartOne().getCommodities().getComplementParameterSet().get(0)
            .addKeyDataPairItem(keyDataPair);

        // When
        IncludedSpsConsignmentItem consignmentItem = mapper.map(notification);
        IncludedSpsTradeLineItem tradeLineItem = consignmentItem.getIncludedSpsTradeLineItem().get(1);

        // Then
        assertThat(tradeLineItem.getAdditionalInformationSpsNote()).hasSize(1)
            .first()
            .isEqualTo(new SpsNoteType()
                .withContent(List.of(new TextType().withValue(value)))
                .withSubjectCode(new CodeType().withValue(FOR_TRIAL_OR_TESTING_SUBJECT_CODE))
            );
    }

    @Test
    void map_ReturnsSPSConsignmentItem_whenTrialOrTestingIsEmpty() {
        // When
        IncludedSpsConsignmentItem consignmentItem = mapper.map(notification);

        // Then
        IncludedSpsTradeLineItem tradeLineItem = consignmentItem.getIncludedSpsTradeLineItem().get(1);
        assertThat(tradeLineItem.getAdditionalInformationSpsNote()).isEmpty();
    }
}
