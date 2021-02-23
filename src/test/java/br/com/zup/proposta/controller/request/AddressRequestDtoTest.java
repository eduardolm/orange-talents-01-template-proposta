package br.com.zup.proposta.controller.request;

import br.com.zup.proposta.model.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressRequestDtoTest {
    @Test
    public void testToModel() {
        Address actualToModelResult = (new AddressRequestDto()).toModel();

        assertNull(actualToModelResult.getStreet());
        assertNull(actualToModelResult.getComplement());
        assertNull(actualToModelResult.getZip());
        assertNull(actualToModelResult.getNumber());
    }

    @Test
    public void testToString() {
        assertEquals("AddressRequestDto{street='null', zip='null', number='null', complement='null'}",
                (new AddressRequestDto()).toString());
    }

    @Test
    public void testEquals1() {
        AddressRequestDto addressRequestDto = new AddressRequestDto("Street", "21654", null, "Complement");

        assertNotEquals(new AddressRequestDto("Street", "21654", "Number", "Complement"), addressRequestDto);
    }

    @Test
    public void testEquals2() {
        AddressRequestDto addressRequestDto = new AddressRequestDto("Street", "21654", "Number", null);

        assertNotEquals(new AddressRequestDto("Street", "21654", "Number", "Complement"), addressRequestDto);
    }

    @Test
    public void testEquals3() {
        assertNotEquals((new AddressRequestDto()), "o");
    }

    @Test
    public void testEquals4() {
        AddressRequestDto addressRequestDto = new AddressRequestDto();

        assertEquals(new AddressRequestDto(), addressRequestDto);
    }

    @Test
    public void testEquals5() {
        AddressRequestDto addressRequestDto = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");
        AddressRequestDto addressRequestDto2 = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");
        AddressRequestDto addressRequestDto3 = new AddressRequestDto();
        AddressRequestDto addressRequestDto4 = new AddressRequestDto();

        assertEquals(addressRequestDto, addressRequestDto2);
        assertEquals(addressRequestDto3, addressRequestDto4);
        assertNotEquals(addressRequestDto, addressRequestDto3);
    }

    @Test
    public void testConstructor() {
        AddressRequestDto addressRequestDto = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");

        assertEquals("Rua Arthur de Azevedo", addressRequestDto.getStreet());
        assertEquals("05470-050", addressRequestDto.getZip());
        assertEquals("235", addressRequestDto.getNumber());
        assertEquals("Casa", addressRequestDto.getComplement());
    }

    @Test
    public void testHashCode() {
        AddressRequestDto addressRequestDto = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");
        AddressRequestDto addressRequestDto2 = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");

        assertEquals(addressRequestDto.hashCode(), addressRequestDto2.hashCode());
    }

    @Test
    public void testHashCode2() {
        // Arrange, Act and Assert
        assertEquals(0, (new AddressRequestDto()).hashCode());
    }

    @Test
    public void testHashCode3() {
        // Arrange, Act and Assert
        assertEquals(1393208880, (new AddressRequestDto("Street", "21654", "Number", "Complement")).hashCode());
    }
}

