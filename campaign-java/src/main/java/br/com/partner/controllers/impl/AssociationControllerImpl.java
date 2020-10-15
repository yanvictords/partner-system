package br.com.partner.controllers.impl;

import br.com.partner.controllers.AssociationController;
import br.com.partner.parameters.create_association.CreateAssociationRequest;
import br.com.partner.presenters.consult_association.ConsultAssociationResponse;
import br.com.partner.presenters.create_association.CreateAssociationResponse;
import br.com.partner.services.AssociationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class AssociationControllerImpl implements AssociationController {

    private final AssociationService associationService;

    public AssociationControllerImpl(final AssociationService associationService) {
        this.associationService = associationService;
    }

    @Override
    public ResponseEntity<ConsultAssociationResponse> findAllPartnerCampaignAssociation(final BigInteger partnerId) {
        return ResponseEntity.ok(associationService.findAllPartnerCampaignAssociation(partnerId));
    }

    @Override
    public ResponseEntity<CreateAssociationResponse> associate(final BigInteger partnerId,
                                                               final CreateAssociationRequest request) {
        return ResponseEntity.status(CREATED).body(associationService.associate(partnerId, request));
    }

}
