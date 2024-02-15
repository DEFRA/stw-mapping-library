
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApplicableSpsClassification implements Serializable
{

    private CodeType classCode;
    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> className = new ArrayList<TextType>();
    private IDType systemID;
    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> systemName = new ArrayList<TextType>();
    private final static long serialVersionUID = -7326403450603196907L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ApplicableSpsClassification() {
    }

    /**
     * 
     * @param systemName
     * @param className
     */
    public ApplicableSpsClassification(List<TextType> className, List<TextType> systemName) {
        super();
        this.className = className;
        this.systemName = systemName;
    }

    public void setClassCode(CodeType classCode) {
        this.classCode = classCode;
    }

    public void setClassName(List<TextType> className) {
        this.className = className;
    }

    public void setSystemID(IDType systemID) {
        this.systemID = systemID;
    }

    public void setSystemName(List<TextType> systemName) {
        this.systemName = systemName;
    }

    public CodeType getClassCode() {
        return classCode;
    }

    public ApplicableSpsClassification withClassCode(CodeType classCode) {
        this.classCode = classCode;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<TextType> getClassName() {
        return className;
    }

    public ApplicableSpsClassification withClassName(List<TextType> className) {
        this.className = className;
        return this;
    }

    public IDType getSystemID() {
        return systemID;
    }

    public ApplicableSpsClassification withSystemID(IDType systemID) {
        this.systemID = systemID;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<TextType> getSystemName() {
        return systemName;
    }

    public ApplicableSpsClassification withSystemName(List<TextType> systemName) {
        this.systemName = systemName;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ApplicableSpsClassification.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("classCode");
        sb.append('=');
        sb.append(((this.classCode == null)?"<null>":this.classCode));
        sb.append(',');
        sb.append("className");
        sb.append('=');
        sb.append(((this.className == null)?"<null>":this.className));
        sb.append(',');
        sb.append("systemID");
        sb.append('=');
        sb.append(((this.systemID == null)?"<null>":this.systemID));
        sb.append(',');
        sb.append("systemName");
        sb.append('=');
        sb.append(((this.systemName == null)?"<null>":this.systemName));
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
        result = ((result* 31)+((this.classCode == null)? 0 :this.classCode.hashCode()));
        result = ((result* 31)+((this.className == null)? 0 :this.className.hashCode()));
        result = ((result* 31)+((this.systemID == null)? 0 :this.systemID.hashCode()));
        result = ((result* 31)+((this.systemName == null)? 0 :this.systemName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ApplicableSpsClassification) == false) {
            return false;
        }
        ApplicableSpsClassification rhs = ((ApplicableSpsClassification) other);
        return (((((this.classCode == rhs.classCode)||((this.classCode!= null)&&this.classCode.equals(rhs.classCode)))&&((this.className == rhs.className)||((this.className!= null)&&this.className.equals(rhs.className))))&&((this.systemID == rhs.systemID)||((this.systemID!= null)&&this.systemID.equals(rhs.systemID))))&&((this.systemName == rhs.systemName)||((this.systemName!= null)&&this.systemName.equals(rhs.systemName))));
    }

}
