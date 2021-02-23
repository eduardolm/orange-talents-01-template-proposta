package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.Blocked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedRepository extends JpaRepository<Blocked, Long> {
}
