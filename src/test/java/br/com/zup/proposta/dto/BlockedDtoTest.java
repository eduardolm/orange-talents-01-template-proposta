package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.Blocked;
import br.com.zup.proposta.model.CreditCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockedDtoTest {
    @Test
    public void testConstructor() {
        // Arrange and Act
        BlockedDto actualBlockedDto = new BlockedDto(new Blocked());

        // Assert
        assertNull(actualBlockedDto.getBlockedAt());
        assertNull(actualBlockedDto.getId());
        assertFalse(actualBlockedDto.isActive());
        assertNull(actualBlockedDto.getResponsibleSystem());
    }

    @Test
    public void testConstructor2() {
        // Arrange and Act
        BlockedDto actualBlockedDto = new BlockedDto(new Blocked("Responsible System", true, new CreditCard()));

        // Assert
        assertNull(actualBlockedDto.getId());
        assertEquals("Responsible System", actualBlockedDto.getResponsibleSystem());
        assertTrue(actualBlockedDto.isActive());
    }

    @Test
    public void testConstructor3() {
        // Arrange and Act
        BlockedDto actualBlockedDto = new BlockedDto(new Blocked("", true, new CreditCard()));

        // Assert
        assertNull(actualBlockedDto.getId());
        assertEquals("", actualBlockedDto.getResponsibleSystem());
        assertTrue(actualBlockedDto.isActive());
    }
}

