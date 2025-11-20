package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCostTest {

    @Test
    public void testStandardParcelCost() {
        StandardParcel parcel = new StandardParcel("Книги", 5, "Москва", 1);
        assertEquals(10, parcel.calculateDeliveryCost());
    }

    @Test
    public void testFragileParcelCost() {
        FragileParcel parcel = new FragileParcel("Ваза", 3, "СПб", 1);
        assertEquals(12, parcel.calculateDeliveryCost());
    }

    @Test
    public void testPerishableParcelCost() {
        PerishableParcel parcel = new PerishableParcel("Торт", 2, "Казань", 1, 3);
        assertEquals(6, parcel.calculateDeliveryCost());
    }

    @Test
    public void testPerishableParcelExpired() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 1, "Москва", 1, 3);
        // День отправки 1 + срок годности 3 = 4, текущий день 5 -> испорчено
        assertTrue(parcel.isExpired(5)); // Передаем currentDay = 5
    }

    @Test
    public void testParcelBoxAddSuccess() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel parcel = new StandardParcel("Книга", 5, "Москва", 1);

        assertTrue(box.addParcel(parcel));
        assertEquals(1, box.getAllParcels().size());
    }

    @Test
    public void testParcelBoxAddFail() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5);
        StandardParcel parcel = new StandardParcel("Книга", 6, "Москва", 1);

        assertFalse(box.addParcel(parcel));
        assertEquals(0, box.getAllParcels().size());
    }

    @Test
    public void testFragileParcelTracking() {
        FragileParcel parcel = new FragileParcel("Ваза", 2, "Москва", 1);
        assertInstanceOf(Trackable.class, parcel);
    }
}