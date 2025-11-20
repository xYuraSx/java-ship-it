package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

// Параметризованный класс коробки
public class ParcelBox<T extends Parcel> {
    private final List<T> parcels = new ArrayList<>();
    private final int maxWeight;
    private int currentWeight = 0;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        if (currentWeight + parcel.getWeight() <= maxWeight) {
            parcels.add(parcel);
            currentWeight += parcel.getWeight();
            return true;
        } else {
            System.out.println("Предупреждение: превышен максимальный вес коробки. Посылка не добавлена.");
            return false;
        }
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }

    public int getCurrentWeight() { return currentWeight; }
    public int getMaxWeight() { return maxWeight; }
}