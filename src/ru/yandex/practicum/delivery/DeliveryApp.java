package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final List<Trackable> trackableParcels = new ArrayList<>();

    // Коробки для разных типов посылок (инициализируем как null, вес зададим при первой посылке)
    private static ParcelBox<StandardParcel> standardBox;
    private static ParcelBox<FragileParcel> fragileBox;
    private static ParcelBox<PerishableParcel> perishableBox;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addParcel();
                        break;
                    case 2:
                        sendParcels();
                        break;
                    case 3:
                        calculateCosts();
                        break;
                    case 4:
                        reportStatus();
                        break;
                    case 5:
                        showBoxContents();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Неверный выбор.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число от 0 до 5");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отчет о статусе отслеживаемых посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        try {
            System.out.println("Выберите тип посылки:");
            System.out.println("1 — Стандартная");
            System.out.println("2 — Хрупкая");
            System.out.println("3 — Скоропортящаяся");

            int type = Integer.parseInt(scanner.nextLine());

            System.out.println("Введите описание посылки:");
            String description = scanner.nextLine();

            System.out.println("Введите вес посылки:");
            int weight = Integer.parseInt(scanner.nextLine());
            if (weight <= 0) {
                System.out.println("Ошибка: вес должен быть положительным числом");
                return;
            }

            System.out.println("Введите адрес доставки:");
            String address = scanner.nextLine();

            System.out.println("Введите день отправки (1-31):");
            int sendDay = Integer.parseInt(scanner.nextLine());
            if (sendDay < 1 || sendDay > 31) {
                System.out.println("Ошибка: день должен быть от 1 до 31");
                return;
            }

            Parcel parcel;

            switch (type) {
                case 1:
                    // Создаем коробку для стандартных посылок при первой посылке
                    if (standardBox == null) {
                        System.out.println("Введите максимальный вес коробки для стандартных посылок:");
                        int maxWeight = Integer.parseInt(scanner.nextLine());
                        if (maxWeight <= 0) {
                            System.out.println("Ошибка: максимальный вес должен быть положительным числом");
                            return;
                        }
                        standardBox = new ParcelBox<>(maxWeight);
                    }

                    parcel = new StandardParcel(description, weight, address, sendDay);
                    if (standardBox.addParcel((StandardParcel) parcel)) {
                        allParcels.add(parcel);
                        System.out.println("Стандартная посылка добавлена!");
                    }
                    break;
                case 2:
                    // Создаем коробку для хрупких посылок при первой посылке
                    if (fragileBox == null) {
                        System.out.println("Введите максимальный вес коробки для хрупких посылок:");
                        int maxWeight = Integer.parseInt(scanner.nextLine());
                        if (maxWeight <= 0) {
                            System.out.println("Ошибка: максимальный вес должен быть положительным числом");
                            return;
                        }
                        fragileBox = new ParcelBox<>(maxWeight);
                    }

                    parcel = new FragileParcel(description, weight, address, sendDay);
                    if (fragileBox.addParcel((FragileParcel) parcel)) {
                        allParcels.add(parcel);
                        trackableParcels.add((Trackable) parcel);
                        System.out.println("Хрупкая посылка добавлена!");
                    }
                    break;
                case 3:
                    // Создаем коробку для скоропортящихся посылок при первой посылке
                    if (perishableBox == null) {
                        System.out.println("Введите максимальный вес коробки для скоропортящихся посылок:");
                        int maxWeight = Integer.parseInt(scanner.nextLine());
                        if (maxWeight <= 0) {
                            System.out.println("Ошибка: максимальный вес должен быть положительным числом");
                            return;
                        }
                        perishableBox = new ParcelBox<>(maxWeight);
                    }

                    System.out.println("Введите срок годности (в днях):");
                    int timeToLive = Integer.parseInt(scanner.nextLine());
                    if (timeToLive <= 0) {
                        System.out.println("Ошибка: срок годности должен быть положительным числом");
                        return;
                    }
                    parcel = new PerishableParcel(description, weight, address, sendDay, timeToLive);
                    if (perishableBox.addParcel((PerishableParcel) parcel)) {
                        allParcels.add(parcel);
                        System.out.println("Скоропортящаяся посылка добавлена!");
                    }
                    break;
                default:
                    System.out.println("Неверный тип посылки.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число");
        }
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }

        System.out.println("Отправка посылок...");

        // Получаем текущий день месяца
        int currentDay = java.time.LocalDate.now().getDayOfMonth();

        for (Parcel parcel : allParcels) {
            parcel.packageItem();

            // Проверяем, не испортилась ли скоропортящаяся посылка
            if (parcel instanceof PerishableParcel perishable) {
                // Используем текущий день месяца для проверки - ПЕРЕДАЕМ ПАРАМЕТР
                if (perishable.isExpired(currentDay)) {
                    System.out.println("Посылка <<" + perishable.getDescription() + ">> доставлена по адресу " + perishable.getDeliveryAddress());
                    System.out.println("❌ ВНИМАНИЕ: Посылка <<" + perishable.getDescription() + ">> ИСПОРТИЛАСЬ во время доставки!");
                    continue;
                }
            }

            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для расчета стоимости.");
            return;
        }

        int totalCost = 0;
        for (Parcel parcel : allParcels) {
            totalCost += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставки всех посылок: " + totalCost);
    }

    private static void reportStatus() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет отслеживаемых посылок.");
            return;
        }

        System.out.println("Введите новое местоположение:");
        String location = scanner.nextLine();

        System.out.println("Отчет о статусе посылок:");
        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(location);
        }
    }

    private static void showBoxContents() {
        System.out.println("Выберите тип коробки:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    if (standardBox == null || standardBox.getAllParcels().isEmpty()) {
                        System.out.println("Коробка для стандартных посылок пуста или не создана");
                    } else {
                        System.out.println("Стандартные посылки (макс. вес: " + standardBox.getMaxWeight() + " кг, текущий вес: " + standardBox.getCurrentWeight() + " кг):");
                        for (StandardParcel parcel : standardBox.getAllParcels()) {
                            System.out.println("- " + parcel.getDescription() + " (" + parcel.getWeight() + " кг)");
                        }
                    }
                    break;
                case 2:
                    if (fragileBox == null || fragileBox.getAllParcels().isEmpty()) {
                        System.out.println("Коробка для хрупких посылок пуста или не создана");
                    } else {
                        System.out.println("Хрупкие посылки (макс. вес: " + fragileBox.getMaxWeight() + " кг, текущий вес: " + fragileBox.getCurrentWeight() + " кг):");
                        for (FragileParcel parcel : fragileBox.getAllParcels()) {
                            System.out.println("- " + parcel.getDescription() + " (" + parcel.getWeight() + " кг)");
                        }
                    }
                    break;
                case 3:
                    if (perishableBox == null || perishableBox.getAllParcels().isEmpty()) {
                        System.out.println("Коробка для скоропортящихся посылок пуста или не создана");
                    } else {
                        System.out.println("Скоропортящиеся посылки (макс. вес: " + perishableBox.getMaxWeight() + " кг, текущий вес: " + perishableBox.getCurrentWeight() + " кг):");
                        for (PerishableParcel parcel : perishableBox.getAllParcels()) {
                            System.out.println("- " + parcel.getDescription() + " (" + parcel.getWeight() + " кг), срок: " + parcel.getTimeToLive() + " дней");
                        }
                    }
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите число от 1 до 3");
        }
    }
}