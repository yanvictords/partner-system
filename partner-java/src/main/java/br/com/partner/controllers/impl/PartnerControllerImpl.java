package br.com.partner.controllers.impl;

import br.com.partner.controllers.PartnerController;
import br.com.partner.parameters.partner_register.PartnerRegisterRequest;
import br.com.partner.presenters.partner_register.PartnerRegisterResponse;
import br.com.partner.services.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class PartnerControllerImpl implements PartnerController {

    private final PartnerService partnerService;

    public PartnerControllerImpl(final PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Override
    public ResponseEntity<PartnerRegisterResponse> register(@Valid PartnerRegisterRequest request) {
        return ResponseEntity.status(CREATED).body(partnerService.register(request));
    }

}
