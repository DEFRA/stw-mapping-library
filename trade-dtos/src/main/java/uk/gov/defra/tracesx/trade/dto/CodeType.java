
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class CodeType implements Serializable
{

    private String languageID;
    private String listAgencyID;
    private String listAgencyName;
    private String listID;
    private String listName;
    private String listSchemeURI;
    private String listURI;
    private String listVersionID;
    private String name;
    private String value;
    private final static long serialVersionUID = 4930383723132015871L;

    public CodeType() {
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public void setListAgencyID(String listAgencyID) {
        this.listAgencyID = listAgencyID;
    }

    public void setListAgencyName(String listAgencyName) {
        this.listAgencyName = listAgencyName;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setListSchemeURI(String listSchemeURI) {
        this.listSchemeURI = listSchemeURI;
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

    public String getLanguageID() {
        return languageID;
    }

    public CodeType withLanguageID(String languageID) {
        this.languageID = languageID;
        return this;
    }

    public String getListAgencyID() {
        return listAgencyID;
    }

    public CodeType withListAgencyID(String listAgencyID) {
        this.listAgencyID = listAgencyID;
        return this;
    }

    public String getListAgencyName() {
        return listAgencyName;
    }

    public CodeType withListAgencyName(String listAgencyName) {
        this.listAgencyName = listAgencyName;
        return this;
    }

    public String getListID() {
        return listID;
    }

    public CodeType withListID(String listID) {
        this.listID = listID;
        return this;
    }

    public String getListName() {
        return listName;
    }

    public CodeType withListName(String listName) {
        this.listName = listName;
        return this;
    }

    public String getListSchemeURI() {
        return listSchemeURI;
    }

    public CodeType withListSchemeURI(String listSchemeURI) {
        this.listSchemeURI = listSchemeURI;
        return this;
    }

    public String getListURI() {
        return listURI;
    }

    public CodeType withListURI(String listURI) {
        this.listURI = listURI;
        return this;
    }

    public String getListVersionID() {
        return listVersionID;
    }

    public CodeType withListVersionID(String listVersionID) {
        this.listVersionID = listVersionID;
        return this;
    }

    public String getName() {
        return name;
    }

    public CodeType withName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public CodeType withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CodeType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("languageID");
        sb.append('=');
        sb.append(((this.languageID == null)?"<null>":this.languageID));
        sb.append(',');
        sb.append("listAgencyID");
        sb.append('=');
        sb.append(((this.listAgencyID == null)?"<null>":this.listAgencyID));
        sb.append(',');
        sb.append("listAgencyName");
        sb.append('=');
        sb.append(((this.listAgencyName == null)?"<null>":this.listAgencyName));
        sb.append(',');
        sb.append("listID");
        sb.append('=');
        sb.append(((this.listID == null)?"<null>":this.listID));
        sb.append(',');
        sb.append("listName");
        sb.append('=');
        sb.append(((this.listName == null)?"<null>":this.listName));
        sb.append(',');
        sb.append("listSchemeURI");
        sb.append('=');
        sb.append(((this.listSchemeURI == null)?"<null>":this.listSchemeURI));
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
        result = ((result* 31)+((this.listAgencyName == null)? 0 :this.listAgencyName.hashCode()));
        result = ((result* 31)+((this.listSchemeURI == null)? 0 :this.listSchemeURI.hashCode()));
        result = ((result* 31)+((this.listURI == null)? 0 :this.listURI.hashCode()));
        result = ((result* 31)+((this.languageID == null)? 0 :this.languageID.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.listName == null)? 0 :this.listName.hashCode()));
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
        if ((other instanceof CodeType) == false) {
            return false;
        }
        CodeType rhs = ((CodeType) other);
        return (((((((((((this.listID == rhs.listID)||((this.listID!= null)&&this.listID.equals(rhs.listID)))&&((this.listAgencyName == rhs.listAgencyName)||((this.listAgencyName!= null)&&this.listAgencyName.equals(rhs.listAgencyName))))&&((this.listSchemeURI == rhs.listSchemeURI)||((this.listSchemeURI!= null)&&this.listSchemeURI.equals(rhs.listSchemeURI))))&&((this.listURI == rhs.listURI)||((this.listURI!= null)&&this.listURI.equals(rhs.listURI))))&&((this.languageID == rhs.languageID)||((this.languageID!= null)&&this.languageID.equals(rhs.languageID))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.listName == rhs.listName)||((this.listName!= null)&&this.listName.equals(rhs.listName))))&&((this.listAgencyID == rhs.listAgencyID)||((this.listAgencyID!= null)&&this.listAgencyID.equals(rhs.listAgencyID))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.listVersionID == rhs.listVersionID)||((this.listVersionID!= null)&&this.listVersionID.equals(rhs.listVersionID))));
    }

}
