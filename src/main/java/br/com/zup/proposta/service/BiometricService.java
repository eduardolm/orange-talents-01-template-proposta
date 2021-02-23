package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.BiometryImageRequestDto;
import br.com.zup.proposta.model.BiometryImage;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.repository.BiometryImageRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class BiometricService {

    @Autowired
    private BiometryImageRepository imageRepository;

    @Autowired
    private FileService fileService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BiometricService.class);

    public BiometryImage uploadImage(BiometryImageRequestDto requestDto, CreditCard creditCard) throws IOException, Exception {

        try {
            MultipartFile image = requestDto.getImage();
            File file = fileService.convertMultiPartToFile(image);

            if (preventRepeatedImages(file)) return null;

            // file -> byte[] -> base64 -> hash
            String imageFile = fileService.convertToBase64(fileService.readBytesFromFile(file));

            if (!Base64.isBase64(imageFile.getBytes())) {
                LOGGER.error("Formato do arquivo inválido.");
                throw  new IllegalArgumentException("Formato do arquivo inválido!");
            }
            BiometryImage biometryImage = new BiometryImage(creditCard, imageFile, file.getName());
            imageRepository.save(biometryImage);

            return biometryImage;
        }
        catch (IOException ex) {
            LOGGER.error("Erro ao gravar o arquivo no banco de dados.", ex);
        }
        catch (Exception ex1) {
            LOGGER.error("Erro desconhecido.", ex1);
        }
        return null;
    }

    public boolean preventRepeatedImages(File file) {
        Optional<BiometryImage> image = imageRepository.findByOriginalFileName(file.getName());

        if (image.isPresent()) {
            String originalName = image.get().getOriginalFileName();
            return originalName.equals(file.getName());
        }
        return false;
    }
}
