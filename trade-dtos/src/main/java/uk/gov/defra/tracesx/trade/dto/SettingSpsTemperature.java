
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class SettingSpsTemperature implements Serializable
{

    private MeasureType maximumValueMeasure;
    private MeasureType minimumValueMeasure;
    private TemperatureTypeCodeType typeCode;
    private MeasureType valueMeasure;
    private final static long serialVersionUID = -6361422156498892947L;

    public SettingSpsTemperature() {
    }

    public void setMaximumValueMeasure(MeasureType maximumValueMeasure) {
        this.maximumValueMeasure = maximumValueMeasure;
    }

    public void setMinimumValueMeasure(MeasureType minimumValueMeasure) {
        this.minimumValueMeasure = minimumValueMeasure;
    }

    public void setTypeCode(TemperatureTypeCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public void setValueMeasure(MeasureType valueMeasure) {
        this.valueMeasure = valueMeasure;
    }

    public MeasureType getMaximumValueMeasure() {
        return maximumValueMeasure;
    }

    public SettingSpsTemperature withMaximumValueMeasure(MeasureType maximumValueMeasure) {
        this.maximumValueMeasure = maximumValueMeasure;
        return this;
    }

    public MeasureType getMinimumValueMeasure() {
        return minimumValueMeasure;
    }

    public SettingSpsTemperature withMinimumValueMeasure(MeasureType minimumValueMeasure) {
        this.minimumValueMeasure = minimumValueMeasure;
        return this;
    }

    public TemperatureTypeCodeType getTypeCode() {
        return typeCode;
    }

    public SettingSpsTemperature withTypeCode(TemperatureTypeCodeType typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public MeasureType getValueMeasure() {
        return valueMeasure;
    }

    public SettingSpsTemperature withValueMeasure(MeasureType valueMeasure) {
        this.valueMeasure = valueMeasure;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SettingSpsTemperature.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof SettingSpsTemperature) == false) {
            return false;
        }
        SettingSpsTemperature rhs = ((SettingSpsTemperature) other);
        return (((((this.minimumValueMeasure == rhs.minimumValueMeasure)||((this.minimumValueMeasure!= null)&&this.minimumValueMeasure.equals(rhs.minimumValueMeasure)))&&((this.maximumValueMeasure == rhs.maximumValueMeasure)||((this.maximumValueMeasure!= null)&&this.maximumValueMeasure.equals(rhs.maximumValueMeasure))))&&((this.valueMeasure == rhs.valueMeasure)||((this.valueMeasure!= null)&&this.valueMeasure.equals(rhs.valueMeasure))))&&((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode))));
    }

}
