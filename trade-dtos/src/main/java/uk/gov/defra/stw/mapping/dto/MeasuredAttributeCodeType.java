
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MeasuredAttributeCodeType implements Serializable
{

    private String listAgencyID;
    private String listID;
    private String listVersionID;
    private String name;
    private MeasuredAttributeCodeType.Value value;
    private final static long serialVersionUID = 5482938234518515159L;

    public MeasuredAttributeCodeType() {
    }

    public void setListAgencyID(String listAgencyID) {
        this.listAgencyID = listAgencyID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    public void setListVersionID(String listVersionID) {
        this.listVersionID = listVersionID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = Value.fromValue(value);
    }

    public String getListAgencyID() {
        return listAgencyID;
    }

    public MeasuredAttributeCodeType withListAgencyID(String listAgencyID) {
        this.listAgencyID = listAgencyID;
        return this;
    }

    public String getListID() {
        return listID;
    }

    public MeasuredAttributeCodeType withListID(String listID) {
        this.listID = listID;
        return this;
    }

    public String getListVersionID() {
        return listVersionID;
    }

    public MeasuredAttributeCodeType withListVersionID(String listVersionID) {
        this.listVersionID = listVersionID;
        return this;
    }

    public String getName() {
        return name;
    }

    public MeasuredAttributeCodeType withName(String name) {
        this.name = name;
        return this;
    }

    public MeasuredAttributeCodeType.Value getValue() {
        return value;
    }

    public MeasuredAttributeCodeType withValue(MeasuredAttributeCodeType.Value value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MeasuredAttributeCodeType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("listAgencyID");
        sb.append('=');
        sb.append(((this.listAgencyID == null)?"<null>":this.listAgencyID));
        sb.append(',');
        sb.append("listID");
        sb.append('=');
        sb.append(((this.listID == null)?"<null>":this.listID));
        sb.append(',');
        sb.append("listVersionID");
        sb.append('=');
        sb.append(((this.listVersionID == null)?"<null>":this.listVersionID));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
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
        result = ((result* 31)+((this.listID == null)? 0 :this.listID.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.listAgencyID == null)? 0 :this.listAgencyID.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.listVersionID == null)? 0 :this.listVersionID.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MeasuredAttributeCodeType) == false) {
            return false;
        }
        MeasuredAttributeCodeType rhs = ((MeasuredAttributeCodeType) other);
        return ((((((this.listID == rhs.listID)||((this.listID!= null)&&this.listID.equals(rhs.listID)))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.listAgencyID == rhs.listAgencyID)||((this.listAgencyID!= null)&&this.listAgencyID.equals(rhs.listAgencyID))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.listVersionID == rhs.listVersionID)||((this.listVersionID!= null)&&this.listVersionID.equals(rhs.listVersionID))));
    }

    public enum Value {

        A("A"),
        AAA("AAA"),
        AAB("AAB"),
        AAC("AAC"),
        AAD("AAD"),
        AAF("AAF"),
        AAG("AAG"),
        AAH("AAH"),
        AAI("AAI"),
        AAJ("AAJ"),
        AAK("AAK"),
        AAM("AAM"),
        AAN("AAN"),
        AAO("AAO"),
        AAP("AAP"),
        AAQ("AAQ"),
        AAR("AAR"),
        AAS("AAS"),
        AAT("AAT"),
        AAU("AAU"),
        AAV("AAV"),
        AAW("AAW"),
        AAX("AAX"),
        AAY("AAY"),
        AAZ("AAZ"),
        ABA("ABA"),
        ABB("ABB"),
        ABC("ABC"),
        ABD("ABD"),
        ABE("ABE"),
        ABF("ABF"),
        ABG("ABG"),
        ABH("ABH"),
        ABI("ABI"),
        ABJ("ABJ"),
        ABK("ABK"),
        ABL("ABL"),
        ABM("ABM"),
        ABN("ABN"),
        ABO("ABO"),
        ABP("ABP"),
        ABS("ABS"),
        ABT("ABT"),
        ABX("ABX"),
        ABY("ABY"),
        ABZ("ABZ"),
        ACA("ACA"),
        ACE("ACE"),
        ACG("ACG"),
        ACN("ACN"),
        ACP("ACP"),
        ACS("ACS"),
        ACV("ACV"),
        ACW("ACW"),
        ACX("ACX"),
        ADR("ADR"),
        ADS("ADS"),
        ADT("ADT"),
        ADU("ADU"),
        ADV("ADV"),
        ADW("ADW"),
        ADX("ADX"),
        ADY("ADY"),
        ADZ("ADZ"),
        AEA("AEA"),
        AEB("AEB"),
        AEC("AEC"),
        AED("AED"),
        AEE("AEE"),
        AEF("AEF"),
        AEG("AEG"),
        AEH("AEH"),
        AEI("AEI"),
        AEJ("AEJ"),
        AEK("AEK"),
        AEL("AEL"),
        AEM("AEM"),
        AEN("AEN"),
        AEO("AEO"),
        AEP("AEP"),
        AEQ("AEQ"),
        AER("AER"),
        AES("AES"),
        AET("AET"),
        AEU("AEU"),
        AEV("AEV"),
        AEW("AEW"),
        AEX("AEX"),
        AEY("AEY"),
        AEZ("AEZ"),
        AF("AF"),
        AFA("AFA"),
        AFB("AFB"),
        AFC("AFC"),
        AFD("AFD"),
        AFE("AFE"),
        AFF("AFF"),
        AFG("AFG"),
        AFH("AFH"),
        AFI("AFI"),
        AFJ("AFJ"),
        AFK("AFK"),
        AFL("AFL"),
        AFM("AFM"),
        AFN("AFN"),
        AFO("AFO"),
        AFP("AFP"),
        AFQ("AFQ"),
        AFR("AFR"),
        AFS("AFS"),
        AFT("AFT"),
        AFU("AFU"),
        AFV("AFV"),
        AFW("AFW"),
        AFX("AFX"),
        B("B"),
        BL("BL"),
        BMY("BMY"),
        BMZ("BMZ"),
        BNA("BNA"),
        BNB("BNB"),
        BNC("BNC"),
        BND("BND"),
        BNE("BNE"),
        BNF("BNF"),
        BNG("BNG"),
        BNH("BNH"),
        BNI("BNI"),
        BNJ("BNJ"),
        BNK("BNK"),
        BNL("BNL"),
        BNM("BNM"),
        BNN("BNN"),
        BNO("BNO"),
        BNP("BNP"),
        BNQ("BNQ"),
        BNR("BNR"),
        BNS("BNS"),
        BNT("BNT"),
        BNU("BNU"),
        BNV("BNV"),
        BNW("BNW"),
        BNX("BNX"),
        BNY("BNY"),
        BNZ("BNZ"),
        BR("BR"),
        BRA("BRA"),
        BRB("BRB"),
        BRC("BRC"),
        BRD("BRD"),
        BRE("BRE"),
        BRF("BRF"),
        BRG("BRG"),
        BRH("BRH"),
        BRI("BRI"),
        BRJ("BRJ"),
        BRK("BRK"),
        BRL("BRL"),
        BS("BS"),
        BSW("BSW"),
        BW("BW"),
        CHN("CHN"),
        CM("CM"),
        CT("CT"),
        CV("CV"),
        CZ("CZ"),
        D("D"),
        DI("DI"),
        DL("DL"),
        DN("DN"),
        DP("DP"),
        DR("DR"),
        DS("DS"),
        DW("DW"),
        E("E"),
        EA("EA"),
        F("F"),
        FI("FI"),
        FL("FL"),
        FN("FN"),
        FV("FV"),
        GG("GG"),
        GW("GW"),
        HF("HF"),
        HM("HM"),
        HT("HT"),
        IB("IB"),
        ID("ID"),
        L("L"),
        LM("LM"),
        LN("LN"),
        LND("LND"),
        M("M"),
        MO("MO"),
        MW("MW"),
        N("N"),
        OD("OD"),
        PRS("PRS"),
        PTN("PTN"),
        RA("RA"),
        RF("RF"),
        RJ("RJ"),
        RMW("RMW"),
        RP("RP"),
        RUN("RUN"),
        RY("RY"),
        SQ("SQ"),
        T("T"),
        TC("TC"),
        TH("TH"),
        TN("TN"),
        TT("TT"),
        VGM("VGM"),
        VH("VH"),
        VW("VW"),
        WA("WA"),
        WD("WD"),
        WM("WM"),
        WU("WU"),
        XH("XH"),
        XQ("XQ"),
        XZ("XZ"),
        YS("YS"),
        ZAL("ZAL"),
        ZAS("ZAS"),
        ZB("ZB"),
        ZBI("ZBI"),
        ZC("ZC"),
        ZCA("ZCA"),
        ZCB("ZCB"),
        ZCE("ZCE"),
        ZCL("ZCL"),
        ZCO("ZCO"),
        ZCR("ZCR"),
        ZCU("ZCU"),
        ZFE("ZFE"),
        ZFS("ZFS"),
        ZGE("ZGE"),
        ZH("ZH"),
        ZK("ZK"),
        ZMG("ZMG"),
        ZMN("ZMN"),
        ZMO("ZMO"),
        ZN("ZN"),
        ZNA("ZNA"),
        ZNB("ZNB"),
        ZNI("ZNI"),
        ZO("ZO"),
        ZP("ZP"),
        ZPB("ZPB"),
        ZS("ZS"),
        ZSB("ZSB"),
        ZSE("ZSE"),
        ZSI("ZSI"),
        ZSL("ZSL"),
        ZSN("ZSN"),
        ZTA("ZTA"),
        ZTE("ZTE"),
        ZTI("ZTI"),
        ZV("ZV"),
        ZW("ZW"),
        ZWA("ZWA"),
        ZZN("ZZN"),
        ZZR("ZZR"),
        ZZZ("ZZZ");
        private final String value;
        private final static Map<String, MeasuredAttributeCodeType.Value> CONSTANTS = new HashMap<String, MeasuredAttributeCodeType.Value>();

        static {
            for (MeasuredAttributeCodeType.Value c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Value(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static MeasuredAttributeCodeType.Value fromValue(String value) {
            MeasuredAttributeCodeType.Value constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
