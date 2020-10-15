package br.com.partner.controllers;

import br.com.partner.controllers.impl.PartnerControllerImpl;
import br.com.partner.services.impl.PartnerServiceImpl;
import br.com.partner.utils.ObjectMapperUtils;
import br.com.partner.utils.PartnerRegisterCampaignDTOUtils;
import br.com.partner.utils.PartnerRegisterRequestUtils;
import br.com.partner.utils.PartnerRegisterResponseUtils;
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

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PartnerControllerImplTest {

    private MockMvc mockMvc;

    @Mock
    private PartnerServiceImpl partnerService;

    @InjectMocks
    private PartnerControllerImpl partnerController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        partnerController = new PartnerControllerImpl(partnerService);
        mockMvc = MockMvcBuilders.standaloneSetup(partnerController).build();
    }

    @Test
    public void shouldRegisterANewPartnerAndAssociateToOneTeamCampaignSuccessfully() throws Exception {
        final var email = "test@email.com";
        final var request = PartnerRegisterRequestUtils.create(email);

        final var campaignId = ONE;
        final var campaign = PartnerRegisterCampaignDTOUtils.create(campaignId);
        final var response = PartnerRegisterResponseUtils.create(email, newArrayList(campaign));

        when(partnerService.register(request)).thenReturn(response);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/partners/")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.campaigns[0].id", is(campaignId.intValue())))
                .andReturn();
    }

    @Test
    public void shouldReturnBadRequestStatusIfRequestIsNotValidWhenCallsPartnerRegister() throws Exception {
        final var request = PartnerRegisterRequestUtils.create(null);
        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/partners/")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeIfContentTypeIsNotJsonWhenCallsPartnerRegister() throws Exception {
        final var email = "test@email.com";
        final var request = PartnerRegisterRequestUtils.create(email);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/partners/")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_XML)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

}