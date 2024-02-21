
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IncludedSpsTradeLineItem implements Serializable
{

    private List<SpsNoteType> additionalInformationSpsNote = new ArrayList<SpsNoteType>();
    private List<ApplicableSpsClassification> applicableSpsClassification = new ArrayList<ApplicableSpsClassification>();
    private List<AppliedSpsProcess> appliedSpsProcess = new ArrayList<AppliedSpsProcess>();
    private List<SpsAuthenticationType> assertedSpsAuthentication = new ArrayList<SpsAuthenticationType>();
    private List<SpsTransportEquipmentType> associatedSpsTransportEquipment = new ArrayList<SpsTransportEquipmentType>();
    private List<TextType> commonName = new ArrayList<TextType>();
    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> description = new ArrayList<TextType>();
    private List<DateTimeType> expiryDateTime = new ArrayList<DateTimeType>();
    private MeasureType grossVolumeMeasure;
    private MeasureType grossWeightMeasure;
    private List<TextType> intendedUse = new ArrayList<TextType>();
    private MeasureType netVolumeMeasure;
    private MeasureType netWeightMeasure;
    private List<SpsCountryType> originSpsCountry = new ArrayList<SpsCountryType>();
    private List<SpsLocationType> originSpsLocation = new ArrayList<SpsLocationType>();
    private List<PhysicalSpsPackage> physicalSpsPackage = new ArrayList<PhysicalSpsPackage>();
    private List<IDType> productionBatchID = new ArrayList<IDType>();
    private List<SpsReferencedDocumentType> referenceSpsReferencedDocument = new ArrayList<SpsReferencedDocumentType>();
    private List<TextType> scientificName = new ArrayList<TextType>();
    /**
     * 
     * (Required)
     * 
     */
    private SequenceNumeric sequenceNumeric;
    private final static long serialVersionUID = -6147493571750348989L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IncludedSpsTradeLineItem() {
    }

    /**
     * 
     * @param sequenceNumeric
     * @param description
     */
    public IncludedSpsTradeLineItem(List<TextType> description, SequenceNumeric sequenceNumeric) {
        super();
        this.description = description;
        this.sequenceNumeric = sequenceNumeric;
    }

    public void setAdditionalInformationSpsNote(
        List<SpsNoteType> additionalInformationSpsNote) {
        this.additionalInformationSpsNote = additionalInformationSpsNote;
    }

    public void setApplicableSpsClassification(
        List<ApplicableSpsClassification> applicableSpsClassification) {
        this.applicableSpsClassification = applicableSpsClassification;
    }

    public void setAppliedSpsProcess(
        List<AppliedSpsProcess> appliedSpsProcess) {
        this.appliedSpsProcess = appliedSpsProcess;
    }

    public void setAssertedSpsAuthentication(
        List<SpsAuthenticationType> assertedSpsAuthentication) {
        this.assertedSpsAuthentication = assertedSpsAuthentication;
    }

    public void setAssociatedSpsTransportEquipment(
        List<SpsTransportEquipmentType> associatedSpsTransportEquipment) {
        this.associatedSpsTransportEquipment = associatedSpsTransportEquipment;
    }

    public void setCommonName(List<TextType> commonName) {
        this.commonName = commonName;
    }

    public void setDescription(List<TextType> description) {
        this.description = description;
    }

    public void setExpiryDateTime(List<DateTimeType> expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public void setGrossVolumeMeasure(MeasureType grossVolumeMeasure) {
        this.grossVolumeMeasure = grossVolumeMeasure;
    }

    public void setGrossWeightMeasure(MeasureType grossWeightMeasure) {
        this.grossWeightMeasure = grossWeightMeasure;
    }

    public void setIntendedUse(List<TextType> intendedUse) {
        this.intendedUse = intendedUse;
    }

    public void setNetVolumeMeasure(MeasureType netVolumeMeasure) {
        this.netVolumeMeasure = netVolumeMeasure;
    }

    public void setNetWeightMeasure(MeasureType netWeightMeasure) {
        this.netWeightMeasure = netWeightMeasure;
    }

    public void setOriginSpsCountry(
        List<SpsCountryType> originSpsCountry) {
        this.originSpsCountry = originSpsCountry;
    }

    public void setOriginSpsLocation(
        List<SpsLocationType> originSpsLocation) {
        this.originSpsLocation = originSpsLocation;
    }

    public void setPhysicalSpsPackage(
        List<PhysicalSpsPackage> physicalSpsPackage) {
        this.physicalSpsPackage = physicalSpsPackage;
    }

    public void setProductionBatchID(List<IDType> productionBatchID) {
        this.productionBatchID = productionBatchID;
    }

    public void setReferenceSpsReferencedDocument(
        List<SpsReferencedDocumentType> referenceSpsReferencedDocument) {
        this.referenceSpsReferencedDocument = referenceSpsReferencedDocument;
    }

    public void setScientificName(List<TextType> scientificName) {
        this.scientificName = scientificName;
    }

    public void setSequenceNumeric(SequenceNumeric sequenceNumeric) {
        this.sequenceNumeric = sequenceNumeric;
    }

    public List<SpsNoteType> getAdditionalInformationSpsNote() {
        return additionalInformationSpsNote;
    }

    public IncludedSpsTradeLineItem withAdditionalInformationSpsNote(List<SpsNoteType> additionalInformationSpsNote) {
        this.additionalInformationSpsNote = additionalInformationSpsNote;
        return this;
    }

    public List<ApplicableSpsClassification> getApplicableSpsClassification() {
        return applicableSpsClassification;
    }

    public IncludedSpsTradeLineItem withApplicableSpsClassification(List<ApplicableSpsClassification> applicableSpsClassification) {
        this.applicableSpsClassification = applicableSpsClassification;
        return this;
    }

    public List<AppliedSpsProcess> getAppliedSpsProcess() {
        return appliedSpsProcess;
    }

    public IncludedSpsTradeLineItem withAppliedSpsProcess(List<AppliedSpsProcess> appliedSpsProcess) {
        this.appliedSpsProcess = appliedSpsProcess;
        return this;
    }

    public List<SpsAuthenticationType> getAssertedSpsAuthentication() {
        return assertedSpsAuthentication;
    }

    public IncludedSpsTradeLineItem withAssertedSpsAuthentication(List<SpsAuthenticationType> assertedSpsAuthentication) {
        this.assertedSpsAuthentication = assertedSpsAuthentication;
        return this;
    }

    public List<SpsTransportEquipmentType> getAssociatedSpsTransportEquipment() {
        return associatedSpsTransportEquipment;
    }

    public IncludedSpsTradeLineItem withAssociatedSpsTransportEquipment(List<SpsTransportEquipmentType> associatedSpsTransportEquipment) {
        this.associatedSpsTransportEquipment = associatedSpsTransportEquipment;
        return this;
    }

    public List<TextType> getCommonName() {
        return commonName;
    }

    public IncludedSpsTradeLineItem withCommonName(List<TextType> commonName) {
        this.commonName = commonName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<TextType> getDescription() {
        return description;
    }

    public IncludedSpsTradeLineItem withDescription(List<TextType> description) {
        this.description = description;
        return this;
    }

    public List<DateTimeType> getExpiryDateTime() {
        return expiryDateTime;
    }

    public IncludedSpsTradeLineItem withExpiryDateTime(List<DateTimeType> expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
        return this;
    }

    public MeasureType getGrossVolumeMeasure() {
        return grossVolumeMeasure;
    }

    public IncludedSpsTradeLineItem withGrossVolumeMeasure(MeasureType grossVolumeMeasure) {
        this.grossVolumeMeasure = grossVolumeMeasure;
        return this;
    }

    public MeasureType getGrossWeightMeasure() {
        return grossWeightMeasure;
    }

    public IncludedSpsTradeLineItem withGrossWeightMeasure(MeasureType grossWeightMeasure) {
        this.grossWeightMeasure = grossWeightMeasure;
        return this;
    }

    public List<TextType> getIntendedUse() {
        return intendedUse;
    }

    public IncludedSpsTradeLineItem withIntendedUse(List<TextType> intendedUse) {
        this.intendedUse = intendedUse;
        return this;
    }

    public MeasureType getNetVolumeMeasure() {
        return netVolumeMeasure;
    }

    public IncludedSpsTradeLineItem withNetVolumeMeasure(MeasureType netVolumeMeasure) {
        this.netVolumeMeasure = netVolumeMeasure;
        return this;
    }

    public MeasureType getNetWeightMeasure() {
        return netWeightMeasure;
    }

    public IncludedSpsTradeLineItem withNetWeightMeasure(MeasureType netWeightMeasure) {
        this.netWeightMeasure = netWeightMeasure;
        return this;
    }

    public List<SpsCountryType> getOriginSpsCountry() {
        return originSpsCountry;
    }

    public IncludedSpsTradeLineItem withOriginSpsCountry(List<SpsCountryType> originSpsCountry) {
        this.originSpsCountry = originSpsCountry;
        return this;
    }

    public List<SpsLocationType> getOriginSpsLocation() {
        return originSpsLocation;
    }

    public IncludedSpsTradeLineItem withOriginSpsLocation(List<SpsLocationType> originSpsLocation) {
        this.originSpsLocation = originSpsLocation;
        return this;
    }

    public List<PhysicalSpsPackage> getPhysicalSpsPackage() {
        return physicalSpsPackage;
    }

    public IncludedSpsTradeLineItem withPhysicalSpsPackage(List<PhysicalSpsPackage> physicalSpsPackage) {
        this.physicalSpsPackage = physicalSpsPackage;
        return this;
    }

    public List<IDType> getProductionBatchID() {
        return productionBatchID;
    }

    public IncludedSpsTradeLineItem withProductionBatchID(List<IDType> productionBatchID) {
        this.productionBatchID = productionBatchID;
        return this;
    }

    public List<SpsReferencedDocumentType> getReferenceSpsReferencedDocument() {
        return referenceSpsReferencedDocument;
    }

    public IncludedSpsTradeLineItem withReferenceSpsReferencedDocument(List<SpsReferencedDocumentType> referenceSpsReferencedDocument) {
        this.referenceSpsReferencedDocument = referenceSpsReferencedDocument;
        return this;
    }

    public List<TextType> getScientificName() {
        return scientificName;
    }

    public IncludedSpsTradeLineItem withScientificName(List<TextType> scientificName) {
        this.scientificName = scientificName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SequenceNumeric getSequenceNumeric() {
        return sequenceNumeric;
    }

    public IncludedSpsTradeLineItem withSequenceNumeric(SequenceNumeric sequenceNumeric) {
        this.sequenceNumeric = sequenceNumeric;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IncludedSpsTradeLineItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("additionalInformationSpsNote");
        sb.append('=');
        sb.append(((this.additionalInformationSpsNote == null)?"<null>":this.additionalInformationSpsNote));
        sb.append(',');
        sb.append("applicableSpsClassification");
        sb.append('=');
        sb.append(((this.applicableSpsClassification == null)?"<null>":this.applicableSpsClassification));
        sb.append(',');
        sb.append("appliedSpsProcess");
        sb.append('=');
        sb.append(((this.appliedSpsProcess == null)?"<null>":this.appliedSpsProcess));
        sb.append(',');
        sb.append("assertedSpsAuthentication");
        sb.append('=');
        sb.append(((this.assertedSpsAuthentication == null)?"<null>":this.assertedSpsAuthentication));
        sb.append(',');
        sb.append("associatedSpsTransportEquipment");
        sb.append('=');
        sb.append(((this.associatedSpsTransportEquipment == null)?"<null>":this.associatedSpsTransportEquipment));
        sb.append(',');
        sb.append("commonName");
        sb.append('=');
        sb.append(((this.commonName == null)?"<null>":this.commonName));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("expiryDateTime");
        sb.append('=');
        sb.append(((this.expiryDateTime == null)?"<null>":this.expiryDateTime));
        sb.append(',');
        sb.append("grossVolumeMeasure");
        sb.append('=');
        sb.append(((this.grossVolumeMeasure == null)?"<null>":this.grossVolumeMeasure));
        sb.append(',');
        sb.append("grossWeightMeasure");
        sb.append('=');
        sb.append(((this.grossWeightMeasure == null)?"<null>":this.grossWeightMeasure));
        sb.append(',');
        sb.append("intendedUse");
        sb.append('=');
        sb.append(((this.intendedUse == null)?"<null>":this.intendedUse));
        sb.append(',');
        sb.append("netVolumeMeasure");
        sb.append('=');
        sb.append(((this.netVolumeMeasure == null)?"<null>":this.netVolumeMeasure));
        sb.append(',');
        sb.append("netWeightMeasure");
        sb.append('=');
        sb.append(((this.netWeightMeasure == null)?"<null>":this.netWeightMeasure));
        sb.append(',');
        sb.append("originSpsCountry");
        sb.append('=');
        sb.append(((this.originSpsCountry == null)?"<null>":this.originSpsCountry));
        sb.append(',');
        sb.append("originSpsLocation");
        sb.append('=');
        sb.append(((this.originSpsLocation == null)?"<null>":this.originSpsLocation));
        sb.append(',');
        sb.append("physicalSpsPackage");
        sb.append('=');
        sb.append(((this.physicalSpsPackage == null)?"<null>":this.physicalSpsPackage));
        sb.append(',');
        sb.append("productionBatchID");
        sb.append('=');
        sb.append(((this.productionBatchID == null)?"<null>":this.productionBatchID));
        sb.append(',');
        sb.append("referenceSpsReferencedDocument");
        sb.append('=');
        sb.append(((this.referenceSpsReferencedDocument == null)?"<null>":this.referenceSpsReferencedDocument));
        sb.append(',');
        sb.append("scientificName");
        sb.append('=');
        sb.append(((this.scientificName == null)?"<null>":this.scientificName));
        sb.append(',');
        sb.append("sequenceNumeric");
        sb.append('=');
        sb.append(((this.sequenceNumeric == null)?"<null>":this.sequenceNumeric));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.commonName == null)? 0 :this.commonName.hashCode()));
        result = ((result* 31)+((this.referenceSpsReferencedDocument == null)? 0 :this.referenceSpsReferencedDocument.hashCode()));
        result = ((result* 31)+((this.assertedSpsAuthentication == null)? 0 :this.assertedSpsAuthentication.hashCode()));
        result = ((result* 31)+((this.scientificName == null)? 0 :this.scientificName.hashCode()));
        result = ((result* 31)+((this.applicableSpsClassification == null)? 0 :this.applicableSpsClassification.hashCode()));
        result = ((result* 31)+((this.sequenceNumeric == null)? 0 :this.sequenceNumeric.hashCode()));
        result = ((result* 31)+((this.additionalInformationSpsNote == null)? 0 :this.additionalInformationSpsNote.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.originSpsCountry == null)? 0 :this.originSpsCountry.hashCode()));
        result = ((result* 31)+((this.physicalSpsPackage == null)? 0 :this.physicalSpsPackage.hashCode()));
        result = ((result* 31)+((this.netVolumeMeasure == null)? 0 :this.netVolumeMeasure.hashCode()));
        result = ((result* 31)+((this.associatedSpsTransportEquipment == null)? 0 :this.associatedSpsTransportEquipment.hashCode()));
        result = ((result* 31)+((this.appliedSpsProcess == null)? 0 :this.appliedSpsProcess.hashCode()));
        result = ((result* 31)+((this.grossWeightMeasure == null)? 0 :this.grossWeightMeasure.hashCode()));
        result = ((result* 31)+((this.intendedUse == null)? 0 :this.intendedUse.hashCode()));
        result = ((result* 31)+((this.netWeightMeasure == null)? 0 :this.netWeightMeasure.hashCode()));
        result = ((result* 31)+((this.originSpsLocation == null)? 0 :this.originSpsLocation.hashCode()));
        result = ((result* 31)+((this.expiryDateTime == null)? 0 :this.expiryDateTime.hashCode()));
        result = ((result* 31)+((this.productionBatchID == null)? 0 :this.productionBatchID.hashCode()));
        result = ((result* 31)+((this.grossVolumeMeasure == null)? 0 :this.grossVolumeMeasure.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IncludedSpsTradeLineItem) == false) {
            return false;
        }
        IncludedSpsTradeLineItem rhs = ((IncludedSpsTradeLineItem) other);
        return (((((((((((((((((((((this.commonName == rhs.commonName)||((this.commonName!= null)&&this.commonName.equals(rhs.commonName)))&&((this.referenceSpsReferencedDocument == rhs.referenceSpsReferencedDocument)||((this.referenceSpsReferencedDocument!= null)&&this.referenceSpsReferencedDocument.equals(rhs.referenceSpsReferencedDocument))))&&((this.assertedSpsAuthentication == rhs.assertedSpsAuthentication)||((this.assertedSpsAuthentication!= null)&&this.assertedSpsAuthentication.equals(rhs.assertedSpsAuthentication))))&&((this.scientificName == rhs.scientificName)||((this.scientificName!= null)&&this.scientificName.equals(rhs.scientificName))))&&((this.applicableSpsClassification == rhs.applicableSpsClassification)||((this.applicableSpsClassification!= null)&&this.applicableSpsClassification.equals(rhs.applicableSpsClassification))))&&((this.sequenceNumeric == rhs.sequenceNumeric)||((this.sequenceNumeric!= null)&&this.sequenceNumeric.equals(rhs.sequenceNumeric))))&&((this.additionalInformationSpsNote == rhs.additionalInformationSpsNote)||((this.additionalInformationSpsNote!= null)&&this.additionalInformationSpsNote.equals(rhs.additionalInformationSpsNote))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.originSpsCountry == rhs.originSpsCountry)||((this.originSpsCountry!= null)&&this.originSpsCountry.equals(rhs.originSpsCountry))))&&((this.physicalSpsPackage == rhs.physicalSpsPackage)||((this.physicalSpsPackage!= null)&&this.physicalSpsPackage.equals(rhs.physicalSpsPackage))))&&((this.netVolumeMeasure == rhs.netVolumeMeasure)||((this.netVolumeMeasure!= null)&&this.netVolumeMeasure.equals(rhs.netVolumeMeasure))))&&((this.associatedSpsTransportEquipment == rhs.associatedSpsTransportEquipment)||((this.associatedSpsTransportEquipment!= null)&&this.associatedSpsTransportEquipment.equals(rhs.associatedSpsTransportEquipment))))&&((this.appliedSpsProcess == rhs.appliedSpsProcess)||((this.appliedSpsProcess!= null)&&this.appliedSpsProcess.equals(rhs.appliedSpsProcess))))&&((this.grossWeightMeasure == rhs.grossWeightMeasure)||((this.grossWeightMeasure!= null)&&this.grossWeightMeasure.equals(rhs.grossWeightMeasure))))&&((this.intendedUse == rhs.intendedUse)||((this.intendedUse!= null)&&this.intendedUse.equals(rhs.intendedUse))))&&((this.netWeightMeasure == rhs.netWeightMeasure)||((this.netWeightMeasure!= null)&&this.netWeightMeasure.equals(rhs.netWeightMeasure))))&&((this.originSpsLocation == rhs.originSpsLocation)||((this.originSpsLocation!= null)&&this.originSpsLocation.equals(rhs.originSpsLocation))))&&((this.expiryDateTime == rhs.expiryDateTime)||((this.expiryDateTime!= null)&&this.expiryDateTime.equals(rhs.expiryDateTime))))&&((this.productionBatchID == rhs.productionBatchID)||((this.productionBatchID!= null)&&this.productionBatchID.equals(rhs.productionBatchID))))&&((this.grossVolumeMeasure == rhs.grossVolumeMeasure)||((this.grossVolumeMeasure!= null)&&this.grossVolumeMeasure.equals(rhs.grossVolumeMeasure))));
    }

}
