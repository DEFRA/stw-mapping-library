
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsNoteType implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    private List<TextType> content = new ArrayList<TextType>();
    private List<CodeType> contentCode = new ArrayList<CodeType>();
    private TextType subject;
    private CodeType subjectCode;
    private final static long serialVersionUID = 4798343690889479069L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsNoteType() {
    }

    /**
     * 
     * @param content
     */
    public SpsNoteType(List<TextType> content) {
        super();
        this.content = content;
    }

    public void setContent(List<TextType> content) {
        this.content = content;
    }

    public void setContentCode(List<CodeType> contentCode) {
        this.contentCode = contentCode;
    }

    public void setSubject(TextType subject) {
        this.subject = subject;
    }

    public void setSubjectCode(CodeType subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<TextType> getContent() {
        return content;
    }

    public SpsNoteType withContent(List<TextType> content) {
        this.content = content;
        return this;
    }

    public List<CodeType> getContentCode() {
        return contentCode;
    }

    public SpsNoteType withContentCode(List<CodeType> contentCode) {
        this.contentCode = contentCode;
        return this;
    }

    public TextType getSubject() {
        return subject;
    }

    public SpsNoteType withSubject(TextType subject) {
        this.subject = subject;
        return this;
    }

    public CodeType getSubjectCode() {
        return subjectCode;
    }

    public SpsNoteType withSubjectCode(CodeType subjectCode) {
        this.subjectCode = subjectCode;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsNoteType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null)?"<null>":this.content));
        sb.append(',');
        sb.append("contentCode");
        sb.append('=');
        sb.append(((this.contentCode == null)?"<null>":this.contentCode));
        sb.append(',');
        sb.append("subject");
        sb.append('=');
        sb.append(((this.subject == null)?"<null>":this.subject));
        sb.append(',');
        sb.append("subjectCode");
        sb.append('=');
        sb.append(((this.subjectCode == null)?"<null>":this.subjectCode));
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
        result = ((result* 31)+((this.contentCode == null)? 0 :this.contentCode.hashCode()));
        result = ((result* 31)+((this.subjectCode == null)? 0 :this.subjectCode.hashCode()));
        result = ((result* 31)+((this.content == null)? 0 :this.content.hashCode()));
        result = ((result* 31)+((this.subject == null)? 0 :this.subject.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsNoteType) == false) {
            return false;
        }
        SpsNoteType rhs = ((SpsNoteType) other);
        return (((((this.contentCode == rhs.contentCode)||((this.contentCode!= null)&&this.contentCode.equals(rhs.contentCode)))&&((this.subjectCode == rhs.subjectCode)||((this.subjectCode!= null)&&this.subjectCode.equals(rhs.subjectCode))))&&((this.content == rhs.content)||((this.content!= null)&&this.content.equals(rhs.content))))&&((this.subject == rhs.subject)||((this.subject!= null)&&this.subject.equals(rhs.subject))));
    }

}
