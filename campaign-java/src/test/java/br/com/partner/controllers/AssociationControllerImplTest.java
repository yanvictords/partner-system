package br.com.partner.controllers;

import br.com.partner.controllers.impl.AssociationControllerImpl;
import br.com.partner.services.AssociationService;
import br.com.partner.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.valueOf;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AssociationControllerImplTest {

    private MockMvc mockMvc;

    @Mock
    private AssociationService associationService;

    @InjectMocks
    private AssociationControllerImpl associationController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        associationController = new AssociationControllerImpl(associationService);
        mockMvc = MockMvcBuilders.standaloneSetup(associationController).build();
    }

    @Test
    public void shouldAssociatePartnerToHisTeamCampaignsSuccessfully() throws Exception {
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;

        final var campaigns = newArrayList(firstCampaignId, secondCampaignId);
        final var request = CreateAssociationRequestUtils.create(campaigns);

        final var response = CreateAssociationResponseUtils.create(campaigns);

        final var partnerId = "1";
        when(associationService.associate(new BigInteger(partnerId), request)).thenReturn(response);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/associations/partner/" + partnerId)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.associatedCampaignIds[0]", is(firstCampaignId.intValue())))
                .andExpect(jsonPath("$.associatedCampaignIds[1]", is(secondCampaignId.intValue())));
    }

    @Test
    public void shouldReturnBadRequestStatusIfRequestIsNotValidWhenCallsAssociatePartner() throws Exception {
        final var campaignId = ONE;
        final var request = CreateAssociationRequestUtils.create(newArrayList(campaignId));

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/associations/partner/" + null)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeIfContentTypeIsNotJsonWhenCallsAssociatePartner() throws Exception {
        final var campaignId = ONE;
        final var request = CreateAssociationRequestUtils.create(newArrayList(campaignId));
        final var partnerId = "1";

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/associations/partner/" + partnerId)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_XML)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldFindAllAssociatedCampaignsForAPartnerSuccessfully() throws Exception {
        final var firstCampaignId = "1";
        final var secondCampaignId = "2";
        final var firstCampaign = ConsultAssociationDTOUtils.create(firstCampaignId);
        final var secondCampaign = ConsultAssociationDTOUtils.create(secondCampaignId);

        final var associatedCampaigns = newArrayList(firstCampaign, secondCampaign);
        final var response = ConsultAssociationResponseUtils.create(associatedCampaigns);

        final var partnerId = "1";
        when(associationService.findAllPartnerCampaignAssociation(new BigInteger(partnerId))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/associations/partner/" + partnerId)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.campaigns[0].id", is(valueOf(firstCampaignId))))
                .andExpect(jsonPath("$.campaigns[1].id", is(valueOf(secondCampaignId))));
    }

    @Test
    public void shouldReturnBadRequestStatusIfRequestIsNotValidWhenCallsGetPartnerAssociations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/associations/partner/" + null)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
