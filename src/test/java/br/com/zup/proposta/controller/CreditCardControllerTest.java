package br.com.zup.proposta.controller;

import br.com.zup.proposta.controller.request.BiometryImageRequestDto;
import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.model.*;
import br.com.zup.proposta.repository.CreditCardRepository;
import br.com.zup.proposta.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @Autowired
    private FileService fileService;

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
        HashSet<TravelNote> travelNoteSet = new HashSet<>();
        HashSet<Wallet> walletSet = new HashSet<>();
        HashSet<Installment> installmentSet = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate("Test id", 20, LocalDateTime.now());
        Proposal proposal = new Proposal();

        this.creditCard = new CreditCard("9691-6349-3129-9350", createdAt, "Name", blockedSet, travelNoteSet, walletSet,
                installmentSet, 1, renegotiation, dueDate, proposal, CreditCardStatus.ATIVO);
        when(creditCardRepository.save(this.creditCard)).thenReturn(this.creditCard);
        creditCardRepository.save(creditCard);

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

    @Test
    public void testFindCardByIdWhenCardExists() throws Exception {
        when(creditCardRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(this.creditCard));
        MockMvcBuilders.webAppContextSetup(wContext).build();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cartoes/{id}", "9691-6349-3129-9350")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
