package ru.yandex.practicum.delivery;

// Интерфейс для отслеживания
public interface Trackable {
    void reportStatus(String newLocation);
}