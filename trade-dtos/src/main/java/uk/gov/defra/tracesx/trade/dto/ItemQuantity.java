
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class ItemQuantity implements Serializable
{

    private String unitCode;
    private String unitCodeListAgencyID;
    private String unitCodeListAgencyName;
    private String unitCodeListID;
    private Double value;
    private final static long serialVersionUID = 7079196210070291881L;

    public ItemQuantity() {
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public void setUnitCodeListAgencyID(String unitCodeListAgencyID) {
        this.unitCodeListAgencyID = unitCodeListAgencyID;
    }

    public void setUnitCodeListAgencyName(String unitCodeListAgencyName) {
        this.unitCodeListAgencyName = unitCodeListAgencyName;
    }

    public void setUnitCodeListID(String unitCodeListID) {
        this.unitCodeListID = unitCodeListID;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public ItemQuantity withUnitCode(String unitCode) {
        this.unitCode = unitCode;
        return this;
    }

    public String getUnitCodeListAgencyID() {
        return unitCodeListAgencyID;
    }

    public ItemQuantity withUnitCodeListAgencyID(String unitCodeListAgencyID) {
        this.unitCodeListAgencyID = unitCodeListAgencyID;
        return this;
    }

    public String getUnitCodeListAgencyName() {
        return unitCodeListAgencyName;
    }

    public ItemQuantity withUnitCodeListAgencyName(String unitCodeListAgencyName) {
        this.unitCodeListAgencyName = unitCodeListAgencyName;
        return this;
    }

    public String getUnitCodeListID() {
        return unitCodeListID;
    }

    public ItemQuantity withUnitCodeListID(String unitCodeListID) {
        this.unitCodeListID = unitCodeListID;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public ItemQuantity withValue(Double value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ItemQuantity.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("unitCode");
        sb.append('=');
        sb.append(((this.unitCode == null)?"<null>":this.unitCode));
        sb.append(',');
        sb.append("unitCodeListAgencyID");
        sb.append('=');
        sb.append(((this.unitCodeListAgencyID == null)?"<null>":this.unitCodeListAgencyID));
        sb.append(',');
        sb.append("unitCodeListAgencyName");
        sb.append('=');
        sb.append(((this.unitCodeListAgencyName == null)?"<null>":this.unitCodeListAgencyName));
        sb.append(',');
        sb.append("unitCodeListID");
        sb.append('=');
        sb.append(((this.unitCodeListID == null)?"<null>":this.unitCodeListID));
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
        result = ((result* 31)+((this.unitCodeListAgencyID == null)? 0 :this.unitCodeListAgencyID.hashCode()));
        result = ((result* 31)+((this.unitCodeListAgencyName == null)? 0 :this.unitCodeListAgencyName.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.unitCode == null)? 0 :this.unitCode.hashCode()));
        result = ((result* 31)+((this.unitCodeListID == null)? 0 :this.unitCodeListID.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ItemQuantity) == false) {
            return false;
        }
        ItemQuantity rhs = ((ItemQuantity) other);
        return ((((((this.unitCodeListAgencyID == rhs.unitCodeListAgencyID)||((this.unitCodeListAgencyID!= null)&&this.unitCodeListAgencyID.equals(rhs.unitCodeListAgencyID)))&&((this.unitCodeListAgencyName == rhs.unitCodeListAgencyName)||((this.unitCodeListAgencyName!= null)&&this.unitCodeListAgencyName.equals(rhs.unitCodeListAgencyName))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.unitCode == rhs.unitCode)||((this.unitCode!= null)&&this.unitCode.equals(rhs.unitCode))))&&((this.unitCodeListID == rhs.unitCodeListID)||((this.unitCodeListID!= null)&&this.unitCodeListID.equals(rhs.unitCodeListID))));
    }

}
