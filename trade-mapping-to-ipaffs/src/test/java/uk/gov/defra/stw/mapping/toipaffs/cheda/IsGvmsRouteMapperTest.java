package uk.gov.defra.stw.mapping.toipaffs.cheda;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.dto.TextType;

class IsGvmsRouteMapperTest {

  private final IsGvmsRouteMapper mapper = new IsGvmsRouteMapper();

  @Test
  void map_ReturnsTrue_WhenTrue() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("IS_GVMS_ROUTE"))
                .withContent(List.of(new TextType().withValue("TRUE")))
            ))
        );
    Boolean actual = mapper.map(spsCertificate);

    assertThat(actual).isTrue();
  }

  @Test
  void map_ReturnsFalse_WhenFalse() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of(new SpsNoteType()
                .withSubjectCode(new CodeType().withValue("IS_GVMS_ROUTE"))
                .withContent(List.of(new TextType().withValue("FALSE")))
            ))
        );

    Boolean actual = mapper.map(spsCertificate);

    assertThat(actual).isFalse();
  }

  @Test
  void map_ReturnsNull_WhenNoteIsMissing() {
    SpsCertificate spsCertificate = new SpsCertificate()
        .withSpsExchangedDocument(new SpsExchangedDocument()
            .withIncludedSpsNote(List.of())
        );

    Boolean actual = mapper.map(spsCertificate);

    assertThat(actual).isNull();
  }
}
