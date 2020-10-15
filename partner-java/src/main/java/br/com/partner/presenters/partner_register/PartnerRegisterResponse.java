package br.com.partner.presenters.partner_register;

import br.com.partner.configs.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PartnerRegisterResponse {

    private BigInteger id;
    private String name;
    private String email;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    private BigInteger teamId;

    private List<PartnerRegisterCampaignDTO> campaigns;

}
