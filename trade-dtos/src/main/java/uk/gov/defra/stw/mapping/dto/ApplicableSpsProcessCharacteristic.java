
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApplicableSpsProcessCharacteristic implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> description = new ArrayList<TextType>();
    private MeasureType maximumValueMeasure;
    private MeasureType minimumValueMeasure;
    private MeasuredAttributeCodeType typeCode;
    private MeasureType valueMeasure;
    private final static long serialVersionUID = -7714453871118046730L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ApplicableSpsProcessCharacteristic() {
    }

    /**
     * 
     * @param description
     */
    public ApplicableSpsProcessCharacteristic(List<TextType> description) {
        super();
        this.description = description;
    }

    public void setDescription(List<TextType> description) {
        this.description = description;
    }

    public void setMaximumValueMeasure(MeasureType maximumValueMeasure) {
        this.maximumValueMeasure = maximumValueMeasure;
    }

    public void setMinimumValueMeasure(MeasureType minimumValueMeasure) {
        this.minimumValueMeasure = minimumValueMeasure;
    }

    public void setTypeCode(MeasuredAttributeCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public void setValueMeasure(MeasureType valueMeasure) {
        this.valueMeasure = valueMeasure;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<TextType> getDescription() {
        return description;
    }

    public ApplicableSpsProcessCharacteristic withDescription(List<TextType> description) {
        this.description = description;
        return this;
    }

    public MeasureType getMaximumValueMeasure() {
        return maximumValueMeasure;
    }

    public ApplicableSpsProcessCharacteristic withMaximumValueMeasure(MeasureType maximumValueMeasure) {
        this.maximumValueMeasure = maximumValueMeasure;
        return this;
    }

    public MeasureType getMinimumValueMeasure() {
        return minimumValueMeasure;
    }

    public ApplicableSpsProcessCharacteristic withMinimumValueMeasure(MeasureType minimumValueMeasure) {
        this.minimumValueMeasure = minimumValueMeasure;
        return this;
    }

    public MeasuredAttributeCodeType getTypeCode() {
        return typeCode;
    }

    public ApplicableSpsProcessCharacteristic withTypeCode(MeasuredAttributeCodeType typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public MeasureType getValueMeasure() {
        return valueMeasure;
    }

    public ApplicableSpsProcessCharacteristic withValueMeasure(MeasureType valueMeasure) {
        this.valueMeasure = valueMeasure;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ApplicableSpsProcessCharacteristic.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("maximumValueMeasure");
        sb.append('=');
        sb.append(((this.maximumValueMeasure == null)?"<null>":this.maximumValueMeasure));
        sb.append(',');
        sb.append("minimumValueMeasure");
        sb.append('=');
        sb.append(((this.minimumValueMeasure == null)?"<null>":this.minimumValueMeasure));
        sb.append(',');
        sb.append("typeCode");
        sb.append('=');
        sb.append(((this.typeCode == null)?"<null>":this.typeCode));
        sb.append(',');
        sb.append("valueMeasure");
        sb.append('=');
        sb.append(((this.valueMeasure == null)?"<null>":this.valueMeasure));
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
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.minimumValueMeasure == null)? 0 :this.minimumValueMeasure.hashCode()));
        result = ((result* 31)+((this.maximumValueMeasure == null)? 0 :this.maximumValueMeasure.hashCode()));
        result = ((result* 31)+((this.valueMeasure == null)? 0 :this.valueMeasure.hashCode()));
        result = ((result* 31)+((this.typeCode == null)? 0 :this.typeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ApplicableSpsProcessCharacteristic) == false) {
            return false;
        }
        ApplicableSpsProcessCharacteristic rhs = ((ApplicableSpsProcessCharacteristic) other);
        return ((((((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description)))&&((this.minimumValueMeasure == rhs.minimumValueMeasure)||((this.minimumValueMeasure!= null)&&this.minimumValueMeasure.equals(rhs.minimumValueMeasure))))&&((this.maximumValueMeasure == rhs.maximumValueMeasure)||((this.maximumValueMeasure!= null)&&this.maximumValueMeasure.equals(rhs.maximumValueMeasure))))&&((this.valueMeasure == rhs.valueMeasure)||((this.valueMeasure!= null)&&this.valueMeasure.equals(rhs.valueMeasure))))&&((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode))));
    }

}
