package ru.yandex.practicum.delivery;

// Скоропортящаяся посылка
public class PerishableParcel extends Parcel {
    private static final int BASE_COST = 3;
    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay, BASE_COST);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay) {
        return (sendDay + timeToLive) < currentDay;
    }

    public int getTimeToLive() {
        return timeToLive;
    }
}