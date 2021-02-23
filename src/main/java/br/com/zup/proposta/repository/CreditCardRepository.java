package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {

    CreditCard findByProposal(Proposal proposal);

    Optional<CreditCard> findByProposal_Id(Long id);
}
