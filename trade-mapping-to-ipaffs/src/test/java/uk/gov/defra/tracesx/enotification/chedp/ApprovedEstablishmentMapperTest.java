package uk.gov.defra.tracesx.enotification.chedp;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.tracesx.notificationschema.representation.ApprovedEstablishment;
import uk.gov.defra.tracesx.trade.dto.AppliedSpsProcess;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.ProcessTypeCodeType;
import uk.gov.defra.tracesx.trade.dto.RoleCode;
import uk.gov.defra.tracesx.trade.dto.RoleCode.Value;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;
import uk.gov.defra.tracesx.trade.dto.TextType;

class ApprovedEstablishmentMapperTest {

  private static final String SH = "SH";
  private static final String NZ = "NZ";
  private static final String ME_26 = "ME26";
  private static final String SILVER_FERN_FARMS_LIMITED_PAREORA =
      "Silver Fern Farms Limited - Pareora";
  private static final String MEAT_OF_DOMESTIC_UNGULATES = "Meat of domestic ungulates";
  private static final String SLAUGHTERHOUSES = "Slaughterhouses";

  private final ApprovedEstablishmentMapper mapper = new ApprovedEstablishmentMapper();

  @Test
  void map_ReturnsCorrectApprovedEstablishments_WhenMapped() {
    // Given
    AppliedSpsProcess appliedSpsProcess =
        new AppliedSpsProcess()
            .withOperatorSpsParty(createSpsPartyType())
            .withOperationSpsCountry(createSpsCountryType())
            .withTypeCode(new ProcessTypeCodeType().withName(SLAUGHTERHOUSES).withValue(SH));

    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        new IncludedSpsTradeLineItem().withAppliedSpsProcess(List.of(appliedSpsProcess));
    List<IncludedSpsConsignmentItem> includedSpsConsignmentItems =
        List.of(
            new IncludedSpsConsignmentItem()
                .withIncludedSpsTradeLineItem(List.of(includedSpsTradeLineItem)));

    // When
    List<ApprovedEstablishment> approvedEstablishments = mapper.map(includedSpsConsignmentItems);

    // Then
    ApprovedEstablishment expected =
        ApprovedEstablishment.builder()
            .name(SILVER_FERN_FARMS_LIMITED_PAREORA)
            .country(NZ)
            .types(List.of(SLAUGHTERHOUSES))
            .approvalNumber(ME_26)
            .section(MEAT_OF_DOMESTIC_UNGULATES)
            .build();

    assertThat(approvedEstablishments).hasSize(1).containsExactly(expected);
  }

  private SpsPartyType createSpsPartyType() {
    return new SpsPartyType()
        .withId(new IDType().withValue(ME_26))
        .withName(new TextType().withValue(SILVER_FERN_FARMS_LIMITED_PAREORA))
        .withRoleCode(new RoleCode().withName(MEAT_OF_DOMESTIC_UNGULATES).withValue(Value.ZZZ));
  }

  private SpsCountryType createSpsCountryType() {
    return new SpsCountryType()
        .withId(new IDType().withValue(NZ))
        .withName(List.of(new TextType().withValue(NZ)));
  }
}
