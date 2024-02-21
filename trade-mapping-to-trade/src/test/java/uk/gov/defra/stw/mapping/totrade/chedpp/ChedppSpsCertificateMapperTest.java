package uk.gov.defra.stw.mapping.totrade.chedpp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.defra.stw.mapping.dto.SpsCertificate;
import uk.gov.defra.stw.mapping.dto.SpsConsignment;
import uk.gov.defra.stw.mapping.dto.SpsExchangedDocument;
import uk.gov.defra.stw.mapping.totrade.chedpp.spsconsignment.ChedppSpsConsignmentMapper;
import uk.gov.defra.stw.mapping.totrade.chedpp.spsexchangeddocument.ChedppSpsExchangedDocumentMapper;
import uk.gov.defra.stw.mapping.totrade.testutils.ResourceUtil;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;

@ExtendWith(MockitoExtension.class)
class ChedppSpsCertificateMapperTest {

  private ObjectMapper objectMapper;

  @Mock
  private ChedppSpsConsignmentMapper consignmentMapper;
  @Mock
  private ChedppSpsExchangedDocumentMapper exchangedDocumentMapper;

  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void map_ReturnsSpsCertificate_WhenComplete() throws Exception {
    String notificationString = ResourceUtil.readFileToString("classpath:validatedCHEDPP.json");
    Notification notification = objectMapper.readValue(notificationString, Notification.class);

    String spsConsignmentString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsconsignment/spsConsignmentComplete.json");
    SpsConsignment spsConsignment = objectMapper.readValue(spsConsignmentString, SpsConsignment.class);

    when(consignmentMapper.map(notification)).thenReturn(spsConsignment);

    String spsExchangedDocumentString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsexchangeddocument/spsExchangedDocumentComplete.json");
    SpsExchangedDocument spsExchangedDocument = objectMapper.readValue(spsExchangedDocumentString, SpsExchangedDocument.class);

    when(exchangedDocumentMapper.map(notification)).thenReturn(spsExchangedDocument);

    SpsCertificate actualSpsCertificate = new ChedppSpsCertificateMapper(consignmentMapper, exchangedDocumentMapper).map(notification);

    String expectedSpsCertificateString = ResourceUtil.readFileToString("classpath:mapping/chedpp/spsCertificate.json");
    SpsCertificate expectedSpsCertificate = objectMapper.readValue(expectedSpsCertificateString, SpsCertificate.class);

    assertThat(actualSpsCertificate).isEqualTo(expectedSpsCertificate);
  }
}
