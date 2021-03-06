package mmir2764.service;

import mmir2764.model.MenuDataModel;
import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.model.PaymentValidator;
import mmir2764.repository.MenuRepository;
import mmir2764.repository.PaymentRepository;
import mmir2764.repository.Repository;

import java.util.List;

public class PizzaService {

    private final MenuRepository menuRepo;
    private final Repository<Payment> payRepo;
    private final PaymentValidator validator = new PaymentValidator();

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo) {
        this.menuRepo = menuRepo;
        this.payRepo = payRepo;
    }

    public List<MenuDataModel> getMenuData() {
        return menuRepo.getAll();
    }

    public List<Payment> getPayments() {
        return payRepo.getAll();
    }

    public void addPayment(Payment payment) throws Exception {
        validator.validate(payment);
        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type) throws Exception {
        double total = 0.0f;
        if (type == null)
            throw new Exception("Payment type cannot be null!");
        List<Payment> paymentList = getPayments();
        if ((paymentList == null) || (paymentList.isEmpty()))
            return total;
        for (Payment payment : paymentList) {
            if (payment.getType().equals(type))
                total += payment.getAmount();
        }
        return total;
    }
}