package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.BiometryImageRequestDto;
import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.model.*;
import br.com.zup.proposta.repository.BiometryImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BiometricServiceTest {

    @Autowired
    private FileService fileService;

    @Autowired
    private BiometricService biometricService;

    @MockBean
    private BiometryImageRepository biometryImageRepository;

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

        this.creditCard = new CreditCard("42", createdAt, "Name", blockedSet, travelNoteSet, walletSet,
                installmentSet, 1, renegotiation, dueDate, proposal, CreditCardStatus.ATIVO);

        // Create BiometricImage
        String fileName = "src/test/resources/test-data/img/CPF.jpg";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        MultipartFile mockMultipartFile = new MockMultipartFile("CPF.jpg",
                "CPF.jpg",
                "image/jpg",
                inputStream);

        BiometryImageRequestDto biometryImageRequestDto = new BiometryImageRequestDto(mockMultipartFile);
        this.biometryImageRequestDto = biometryImageRequestDto;
        File file = fileService.convertMultiPartToFile(mockMultipartFile);
        String imageFile = fileService.convertToBase64(fileService.readBytesFromFile(file));

        this.biometryImage = new BiometryImage(this.creditCard, imageFile, file.getName());
    }

    @Test
    public void testPreventRepeatedImagesWithNonRepeatingImages() throws IOException {
        String fileName = "src/test/resources/test-data/img/B1B-50x.jpg";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        MultipartFile mockMultipartFile = new MockMultipartFile("B1B-50x.jpg",
                "B1B-50x.jpg",
                "image/jpg",
                inputStream);
        File file = fileService.convertMultiPartToFile(mockMultipartFile);

        when(biometryImageRepository.findByOriginalFileName("CPF.jpg"))
                .thenReturn(java.util.Optional.ofNullable(this.biometryImage));

        assertFalse(biometricService.preventRepeatedImages(file, this.creditCard));
    }

    @Test
    public void testUploadImage() throws Exception {
        var actual = biometricService.uploadImage(this.biometryImageRequestDto, this.creditCard);

        assertEquals(actual.getImageFile(), fileService
                .convertToBase64(fileService
                        .readBytesFromFile(fileService.convertMultiPartToFile(this.biometryImageRequestDto.getImage()))));
    }
}
