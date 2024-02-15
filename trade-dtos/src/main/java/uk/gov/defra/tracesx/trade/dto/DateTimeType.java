
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class DateTimeType implements Serializable
{

    private DateTime dateTime;
    private DateTimeString dateTimeString;
    private final static long serialVersionUID = 2977081898429161166L;

    public DateTimeType() {
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDateTimeString(DateTimeString dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public DateTimeType withDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public DateTimeString getDateTimeString() {
        return dateTimeString;
    }

    public DateTimeType withDateTimeString(DateTimeString dateTimeString) {
        this.dateTimeString = dateTimeString;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DateTimeType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("dateTime");
        sb.append('=');
        sb.append(((this.dateTime == null)?"<null>":this.dateTime));
        sb.append(',');
        sb.append("dateTimeString");
        sb.append('=');
        sb.append(((this.dateTimeString == null)?"<null>":this.dateTimeString));
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
        result = ((result* 31)+((this.dateTime == null)? 0 :this.dateTime.hashCode()));
        result = ((result* 31)+((this.dateTimeString == null)? 0 :this.dateTimeString.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DateTimeType) == false) {
            return false;
        }
        DateTimeType rhs = ((DateTimeType) other);
        return (((this.dateTime == rhs.dateTime)||((this.dateTime!= null)&&this.dateTime.equals(rhs.dateTime)))&&((this.dateTimeString == rhs.dateTimeString)||((this.dateTimeString!= null)&&this.dateTimeString.equals(rhs.dateTimeString))));
    }

}
