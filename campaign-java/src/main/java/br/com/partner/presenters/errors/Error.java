package br.com.partner.presenters.errors;

import br.com.partner.utils.RequestUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    @ApiModelProperty(required = true, example = "1996-10-06T21:00:13.086Z",
            value = "The timestamp where the error occurred.")
    private String timestamp;

    @ApiModelProperty(example = "Bad Request", required = true, value = "The description of the HTTP status.")
    private String error;

    @ApiModelProperty(example = "400", required = true,
            value = "The HTTP status. Must be the same status returned as request response.")
    @Valid
    @DecimalMin("100")
    @DecimalMax("599")
    private Integer status;

    @ApiModelProperty(required = true, example = "/campaign")
    private String path;

    @JsonProperty
    @ApiModelProperty
    private Set<ErrorDetail> errors;

    @Builder
    public Error(final HttpStatus status, final Set<ErrorDetail> errors) {
        super();
        if (!Objects.isNull(status)) {
            this.error = status.name();
            this.status = status.value();
        }
        this.timestamp = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
        this.path = RequestUtils.getContextPath();
        this.errors = errors;
    }

    public void updatePath() {
        this.path = RequestUtils.getContextPath();
    }

}
