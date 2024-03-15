package uk.gov.defra.stw.mapping.toipaffs.chedpp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.SpsLocationType;
import uk.gov.defra.stw.mapping.dto.TextType;

class ChedppPointOfEntryControlPointMapperTest {

  private final ChedppPointOfEntryControlPointMapper mapper = new ChedppPointOfEntryControlPointMapper();

  @Test
  void map_ReturnsControlPoint_WhenPresent() {
    SpsLocationType spsLocationType = new SpsLocationType()
        .withName(List.of(new TextType()
            .withValue("Control point")));

    String actual = mapper.map(spsLocationType);

    assertThat(actual).isEqualTo("Control point");
  }

  @Test
  void map_ReturnsNull_WhenNameIsBlank() {
    SpsLocationType spsLocationType = new SpsLocationType()
        .withName(List.of(new TextType()
            .withValue(" ")));

    String actual = mapper.map(spsLocationType);

    assertThat(actual).isNull();
  }

  @Test
  void map_ReturnsNull_WhenNameIsEmpty() {
    SpsLocationType spsLocationType = new SpsLocationType()
        .withName(List.of(new TextType()
            .withValue("")));

    String actual = mapper.map(spsLocationType);

    assertThat(actual).isNull();
  }
}
