package br.com.partner.repositories;

import br.com.partner.repositories.impl.PartnerCampaignRepositoryImpl;
import br.com.partner.utils.PartnerCampaignUtils;
import br.com.partner.utils.database.mappers.PartnerCampaignRowMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Map;

import static br.com.partner.models.PartnerCampaign.SCHEME_NAME;
import static br.com.partner.models.PartnerCampaign.TABLE_NAME;
import static br.com.partner.utils.database.queries.PartnerCampaignQuery.FIND_ALL_BY_PARTNER_ID_QUERY;
import static br.com.partner.utils.database.queries.PartnerCampaignQuery.SAVE_QUERY;
import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PartnerCampaignRepositoryImplTest {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME + " WHERE ID = :ID";

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private PartnerCampaignRowMapper rowMapper;

    @InjectMocks
    private PartnerCampaignRepositoryImpl partnerCampaignRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        partnerCampaignRepository = new PartnerCampaignRepositoryImpl(jdbcTemplate, rowMapper);
    }

    @Test
    public void shouldCreatePartnerCampaignAssociationSuccessfully() {
        final var association = PartnerCampaignUtils.create(ONE, TWO);

        when(jdbcTemplate.queryForObject(eq(FIND_BY_ID_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(association);

        partnerCampaignRepository.create(association);

        verify(jdbcTemplate, times(1))
                .update(eq(SAVE_QUERY), any(MapSqlParameterSource.class),
                        any(GeneratedKeyHolder.class), any(String[].class));
    }


    @Test
    public void shouldFindAllByPartnerIdSuccessfully() {
        final var campaignId = ONE;
        final var partnerId = TWO;
        final var association = PartnerCampaignUtils.create(campaignId, partnerId);

        when(jdbcTemplate.query(eq(FIND_ALL_BY_PARTNER_ID_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(newArrayList(association));

        final var createdAssociation = partnerCampaignRepository.findAllByPartnerId(partnerId)
                .get(0);

        assertNotNull(createdAssociation);
        assertEquals(partnerId, association.getPartnerId());
        assertEquals(campaignId, association.getCampaignId());

        verify(jdbcTemplate, times(1))
                .query(eq(FIND_ALL_BY_PARTNER_ID_QUERY), any(Map.class), eq(rowMapper));
    }

}
