
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;

public class MeasureType implements Serializable
{

    private String unitCode;
    private String unitCodeListVersionID;
    private Double value;
    private final static long serialVersionUID = 6958181453404588616L;

    public MeasureType() {
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public void setUnitCodeListVersionID(String unitCodeListVersionID) {
        this.unitCodeListVersionID = unitCodeListVersionID;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public MeasureType withUnitCode(String unitCode) {
        this.unitCode = unitCode;
        return this;
    }

    public String getUnitCodeListVersionID() {
        return unitCodeListVersionID;
    }

    public MeasureType withUnitCodeListVersionID(String unitCodeListVersionID) {
        this.unitCodeListVersionID = unitCodeListVersionID;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public MeasureType withValue(Double value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MeasureType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("unitCode");
        sb.append('=');
        sb.append(((this.unitCode == null)?"<null>":this.unitCode));
        sb.append(',');
        sb.append("unitCodeListVersionID");
        sb.append('=');
        sb.append(((this.unitCodeListVersionID == null)?"<null>":this.unitCodeListVersionID));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
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
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.unitCodeListVersionID == null)? 0 :this.unitCodeListVersionID.hashCode()));
        result = ((result* 31)+((this.unitCode == null)? 0 :this.unitCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MeasureType) == false) {
            return false;
        }
        MeasureType rhs = ((MeasureType) other);
        return ((((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value)))&&((this.unitCodeListVersionID == rhs.unitCodeListVersionID)||((this.unitCodeListVersionID!= null)&&this.unitCodeListVersionID.equals(rhs.unitCodeListVersionID))))&&((this.unitCode == rhs.unitCode)||((this.unitCode!= null)&&this.unitCode.equals(rhs.unitCode))));
    }

}
