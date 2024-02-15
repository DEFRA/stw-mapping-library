package uk.gov.defra.tracesx.trade.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpsExchangedDocument implements Serializable {

  @Serial
  private final static long serialVersionUID = -1352954964996998885L;
  private IndicatorType copyIndicator;
  private List<TextType> description = new ArrayList<>();
  /**
   * (Required)
   */
  private IDType id;
  private List<SpsNoteType> includedSpsNote = new ArrayList<>();
  /**
   * (Required)
   */
  private DateTimeType issueDateTime;
  private SpsPartyType issuerSpsParty;
  /**
   * (Required)
   */
  private List<TextType> name = new ArrayList<>();
  private List<SpsPartyType> recipientSpsParty = new ArrayList<>();
  private List<SpsReferencedDocumentType> referenceSpsReferencedDocument = new ArrayList<>();
  /**
   * (Required)
   */
  private List<SpsAuthenticationType> signatorySpsAuthentication = new ArrayList<>();
  /**
   * (Required)
   */
  private StatusCode statusCode;
  /**
   * (Required)
   */
  private DocumentCodeType typeCode;

  private SubmittedBy submittedBy;

  /**
   * No args constructor for use in serialization
   */
  public SpsExchangedDocument() {
  }

  /**
   * @param name
   * @param issueDateTime
   * @param id
   * @param signatorySpsAuthentication
   * @param statusCode
   * @param typeCode
   */
  public SpsExchangedDocument(IDType id, DateTimeType issueDateTime, List<TextType> name,
      List<SpsAuthenticationType> signatorySpsAuthentication, StatusCode statusCode,
      DocumentCodeType typeCode, SubmittedBy submittedBy) {
    super();
    this.id = id;
    this.issueDateTime = issueDateTime;
    this.name = name;
    this.signatorySpsAuthentication = signatorySpsAuthentication;
    this.statusCode = statusCode;
    this.typeCode = typeCode;
    this.submittedBy = submittedBy;
  }

  public IndicatorType getCopyIndicator() {
    return copyIndicator;
  }

  public void setCopyIndicator(IndicatorType copyIndicator) {
    this.copyIndicator = copyIndicator;
  }

  public SpsExchangedDocument withCopyIndicator(IndicatorType copyIndicator) {
    this.copyIndicator = copyIndicator;
    return this;
  }

  public List<TextType> getDescription() {
    return description;
  }

  public void setDescription(List<TextType> description) {
    this.description = description;
  }

  public SpsExchangedDocument withDescription(List<TextType> description) {
    this.description = description;
    return this;
  }

  /**
   * (Required)
   */
  public IDType getId() {
    return id;
  }

  public void setId(IDType id) {
    this.id = id;
  }

  public SpsExchangedDocument withId(IDType id) {
    this.id = id;
    return this;
  }

  public List<SpsNoteType> getIncludedSpsNote() {
    return includedSpsNote;
  }

  public void setIncludedSpsNote(
      List<SpsNoteType> includedSpsNote) {
    this.includedSpsNote = includedSpsNote;
  }

  public SpsExchangedDocument withIncludedSpsNote(List<SpsNoteType> includedSpsNote) {
    this.includedSpsNote = includedSpsNote;
    return this;
  }

  /**
   * (Required)
   */
  public DateTimeType getIssueDateTime() {
    return issueDateTime;
  }

  public void setIssueDateTime(DateTimeType issueDateTime) {
    this.issueDateTime = issueDateTime;
  }

  public SpsExchangedDocument withIssueDateTime(DateTimeType issueDateTime) {
    this.issueDateTime = issueDateTime;
    return this;
  }

  public SpsPartyType getIssuerSpsParty() {
    return issuerSpsParty;
  }

  public void setIssuerSpsParty(SpsPartyType issuerSpsParty) {
    this.issuerSpsParty = issuerSpsParty;
  }

  public SpsExchangedDocument withIssuerSpsParty(SpsPartyType issuerSpsParty) {
    this.issuerSpsParty = issuerSpsParty;
    return this;
  }

  /**
   * (Required)
   */
  public List<TextType> getName() {
    return name;
  }

  public void setName(List<TextType> name) {
    this.name = name;
  }

  public SpsExchangedDocument withName(List<TextType> name) {
    this.name = name;
    return this;
  }

  public List<SpsPartyType> getRecipientSpsParty() {
    return recipientSpsParty;
  }

  public void setRecipientSpsParty(
      List<SpsPartyType> recipientSpsParty) {
    this.recipientSpsParty = recipientSpsParty;
  }

  public SpsExchangedDocument withRecipientSpsParty(List<SpsPartyType> recipientSpsParty) {
    this.recipientSpsParty = recipientSpsParty;
    return this;
  }

  public List<SpsReferencedDocumentType> getReferenceSpsReferencedDocument() {
    return referenceSpsReferencedDocument;
  }

  public void setReferenceSpsReferencedDocument(
      List<SpsReferencedDocumentType> referenceSpsReferencedDocument) {
    this.referenceSpsReferencedDocument = referenceSpsReferencedDocument;
  }

  public SpsExchangedDocument withReferenceSpsReferencedDocument(
      List<SpsReferencedDocumentType> referenceSpsReferencedDocument) {
    this.referenceSpsReferencedDocument = referenceSpsReferencedDocument;
    return this;
  }

  /**
   * (Required)
   */
  public List<SpsAuthenticationType> getSignatorySpsAuthentication() {
    return signatorySpsAuthentication;
  }

  public void setSignatorySpsAuthentication(
      List<SpsAuthenticationType> signatorySpsAuthentication) {
    this.signatorySpsAuthentication = signatorySpsAuthentication;
  }

  public SpsExchangedDocument withSignatorySpsAuthentication(
      List<SpsAuthenticationType> signatorySpsAuthentication) {
    this.signatorySpsAuthentication = signatorySpsAuthentication;
    return this;
  }

  /**
   * (Required)
   */
  public StatusCode getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(StatusCode statusCode) {
    this.statusCode = statusCode;
  }

  public SpsExchangedDocument withStatusCode(StatusCode statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  public SubmittedBy getSubmittedBy() {
    return submittedBy;
  }

  public SpsExchangedDocument withSubmittedBy(SubmittedBy submittedBy) {
    this.submittedBy = submittedBy;
    return this;
  }

  /**
   * (Required)
   */
  public DocumentCodeType getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(DocumentCodeType typeCode) {
    this.typeCode = typeCode;
  }

  public SpsExchangedDocument withTypeCode(DocumentCodeType typeCode) {
    this.typeCode = typeCode;
    return this;
  }

  @Override
  public String toString() {
    return "SpsExchangedDocument{" +
        "copyIndicator=" + copyIndicator +
        ", description=" + description +
        ", id=" + id +
        ", includedSpsNote=" + includedSpsNote +
        ", issueDateTime=" + issueDateTime +
        ", issuerSpsParty=" + issuerSpsParty +
        ", name=" + name +
        ", recipientSpsParty=" + recipientSpsParty +
        ", referenceSpsReferencedDocument=" + referenceSpsReferencedDocument +
        ", signatorySpsAuthentication=" + signatorySpsAuthentication +
        ", statusCode=" + statusCode +
        ", typeCode=" + typeCode +
        ", submittedBy=" + submittedBy +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpsExchangedDocument that = (SpsExchangedDocument) o;
    return Objects.equals(copyIndicator, that.copyIndicator) && Objects.equals(
        description, that.description) && Objects.equals(id, that.id)
        && Objects.equals(includedSpsNote, that.includedSpsNote)
        && Objects.equals(issueDateTime, that.issueDateTime) && Objects.equals(
        issuerSpsParty, that.issuerSpsParty) && Objects.equals(name, that.name)
        && Objects.equals(recipientSpsParty, that.recipientSpsParty)
        && Objects.equals(referenceSpsReferencedDocument,
        that.referenceSpsReferencedDocument) && Objects.equals(signatorySpsAuthentication,
        that.signatorySpsAuthentication) && Objects.equals(statusCode, that.statusCode)
        && Objects.equals(typeCode, that.typeCode) && Objects.equals(submittedBy,
        that.submittedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(copyIndicator, description, id, includedSpsNote, issueDateTime,
        issuerSpsParty, name, recipientSpsParty, referenceSpsReferencedDocument,
        signatorySpsAuthentication, statusCode, typeCode, submittedBy);
  }
}
