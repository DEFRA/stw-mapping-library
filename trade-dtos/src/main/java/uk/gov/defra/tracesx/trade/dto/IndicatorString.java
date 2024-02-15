
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class IndicatorString implements Serializable
{

    private String format;
    private String value;
    private final static long serialVersionUID = -4052324056685621080L;

    public IndicatorString() {
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public IndicatorString withFormat(String format) {
        this.format = format;
        return this;
    }

    public String getValue() {
        return value;
    }

    public IndicatorString withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IndicatorString.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof IndicatorString) == false) {
            return false;
        }
        IndicatorString rhs = ((IndicatorString) other);
        return (((this.format == rhs.format)||((this.format!= null)&&this.format.equals(rhs.format)))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))));
    }

}
