package br.com.partner.repositories.impl;

import br.com.partner.models.Partner;
import br.com.partner.repositories.PartnerRepository;
import br.com.partner.utils.database.mappers.PartnerRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

import static br.com.partner.models.Partner.SCHEME_NAME;
import static br.com.partner.models.Partner.TABLE_NAME;
import static br.com.partner.utils.database.queries.PartnerQuery.FIND_BY_EMAIL_QUERY;
import static br.com.partner.utils.database.queries.PartnerQuery.SAVE_QUERY;

@Service
public class PartnerRepositoryImpl extends NativeQueryRepository<Partner> implements PartnerRepository {

    public PartnerRepositoryImpl(final NamedParameterJdbcOperations jdbcTemplate,
                                 final PartnerRowMapper rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public Partner create(final Partner partner) {
        final var namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("NAME", partner.getName());
        namedParameters.addValue("EMAIL", partner.getEmail());
        namedParameters.addValue("BIRTH_DATE", partner.getBirthDate());
        namedParameters.addValue("TEAM_ID", partner.getTeamId());
        return save(SAVE_QUERY, namedParameters);
    }

    @Override
    public Partner findByEmail(final String email) {
        final var parameters = Map.of("EMAIL", email);
        return queryForObject(FIND_BY_EMAIL_QUERY, parameters);
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
