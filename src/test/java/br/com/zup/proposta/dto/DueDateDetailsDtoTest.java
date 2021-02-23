package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.DueDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DueDateDetailsDtoTest {
    @Test
    public void testConstructor() {
        DueDateDetailsDto actualDueDateDetailsDto = new DueDateDetailsDto(new DueDate());

        assertNull(actualDueDateDetailsDto.getDataDeCriacao());
        assertEquals(0, actualDueDateDetailsDto.getDia());
        assertNull(actualDueDateDetailsDto.getId());
    }

    @Test
    public void testConstructor2() {
        DueDateDetailsDto actualDueDateDetailsDto = new DueDateDetailsDto(
                new DueDate("Id", 1, LocalDateTime.of(1, 1, 1, 1, 1)));

        assertEquals(1, actualDueDateDetailsDto.getDia());
        assertEquals("Id", actualDueDateDetailsDto.getId());
    }
}

