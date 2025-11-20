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
    public void testZeroWeightParcelCost() {
        StandardParcel parcel = new StandardParcel("Письмо", 0, "Москва", 1);
        assertEquals(0, parcel.calculateDeliveryCost());
    }

    @Test
    public void testSingleWeightParcelCost() {
        StandardParcel parcel = new StandardParcel("Документы", 1, "Москва", 1);
        assertEquals(2, parcel.calculateDeliveryCost());
    }
}