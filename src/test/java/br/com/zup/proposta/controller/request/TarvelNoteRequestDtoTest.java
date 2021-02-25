package br.com.zup.proposta.controller.request;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class TarvelNoteRequestDtoTest {

    @Test
    public void testTravelNoteRequestDtoConstructor() {
        TravelNoteRequestDto newTravel = new TravelNoteRequestDto("Madrid", LocalDate.MAX);

        assertEquals("Madrid", newTravel.getDestino());
        assertEquals(LocalDate.MAX, newTravel.getValidoAte());
    }
}
