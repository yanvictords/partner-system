package br.com.partner.repositories;

import br.com.partner.repositories.impl.CampaignRepositoryImpl;
import br.com.partner.utils.CampaignUtils;
import br.com.partner.utils.database.mappers.CampaignRowMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Map;

import static br.com.partner.models.Campaign.SCHEME_NAME;
import static br.com.partner.models.Campaign.TABLE_NAME;
import static br.com.partner.utils.database.queries.CampaignQuery.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CampaignRepositoryImplTest {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME + " WHERE ID = :ID";

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private CampaignRowMapper rowMapper;

    @InjectMocks
    private CampaignRepositoryImpl campaignRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        campaignRepository = new CampaignRepositoryImpl(jdbcTemplate, rowMapper);
    }

    @Test
    public void shouldCreateCampaignSuccessfully() {
        final var campaignId = ONE;
        final var newCampaign = CampaignUtils.create();
        final var createdCampaign = CampaignUtils.create(campaignId);

        when(jdbcTemplate.queryForObject(eq(FIND_BY_ID_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(createdCampaign);

        campaignRepository.create(newCampaign);

        verify(jdbcTemplate, times(1))
                .update(eq(SAVE_QUERY), any(MapSqlParameterSource.class),
                        any(GeneratedKeyHolder.class), any(String[].class));
    }

    @Test
    public void shouldUpdateCampaignSuccessfully() {
        final var campaignId = ONE;
        final var newCampaignData = CampaignUtils.create(campaignId);
        final var updatedCampaign = CampaignUtils.create(campaignId);

        when(jdbcTemplate.queryForObject(eq(FIND_BY_ID_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(updatedCampaign);

        final var campaign = campaignRepository.update(campaignId, newCampaignData);

        assertNotNull(campaign);
        assertEquals(campaignId, campaign.getId());

        verify(jdbcTemplate, times(1))
                .update(eq(UPDATE_QUERY), any(SqlParameterSource.class));

        verify(jdbcTemplate, times(1))
                .queryForObject(eq(FIND_BY_ID_QUERY), any(Map.class), eq(rowMapper));
    }

    @Test
    public void shouldFindByIdSuccessfully() {
        final var campaignId = ONE;
        final var campaign = CampaignUtils.create(campaignId);

        when(jdbcTemplate.queryForObject(eq(FIND_BY_ID_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(campaign);

        final var foundCampaign = campaignRepository.findByID(campaignId);

        assertNotNull(foundCampaign);
        assertEquals(campaignId, foundCampaign.getId());

        verify(jdbcTemplate, times(1))
                .queryForObject(eq(FIND_BY_ID_QUERY), any(Map.class), eq(rowMapper));
    }

    @Test
    public void shouldDeleteIdSuccessfully() {
        final var campaignId = ONE;
        final var campaign = CampaignUtils.create(campaignId);

        when(jdbcTemplate.queryForObject(eq(FIND_BY_ID_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(campaign);

        campaignRepository.deleteById(campaignId);

        verify(jdbcTemplate, times(1))
                .update(eq(DELETE_BY_ID_QUERY), any(SqlParameterSource.class));

        verify(jdbcTemplate, times(1))
                .queryForObject(eq(FIND_BY_ID_QUERY), any(Map.class), eq(rowMapper));
    }

    @Test
    public void shouldFindByTeamIdAndNotExpiredSuccessfully() {
        final var campaignId = ONE;
        final var campaign = CampaignUtils.create(campaignId);

        when(jdbcTemplate.query(eq(FIND_BY_TEAM_ID_AND_NOT_EXPIRED_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(newArrayList(campaign));

        final var foundCampaign = campaignRepository.findByTeamIdAndNotExpired(campaignId)
                .get(0);

        assertNotNull(foundCampaign);
        assertEquals(campaignId, foundCampaign.getId());

        verify(jdbcTemplate, times(1))
                .query(eq(FIND_BY_TEAM_ID_AND_NOT_EXPIRED_QUERY), any(Map.class), eq(rowMapper));
    }

    @Test
    public void shouldFindByIdsAndNotExpiredSuccessfully() {
        final var campaignId = ONE;
        final var campaign = CampaignUtils.create(campaignId);

        when(jdbcTemplate.query(eq(FIND_BY_IDS_AND_NOT_EXPIRED_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(newArrayList(campaign));

        final var foundCampaign = campaignRepository.findByIdsAndNotExpired(newArrayList(campaignId))
                .get(0);

        assertNotNull(foundCampaign);
        assertEquals(campaignId, foundCampaign.getId());

        verify(jdbcTemplate, times(1))
                .query(eq(FIND_BY_IDS_AND_NOT_EXPIRED_QUERY), any(Map.class), eq(rowMapper));
    }

    @Test
    public void shouldFindAllNotExpiredByLessOrEqualsExpiryDateAscSuccessfully() {
        final var campaignId = ONE;
        final var campaign = CampaignUtils.create(campaignId);

        when(jdbcTemplate.query(eq(FIND_ALL_NOT_EXPIRED_BY_LESS_OR_EQUALS_EXPIRY_DATE_ASC),
                any(Map.class),
                eq(rowMapper)))
                .thenReturn(newArrayList(campaign));

        final var foundCampaign =
                campaignRepository.findAllNotExpiredByLessOrEqualsExpiryDateAsc(campaign.getExpiryDate())
                        .get(0);

        assertNotNull(foundCampaign);
        assertEquals(campaignId, foundCampaign.getId());

        verify(jdbcTemplate, times(1))
                .query(eq(FIND_ALL_NOT_EXPIRED_BY_LESS_OR_EQUALS_EXPIRY_DATE_ASC), any(Map.class), eq(rowMapper));
    }

}
