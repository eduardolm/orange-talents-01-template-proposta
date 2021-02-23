package br.com.zup.proposta.controller;

import br.com.zup.proposta.builder.AddressRequestDtoBuilder;
import br.com.zup.proposta.builder.ProposalRequestDtoBuilder;
import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.controller.request.ProposalRequestDto;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.ProposalRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProposalRepository proposalRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressRequestDto address;
    private ProposalRequestDto proposalRequest;

    @BeforeEach
    public void setup() throws IOException {

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
                .withName("José Santos Silva Filho")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        this.proposalRequest = proposalRequestDto;
    }

    @AfterEach
    public void rollbackDatabase() {
        proposalRepository.deleteAll();
    }

    @Test
    public void shouldCreateProposal() throws Exception {

        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        Proposal proposal = this.proposalRequest.toModel();
        proposalRepository.save(proposal);

        when(proposalRepository.save(proposal)).thenReturn(proposal);

        mockMvc.perform(post("/api/propostas")
                .content(objectMapper.writeValueAsString(proposalRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void shouldReturnBadRequestWhenAddressIsNull() throws Exception {
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        AddressRequestDto address1 = new AddressRequestDto();
        ProposalRequestDto proposalRequest1 = new ProposalRequestDtoBuilder()
                .withDocument("116.238.120-54")
                .withName("José Santos")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(address1)
                .build();

        Proposal proposal1 = proposalRequest1.toModel();
        proposalRepository.save(proposal1);

        when(proposalRepository.save(proposal1)).thenReturn(proposal1);

        mockMvc.perform(post("/api/propostas")
                .content(objectMapper.writeValueAsString(proposalRequest1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        ProposalRequestDto proposalRequest1 = new ProposalRequestDtoBuilder()
                .withDocument("116.238.120-54")
                .withName("")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        Proposal proposal1 = proposalRequest1.toModel();
        proposalRepository.save(proposal1);

        when(proposalRepository.save(proposal1)).thenReturn(proposal1);

        mockMvc.perform(post("/api/propostas")
                .content(objectMapper.writeValueAsString(proposalRequest1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void shouldReturnBadRequestWhenDocumentIsNull() throws Exception {
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        ProposalRequestDto proposalRequest1 = new ProposalRequestDtoBuilder()
                .withDocument("")
                .withName("José Santos")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        Proposal proposal1 = proposalRequest1.toModel();
        proposalRepository.save(proposal1);

        when(proposalRepository.save(proposal1)).thenReturn(proposal1);

        mockMvc.perform(post("/api/propostas")
                .content(objectMapper.writeValueAsString(proposalRequest1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void shouldReturnBadRequestWhenDocumentIsInvalid() throws Exception {
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        ProposalRequestDto proposalRequest1 = new ProposalRequestDtoBuilder()
                .withDocument("123.456.789-01")
                .withName("José Santos")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        Proposal proposal1 = proposalRequest1.toModel();
        proposalRepository.save(proposal1);

        when(proposalRepository.save(proposal1)).thenReturn(proposal1);

        mockMvc.perform(post("/api/propostas")
                .content(objectMapper.writeValueAsString(proposalRequest1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void shouldReturnProposalWhenIdExists() throws Exception {
        Proposal proposal = this.proposalRequest.toModel();
        proposalRepository.save(proposal);

        when(proposalRepository.findById(1L)).thenReturn(java.util.Optional.of(proposal));

        mockMvc.perform(get("/api/propostas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldReturn404WhenProposalDoesNotExist() throws Exception {
        Proposal proposal = this.proposalRequest.toModel();
        proposalRepository.save(proposal);

        when(proposalRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/propostas/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
