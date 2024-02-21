package uk.gov.defra.stw.mapping.toipaffs.testutils;

import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.GOODS_CERTIFIED_AS;
import static uk.gov.defra.stw.mapping.toipaffs.testutils.TestConstants.PURPOSE;

import java.util.Collections;
import java.util.List;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsClause;
import uk.gov.defra.stw.mapping.dto.SpsAuthenticationType;
import uk.gov.defra.stw.mapping.dto.SpsCountryType;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.SpsPartyType;
import uk.gov.defra.stw.mapping.dto.TextType;

public class TestHelper {

  public static SpsNoteType buildSpsNoteType(String contentValue, String subjectCodeValue) {
    return new SpsNoteType()
        .withContent(Collections.singletonList(new TextType().withValue(contentValue)))
        .withSubjectCode(new CodeType().withValue(subjectCodeValue));
  }

  public static SpsAuthenticationType buildSpsAuthenticationTypeForPurpose(String purpose) {
    return new SpsAuthenticationType()
        .withIncludedSpsClause(
            Collections.singletonList(
                new IncludedSpsClause()
                    .withId(new IDType().withValue(PURPOSE))
                    .withContent(Collections.singletonList(new TextType().withValue(purpose)))));
  }

  public static SpsAuthenticationType buildSpsAuthenticationTypeForGoodsCertifiedAs(String certifiedAs) {
    return new SpsAuthenticationType()
        .withIncludedSpsClause(
            List.of(
                new IncludedSpsClause()
                    .withId(new IDType().withValue(GOODS_CERTIFIED_AS))
                    .withContent(List.of(new TextType().withValue(certifiedAs)))));
  }

  public static SpsCountryType buildSpsCountryType(String id, String name) {
    return new SpsCountryType()
        .withId(new IDType().withValue(id))
        .withName(List.of(new TextType().withValue(name)));
  }

  public static SpsPartyType buildSpsPartyType(String id, String name) {
    return new SpsPartyType()
        .withId(new IDType().withValue(id))
        .withName(new TextType().withValue(name));
  }
}
