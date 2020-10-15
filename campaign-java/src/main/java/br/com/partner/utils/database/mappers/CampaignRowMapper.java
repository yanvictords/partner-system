package br.com.partner.utils.database.mappers;

import br.com.partner.models.Campaign;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class CampaignRowMapper implements RowMapper<Campaign> {

    @Override
    public Campaign mapRow(final ResultSet rs, int i) throws SQLException {
        return Campaign.builder()
                .id(rs.getObject("ID", BigInteger.class))
                .name(rs.getString("NAME"))
                .teamID(rs.getObject("TEAM_ID", BigInteger.class))
                .startDate(rs.getObject("START_DATE", LocalDate.class))
                .expiryDate(rs.getObject("EXPIRY_DATE", LocalDate.class))
                .build();
    }
}
