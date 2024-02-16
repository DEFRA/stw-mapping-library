
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsLocationType implements Serializable
{

    private IDType id;
    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> name = new ArrayList<TextType>();
    private final static long serialVersionUID = -9110060341626441735L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsLocationType() {
    }

    /**
     * 
     * @param name
     */
    public SpsLocationType(List<TextType> name) {
        super();
        this.name = name;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public void setName(List<TextType> name) {
        this.name = name;
    }

    public IDType getId() {
        return id;
    }

    public SpsLocationType withId(IDType id) {
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

    public SpsLocationType withName(List<TextType> name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsLocationType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
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
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsLocationType) == false) {
            return false;
        }
        SpsLocationType rhs = ((SpsLocationType) other);
        return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))));
    }

}
