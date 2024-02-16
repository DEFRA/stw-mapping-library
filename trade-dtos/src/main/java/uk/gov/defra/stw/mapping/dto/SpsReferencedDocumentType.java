
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsReferencedDocumentType implements Serializable
{

    private List<AttachmentBinaryObject> attachmentBinaryObject = new ArrayList<AttachmentBinaryObject>();
    /**
     * 
     * (Required)
     * 
     */
    private IDType id;
    private TextType information;
    private String issueDateTime;
    /**
     * 
     * (Required)
     * 
     */
    private RelationshipTypeCode relationshipTypeCode;
    private DocumentCodeType typeCode;
    private final static long serialVersionUID = -3129973190394567532L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsReferencedDocumentType() {
    }

    /**
     * 
     * @param relationshipTypeCode
     * @param id
     */
    public SpsReferencedDocumentType(IDType id, RelationshipTypeCode relationshipTypeCode) {
        super();
        this.id = id;
        this.relationshipTypeCode = relationshipTypeCode;
    }

    public void setAttachmentBinaryObject(
        List<AttachmentBinaryObject> attachmentBinaryObject) {
        this.attachmentBinaryObject = attachmentBinaryObject;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public void setInformation(TextType information) {
        this.information = information;
    }

    public void setIssueDateTime(String issueDateTime) {
        this.issueDateTime = issueDateTime;
    }

    public void setRelationshipTypeCode(
        RelationshipTypeCode relationshipTypeCode) {
        this.relationshipTypeCode = relationshipTypeCode;
    }

    public void setTypeCode(DocumentCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public List<AttachmentBinaryObject> getAttachmentBinaryObject() {
        return attachmentBinaryObject;
    }

    public SpsReferencedDocumentType withAttachmentBinaryObject(List<AttachmentBinaryObject> attachmentBinaryObject) {
        this.attachmentBinaryObject = attachmentBinaryObject;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public IDType getId() {
        return id;
    }

    public SpsReferencedDocumentType withId(IDType id) {
        this.id = id;
        return this;
    }

    public TextType getInformation() {
        return information;
    }

    public SpsReferencedDocumentType withInformation(TextType information) {
        this.information = information;
        return this;
    }

    public String getIssueDateTime() {
        return issueDateTime;
    }

    public SpsReferencedDocumentType withIssueDateTime(String issueDateTime) {
        this.issueDateTime = issueDateTime;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public RelationshipTypeCode getRelationshipTypeCode() {
        return relationshipTypeCode;
    }

    public SpsReferencedDocumentType withRelationshipTypeCode(RelationshipTypeCode relationshipTypeCode) {
        this.relationshipTypeCode = relationshipTypeCode;
        return this;
    }

    public DocumentCodeType getTypeCode() {
        return typeCode;
    }

    public SpsReferencedDocumentType withTypeCode(DocumentCodeType typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsReferencedDocumentType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("attachmentBinaryObject");
        sb.append('=');
        sb.append(((this.attachmentBinaryObject == null)?"<null>":this.attachmentBinaryObject));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("information");
        sb.append('=');
        sb.append(((this.information == null)?"<null>":this.information));
        sb.append(',');
        sb.append("issueDateTime");
        sb.append('=');
        sb.append(((this.issueDateTime == null)?"<null>":this.issueDateTime));
        sb.append(',');
        sb.append("relationshipTypeCode");
        sb.append('=');
        sb.append(((this.relationshipTypeCode == null)?"<null>":this.relationshipTypeCode));
        sb.append(',');
        sb.append("typeCode");
        sb.append('=');
        sb.append(((this.typeCode == null)?"<null>":this.typeCode));
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
        result = ((result* 31)+((this.attachmentBinaryObject == null)? 0 :this.attachmentBinaryObject.hashCode()));
        result = ((result* 31)+((this.relationshipTypeCode == null)? 0 :this.relationshipTypeCode.hashCode()));
        result = ((result* 31)+((this.issueDateTime == null)? 0 :this.issueDateTime.hashCode()));
        result = ((result* 31)+((this.information == null)? 0 :this.information.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.typeCode == null)? 0 :this.typeCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsReferencedDocumentType) == false) {
            return false;
        }
        SpsReferencedDocumentType rhs = ((SpsReferencedDocumentType) other);
        return (((((((this.attachmentBinaryObject == rhs.attachmentBinaryObject)||((this.attachmentBinaryObject!= null)&&this.attachmentBinaryObject.equals(rhs.attachmentBinaryObject)))&&((this.relationshipTypeCode == rhs.relationshipTypeCode)||((this.relationshipTypeCode!= null)&&this.relationshipTypeCode.equals(rhs.relationshipTypeCode))))&&((this.issueDateTime == rhs.issueDateTime)||((this.issueDateTime!= null)&&this.issueDateTime.equals(rhs.issueDateTime))))&&((this.information == rhs.information)||((this.information!= null)&&this.information.equals(rhs.information))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode))));
    }

}
