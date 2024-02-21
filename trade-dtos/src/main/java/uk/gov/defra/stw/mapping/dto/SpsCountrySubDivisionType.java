
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsCountrySubDivisionType implements Serializable
{

    private List<SpsPartyType> activityAuthorizedSpsParty = new ArrayList<SpsPartyType>();
    private FunctionTypeCode functionTypeCode;
    /**
     * 
     * (Required)
     * 
     */
    private CodeType hierarchicalLevelCode;
    private IDType id;
    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> name = new ArrayList<TextType>();
    private List<SpsCountrySubDivisionType> subordinateSpsCountrySubDivision = new ArrayList<SpsCountrySubDivisionType>();
    private List<SpsCountrySubDivisionType> superordinateSpsCountrySubDivision = new ArrayList<SpsCountrySubDivisionType>();
    private final static long serialVersionUID = -4671995690827948924L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsCountrySubDivisionType() {
    }

    /**
     * 
     * @param hierarchicalLevelCode
     * @param name
     */
    public SpsCountrySubDivisionType(CodeType hierarchicalLevelCode, List<TextType> name) {
        super();
        this.hierarchicalLevelCode = hierarchicalLevelCode;
        this.name = name;
    }

    public void setActivityAuthorizedSpsParty(
        List<SpsPartyType> activityAuthorizedSpsParty) {
        this.activityAuthorizedSpsParty = activityAuthorizedSpsParty;
    }

    public void setFunctionTypeCode(FunctionTypeCode functionTypeCode) {
        this.functionTypeCode = functionTypeCode;
    }

    public void setHierarchicalLevelCode(CodeType hierarchicalLevelCode) {
        this.hierarchicalLevelCode = hierarchicalLevelCode;
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

    public void setSuperordinateSpsCountrySubDivision(
        List<SpsCountrySubDivisionType> superordinateSpsCountrySubDivision) {
        this.superordinateSpsCountrySubDivision = superordinateSpsCountrySubDivision;
    }

    public List<SpsPartyType> getActivityAuthorizedSpsParty() {
        return activityAuthorizedSpsParty;
    }

    public SpsCountrySubDivisionType withActivityAuthorizedSpsParty(List<SpsPartyType> activityAuthorizedSpsParty) {
        this.activityAuthorizedSpsParty = activityAuthorizedSpsParty;
        return this;
    }

    public FunctionTypeCode getFunctionTypeCode() {
        return functionTypeCode;
    }

    public SpsCountrySubDivisionType withFunctionTypeCode(FunctionTypeCode functionTypeCode) {
        this.functionTypeCode = functionTypeCode;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public CodeType getHierarchicalLevelCode() {
        return hierarchicalLevelCode;
    }

    public SpsCountrySubDivisionType withHierarchicalLevelCode(CodeType hierarchicalLevelCode) {
        this.hierarchicalLevelCode = hierarchicalLevelCode;
        return this;
    }

    public IDType getId() {
        return id;
    }

    public SpsCountrySubDivisionType withId(IDType id) {
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

    public SpsCountrySubDivisionType withName(List<TextType> name) {
        this.name = name;
        return this;
    }

    public List<SpsCountrySubDivisionType> getSubordinateSpsCountrySubDivision() {
        return subordinateSpsCountrySubDivision;
    }

    public SpsCountrySubDivisionType withSubordinateSpsCountrySubDivision(List<SpsCountrySubDivisionType> subordinateSpsCountrySubDivision) {
        this.subordinateSpsCountrySubDivision = subordinateSpsCountrySubDivision;
        return this;
    }

    public List<SpsCountrySubDivisionType> getSuperordinateSpsCountrySubDivision() {
        return superordinateSpsCountrySubDivision;
    }

    public SpsCountrySubDivisionType withSuperordinateSpsCountrySubDivision(List<SpsCountrySubDivisionType> superordinateSpsCountrySubDivision) {
        this.superordinateSpsCountrySubDivision = superordinateSpsCountrySubDivision;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsCountrySubDivisionType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("activityAuthorizedSpsParty");
        sb.append('=');
        sb.append(((this.activityAuthorizedSpsParty == null)?"<null>":this.activityAuthorizedSpsParty));
        sb.append(',');
        sb.append("functionTypeCode");
        sb.append('=');
        sb.append(((this.functionTypeCode == null)?"<null>":this.functionTypeCode));
        sb.append(',');
        sb.append("hierarchicalLevelCode");
        sb.append('=');
        sb.append(((this.hierarchicalLevelCode == null)?"<null>":this.hierarchicalLevelCode));
        sb.append(',');
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
        sb.append("superordinateSpsCountrySubDivision");
        sb.append('=');
        sb.append(((this.superordinateSpsCountrySubDivision == null)?"<null>":this.superordinateSpsCountrySubDivision));
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
        result = ((result* 31)+((this.functionTypeCode == null)? 0 :this.functionTypeCode.hashCode()));
        result = ((result* 31)+((this.subordinateSpsCountrySubDivision == null)? 0 :this.subordinateSpsCountrySubDivision.hashCode()));
        result = ((result* 31)+((this.superordinateSpsCountrySubDivision == null)? 0 :this.superordinateSpsCountrySubDivision.hashCode()));
        result = ((result* 31)+((this.hierarchicalLevelCode == null)? 0 :this.hierarchicalLevelCode.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.activityAuthorizedSpsParty == null)? 0 :this.activityAuthorizedSpsParty.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsCountrySubDivisionType) == false) {
            return false;
        }
        SpsCountrySubDivisionType rhs = ((SpsCountrySubDivisionType) other);
        return ((((((((this.functionTypeCode == rhs.functionTypeCode)||((this.functionTypeCode!= null)&&this.functionTypeCode.equals(rhs.functionTypeCode)))&&((this.subordinateSpsCountrySubDivision == rhs.subordinateSpsCountrySubDivision)||((this.subordinateSpsCountrySubDivision!= null)&&this.subordinateSpsCountrySubDivision.equals(rhs.subordinateSpsCountrySubDivision))))&&((this.superordinateSpsCountrySubDivision == rhs.superordinateSpsCountrySubDivision)||((this.superordinateSpsCountrySubDivision!= null)&&this.superordinateSpsCountrySubDivision.equals(rhs.superordinateSpsCountrySubDivision))))&&((this.hierarchicalLevelCode == rhs.hierarchicalLevelCode)||((this.hierarchicalLevelCode!= null)&&this.hierarchicalLevelCode.equals(rhs.hierarchicalLevelCode))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.activityAuthorizedSpsParty == rhs.activityAuthorizedSpsParty)||((this.activityAuthorizedSpsParty!= null)&&this.activityAuthorizedSpsParty.equals(rhs.activityAuthorizedSpsParty))));
    }

}
