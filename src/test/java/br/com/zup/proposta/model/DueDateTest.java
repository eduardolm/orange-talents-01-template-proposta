package br.com.zup.proposta.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DueDateTest {
    @Test
    public void testToString() {
        assertEquals("Vencimento{id='null', dia=0, dataDeCriacao=null}", (new DueDate()).toString());
    }

    @Test
    public void testConstructor() {
        DueDate actualToModelResult = (new DueDate("b2a77d4f-1e15-4287-b66c-552172705a27", 30,
                LocalDateTime.of(2020, 9, 25, 15, 25)));

        assertEquals(30, actualToModelResult.getDay());
        assertEquals("b2a77d4f-1e15-4287-b66c-552172705a27", actualToModelResult.getId());
        assertEquals(LocalDateTime.of(2020, 9, 25, 15, 25), actualToModelResult.getCreatedAt());
    }
}

