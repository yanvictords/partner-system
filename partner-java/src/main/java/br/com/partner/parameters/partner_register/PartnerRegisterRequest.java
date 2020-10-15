package br.com.partner.parameters.partner_register;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerRegisterRequest {

    @ApiModelProperty(required = true, value = "O nome do sócio é obrigatório", example = "Yan Victor")
    @NotNull(message = "O nome do sócio não pode ser nulo")
    @NotEmpty(message = "O nome do sócio não pode ser vazio")
    private String name;

    @ApiModelProperty(required = true,
            value = "O email do sócio é obrigatório",
            example = "yanvictor_ds@hormail.com")
    @NotNull(message = "O email do sócio não pode ser nulo")
    @NotEmpty(message = "O email do sócio não pode ser vazio")
    private String email;

    @Past(message = "Data de nascimento inválida")
    @ApiModelProperty(required = true, value = "A data de nascimento do sócio é obrigatória", example = "1997-01-01")
    @NotNull(message = "A data de nascimento do sócio não pode ser ser nulo")
    private LocalDate birthDate;

    @Positive(message = "O identificador do time deve ser maior que zero")
    @ApiModelProperty(required = true, value = "A sócio deve estar associado a um time", example = "123")
    @NotNull(message = "O identificador do time não pode ser nulo")
    private BigInteger teamId;

}
