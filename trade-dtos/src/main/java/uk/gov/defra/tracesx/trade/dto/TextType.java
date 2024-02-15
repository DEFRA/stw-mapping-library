
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class TextType implements Serializable
{
    private String languageID;
    private String languageLocaleID;
    private String value;
    private final static long serialVersionUID = -467932205017672315L;

    public TextType() {
    }

    public TextType(String languageID, String languageLocaleID, String value) {
        this.languageID = languageID;
        this.languageLocaleID = languageLocaleID;
        this.value = value;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public void setLanguageLocaleID(String languageLocaleID) {
        this.languageLocaleID = languageLocaleID;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguageID() {
        return languageID;
    }

    public TextType withLanguageID(String languageID) {
        this.languageID = languageID;
        return this;
    }

    public String getLanguageLocaleID() {
        return languageLocaleID;
    }

    public TextType withLanguageLocaleID(String languageLocaleID) {
        this.languageLocaleID = languageLocaleID;
        return this;
    }

    public String getValue() {
        return value;
    }

    public TextType withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TextType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("languageID");
        sb.append('=');
        sb.append(((this.languageID == null)?"<null>":this.languageID));
        sb.append(',');
        sb.append("languageLocaleID");
        sb.append('=');
        sb.append(((this.languageLocaleID == null)?"<null>":this.languageLocaleID));
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
        result = ((result* 31)+((this.languageID == null)? 0 :this.languageID.hashCode()));
        result = ((result* 31)+((this.languageLocaleID == null)? 0 :this.languageLocaleID.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TextType) == false) {
            return false;
        }
        TextType rhs = ((TextType) other);
        return ((((this.languageID == rhs.languageID)||((this.languageID!= null)&&this.languageID.equals(rhs.languageID)))&&((this.languageLocaleID == rhs.languageLocaleID)||((this.languageLocaleID!= null)&&this.languageLocaleID.equals(rhs.languageLocaleID))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))));
    }

}
