package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.TravelNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelNoteRepository extends JpaRepository<TravelNote, Long> {

    TravelNote findByCreditCard_Id(String id);
}
