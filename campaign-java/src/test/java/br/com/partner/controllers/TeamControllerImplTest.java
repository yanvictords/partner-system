package br.com.partner.controllers;

import br.com.partner.controllers.impl.TeamControllerImpl;
import br.com.partner.services.TeamService;
import br.com.partner.utils.ConsultTeamCampaignDTOUtils;
import br.com.partner.utils.ConsultTeamCampaignResponseUtils;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TeamControllerImplTest {

    private MockMvc mockMvc;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamControllerImpl teamController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        teamController = new TeamControllerImpl(teamService);
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    public void shouldFindAllTeamCampaignSuccessfully() throws Exception {
        final var firstCampaignId = "1";
        final var secondCampaignId = "2";
        final var firstCampaign = ConsultTeamCampaignDTOUtils.create(firstCampaignId);
        final var secondCampaign = ConsultTeamCampaignDTOUtils.create(secondCampaignId);

        final var campaigns = newArrayList(firstCampaign, secondCampaign);
        final var response = ConsultTeamCampaignResponseUtils.create(campaigns);

        final var partnerId = "1";
        when(teamService.findNotExpiredCampaignByTeamId(new BigInteger(partnerId))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/teams/" + partnerId)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.campaigns[0].id", is(valueOf(firstCampaignId))))
                .andExpect(jsonPath("$.campaigns[1].id", is(valueOf(secondCampaignId))));
    }

    @Test
    public void shouldReturnBadRequestStatusIfRequestIsNotValidWhenCallsFindAllCampaignTeam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/teams/" + null)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
