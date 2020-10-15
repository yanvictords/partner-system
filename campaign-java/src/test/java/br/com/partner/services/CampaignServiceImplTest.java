package br.com.partner.services;

import br.com.partner.exceptions.NotFoundException;
import br.com.partner.exceptions.UnprocessableEntityException;
import br.com.partner.models.Campaign;
import br.com.partner.repositories.CampaignRepository;
import br.com.partner.services.facades.CampaignValidityFacade;
import br.com.partner.services.impl.CampaignServiceImpl;
import br.com.partner.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigInteger;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.math.BigInteger.*;
import static java.time.LocalDate.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtilsConfig.class})
public class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CampaignValidityFacade campaignValidityFacade;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        campaignService = new CampaignServiceImpl(campaignRepository, campaignValidityFacade);
        mockStatic(BeanUtilsConfig.class);
        when(BeanUtilsConfig.getBean(ObjectMapper.class)).thenReturn(ObjectMapperUtils.create());
    }

    @Test
    public void shouldCreateCampaignWithNoAnotherExpiryDateCampaignsAssociatedSuccessfully() {
        final var expiryDate = now().plusDays(30);
        final var request = CreateCampaignRequestUtils.create(expiryDate);

        final var createdCampaignId = ONE;
        final var createdCampaign = CampaignUtils.create(createdCampaignId);

        when(campaignValidityFacade.processAllCampaignExpiryDates(any(Campaign.class))).thenReturn(newArrayList());
        when(campaignRepository.create(any(Campaign.class))).thenReturn(createdCampaign);

        final var response = campaignService.createCampaign(request);

        assertNotNull(response);
        assertEquals(createdCampaignId, response.getCreatedCampaign().getId());
        assertEquals(0, response.getUpdatedCampaigns().size());

        verify(campaignRepository, times(1))
                .create(any(Campaign.class));

        verify(campaignRepository, times(0))
                .update(any(BigInteger.class), any(Campaign.class));
    }

    @Test
    public void shouldCreateCampaignAndUpdateTwoExistingCampaignsWithAssociatedExpiryDatePeriodSuccessfully() {
        final var expiryDate = now().plusDays(30);
        final var request = CreateCampaignRequestUtils.create(expiryDate);

        final var createdCampaignId = TEN;
        final var createdCampaign = CampaignUtils.create(createdCampaignId);

        final var firstExistingCampaignId = ONE;
        final var firstExistingCampaign = CampaignUtils.create(firstExistingCampaignId);

        final var secondExistingCampaignId = TWO;
        final var secondExistingCampaign = CampaignUtils.create(secondExistingCampaignId);

        final var firstUpdatedCampaign = CampaignUtils.create(firstExistingCampaignId);
        firstUpdatedCampaign.plusOneDayExpiryDate();

        final var secondUpdatedCampaign = CampaignUtils.create(secondExistingCampaignId);
        secondUpdatedCampaign.plusOneDayExpiryDate();

        when(campaignValidityFacade.processAllCampaignExpiryDates(any(Campaign.class)))
                .thenReturn(newArrayList(firstUpdatedCampaign, secondUpdatedCampaign));

        when(campaignRepository.findByID(firstExistingCampaignId)).thenReturn(firstExistingCampaign);
        when(campaignRepository.findByID(secondExistingCampaignId)).thenReturn(secondExistingCampaign);

        when(campaignRepository.update(firstExistingCampaignId, firstUpdatedCampaign)).thenReturn(firstUpdatedCampaign);
        when(campaignRepository.update(secondExistingCampaignId, secondUpdatedCampaign)).thenReturn(secondUpdatedCampaign);

        when(campaignRepository.create(any(Campaign.class))).thenReturn(createdCampaign);

        final var response = campaignService.createCampaign(request);

        assertNotNull(response);
        assertEquals(createdCampaignId, response.getCreatedCampaign().getId());
        assertEquals(2, response.getUpdatedCampaigns().size());
        assertEquals(firstExistingCampaignId, response.getUpdatedCampaigns().get(0).getId());
        assertEquals(secondExistingCampaignId, response.getUpdatedCampaigns().get(1).getId());

        verify(campaignRepository, times(1))
                .create(any(Campaign.class));

        verify(campaignRepository, times(1))
                .update(eq(secondExistingCampaignId), any(Campaign.class));

        verify(campaignRepository, times(1))
                .update(eq(secondExistingCampaignId), any(Campaign.class));
    }

    @Test
    public void shouldUpdateExistingCampaignByChangeNameAndPlusOneDayInExpiryDateSuccessfully() {
        final var expiryDate = now().plusDays(30);
        final var request = UpdateCampaignRequestUtils.create(expiryDate);

        final var campaignId = ONE;
        final var existingCampaign = CampaignUtils.create(campaignId);
        final var updatedCampaign = CampaignUtils.create(campaignId);

        final var newName = format("%s %s", existingCampaign.getName(), "Final name");
        updatedCampaign.setName(newName);
        updatedCampaign.plusOneDayExpiryDate();

        when(campaignValidityFacade.processAllCampaignExpiryDates(any(Campaign.class))).thenReturn(newArrayList());
        when(campaignRepository.findByID(eq(campaignId))).thenReturn(existingCampaign);
        when(campaignRepository.update(eq(campaignId), any(Campaign.class))).thenReturn(updatedCampaign);

        final var response = campaignService.updateCampaign(campaignId, request);

        assertNotNull(response);
        assertEquals(campaignId, response.getId());
        assertEquals(newName, response.getName());
        assertEquals(1, response.getExpiryDate().compareTo(existingCampaign.getExpiryDate()));

        verify(campaignRepository, times(1))
                .findByID(eq(campaignId));

        verify(campaignRepository, times(1))
                .update(eq(campaignId), any(Campaign.class));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowsNotFoundIfTryToUpdateNoExistentCampaign() {
        final var campaignId = ONE;

        doThrow(EmptyResultDataAccessException.class)
                .when(campaignRepository)
                .findByID(eq(campaignId));

        final var expiryDate = now().plusDays(30);
        final var request = UpdateCampaignRequestUtils.create(expiryDate);
        campaignService.updateCampaign(campaignId, request);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldThrowsUnprocessableEntityIfTryToUpdateAnExpiredCampaign() {
        final var campaignId = ONE;
        final var existingCampaign = CampaignUtils.create(campaignId);
        existingCampaign.setExpiryDate(now().minusDays(1));

        when(campaignRepository.findByID(eq(campaignId))).thenReturn(existingCampaign);

        final var expiryDate = now().plusDays(30);
        final var request = UpdateCampaignRequestUtils.create(expiryDate);
        campaignService.updateCampaign(campaignId, request);
    }

    @Test
    public void shouldFindCampaignByIdSuccessfully() {
        final var campaignId = ONE;
        final var existingCampaign = CampaignUtils.create(campaignId);

        when(campaignRepository.findByID(eq(campaignId))).thenReturn(existingCampaign);

        final var response = campaignService.findCampaign(campaignId);

        assertNotNull(response);
        assertEquals(campaignId, response.getId());

        verify(campaignRepository, times(1))
                .findByID(eq(campaignId));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowsNotFoundIfTryToFindNoExistentCampaign() {
        final var campaignId = ONE;

        doThrow(EmptyResultDataAccessException.class)
                .when(campaignRepository)
                .findByID(eq(campaignId));

        campaignService.findCampaign(campaignId);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldThrowsUnprocessableEntityIfTryToFindAnExpiredCampaign() {
        final var campaignId = ONE;
        final var existingCampaign = CampaignUtils.create(campaignId);
        existingCampaign.setExpiryDate(now().minusDays(1));

        when(campaignRepository.findByID(eq(campaignId))).thenReturn(existingCampaign);

        campaignService.findCampaign(campaignId);
    }

    @Test
    public void shouldDeleteCampaignByIdSuccessfully() {
        final var campaignId = ONE;
        campaignService.deleteCampaign(campaignId);

        verify(campaignRepository, times(1))
                .deleteById(eq(campaignId));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowsNotFoundIfTryToDeleteNoExistentCampaign() {
        final var campaignId = ONE;

        doThrow(EmptyResultDataAccessException.class)
                .when(campaignRepository)
                .deleteById(eq(campaignId));

        campaignService.deleteCampaign(campaignId);
    }

    @Test
    public void shouldFindAllUnexpiredTeamCampaignsSuccessfully() {
        final var teamId = ONE;

        final var firstUnexpiredCampaignId = ONE;
        final var firstUnexpiredCampaign = CampaignUtils.create(firstUnexpiredCampaignId);

        final var secondUnexpiredCampaignId = ONE;
        final var secondUnexpiredCampaign = CampaignUtils.create(firstUnexpiredCampaignId);

        when(campaignRepository.findByTeamIdAndNotExpired(teamId))
                .thenReturn(newArrayList(firstUnexpiredCampaign, secondUnexpiredCampaign));

        final var response = campaignService.findUnexpiredCampaignsByTeamId(teamId);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(firstUnexpiredCampaignId, response.get(0).getId());
        assertEquals(secondUnexpiredCampaignId, response.get(1).getId());

        verify(campaignRepository, times(1))
                .findByTeamIdAndNotExpired(eq(teamId));
    }

    @Test
    public void shouldReturnEmptyListIfTeamHasNoCampaignsSuccessfully() {
        final var teamId = ONE;

        when(campaignRepository.findByTeamIdAndNotExpired(teamId))
                .thenReturn(newArrayList());

        final var response = campaignService.findUnexpiredCampaignsByTeamId(teamId);

        assertNotNull(response);
        assertEquals(0, response.size());

        verify(campaignRepository, times(1))
                .findByTeamIdAndNotExpired(eq(teamId));
    }

    @Test
    public void shouldFindAllUnexpiredCampaignsByIdListSuccessfully() {
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;
        final var campaignIds = newArrayList(firstCampaignId, secondCampaignId);

        final var firstUnexpiredCampaign = CampaignUtils.create(firstCampaignId);
        final var secondUnexpiredCampaign = CampaignUtils.create(secondCampaignId);

        when(campaignRepository.findByIdsAndNotExpired(campaignIds))
                .thenReturn(newArrayList(firstUnexpiredCampaign, secondUnexpiredCampaign));

        final var response = campaignService.findUnexpiredCampaignsByIds(campaignIds);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(firstCampaignId, response.get(0).getId());
        assertEquals(secondCampaignId, response.get(1).getId());

        verify(campaignRepository, times(1))
                .findByIdsAndNotExpired(eq(campaignIds));
    }

    @Test
    public void shouldReturnEmptyListIfParameterIsAnEmptyListWhenCallsFindAllUnexpiredCampaigns() {
        final var response = campaignService.findUnexpiredCampaignsByIds(newArrayList());

        assertNotNull(response);
        assertEquals(0, response.size());

        verify(campaignRepository, times(0))
                .findByIdsAndNotExpired(anyList());
    }

}
