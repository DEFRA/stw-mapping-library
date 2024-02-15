
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttainedSpsQualification implements Serializable
{

    private TextType abbreviatedName;
    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> name = new ArrayList<TextType>();
    private final static long serialVersionUID = 7062247245904810128L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AttainedSpsQualification() {
    }

    /**
     * 
     * @param name
     */
    public AttainedSpsQualification(List<TextType> name) {
        super();
        this.name = name;
    }

    public void setAbbreviatedName(TextType abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
    }

    public void setName(List<TextType> name) {
        this.name = name;
    }

    public TextType getAbbreviatedName() {
        return abbreviatedName;
    }

    public AttainedSpsQualification withAbbreviatedName(TextType abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<TextType> getName() {
        return name;
    }

    public AttainedSpsQualification withName(List<TextType> name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AttainedSpsQualification.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("abbreviatedName");
        sb.append('=');
        sb.append(((this.abbreviatedName == null)?"<null>":this.abbreviatedName));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.abbreviatedName == null)? 0 :this.abbreviatedName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AttainedSpsQualification) == false) {
            return false;
        }
        AttainedSpsQualification rhs = ((AttainedSpsQualification) other);
        return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.abbreviatedName == rhs.abbreviatedName)||((this.abbreviatedName!= null)&&this.abbreviatedName.equals(rhs.abbreviatedName))));
    }

}
