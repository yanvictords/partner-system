package br.com.partner.presenters.clients.consult_association;

import br.com.partner.configs.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultAssociationDTO {

    private BigInteger id;
    private String name;
    private BigInteger teamID;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate expiryDate;

}
