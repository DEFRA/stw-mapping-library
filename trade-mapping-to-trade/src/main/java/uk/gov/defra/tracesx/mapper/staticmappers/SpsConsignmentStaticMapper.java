package uk.gov.defra.tracesx.mapper.staticmappers;

import static uk.gov.defra.tracesx.mapper.staticmappers.SpsCertificateStaticMapper.REPLACEMENT_GUID;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uk.gov.defra.tracesx.trade.dto.IDType;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsConsignmentItem;
import uk.gov.defra.tracesx.trade.dto.IncludedSpsTradeLineItem;
import uk.gov.defra.tracesx.trade.dto.SpsAuthenticationType;
import uk.gov.defra.tracesx.trade.dto.SpsConsignment;
import uk.gov.defra.tracesx.trade.dto.SpsCountrySubDivisionType;
import uk.gov.defra.tracesx.trade.dto.SpsCountryType;
import uk.gov.defra.tracesx.trade.dto.SpsEventType;
import uk.gov.defra.tracesx.trade.dto.SpsLocationType;
import uk.gov.defra.tracesx.trade.dto.SpsPartyType;
import uk.gov.defra.tracesx.trade.dto.SpsTransportEquipmentType;
import uk.gov.defra.tracesx.trade.dto.TextType;

@Component
public class SpsConsignmentStaticMapper implements StaticDataMapper<SpsConsignment> {

  @Override
  public void apply(SpsConsignment spsConsignmentType) {
    setSpsConsignment(spsConsignmentType);
  }

  private void setSpsConsignment(SpsConsignment spsConsignment) {
    setSpsImportCountryType(spsConsignment);
    setSpsCountryTypeName(spsConsignment.getExportSpsCountry());
    setTransitSpsCountry(spsConsignment.getTransitSpsCountry());
    setIncludedSpsConsignmentItem(spsConsignment);
    setExaminationSpsEvent(spsConsignment);
    setUtilizedSpsTransportEquipment(spsConsignment.getUtilizedSpsTransportEquipment());
  }

  private void setSpsImportCountryType(SpsConsignment spsConsignment) {
    if (spsConsignment.getImportSpsCountry() == null) {
      SpsCountryType spsCountryType = new SpsCountryType()
          .withId(new IDType().withValue(REPLACEMENT_GUID))
          .withName(Collections.singletonList(new TextType().withValue(REPLACEMENT_GUID)));
      spsConsignment.setImportSpsCountry(spsCountryType);
    } else {
      spsConsignment.getImportSpsCountry()
          .withName(Collections.singletonList(new TextType().withValue(REPLACEMENT_GUID)));
    }
  }

  private void setTransitSpsCountry(List<SpsCountryType> transitSpsCountry) {
    if (transitSpsCountry != null) {
      for (SpsCountryType spsCountry : transitSpsCountry) {
        setSpsCountryId(spsCountry);
        setSpsCountryTypeName(spsCountry);
      }
    }
    setSpsCountrySubDivisionName(transitSpsCountry);
  }

  private void setSpsCountryId(SpsCountryType spsCountryType) {
    if (spsCountryType != null && spsCountryType.getId() == null) {
      spsCountryType.setId(new IDType().withValue(REPLACEMENT_GUID));
    }
  }

  private void setSpsCountryTypeName(SpsCountryType spsCountryType) {
    if (spsCountryType != null && CollectionUtils.isEmpty(spsCountryType.getName())) {
      spsCountryType.setName(Collections.singletonList(new TextType().withValue(REPLACEMENT_GUID)));
    }
  }

  private void setSpsCountrySubDivisionName(List<SpsCountryType> spsCountryType) {
    if (spsCountryType != null) {
      for (SpsCountryType spsCountry : spsCountryType) {
        List<SpsCountrySubDivisionType> spsCountrySubDivisionType =
            spsCountry.getSubordinateSpsCountrySubDivision();
        if (spsCountrySubDivisionType != null) {
          for (SpsCountrySubDivisionType spsCountrySubDivision : spsCountrySubDivisionType) {
            List<TextType> name = spsCountrySubDivision.getName();
            if (name == null || name.isEmpty()) {
              spsCountrySubDivision
                  .setName(Collections.singletonList(new TextType().withValue(REPLACEMENT_GUID)));
            }
            for (SpsPartyType spsPartyType : spsCountrySubDivision
                .getActivityAuthorizedSpsParty()) {
              if (spsPartyType.getName() == null) {
                spsPartyType.setName(new TextType().withValue(REPLACEMENT_GUID));
              }
            }
          }
        }
      }
    }
  }

  private void setIncludedSpsConsignmentItem(SpsConsignment spsConsignment) {
    for (IncludedSpsConsignmentItem spsConsignmentItem :
        spsConsignment.getIncludedSpsConsignmentItem()) {
      for (IncludedSpsTradeLineItem spsTradeLineItemType :
          spsConsignmentItem.getIncludedSpsTradeLineItem()) {
        spsTradeLineItemType
            .setDescription(Collections.singletonList(new TextType().withValue(REPLACEMENT_GUID)));

        for (SpsAuthenticationType spsAuthenticationType : spsTradeLineItemType
            .getAssertedSpsAuthentication()) {
          SpsPartyType spsPartyType = spsAuthenticationType.getProviderSpsParty();
          if (spsPartyType.getName() == null) {
            spsPartyType.setName(new TextType().withValue(REPLACEMENT_GUID));
          }
        }

        setOriginSpsCountry(spsTradeLineItemType);
      }
    }
  }

  private void setOriginSpsCountry(IncludedSpsTradeLineItem spsTradeLineItemType) {
    for (SpsCountryType spsCountryType : spsTradeLineItemType.getOriginSpsCountry()) {
      if (spsCountryType.getName().isEmpty()) {
        spsCountryType.setName(Collections.singletonList(
            new TextType().withValue(REPLACEMENT_GUID)));
      }
    }
  }

  private void setExaminationSpsEvent(SpsConsignment spsConsignment) {
    if (spsConsignment.getExaminationSpsEvent() == null) {
      spsConsignment.setExaminationSpsEvent(
          new SpsEventType().withOccurrenceSpsLocation(
              new SpsLocationType().withName(Collections.singletonList(
                  new TextType().withValue(REPLACEMENT_GUID)))));
    }
  }

  private void setUtilizedSpsTransportEquipment(
      List<SpsTransportEquipmentType> utilizedSpsTransportEquipmentList) {
    for (SpsTransportEquipmentType utilizedSpsTransportEquipment :
        utilizedSpsTransportEquipmentList) {
      if (utilizedSpsTransportEquipment.getId() == null) {
        utilizedSpsTransportEquipment
            .setId(new IDType().withSchemeID(REPLACEMENT_GUID).withValue(REPLACEMENT_GUID));
      }
    }
  }
}
