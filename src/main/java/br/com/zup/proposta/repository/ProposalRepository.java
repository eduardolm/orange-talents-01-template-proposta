package br.com.zup.proposta.repository;

import br.com.zup.proposta.enums.Status;
import br.com.zup.proposta.model.Proposal;
import org.hibernate.LockOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    boolean existsByDocument(String document);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = (LockOptions.SKIP_LOCKED + ""))
    })
    List<Proposal> findTop5ByStatusOrderByIdAsc(Status status);
}
