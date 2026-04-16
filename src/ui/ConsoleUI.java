package ui;

import exception.*;
import model.Medicine;
import model.PrescriptionType;
import model.Sale;
import service.PharmacyService;
import service.PharmacyServiceInterface;
import service.strategy.*;

import java.time.LocalDate; //дата без времени 2026-03-01
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private PharmacyServiceInterface service;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleUI(PharmacyServiceInterface service) { this.service = service; }

    public void start() {
        while (true) {
            System.out.println("\n  АПТЕКА");
            System.out.println("1. Просмотреть список лекарств");
            System.out.println("2. Добавить лекарство");
            System.out.println("3. Удалить лекарство");
            System.out.println("4. Продать лекарство");
            System.out.println("5. Просмотреть журнал продаж");
            System.out.println("6. Отчёт по продажам ");
            System.out.println("7. Отчёт по просрочке");
            System.out.println("0. Выход");

            System.out.print("Введите число: ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> viewMedicines();
                    case 2 -> addMedicine();
                    case 3 -> deleteMedicine();
                    case 4 -> sellMedicine();
                    case 5 -> viewSales();
                    case 6 -> service.printSalesReport();
                    case 7 -> service.printExpiredReport();
                    case 0 -> { System.out.println("До свидания)"); return; }
                    default -> System.out.println("Неверный пункт меню(");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число");
            } catch (PharmacyException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
    }

    private void viewMedicines() {
        System.out.println("\n  СПИСОК ЛЕКАРСТВ:");
        List<Medicine> medicines = service.getAllMedicines();
        if (medicines.isEmpty()) System.out.println("Список пуст");
        else medicines.forEach(System.out::println);
    }

    private void addMedicine() {
        System.out.println("\n  ДОБАВЛЕНИЕ ЛЕКАРСТВА");

        System.out.print("Название: ");
        String name = scanner.nextLine();

        PrescriptionType prescriptionType = readPrescriptionType("Требуется рецепт? (да/нет): ");
        int quantity = readInt("Количество: ");
        double price = readDouble("Цена (в рублях): ");
        LocalDate expirationDate = readDate("Годен до (дд.мм.гггг): ");

        service.addMedicine(new Medicine(name, prescriptionType, expirationDate, quantity, price));
        System.out.println("Операция выполнена успешно!");
    }

    private void deleteMedicine() {
        viewMedicines();
        System.out.print("Введите ID лекарства для удаления: ");
        String id = scanner.nextLine();
        service.deleteMedicine(id);
        System.out.println("Операция выполнена успешно!");
    }

    private void sellMedicine() {
        viewMedicines();
        System.out.print("Введите ID лекарства: ");
        String id = scanner.nextLine();
        int quantity = readInt("Введите количество: ");
        boolean hasPrescription = readYesNo("Есть рецепт? (да/нет): ");
        PricingStrategy strategy = chooseStrategy();
        service.sellMedicine(id, quantity, hasPrescription, strategy);
        System.out.println("Операция выполнена успешно!");
    }

    private PricingStrategy chooseStrategy() {
        while (true) {
            System.out.println("Выбор скидки: 1 - Пенсионная (25%), 2 - Для многодетных семей (10%), 3 - Без скидки ");
            int choice = readInt("Ваш выбор: ");
            switch (choice) {
                case 1:
                    System.out.println("Применена пенсионная скидка 25%");
                    return new PensionerDiscountStrategy();
                case 2:
                    System.out.println("Применена скидка для многодетных 10%");
                    return new LargeFamilyDiscountStrategy();
                case 3:
                    System.out.println("Скидка не применена");
                    return new NoDiscountStrategy();
                default:
                    System.out.println("Неверный выбор, введите 1,2 или 3");
            }
        }
    }

    private void viewSales() {
        System.out.println("\n  ЖУРНАЛ ПРОДАЖ:");
        List<Sale> sales = service.getSales();
        if (sales.isEmpty()) System.out.println("Список пуст");
        else sales.forEach(System.out::println);
    }



    private boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("да")) return true;
            if (input.equals("нет")) return false;
            System.out.println("Введите 'да' или 'нет'");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value <= 0) {
                    System.out.println("Цена должна быть положительной!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Введите число (например: 150.50)!");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Введите дату в формате дд.мм.гггг");
            }
        }
    }

    private PrescriptionType readPrescriptionType(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("да")) {
                return PrescriptionType.PRESCRIPTION;
            } else if (input.equals("нет")) {
                return PrescriptionType.WITHOUTPRESCRIPTION;
            }
            System.out.println("Введите 'да' или 'нет'");
        }
    }
}