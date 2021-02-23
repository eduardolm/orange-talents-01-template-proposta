package br.com.zup.proposta.controller.response;

import br.com.zup.proposta.model.DueDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DueDateResponseDtoTest {
    @Test
    public void testToModel() {
        DueDate actualToModelResult = (new DueDateResponseDto()).toModel();

        assertEquals(0, actualToModelResult.getDay());
        assertNull(actualToModelResult.getId());
        assertNull(actualToModelResult.getCreatedAt());
    }

    @Test
    public void testToModel2() {
        DueDate actualToModelResult = (new DueDateResponseDto("b2a77d4f-1e15-4287-b66c-552172705a27", 30,
                LocalDateTime.of(2020, 9, 25, 15, 25))).toModel();

        assertEquals(30, actualToModelResult.getDay());
        assertEquals("b2a77d4f-1e15-4287-b66c-552172705a27", actualToModelResult.getId());
        assertEquals(LocalDateTime.of(2020, 9, 25, 15, 25), actualToModelResult.getCreatedAt());
    }
}

