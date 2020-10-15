package br.com.partner.services.impl;

import br.com.partner.models.Partner;
import br.com.partner.parameters.partner_register.PartnerRegisterRequest;
import br.com.partner.presenters.partner_register.PartnerRegisterCampaignDTO;
import br.com.partner.presenters.partner_register.PartnerRegisterResponse;
import br.com.partner.repositories.PartnerRepository;
import br.com.partner.services.PartnerService;
import br.com.partner.services.impl.facades.AssociationFacade;
import br.com.partner.utils.mappers.PartnerMapper;
import br.com.partner.utils.mappers.PartnerRegisterCampaignDTOMapper;
import br.com.partner.utils.mappers.PartnerRegisterResponseMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final AssociationFacade associationFacade;

    private final PartnerRepository partnerRepository;

    public PartnerServiceImpl(final AssociationFacade associationFacade,
                              final PartnerRepository partnerRepository) {
        this.associationFacade = associationFacade;
        this.partnerRepository = partnerRepository;
    }

    @Override
    public PartnerRegisterResponse register(final PartnerRegisterRequest request) {

        var partner = findByEmail(request.getEmail());
        if (isNull(partner)) {
            partner = createPartner(request);
        }

        final var myCampaigns = associateCampaigns(partner);
        return PartnerRegisterResponseMapper.map(partner)
                .toBuilder()
                .campaigns(myCampaigns)
                .build();
    }

    private Partner findByEmail(final String email) {
        try {
            return partnerRepository.findByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Partner createPartner(final PartnerRegisterRequest request) {
        final var partner = PartnerMapper.map(request);
        return partnerRepository.create(partner);
    }

    private List<PartnerRegisterCampaignDTO> associateCampaigns(final Partner partner) {
        return associationFacade.updateCampaignAssociations(partner)
                .stream()
                .map(PartnerRegisterCampaignDTOMapper::map)
                .collect(toList());
    }

}
