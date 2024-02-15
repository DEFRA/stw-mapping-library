
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class StatusCode implements Serializable
{

    private String listAgencyID;
    private String listID;
    private String listURI;
    private String listVersionID;
    private String name;
    private String value;
    private final static long serialVersionUID = 1444773494663435583L;

    public StatusCode() {
    }

    public void setListAgencyID(String listAgencyID) {
        this.listAgencyID = listAgencyID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    public void setListURI(String listURI) {
        this.listURI = listURI;
    }

    public void setListVersionID(String listVersionID) {
        this.listVersionID = listVersionID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getListAgencyID() {
        return listAgencyID;
    }

    public StatusCode withListAgencyID(String listAgencyID) {
        this.listAgencyID = listAgencyID;
        return this;
    }

    public String getListID() {
        return listID;
    }

    public StatusCode withListID(String listID) {
        this.listID = listID;
        return this;
    }

    public String getListURI() {
        return listURI;
    }

    public StatusCode withListURI(String listURI) {
        this.listURI = listURI;
        return this;
    }

    public String getListVersionID() {
        return listVersionID;
    }

    public StatusCode withListVersionID(String listVersionID) {
        this.listVersionID = listVersionID;
        return this;
    }

    public String getName() {
        return name;
    }

    public StatusCode withName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public StatusCode withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StatusCode.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("listAgencyID");
        sb.append('=');
        sb.append(((this.listAgencyID == null)?"<null>":this.listAgencyID));
        sb.append(',');
        sb.append("listID");
        sb.append('=');
        sb.append(((this.listID == null)?"<null>":this.listID));
        sb.append(',');
        sb.append("listURI");
        sb.append('=');
        sb.append(((this.listURI == null)?"<null>":this.listURI));
        sb.append(',');
        sb.append("listVersionID");
        sb.append('=');
        sb.append(((this.listVersionID == null)?"<null>":this.listVersionID));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
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
        result = ((result* 31)+((this.listID == null)? 0 :this.listID.hashCode()));
        result = ((result* 31)+((this.listURI == null)? 0 :this.listURI.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.listAgencyID == null)? 0 :this.listAgencyID.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.listVersionID == null)? 0 :this.listVersionID.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof StatusCode) == false) {
            return false;
        }
        StatusCode rhs = ((StatusCode) other);
        return (((((((this.listID == rhs.listID)||((this.listID!= null)&&this.listID.equals(rhs.listID)))&&((this.listURI == rhs.listURI)||((this.listURI!= null)&&this.listURI.equals(rhs.listURI))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.listAgencyID == rhs.listAgencyID)||((this.listAgencyID!= null)&&this.listAgencyID.equals(rhs.listAgencyID))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.listVersionID == rhs.listVersionID)||((this.listVersionID!= null)&&this.listVersionID.equals(rhs.listVersionID))));
    }

}
