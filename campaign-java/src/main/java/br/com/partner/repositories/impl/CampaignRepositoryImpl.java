package br.com.partner.repositories.impl;

import br.com.partner.models.Campaign;
import br.com.partner.repositories.CampaignRepository;
import br.com.partner.utils.database.mappers.CampaignRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static br.com.partner.models.Campaign.SCHEME_NAME;
import static br.com.partner.models.Campaign.TABLE_NAME;
import static br.com.partner.utils.database.queries.CampaignQuery.*;

@Service
public class CampaignRepositoryImpl extends NativeQueryRepository<Campaign> implements CampaignRepository {

    public CampaignRepositoryImpl(final NamedParameterJdbcOperations jdbcTemplate,
                                  final CampaignRowMapper rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public Campaign create(final Campaign campaign) {
        final var parameters = mapAllParameters(campaign);
        return save(SAVE_QUERY, parameters);
    }

    @Override
    public Campaign update(final BigInteger campaignId, final Campaign campaign) {
        campaign.setId(campaignId);

        final var parameters = mapAllParameters(campaign);
        return update(UPDATE_QUERY, parameters);
    }

    @Override
    public Campaign findByID(final BigInteger campaignId) {
        return find(campaignId);
    }

    @Override
    public void deleteById(final BigInteger campaignId) {
        findByID(campaignId);

        final var namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("ID", campaignId);
        delete(DELETE_BY_ID_QUERY, namedParameters);
    }

    public List<Campaign> findByTeamIdAndNotExpired(final BigInteger teamId) {
        final var parameters = Map.of("TEAM_ID", teamId);
        return query(FIND_BY_TEAM_ID_AND_NOT_EXPIRED_QUERY, parameters);
    }

    public List<Campaign> findByIdsAndNotExpired(final List<BigInteger> ids) {
        final var parameters = Map.of("IDS", ids);
        return query(FIND_BY_IDS_AND_NOT_EXPIRED_QUERY, parameters);
    }

    public List<Campaign> findAllNotExpiredByLessOrEqualsExpiryDateAsc(final LocalDate expiryDate) {
        final var parameters = Map.of("EXPIRY_DATE", expiryDate);
        return query(FIND_ALL_NOT_EXPIRED_BY_LESS_OR_EQUALS_EXPIRY_DATE_ASC, parameters);
    }

    @Override
    protected String getTable() {
        return TABLE_NAME;
    }

    @Override
    protected String getScheme() {
        return SCHEME_NAME;
    }

    private SqlParameterSource mapAllParameters(final Campaign campaign) {
        final var namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("ID", campaign.getId());
        namedParameters.addValue("NAME", campaign.getName());
        namedParameters.addValue("TEAM_ID", campaign.getTeamID());
        namedParameters.addValue("START_DATE", campaign.getStartDate());
        namedParameters.addValue("EXPIRY_DATE", campaign.getExpiryDate());
        return namedParameters;
    }

}
