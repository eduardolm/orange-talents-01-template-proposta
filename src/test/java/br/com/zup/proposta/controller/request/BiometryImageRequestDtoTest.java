package br.com.zup.proposta.controller.request;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertSame;

public class BiometryImageRequestDtoTest {
    @Test
    public void testSetImages() {
        BiometryImageRequestDto biometryImageRequestDto = new BiometryImageRequestDto(new MockMultipartFile("Name",
                new byte[]{65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65}));
        MockMultipartFile mockMultipartFile = new MockMultipartFile("Name",
                new byte[]{65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65});

        biometryImageRequestDto.setImages(mockMultipartFile);

        assertSame(mockMultipartFile, biometryImageRequestDto.getImage());
    }
}

