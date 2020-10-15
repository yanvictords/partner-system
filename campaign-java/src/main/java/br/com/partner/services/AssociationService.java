package br.com.partner.services;

import br.com.partner.parameters.create_association.CreateAssociationRequest;
import br.com.partner.presenters.consult_association.ConsultAssociationResponse;
import br.com.partner.presenters.create_association.CreateAssociationResponse;

import java.math.BigInteger;

public interface AssociationService {

    CreateAssociationResponse associate(BigInteger partnerId, CreateAssociationRequest request);

    ConsultAssociationResponse findAllPartnerCampaignAssociation(BigInteger partnerId);

}
