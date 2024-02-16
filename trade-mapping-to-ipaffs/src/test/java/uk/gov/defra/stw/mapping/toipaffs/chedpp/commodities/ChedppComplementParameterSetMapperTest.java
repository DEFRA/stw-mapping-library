package uk.gov.defra.stw.mapping.toipaffs.chedpp.commodities;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import uk.gov.defra.stw.mapping.dto.CodeType;
import uk.gov.defra.stw.mapping.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsNoteType;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.ComplementParameterSetMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NetWeightMeasureKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.common.commodities.NumberOfPackagesKeyDataMapper;
import uk.gov.defra.stw.mapping.toipaffs.testutils.JsonDeserializer;
import uk.gov.defra.stw.mapping.toipaffs.testutils.ResourceUtils;
import uk.gov.defra.stw.mapping.toipaffs.testutils.TestUtils;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSet;
import uk.gov.defra.tracesx.notificationschema.representation.ComplementParameterSetKeyDataPair;

class ChedppComplementParameterSetMapperTest {

  private ChedppComplementParameterSetMapper mapper;
  private ObjectMapper objectMapper;
  private SpsCertificate spsCertificate;
  private MockedStatic<UUID> mockedUuid;

  @BeforeEach
  void setup() throws JsonProcessingException {
    ChedppQuantityTypeMapper chedppQuantityTypeMapper = new ChedppQuantityTypeMapper();

    mapper = new ChedppComplementParameterSetMapper(
        new ComplementParameterSetMapper(),
        chedppQuantityTypeMapper,
        new ChedppQuantityMapper(),
        new NetWeightMeasureKeyDataMapper(),
        new NumberOfPackagesKeyDataMapper(),
        new ChedppPackageTypeMapper(),
        new ChedppControlledAtmosphereContainerMapper(),
        new FinishedOrPropagatedMapper(),
        new VarietyMapper(),
        new ClassMapper(),
        new ChedppSequenceNumericMapper());
    objectMapper = TestUtils.initObjectMapper();

    spsCertificate = JsonDeserializer
        .get(SpsCertificate.class, "chedpp/chedpp_ehc_complete.json", objectMapper);

    UUID uuid = UUID.fromString("3f8bd1d2-199c-447f-955e-c5a5a9160c95");
    mockedUuid = Mockito.mockStatic(UUID.class);
    mockedUuid.when(UUID::randomUUID).thenReturn(uuid);
  }

  @AfterEach
  void teardown() {
    mockedUuid.close();
  }

  @Test
  void map_ReturnsComplementParameterSet_WhenComplete() throws JsonProcessingException {
    List<ComplementParameterSet> complementParameterSet = mapper.map(spsCertificate);
    String actualComplementParameterSet = objectMapper.writeValueAsString(complementParameterSet);

    String expectedComplementParameterSet = ResourceUtils
        .readFileToString("classpath:chedpp/partone/commodities/chedpp_ipaffs_complementParameterSet_complete.json");
    assertThat(complementParameterSet.get(0).getKeyDataPair()).hasSize(10);
    assertThat(actualComplementParameterSet).isEqualTo(expectedComplementParameterSet);
  }

  @Test
  void map_ReturnsComplementParameterSet_WhenFinishedOrPropagatedMapperReturnsNull() {
    spsCertificate
        .getSpsConsignment()
        .getIncludedSpsConsignmentItem()
        .get(0)
        .getIncludedSpsTradeLineItem()
        .get(0)
        .setAdditionalInformationSpsNote(Collections.emptyList());

    List<ComplementParameterSet> complementParameterSet = mapper.map(spsCertificate);

    assertThat(findByKey(complementParameterSet.get(0).getKeyDataPair(), "finished_or_propagated"))
        .isNull();
  }

  @Test
  void map_ReturnsComplementParameterSetWithoutVariety_WhenInvalidSubjectCode() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        spsCertificate
            .getSpsConsignment()
            .getIncludedSpsConsignmentItem()
            .get(0)
            .getIncludedSpsTradeLineItem()
            .get(0);
    SpsNoteType spsNoteType =
        findSpsNoteBySubjectCode(
            includedSpsTradeLineItem.getAdditionalInformationSpsNote(), "variety");
    spsNoteType.setSubjectCode(new CodeType().withValue("Invalid"));

    List<ComplementParameterSet> complementParameterSet = mapper.map(spsCertificate);

    assertThat(findByKey(complementParameterSet.get(0).getKeyDataPair(), "variety")).isNull();
  }

  @Test
  void map_ReturnsComplementParameterSetWithoutClass_WhenInvalidSubjectCode() {
    IncludedSpsTradeLineItem includedSpsTradeLineItem =
        spsCertificate
            .getSpsConsignment()
            .getIncludedSpsConsignmentItem()
            .get(0)
            .getIncludedSpsTradeLineItem()
            .get(0);
    SpsNoteType spsNoteType =
        findSpsNoteBySubjectCode(
            includedSpsTradeLineItem.getAdditionalInformationSpsNote(), "class");
    spsNoteType.setSubjectCode(new CodeType().withValue("Invalid"));

    List<ComplementParameterSet> complementParameterSet = mapper.map(spsCertificate);

    assertThat(findByKey(complementParameterSet.get(0).getKeyDataPair(), "class")).isNull();
  }

  private ComplementParameterSetKeyDataPair findByKey(List<ComplementParameterSetKeyDataPair> keyDataPairs, String key) {
    return keyDataPairs.stream()
        .filter(pair -> pair.getKey().equals(key))
        .findFirst()
        .orElse(null);
  }

  private SpsNoteType findSpsNoteBySubjectCode(List<SpsNoteType> items, String code) {
    return items.stream()
        .filter(item -> item.getSubjectCode().getValue().equals(code))
        .findFirst()
        .orElse(null);
  }
}
