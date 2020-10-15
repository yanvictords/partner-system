package br.com.partner.parameters.create_association;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssociationRequest {

    @ApiModelProperty(required = true, value = "A lista de campanhas é obrigatória")
    @NotNull(message = "A lista de campanhas não pode ser nula")
    @NotEmpty(message = "A lista de campanhas não deve ser vazia")
    private List<BigInteger> campaignIds;

}
