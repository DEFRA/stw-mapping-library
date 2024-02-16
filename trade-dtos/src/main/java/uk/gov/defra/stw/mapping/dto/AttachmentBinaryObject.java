
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttachmentBinaryObject implements Serializable
{

    private String characterSetCode;
    private String encodingCode;
    private String filename;
    private String format;
    private String mimeCode;
    private String uri;
    private List<String> value = new ArrayList<String>();
    private final static long serialVersionUID = 1037604327858338847L;

    public AttachmentBinaryObject() {
    }

    public void setCharacterSetCode(String characterSetCode) {
        this.characterSetCode = characterSetCode;
    }

    public void setEncodingCode(String encodingCode) {
        this.encodingCode = encodingCode;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setMimeCode(String mimeCode) {
        this.mimeCode = mimeCode;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public String getCharacterSetCode() {
        return characterSetCode;
    }

    public AttachmentBinaryObject withCharacterSetCode(String characterSetCode) {
        this.characterSetCode = characterSetCode;
        return this;
    }

    public String getEncodingCode() {
        return encodingCode;
    }

    public AttachmentBinaryObject withEncodingCode(String encodingCode) {
        this.encodingCode = encodingCode;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public AttachmentBinaryObject withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public AttachmentBinaryObject withFormat(String format) {
        this.format = format;
        return this;
    }

    public String getMimeCode() {
        return mimeCode;
    }

    public AttachmentBinaryObject withMimeCode(String mimeCode) {
        this.mimeCode = mimeCode;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public AttachmentBinaryObject withUri(String uri) {
        this.uri = uri;
        return this;
    }

    public List<String> getValue() {
        return value;
    }

    public AttachmentBinaryObject withValue(List<String> value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AttachmentBinaryObject.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("characterSetCode");
        sb.append('=');
        sb.append(((this.characterSetCode == null)?"<null>":this.characterSetCode));
        sb.append(',');
        sb.append("encodingCode");
        sb.append('=');
        sb.append(((this.encodingCode == null)?"<null>":this.encodingCode));
        sb.append(',');
        sb.append("filename");
        sb.append('=');
        sb.append(((this.filename == null)?"<null>":this.filename));
        sb.append(',');
        sb.append("format");
        sb.append('=');
        sb.append(((this.format == null)?"<null>":this.format));
        sb.append(',');
        sb.append("mimeCode");
        sb.append('=');
        sb.append(((this.mimeCode == null)?"<null>":this.mimeCode));
        sb.append(',');
        sb.append("uri");
        sb.append('=');
        sb.append(((this.uri == null)?"<null>":this.uri));
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
        result = ((result* 31)+((this.encodingCode == null)? 0 :this.encodingCode.hashCode()));
        result = ((result* 31)+((this.filename == null)? 0 :this.filename.hashCode()));
        result = ((result* 31)+((this.format == null)? 0 :this.format.hashCode()));
        result = ((result* 31)+((this.characterSetCode == null)? 0 :this.characterSetCode.hashCode()));
        result = ((result* 31)+((this.uri == null)? 0 :this.uri.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.mimeCode == null)? 0 :this.mimeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AttachmentBinaryObject) == false) {
            return false;
        }
        AttachmentBinaryObject rhs = ((AttachmentBinaryObject) other);
        return ((((((((this.encodingCode == rhs.encodingCode)||((this.encodingCode!= null)&&this.encodingCode.equals(rhs.encodingCode)))&&((this.filename == rhs.filename)||((this.filename!= null)&&this.filename.equals(rhs.filename))))&&((this.format == rhs.format)||((this.format!= null)&&this.format.equals(rhs.format))))&&((this.characterSetCode == rhs.characterSetCode)||((this.characterSetCode!= null)&&this.characterSetCode.equals(rhs.characterSetCode))))&&((this.uri == rhs.uri)||((this.uri!= null)&&this.uri.equals(rhs.uri))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.mimeCode == rhs.mimeCode)||((this.mimeCode!= null)&&this.mimeCode.equals(rhs.mimeCode))));
    }

}
