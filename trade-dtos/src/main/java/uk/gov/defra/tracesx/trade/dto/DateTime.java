
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class DateTime implements Serializable
{

    private String value;
    private final static long serialVersionUID = -1582015548166853635L;

    public DateTime() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DateTime withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DateTime.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DateTime) == false) {
            return false;
        }
        DateTime rhs = ((DateTime) other);
        return true;
    }

}
