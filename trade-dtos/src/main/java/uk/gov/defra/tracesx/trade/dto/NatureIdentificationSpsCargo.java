
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class NatureIdentificationSpsCargo implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private CargoTypeClassificationCodeType typeCode;
    private final static long serialVersionUID = -3494654321794024726L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NatureIdentificationSpsCargo() {
    }

    /**
     * 
     * @param typeCode
     */
    public NatureIdentificationSpsCargo(CargoTypeClassificationCodeType typeCode) {
        super();
        this.typeCode = typeCode;
    }

    public void setTypeCode(CargoTypeClassificationCodeType typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 
     * (Required)
     * 
     */
    public CargoTypeClassificationCodeType getTypeCode() {
        return typeCode;
    }

    public NatureIdentificationSpsCargo withTypeCode(CargoTypeClassificationCodeType typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NatureIdentificationSpsCargo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        result = ((result* 31)+((this.typeCode == null)? 0 :this.typeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NatureIdentificationSpsCargo) == false) {
            return false;
        }
        NatureIdentificationSpsCargo rhs = ((NatureIdentificationSpsCargo) other);
        return ((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode)));
    }

}
