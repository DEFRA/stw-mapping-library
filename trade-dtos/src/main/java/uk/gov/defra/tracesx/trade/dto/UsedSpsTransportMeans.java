
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class UsedSpsTransportMeans implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private TextType name;
    private final static long serialVersionUID = 1248683508969157813L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UsedSpsTransportMeans() {
    }

    /**
     * 
     * @param name
     */
    public UsedSpsTransportMeans(TextType name) {
        super();
        this.name = name;
    }

    public void setName(TextType name) {
        this.name = name;
    }

    /**
     * 
     * (Required)
     * 
     */
    public TextType getName() {
        return name;
    }

    public UsedSpsTransportMeans withName(TextType name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(UsedSpsTransportMeans.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UsedSpsTransportMeans) == false) {
            return false;
        }
        UsedSpsTransportMeans rhs = ((UsedSpsTransportMeans) other);
        return ((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)));
    }

}
