
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class MainCarriageSpsTransportMovement implements Serializable
{

    private IDType id;
    /**
     * 
     * (Required)
     * 
     */
    private ModeCode modeCode;
    private UsedSpsTransportMeans usedSpsTransportMeans;
    private final static long serialVersionUID = -6971865816186247499L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MainCarriageSpsTransportMovement() {
    }

    /**
     * 
     * @param modeCode
     */
    public MainCarriageSpsTransportMovement(ModeCode modeCode) {
        super();
        this.modeCode = modeCode;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public void setModeCode(ModeCode modeCode) {
        this.modeCode = modeCode;
    }

    public void setUsedSpsTransportMeans(
        UsedSpsTransportMeans usedSpsTransportMeans) {
        this.usedSpsTransportMeans = usedSpsTransportMeans;
    }

    public IDType getId() {
        return id;
    }

    public MainCarriageSpsTransportMovement withId(IDType id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public ModeCode getModeCode() {
        return modeCode;
    }

    public MainCarriageSpsTransportMovement withModeCode(ModeCode modeCode) {
        this.modeCode = modeCode;
        return this;
    }

    public UsedSpsTransportMeans getUsedSpsTransportMeans() {
        return usedSpsTransportMeans;
    }

    public MainCarriageSpsTransportMovement withUsedSpsTransportMeans(
        UsedSpsTransportMeans usedSpsTransportMeans) {
        this.usedSpsTransportMeans = usedSpsTransportMeans;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MainCarriageSpsTransportMovement.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("modeCode");
        sb.append('=');
        sb.append(((this.modeCode == null)?"<null>":this.modeCode));
        sb.append(',');
        sb.append("usedSpsTransportMeans");
        sb.append('=');
        sb.append(((this.usedSpsTransportMeans == null)?"<null>":this.usedSpsTransportMeans));
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
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.usedSpsTransportMeans == null)? 0 :this.usedSpsTransportMeans.hashCode()));
        result = ((result* 31)+((this.modeCode == null)? 0 :this.modeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MainCarriageSpsTransportMovement) == false) {
            return false;
        }
        MainCarriageSpsTransportMovement rhs = ((MainCarriageSpsTransportMovement) other);
        return ((((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id)))&&((this.usedSpsTransportMeans == rhs.usedSpsTransportMeans)||((this.usedSpsTransportMeans!= null)&&this.usedSpsTransportMeans.equals(rhs.usedSpsTransportMeans))))&&((this.modeCode == rhs.modeCode)||((this.modeCode!= null)&&this.modeCode.equals(rhs.modeCode))));
    }

}
