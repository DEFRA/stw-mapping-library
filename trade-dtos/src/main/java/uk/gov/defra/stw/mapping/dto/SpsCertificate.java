
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;

public class SpsCertificate implements Serializable
{

    private SpsConsignment spsConsignment;
    private SpsExchangedDocument spsExchangedDocument;
    private final static long serialVersionUID = -7248807235443362065L;

    public SpsCertificate() {
    }

    public SpsCertificate(SpsConsignment spsConsignment,
        SpsExchangedDocument spsExchangedDocument) {
        this.spsConsignment = spsConsignment;
        this.spsExchangedDocument = spsExchangedDocument;
    }

    public void setSpsConsignment(SpsConsignment spsConsignment) {
        this.spsConsignment = spsConsignment;
    }

    public void setSpsExchangedDocument(
        SpsExchangedDocument spsExchangedDocument) {
        this.spsExchangedDocument = spsExchangedDocument;
    }

    public SpsConsignment getSpsConsignment() {
        return spsConsignment;
    }

    public SpsCertificate withSpsConsignment(SpsConsignment spsConsignment) {
        this.spsConsignment = spsConsignment;
        return this;
    }

    public SpsExchangedDocument getSpsExchangedDocument() {
        return spsExchangedDocument;
    }

    public SpsCertificate withSpsExchangedDocument(SpsExchangedDocument spsExchangedDocument) {
        this.spsExchangedDocument = spsExchangedDocument;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsCertificate.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("sPSConsignment");
        sb.append('=');
        sb.append(((this.spsConsignment == null)?"<null>":this.spsConsignment));
        sb.append(',');
        sb.append("sPSExchangedDocument");
        sb.append('=');
        sb.append(((this.spsExchangedDocument == null)?"<null>":this.spsExchangedDocument));
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
        result = ((result* 31)+((this.spsConsignment == null)? 0 :this.spsConsignment.hashCode()));
        result = ((result* 31)+((this.spsExchangedDocument == null)? 0 :this.spsExchangedDocument.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsCertificate) == false) {
            return false;
        }
        SpsCertificate rhs = ((SpsCertificate) other);
        return (((this.spsConsignment == rhs.spsConsignment)||((this.spsConsignment
            != null)&&this.spsConsignment.equals(rhs.spsConsignment)))&&((this.spsExchangedDocument
            == rhs.spsExchangedDocument)||((this.spsExchangedDocument
            != null)&&this.spsExchangedDocument.equals(rhs.spsExchangedDocument))));
    }

}
