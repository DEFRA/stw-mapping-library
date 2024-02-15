
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class PhysicalSpsShippingMark implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private TextType marking;
    private final static long serialVersionUID = -1353916846548371505L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PhysicalSpsShippingMark() {
    }

    /**
     * 
     * @param marking
     */
    public PhysicalSpsShippingMark(TextType marking) {
        super();
        this.marking = marking;
    }

    public void setMarking(TextType marking) {
        this.marking = marking;
    }

    /**
     * 
     * (Required)
     * 
     */
    public TextType getMarking() {
        return marking;
    }

    public PhysicalSpsShippingMark withMarking(TextType marking) {
        this.marking = marking;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PhysicalSpsShippingMark.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("marking");
        sb.append('=');
        sb.append(((this.marking == null)?"<null>":this.marking));
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
        result = ((result* 31)+((this.marking == null)? 0 :this.marking.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PhysicalSpsShippingMark) == false) {
            return false;
        }
        PhysicalSpsShippingMark rhs = ((PhysicalSpsShippingMark) other);
        return ((this.marking == rhs.marking)||((this.marking!= null)&&this.marking.equals(rhs.marking)));
    }

}
