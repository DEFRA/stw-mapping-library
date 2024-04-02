package uk.gov.defra.stw.mapping.toipaffs.chedd;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.INTERNAL_MARKET;
import static uk.gov.defra.stw.mapping.toipaffs.enumeration.Purpose.TRANSHIPMENT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.IMPORT;
import static uk.gov.defra.tracesx.notificationschema.representation.enumeration.PurposeGroupEnum.TRANSHIPMENT_TO;

import java.util.List;

import org.junit.jupiter.api.Test;

import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsCountrySubDivisionType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.dto.TextType;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;
import uk.gov.defra.tracesx.notificationschema.representation.Purpose;

public class CheddPurposeMapperTest {
  private final CheddPurposeMapper cheddPurposeMapper = new CheddPurposeMapper();

  @Test
  void map_returnsFinalBip_whenTranshipment() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(
                    List.of(new IncludedSpsClause()
                        .withId(
                            new IDType().withValue("PURPOSE"))
                        .withContent(
                            List.of(new TextType().withValue(TRANSHIPMENT.toString()))))))))
        .withSpsConsignment(
            new SpsConsignment()
                .withTransitSpsCountry(
                    List.of(new SpsCountryType()
                        .withSubordinateSpsCountrySubDivision(
                            List.of(
                                new SpsCountrySubDivisionType()
                                    .withActivityAuthorizedSpsParty(
                                        List.of(new SpsPartyType()
                                            .withId(new IDType().withValue("TEST")))))))));

    Purpose purpose = cheddPurposeMapper.map(spsCertificate);
    assertThat(purpose.getFinalBIP()).isEqualTo("TEST");
    assertThat(purpose.getPurposeGroup()).isEqualTo(TRANSHIPMENT_TO);
  }

  @Test
  void map_returnsPurposeGroup_whenInternalMarket() throws NotificationMapperException {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withSignatorySpsAuthentication(List.of(new SpsAuthenticationType()
                .withIncludedSpsClause(
                    List.of(new IncludedSpsClause()
                        .withId(
                            new IDType().withValue("PURPOSE"))
                        .withContent(
                            List.of(new TextType().withValue(INTERNAL_MARKET.toString()))))))));

    Purpose purpose = cheddPurposeMapper.map(spsCertificate);
    assertThat(purpose.getPurposeGroup()).isEqualTo(IMPORT);
  }

}
