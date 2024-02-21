package uk.gov.defra.stw.mapping.toipaffs.common.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.defra.stw.mapping.dto.IDType;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

class ReferenceNumberMapperTest {

  private ReferenceNumberMapper referenceNumberMapper;
  private SpsCertificate spsCertificate;
  private SpsConsignment spsConsignment;

  @BeforeEach
  void setup() {
    spsCertificate = new SpsCertificate();
    spsConsignment = new SpsConsignment();
    spsCertificate.setSpsConsignment(spsConsignment);
    referenceNumberMapper = new ReferenceNumberMapper();
  }

  @Test
  void map_ReturnsReferenceNumber_WhenIdExists() throws NotificationMapperException {
    spsConsignment.setId(new IDType().withValue("DRAFT.GB.2022.1234567"));

    assertThat(referenceNumberMapper.map(spsCertificate)).isEqualTo("DRAFT.GB.2022.1234567");
  }

  @Test
  void map_ReturnsNull_WhenIdDoesNotExist() throws NotificationMapperException {
    assertThat(referenceNumberMapper.map(spsCertificate)).isNull();
  }

  @Test
  void map_ReturnsNull_WhenIdHasNullValue() throws NotificationMapperException {
    spsConsignment.setId(new IDType().withValue(null));

    assertThat(referenceNumberMapper.map(spsCertificate)).isNull();
  }
}
