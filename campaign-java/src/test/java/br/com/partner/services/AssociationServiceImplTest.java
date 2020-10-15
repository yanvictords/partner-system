package br.com.partner.services;

import br.com.partner.models.PartnerCampaign;
import br.com.partner.repositories.PartnerCampaignRepository;
import br.com.partner.services.impl.AssociationServiceImpl;
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

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtilsConfig.class})
public class AssociationServiceImplTest {

    @Mock
    private CampaignService campaignService;

    @Mock
    private PartnerCampaignRepository partnerCampaignRepository;

    @InjectMocks
    private AssociationServiceImpl associationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        associationService = new AssociationServiceImpl(campaignService, partnerCampaignRepository);
        mockStatic(BeanUtilsConfig.class);
        when(BeanUtilsConfig.getBean(ObjectMapper.class)).thenReturn(ObjectMapperUtils.create());
    }

    @Test
    public void shouldConsultAssociatedCampaignsSuccessfully() {
        final var partnerId = ONE;
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;

        final var firstAssociation = PartnerCampaignUtils.create(partnerId, firstCampaignId);
        final var secondAssociation = PartnerCampaignUtils.create(partnerId, secondCampaignId);

        final var firstCampaign = CampaignUtils.create(firstCampaignId);
        final var secondCampaign = CampaignUtils.create(secondCampaignId);

        when(partnerCampaignRepository.findAllByPartnerId(partnerId))
                .thenReturn(newArrayList(firstAssociation, secondAssociation));

        when(campaignService.findUnexpiredCampaignsByIds(anyList()))
                .thenReturn(newArrayList(firstCampaign, secondCampaign));

        final var responseCampaignIds = associationService.findAllPartnerCampaignAssociation(partnerId)
                .getCampaignIds();

        assertNotNull(responseCampaignIds);
        assertEquals(firstCampaignId, responseCampaignIds.get(0));
        assertEquals(secondCampaignId, responseCampaignIds.get(1));

        verify(partnerCampaignRepository, times(1))
                .findAllByPartnerId(partnerId);

        verify(campaignService, times(1))
                .findUnexpiredCampaignsByIds(anyList());
    }

    @Test
    public void shouldReturnEmptyIfNotExitsAssociatedCampaignsSuccessfully() {
        final var partnerId = ONE;
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;

        when(partnerCampaignRepository.findAllByPartnerId(partnerId))
                .thenReturn(newArrayList());

        when(campaignService.findUnexpiredCampaignsByIds(anyList()))
                .thenReturn(newArrayList());

        final var responseCampaignIds = associationService.findAllPartnerCampaignAssociation(partnerId)
                .getCampaignIds();

        assertNotNull(responseCampaignIds);
        assertEquals(0, responseCampaignIds.size());

        verify(partnerCampaignRepository, times(1))
                .findAllByPartnerId(partnerId);

        verify(campaignService, times(1))
                .findUnexpiredCampaignsByIds(anyList());
    }

    @Test
    public void shouldAssociateAllCampaignsToPartnerSuccessfully() {
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;

        final var request = CreateAssociationRequestUtils.create(newArrayList(firstCampaignId, secondCampaignId));

        final var newFirstCampaign = CampaignUtils.create(firstCampaignId);
        final var newSecondCampaign = CampaignUtils.create(secondCampaignId);

        when(campaignService.findUnexpiredCampaignsByIds(anyList()))
                .thenReturn(newArrayList())
                .thenReturn(newArrayList(newFirstCampaign, newSecondCampaign));

        final var responseCampaignIds = associationService.associate(ONE, request)
                .getAssociatedCampaignIds();

        assertNotNull(responseCampaignIds);
        assertEquals(2, responseCampaignIds.size());
        assertEquals(firstCampaignId, responseCampaignIds.get(0));
        assertEquals(secondCampaignId, responseCampaignIds.get(1));

        verify(partnerCampaignRepository, times(2))
                .create(any(PartnerCampaign.class));

        verify(campaignService, times(2))
                .findUnexpiredCampaignsByIds(anyList());
    }

    @Test
    public void shouldAssociateOneCampaignToPartnerIfAnotherIsAlreadyAssociatedSuccessfully() {
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;

        final var request = CreateAssociationRequestUtils.create(newArrayList(firstCampaignId, secondCampaignId));

        final var associatedCampaign = CampaignUtils.create(firstCampaignId);
        final var newCampaign = CampaignUtils.create(secondCampaignId);

        when(campaignService.findUnexpiredCampaignsByIds(anyList()))
                .thenReturn(newArrayList(associatedCampaign))
                .thenReturn(newArrayList(newCampaign));

        final var responseCampaignIds = associationService.associate(ONE, request)
                .getAssociatedCampaignIds();

        assertNotNull(responseCampaignIds);
        assertEquals(1, responseCampaignIds.size());
        assertEquals(secondCampaignId, responseCampaignIds.get(0));

        verify(partnerCampaignRepository, times(1))
                .create(any(PartnerCampaign.class));

        verify(campaignService, times(2))
                .findUnexpiredCampaignsByIds(anyList());
    }

    @Test
    public void shouldNotAssociateToPartnerIfAllCampaignsIsAlreadyAssociated() {
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;

        final var request = CreateAssociationRequestUtils.create(newArrayList(firstCampaignId, secondCampaignId));

        final var firstAssociatedCampaign = CampaignUtils.create(firstCampaignId);
        final var secondAssociatedCampaign = CampaignUtils.create(secondCampaignId);

        when(campaignService.findUnexpiredCampaignsByIds(anyList()))
                .thenReturn(newArrayList(firstAssociatedCampaign, secondAssociatedCampaign))
                .thenReturn(newArrayList());

        final var responseCampaignIds = associationService.associate(ONE, request)
                .getAssociatedCampaignIds();

        assertNotNull(responseCampaignIds);
        assertEquals(0, responseCampaignIds.size());

        verify(partnerCampaignRepository, times(0))
                .create(any(PartnerCampaign.class));

        verify(campaignService, times(2))
                .findUnexpiredCampaignsByIds(anyList());
    }

    @Test
    public void shouldNotAssociateToPartnerIfFirstAlreadyAssociatedAndSecondIsExpired() {
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;

        final var request = CreateAssociationRequestUtils.create(newArrayList(firstCampaignId, secondCampaignId));
        final var associatedCampaign = CampaignUtils.create(firstCampaignId);

        when(campaignService.findUnexpiredCampaignsByIds(anyList()))
                .thenReturn(newArrayList(associatedCampaign))
                .thenReturn(newArrayList());

        final var responseCampaignIds = associationService.associate(ONE, request)
                .getAssociatedCampaignIds();

        assertNotNull(responseCampaignIds);
        assertEquals(0, responseCampaignIds.size());

        verify(partnerCampaignRepository, times(0))
                .create(any(PartnerCampaign.class));

        verify(campaignService, times(2))
                .findUnexpiredCampaignsByIds(anyList());
    }

    @Test
    public void shouldNotAssociateToPartnerIfAllCampaignsIsExpired() {
        final var request = CreateAssociationRequestUtils.create(newArrayList(ONE, TWO));

        when(campaignService.findUnexpiredCampaignsByIds(anyList()))
                .thenReturn(newArrayList())
                .thenReturn(newArrayList());

        final var responseCampaignIds = associationService.associate(ONE, request)
                .getAssociatedCampaignIds();

        assertNotNull(responseCampaignIds);
        assertEquals(0, responseCampaignIds.size());

        verify(partnerCampaignRepository, times(0))
                .create(any(PartnerCampaign.class));

        verify(campaignService, times(2))
                .findUnexpiredCampaignsByIds(anyList());
    }

}
