package br.com.partner.services;

import br.com.partner.parameters.partner_register.PartnerRegisterRequest;
import br.com.partner.presenters.partner_register.PartnerRegisterResponse;

public interface PartnerService {

    PartnerRegisterResponse register(PartnerRegisterRequest request);

}
