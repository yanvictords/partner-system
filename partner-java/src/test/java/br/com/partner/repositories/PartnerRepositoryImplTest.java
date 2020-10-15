package br.com.partner.repositories;

import br.com.partner.repositories.impl.PartnerRepositoryImpl;
import br.com.partner.utils.PartnerUtils;
import br.com.partner.utils.database.mappers.PartnerRowMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Map;

import static br.com.partner.utils.database.queries.PartnerQuery.FIND_BY_EMAIL_QUERY;
import static br.com.partner.utils.database.queries.PartnerQuery.SAVE_QUERY;
import static java.math.BigInteger.ONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PartnerRepositoryImplTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private PartnerRowMapper rowMapper;

    @InjectMocks
    private PartnerRepositoryImpl partnerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        partnerRepository = new PartnerRepositoryImpl(jdbcTemplate, rowMapper);
    }

    @Test
    public void shouldCreatePartnerSuccessfully() {
        final var email = "test@email.com";
        final var newPartner = PartnerUtils.create(null, email);
        final var createdPartner = PartnerUtils.create(ONE, email);

        when(jdbcTemplate.queryForObject(eq(SAVE_QUERY), any(Map.class), eq(rowMapper)))
                .thenReturn(createdPartner);

        partnerRepository.create(newPartner);

        verify(jdbcTemplate, times(1))
                .update(eq(SAVE_QUERY), any(MapSqlParameterSource.class),
                        any(GeneratedKeyHolder.class), any(String[].class));
    }

    @Test
    public void shouldFindPartnerByEmailSuccessfully() {
        final var email = "test@email.com";

        partnerRepository.findByEmail(email);

        verify(jdbcTemplate, times(1))
                .queryForObject(eq(FIND_BY_EMAIL_QUERY), any(Map.class), eq(rowMapper));
    }

}
