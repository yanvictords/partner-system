package br.com.partner.utils.database.mappers;

import br.com.partner.models.Partner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class PartnerRowMapper implements RowMapper<Partner> {

    @Override
    public Partner mapRow(final ResultSet rs, int i) throws SQLException {
        return Partner.builder()
                .id(rs.getObject("ID", BigInteger.class))
                .name(rs.getString("NAME"))
                .email(rs.getString("EMAIL"))
                .birthDate(rs.getObject("BIRTH_DATE", LocalDate.class))
                .teamId(rs.getObject("TEAM_ID", BigInteger.class))
                .build();
    }
}
