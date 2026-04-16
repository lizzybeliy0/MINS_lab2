/*package factory;

import model.Medicine;
import model.PrescriptionType;
import java.time.LocalDate;

public class MedicineFactory {

    public static Medicine create(String name, PrescriptionType type,
                                  LocalDate expirationDate, int quantity, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Цена должна быть положительной");
        }

        return new Medicine(name, type, expirationDate, quantity, price);
    }
}*/