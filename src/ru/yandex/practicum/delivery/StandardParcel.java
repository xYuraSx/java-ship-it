package ru.yandex.practicum.delivery;

// Стандартная посылка
public class StandardParcel extends Parcel {
    private static final int BASE_COST = 2;

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay, BASE_COST);
    }
}