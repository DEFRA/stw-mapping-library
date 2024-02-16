
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecifiedSpsPerson implements Serializable
{

    private List<AttainedSpsQualification> attainedSpsQualification = new ArrayList<AttainedSpsQualification>();
    private TextType name;
    private final static long serialVersionUID = 1313073474264588567L;

    public SpecifiedSpsPerson() {
    }

    public void setAttainedSpsQualification(
        List<AttainedSpsQualification> attainedSpsQualification) {
        this.attainedSpsQualification = attainedSpsQualification;
    }

    public void setName(TextType name) {
        this.name = name;
    }

    public List<AttainedSpsQualification> getAttainedSpsQualification() {
        return attainedSpsQualification;
    }

    public SpecifiedSpsPerson withAttainedSpsQualification(List<AttainedSpsQualification> attainedSpsQualification) {
        this.attainedSpsQualification = attainedSpsQualification;
        return this;
    }

    public TextType getName() {
        return name;
    }

    public SpecifiedSpsPerson withName(TextType name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpecifiedSpsPerson.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("attainedSpsQualification");
        sb.append('=');
        sb.append(((this.attainedSpsQualification == null)?"<null>":this.attainedSpsQualification));
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
        result = ((result* 31)+((this.attainedSpsQualification == null)? 0 :this.attainedSpsQualification.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpecifiedSpsPerson) == false) {
            return false;
        }
        SpecifiedSpsPerson rhs = ((SpecifiedSpsPerson) other);
        return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.attainedSpsQualification == rhs.attainedSpsQualification)||((this.attainedSpsQualification!= null)&&this.attainedSpsQualification.equals(rhs.attainedSpsQualification))));
    }

}
