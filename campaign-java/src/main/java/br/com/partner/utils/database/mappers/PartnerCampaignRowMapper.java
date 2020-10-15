package br.com.partner.utils.database.mappers;

import br.com.partner.models.PartnerCampaign;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PartnerCampaignRowMapper implements RowMapper<PartnerCampaign> {

    @Override
    public PartnerCampaign mapRow(final ResultSet rs, int i) throws SQLException {
        return PartnerCampaign.builder()
                .id(rs.getObject("ID", BigInteger.class))
                .partnerId(rs.getObject("PARTNER_ID", BigInteger.class))
                .campaignId(rs.getObject("CAMPAIGN_ID", BigInteger.class))
                .build();
    }
}
