
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsPartyType implements Serializable
{

    private List<DefinedSpsContact> definedSpsContact = new ArrayList<DefinedSpsContact>();
    private IDType id;
    /**
     * 
     * (Required)
     * 
     */
    private TextType name;
    private RoleCode roleCode;
    private SpecifiedSpsAddress specifiedSpsAddress;
    private SpecifiedSpsPerson specifiedSpsPerson;
    private List<CodeType> typeCode = new ArrayList<CodeType>();
    private final static long serialVersionUID = -2873674336958900905L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsPartyType() {
    }

    /**
     * 
     * @param name
     */
    public SpsPartyType(TextType name) {
        super();
        this.name = name;
    }

    public void setDefinedSpsContact(
        List<DefinedSpsContact> definedSpsContact) {
        this.definedSpsContact = definedSpsContact;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public void setName(TextType name) {
        this.name = name;
    }

    public void setRoleCode(RoleCode roleCode) {
        this.roleCode = roleCode;
    }

    public void setSpecifiedSpsAddress(
        SpecifiedSpsAddress specifiedSpsAddress) {
        this.specifiedSpsAddress = specifiedSpsAddress;
    }

    public void setSpecifiedSpsPerson(SpecifiedSpsPerson specifiedSpsPerson) {
        this.specifiedSpsPerson = specifiedSpsPerson;
    }

    public void setTypeCode(List<CodeType> typeCode) {
        this.typeCode = typeCode;
    }

    public List<DefinedSpsContact> getDefinedSpsContact() {
        return definedSpsContact;
    }

    public SpsPartyType withDefinedSpsContact(List<DefinedSpsContact> definedSpsContact) {
        this.definedSpsContact = definedSpsContact;
        return this;
    }

    public IDType getId() {
        return id;
    }

    public SpsPartyType withId(IDType id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public TextType getName() {
        return name;
    }

    public SpsPartyType withName(TextType name) {
        this.name = name;
        return this;
    }

    public RoleCode getRoleCode() {
        return roleCode;
    }

    public SpsPartyType withRoleCode(RoleCode roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    public SpecifiedSpsAddress getSpecifiedSpsAddress() {
        return specifiedSpsAddress;
    }

    public SpsPartyType withSpecifiedSpsAddress(SpecifiedSpsAddress specifiedSpsAddress) {
        this.specifiedSpsAddress = specifiedSpsAddress;
        return this;
    }

    public SpecifiedSpsPerson getSpecifiedSpsPerson() {
        return specifiedSpsPerson;
    }

    public SpsPartyType withSpecifiedSpsPerson(SpecifiedSpsPerson specifiedSpsPerson) {
        this.specifiedSpsPerson = specifiedSpsPerson;
        return this;
    }

    public List<CodeType> getTypeCode() {
        return typeCode;
    }

    public SpsPartyType withTypeCode(List<CodeType> typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsPartyType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("definedSpsContact");
        sb.append('=');
        sb.append(((this.definedSpsContact == null)?"<null>":this.definedSpsContact));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("roleCode");
        sb.append('=');
        sb.append(((this.roleCode == null)?"<null>":this.roleCode));
        sb.append(',');
        sb.append("specifiedSpsAddress");
        sb.append('=');
        sb.append(((this.specifiedSpsAddress == null)?"<null>":this.specifiedSpsAddress));
        sb.append(',');
        sb.append("specifiedSpsPerson");
        sb.append('=');
        sb.append(((this.specifiedSpsPerson == null)?"<null>":this.specifiedSpsPerson));
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
        result = ((result* 31)+((this.definedSpsContact == null)? 0 :this.definedSpsContact.hashCode()));
        result = ((result* 31)+((this.specifiedSpsPerson == null)? 0 :this.specifiedSpsPerson.hashCode()));
        result = ((result* 31)+((this.roleCode == null)? 0 :this.roleCode.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.specifiedSpsAddress == null)? 0 :this.specifiedSpsAddress.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.typeCode == null)? 0 :this.typeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsPartyType) == false) {
            return false;
        }
        SpsPartyType rhs = ((SpsPartyType) other);
        return ((((((((this.definedSpsContact == rhs.definedSpsContact)||((this.definedSpsContact!= null)&&this.definedSpsContact.equals(rhs.definedSpsContact)))&&((this.specifiedSpsPerson == rhs.specifiedSpsPerson)||((this.specifiedSpsPerson!= null)&&this.specifiedSpsPerson.equals(rhs.specifiedSpsPerson))))&&((this.roleCode == rhs.roleCode)||((this.roleCode!= null)&&this.roleCode.equals(rhs.roleCode))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.specifiedSpsAddress == rhs.specifiedSpsAddress)||((this.specifiedSpsAddress!= null)&&this.specifiedSpsAddress.equals(rhs.specifiedSpsAddress))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode))));
    }

}
