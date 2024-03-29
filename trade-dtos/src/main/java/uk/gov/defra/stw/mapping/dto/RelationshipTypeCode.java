
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RelationshipTypeCode implements Serializable
{

    private String listAgencyID;
    private String listID;
    private String listVersionID;
    private String name;
    private RelationshipTypeCode.Value value;
    private final static long serialVersionUID = 488043651188476106L;

    public RelationshipTypeCode() {
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

    public RelationshipTypeCode withListAgencyID(String listAgencyID) {
        this.listAgencyID = listAgencyID;
        return this;
    }

    public String getListID() {
        return listID;
    }

    public RelationshipTypeCode withListID(String listID) {
        this.listID = listID;
        return this;
    }

    public String getListVersionID() {
        return listVersionID;
    }

    public RelationshipTypeCode withListVersionID(String listVersionID) {
        this.listVersionID = listVersionID;
        return this;
    }

    public String getName() {
        return name;
    }

    public RelationshipTypeCode withName(String name) {
        this.name = name;
        return this;
    }

    public RelationshipTypeCode.Value getValue() {
        return value;
    }

    public RelationshipTypeCode withValue(RelationshipTypeCode.Value value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(RelationshipTypeCode.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof RelationshipTypeCode) == false) {
            return false;
        }
        RelationshipTypeCode rhs = ((RelationshipTypeCode) other);
        return ((((((this.listID == rhs.listID)||((this.listID!= null)&&this.listID.equals(rhs.listID)))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.listAgencyID == rhs.listAgencyID)||((this.listAgencyID!= null)&&this.listAgencyID.equals(rhs.listAgencyID))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.listVersionID == rhs.listVersionID)||((this.listVersionID!= null)&&this.listVersionID.equals(rhs.listVersionID))));
    }

    public enum Value {

        AAA("AAA"),
        AAB("AAB"),
        AAC("AAC"),
        AAD("AAD"),
        AAE("AAE"),
        AAF("AAF"),
        AAG("AAG"),
        AAH("AAH"),
        AAI("AAI"),
        AAJ("AAJ"),
        AAK("AAK"),
        AAL("AAL"),
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
        ABQ("ABQ"),
        ABR("ABR"),
        ABS("ABS"),
        ABT("ABT"),
        ABU("ABU"),
        ABV("ABV"),
        ABW("ABW"),
        ABX("ABX"),
        ABY("ABY"),
        ABZ("ABZ"),
        AC("AC"),
        ACA("ACA"),
        ACB("ACB"),
        ACC("ACC"),
        ACD("ACD"),
        ACE("ACE"),
        ACF("ACF"),
        ACG("ACG"),
        ACH("ACH"),
        ACI("ACI"),
        ACJ("ACJ"),
        ACK("ACK"),
        ACL("ACL"),
        ACN("ACN"),
        ACO("ACO"),
        ACP("ACP"),
        ACQ("ACQ"),
        ACR("ACR"),
        ACT("ACT"),
        ACU("ACU"),
        ACV("ACV"),
        ACW("ACW"),
        ACX("ACX"),
        ACY("ACY"),
        ACZ("ACZ"),
        ADA("ADA"),
        ADB("ADB"),
        ADC("ADC"),
        ADD("ADD"),
        ADE("ADE"),
        ADF("ADF"),
        ADG("ADG"),
        ADI("ADI"),
        ADJ("ADJ"),
        ADK("ADK"),
        ADL("ADL"),
        ADM("ADM"),
        ADN("ADN"),
        ADO("ADO"),
        ADP("ADP"),
        ADQ("ADQ"),
        ADT("ADT"),
        ADU("ADU"),
        ADV("ADV"),
        ADW("ADW"),
        ADX("ADX"),
        ADY("ADY"),
        ADZ("ADZ"),
        AE("AE"),
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
        AFY("AFY"),
        AFZ("AFZ"),
        AGA("AGA"),
        AGB("AGB"),
        AGC("AGC"),
        AGD("AGD"),
        AGE("AGE"),
        AGF("AGF"),
        AGG("AGG"),
        AGH("AGH"),
        AGI("AGI"),
        AGJ("AGJ"),
        AGK("AGK"),
        AGL("AGL"),
        AGM("AGM"),
        AGN("AGN"),
        AGO("AGO"),
        AGP("AGP"),
        AGQ("AGQ"),
        AGR("AGR"),
        AGS("AGS"),
        AGT("AGT"),
        AGU("AGU"),
        AGV("AGV"),
        AGW("AGW"),
        AGX("AGX"),
        AGY("AGY"),
        AGZ("AGZ"),
        AHA("AHA"),
        AHB("AHB"),
        AHC("AHC"),
        AHD("AHD"),
        AHE("AHE"),
        AHF("AHF"),
        AHG("AHG"),
        AHH("AHH"),
        AHI("AHI"),
        AHJ("AHJ"),
        AHK("AHK"),
        AHL("AHL"),
        AHM("AHM"),
        AHN("AHN"),
        AHO("AHO"),
        AHP("AHP"),
        AHQ("AHQ"),
        AHR("AHR"),
        AHS("AHS"),
        AHT("AHT"),
        AHU("AHU"),
        AHV("AHV"),
        AHX("AHX"),
        AHY("AHY"),
        AHZ("AHZ"),
        AIA("AIA"),
        AIB("AIB"),
        AIC("AIC"),
        AID("AID"),
        AIE("AIE"),
        AIF("AIF"),
        AIG("AIG"),
        AIH("AIH"),
        AII("AII"),
        AIJ("AIJ"),
        AIK("AIK"),
        AIL("AIL"),
        AIM("AIM"),
        AIN("AIN"),
        AIO("AIO"),
        AIP("AIP"),
        AIQ("AIQ"),
        AIR("AIR"),
        AIS("AIS"),
        AIT("AIT"),
        AIU("AIU"),
        AIV("AIV"),
        AIW("AIW"),
        AIX("AIX"),
        AIY("AIY"),
        AIZ("AIZ"),
        AJA("AJA"),
        AJB("AJB"),
        AJC("AJC"),
        AJD("AJD"),
        AJE("AJE"),
        AJF("AJF"),
        AJG("AJG"),
        AJH("AJH"),
        AJI("AJI"),
        AJJ("AJJ"),
        AJK("AJK"),
        AJL("AJL"),
        AJM("AJM"),
        AJN("AJN"),
        AJO("AJO"),
        AJP("AJP"),
        AJQ("AJQ"),
        AJR("AJR"),
        AJS("AJS"),
        AJT("AJT"),
        AJU("AJU"),
        AJV("AJV"),
        AJW("AJW"),
        AJX("AJX"),
        AJY("AJY"),
        AJZ("AJZ"),
        AKA("AKA"),
        AKB("AKB"),
        AKC("AKC"),
        AKD("AKD"),
        AKE("AKE"),
        AKF("AKF"),
        AKG("AKG"),
        AKH("AKH"),
        AKI("AKI"),
        AKJ("AKJ"),
        AKK("AKK"),
        AKL("AKL"),
        AKM("AKM"),
        AKN("AKN"),
        AKO("AKO"),
        AKP("AKP"),
        AKQ("AKQ"),
        AKR("AKR"),
        AKS("AKS"),
        AKT("AKT"),
        AKU("AKU"),
        AKV("AKV"),
        AKW("AKW"),
        AKX("AKX"),
        AKY("AKY"),
        AKZ("AKZ"),
        ALA("ALA"),
        ALB("ALB"),
        ALC("ALC"),
        ALD("ALD"),
        ALE("ALE"),
        ALF("ALF"),
        ALG("ALG"),
        ALH("ALH"),
        ALI("ALI"),
        ALJ("ALJ"),
        ALK("ALK"),
        ALL("ALL"),
        ALM("ALM"),
        ALN("ALN"),
        ALO("ALO"),
        ALP("ALP"),
        ALQ("ALQ"),
        ALR("ALR"),
        ALS("ALS"),
        ALT("ALT"),
        ALU("ALU"),
        ALV("ALV"),
        ALW("ALW"),
        ALX("ALX"),
        ALY("ALY"),
        ALZ("ALZ"),
        AMA("AMA"),
        AMB("AMB"),
        AMC("AMC"),
        AMD("AMD"),
        AME("AME"),
        AMF("AMF"),
        AMG("AMG"),
        AMH("AMH"),
        AMI("AMI"),
        AMJ("AMJ"),
        AMK("AMK"),
        AML("AML"),
        AMM("AMM"),
        AMN("AMN"),
        AMO("AMO"),
        AMP("AMP"),
        AMQ("AMQ"),
        AMR("AMR"),
        AMS("AMS"),
        AMT("AMT"),
        AMU("AMU"),
        AMV("AMV"),
        AMW("AMW"),
        AMX("AMX"),
        AMY("AMY"),
        AMZ("AMZ"),
        ANA("ANA"),
        ANB("ANB"),
        ANC("ANC"),
        AND("AND"),
        ANE("ANE"),
        ANF("ANF"),
        ANG("ANG"),
        ANH("ANH"),
        ANI("ANI"),
        ANJ("ANJ"),
        ANK("ANK"),
        ANL("ANL"),
        ANM("ANM"),
        ANN("ANN"),
        ANO("ANO"),
        ANP("ANP"),
        ANQ("ANQ"),
        ANR("ANR"),
        ANS("ANS"),
        ANT("ANT"),
        ANU("ANU"),
        ANV("ANV"),
        ANW("ANW"),
        ANX("ANX"),
        ANY("ANY"),
        AOA("AOA"),
        AOD("AOD"),
        AOE("AOE"),
        AOF("AOF"),
        AOG("AOG"),
        AOH("AOH"),
        AOI("AOI"),
        AOJ("AOJ"),
        AOK("AOK"),
        AOL("AOL"),
        AOM("AOM"),
        AON("AON"),
        AOO("AOO"),
        AOP("AOP"),
        AOQ("AOQ"),
        AOR("AOR"),
        AOS("AOS"),
        AOT("AOT"),
        AOU("AOU"),
        AOV("AOV"),
        AOW("AOW"),
        AOX("AOX"),
        AOY("AOY"),
        AOZ("AOZ"),
        AP("AP"),
        APA("APA"),
        APB("APB"),
        APC("APC"),
        APD("APD"),
        APE("APE"),
        APF("APF"),
        APG("APG"),
        APH("APH"),
        API("API"),
        APJ("APJ"),
        APK("APK"),
        APL("APL"),
        APM("APM"),
        APN("APN"),
        APO("APO"),
        APP("APP"),
        APQ("APQ"),
        APR("APR"),
        APS("APS"),
        APT("APT"),
        APU("APU"),
        APV("APV"),
        APW("APW"),
        APX("APX"),
        APY("APY"),
        APZ("APZ"),
        AQA("AQA"),
        AQB("AQB"),
        AQC("AQC"),
        AQD("AQD"),
        AQE("AQE"),
        AQF("AQF"),
        AQG("AQG"),
        AQH("AQH"),
        AQI("AQI"),
        AQJ("AQJ"),
        AQK("AQK"),
        AQL("AQL"),
        AQM("AQM"),
        AQN("AQN"),
        AQO("AQO"),
        AQP("AQP"),
        AQQ("AQQ"),
        AQR("AQR"),
        AQS("AQS"),
        AQT("AQT"),
        AQU("AQU"),
        AQV("AQV"),
        AQW("AQW"),
        AQX("AQX"),
        AQY("AQY"),
        AQZ("AQZ"),
        ARA("ARA"),
        ARB("ARB"),
        ARC("ARC"),
        ARD("ARD"),
        ARE("ARE"),
        ARF("ARF"),
        ARG("ARG"),
        ARH("ARH"),
        ARI("ARI"),
        ARJ("ARJ"),
        ARK("ARK"),
        ARL("ARL"),
        ARM("ARM"),
        ARN("ARN"),
        ARO("ARO"),
        ARP("ARP"),
        ARQ("ARQ"),
        ARR("ARR"),
        ARS("ARS"),
        ART("ART"),
        ARU("ARU"),
        ARV("ARV"),
        ARW("ARW"),
        ARX("ARX"),
        ARY("ARY"),
        ARZ("ARZ"),
        ASA("ASA"),
        ASB("ASB"),
        ASC("ASC"),
        ASD("ASD"),
        ASE("ASE"),
        ASF("ASF"),
        ASG("ASG"),
        ASH("ASH"),
        ASI("ASI"),
        ASJ("ASJ"),
        ASK("ASK"),
        ASL("ASL"),
        ASM("ASM"),
        ASN("ASN"),
        ASO("ASO"),
        ASP("ASP"),
        ASQ("ASQ"),
        ASR("ASR"),
        ASS("ASS"),
        AST("AST"),
        ASU("ASU"),
        ASV("ASV"),
        ASW("ASW"),
        ASX("ASX"),
        ASY("ASY"),
        ASZ("ASZ"),
        ATA("ATA"),
        ATB("ATB"),
        ATC("ATC"),
        ATD("ATD"),
        ATE("ATE"),
        ATF("ATF"),
        ATG("ATG"),
        ATH("ATH"),
        ATI("ATI"),
        ATJ("ATJ"),
        ATK("ATK"),
        ATL("ATL"),
        ATM("ATM"),
        ATN("ATN"),
        ATO("ATO"),
        ATP("ATP"),
        ATQ("ATQ"),
        ATR("ATR"),
        ATS("ATS"),
        ATT("ATT"),
        ATU("ATU"),
        ATV("ATV"),
        ATW("ATW"),
        ATX("ATX"),
        ATY("ATY"),
        ATZ("ATZ"),
        AU("AU"),
        AUA("AUA"),
        AUB("AUB"),
        AUC("AUC"),
        AUD("AUD"),
        AUE("AUE"),
        AUF("AUF"),
        AUG("AUG"),
        AUH("AUH"),
        AUI("AUI"),
        AUJ("AUJ"),
        AUK("AUK"),
        AUL("AUL"),
        AUM("AUM"),
        AUN("AUN"),
        AUO("AUO"),
        AUP("AUP"),
        AUQ("AUQ"),
        AUR("AUR"),
        AUS("AUS"),
        AUT("AUT"),
        AUU("AUU"),
        AUV("AUV"),
        AUW("AUW"),
        AUX("AUX"),
        AUY("AUY"),
        AUZ("AUZ"),
        AV("AV"),
        AVA("AVA"),
        AVB("AVB"),
        AVC("AVC"),
        AVD("AVD"),
        AVE("AVE"),
        AVF("AVF"),
        AVG("AVG"),
        AVH("AVH"),
        AVI("AVI"),
        AVJ("AVJ"),
        AVK("AVK"),
        AVL("AVL"),
        AVM("AVM"),
        AVN("AVN"),
        AVO("AVO"),
        AVP("AVP"),
        AVQ("AVQ"),
        AVR("AVR"),
        AVS("AVS"),
        AVT("AVT"),
        AVU("AVU"),
        AVV("AVV"),
        AVW("AVW"),
        AVX("AVX"),
        AVY("AVY"),
        AVZ("AVZ"),
        AWA("AWA"),
        AWB("AWB"),
        AWC("AWC"),
        AWD("AWD"),
        AWE("AWE"),
        AWF("AWF"),
        AWG("AWG"),
        AWH("AWH"),
        AWI("AWI"),
        AWJ("AWJ"),
        AWK("AWK"),
        AWL("AWL"),
        AWM("AWM"),
        AWN("AWN"),
        AWO("AWO"),
        AWP("AWP"),
        AWQ("AWQ"),
        AWR("AWR"),
        AWS("AWS"),
        AWT("AWT"),
        AWU("AWU"),
        AWV("AWV"),
        AWW("AWW"),
        AWX("AWX"),
        AWY("AWY"),
        AWZ("AWZ"),
        AXA("AXA"),
        AXB("AXB"),
        AXC("AXC"),
        AXD("AXD"),
        AXE("AXE"),
        AXF("AXF"),
        AXG("AXG"),
        AXH("AXH"),
        AXI("AXI"),
        AXJ("AXJ"),
        AXK("AXK"),
        AXL("AXL"),
        AXM("AXM"),
        AXN("AXN"),
        AXO("AXO"),
        AXP("AXP"),
        AXQ("AXQ"),
        AXR("AXR"),
        BA("BA"),
        BC("BC"),
        BD("BD"),
        BE("BE"),
        BH("BH"),
        BM("BM"),
        BN("BN"),
        BO("BO"),
        BR("BR"),
        BT("BT"),
        BW("BW"),
        CAS("CAS"),
        CAT("CAT"),
        CAU("CAU"),
        CAV("CAV"),
        CAW("CAW"),
        CAX("CAX"),
        CAY("CAY"),
        CAZ("CAZ"),
        CBA("CBA"),
        CBB("CBB"),
        CD("CD"),
        CEC("CEC"),
        CED("CED"),
        CFE("CFE"),
        CFF("CFF"),
        CFO("CFO"),
        CG("CG"),
        CH("CH"),
        CK("CK"),
        CKN("CKN"),
        CM("CM"),
        CMR("CMR"),
        CN("CN"),
        CNO("CNO"),
        COF("COF"),
        CP("CP"),
        CR("CR"),
        CRN("CRN"),
        CS("CS"),
        CST("CST"),
        CT("CT"),
        CU("CU"),
        CV("CV"),
        CW("CW"),
        CZ("CZ"),
        DA("DA"),
        DAN("DAN"),
        DB("DB"),
        DI("DI"),
        DL("DL"),
        DM("DM"),
        DQ("DQ"),
        DR("DR"),
        EA("EA"),
        EB("EB"),
        ED("ED"),
        EE("EE"),
        EI("EI"),
        EN("EN"),
        EQ("EQ"),
        ER("ER"),
        ERN("ERN"),
        ET("ET"),
        EX("EX"),
        FC("FC"),
        FF("FF"),
        FI("FI"),
        FLW("FLW"),
        FN("FN"),
        FO("FO"),
        FS("FS"),
        FT("FT"),
        FV("FV"),
        FX("FX"),
        GA("GA"),
        GC("GC"),
        GD("GD"),
        GDN("GDN"),
        GN("GN"),
        HS("HS"),
        HWB("HWB"),
        IA("IA"),
        IB("IB"),
        ICA("ICA"),
        ICE("ICE"),
        ICO("ICO"),
        II("II"),
        IL("IL"),
        INB("INB"),
        INN("INN"),
        INO("INO"),
        IP("IP"),
        IS("IS"),
        IT("IT"),
        IV("IV"),
        JB("JB"),
        JE("JE"),
        LA("LA"),
        LAN("LAN"),
        LAR("LAR"),
        LB("LB"),
        LC("LC"),
        LI("LI"),
        LO("LO"),
        LRC("LRC"),
        LS("LS"),
        MA("MA"),
        MB("MB"),
        MF("MF"),
        MG("MG"),
        MH("MH"),
        MR("MR"),
        MRN("MRN"),
        MS("MS"),
        MSS("MSS"),
        MWB("MWB"),
        NA("NA"),
        NF("NF"),
        OH("OH"),
        OI("OI"),
        ON("ON"),
        OP("OP"),
        OR("OR"),
        PB("PB"),
        PC("PC"),
        PD("PD"),
        PE("PE"),
        PF("PF"),
        PI("PI"),
        PK("PK"),
        PL("PL"),
        POR("POR"),
        PP("PP"),
        PQ("PQ"),
        PR("PR"),
        PS("PS"),
        PW("PW"),
        PY("PY"),
        RA("RA"),
        RC("RC"),
        RCN("RCN"),
        RE("RE"),
        REN("REN"),
        RF("RF"),
        RR("RR"),
        RT("RT"),
        SA("SA"),
        SB("SB"),
        SD("SD"),
        SE("SE"),
        SEA("SEA"),
        SF("SF"),
        SH("SH"),
        SI("SI"),
        SM("SM"),
        SN("SN"),
        SP("SP"),
        SQ("SQ"),
        SRN("SRN"),
        SS("SS"),
        STA("STA"),
        SW("SW"),
        SZ("SZ"),
        TB("TB"),
        TCR("TCR"),
        TE("TE"),
        TF("TF"),
        TI("TI"),
        TIN("TIN"),
        TL("TL"),
        TN("TN"),
        TP("TP"),
        UAR("UAR"),
        UC("UC"),
        UCN("UCN"),
        UN("UN"),
        UO("UO"),
        URI("URI"),
        VA("VA"),
        VC("VC"),
        VGR("VGR"),
        VM("VM"),
        VN("VN"),
        VON("VON"),
        VOR("VOR"),
        VP("VP"),
        VR("VR"),
        VS("VS"),
        VT("VT"),
        VV("VV"),
        WE("WE"),
        WM("WM"),
        WN("WN"),
        WR("WR"),
        WS("WS"),
        WY("WY"),
        XA("XA"),
        XC("XC"),
        XP("XP"),
        ZZZ("ZZZ");
        private final String value;
        private final static Map<String, RelationshipTypeCode.Value> CONSTANTS = new HashMap<String, RelationshipTypeCode.Value>();

        static {
            for (RelationshipTypeCode.Value c: values()) {
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

        public static RelationshipTypeCode.Value fromValue(String value) {
            RelationshipTypeCode.Value constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
