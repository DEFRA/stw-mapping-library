
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class IndicatorType implements Serializable
{

    private Boolean indicator;
    private IndicatorString indicatorString;
    private final static long serialVersionUID = 320677496128442932L;

    public IndicatorType() {
    }

    public void setIndicator(Boolean indicator) {
        this.indicator = indicator;
    }

    public void setIndicatorString(IndicatorString indicatorString) {
        this.indicatorString = indicatorString;
    }

    public Boolean getIndicator() {
        return indicator;
    }

    public IndicatorType withIndicator(Boolean indicator) {
        this.indicator = indicator;
        return this;
    }

    public IndicatorString getIndicatorString() {
        return indicatorString;
    }

    public IndicatorType withIndicatorString(IndicatorString indicatorString) {
        this.indicatorString = indicatorString;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IndicatorType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("indicator");
        sb.append('=');
        sb.append(((this.indicator == null)?"<null>":this.indicator));
        sb.append(',');
        sb.append("indicatorString");
        sb.append('=');
        sb.append(((this.indicatorString == null)?"<null>":this.indicatorString));
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
        result = ((result* 31)+((this.indicator == null)? 0 :this.indicator.hashCode()));
        result = ((result* 31)+((this.indicatorString == null)? 0 :this.indicatorString.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IndicatorType) == false) {
            return false;
        }
        IndicatorType rhs = ((IndicatorType) other);
        return (((this.indicator == rhs.indicator)||((this.indicator!= null)&&this.indicator.equals(rhs.indicator)))&&((this.indicatorString == rhs.indicatorString)||((this.indicatorString!= null)&&this.indicatorString.equals(rhs.indicatorString))));
    }

}
