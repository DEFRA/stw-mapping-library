
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsTransportEquipmentType implements Serializable
{

    private List<AffixedSpsSeal> affixedSpsSeal = new ArrayList<AffixedSpsSeal>();
    /**
     * 
     * (Required)
     * 
     */
    private IDType id;
    private List<SettingSpsTemperature> settingSpsTemperature = new ArrayList<SettingSpsTemperature>();
    private final static long serialVersionUID = -6755110458706279261L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsTransportEquipmentType() {
    }

    /**
     * 
     * @param id
     */
    public SpsTransportEquipmentType(IDType id) {
        super();
        this.id = id;
    }

    public void setAffixedSpsSeal(
        List<AffixedSpsSeal> affixedSpsSeal) {
        this.affixedSpsSeal = affixedSpsSeal;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public void setSettingSpsTemperature(
        List<SettingSpsTemperature> settingSpsTemperature) {
        this.settingSpsTemperature = settingSpsTemperature;
    }

    public List<AffixedSpsSeal> getAffixedSpsSeal() {
        return affixedSpsSeal;
    }

    public SpsTransportEquipmentType withAffixedSpsSeal(List<AffixedSpsSeal> affixedSpsSeal) {
        this.affixedSpsSeal = affixedSpsSeal;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public IDType getId() {
        return id;
    }

    public SpsTransportEquipmentType withId(IDType id) {
        this.id = id;
        return this;
    }

    public List<SettingSpsTemperature> getSettingSpsTemperature() {
        return settingSpsTemperature;
    }

    public SpsTransportEquipmentType withSettingSpsTemperature(List<SettingSpsTemperature> settingSpsTemperature) {
        this.settingSpsTemperature = settingSpsTemperature;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsTransportEquipmentType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("affixedSpsSeal");
        sb.append('=');
        sb.append(((this.affixedSpsSeal == null)?"<null>":this.affixedSpsSeal));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("settingSpsTemperature");
        sb.append('=');
        sb.append(((this.settingSpsTemperature == null)?"<null>":this.settingSpsTemperature));
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
        result = ((result* 31)+((this.affixedSpsSeal == null)? 0 :this.affixedSpsSeal.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.settingSpsTemperature == null)? 0 :this.settingSpsTemperature.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsTransportEquipmentType) == false) {
            return false;
        }
        SpsTransportEquipmentType rhs = ((SpsTransportEquipmentType) other);
        return ((((this.affixedSpsSeal == rhs.affixedSpsSeal)||((this.affixedSpsSeal!= null)&&this.affixedSpsSeal.equals(rhs.affixedSpsSeal)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.settingSpsTemperature == rhs.settingSpsTemperature)||((this.settingSpsTemperature!= null)&&this.settingSpsTemperature.equals(rhs.settingSpsTemperature))));
    }

}
