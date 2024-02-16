package uk.gov.defra.stw.mapping.toipaffs.chedp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.ComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;

class ChedpComplementParameterSetHelperTest {

  private ChedpComplementParameterSetHelper mapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;
  private MockedStatic<UUID> mockedUuid;

  @BeforeEach
  void setup() throws JsonProcessingException {
    mapper = new ChedpComplementParameterSetHelper(
        new ComplementParameterSetMapper(),
        new NetWeightMeasureKeyDataMapper(),
        new NumberOfPackagesKeyDataMapper(),
        new ChedpPackageTypeKeyDataMapper());
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "chedp/chedp_ehc_complete.json", objectMapper);

    UUID uuid1 = UUID.fromString("0d1be0bc-a7c9-430e-8b8c-5f18d83f687d");
    UUID uuid2 = UUID.fromString("1e364d17-5ba6-49e7-953f-338b9792557b");
    mockedUuid = Mockito.mockStatic(UUID.class);
    mockedUuid.when(UUID::randomUUID).thenReturn(uuid1, uuid2);
  }

  @AfterEach
  void teardown() {
    mockedUuid.close();
  }

  @Test
  void map_ReturnsComplementParameterSet_WhenComplete() throws JsonProcessingException {
    List<ComplementParameterSet> complementParameterSet =
        mapper.map(spsCertificate.getSpsConsignment().getIncludedSpsConsignmentItem());
    String actualComplementParameterSet = objectMapper.writeValueAsString(complementParameterSet);

    String expectedComplementParameterSet = ResourceUtils
        .readFileToString("classpath:chedp/partone/commodities/chedp_ipaffs_complementParameterSet_complete.json");
    assertThat(actualComplementParameterSet).isEqualTo(expectedComplementParameterSet);
  }
}
