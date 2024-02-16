package uk.gov.defra.stw.mapping.validation.rules.common.partone;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import uk.gov.defra.stw.mapping.validation.rules.TradeValidationRule;
import uk.gov.defra.tracesx.notificationschema.representation.AccompanyingDocument;
import uk.gov.defra.tracesx.notificationschema.representation.Notification;
import uk.gov.defra.tracesx.notificationschema.representation.VeterinaryInformation;
import uk.gov.defra.tracesx.notificationschema.representation.enumeration.DocumentType;

public class NotificationVeterinaryInformationValidator implements TradeValidationRule {

  private static final String VALIDATION_MESSAGE = "Invalid veterinary information";

  private final Set<DocumentType> documentTypeSet;

  public NotificationVeterinaryInformationValidator() {
    this.documentTypeSet
        = Set.of(DocumentType.AIR_WAYBILL,
        DocumentType.BILL_OF_LADING,
        DocumentType.CATCH_CERTIFICATE,
        DocumentType.COMMERCIAL_INVOICE,
        DocumentType.CUSTOMS_DECLARATION,
        DocumentType.HEALTH_CERTIFICATE,
        DocumentType.IMPORT_PERMIT,
        DocumentType.LABORATORY_SAMPLING_RESULTS_FOR_AFLATOXIN,
        DocumentType.LETTER_OF_AUTHORITY,
        DocumentType.OTHER,
        DocumentType.RAILWAY_BILL,
        DocumentType.SEA_WAYBILL,
        DocumentType.VETERINARY_HEALTH_CERTIFICATE,
        DocumentType.CARGO_MANIFEST,
        DocumentType.INSPECTION_CERTIFICATE,
        DocumentType.PHYTOSANITARY_CERTIFICATE,
        DocumentType.ORIGIN_CERTIFICATE,
        DocumentType.HEAT_TREATMENT_CERTIFICATE,
        DocumentType.CONTAINER_MANIFEST,
        DocumentType.CONFORMITY_CERTIFICATE
    );
  }

  @Override
  public boolean validate(Notification notification) {
    VeterinaryInformation veterinaryInformation
        = notification.getPartOne().getVeterinaryInformation();

    if (veterinaryInformation == null) {
      return true;
    }

    if (!isValid(veterinaryInformation.getVeterinaryDocumentIssueDate())) {
      return false;
    }

    if (veterinaryInformation.getAccompanyingDocuments() != null) {
      for (AccompanyingDocument accompanyingDocument
          : veterinaryInformation.getAccompanyingDocuments()) {
        if (StringUtils.isEmpty(accompanyingDocument.getDocumentReference())
            || !isValidDocumentType(accompanyingDocument.getDocumentType())) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public String getMessage() {
    return VALIDATION_MESSAGE;
  }

  private boolean isValidDocumentType(DocumentType documentType) {
    return documentType != null && documentTypeSet.contains(documentType);
  }

  private boolean isValid(String dateTimeString) {
    if (dateTimeString == null) {
      return true;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      formatter.parse(dateTimeString);
    } catch (DateTimeParseException dateTimeParseException) {
      return false;
    }
    return true;
  }
}
