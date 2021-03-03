package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.BiometryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BiometryImageRepository extends JpaRepository<BiometryImage, Long> {

    Optional<BiometryImage> findByOriginalFileNameAndCreditCard_Id(String name, String id);
}
