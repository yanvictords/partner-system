package br.com.partner.utils.mappers;

import br.com.partner.models.Partner;
import br.com.partner.parameters.partner_register.PartnerRegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.partner.utils.BeanUtilsConfig.getBean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerMapper {

    public static Partner map(final PartnerRegisterRequest request) {
        final var objectMapper = getBean(ObjectMapper.class);
        return objectMapper.convertValue(request, Partner.class);
    }

}
