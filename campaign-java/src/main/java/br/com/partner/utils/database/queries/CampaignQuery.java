package br.com.partner.utils.database.queries;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.partner.models.Campaign.SCHEME_NAME;
import static br.com.partner.models.Campaign.TABLE_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CampaignQuery {

    public static String SAVE_QUERY = "INSERT INTO " + SCHEME_NAME + "." + TABLE_NAME + " " +
            "(NAME, TEAM_ID, START_DATE, EXPIRY_DATE) " +
            "VALUES (:NAME, :TEAM_ID, :START_DATE, :EXPIRY_DATE)";

    public static String UPDATE_QUERY = "UPDATE " + SCHEME_NAME + "." + TABLE_NAME + " SET " +
            "NAME = :NAME, TEAM_ID = :TEAM_ID, EXPIRY_DATE = :EXPIRY_DATE " +
            "WHERE ID = :ID";

    public static String FIND_BY_TEAM_ID_AND_NOT_EXPIRED_QUERY =
            "SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME + " " +
                    "WHERE TEAM_ID = :TEAM_ID " +
                    "AND EXPIRY_DATE > NOW()";

    public static String FIND_BY_IDS_AND_NOT_EXPIRED_QUERY =
            "SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME + " " +
                    "WHERE ID IN (:IDS) " +
                    "AND EXPIRY_DATE > NOW()";

    public static final String DELETE_BY_ID_QUERY = "DELETE FROM " + SCHEME_NAME + "." + TABLE_NAME + " " +
            "WHERE ID = :ID";

    public static final String FIND_ALL_NOT_EXPIRED_BY_LESS_OR_EQUALS_EXPIRY_DATE_ASC =
            "SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME + " " +
                    "WHERE EXPIRY_DATE <= :EXPIRY_DATE " +
                    "AND EXPIRY_DATE > NOW() " +
                    "ORDER BY EXPIRY_DATE ASC";

}
