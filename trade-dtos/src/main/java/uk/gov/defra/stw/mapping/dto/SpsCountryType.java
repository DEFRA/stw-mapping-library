
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsCountryType implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private IDType id;
    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> name = new ArrayList<TextType>();
    private List<SpsCountrySubDivisionType> subordinateSpsCountrySubDivision = new ArrayList<SpsCountrySubDivisionType>();
    private final static long serialVersionUID = 7193998010151652937L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsCountryType() {
    }

    /**
     * 
     * @param name
     * @param id
     */
    public SpsCountryType(IDType id, List<TextType> name) {
        super();
        this.id = id;
        this.name = name;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public void setName(List<TextType> name) {
        this.name = name;
    }

    public void setSubordinateSpsCountrySubDivision(
        List<SpsCountrySubDivisionType> subordinateSpsCountrySubDivision) {
        this.subordinateSpsCountrySubDivision = subordinateSpsCountrySubDivision;
    }

    /**
     * 
     * (Required)
     * 
     */
    public IDType getId() {
        return id;
    }

    public SpsCountryType withId(IDType id) {
        this.id = id;
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

    public SpsCountryType withName(List<TextType> name) {
        this.name = name;
        return this;
    }

    public List<SpsCountrySubDivisionType> getSubordinateSpsCountrySubDivision() {
        return subordinateSpsCountrySubDivision;
    }

    public SpsCountryType withSubordinateSpsCountrySubDivision(List<SpsCountrySubDivisionType> subordinateSpsCountrySubDivision) {
        this.subordinateSpsCountrySubDivision = subordinateSpsCountrySubDivision;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsCountryType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("subordinateSpsCountrySubDivision");
        sb.append('=');
        sb.append(((this.subordinateSpsCountrySubDivision == null)?"<null>":this.subordinateSpsCountrySubDivision));
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
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.subordinateSpsCountrySubDivision == null)? 0 :this.subordinateSpsCountrySubDivision.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsCountryType) == false) {
            return false;
        }
        SpsCountryType rhs = ((SpsCountryType) other);
        return ((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.subordinateSpsCountrySubDivision == rhs.subordinateSpsCountrySubDivision)||((this.subordinateSpsCountrySubDivision!= null)&&this.subordinateSpsCountrySubDivision.equals(rhs.subordinateSpsCountrySubDivision))));
    }

}
