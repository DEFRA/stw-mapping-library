
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;

public class DefinedSpsContact implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private TextType personName;
    private final static long serialVersionUID = -1199888371161122290L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DefinedSpsContact() {
    }

    /**
     * 
     * @param personName
     */
    public DefinedSpsContact(TextType personName) {
        super();
        this.personName = personName;
    }

    public void setPersonName(TextType personName) {
        this.personName = personName;
    }

    /**
     * 
     * (Required)
     * 
     */
    public TextType getPersonName() {
        return personName;
    }

    public DefinedSpsContact withPersonName(TextType personName) {
        this.personName = personName;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DefinedSpsContact.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("personName");
        sb.append('=');
        sb.append(((this.personName == null)?"<null>":this.personName));
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
        result = ((result* 31)+((this.personName == null)? 0 :this.personName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DefinedSpsContact) == false) {
            return false;
        }
        DefinedSpsContact rhs = ((DefinedSpsContact) other);
        return ((this.personName == rhs.personName)||((this.personName!= null)&&this.personName.equals(rhs.personName)));
    }

}
