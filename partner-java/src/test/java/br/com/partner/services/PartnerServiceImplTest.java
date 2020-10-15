package br.com.partner.services;

import br.com.partner.models.Partner;
import br.com.partner.repositories.PartnerRepository;
import br.com.partner.services.impl.PartnerServiceImpl;
import br.com.partner.services.impl.facades.AssociationFacade;
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

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtilsConfig.class})
public class PartnerServiceImplTest {

    @Mock
    private AssociationFacade associationFacade;

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        partnerService = new PartnerServiceImpl(associationFacade, partnerRepository);
        mockStatic(BeanUtilsConfig.class);
        when(BeanUtilsConfig.getBean(ObjectMapper.class)).thenReturn(ObjectMapperUtils.create());
    }

    @Test
    public void shouldRegisterPartnerAndReturnAllTeamCampaignsSuccessfully() {
        final var partnerId = ONE;
        final var email = "test@email.com";
        final var request = PartnerRegisterRequestUtils.create(email);

        final var newPartner = PartnerUtils.create(null, email);
        final var createdPartner = PartnerUtils.create(partnerId, email);

        final var firstAssociatedCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondAssociatedCampaign = ConsultTeamCampaignDTOUtils.create(TWO);

        doThrow(EmptyResultDataAccessException.class)
                .when(partnerRepository)
                .findByEmail(eq(email));

        when(partnerRepository.create(newPartner)).thenReturn(createdPartner);

        when(associationFacade.updateCampaignAssociations(createdPartner))
                .thenReturn(newArrayList(firstAssociatedCampaign, secondAssociatedCampaign));

        final var response = partnerService.register(request);

        assertNotNull(response);
        assertEquals(partnerId, response.getId());
        assertEquals(email, response.getEmail());
        assertEquals(2, response.getCampaigns().size());
        assertEquals(firstAssociatedCampaign.getId(), response.getCampaigns().get(0).getId());
        assertEquals(secondAssociatedCampaign.getId(), response.getCampaigns().get(1).getId());

        verify(partnerRepository, times(1))
                .findByEmail(eq(email));

        verify(partnerRepository, times(1))
                .create(newPartner);

        verify(associationFacade, times(1))
                .updateCampaignAssociations(createdPartner);
    }

    @Test
    public void shouldNotRegisterExistingPartnerAndReturnAllTeamCampaigns() {
        final var partnerId = ONE;
        final var email = "test@email.com";
        final var request = PartnerRegisterRequestUtils.create(email);

        final var existingPartner = PartnerUtils.create(partnerId, email);

        final var firstAssociatedCampaign = ConsultTeamCampaignDTOUtils.create(ONE);
        final var secondAssociatedCampaign = ConsultTeamCampaignDTOUtils.create(TWO);

        when(partnerRepository.findByEmail(email)).thenReturn(existingPartner);

        when(associationFacade.updateCampaignAssociations(existingPartner))
                .thenReturn(newArrayList(firstAssociatedCampaign, secondAssociatedCampaign));

        final var response = partnerService.register(request);

        assertNotNull(response);
        assertEquals(partnerId, response.getId());
        assertEquals(email, response.getEmail());
        assertEquals(2, response.getCampaigns().size());
        assertEquals(firstAssociatedCampaign.getId(), response.getCampaigns().get(0).getId());
        assertEquals(secondAssociatedCampaign.getId(), response.getCampaigns().get(1).getId());

        verify(partnerRepository, times(1))
                .findByEmail(eq(email));

        verify(partnerRepository, times(0))
                .create(any(Partner.class));

        verify(associationFacade, times(1))
                .updateCampaignAssociations(existingPartner);
    }

}
