package br.com.partner.presenters.consult_association;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultAssociationResponse {

    private List<ConsultAssociationDTO> campaigns;

    @JsonIgnore
    public List<BigInteger> getCampaignIds() {
        if (isEmpty(campaigns)) {
            return newArrayList();
        }

        return campaigns.stream()
                .map(ConsultAssociationDTO::getId)
                .collect(toList());
    }

}
