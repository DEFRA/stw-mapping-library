
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsAuthenticationType implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private DateTimeType actualDateTime;
    /**
     * 
     * (Required)
     * 
     */
    private List<IncludedSpsClause> includedSpsClause = new ArrayList<IncludedSpsClause>();
    private SpsLocationType issueSpsLocation;
    private SpsPartyType locationProviderSpsParty;
    /**
     * 
     * (Required)
     * 
     */
    private SpsPartyType providerSpsParty;
    private GovernmentActionCodeType typeCode;
    private final static long serialVersionUID = 8296478942182435313L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsAuthenticationType() {
    }

    /**
     * 
     * @param includedSpsClause
     * @param providerSpsParty
     * @param actualDateTime
     */
    public SpsAuthenticationType(DateTimeType actualDateTime, List<IncludedSpsClause> includedSpsClause, SpsPartyType providerSpsParty) {
        super();
        this.actualDateTime = actualDateTime;
        this.includedSpsClause = includedSpsClause;
        this.providerSpsParty = providerSpsParty;
    }

    public void setActualDateTime(DateTimeType actualDateTime) {
        this.actualDateTime = actualDateTime;
    }

    public void setIncludedSpsClause(
        List<IncludedSpsClause> includedSpsClause) {
        this.includedSpsClause = includedSpsClause;
    }

    public void setIssueSpsLocation(SpsLocationType issueSpsLocation) {
        this.issueSpsLocation = issueSpsLocation;
    }

    public void setLocationProviderSpsParty(
        SpsPartyType locationProviderSpsParty) {
        this.locationProviderSpsParty = locationProviderSpsParty;
    }

    public void setProviderSpsParty(SpsPartyType providerSpsParty) {
        this.providerSpsParty = providerSpsParty;
    }

    public void setTypeCode(GovernmentActionCodeType typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 
     * (Required)
     * 
     */
    public DateTimeType getActualDateTime() {
        return actualDateTime;
    }

    public SpsAuthenticationType withActualDateTime(DateTimeType actualDateTime) {
        this.actualDateTime = actualDateTime;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<IncludedSpsClause> getIncludedSpsClause() {
        return includedSpsClause;
    }

    public SpsAuthenticationType withIncludedSpsClause(List<IncludedSpsClause> includedSpsClause) {
        this.includedSpsClause = includedSpsClause;
        return this;
    }

    public SpsLocationType getIssueSpsLocation() {
        return issueSpsLocation;
    }

    public SpsAuthenticationType withIssueSpsLocation(SpsLocationType issueSpsLocation) {
        this.issueSpsLocation = issueSpsLocation;
        return this;
    }

    public SpsPartyType getLocationProviderSpsParty() {
        return locationProviderSpsParty;
    }

    public SpsAuthenticationType withLocationProviderSpsParty(SpsPartyType locationProviderSpsParty) {
        this.locationProviderSpsParty = locationProviderSpsParty;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SpsPartyType getProviderSpsParty() {
        return providerSpsParty;
    }

    public SpsAuthenticationType withProviderSpsParty(SpsPartyType providerSpsParty) {
        this.providerSpsParty = providerSpsParty;
        return this;
    }

    public GovernmentActionCodeType getTypeCode() {
        return typeCode;
    }

    public SpsAuthenticationType withTypeCode(GovernmentActionCodeType typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsAuthenticationType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("actualDateTime");
        sb.append('=');
        sb.append(((this.actualDateTime == null)?"<null>":this.actualDateTime));
        sb.append(',');
        sb.append("includedSpsClause");
        sb.append('=');
        sb.append(((this.includedSpsClause == null)?"<null>":this.includedSpsClause));
        sb.append(',');
        sb.append("issueSpsLocation");
        sb.append('=');
        sb.append(((this.issueSpsLocation == null)?"<null>":this.issueSpsLocation));
        sb.append(',');
        sb.append("locationProviderSpsParty");
        sb.append('=');
        sb.append(((this.locationProviderSpsParty == null)?"<null>":this.locationProviderSpsParty));
        sb.append(',');
        sb.append("providerSpsParty");
        sb.append('=');
        sb.append(((this.providerSpsParty == null)?"<null>":this.providerSpsParty));
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
        result = ((result* 31)+((this.locationProviderSpsParty == null)? 0 :this.locationProviderSpsParty.hashCode()));
        result = ((result* 31)+((this.issueSpsLocation == null)? 0 :this.issueSpsLocation.hashCode()));
        result = ((result* 31)+((this.includedSpsClause == null)? 0 :this.includedSpsClause.hashCode()));
        result = ((result* 31)+((this.providerSpsParty == null)? 0 :this.providerSpsParty.hashCode()));
        result = ((result* 31)+((this.actualDateTime == null)? 0 :this.actualDateTime.hashCode()));
        result = ((result* 31)+((this.typeCode == null)? 0 :this.typeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsAuthenticationType) == false) {
            return false;
        }
        SpsAuthenticationType rhs = ((SpsAuthenticationType) other);
        return (((((((this.locationProviderSpsParty == rhs.locationProviderSpsParty)||((this.locationProviderSpsParty!= null)&&this.locationProviderSpsParty.equals(rhs.locationProviderSpsParty)))&&((this.issueSpsLocation == rhs.issueSpsLocation)||((this.issueSpsLocation!= null)&&this.issueSpsLocation.equals(rhs.issueSpsLocation))))&&((this.includedSpsClause == rhs.includedSpsClause)||((this.includedSpsClause!= null)&&this.includedSpsClause.equals(rhs.includedSpsClause))))&&((this.providerSpsParty == rhs.providerSpsParty)||((this.providerSpsParty!= null)&&this.providerSpsParty.equals(rhs.providerSpsParty))))&&((this.actualDateTime == rhs.actualDateTime)||((this.actualDateTime!= null)&&this.actualDateTime.equals(rhs.actualDateTime))))&&((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode))));
    }

}
