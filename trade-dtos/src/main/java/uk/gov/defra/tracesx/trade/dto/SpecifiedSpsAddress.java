
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;

public class SpecifiedSpsAddress implements Serializable
{

    private IDType cityID;
    private TextType cityName;
    private IDType countryID;
    private TextType countryName;
    private IDType countrySubDivisionID;
    private TextType countrySubDivisionName;
    private TextType lineFive;
    private TextType lineFour;
    private TextType lineOne;
    private TextType lineThree;
    private TextType lineTwo;
    private CodeType postcodeCode;
    private AddressTypeCodeType typeCode;
    private final static long serialVersionUID = 8143299535146651934L;

    public SpecifiedSpsAddress() {
    }

    public void setCityID(IDType cityID) {
        this.cityID = cityID;
    }

    public void setCityName(TextType cityName) {
        this.cityName = cityName;
    }

    public void setCountryID(IDType countryID) {
        this.countryID = countryID;
    }

    public void setCountryName(TextType countryName) {
        this.countryName = countryName;
    }

    public void setCountrySubDivisionID(IDType countrySubDivisionID) {
        this.countrySubDivisionID = countrySubDivisionID;
    }

    public void setCountrySubDivisionName(TextType countrySubDivisionName) {
        this.countrySubDivisionName = countrySubDivisionName;
    }

    public void setLineFive(TextType lineFive) {
        this.lineFive = lineFive;
    }

    public void setLineFour(TextType lineFour) {
        this.lineFour = lineFour;
    }

    public void setLineOne(TextType lineOne) {
        this.lineOne = lineOne;
    }

    public void setLineThree(TextType lineThree) {
        this.lineThree = lineThree;
    }

    public void setLineTwo(TextType lineTwo) {
        this.lineTwo = lineTwo;
    }

    public void setPostcodeCode(CodeType postcodeCode) {
        this.postcodeCode = postcodeCode;
    }

    public void setTypeCode(AddressTypeCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public IDType getCityID() {
        return cityID;
    }

    public SpecifiedSpsAddress withCityID(IDType cityID) {
        this.cityID = cityID;
        return this;
    }

    public TextType getCityName() {
        return cityName;
    }

    public SpecifiedSpsAddress withCityName(TextType cityName) {
        this.cityName = cityName;
        return this;
    }

    public IDType getCountryID() {
        return countryID;
    }

    public SpecifiedSpsAddress withCountryID(IDType countryID) {
        this.countryID = countryID;
        return this;
    }

    public TextType getCountryName() {
        return countryName;
    }

    public SpecifiedSpsAddress withCountryName(TextType countryName) {
        this.countryName = countryName;
        return this;
    }

    public IDType getCountrySubDivisionID() {
        return countrySubDivisionID;
    }

    public SpecifiedSpsAddress withCountrySubDivisionID(IDType countrySubDivisionID) {
        this.countrySubDivisionID = countrySubDivisionID;
        return this;
    }

    public TextType getCountrySubDivisionName() {
        return countrySubDivisionName;
    }

    public SpecifiedSpsAddress withCountrySubDivisionName(TextType countrySubDivisionName) {
        this.countrySubDivisionName = countrySubDivisionName;
        return this;
    }

    public TextType getLineFive() {
        return lineFive;
    }

    public SpecifiedSpsAddress withLineFive(TextType lineFive) {
        this.lineFive = lineFive;
        return this;
    }

    public TextType getLineFour() {
        return lineFour;
    }

    public SpecifiedSpsAddress withLineFour(TextType lineFour) {
        this.lineFour = lineFour;
        return this;
    }

    public TextType getLineOne() {
        return lineOne;
    }

    public SpecifiedSpsAddress withLineOne(TextType lineOne) {
        this.lineOne = lineOne;
        return this;
    }

    public TextType getLineThree() {
        return lineThree;
    }

    public SpecifiedSpsAddress withLineThree(TextType lineThree) {
        this.lineThree = lineThree;
        return this;
    }

    public TextType getLineTwo() {
        return lineTwo;
    }

    public SpecifiedSpsAddress withLineTwo(TextType lineTwo) {
        this.lineTwo = lineTwo;
        return this;
    }

    public CodeType getPostcodeCode() {
        return postcodeCode;
    }

    public SpecifiedSpsAddress withPostcodeCode(CodeType postcodeCode) {
        this.postcodeCode = postcodeCode;
        return this;
    }

    public AddressTypeCodeType getTypeCode() {
        return typeCode;
    }

    public SpecifiedSpsAddress withTypeCode(AddressTypeCodeType typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpecifiedSpsAddress.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cityID");
        sb.append('=');
        sb.append(((this.cityID == null)?"<null>":this.cityID));
        sb.append(',');
        sb.append("cityName");
        sb.append('=');
        sb.append(((this.cityName == null)?"<null>":this.cityName));
        sb.append(',');
        sb.append("countryID");
        sb.append('=');
        sb.append(((this.countryID == null)?"<null>":this.countryID));
        sb.append(',');
        sb.append("countryName");
        sb.append('=');
        sb.append(((this.countryName == null)?"<null>":this.countryName));
        sb.append(',');
        sb.append("countrySubDivisionID");
        sb.append('=');
        sb.append(((this.countrySubDivisionID == null)?"<null>":this.countrySubDivisionID));
        sb.append(',');
        sb.append("countrySubDivisionName");
        sb.append('=');
        sb.append(((this.countrySubDivisionName == null)?"<null>":this.countrySubDivisionName));
        sb.append(',');
        sb.append("lineFive");
        sb.append('=');
        sb.append(((this.lineFive == null)?"<null>":this.lineFive));
        sb.append(',');
        sb.append("lineFour");
        sb.append('=');
        sb.append(((this.lineFour == null)?"<null>":this.lineFour));
        sb.append(',');
        sb.append("lineOne");
        sb.append('=');
        sb.append(((this.lineOne == null)?"<null>":this.lineOne));
        sb.append(',');
        sb.append("lineThree");
        sb.append('=');
        sb.append(((this.lineThree == null)?"<null>":this.lineThree));
        sb.append(',');
        sb.append("lineTwo");
        sb.append('=');
        sb.append(((this.lineTwo == null)?"<null>":this.lineTwo));
        sb.append(',');
        sb.append("postcodeCode");
        sb.append('=');
        sb.append(((this.postcodeCode == null)?"<null>":this.postcodeCode));
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
        result = ((result* 31)+((this.postcodeCode == null)? 0 :this.postcodeCode.hashCode()));
        result = ((result* 31)+((this.lineFour == null)? 0 :this.lineFour.hashCode()));
        result = ((result* 31)+((this.cityID == null)? 0 :this.cityID.hashCode()));
        result = ((result* 31)+((this.countrySubDivisionID == null)? 0 :this.countrySubDivisionID.hashCode()));
        result = ((result* 31)+((this.countryID == null)? 0 :this.countryID.hashCode()));
        result = ((result* 31)+((this.countrySubDivisionName == null)? 0 :this.countrySubDivisionName.hashCode()));
        result = ((result* 31)+((this.typeCode == null)? 0 :this.typeCode.hashCode()));
        result = ((result* 31)+((this.lineTwo == null)? 0 :this.lineTwo.hashCode()));
        result = ((result* 31)+((this.cityName == null)? 0 :this.cityName.hashCode()));
        result = ((result* 31)+((this.lineFive == null)? 0 :this.lineFive.hashCode()));
        result = ((result* 31)+((this.lineOne == null)? 0 :this.lineOne.hashCode()));
        result = ((result* 31)+((this.countryName == null)? 0 :this.countryName.hashCode()));
        result = ((result* 31)+((this.lineThree == null)? 0 :this.lineThree.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpecifiedSpsAddress) == false) {
            return false;
        }
        SpecifiedSpsAddress rhs = ((SpecifiedSpsAddress) other);
        return ((((((((((((((this.postcodeCode == rhs.postcodeCode)||((this.postcodeCode!= null)&&this.postcodeCode.equals(rhs.postcodeCode)))&&((this.lineFour == rhs.lineFour)||((this.lineFour!= null)&&this.lineFour.equals(rhs.lineFour))))&&((this.cityID == rhs.cityID)||((this.cityID!= null)&&this.cityID.equals(rhs.cityID))))&&((this.countrySubDivisionID == rhs.countrySubDivisionID)||((this.countrySubDivisionID!= null)&&this.countrySubDivisionID.equals(rhs.countrySubDivisionID))))&&((this.countryID == rhs.countryID)||((this.countryID!= null)&&this.countryID.equals(rhs.countryID))))&&((this.countrySubDivisionName == rhs.countrySubDivisionName)||((this.countrySubDivisionName!= null)&&this.countrySubDivisionName.equals(rhs.countrySubDivisionName))))&&((this.typeCode == rhs.typeCode)||((this.typeCode!= null)&&this.typeCode.equals(rhs.typeCode))))&&((this.lineTwo == rhs.lineTwo)||((this.lineTwo!= null)&&this.lineTwo.equals(rhs.lineTwo))))&&((this.cityName == rhs.cityName)||((this.cityName!= null)&&this.cityName.equals(rhs.cityName))))&&((this.lineFive == rhs.lineFive)||((this.lineFive!= null)&&this.lineFive.equals(rhs.lineFive))))&&((this.lineOne == rhs.lineOne)||((this.lineOne!= null)&&this.lineOne.equals(rhs.lineOne))))&&((this.countryName == rhs.countryName)||((this.countryName!= null)&&this.countryName.equals(rhs.countryName))))&&((this.lineThree == rhs.lineThree)||((this.lineThree!= null)&&this.lineThree.equals(rhs.lineThree))));
    }

}
