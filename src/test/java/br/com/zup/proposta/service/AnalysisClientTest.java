package br.com.zup.proposta.service;

import br.com.zup.proposta.builder.AddressRequestDtoBuilder;
import br.com.zup.proposta.builder.ProposalRequestDtoBuilder;
import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.controller.request.ProposalRequestDto;
import br.com.zup.proposta.controller.request.StatusRequestDto;
import br.com.zup.proposta.controller.response.StatusResponseDto;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.ProposalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;

import static br.com.zup.proposta.config.AnalysisClientMocks.setupAnalysisClientResponseComRestricao;
import static br.com.zup.proposta.config.AnalysisClientMocks.setupAnalysisClientResponseSemRestricao;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AnalysisClientTest {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private WireMockServer analysisClientMockService;

    @Autowired
    private AnalysisClient analysisClient;

    @Autowired
    private ObjectMapper mapper;

    private AddressRequestDto address;
    private ProposalRequestDto proposalRequest;
    private StatusResponseDto response;

    @BeforeEach
    public void setup() throws IOException {
        this.analysisClientMockService.start();
        // Create address
        AddressRequestDto address = new AddressRequestDtoBuilder()
                .withStreet("Rua da Bica")
                .withZip("77823-300")
                .withNumber("254")
                .withComplement("Casa")
                .build();

        this.address = address;

        // Create proposal
        ProposalRequestDto proposalRequestDto = new ProposalRequestDtoBuilder()
                .withDocument("116.238.120-54")
                .withName("José Santos")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        this.proposalRequest = proposalRequestDto;
    }

    @AfterEach
    public void rollbackDatabase() {
        proposalRepository.deleteAll();
        this.analysisClientMockService.stop();
    }

    @Test
    public void testStatusRequestDtoConstructor() {
        StatusRequestDto actualStatusRequestDto = new StatusRequestDto(new Proposal());

        assertNull(actualStatusRequestDto.getDocumento());
        assertNull(actualStatusRequestDto.getIdProposta());
        assertNull(actualStatusRequestDto.getNome());
    }

    @Test
    public void shouldCreateStatusRequestDto() {

        Proposal proposal = this.proposalRequest.toModel();
        proposalRepository.save(proposal);

        StatusRequestDto statusRequestDto = new StatusRequestDto(proposal);

        assertEquals("116.238.120-54", statusRequestDto.getDocumento());
        assertEquals("José Santos", statusRequestDto.getNome());
        assertEquals(proposal.getId(), statusRequestDto.getIdProposta());
    }

    @Test
    public void shouldCreateStatusResponse() {
        Proposal proposal = this.proposalRequest.toModel();
        proposalRepository.save(proposal);

        StatusRequestDto statusRequestDto = new StatusRequestDto(proposal);
        String resultadoSolicitacao = "COM_RESTRICAO";

        StatusResponseDto response = new StatusResponseDto(statusRequestDto.getDocumento(),
                statusRequestDto.getNome(), resultadoSolicitacao, statusRequestDto.getIdProposta());

        assertEquals("116.238.120-54", response.getDocumento());
        assertEquals("José Santos", response.getNome());
        assertEquals(proposal.getId(), response.getIdProposta());
        assertEquals(resultadoSolicitacao, response.getResultadoSolicitacao());
    }

    @Test
    public void shouldReturnSEM_RESTRICAO() throws Exception {
        setupAnalysisClientResponseSemRestricao(analysisClientMockService);
        Proposal proposal = this.proposalRequest.toModel();
        proposalRepository.save(proposal);
        StatusRequestDto statusRequestDto = new StatusRequestDto(proposal);

        this.response = analysisClient.checkCustomer(statusRequestDto);

        assertEquals("116.238.120-54", response.getDocumento());
        assertEquals("SEM_RESTRICAO", response.getResultadoSolicitacao());
    }

    @Test
    public void shouldReturnCOM_RESTRICAO() throws Exception {
        setupAnalysisClientResponseComRestricao(analysisClientMockService);

        ProposalRequestDto proposalRequestDto1 = new ProposalRequestDtoBuilder()
                .withDocument("311.853.830-93")
                .withName("José Santos")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        Proposal proposal = proposalRequestDto1.toModel();
        proposalRepository.save(proposal);
        StatusRequestDto statusRequestDto = new StatusRequestDto(proposal);

        try {
            this.response = analysisClient.checkCustomer(statusRequestDto);
        }
        catch (FeignException.UnprocessableEntity ex) {
            this.response = mapper.readValue(ex.contentUTF8(), StatusResponseDto.class);
        }

        assertEquals("311.853.830-93", this.response.getDocumento());
        assertEquals("José Santos", this.response.getNome());
        assertEquals(proposal.getId(), this.response.getIdProposta());
        assertEquals("COM_RESTRICAO", this.response.getResultadoSolicitacao());
    }

    @Test
    public void shouldThrowFeignExceptionUnprocessableEntityIfCustomerHasRestrictions() throws Exception {
        setupAnalysisClientResponseComRestricao(analysisClientMockService);

        ProposalRequestDto proposalRequestDto1 = new ProposalRequestDtoBuilder()
                .withDocument("311.853.830-93")
                .withName("José Santos")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        Proposal proposal = proposalRequestDto1.toModel();
        proposalRepository.save(proposal);
        StatusRequestDto statusRequestDto = new StatusRequestDto(proposal);

        assertThrows(FeignException.class, () -> this.response = analysisClient.checkCustomer(statusRequestDto));
    }

}

