package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelBoxTest {

    @Test
    public void testParcelBoxAddSuccess() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel parcel = new StandardParcel("Книга", 5, "Москва", 1);

        box.addParcel(parcel);
        assertEquals(1, box.getAllParcels().size());
        assertEquals(5, box.getCurrentWeight());
    }

    @Test
    public void testParcelBoxAddFail() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5);
        StandardParcel parcel = new StandardParcel("Книга", 6, "Москва", 1);

        box.addParcel(parcel);
        assertEquals(0, box.getAllParcels().size());
        assertEquals(0, box.getCurrentWeight());
    }

    @Test
    public void testParcelBoxAddExactWeight() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel parcel = new StandardParcel("Книга", 10, "Москва", 1);

        box.addParcel(parcel);
        assertEquals(1, box.getAllParcels().size());
        assertEquals(10, box.getCurrentWeight());
    }

    @Test
    public void testFragileParcelTracking() {
        FragileParcel parcel = new FragileParcel("Ваза", 2, "Москва", 1);
        assertInstanceOf(Trackable.class, parcel);
    }
}