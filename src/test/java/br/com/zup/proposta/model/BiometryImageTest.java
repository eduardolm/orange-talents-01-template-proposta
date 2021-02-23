package br.com.zup.proposta.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BiometryImageTest {
    @Test
    public void testConstructor() {
        CreditCard creditCard = new CreditCard();

        BiometryImage actualBiometryImage = new BiometryImage(creditCard, "Image File", "foo.txt");

        assertSame(creditCard, actualBiometryImage.getCreditCard());
        assertEquals("Image File", actualBiometryImage.getImageFile());
        assertEquals("foo.txt", actualBiometryImage.getOriginalFileName());
    }

    @Test
    public void testEquals() {
        assertNotEquals((new BiometryImage()), "o");
    }

    @Test
    public void testEquals2() {
        BiometryImage biometryImage = new BiometryImage();

        assertEquals(new BiometryImage(), biometryImage);
    }

    @Test
    public void testEquals3() {
        BiometryImage biometryImage = new BiometryImage();

        assertNotEquals(new BiometryImage(new CreditCard(), "Image File", "foo.txt"), biometryImage);
    }

    @Test
    public void testHashCode() {
        assertEquals(0, (new BiometryImage()).hashCode());
    }

}

