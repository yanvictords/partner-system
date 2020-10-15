package br.com.partner.repositories.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public abstract class NativeQueryRepository<T> {

    protected static final String FIND_BY_ID_QUERY = "SELECT * FROM %s.%s WHERE ID = :ID";
    protected final NamedParameterJdbcOperations jdbcTemplate;
    private final RowMapper<T> rowMapper;

    public NativeQueryRepository(final NamedParameterJdbcOperations jdbcTemplate, final RowMapper<T> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    protected abstract String getTable();

    protected abstract String getScheme();

    protected List<T> query(final String query, final Map<String, ?> paramMap) {
        return jdbcTemplate.query(query, paramMap, rowMapper);
    }

    protected T queryForObject(final String query, final Map<String, ?> paramMap) {
        return jdbcTemplate.queryForObject(query, paramMap, rowMapper);
    }

    protected T save(final String query, final SqlParameterSource parameterSource) {
        final var holder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, parameterSource, holder, new String[]{"GENERATED_ID"});
        final var key = holder.getKey();
        if (isNull(key)) {
            return null;
        }

        final var generatedId = new BigInteger(key.toString());
        return find(generatedId);
    }

    protected T find(final BigInteger id) {
        final var sqlParameter = new MapSqlParameterSource()
                .addValue("ID", id);
        return queryForObject(getQueryFindById(), sqlParameter.getValues());
    }

    private String getQueryFindById() {
        return format(FIND_BY_ID_QUERY, getScheme(), getTable());
    }

}
