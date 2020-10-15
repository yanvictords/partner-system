package br.com.partner.services;

import br.com.partner.services.impl.TeamServiceImpl;
import br.com.partner.utils.BeanUtilsConfig;
import br.com.partner.utils.CampaignUtils;
import br.com.partner.utils.ObjectMapperUtils;
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
public class TeamServiceImplTest {

    @Mock
    private CampaignService campaignService;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        teamService = new TeamServiceImpl(campaignService);
        mockStatic(BeanUtilsConfig.class);
        when(BeanUtilsConfig.getBean(ObjectMapper.class)).thenReturn(ObjectMapperUtils.create());
    }

    @Test
    public void shouldFindAllTeamCampaignsSuccessfully() {
        final var teamId = ONE;
        final var firstCampaignId = ONE;
        final var secondCampaignId = TWO;

        final var firstCampaign = CampaignUtils.create(firstCampaignId);
        final var secondCampaign = CampaignUtils.create(secondCampaignId);

        when(campaignService.findUnexpiredCampaignsByTeamId(teamId))
                .thenReturn(newArrayList(firstCampaign, secondCampaign));

        final var teamCampaigns = teamService.findNotExpiredCampaignByTeamId(teamId)
                .getCampaigns();

        assertNotNull(teamCampaigns);
        assertEquals(2, teamCampaigns.size());
        assertEquals(firstCampaignId, teamCampaigns.get(0).getId());
        assertEquals(secondCampaignId, teamCampaigns.get(1).getId());

        verify(campaignService, times(1))
                .findUnexpiredCampaignsByTeamId(teamId);
    }

    @Test
    public void shouldReturnEmptyListIfTeamHaveNoCampaignsSuccessfully() {
        final var teamId = ONE;

        when(campaignService.findUnexpiredCampaignsByTeamId(teamId))
                .thenReturn(newArrayList());

        final var teamCampaigns = teamService.findNotExpiredCampaignByTeamId(teamId)
                .getCampaigns();

        assertNotNull(teamCampaigns);
        assertEquals(0, teamCampaigns.size());

        verify(campaignService, times(1))
                .findUnexpiredCampaignsByTeamId(teamId);
    }

}
