package br.com.partner.models;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Campaign {

    public static final String SCHEME_NAME = "partnerdatabase";
    public static final String TABLE_NAME = "CAMPAIGN";

    private BigInteger id;
    private String name;
    private BigInteger teamID;
    private LocalDate startDate;
    private LocalDate expiryDate;

    public Boolean isExpired() {
        if (isNull(expiryDate)) {
            return TRUE;
        }
        return expiryDate.isBefore(LocalDate.now());
    }

    public void plusOneDayExpiryDate() {
        if (!isNull(expiryDate)) {
            expiryDate = expiryDate.plusDays(1);
        }
    }

    public Boolean expiresEqual(final Campaign inCampaign) {
        final var inExpiryDate = inCampaign.getExpiryDate();
        if (isNull(expiryDate) || isNull(inExpiryDate)) {
            return false;
        }

        return expiryDate.isEqual(inExpiryDate);
    }

}
