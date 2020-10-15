package br.com.partner.utils.database.queries;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.partner.models.Partner.SCHEME_NAME;
import static br.com.partner.models.Partner.TABLE_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerQuery {

    public static String SAVE_QUERY = "INSERT INTO " + SCHEME_NAME + "." + TABLE_NAME + " " +
            "(NAME, EMAIL, BIRTH_DATE, TEAM_ID) " +
            "VALUES (:NAME, :EMAIL, :BIRTH_DATE, :TEAM_ID)";

    public static String FIND_BY_EMAIL_QUERY = "SELECT * FROM " + SCHEME_NAME + "." + TABLE_NAME + " " +
            "WHERE EMAIL = :EMAIL";

}
