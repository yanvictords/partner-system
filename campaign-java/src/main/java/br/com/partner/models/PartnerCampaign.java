package br.com.partner.models;

import lombok.*;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class PartnerCampaign {

    public static final String SCHEME_NAME = "partnerdatabase";
    public static final String TABLE_NAME = "PARTNER_CAMPAIGN";

    private BigInteger id;
    private BigInteger partnerId;
    private BigInteger campaignId;

}
