package br.com.zup.proposta.controller.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TarvelNoteRequestDtoTest {

    @Test
    public void testTravelNoteRequestDtoConstructor() {
        TravelNoteRequestDto newTravel = new TravelNoteRequestDto("Madrid", LocalDate.MAX);

        assertEquals("Madrid", newTravel.getDestino());
        assertEquals(LocalDate.MAX, newTravel.getValidoAte());
    }
}
