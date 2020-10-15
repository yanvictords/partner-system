package br.com.partner.controllers;

import br.com.partner.controllers.impl.CampaignControllerImpl;
import br.com.partner.services.CampaignService;
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

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.time.LocalDate.now;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignControllerImplTest {

    private MockMvc mockMvc;

    @Mock
    private CampaignService campaignService;

    @InjectMocks
    private CampaignControllerImpl campaignControllerImpl;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        campaignControllerImpl = new CampaignControllerImpl(campaignService);
        mockMvc = MockMvcBuilders.standaloneSetup(campaignControllerImpl).build();
    }

    @Test
    public void shouldCreateCampaignAndReturnCreatedCampaignSuccessfully() throws Exception {
        final var expiryDate = now().plusDays(30);
        final var request = CreateCampaignRequestUtils.create(expiryDate);

        final var createdCampaignId = ONE;
        final var createdCampaign = CreateCampaignDTOUtils.create(createdCampaignId);
        final var updatedCampaign = CreateCampaignDTOUtils.create(TWO);
        final var response =
                CreateCampaignResponseUtils.create(createdCampaign, newArrayList(updatedCampaign));

        when(campaignService.createCampaign(request)).thenReturn(response);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/campaigns/")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.createdCampaign.id", is(createdCampaignId.intValue())))
                .andExpect(jsonPath("$.updatedCampaigns[0].id", is(TWO.intValue())));
    }

    @Test
    public void shouldReturnBadRequestStatusIfRequestIsNotValidWhenCallsCreateCampaign() throws Exception {
        final var request = CreateCampaignRequestUtils.create(null);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/campaigns/")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeIfContentTypeIsNotJsonWhenCallsCreateCampaign() throws Exception {
        final var expiryDate = now().plusDays(30);
        final var request = CreateCampaignRequestUtils.create(expiryDate);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.post("/campaigns/")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_XML)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldUpdateCampaignAndReturnUpdatedCampaignSuccessfully() throws Exception {
        final var expiryDate = now().plusDays(30);
        final var request = UpdateCampaignRequestUtils.create(expiryDate);

        final var updateCampaignId = ONE;
        final var response =
                UpdateCampaignResponseUtils.create(updateCampaignId, expiryDate);

        when(campaignService.updateCampaign(updateCampaignId, request)).thenReturn(response);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.put("/campaigns/" + updateCampaignId)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(updateCampaignId.intValue())))
                .andExpect(jsonPath("$.expiryDate", is(expiryDate.toString())));
    }

    @Test
    public void shouldReturnBadRequestStatusIfRequestIsNotValidWhenCallsUpdateCampaign() throws Exception {
        final var request = UpdateCampaignRequestUtils.create(null);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.put("/campaigns/" + null)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeIfContentTypeIsNotJsonWhenCallsUpdateCampaign() throws Exception {
        final var expiryDate = now().plusDays(30);
        final var request = CreateCampaignRequestUtils.create(expiryDate);

        final var mapper = ObjectMapperUtils.create();
        mockMvc.perform(MockMvcRequestBuilders.put("/campaigns/" + ONE)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_XML)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldConsultCampaignByIdentificationSuccessfully() throws Exception {
        final var campaignId = ONE;
        final var response =
                ConsultCampaignResponseUtils.create(campaignId);

        when(campaignService.findCampaign(campaignId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/campaigns/" + campaignId)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(campaignId.intValue())));
    }

    @Test
    public void shouldReturnBadRequestStatusIfRequestIsNotValidWhenCallsGetCampaign() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/campaigns/" + null)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteCampaignByIdentificationSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/campaigns/" + ONE)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestStatusIfRequestIsNotValidWhenCallsDeleteCampaign() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/campaigns/" + null)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
