package br.com.partner.utils.mappers;

import br.com.partner.models.Partner;
import br.com.partner.presenters.partner_register.PartnerRegisterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.partner.utils.BeanUtilsConfig.getBean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerRegisterResponseMapper {

    public static PartnerRegisterResponse map(final Partner partner) {
        final var objectMapper = getBean(ObjectMapper.class);
        return objectMapper.convertValue(partner, PartnerRegisterResponse.class);
    }

}
