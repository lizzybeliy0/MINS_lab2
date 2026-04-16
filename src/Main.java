import model.Medicine;
import model.PrescriptionType;
import model.Sale;
import observer.ExpirationAlertObserver;
import report.ConsoleReportFactory;
import report.ReportFactory;
import repository.DataInitializer;
import repository.MedicineRepository;
import repository.Repository;
import repository.SaleRepository;
import service.PharmacyService;
import service.PharmacyServiceInterface;
import ui.ConsoleUI;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Repository<Medicine, String> medicineRepo = new MedicineRepository();
        Repository<Sale, String> saleRepo = new SaleRepository();

        DataInitializer.initMedicineRepository(medicineRepo);

        ReportFactory reportFactory = new ConsoleReportFactory();
        PharmacyServiceInterface service = new PharmacyService(medicineRepo, saleRepo, reportFactory);
        service.addObserver(new ExpirationAlertObserver());
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}