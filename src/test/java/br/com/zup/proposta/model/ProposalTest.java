package br.com.zup.proposta.model;

import br.com.zup.proposta.builder.AddressRequestDtoBuilder;
import br.com.zup.proposta.builder.ProposalRequestDtoBuilder;
import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.controller.request.ProposalRequestDto;
import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.enums.Status;
import br.com.zup.proposta.repository.ProposalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProposalTest {

    @Autowired
    private ProposalRepository proposalRepository;

    private AddressRequestDto address;
    private ProposalRequestDto proposalRequest;
    private Proposal proposal;
    private CreditCard creditCard;


    @BeforeEach
    public void setup() {
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
        this.proposal = proposalRequest.toModel();

        CreditCard creditCard = new CreditCard("1236-4567-4564-1236", LocalDateTime.now(), "Test User",
                null, null, null, null, 3000,  null,
                new DueDate("b2a77d4f-1e15-4287-b66c-552172705a27", 30,
                LocalDateTime.of(2020, 9, 25, 15, 25)),
                this.proposalRequest.toModel(), CreditCardStatus.ATIVO);
        this.creditCard = creditCard;
    }

    @AfterEach
    public void rollbackDatabase() {
        proposalRepository.deleteAll();
    }

    @Test
    public void shouldThrowResponseStatusException() {
        Proposal proposal = new Proposal();

        assertThrows(ResponseStatusException.class, () -> proposal.updateStatus("Request"));
    }

    @Test
    public void shouldSetStatusToNaoElegivel() {
        Proposal proposal = new Proposal();

        proposal.updateStatus("COM_RESTRICAO");

        assertEquals(Status.NAO_ELEGIVEL, proposal.getStatus());
    }

    @Test
    public void shouldSetStatusToElegivel() {
        Proposal proposal = new Proposal();

        proposal.updateStatus("SEM_RESTRICAO");

        assertEquals(Status.ELEGIVEL, proposal.getStatus());
    }

    @Test
    public void shouldCreateProposal() {
        AddressRequestDto address = new AddressRequestDtoBuilder()
                .withStreet("Rua da Bica")
                .withZip("77823-300")
                .withNumber("254")
                .withComplement("Casa")
                .build();


        ProposalRequestDto proposalRequestDto = new ProposalRequestDtoBuilder()
                .withDocument("116.238.120-54")
                .withName("José Santos")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(address)
                .build();

        Proposal proposal = proposalRequestDto.toModel();

        assertEquals("116.238.120-54", proposal.getDocument());
        assertEquals("José Santos", proposal.getName());
        assertEquals("jose@email.com", proposal.getEmail());
        assertEquals("Rua da Bica", proposal.getAddress().getStreet());
    }

    @Test
    public void shouldConstructorCreateProposal() {
        Address address = new AddressRequestDtoBuilder()
                .withStreet("Rua da Bica")
                .withZip("77823-300")
                .withNumber("254")
                .withComplement("Casa")
                .build().toModel();

        Proposal proposal = new Proposal("116.238.120-54", "José Santos", "jose@email.com",
                new BigDecimal("2500"), address, Status.ELEGIVEL);

        assertEquals("116.238.120-54", proposal.getDocument());
        assertEquals("José Santos", proposal.getName());
        assertEquals("jose@email.com", proposal.getEmail());
        assertEquals("Rua da Bica", proposal.getAddress().getStreet());
    }

    @Test
    public void testEquals() {
        Proposal proposal = new Proposal("Document", "Name", "jane.doe@example.org", null, new Address(), Status.ELEGIVEL);
        BigDecimal income = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals1() {
        Proposal proposal = new Proposal("Document", "Name", "jane.doe@example.org", BigDecimal.valueOf(42L), null, Status.ELEGIVEL);
        BigDecimal income = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals2() {
        BigDecimal income = BigDecimal.valueOf(42L);
        Proposal proposal = new Proposal("Document", "Name", "jane.doe@example.org", income,
                new Address("Street", "21654", "Number", "Complement"), Status.ELEGIVEL);
        BigDecimal income1 = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income1, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals3() {
        Proposal proposal = new Proposal();
        BigDecimal income = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal(null, null, "jane.doe@example.org", income, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals4() {
        Proposal proposal = new Proposal();

        assertEquals(new Proposal(), proposal);
    }

    @Test
    public void testEquals5() {
        Proposal proposal = new Proposal();
        BigDecimal income = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals6() {
        BigDecimal income = BigDecimal.valueOf(42L);
        Proposal proposal = new Proposal("Document", "Name", "jane.doe@example.org", income, new Address(), Status.ELEGIVEL);
        BigDecimal income1 = BigDecimal.valueOf(42L);

        assertEquals(new Proposal("Document", "Name", "jane.doe@example.org", income1, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals7() {
        Proposal proposal = new Proposal();
        BigDecimal income = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal(null, "Name", "jane.doe@example.org", income, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals8() {
        BigDecimal income = BigDecimal.valueOf(42L);
        Proposal proposal = new Proposal("Name", "Name", "jane.doe@example.org", income, new Address(), Status.ELEGIVEL);
        BigDecimal income1 = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income1, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals9() {
        BigDecimal income = BigDecimal.valueOf(42L);
        Proposal proposal = new Proposal("Document", "Document", "jane.doe@example.org", income, new Address(), Status.ELEGIVEL);
        BigDecimal income1 = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income1, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals10() {
        BigDecimal income = BigDecimal.valueOf(42L);
        Proposal proposal = new Proposal("Document", "Name", "Document", income, new Address(), Status.ELEGIVEL);
        BigDecimal income1 = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income1, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals11() {
        BigDecimal income = BigDecimal.valueOf(42L);
        Proposal proposal = new Proposal("Document", "Name", null, income, new Address(), Status.ELEGIVEL);
        BigDecimal income1 = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income1, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testEquals12() {
        BigDecimal income = BigDecimal.valueOf(0L);
        Proposal proposal = new Proposal("Document", "Name", "jane.doe@example.org", income, new Address(), Status.ELEGIVEL);
        BigDecimal income1 = BigDecimal.valueOf(42L);

        assertNotEquals(new Proposal("Document", "Name", "jane.doe@example.org", income1, new Address(), Status.ELEGIVEL), proposal);
    }

    @Test
    public void testHashCode() {
        assertEquals(0, (new Proposal()).hashCode());
    }

    @Test
    public void testHashCode3() {
        Proposal proposal = new Proposal();
        proposal.updateCreditCard(new CreditCard());

        assertEquals(0, proposal.hashCode());
    }

    @Test
    public void testUpdateStatus() {
        assertThrows(ResponseStatusException.class, () -> (new Proposal()).updateStatus("Request"));
    }

    @Test
    public void testUpdateStatus2() {
        Proposal proposal = new Proposal();

        proposal.updateStatus("COM_RESTRICAO");

        assertEquals(Status.NAO_ELEGIVEL, proposal.getStatus());
    }

    @Test
    public void testUpdateStatus3() {
        Proposal proposal = new Proposal();

        proposal.updateStatus("SEM_RESTRICAO");

        assertEquals(Status.ELEGIVEL, proposal.getStatus());
    }

    @Test
    public void testUpdateCreditCard() {
        Proposal proposal = new Proposal();
        CreditCard creditCard = new CreditCard();

        proposal.updateCreditCard(creditCard);

        assertSame(creditCard, proposal.getCreditCard());
    }
}

