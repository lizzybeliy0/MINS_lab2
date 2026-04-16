package observer;

import model.Medicine;

public class ExpirationAlertObserver implements Observer {
    @Override
    public void update(Medicine medicine) {
        System.out.println("ВНИМАНИЕ: Просрочено -> " + medicine.getName());
    }
}