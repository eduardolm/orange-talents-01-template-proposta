package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.CreditCardController;
import br.com.zup.proposta.controller.request.BiometryImageRequestDto;
import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.model.*;
import br.com.zup.proposta.repository.BlockedRepository;
import br.com.zup.proposta.repository.CreditCardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
public class CreditCardServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private BlockedRepository blockedRepository;

    @MockBean
    private BiometricService biometricService;

    @MockBean
    private CreditCardController controller;

    @Autowired
    private WebApplicationContext wContext;

    private MultipartFile mockMultipartFile;
    private CreditCard creditCard;
    private BiometryImage biometryImage;
    private BiometryImageRequestDto biometryImageRequestDto;

    @BeforeEach
    public void setup() throws IOException {
        // Create Creditcard
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<Note> noteSet = new HashSet<>();
        HashSet<Wallet> walletSet = new HashSet<>();
        HashSet<Installment> installmentSet = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate("Test id", 20, LocalDateTime.now());
        Proposal proposal = new Proposal();

        this.creditCard = new CreditCard("42", createdAt, "Name", blockedSet, noteSet, walletSet,
                installmentSet, 1, renegotiation, dueDate, proposal, CreditCardStatus.ATIVO);

        creditCardRepository.save(this.creditCard);


        // Create BiometricImage
        String fileName = "src/test/resources/test-data/img/CPF.jpg";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        MultipartFile mockMultipartFile = new MockMultipartFile("CPF.jpg",
                "CPF.jpg",
                "image/jpg",
                inputStream);

        this.mockMultipartFile = mockMultipartFile;
        this.biometryImageRequestDto = new BiometryImageRequestDto(mockMultipartFile);
        File file = fileService.convertMultiPartToFile(mockMultipartFile);
        String imageFile = fileService.convertToBase64(fileService.readBytesFromFile(file));

        this.biometryImage = new BiometryImage(this.creditCard, imageFile, file.getName());
    }

    @AfterEach
    public void rollbackDatabase() {
        blockedRepository.deleteAll();
        creditCardRepository.deleteAll();
    }

    @Test
    public void shouldReturnFalseForUnblockedCreditCard() {
        assertFalse(creditCardService.checkIfCardIsAlreadyBlocked(this.creditCard));
    }

    @Test
    public void shoudlReturnTrueForBLockedCreditCard() {
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<Note> noteSet = new HashSet<>();
        HashSet<Wallet> walletSet = new HashSet<>();
        HashSet<Installment> installmentSet = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate("Test id", 20, LocalDateTime.now());
        Proposal proposal = new Proposal();

        CreditCard creditCard1 = new CreditCard("42", createdAt, "Name", blockedSet, noteSet, walletSet,
                installmentSet, 1, renegotiation, dueDate, proposal, CreditCardStatus.ATIVO);
        Blocked blocked = new Blocked("Test", true, creditCard1);
        blockedSet.add(blocked);

        assertTrue(creditCardService.checkIfCardIsAlreadyBlocked(creditCard1));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfBiometryAlreadyAdded() throws IllegalArgumentException {

        assertThrows(IllegalArgumentException.class, ()  -> creditCardService.addBiometry(this.biometryImageRequestDto, this.creditCard));
    }

    @Test
    public void shouldAddBiometry() throws Exception {
        String fileName2 = "src/test/resources/test-data/img/Teste.jpg";
        ClassLoader classLoader2 = getClass().getClassLoader();
        InputStream inputStream2 = classLoader2.getResourceAsStream(fileName2);
        MultipartFile mockMultipartFile2 = new MockMultipartFile("Teste.jpg",
                "Teste.jpg",
                "image/jpg",
                inputStream2);

        BiometryImage biometryImage1 = new BiometryImage(this.creditCard, "test file", "Teste.jpg");
        var biometryImageRequestDto2 = new BiometryImageRequestDto(mockMultipartFile2);
        when(biometricService.uploadImage(biometryImageRequestDto2, this.creditCard)).thenReturn(biometryImage1);

        BiometryImage biometryImage2 = creditCardService.addBiometry(biometryImageRequestDto2, this.creditCard);

        assertEquals("Teste.jpg", biometryImage2.getOriginalFileName());
    }
}
