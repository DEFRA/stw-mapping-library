
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IncludedSpsClause implements Serializable
{

    private List<TextType> content = new ArrayList<TextType>();
    private IDType id;
    private final static long serialVersionUID = 681095260659111936L;

    public IncludedSpsClause() {
    }

    public void setContent(List<TextType> content) {
        this.content = content;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public List<TextType> getContent() {
        return content;
    }

    public IncludedSpsClause withContent(List<TextType> content) {
        this.content = content;
        return this;
    }

    public IDType getId() {
        return id;
    }

    public IncludedSpsClause withId(IDType id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IncludedSpsClause.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null)?"<null>":this.content));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
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
        result = ((result* 31)+((this.content == null)? 0 :this.content.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IncludedSpsClause) == false) {
            return false;
        }
        IncludedSpsClause rhs = ((IncludedSpsClause) other);
        return (((this.content == rhs.content)||((this.content!= null)&&this.content.equals(rhs.content)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))));
    }

}
