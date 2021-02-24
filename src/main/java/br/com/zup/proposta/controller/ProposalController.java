package br.com.zup.proposta.controller;

import br.com.zup.proposta.controller.request.ProposalRequestDto;
import br.com.zup.proposta.dto.ProposalDto;
import br.com.zup.proposta.helper.ProposalHelper;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.ProposalRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/propostas")
public class ProposalController {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalHelper proposalHelper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalController.class);

    private final Tracer tracer;

    public ProposalController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProposalRequestDto proposalRequestDto,
                                    UriComponentsBuilder uriComponentsBuilder) throws IOException {

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("tag.proposal.action", "Create proposal");
        LOGGER.info("Iniciando cadastro de proposta...");

        ResponseEntity<HashMap<String, Object>> result = proposalHelper.checkExistsProposal(proposalRequestDto);
        if (result != null) return result;

        Proposal proposal = proposalRequestDto.toModel();
        proposalRepository.save(proposal);

        proposalHelper.checkCustomerCredit(proposal);
        proposalRepository.save(proposal);

        URI location = uriComponentsBuilder.path("/api/propostas/{id}")
                .buildAndExpand(proposal.getId())
                .toUri();

        LOGGER.info("Proposta criada com sucesso: " + new ProposalDto(proposal));
        return ResponseEntity.created(location).body(new ProposalDto(proposal));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {

        LOGGER.info("Recuperando proposta...");
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("tag.proposal.action", "Find proposal by id");

        Optional<Proposal> proposal = proposalRepository.findById(id);
        if (proposal.isPresent()) {
            return ResponseEntity.ok().body(new ProposalDto(proposal.get()));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
