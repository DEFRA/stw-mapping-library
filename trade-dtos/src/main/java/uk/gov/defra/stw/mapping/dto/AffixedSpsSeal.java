
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;

public class AffixedSpsSeal implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private IDType id;
    private SpsPartyType issuingSpsParty;
    private IDType maximumID;
    private final static long serialVersionUID = 7085693780110400892L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AffixedSpsSeal() {
    }

    /**
     * 
     * @param id
     */
    public AffixedSpsSeal(IDType id) {
        super();
        this.id = id;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public void setIssuingSpsParty(SpsPartyType issuingSpsParty) {
        this.issuingSpsParty = issuingSpsParty;
    }

    public void setMaximumID(IDType maximumID) {
        this.maximumID = maximumID;
    }

    /**
     * 
     * (Required)
     * 
     */
    public IDType getId() {
        return id;
    }

    public AffixedSpsSeal withId(IDType id) {
        this.id = id;
        return this;
    }

    public SpsPartyType getIssuingSpsParty() {
        return issuingSpsParty;
    }

    public AffixedSpsSeal withIssuingSpsParty(SpsPartyType issuingSpsParty) {
        this.issuingSpsParty = issuingSpsParty;
        return this;
    }

    public IDType getMaximumID() {
        return maximumID;
    }

    public AffixedSpsSeal withMaximumID(IDType maximumID) {
        this.maximumID = maximumID;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AffixedSpsSeal.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("issuingSpsParty");
        sb.append('=');
        sb.append(((this.issuingSpsParty == null)?"<null>":this.issuingSpsParty));
        sb.append(',');
        sb.append("maximumID");
        sb.append('=');
        sb.append(((this.maximumID == null)?"<null>":this.maximumID));
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
        result = ((result* 31)+((this.issuingSpsParty == null)? 0 :this.issuingSpsParty.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.maximumID == null)? 0 :this.maximumID.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AffixedSpsSeal) == false) {
            return false;
        }
        AffixedSpsSeal rhs = ((AffixedSpsSeal) other);
        return ((((this.issuingSpsParty == rhs.issuingSpsParty)||((this.issuingSpsParty!= null)&&this.issuingSpsParty.equals(rhs.issuingSpsParty)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.maximumID == rhs.maximumID)||((this.maximumID!= null)&&this.maximumID.equals(rhs.maximumID))));
    }

}
