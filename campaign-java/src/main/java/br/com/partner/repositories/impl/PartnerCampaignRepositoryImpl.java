package br.com.partner.repositories.impl;

import br.com.partner.models.PartnerCampaign;
import br.com.partner.repositories.PartnerCampaignRepository;
import br.com.partner.utils.database.mappers.PartnerCampaignRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static br.com.partner.models.PartnerCampaign.SCHEME_NAME;
import static br.com.partner.models.PartnerCampaign.TABLE_NAME;
import static br.com.partner.utils.database.queries.PartnerCampaignQuery.FIND_ALL_BY_PARTNER_ID_QUERY;
import static br.com.partner.utils.database.queries.PartnerCampaignQuery.SAVE_QUERY;

@Service
public class PartnerCampaignRepositoryImpl extends NativeQueryRepository<PartnerCampaign>
        implements PartnerCampaignRepository {

    public PartnerCampaignRepositoryImpl(final NamedParameterJdbcOperations jdbcTemplate,
                                         final PartnerCampaignRowMapper rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public PartnerCampaign create(final PartnerCampaign partnerCampaign) {
        final var namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("PARTNER_ID", partnerCampaign.getPartnerId());
        namedParameters.addValue("CAMPAIGN_ID", partnerCampaign.getCampaignId());
        return save(SAVE_QUERY, namedParameters);
    }

    @Override
    public List<PartnerCampaign> findAllByPartnerId(final BigInteger partnerID) {
        final var parameters = Map.of("PARTNER_ID", partnerID);
        return query(FIND_ALL_BY_PARTNER_ID_QUERY, parameters);
    }

    @Override
    protected String getTable() {
        return TABLE_NAME;
    }

    @Override
    protected String getScheme() {
        return SCHEME_NAME;
    }

}
