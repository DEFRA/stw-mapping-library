
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class SpsEventType implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private SpsLocationType occurrenceSpsLocation;
    private final static long serialVersionUID = -7919630947241443124L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsEventType() {
    }

    /**
     * 
     * @param occurrenceSpsLocation
     */
    public SpsEventType(SpsLocationType occurrenceSpsLocation) {
        super();
        this.occurrenceSpsLocation = occurrenceSpsLocation;
    }

    public void setOccurrenceSpsLocation(
        SpsLocationType occurrenceSpsLocation) {
        this.occurrenceSpsLocation = occurrenceSpsLocation;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SpsLocationType getOccurrenceSpsLocation() {
        return occurrenceSpsLocation;
    }

    public SpsEventType withOccurrenceSpsLocation(SpsLocationType occurrenceSpsLocation) {
        this.occurrenceSpsLocation = occurrenceSpsLocation;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsEventType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("occurrenceSpsLocation");
        sb.append('=');
        sb.append(((this.occurrenceSpsLocation == null)?"<null>":this.occurrenceSpsLocation));
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
        result = ((result* 31)+((this.occurrenceSpsLocation == null)? 0 :this.occurrenceSpsLocation.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsEventType) == false) {
            return false;
        }
        SpsEventType rhs = ((SpsEventType) other);
        return ((this.occurrenceSpsLocation == rhs.occurrenceSpsLocation)||((this.occurrenceSpsLocation!= null)&&this.occurrenceSpsLocation.equals(rhs.occurrenceSpsLocation)));
    }

}
