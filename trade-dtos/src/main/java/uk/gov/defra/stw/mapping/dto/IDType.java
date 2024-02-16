
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;

public class IDType implements Serializable
{

    private String schemeAgencyID;
    private String schemeAgencyName;
    private String schemeDataURI;
    private String schemeID;
    private String schemeName;
    private String schemeURI;
    private String schemeVersionID;
    private String value;
    private final static long serialVersionUID = -8325876198970158565L;

    public IDType() {
    }

    public void setSchemeAgencyID(String schemeAgencyID) {
        this.schemeAgencyID = schemeAgencyID;
    }

    public void setSchemeAgencyName(String schemeAgencyName) {
        this.schemeAgencyName = schemeAgencyName;
    }

    public void setSchemeDataURI(String schemeDataURI) {
        this.schemeDataURI = schemeDataURI;
    }

    public void setSchemeID(String schemeID) {
        this.schemeID = schemeID;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public void setSchemeURI(String schemeURI) {
        this.schemeURI = schemeURI;
    }

    public void setSchemeVersionID(String schemeVersionID) {
        this.schemeVersionID = schemeVersionID;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSchemeAgencyID() {
        return schemeAgencyID;
    }

    public IDType withSchemeAgencyID(String schemeAgencyID) {
        this.schemeAgencyID = schemeAgencyID;
        return this;
    }

    public String getSchemeAgencyName() {
        return schemeAgencyName;
    }

    public IDType withSchemeAgencyName(String schemeAgencyName) {
        this.schemeAgencyName = schemeAgencyName;
        return this;
    }

    public String getSchemeDataURI() {
        return schemeDataURI;
    }

    public IDType withSchemeDataURI(String schemeDataURI) {
        this.schemeDataURI = schemeDataURI;
        return this;
    }

    public String getSchemeID() {
        return schemeID;
    }

    public IDType withSchemeID(String schemeID) {
        this.schemeID = schemeID;
        return this;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public IDType withSchemeName(String schemeName) {
        this.schemeName = schemeName;
        return this;
    }

    public String getSchemeURI() {
        return schemeURI;
    }

    public IDType withSchemeURI(String schemeURI) {
        this.schemeURI = schemeURI;
        return this;
    }

    public String getSchemeVersionID() {
        return schemeVersionID;
    }

    public IDType withSchemeVersionID(String schemeVersionID) {
        this.schemeVersionID = schemeVersionID;
        return this;
    }

    public String getValue() {
        return value;
    }

    public IDType withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IDType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("schemeAgencyID");
        sb.append('=');
        sb.append(((this.schemeAgencyID == null)?"<null>":this.schemeAgencyID));
        sb.append(',');
        sb.append("schemeAgencyName");
        sb.append('=');
        sb.append(((this.schemeAgencyName == null)?"<null>":this.schemeAgencyName));
        sb.append(',');
        sb.append("schemeDataURI");
        sb.append('=');
        sb.append(((this.schemeDataURI == null)?"<null>":this.schemeDataURI));
        sb.append(',');
        sb.append("schemeID");
        sb.append('=');
        sb.append(((this.schemeID == null)?"<null>":this.schemeID));
        sb.append(',');
        sb.append("schemeName");
        sb.append('=');
        sb.append(((this.schemeName == null)?"<null>":this.schemeName));
        sb.append(',');
        sb.append("schemeURI");
        sb.append('=');
        sb.append(((this.schemeURI == null)?"<null>":this.schemeURI));
        sb.append(',');
        sb.append("schemeVersionID");
        sb.append('=');
        sb.append(((this.schemeVersionID == null)?"<null>":this.schemeVersionID));
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
        result = ((result* 31)+((this.schemeDataURI == null)? 0 :this.schemeDataURI.hashCode()));
        result = ((result* 31)+((this.schemeURI == null)? 0 :this.schemeURI.hashCode()));
        result = ((result* 31)+((this.schemeName == null)? 0 :this.schemeName.hashCode()));
        result = ((result* 31)+((this.schemeAgencyID == null)? 0 :this.schemeAgencyID.hashCode()));
        result = ((result* 31)+((this.schemeID == null)? 0 :this.schemeID.hashCode()));
        result = ((result* 31)+((this.schemeVersionID == null)? 0 :this.schemeVersionID.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.schemeAgencyName == null)? 0 :this.schemeAgencyName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IDType) == false) {
            return false;
        }
        IDType rhs = ((IDType) other);
        return (((((((((this.schemeDataURI == rhs.schemeDataURI)||((this.schemeDataURI!= null)&&this.schemeDataURI.equals(rhs.schemeDataURI)))&&((this.schemeURI == rhs.schemeURI)||((this.schemeURI!= null)&&this.schemeURI.equals(rhs.schemeURI))))&&((this.schemeName == rhs.schemeName)||((this.schemeName!= null)&&this.schemeName.equals(rhs.schemeName))))&&((this.schemeAgencyID == rhs.schemeAgencyID)||((this.schemeAgencyID!= null)&&this.schemeAgencyID.equals(rhs.schemeAgencyID))))&&((this.schemeID == rhs.schemeID)||((this.schemeID!= null)&&this.schemeID.equals(rhs.schemeID))))&&((this.schemeVersionID == rhs.schemeVersionID)||((this.schemeVersionID!= null)&&this.schemeVersionID.equals(rhs.schemeVersionID))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.schemeAgencyName == rhs.schemeAgencyName)||((this.schemeAgencyName!= null)&&this.schemeAgencyName.equals(rhs.schemeAgencyName))));
    }

}
