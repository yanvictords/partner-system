package br.com.partner.presenters.errors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "message")
public class ErrorDetail {

	@ApiModelProperty(example = "The attribute {1} must be filled.", required = true, value = "Description of the error.")
	private String message;

}
