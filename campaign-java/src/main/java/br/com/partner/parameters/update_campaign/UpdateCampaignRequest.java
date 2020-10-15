package br.com.partner.parameters.update_campaign;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCampaignRequest {

    @ApiModelProperty(required = true, value = "O nome da campanha é obrigatória", example = "Nome Campanha")
    @NotNull(message = "O campo do nome da campanha não pode ser nulo")
    @NotEmpty(message = "O campo do nome da campanha não pode ser vazio")
    private String name;

    @Positive(message = "O identificador do time deve ser maior que zero")
    @ApiModelProperty(required = true, value = "A campanha deve estar associada a um time", example = "123")
    @NotNull(message = "O identificador do time ser nulo")
    private BigInteger teamID;

    @FutureOrPresent(message = "Data de vigência inválida")
    @ApiModelProperty(required = true, value = "A data de vigência da campanha é obrigatória", example = "2021-01-01")
    @NotNull(message = "O campo de data de vigência da campanha não pode ser ser nulo")
    private LocalDate expiryDate;

}
