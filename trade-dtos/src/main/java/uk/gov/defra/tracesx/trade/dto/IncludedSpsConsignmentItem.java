
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IncludedSpsConsignmentItem implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private List<IncludedSpsTradeLineItem> includedSpsTradeLineItem = new ArrayList<IncludedSpsTradeLineItem>();
    private List<NatureIdentificationSpsCargo> natureIdentificationSpsCargo = new ArrayList<NatureIdentificationSpsCargo>();
    private final static long serialVersionUID = -5812326481763199394L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IncludedSpsConsignmentItem() {
    }

    /**
     * 
     * @param includedSpsTradeLineItem
     */
    public IncludedSpsConsignmentItem(List<IncludedSpsTradeLineItem> includedSpsTradeLineItem) {
        super();
        this.includedSpsTradeLineItem = includedSpsTradeLineItem;
    }

    public void setIncludedSpsTradeLineItem(
        List<IncludedSpsTradeLineItem> includedSpsTradeLineItem) {
        this.includedSpsTradeLineItem = includedSpsTradeLineItem;
    }

    public void setNatureIdentificationSpsCargo(
        List<NatureIdentificationSpsCargo> natureIdentificationSpsCargo) {
        this.natureIdentificationSpsCargo = natureIdentificationSpsCargo;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<IncludedSpsTradeLineItem> getIncludedSpsTradeLineItem() {
        return includedSpsTradeLineItem;
    }

    public IncludedSpsConsignmentItem withIncludedSpsTradeLineItem(List<IncludedSpsTradeLineItem> includedSpsTradeLineItem) {
        this.includedSpsTradeLineItem = includedSpsTradeLineItem;
        return this;
    }

    public List<NatureIdentificationSpsCargo> getNatureIdentificationSpsCargo() {
        return natureIdentificationSpsCargo;
    }

    public IncludedSpsConsignmentItem withNatureIdentificationSpsCargo(List<NatureIdentificationSpsCargo> natureIdentificationSpsCargo) {
        this.natureIdentificationSpsCargo = natureIdentificationSpsCargo;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IncludedSpsConsignmentItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("includedSpsTradeLineItem");
        sb.append('=');
        sb.append(((this.includedSpsTradeLineItem == null)?"<null>":this.includedSpsTradeLineItem));
        sb.append(',');
        sb.append("natureIdentificationSpsCargo");
        sb.append('=');
        sb.append(((this.natureIdentificationSpsCargo == null)?"<null>":this.natureIdentificationSpsCargo));
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
        result = ((result* 31)+((this.includedSpsTradeLineItem == null)? 0 :this.includedSpsTradeLineItem.hashCode()));
        result = ((result* 31)+((this.natureIdentificationSpsCargo == null)? 0 :this.natureIdentificationSpsCargo.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IncludedSpsConsignmentItem) == false) {
            return false;
        }
        IncludedSpsConsignmentItem rhs = ((IncludedSpsConsignmentItem) other);
        return (((this.includedSpsTradeLineItem == rhs.includedSpsTradeLineItem)||((this.includedSpsTradeLineItem!= null)&&this.includedSpsTradeLineItem.equals(rhs.includedSpsTradeLineItem)))&&((this.natureIdentificationSpsCargo == rhs.natureIdentificationSpsCargo)||((this.natureIdentificationSpsCargo!= null)&&this.natureIdentificationSpsCargo.equals(rhs.natureIdentificationSpsCargo))));
    }

}
