/*package factory;

import model.Medicine;
import model.Sale;

public class SaleFactory {

    public static Sale create(Medicine medicine, int quantity,
                              double unitPrice, double totalPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Цена за единицу должна быть положительной");
        }

        // todo Округляем до копеек
        double roundedUnitPrice = Math.round(unitPrice * 100.0) / 100.0;
        double roundedTotal = Math.round(totalPrice * 100.0) / 100.0;

        return new Sale(medicine, quantity, roundedUnitPrice, roundedTotal);
    }
}*/