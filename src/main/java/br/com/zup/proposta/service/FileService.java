package br.com.zup.proposta.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.Objects;

@Service
public class FileService {

    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public byte[] getObjectFile(File file) throws FileNotFoundException {

        return readBytesFromFile(file);
    }

    public byte[] readBytesFromFile(File file) {

        FileInputStream fileInputStream = null;
        byte[] byteArray = null;

        try {
            byteArray = new byte[(int) file.length()];

            fileInputStream = new FileInputStream(file);
            fileInputStream.read(byteArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return byteArray;
    }

    public String convertToBase64(byte[] byteArray) {
        return Base64.getEncoder().encodeToString(byteArray);
    }
}
