package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.PerishableParcel;
import static org.junit.jupiter.api.Assertions.*;

public class PerishableParcelTest {

    @Test
    public void testPerishableParcelExpired() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 1, "Москва", 1, 3);
        assertTrue(parcel.isExpired(5));
    }

    @Test
    public void testPerishableParcelNotExpired() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 1, "Москва", 1, 3);
        assertFalse(parcel.isExpired(3));
    }

    @Test
    public void testPerishableParcelExpiredSameDay() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 1, "Москва", 1, 3);
        assertFalse(parcel.isExpired(4));
    }
}