
package uk.gov.defra.tracesx.trade.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RoleCode implements Serializable
{

    private String listAgencyID;
    private String listID;
    private String listVersionID;
    private String name;
    private RoleCode.Value value;
    private final static long serialVersionUID = -8533531159455018565L;

    public RoleCode() {
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

    public RoleCode withListAgencyID(String listAgencyID) {
        this.listAgencyID = listAgencyID;
        return this;
    }

    public String getListID() {
        return listID;
    }

    public RoleCode withListID(String listID) {
        this.listID = listID;
        return this;
    }

    public String getListVersionID() {
        return listVersionID;
    }

    public RoleCode withListVersionID(String listVersionID) {
        this.listVersionID = listVersionID;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleCode withName(String name) {
        this.name = name;
        return this;
    }

    public RoleCode.Value getValue() {
        return value;
    }

    public RoleCode withValue(RoleCode.Value value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(RoleCode.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof RoleCode) == false) {
            return false;
        }
        RoleCode rhs = ((RoleCode) other);
        return ((((((this.listID == rhs.listID)||((this.listID!= null)&&this.listID.equals(rhs.listID)))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.listAgencyID == rhs.listAgencyID)||((this.listAgencyID!= null)&&this.listAgencyID.equals(rhs.listAgencyID))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.listVersionID == rhs.listVersionID)||((this.listVersionID!= null)&&this.listVersionID.equals(rhs.listVersionID))));
    }

    public enum Value {

        AA("AA"),
        AB("AB"),
        AE("AE"),
        AF("AF"),
        AG("AG"),
        AH("AH"),
        AI("AI"),
        AJ("AJ"),
        AK("AK"),
        AL("AL"),
        AM("AM"),
        AN("AN"),
        AO("AO"),
        AP("AP"),
        AQ("AQ"),
        AR("AR"),
        AS("AS"),
        AT("AT"),
        AU("AU"),
        AV("AV"),
        AW("AW"),
        AX("AX"),
        AY("AY"),
        AZ("AZ"),
        B_1("B_1"),
        B_2("B_2"),
        BA("BA"),
        BB("BB"),
        BC("BC"),
        BD("BD"),
        BE("BE"),
        BF("BF"),
        BG("BG"),
        BH("BH"),
        BI("BI"),
        BJ("BJ"),
        BK("BK"),
        BL("BL"),
        BM("BM"),
        BN("BN"),
        BO("BO"),
        BP("BP"),
        BQ("BQ"),
        BS("BS"),
        BT("BT"),
        BU("BU"),
        BV("BV"),
        BW("BW"),
        BX("BX"),
        BY("BY"),
        BZ("BZ"),
        C_1("C_1"),
        C_2("C_2"),
        CA("CA"),
        CB("CB"),
        CC("CC"),
        CD("CD"),
        CE("CE"),
        CF("CF"),
        CG("CG"),
        CH("CH"),
        CI("CI"),
        CJ("CJ"),
        CK("CK"),
        CL("CL"),
        CM("CM"),
        CN("CN"),
        CNX("CNX"),
        CNY("CNY"),
        CNZ("CNZ"),
        CO("CO"),
        COA("COA"),
        COB("COB"),
        COC("COC"),
        COD("COD"),
        COE("COE"),
        COF("COF"),
        COG("COG"),
        COH("COH"),
        COI("COI"),
        COJ("COJ"),
        COK("COK"),
        COL("COL"),
        COM("COM"),
        CON("CON"),
        COO("COO"),
        COP("COP"),
        COQ("COQ"),
        COR("COR"),
        COS("COS"),
        COT("COT"),
        COU("COU"),
        COV("COV"),
        COW("COW"),
        COX("COX"),
        COY("COY"),
        COZ("COZ"),
        CP("CP"),
        CPA("CPA"),
        CPB("CPB"),
        CPC("CPC"),
        CPD("CPD"),
        CPE("CPE"),
        CPF("CPF"),
        CPG("CPG"),
        CPH("CPH"),
        CPI("CPI"),
        CPJ("CPJ"),
        CPK("CPK"),
        CPL("CPL"),
        CPM("CPM"),
        CPN("CPN"),
        CPO("CPO"),
        CQ("CQ"),
        CR("CR"),
        CS("CS"),
        CT("CT"),
        CU("CU"),
        CV("CV"),
        CW("CW"),
        CX("CX"),
        CY("CY"),
        CZ("CZ"),
        DA("DA"),
        DB("DB"),
        DC("DC"),
        DCP("DCP"),
        DCQ("DCQ"),
        DCR("DCR"),
        DCS("DCS"),
        DCT("DCT"),
        DCU("DCU"),
        DCV("DCV"),
        DCW("DCW"),
        DCX("DCX"),
        DCY("DCY"),
        DCZ("DCZ"),
        DD("DD"),
        DDA("DDA"),
        DDB("DDB"),
        DDC("DDC"),
        DDD("DDD"),
        DDE("DDE"),
        DDF("DDF"),
        DDG("DDG"),
        DDH("DDH"),
        DDI("DDI"),
        DDJ("DDJ"),
        DDK("DDK"),
        DDL("DDL"),
        DDM("DDM"),
        DDN("DDN"),
        DDO("DDO"),
        DDP("DDP"),
        DDQ("DDQ"),
        DDR("DDR"),
        DDS("DDS"),
        DDT("DDT"),
        DDU("DDU"),
        DDV("DDV"),
        DDW("DDW"),
        DDX("DDX"),
        DDY("DDY"),
        DDZ("DDZ"),
        DE("DE"),
        DEA("DEA"),
        DEB("DEB"),
        DEC("DEC"),
        DED("DED"),
        DEE("DEE"),
        DEF("DEF"),
        DEG("DEG"),
        DEH("DEH"),
        DEI("DEI"),
        DEJ("DEJ"),
        DEK("DEK"),
        DEL("DEL"),
        DEM("DEM"),
        DEN("DEN"),
        DEO("DEO"),
        DEP("DEP"),
        DEQ("DEQ"),
        DER("DER"),
        DES("DES"),
        DET("DET"),
        DEU("DEU"),
        DEV("DEV"),
        DEW("DEW"),
        DEX("DEX"),
        DEY("DEY"),
        DEZ("DEZ"),
        DF("DF"),
        DFA("DFA"),
        DFB("DFB"),
        DFC("DFC"),
        DFD("DFD"),
        DFE("DFE"),
        DFF("DFF"),
        DFG("DFG"),
        DFH("DFH"),
        DFI("DFI"),
        DFJ("DFJ"),
        DFK("DFK"),
        DFL("DFL"),
        DFM("DFM"),
        DFN("DFN"),
        DFO("DFO"),
        DFP("DFP"),
        DFQ("DFQ"),
        DFR("DFR"),
        DFS("DFS"),
        DFT("DFT"),
        DFU("DFU"),
        DFV("DFV"),
        DFW("DFW"),
        DFX("DFX"),
        DFY("DFY"),
        DFZ("DFZ"),
        DG("DG"),
        DGA("DGA"),
        DGB("DGB"),
        DGC("DGC"),
        DGD("DGD"),
        DGE("DGE"),
        DH("DH"),
        DI("DI"),
        DJ("DJ"),
        DK("DK"),
        DL("DL"),
        DM("DM"),
        DN("DN"),
        DO("DO"),
        DP("DP"),
        DQ("DQ"),
        DR("DR"),
        DS("DS"),
        DT("DT"),
        DU("DU"),
        DV("DV"),
        DW("DW"),
        DX("DX"),
        DY("DY"),
        DZ("DZ"),
        EA("EA"),
        EB("EB"),
        EC("EC"),
        ED("ED"),
        EE("EE"),
        EF("EF"),
        EG("EG"),
        EH("EH"),
        EI("EI"),
        EJ("EJ"),
        EK("EK"),
        EL("EL"),
        EM("EM"),
        EN("EN"),
        EO("EO"),
        EP("EP"),
        EQ("EQ"),
        ER("ER"),
        ES("ES"),
        ET("ET"),
        EU("EU"),
        EV("EV"),
        EW("EW"),
        EX("EX"),
        EY("EY"),
        EZ("EZ"),
        FA("FA"),
        FB("FB"),
        FC("FC"),
        FD("FD"),
        FE("FE"),
        FF("FF"),
        FG("FG"),
        FH("FH"),
        FI("FI"),
        FJ("FJ"),
        FK("FK"),
        FL("FL"),
        FM("FM"),
        FN("FN"),
        FO("FO"),
        FP("FP"),
        FQ("FQ"),
        FR("FR"),
        FS("FS"),
        FT("FT"),
        FU("FU"),
        FV("FV"),
        FW("FW"),
        FX("FX"),
        FY("FY"),
        FZ("FZ"),
        GA("GA"),
        GB("GB"),
        GC("GC"),
        GD("GD"),
        GE("GE"),
        GF("GF"),
        GH("GH"),
        GI("GI"),
        GJ("GJ"),
        GK("GK"),
        GL("GL"),
        GM("GM"),
        GN("GN"),
        GO("GO"),
        GP("GP"),
        GQ("GQ"),
        GR("GR"),
        GS("GS"),
        GT("GT"),
        GU("GU"),
        GV("GV"),
        GW("GW"),
        GX("GX"),
        GY("GY"),
        GZ("GZ"),
        HA("HA"),
        HB("HB"),
        HC("HC"),
        HD("HD"),
        HE("HE"),
        HF("HF"),
        HG("HG"),
        HH("HH"),
        HI("HI"),
        HJ("HJ"),
        HK("HK"),
        HL("HL"),
        HM("HM"),
        HN("HN"),
        HO("HO"),
        HP("HP"),
        HQ("HQ"),
        HR("HR"),
        HS("HS"),
        HT("HT"),
        HU("HU"),
        HV("HV"),
        HW("HW"),
        HX("HX"),
        HY("HY"),
        HZ("HZ"),
        I_1("I_1"),
        I_2("I_2"),
        IB("IB"),
        IC("IC"),
        ID("ID"),
        IE("IE"),
        IF("IF"),
        IG("IG"),
        IH("IH"),
        II("II"),
        IJ("IJ"),
        IL("IL"),
        IM("IM"),
        IN("IN"),
        IO("IO"),
        IP("IP"),
        IQ("IQ"),
        IR("IR"),
        IS("IS"),
        IT("IT"),
        IU("IU"),
        IV("IV"),
        IW("IW"),
        IX("IX"),
        IY("IY"),
        IZ("IZ"),
        JA("JA"),
        JB("JB"),
        JC("JC"),
        JD("JD"),
        JE("JE"),
        JF("JF"),
        JG("JG"),
        JH("JH"),
        LA("LA"),
        LB("LB"),
        LC("LC"),
        LD("LD"),
        LE("LE"),
        LF("LF"),
        LG("LG"),
        LH("LH"),
        LI("LI"),
        LJ("LJ"),
        LK("LK"),
        LL("LL"),
        LM("LM"),
        LN("LN"),
        LO("LO"),
        LP("LP"),
        LQ("LQ"),
        LR("LR"),
        LS("LS"),
        LT("LT"),
        LU("LU"),
        LV("LV"),
        MA("MA"),
        MAD("MAD"),
        MDR("MDR"),
        MF("MF"),
        MG("MG"),
        MI("MI"),
        MOP("MOP"),
        MP("MP"),
        MR("MR"),
        MS("MS"),
        MT("MT"),
        N_2("N_2"),
        NI("NI"),
        OA("OA"),
        OB("OB"),
        OC("OC"),
        OD("OD"),
        OE("OE"),
        OF("OF"),
        OG("OG"),
        OH("OH"),
        OI("OI"),
        OJ("OJ"),
        OK("OK"),
        OL("OL"),
        OM("OM"),
        ON("ON"),
        OO("OO"),
        OP("OP"),
        OQ("OQ"),
        OR("OR"),
        OS("OS"),
        OT("OT"),
        OU("OU"),
        OV("OV"),
        OW("OW"),
        OX("OX"),
        OY("OY"),
        OZ("OZ"),
        P_1("P_1"),
        P_2("P_2"),
        P_3("P_3"),
        P_4("P_4"),
        PA("PA"),
        PB("PB"),
        PC("PC"),
        PD("PD"),
        PE("PE"),
        PF("PF"),
        PG("PG"),
        PH("PH"),
        PI("PI"),
        PJ("PJ"),
        PK("PK"),
        PM("PM"),
        PN("PN"),
        PO("PO"),
        POA("POA"),
        PQ("PQ"),
        PR("PR"),
        PS("PS"),
        PT("PT"),
        PW("PW"),
        PX("PX"),
        PY("PY"),
        PZ("PZ"),
        RA("RA"),
        RB("RB"),
        RCA("RCA"),
        RCR("RCR"),
        RE("RE"),
        RF("RF"),
        RH("RH"),
        RI("RI"),
        RL("RL"),
        RM("RM"),
        RP("RP"),
        RS("RS"),
        RV("RV"),
        RW("RW"),
        SB("SB"),
        SE("SE"),
        SF("SF"),
        SG("SG"),
        SI("SI"),
        SN("SN"),
        SO("SO"),
        SPC("SPC"),
        SR("SR"),
        SS("SS"),
        ST("ST"),
        SU("SU"),
        SX("SX"),
        SY("SY"),
        SZ("SZ"),
        TA("TA"),
        TB("TB"),
        TC("TC"),
        TCP("TCP"),
        TCR("TCR"),
        TD("TD"),
        TE("TE"),
        TF("TF"),
        TG("TG"),
        TH("TH"),
        TI("TI"),
        TJ("TJ"),
        TK("TK"),
        TL("TL"),
        TM("TM"),
        TN("TN"),
        TO("TO"),
        TP("TP"),
        TQ("TQ"),
        TR("TR"),
        TS("TS"),
        TT("TT"),
        TU("TU"),
        TV("TV"),
        TW("TW"),
        TX("TX"),
        TY("TY"),
        TZ("TZ"),
        UA("UA"),
        UB("UB"),
        UC("UC"),
        UD("UD"),
        UE("UE"),
        UF("UF"),
        UG("UG"),
        UH("UH"),
        UHP("UHP"),
        UI("UI"),
        UJ("UJ"),
        UK("UK"),
        UL("UL"),
        UM("UM"),
        UN("UN"),
        UO("UO"),
        UP("UP"),
        UQ("UQ"),
        UR("UR"),
        US("US"),
        UT("UT"),
        UU("UU"),
        UV("UV"),
        UW("UW"),
        UX("UX"),
        UY("UY"),
        UZ("UZ"),
        VA("VA"),
        VB("VB"),
        VC("VC"),
        VE("VE"),
        VF("VF"),
        VG("VG"),
        VH("VH"),
        VI("VI"),
        VJ("VJ"),
        VK("VK"),
        VL("VL"),
        VM("VM"),
        VN("VN"),
        VO("VO"),
        VP("VP"),
        VQ("VQ"),
        VR("VR"),
        VS("VS"),
        VT("VT"),
        VU("VU"),
        VV("VV"),
        VW("VW"),
        VX("VX"),
        VY("VY"),
        VZ("VZ"),
        WA("WA"),
        WB("WB"),
        WC("WC"),
        WD("WD"),
        WE("WE"),
        WF("WF"),
        WG("WG"),
        WH("WH"),
        WI("WI"),
        WJ("WJ"),
        WK("WK"),
        WL("WL"),
        WM("WM"),
        WN("WN"),
        WO("WO"),
        WP("WP"),
        WPA("WPA"),
        WQ("WQ"),
        WR("WR"),
        WS("WS"),
        WT("WT"),
        WU("WU"),
        WV("WV"),
        WW("WW"),
        WX("WX"),
        WY("WY"),
        WZ("WZ"),
        ZZZ("ZZZ");
        private final String value;
        private final static Map<String, RoleCode.Value> CONSTANTS = new HashMap<String, RoleCode.Value>();

        static {
            for (RoleCode.Value c: values()) {
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

        public static RoleCode.Value fromValue(String value) {
            RoleCode.Value constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
