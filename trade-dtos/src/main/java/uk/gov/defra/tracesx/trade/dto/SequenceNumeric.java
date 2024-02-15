
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class SequenceNumeric implements Serializable
{

    private String format;
    private Integer value;
    private final static long serialVersionUID = 825928242282733017L;

    public SequenceNumeric() {
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public SequenceNumeric withFormat(String format) {
        this.format = format;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public SequenceNumeric withValue(Integer value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SequenceNumeric.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("format");
        sb.append('=');
        sb.append(((this.format == null)?"<null>":this.format));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
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
        result = ((result* 31)+((this.format == null)? 0 :this.format.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SequenceNumeric) == false) {
            return false;
        }
        SequenceNumeric rhs = ((SequenceNumeric) other);
        return (((this.format == rhs.format)||((this.format!= null)&&this.format.equals(rhs.format)))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))));
    }

}
