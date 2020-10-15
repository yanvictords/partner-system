package br.com.partner.utils.database.queries;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.partner.models.PartnerCampaign.SCHEME_NAME;
import static br.com.partner.models.PartnerCampaign.TABLE_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerCampaignQuery {

    public static String SAVE_QUERY = "INSERT INTO " + SCHEME_NAME + "." + TABLE_NAME + " " +
            "(PARTNER_ID, CAMPAIGN_ID) " +
            "VALUES (:PARTNER_ID, :CAMPAIGN_ID)";

    public static String FIND_ALL_BY_PARTNER_ID_QUERY = "SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME + " " +
            "WHERE PARTNER_ID = :PARTNER_ID";

}
