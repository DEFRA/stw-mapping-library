
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppliedSpsProcess implements Serializable
{

    private List<ApplicableSpsProcessCharacteristic> applicableSpsProcessCharacteristic = new ArrayList<ApplicableSpsProcessCharacteristic>();
    private CompletionSpsPeriod completionSpsPeriod;
    private SpsCountryType operationSpsCountry;
    private SpsPartyType operatorSpsParty;
    /**
     * 
     * (Required)
     * 
     */
    private ProcessTypeCodeType typeCode;
    private final static long serialVersionUID = 2394880811488228442L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AppliedSpsProcess() {
    }

    /**
     * 
     * @param typeCode
     */
    public AppliedSpsProcess(ProcessTypeCodeType typeCode) {
        super();
        this.typeCode = typeCode;
    }

    public void setApplicableSpsProcessCharacteristic(
        List<ApplicableSpsProcessCharacteristic> applicableSpsProcessCharacteristic) {
        this.applicableSpsProcessCharacteristic = applicableSpsProcessCharacteristic;
    }

    public void setCompletionSpsPeriod(
        CompletionSpsPeriod completionSpsPeriod) {
        this.completionSpsPeriod = completionSpsPeriod;
    }

    public void setOperationSpsCountry(SpsCountryType operationSpsCountry) {
        this.operationSpsCountry = operationSpsCountry;
    }

    public void setOperatorSpsParty(SpsPartyType operatorSpsParty) {
        this.operatorSpsParty = operatorSpsParty;
    }

    public void setTypeCode(ProcessTypeCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public List<ApplicableSpsProcessCharacteristic> getApplicableSpsProcessCharacteristic() {
        return applicableSpsProcessCharacteristic;
    }

    public AppliedSpsProcess withApplicableSpsProcessCharacteristic(List<ApplicableSpsProcessCharacteristic> applicableSpsProcessCharacteristic) {
        this.applicableSpsProcessCharacteristic = applicableSpsProcessCharacteristic;
        return this;
    }

    public CompletionSpsPeriod getCompletionSpsPeriod() {
        return completionSpsPeriod;
    }

    public AppliedSpsProcess withCompletionSpsPeriod(CompletionSpsPeriod completionSpsPeriod) {
        this.completionSpsPeriod = completionSpsPeriod;
        return this;
    }

    public SpsCountryType getOperationSpsCountry() {
        return operationSpsCountry;
    }

    public AppliedSpsProcess withOperationSpsCountry(SpsCountryType operationSpsCountry) {
        this.operationSpsCountry = operationSpsCountry;
        return this;
    }

    public SpsPartyType getOperatorSpsParty() {
        return operatorSpsParty;
    }

    public AppliedSpsProcess withOperatorSpsParty(SpsPartyType operatorSpsParty) {
        this.operatorSpsParty = operatorSpsParty;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public ProcessTypeCodeType getTypeCode() {
        return typeCode;
    }

    public AppliedSpsProcess withTypeCode(ProcessTypeCodeType typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AppliedSpsProcess.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("applicableSpsProcessCharacteristic");
        sb.append('=');
        sb.append(((this.applicableSpsProcessCharacteristic == null)?"<null>":this.applicableSpsProcessCharacteristic));
        sb.append(',');
        sb.append("completionSpsPeriod");
        sb.append('=');
        sb.append(((this.completionSpsPeriod == null)?"<null>":this.completionSpsPeriod));
        sb.append(',');
        sb.append("operationSpsCountry");
        sb.append('=');
        sb.append(((this.operationSpsCountry == null)?"<null>":this.operationSpsCountry));
        sb.append(',');
        sb.append("operatorSpsParty");
        sb.append('=');
        sb.append(((this.operatorSpsParty == null)?"<null>":this.operatorSpsParty));
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
        result = ((result* 31)+((this.applicableSpsProcessCharacteristic == null)? 0 :this.applicableSpsProcessCharacteristic.hashCode()));
        result = ((result* 31)+((this.operatorSpsParty == null)? 0 :this.operatorSpsParty.hashCode()));
        result = ((result* 31)+((this.completionSpsPeriod == null)? 0 :this.completionSpsPeriod.hashCode()));
        result = ((result* 31)+((this.operationSpsCountry == null)? 0 :this.operationSpsCountry.hashCode()));
        result = ((result* 31)+((this.typeCode == null)? 0 :this.typeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AppliedSpsProcess) == false) {
            return false;
        }
        AppliedSpsProcess rhs = ((AppliedSpsProcess) other);
        return ((((((this.applicableSpsProcessCharacteristic == rhs.applicableSpsProcessCharacteristic)||((this.applicableSpsProcessCharacteristic!= null)&&this.applicableSpsProcessCharacteristic.equals(rhs.applicableSpsProcessCharacteristic)))&&((this.operatorSpsParty == rhs.operatorSpsParty)||((this.operatorSpsParty!= null)&&this.operatorSpsParty.equals(rhs.operatorSpsParty))))&&((this.completionSpsPeriod == rhs.completionSpsPeriod)||((this.completionSpsPeriod!= null)&&this.completionSpsPeriod.equals(rhs.completionSpsPeriod))))&&((this.operationSpsCountry == rhs.operationSpsCountry)||((this.operationSpsCountry!= null)&&this.operationSpsCountry.equals(rhs.operationSpsCountry))))&&((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode))));
    }

}
