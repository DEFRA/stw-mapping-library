
package uk.gov.defra.stw.mapping.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpsConsignment implements Serializable
{

    private DateTimeType availabilityDueDateTime;
    private SpsPartyType carrierSpsParty;
    private SpsLocationType consigneeReceiptSpsLocation;
    /**
     * 
     * (Required)
     * 
     */
    private SpsPartyType consigneeSpsParty;
    /**
     * 
     * (Required)
     * 
     */
    private SpsPartyType consignorSpsParty;
    private SpsPartyType customsTransitAgentSpsParty;
    private SpsPartyType deliverySpsParty;
    private SpsPartyType despatchSpsParty;
    /**
     * 
     * (Required)
     * 
     */
    private SpsEventType examinationSpsEvent;
    private DateTimeType exportExitDateTime;
    /**
     * 
     * (Required)
     * 
     */
    private SpsCountryType exportSpsCountry;
    private IDType id;
    /**
     * 
     * (Required)
     * 
     */
    private SpsCountryType importSpsCountry;
    /**
     * 
     * (Required)
     * 
     */
    private List<IncludedSpsConsignmentItem> includedSpsConsignmentItem = new ArrayList<IncludedSpsConsignmentItem>();
    private SpsLocationType loadingBaseportSpsLocation;
    private List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovement = new ArrayList<MainCarriageSpsTransportMovement>();
    private List<SpsCountryType> reExportSpsCountry = new ArrayList<SpsCountryType>();
    private IndicatorType shipStoresIndicator;
    private List<SpsEventType> storageSpsEvent = new ArrayList<SpsEventType>();
    private List<SpsCountryType> transitSpsCountry = new ArrayList<SpsCountryType>();
    private List<SpsLocationType> transitSpsLocation = new ArrayList<SpsLocationType>();
    private SpsLocationType unloadingBaseportSpsLocation;
    private List<SpsTransportEquipmentType> utilizedSpsTransportEquipment = new ArrayList<SpsTransportEquipmentType>();
    private final static long serialVersionUID = 2462676633974366462L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpsConsignment() {
    }

    /**
     * 
     * @param consignorSpsParty
     * @param consigneeSpsParty
     * @param includedSpsConsignmentItem
     * @param exportSpsCountry
     * @param examinationSpsEvent
     * @param importSpsCountry
     */
    public SpsConsignment(SpsPartyType consigneeSpsParty, SpsPartyType consignorSpsParty, SpsEventType examinationSpsEvent, SpsCountryType exportSpsCountry, SpsCountryType importSpsCountry, List<IncludedSpsConsignmentItem> includedSpsConsignmentItem) {
        super();
        this.consigneeSpsParty = consigneeSpsParty;
        this.consignorSpsParty = consignorSpsParty;
        this.examinationSpsEvent = examinationSpsEvent;
        this.exportSpsCountry = exportSpsCountry;
        this.importSpsCountry = importSpsCountry;
        this.includedSpsConsignmentItem = includedSpsConsignmentItem;
    }

    public void setAvailabilityDueDateTime(
        DateTimeType availabilityDueDateTime) {
        this.availabilityDueDateTime = availabilityDueDateTime;
    }

    public void setCarrierSpsParty(SpsPartyType carrierSpsParty) {
        this.carrierSpsParty = carrierSpsParty;
    }

    public void setConsigneeReceiptSpsLocation(
        SpsLocationType consigneeReceiptSpsLocation) {
        this.consigneeReceiptSpsLocation = consigneeReceiptSpsLocation;
    }

    public void setConsigneeSpsParty(SpsPartyType consigneeSpsParty) {
        this.consigneeSpsParty = consigneeSpsParty;
    }

    public void setConsignorSpsParty(SpsPartyType consignorSpsParty) {
        this.consignorSpsParty = consignorSpsParty;
    }

    public void setCustomsTransitAgentSpsParty(
        SpsPartyType customsTransitAgentSpsParty) {
        this.customsTransitAgentSpsParty = customsTransitAgentSpsParty;
    }

    public void setDeliverySpsParty(SpsPartyType deliverySpsParty) {
        this.deliverySpsParty = deliverySpsParty;
    }

    public void setDespatchSpsParty(SpsPartyType despatchSpsParty) {
        this.despatchSpsParty = despatchSpsParty;
    }

    public void setExaminationSpsEvent(SpsEventType examinationSpsEvent) {
        this.examinationSpsEvent = examinationSpsEvent;
    }

    public void setExportExitDateTime(DateTimeType exportExitDateTime) {
        this.exportExitDateTime = exportExitDateTime;
    }

    public void setExportSpsCountry(SpsCountryType exportSpsCountry) {
        this.exportSpsCountry = exportSpsCountry;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public void setImportSpsCountry(SpsCountryType importSpsCountry) {
        this.importSpsCountry = importSpsCountry;
    }

    public void setIncludedSpsConsignmentItem(
        List<IncludedSpsConsignmentItem> includedSpsConsignmentItem) {
        this.includedSpsConsignmentItem = includedSpsConsignmentItem;
    }

    public void setLoadingBaseportSpsLocation(
        SpsLocationType loadingBaseportSpsLocation) {
        this.loadingBaseportSpsLocation = loadingBaseportSpsLocation;
    }

    public void setMainCarriageSpsTransportMovement(
        List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovement) {
        this.mainCarriageSpsTransportMovement = mainCarriageSpsTransportMovement;
    }

    public void setReExportSpsCountry(
        List<SpsCountryType> reExportSpsCountry) {
        this.reExportSpsCountry = reExportSpsCountry;
    }

    public void setShipStoresIndicator(IndicatorType shipStoresIndicator) {
        this.shipStoresIndicator = shipStoresIndicator;
    }

    public void setStorageSpsEvent(
        List<SpsEventType> storageSpsEvent) {
        this.storageSpsEvent = storageSpsEvent;
    }

    public void setTransitSpsCountry(
        List<SpsCountryType> transitSpsCountry) {
        this.transitSpsCountry = transitSpsCountry;
    }

    public void setTransitSpsLocation(
        List<SpsLocationType> transitSpsLocation) {
        this.transitSpsLocation = transitSpsLocation;
    }

    public void setUnloadingBaseportSpsLocation(
        SpsLocationType unloadingBaseportSpsLocation) {
        this.unloadingBaseportSpsLocation = unloadingBaseportSpsLocation;
    }

    public void setUtilizedSpsTransportEquipment(
        List<SpsTransportEquipmentType> utilizedSpsTransportEquipment) {
        this.utilizedSpsTransportEquipment = utilizedSpsTransportEquipment;
    }

    public DateTimeType getAvailabilityDueDateTime() {
        return availabilityDueDateTime;
    }

    public SpsConsignment withAvailabilityDueDateTime(DateTimeType availabilityDueDateTime) {
        this.availabilityDueDateTime = availabilityDueDateTime;
        return this;
    }

    public SpsPartyType getCarrierSpsParty() {
        return carrierSpsParty;
    }

    public SpsConsignment withCarrierSpsParty(SpsPartyType carrierSpsParty) {
        this.carrierSpsParty = carrierSpsParty;
        return this;
    }

    public SpsLocationType getConsigneeReceiptSpsLocation() {
        return consigneeReceiptSpsLocation;
    }

    public SpsConsignment withConsigneeReceiptSpsLocation(
        SpsLocationType consigneeReceiptSpsLocation) {
        this.consigneeReceiptSpsLocation = consigneeReceiptSpsLocation;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SpsPartyType getConsigneeSpsParty() {
        return consigneeSpsParty;
    }

    public SpsConsignment withConsigneeSpsParty(SpsPartyType consigneeSpsParty) {
        this.consigneeSpsParty = consigneeSpsParty;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SpsPartyType getConsignorSpsParty() {
        return consignorSpsParty;
    }

    public SpsConsignment withConsignorSpsParty(SpsPartyType consignorSpsParty) {
        this.consignorSpsParty = consignorSpsParty;
        return this;
    }

    public SpsPartyType getCustomsTransitAgentSpsParty() {
        return customsTransitAgentSpsParty;
    }

    public SpsConsignment withCustomsTransitAgentSpsParty(SpsPartyType customsTransitAgentSpsParty) {
        this.customsTransitAgentSpsParty = customsTransitAgentSpsParty;
        return this;
    }

    public SpsPartyType getDeliverySpsParty() {
        return deliverySpsParty;
    }

    public SpsConsignment withDeliverySpsParty(SpsPartyType deliverySpsParty) {
        this.deliverySpsParty = deliverySpsParty;
        return this;
    }

    public SpsPartyType getDespatchSpsParty() {
        return despatchSpsParty;
    }

    public SpsConsignment withDespatchSpsParty(SpsPartyType despatchSpsParty) {
        this.despatchSpsParty = despatchSpsParty;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SpsEventType getExaminationSpsEvent() {
        return examinationSpsEvent;
    }

    public SpsConsignment withExaminationSpsEvent(SpsEventType examinationSpsEvent) {
        this.examinationSpsEvent = examinationSpsEvent;
        return this;
    }

    public DateTimeType getExportExitDateTime() {
        return exportExitDateTime;
    }

    public SpsConsignment withExportExitDateTime(DateTimeType exportExitDateTime) {
        this.exportExitDateTime = exportExitDateTime;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SpsCountryType getExportSpsCountry() {
        return exportSpsCountry;
    }

    public SpsConsignment withExportSpsCountry(SpsCountryType exportSpsCountry) {
        this.exportSpsCountry = exportSpsCountry;
        return this;
    }

    public IDType getId() {
        return id;
    }

    public SpsConsignment withId(IDType id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public SpsCountryType getImportSpsCountry() {
        return importSpsCountry;
    }

    public SpsConsignment withImportSpsCountry(SpsCountryType importSpsCountry) {
        this.importSpsCountry = importSpsCountry;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<IncludedSpsConsignmentItem> getIncludedSpsConsignmentItem() {
        return includedSpsConsignmentItem;
    }

    public SpsConsignment withIncludedSpsConsignmentItem(List<IncludedSpsConsignmentItem> includedSpsConsignmentItem) {
        this.includedSpsConsignmentItem = includedSpsConsignmentItem;
        return this;
    }

    public SpsLocationType getLoadingBaseportSpsLocation() {
        return loadingBaseportSpsLocation;
    }

    public SpsConsignment withLoadingBaseportSpsLocation(SpsLocationType loadingBaseportSpsLocation) {
        this.loadingBaseportSpsLocation = loadingBaseportSpsLocation;
        return this;
    }

    public List<MainCarriageSpsTransportMovement> getMainCarriageSpsTransportMovement() {
        return mainCarriageSpsTransportMovement;
    }

    public SpsConsignment withMainCarriageSpsTransportMovement(List<MainCarriageSpsTransportMovement> mainCarriageSpsTransportMovement) {
        this.mainCarriageSpsTransportMovement = mainCarriageSpsTransportMovement;
        return this;
    }

    public List<SpsCountryType> getReExportSpsCountry() {
        return reExportSpsCountry;
    }

    public SpsConsignment withReExportSpsCountry(List<SpsCountryType> reExportSpsCountry) {
        this.reExportSpsCountry = reExportSpsCountry;
        return this;
    }

    public IndicatorType getShipStoresIndicator() {
        return shipStoresIndicator;
    }

    public SpsConsignment withShipStoresIndicator(IndicatorType shipStoresIndicator) {
        this.shipStoresIndicator = shipStoresIndicator;
        return this;
    }

    public List<SpsEventType> getStorageSpsEvent() {
        return storageSpsEvent;
    }

    public SpsConsignment withStorageSpsEvent(List<SpsEventType> storageSpsEvent) {
        this.storageSpsEvent = storageSpsEvent;
        return this;
    }

    public List<SpsCountryType> getTransitSpsCountry() {
        return transitSpsCountry;
    }

    public SpsConsignment withTransitSpsCountry(List<SpsCountryType> transitSpsCountry) {
        this.transitSpsCountry = transitSpsCountry;
        return this;
    }

    public List<SpsLocationType> getTransitSpsLocation() {
        return transitSpsLocation;
    }

    public SpsConsignment withTransitSpsLocation(List<SpsLocationType> transitSpsLocation) {
        this.transitSpsLocation = transitSpsLocation;
        return this;
    }

    public SpsLocationType getUnloadingBaseportSpsLocation() {
        return unloadingBaseportSpsLocation;
    }

    public SpsConsignment withUnloadingBaseportSpsLocation(
        SpsLocationType unloadingBaseportSpsLocation) {
        this.unloadingBaseportSpsLocation = unloadingBaseportSpsLocation;
        return this;
    }

    public List<SpsTransportEquipmentType> getUtilizedSpsTransportEquipment() {
        return utilizedSpsTransportEquipment;
    }

    public SpsConsignment withUtilizedSpsTransportEquipment(List<SpsTransportEquipmentType> utilizedSpsTransportEquipment) {
        this.utilizedSpsTransportEquipment = utilizedSpsTransportEquipment;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpsConsignment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("availabilityDueDateTime");
        sb.append('=');
        sb.append(((this.availabilityDueDateTime == null)?"<null>":this.availabilityDueDateTime));
        sb.append(',');
        sb.append("carrierSpsParty");
        sb.append('=');
        sb.append(((this.carrierSpsParty == null)?"<null>":this.carrierSpsParty));
        sb.append(',');
        sb.append("consigneeReceiptSpsLocation");
        sb.append('=');
        sb.append(((this.consigneeReceiptSpsLocation == null)?"<null>":this.consigneeReceiptSpsLocation));
        sb.append(',');
        sb.append("consigneeSpsParty");
        sb.append('=');
        sb.append(((this.consigneeSpsParty == null)?"<null>":this.consigneeSpsParty));
        sb.append(',');
        sb.append("consignorSpsParty");
        sb.append('=');
        sb.append(((this.consignorSpsParty == null)?"<null>":this.consignorSpsParty));
        sb.append(',');
        sb.append("customsTransitAgentSpsParty");
        sb.append('=');
        sb.append(((this.customsTransitAgentSpsParty == null)?"<null>":this.customsTransitAgentSpsParty));
        sb.append(',');
        sb.append("deliverySpsParty");
        sb.append('=');
        sb.append(((this.deliverySpsParty == null)?"<null>":this.deliverySpsParty));
        sb.append(',');
        sb.append("despatchSpsParty");
        sb.append('=');
        sb.append(((this.despatchSpsParty == null)?"<null>":this.despatchSpsParty));
        sb.append(',');
        sb.append("examinationSpsEvent");
        sb.append('=');
        sb.append(((this.examinationSpsEvent == null)?"<null>":this.examinationSpsEvent));
        sb.append(',');
        sb.append("exportExitDateTime");
        sb.append('=');
        sb.append(((this.exportExitDateTime == null)?"<null>":this.exportExitDateTime));
        sb.append(',');
        sb.append("exportSpsCountry");
        sb.append('=');
        sb.append(((this.exportSpsCountry == null)?"<null>":this.exportSpsCountry));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("importSpsCountry");
        sb.append('=');
        sb.append(((this.importSpsCountry == null)?"<null>":this.importSpsCountry));
        sb.append(',');
        sb.append("includedSpsConsignmentItem");
        sb.append('=');
        sb.append(((this.includedSpsConsignmentItem == null)?"<null>":this.includedSpsConsignmentItem));
        sb.append(',');
        sb.append("loadingBaseportSpsLocation");
        sb.append('=');
        sb.append(((this.loadingBaseportSpsLocation == null)?"<null>":this.loadingBaseportSpsLocation));
        sb.append(',');
        sb.append("mainCarriageSpsTransportMovement");
        sb.append('=');
        sb.append(((this.mainCarriageSpsTransportMovement == null)?"<null>":this.mainCarriageSpsTransportMovement));
        sb.append(',');
        sb.append("reExportSpsCountry");
        sb.append('=');
        sb.append(((this.reExportSpsCountry == null)?"<null>":this.reExportSpsCountry));
        sb.append(',');
        sb.append("shipStoresIndicator");
        sb.append('=');
        sb.append(((this.shipStoresIndicator == null)?"<null>":this.shipStoresIndicator));
        sb.append(',');
        sb.append("storageSpsEvent");
        sb.append('=');
        sb.append(((this.storageSpsEvent == null)?"<null>":this.storageSpsEvent));
        sb.append(',');
        sb.append("transitSpsCountry");
        sb.append('=');
        sb.append(((this.transitSpsCountry == null)?"<null>":this.transitSpsCountry));
        sb.append(',');
        sb.append("transitSpsLocation");
        sb.append('=');
        sb.append(((this.transitSpsLocation == null)?"<null>":this.transitSpsLocation));
        sb.append(',');
        sb.append("unloadingBaseportSpsLocation");
        sb.append('=');
        sb.append(((this.unloadingBaseportSpsLocation == null)?"<null>":this.unloadingBaseportSpsLocation));
        sb.append(',');
        sb.append("utilizedSpsTransportEquipment");
        sb.append('=');
        sb.append(((this.utilizedSpsTransportEquipment == null)?"<null>":this.utilizedSpsTransportEquipment));
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
        result = ((result* 31)+((this.consignorSpsParty == null)? 0 :this.consignorSpsParty.hashCode()));
        result = ((result* 31)+((this.transitSpsLocation == null)? 0 :this.transitSpsLocation.hashCode()));
        result = ((result* 31)+((this.despatchSpsParty == null)? 0 :this.despatchSpsParty.hashCode()));
        result = ((result* 31)+((this.transitSpsCountry == null)? 0 :this.transitSpsCountry.hashCode()));
        result = ((result* 31)+((this.exportSpsCountry == null)? 0 :this.exportSpsCountry.hashCode()));
        result = ((result* 31)+((this.carrierSpsParty == null)? 0 :this.carrierSpsParty.hashCode()));
        result = ((result* 31)+((this.deliverySpsParty == null)? 0 :this.deliverySpsParty.hashCode()));
        result = ((result* 31)+((this.shipStoresIndicator == null)? 0 :this.shipStoresIndicator.hashCode()));
        result = ((result* 31)+((this.consigneeSpsParty == null)? 0 :this.consigneeSpsParty.hashCode()));
        result = ((result* 31)+((this.includedSpsConsignmentItem == null)? 0 :this.includedSpsConsignmentItem.hashCode()));
        result = ((result* 31)+((this.storageSpsEvent == null)? 0 :this.storageSpsEvent.hashCode()));
        result = ((result* 31)+((this.customsTransitAgentSpsParty == null)? 0 :this.customsTransitAgentSpsParty.hashCode()));
        result = ((result* 31)+((this.loadingBaseportSpsLocation == null)? 0 :this.loadingBaseportSpsLocation.hashCode()));
        result = ((result* 31)+((this.availabilityDueDateTime == null)? 0 :this.availabilityDueDateTime.hashCode()));
        result = ((result* 31)+((this.utilizedSpsTransportEquipment == null)? 0 :this.utilizedSpsTransportEquipment.hashCode()));
        result = ((result* 31)+((this.mainCarriageSpsTransportMovement == null)? 0 :this.mainCarriageSpsTransportMovement.hashCode()));
        result = ((result* 31)+((this.reExportSpsCountry == null)? 0 :this.reExportSpsCountry.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.examinationSpsEvent == null)? 0 :this.examinationSpsEvent.hashCode()));
        result = ((result* 31)+((this.exportExitDateTime == null)? 0 :this.exportExitDateTime.hashCode()));
        result = ((result* 31)+((this.consigneeReceiptSpsLocation == null)? 0 :this.consigneeReceiptSpsLocation.hashCode()));
        result = ((result* 31)+((this.importSpsCountry == null)? 0 :this.importSpsCountry.hashCode()));
        result = ((result* 31)+((this.unloadingBaseportSpsLocation == null)? 0 :this.unloadingBaseportSpsLocation.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpsConsignment) == false) {
            return false;
        }
        SpsConsignment rhs = ((SpsConsignment) other);
        return ((((((((((((((((((((((((this.consignorSpsParty == rhs.consignorSpsParty)||((this.consignorSpsParty!= null)&&this.consignorSpsParty.equals(rhs.consignorSpsParty)))&&((this.transitSpsLocation == rhs.transitSpsLocation)||((this.transitSpsLocation!= null)&&this.transitSpsLocation.equals(rhs.transitSpsLocation))))&&((this.despatchSpsParty == rhs.despatchSpsParty)||((this.despatchSpsParty!= null)&&this.despatchSpsParty.equals(rhs.despatchSpsParty))))&&((this.transitSpsCountry == rhs.transitSpsCountry)||((this.transitSpsCountry!= null)&&this.transitSpsCountry.equals(rhs.transitSpsCountry))))&&((this.exportSpsCountry == rhs.exportSpsCountry)||((this.exportSpsCountry!= null)&&this.exportSpsCountry.equals(rhs.exportSpsCountry))))&&((this.carrierSpsParty == rhs.carrierSpsParty)||((this.carrierSpsParty!= null)&&this.carrierSpsParty.equals(rhs.carrierSpsParty))))&&((this.deliverySpsParty == rhs.deliverySpsParty)||((this.deliverySpsParty!= null)&&this.deliverySpsParty.equals(rhs.deliverySpsParty))))&&((this.shipStoresIndicator == rhs.shipStoresIndicator)||((this.shipStoresIndicator!= null)&&this.shipStoresIndicator.equals(rhs.shipStoresIndicator))))&&((this.consigneeSpsParty == rhs.consigneeSpsParty)||((this.consigneeSpsParty!= null)&&this.consigneeSpsParty.equals(rhs.consigneeSpsParty))))&&((this.includedSpsConsignmentItem == rhs.includedSpsConsignmentItem)||((this.includedSpsConsignmentItem!= null)&&this.includedSpsConsignmentItem.equals(rhs.includedSpsConsignmentItem))))&&((this.storageSpsEvent == rhs.storageSpsEvent)||((this.storageSpsEvent!= null)&&this.storageSpsEvent.equals(rhs.storageSpsEvent))))&&((this.customsTransitAgentSpsParty == rhs.customsTransitAgentSpsParty)||((this.customsTransitAgentSpsParty!= null)&&this.customsTransitAgentSpsParty.equals(rhs.customsTransitAgentSpsParty))))&&((this.loadingBaseportSpsLocation == rhs.loadingBaseportSpsLocation)||((this.loadingBaseportSpsLocation!= null)&&this.loadingBaseportSpsLocation.equals(rhs.loadingBaseportSpsLocation))))&&((this.availabilityDueDateTime == rhs.availabilityDueDateTime)||((this.availabilityDueDateTime!= null)&&this.availabilityDueDateTime.equals(rhs.availabilityDueDateTime))))&&((this.utilizedSpsTransportEquipment == rhs.utilizedSpsTransportEquipment)||((this.utilizedSpsTransportEquipment!= null)&&this.utilizedSpsTransportEquipment.equals(rhs.utilizedSpsTransportEquipment))))&&((this.mainCarriageSpsTransportMovement == rhs.mainCarriageSpsTransportMovement)||((this.mainCarriageSpsTransportMovement!= null)&&this.mainCarriageSpsTransportMovement.equals(rhs.mainCarriageSpsTransportMovement))))&&((this.reExportSpsCountry == rhs.reExportSpsCountry)||((this.reExportSpsCountry!= null)&&this.reExportSpsCountry.equals(rhs.reExportSpsCountry))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.examinationSpsEvent == rhs.examinationSpsEvent)||((this.examinationSpsEvent!= null)&&this.examinationSpsEvent.equals(rhs.examinationSpsEvent))))&&((this.exportExitDateTime == rhs.exportExitDateTime)||((this.exportExitDateTime!= null)&&this.exportExitDateTime.equals(rhs.exportExitDateTime))))&&((this.consigneeReceiptSpsLocation == rhs.consigneeReceiptSpsLocation)||((this.consigneeReceiptSpsLocation!= null)&&this.consigneeReceiptSpsLocation.equals(rhs.consigneeReceiptSpsLocation))))&&((this.importSpsCountry == rhs.importSpsCountry)||((this.importSpsCountry!= null)&&this.importSpsCountry.equals(rhs.importSpsCountry))))&&((this.unloadingBaseportSpsLocation == rhs.unloadingBaseportSpsLocation)||((this.unloadingBaseportSpsLocation!= null)&&this.unloadingBaseportSpsLocation.equals(rhs.unloadingBaseportSpsLocation))));
    }

}
