package uk.gov.defra.stw.mapping.totrade.common.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IpaffsRegionsUtilTest {
    @Test
    void isUkRegion_ReturnsTrueForUkRegion() {
        boolean result = IpaffsRegionsUtil.isUkRegion("WA");
        assertThat(result).isTrue();
    }

    @Test
    void isUkRegion_ReturnsFalseForNonUkRegion() {
        boolean result = IpaffsRegionsUtil.isUkRegion("AF");
        assertThat(result).isFalse();
    }

    @Test
    void getIsoRegionFromIpaffsRegion_ReturnsCorrectIsoRegions() {
        String regionOne = IpaffsRegionsUtil.getIsoRegionFromIpaffsRegion("EN");
        assertThat(regionOne).isEqualTo("GB-ENG");

        String regionTwo = IpaffsRegionsUtil.getIsoRegionFromIpaffsRegion("SCO");
        assertThat(regionTwo).isEqualTo("GB-SCT");

        String regionThree = IpaffsRegionsUtil.getIsoRegionFromIpaffsRegion("WA");
        assertThat(regionThree).isEqualTo("GB-WLS");

        String regionFour = IpaffsRegionsUtil.getIsoRegionFromIpaffsRegion("NIR");
        assertThat(regionFour).isEqualTo("GB-NIR");
    }

    @Test
    void getIsoRegionFromIpaffsRegion_ReturnsNullForNonUkRegion() {
        String result = IpaffsRegionsUtil.getIsoRegionFromIpaffsRegion("AF");
        assertThat(result).isNull();
    }
}
