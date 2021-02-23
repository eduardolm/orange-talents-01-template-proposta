package br.com.zup.proposta.model;

import br.com.zup.proposta.builder.AddressRequestDtoBuilder;
import br.com.zup.proposta.controller.request.AddressRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AddressTest {

    @Test
    public void testToString() {
        assertEquals("Address{street='null', zip='null', number='null', complement='null'}", (new Address()).toString());
    }

    @Test
    public void shouldCreateAddress() {
        AddressRequestDto addressRequestDto = new AddressRequestDtoBuilder()
                .withStreet("Rua da Bica")
                .withZip("77823-300")
                .withNumber("254")
                .withComplement("Casa")
                .build();


        Address address = addressRequestDto.toModel();

        assertEquals("Rua da Bica", address.getStreet());
        assertEquals("77823-300", address.getZip());
        assertEquals("254", address.getNumber());
        assertEquals("Casa", address.getComplement());
    }

    @Test
    public void testEquals() {
        assertNotEquals((new Address()), "o");
    }

    @Test
    public void testEquals10() {
        Address address = new Address("Street", "21654", "Number", null);

        assertNotEquals(new Address("Street", "21654", "Number", "Complement"), address);
    }

    @Test
    public void testEquals2() {
        Address address = new Address();

        assertEquals(new Address(), address);
    }

    @Test
    public void testEquals3() {
        Address address = new Address();

        assertNotEquals(new Address("Street", "21654", "Number", "Complement"), address);
    }

    @Test
    public void testEquals4() {
        Address address = new Address("Street", "21654", "Number", "Complement");

        assertNotEquals(new Address(), address);
    }

    @Test
    public void testEquals5() {
        Address address = new Address("Street", "21654", "Number", "Complement");

        assertEquals(new Address("Street", "21654", "Number", "Complement"), address);
    }

    @Test
    public void testEquals6() {
        Address address = new Address();

        assertNotEquals(new Address(null, "21654", "Number", "Complement"), address);
    }

    @Test
    public void testEquals7() {
        Address address = new Address(null, "21654", "Number", "Complement");

        assertNotEquals(new Address(), address);
    }

    @Test
    public void testEquals8() {
        Address address = new Address("Street", "21654", "Street", "Complement");

        assertNotEquals(new Address("Street", "21654", "Number", "Complement"), address);
    }

    @Test
    public void testEquals9() {
        Address address = new Address("Street", "21654", null, "Complement");

        assertNotEquals(new Address("Street", "21654", "Number", "Complement"), address);
    }

    @Test
    public void testHashCode() {
        assertEquals(0, (new Address()).hashCode());
        assertEquals(1393208880, (new Address("Street", "21654", "Number", "Complement")).hashCode());
    }

    @Test
    public void shouldConstructorCreateAddress() {
        Address address = new AddressRequestDtoBuilder()
                .withStreet("Rua da Bica")
                .withZip("77823-300")
                .withNumber("254")
                .withComplement("Casa")
                .build().toModel();

        assertEquals("Rua da Bica", address.getStreet());
        assertEquals("77823-300", address.getZip());
        assertEquals("254", address.getNumber());
        assertEquals("Casa", address.getComplement());
    }
}
