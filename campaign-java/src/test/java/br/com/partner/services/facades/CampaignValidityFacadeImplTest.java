package br.com.partner.services.facades;

import br.com.partner.repositories.CampaignRepository;
import br.com.partner.services.facades.impl.CampaignValidityFacadeImpl;
import br.com.partner.utils.CampaignUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.*;
import static java.time.LocalDate.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class CampaignValidityFacadeImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks

    private CampaignValidityFacadeImpl campaignValidityFacade;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        campaignValidityFacade = new CampaignValidityFacadeImpl(campaignRepository);
    }

    @Test
    public void shouldProcessAndReturnUpdatedCampaignsWhenTheirExpiryDatesAreAssociatedToTheNewCampaignExpiryDate() {
        final var localDateNow = now();
        final var newCampaign = CampaignUtils.create(TEN, now().plusDays(3));

        final var firstExistingCampaignId = ONE;
        final var secondExistingCampaignId = TWO;

        final var firstExistingCampaign = CampaignUtils.create(firstExistingCampaignId, localDateNow.plusDays(3));
        final var secondExistingCampaign = CampaignUtils.create(secondExistingCampaignId, localDateNow.plusDays(2));

        final var campaignsAscByExpiryDate = newArrayList(secondExistingCampaign, firstExistingCampaign);

        when(campaignRepository.findAllNotExpiredByLessOrEqualsExpiryDateAsc(newCampaign.getExpiryDate()))
                .thenReturn(campaignsAscByExpiryDate);

        final var updatedCampaigns = campaignValidityFacade.processAllCampaignExpiryDates(newCampaign);

        final var expectedFirstCampaignExpiryDate = localDateNow.plusDays(5);
        final var expectedSecondCampaignExpiryDate = localDateNow.plusDays(4);

        assertNotNull(updatedCampaigns);
        assertEquals(2, updatedCampaigns.size());

        assertEquals(secondExistingCampaignId, updatedCampaigns.get(0).getId());
        assertEquals(firstExistingCampaignId, updatedCampaigns.get(1).getId());

        assertEquals(expectedSecondCampaignExpiryDate, updatedCampaigns.get(0).getExpiryDate());
        assertEquals(expectedFirstCampaignExpiryDate, updatedCampaigns.get(1).getExpiryDate());

        verify(campaignRepository, times(1))
                .findAllNotExpiredByLessOrEqualsExpiryDateAsc(newCampaign.getExpiryDate());
    }

    @Test
    public void shouldReturnEmptyListIfNotExistsCampaignsAssociatedToTheNewCampaignByExpiryDate() {
        final var newCampaign = CampaignUtils.create(TEN, now().plusDays(3));

        when(campaignRepository.findAllNotExpiredByLessOrEqualsExpiryDateAsc(newCampaign.getExpiryDate()))
                .thenReturn(newArrayList());

        final var updatedCampaigns = campaignValidityFacade.processAllCampaignExpiryDates(newCampaign);

        assertNotNull(updatedCampaigns);
        assertEquals(0, updatedCampaigns.size());

        verify(campaignRepository, times(1))
                .findAllNotExpiredByLessOrEqualsExpiryDateAsc(newCampaign.getExpiryDate());
    }

}
