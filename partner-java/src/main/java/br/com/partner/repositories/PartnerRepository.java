package br.com.partner.repositories;

import br.com.partner.models.Partner;

public interface PartnerRepository {
    Partner create(Partner partner);

    Partner findByEmail(String email);
}
