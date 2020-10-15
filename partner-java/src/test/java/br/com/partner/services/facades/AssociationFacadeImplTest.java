package br.com.partner.services.facades;

import br.com.partner.exceptions.BadGatewayException;
import br.com.partner.exceptions.FeignClientException;
import br.com.partner.exceptions.UnprocessableEntityException;
import br.com.partner.parameters.clients.create_association.CreateAssociationRequest;
import br.com.partner.services.clients.CampaignClient;
import br.com.partner.services.impl.facades.impl.AssociationFacadeImpl;
import br.com.partner.utils.*;
import feign.FeignException;
import feign.RetryableException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class AssociationFacadeImplTest {

    @Mock
    private CampaignClient campaignClient;

    @InjectMocks
    private AssociationFacadeImpl associationFacade;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        associationFacade = new AssociationFacadeImpl(campaignClient);
    }

    @Test
    public void shouldFindAllTeamCampaignsAndAssociateAndReturnThemIfHaveNoAssociations() {
        final var email = "test@email.com";
        final var partner = PartnerUtils.create(ONE, email);

        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(TWO);
        final var consultAllTeamCampaignResponse =
                ConsultTeamCampaignResponseUtils.create(newArrayList(firstCampaign, secondCampaign));

        final var consultAssociationResponse =
                ConsultAssociationResponseUtils.create(newArrayList());

        when(campaignClient.findAllCampaignByTeamId(partner.getTeamId())).thenReturn(consultAllTeamCampaignResponse);
        when(campaignClient.findAllCampaignByPartnerId(partner.getId())).thenReturn(consultAssociationResponse);

        final var campaigns = associationFacade.updateCampaignAssociations(partner);

        assertNotNull(campaigns);
        assertEquals(2, campaigns.size());
        assertEquals(firstCampaign.getId(), campaigns.get(0).getId());
        assertEquals(secondCampaign.getId(), campaigns.get(1).getId());

        verify(campaignClient, times(1))
                .findAllCampaignByTeamId(eq(partner.getTeamId()));

        verify(campaignClient, times(1))
                .findAllCampaignByPartnerId(eq(partner.getId()));

        verify(campaignClient, times(1))
                .associateAllCampaign(eq(partner.getId()), any(CreateAssociationRequest.class));
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldFindAllTeamCampaignsAndAssociateOnlyNewCampaignsAndThrowsErrorForPartnerWithExistingAssociations() {
        final var email = "test@email.com";
        final var partner = PartnerUtils.create(ONE, email);

        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(TWO);
        final var consultAllTeamCampaignResponse =
                ConsultTeamCampaignResponseUtils.create(newArrayList(firstCampaign, secondCampaign));

        final var associatedCampaign = ConsultAssociationDTOUtils.create(ONE);
        final var consultAssociationResponse =
                ConsultAssociationResponseUtils.create(newArrayList(associatedCampaign));

        when(campaignClient.findAllCampaignByTeamId(partner.getTeamId())).thenReturn(consultAllTeamCampaignResponse);
        when(campaignClient.findAllCampaignByPartnerId(partner.getId())).thenReturn(consultAssociationResponse);

        final var campaigns = associationFacade.updateCampaignAssociations(partner);

        assertNotNull(campaigns);
        assertEquals(1, campaigns.size());
        assertEquals(firstCampaign.getId(), campaigns.get(0).getId());

        verify(campaignClient, times(1))
                .findAllCampaignByTeamId(eq(partner.getTeamId()));

        verify(campaignClient, times(1))
                .findAllCampaignByPartnerId(eq(partner.getId()));

        verify(campaignClient, times(1))
                .associateAllCampaign(eq(partner.getId()), any(CreateAssociationRequest.class));
    }

    @Test(expected = BadGatewayException.class)
    public void shouldThrowsBadGateWayIfCampaignServiceIsOffWhenTringGetAllTeamCampaigns() {
        final var email = "test@email.com";
        final var partner = PartnerUtils.create(ONE, email);

        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(TWO);
        final var consultAllTeamCampaignResponse =
                ConsultTeamCampaignResponseUtils.create(newArrayList(firstCampaign, secondCampaign));

        final var associatedCampaign = ConsultAssociationDTOUtils.create(ONE);
        final var consultAssociationResponse =
                ConsultAssociationResponseUtils.create(newArrayList(associatedCampaign));

        when(campaignClient.findAllCampaignByTeamId(partner.getTeamId())).thenReturn(consultAllTeamCampaignResponse);
        when(campaignClient.findAllCampaignByPartnerId(partner.getId())).thenReturn(consultAssociationResponse);

        doThrow(RetryableException.class)
                .when(campaignClient)
                .findAllCampaignByTeamId(eq(partner.getTeamId()));

        associationFacade.updateCampaignAssociations(partner);
    }


    @Test(expected = BadGatewayException.class)
    public void shouldThrowsBadGateWayIfCampaignServiceIsOffWhenTringGetMyCampaigns() {
        final var email = "test@email.com";
        final var partner = PartnerUtils.create(ONE, email);

        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(TWO);
        final var consultAllTeamCampaignResponse =
                ConsultTeamCampaignResponseUtils.create(newArrayList(firstCampaign, secondCampaign));

        final var associatedCampaign = ConsultAssociationDTOUtils.create(ONE);
        final var consultAssociationResponse =
                ConsultAssociationResponseUtils.create(newArrayList(associatedCampaign));

        when(campaignClient.findAllCampaignByTeamId(partner.getTeamId())).thenReturn(consultAllTeamCampaignResponse);
        when(campaignClient.findAllCampaignByPartnerId(partner.getId())).thenReturn(consultAssociationResponse);

        doThrow(RetryableException.class)
                .when(campaignClient)
                .findAllCampaignByPartnerId(eq(partner.getId()));

        associationFacade.updateCampaignAssociations(partner);
    }

    @Test(expected = BadGatewayException.class)
    public void shouldThrowsBadGateWayIfCampaignServiceIsOffWhenTryingAssociateCampaigns() {
        final var email = "test@email.com";
        final var partner = PartnerUtils.create(ONE, email);

        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(TWO);
        final var consultAllTeamCampaignResponse =
                ConsultTeamCampaignResponseUtils.create(newArrayList(firstCampaign, secondCampaign));

        final var associatedCampaign = ConsultAssociationDTOUtils.create(ONE);
        final var consultAssociationResponse =
                ConsultAssociationResponseUtils.create(newArrayList(associatedCampaign));

        when(campaignClient.findAllCampaignByTeamId(partner.getTeamId())).thenReturn(consultAllTeamCampaignResponse);
        when(campaignClient.findAllCampaignByPartnerId(partner.getId())).thenReturn(consultAssociationResponse);

        doThrow(RetryableException.class)
                .when(campaignClient)
                .associateAllCampaign(eq(partner.getId()), any(CreateAssociationRequest.class));

        associationFacade.updateCampaignAssociations(partner);
    }

    @Test(expected = FeignClientException.class)
    public void shouldThrowsFeignClientIfCampaignServiceReturnsProblemAfterTryGetTeamCampaigns() {
        final var email = "test@email.com";
        final var partner = PartnerUtils.create(ONE, email);

        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(TWO);
        final var consultAllTeamCampaignResponse =
                ConsultTeamCampaignResponseUtils.create(newArrayList(firstCampaign, secondCampaign));

        final var associatedCampaign = ConsultAssociationDTOUtils.create(ONE);
        final var consultAssociationResponse =
                ConsultAssociationResponseUtils.create(newArrayList(associatedCampaign));

        when(campaignClient.findAllCampaignByTeamId(partner.getTeamId())).thenReturn(consultAllTeamCampaignResponse);
        when(campaignClient.findAllCampaignByPartnerId(partner.getId())).thenReturn(consultAssociationResponse);

        doThrow(FeignException.class)
                .when(campaignClient)
                .findAllCampaignByTeamId(eq(partner.getTeamId()));

        associationFacade.updateCampaignAssociations(partner);
    }


    @Test(expected = FeignClientException.class)
    public void shouldThrowsFeignClientIfCampaignServiceReturnsProblemAfterTryGetPartnerCampaigns() {
        final var email = "test@email.com";
        final var partner = PartnerUtils.create(ONE, email);

        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(TWO);
        final var consultAllTeamCampaignResponse =
                ConsultTeamCampaignResponseUtils.create(newArrayList(firstCampaign, secondCampaign));

        final var associatedCampaign = ConsultAssociationDTOUtils.create(ONE);
        final var consultAssociationResponse =
                ConsultAssociationResponseUtils.create(newArrayList(associatedCampaign));

        when(campaignClient.findAllCampaignByTeamId(partner.getTeamId())).thenReturn(consultAllTeamCampaignResponse);
        when(campaignClient.findAllCampaignByPartnerId(partner.getId())).thenReturn(consultAssociationResponse);

        doThrow(FeignException.class)
                .when(campaignClient)
                .findAllCampaignByPartnerId(eq(partner.getId()));

        associationFacade.updateCampaignAssociations(partner);
    }

    @Test(expected = FeignClientException.class)
    public void shouldThrowsFeignClientIfCampaignServiceReturnsProblemAfterTryAssociateCampaigns() {
        final var email = "test@email.com";
        final var partner = PartnerUtils.create(ONE, email);

        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(TWO);
        final var consultAllTeamCampaignResponse =
                ConsultTeamCampaignResponseUtils.create(newArrayList(firstCampaign, secondCampaign));

        final var associatedCampaign = ConsultAssociationDTOUtils.create(ONE);
        final var consultAssociationResponse =
                ConsultAssociationResponseUtils.create(newArrayList(associatedCampaign));

        when(campaignClient.findAllCampaignByTeamId(partner.getTeamId())).thenReturn(consultAllTeamCampaignResponse);
        when(campaignClient.findAllCampaignByPartnerId(partner.getId())).thenReturn(consultAssociationResponse);

        doThrow(FeignException.class)
                .when(campaignClient)
                .associateAllCampaign(eq(partner.getId()), any(CreateAssociationRequest.class));

        associationFacade.updateCampaignAssociations(partner);
    }

}
