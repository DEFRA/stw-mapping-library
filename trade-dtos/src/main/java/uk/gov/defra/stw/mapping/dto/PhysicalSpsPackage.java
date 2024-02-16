
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhysicalSpsPackage implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private ItemQuantity itemQuantity;
    /**
     * 
     * (Required)
     * 
     */
    private CodeType levelCode;
    private MeasureType nominalGrossVolumeMeasure;
    private MeasureType nominalGrossWeightMeasure;
    private List<PhysicalSpsShippingMark> physicalSpsShippingMarks = new ArrayList<PhysicalSpsShippingMark>();
    /**
     * 
     * (Required)
     * 
     */
    private PackageTypeCodeType typeCode;
    private final static long serialVersionUID = 7691559516733282969L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PhysicalSpsPackage() {
    }

    /**
     * 
     * @param levelCode
     * @param itemQuantity
     * @param typeCode
     */
    public PhysicalSpsPackage(ItemQuantity itemQuantity, CodeType levelCode, PackageTypeCodeType typeCode) {
        super();
        this.itemQuantity = itemQuantity;
        this.levelCode = levelCode;
        this.typeCode = typeCode;
    }

    public void setItemQuantity(ItemQuantity itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setLevelCode(CodeType levelCode) {
        this.levelCode = levelCode;
    }

    public void setNominalGrossVolumeMeasure(
        MeasureType nominalGrossVolumeMeasure) {
        this.nominalGrossVolumeMeasure = nominalGrossVolumeMeasure;
    }

    public void setNominalGrossWeightMeasure(
        MeasureType nominalGrossWeightMeasure) {
        this.nominalGrossWeightMeasure = nominalGrossWeightMeasure;
    }

    public void setPhysicalSpsShippingMarks(
        List<PhysicalSpsShippingMark> physicalSpsShippingMarks) {
        this.physicalSpsShippingMarks = physicalSpsShippingMarks;
    }

    public void setTypeCode(PackageTypeCodeType typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 
     * (Required)
     * 
     */
    public ItemQuantity getItemQuantity() {
        return itemQuantity;
    }

    public PhysicalSpsPackage withItemQuantity(ItemQuantity itemQuantity) {
        this.itemQuantity = itemQuantity;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public CodeType getLevelCode() {
        return levelCode;
    }

    public PhysicalSpsPackage withLevelCode(CodeType levelCode) {
        this.levelCode = levelCode;
        return this;
    }

    public MeasureType getNominalGrossVolumeMeasure() {
        return nominalGrossVolumeMeasure;
    }

    public PhysicalSpsPackage withNominalGrossVolumeMeasure(MeasureType nominalGrossVolumeMeasure) {
        this.nominalGrossVolumeMeasure = nominalGrossVolumeMeasure;
        return this;
    }

    public MeasureType getNominalGrossWeightMeasure() {
        return nominalGrossWeightMeasure;
    }

    public PhysicalSpsPackage withNominalGrossWeightMeasure(MeasureType nominalGrossWeightMeasure) {
        this.nominalGrossWeightMeasure = nominalGrossWeightMeasure;
        return this;
    }

    public List<PhysicalSpsShippingMark> getPhysicalSpsShippingMarks() {
        return physicalSpsShippingMarks;
    }

    public PhysicalSpsPackage withPhysicalSpsShippingMarks(List<PhysicalSpsShippingMark> physicalSpsShippingMarks) {
        this.physicalSpsShippingMarks = physicalSpsShippingMarks;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public PackageTypeCodeType getTypeCode() {
        return typeCode;
    }

    public PhysicalSpsPackage withTypeCode(PackageTypeCodeType typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PhysicalSpsPackage.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("itemQuantity");
        sb.append('=');
        sb.append(((this.itemQuantity == null)?"<null>":this.itemQuantity));
        sb.append(',');
        sb.append("levelCode");
        sb.append('=');
        sb.append(((this.levelCode == null)?"<null>":this.levelCode));
        sb.append(',');
        sb.append("nominalGrossVolumeMeasure");
        sb.append('=');
        sb.append(((this.nominalGrossVolumeMeasure == null)?"<null>":this.nominalGrossVolumeMeasure));
        sb.append(',');
        sb.append("nominalGrossWeightMeasure");
        sb.append('=');
        sb.append(((this.nominalGrossWeightMeasure == null)?"<null>":this.nominalGrossWeightMeasure));
        sb.append(',');
        sb.append("physicalSpsShippingMarks");
        sb.append('=');
        sb.append(((this.physicalSpsShippingMarks == null)?"<null>":this.physicalSpsShippingMarks));
        sb.append(',');
        sb.append("typeCode");
        sb.append('=');
        sb.append(((this.typeCode == null)?"<null>":this.typeCode));
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
        result = ((result* 31)+((this.levelCode == null)? 0 :this.levelCode.hashCode()));
        result = ((result* 31)+((this.itemQuantity == null)? 0 :this.itemQuantity.hashCode()));
        result = ((result* 31)+((this.nominalGrossVolumeMeasure == null)? 0 :this.nominalGrossVolumeMeasure.hashCode()));
        result = ((result* 31)+((this.nominalGrossWeightMeasure == null)? 0 :this.nominalGrossWeightMeasure.hashCode()));
        result = ((result* 31)+((this.physicalSpsShippingMarks
            == null)? 0 :this.physicalSpsShippingMarks.hashCode()));
        result = ((result* 31)+((this.typeCode == null)? 0 :this.typeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PhysicalSpsPackage) == false) {
            return false;
        }
        PhysicalSpsPackage rhs = ((PhysicalSpsPackage) other);
        return (((((((this.levelCode == rhs.levelCode)||((this.levelCode!= null)&&this.levelCode.equals(rhs.levelCode)))&&((this.itemQuantity == rhs.itemQuantity)||((this.itemQuantity!= null)&&this.itemQuantity.equals(rhs.itemQuantity))))&&((this.nominalGrossVolumeMeasure == rhs.nominalGrossVolumeMeasure)||((this.nominalGrossVolumeMeasure!= null)&&this.nominalGrossVolumeMeasure.equals(rhs.nominalGrossVolumeMeasure))))&&((this.nominalGrossWeightMeasure == rhs.nominalGrossWeightMeasure)||((this.nominalGrossWeightMeasure!= null)&&this.nominalGrossWeightMeasure.equals(rhs.nominalGrossWeightMeasure))))&&((this.physicalSpsShippingMarks
            == rhs.physicalSpsShippingMarks)||((this.physicalSpsShippingMarks
            != null)&&this.physicalSpsShippingMarks.equals(rhs.physicalSpsShippingMarks))))&&((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode))));
    }

}
