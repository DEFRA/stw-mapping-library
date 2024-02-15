
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class CompletionSpsPeriod implements Serializable
{

    private MeasureType durationMeasure;
    private DateTimeType endDateTime;
    /**
     * 
     * (Required)
     * 
     */
    private DateTimeType startDateTime;
    private final static long serialVersionUID = -2515182386946543538L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CompletionSpsPeriod() {
    }

    /**
     * 
     * @param startDateTime
     */
    public CompletionSpsPeriod(DateTimeType startDateTime) {
        super();
        this.startDateTime = startDateTime;
    }

    public void setDurationMeasure(MeasureType durationMeasure) {
        this.durationMeasure = durationMeasure;
    }

    public void setEndDateTime(DateTimeType endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setStartDateTime(DateTimeType startDateTime) {
        this.startDateTime = startDateTime;
    }

    public MeasureType getDurationMeasure() {
        return durationMeasure;
    }

    public CompletionSpsPeriod withDurationMeasure(MeasureType durationMeasure) {
        this.durationMeasure = durationMeasure;
        return this;
    }

    public DateTimeType getEndDateTime() {
        return endDateTime;
    }

    public CompletionSpsPeriod withEndDateTime(DateTimeType endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public DateTimeType getStartDateTime() {
        return startDateTime;
    }

    public CompletionSpsPeriod withStartDateTime(DateTimeType startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CompletionSpsPeriod.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("durationMeasure");
        sb.append('=');
        sb.append(((this.durationMeasure == null)?"<null>":this.durationMeasure));
        sb.append(',');
        sb.append("endDateTime");
        sb.append('=');
        sb.append(((this.endDateTime == null)?"<null>":this.endDateTime));
        sb.append(',');
        sb.append("startDateTime");
        sb.append('=');
        sb.append(((this.startDateTime == null)?"<null>":this.startDateTime));
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
        result = ((result* 31)+((this.startDateTime == null)? 0 :this.startDateTime.hashCode()));
        result = ((result* 31)+((this.endDateTime == null)? 0 :this.endDateTime.hashCode()));
        result = ((result* 31)+((this.durationMeasure == null)? 0 :this.durationMeasure.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CompletionSpsPeriod) == false) {
            return false;
        }
        CompletionSpsPeriod rhs = ((CompletionSpsPeriod) other);
        return ((((this.startDateTime == rhs.startDateTime)||((this.startDateTime!= null)&&this.startDateTime.equals(rhs.startDateTime)))&&((this.endDateTime == rhs.endDateTime)||((this.endDateTime!= null)&&this.endDateTime.equals(rhs.endDateTime))))&&((this.durationMeasure == rhs.durationMeasure)||((this.durationMeasure!= null)&&this.durationMeasure.equals(rhs.durationMeasure))));
    }

}
