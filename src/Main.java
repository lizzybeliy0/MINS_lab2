import model.Medicine;
import model.Sale;
import observer.*;
import report.ConsoleReportFactory;
import report.ReportFactory;
import repository.DataInitializer;
import repository.MedicineRepository;
import repository.Repository;
import repository.SaleRepository;
import service.PharmacyService;
import service.PharmacyServiceInterface;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {

        Repository<Medicine, String> medicineRepo = new MedicineRepository();
        Repository<Sale, String> saleRepo = new SaleRepository();

        DataInitializer.initMedicineRepository(medicineRepo);

        ReportFactory reportFactory = new ConsoleReportFactory();
        PharmacyServiceInterface service = new PharmacyService(medicineRepo, saleRepo, reportFactory);
        service.addObserver(new AddedObserver());
        service.addObserver(new RemovedObserver());
        service.addObserver(new ExpiredObserver());
        service.addObserver(new SoldObserver());
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}