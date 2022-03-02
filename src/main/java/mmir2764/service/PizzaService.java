package mmir2764.service;

import mmir2764.model.MenuDataModel;
import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.repository.MenuRepository;
import mmir2764.repository.PaymentRepository;

import java.util.List;

public class PizzaService {

    private final MenuRepository menuRepo;
    private final PaymentRepository payRepo;

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo){
        this.menuRepo=menuRepo;
        this.payRepo=payRepo;
    }

    public List<MenuDataModel> getMenuData(){return menuRepo.getMenu();}

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public void addPayment(int table, PaymentType type, double amount){
        Payment payment= new Payment(table, type, amount);
        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type){
        double total=0.0f;
        List<Payment> paymentList=getPayments();
        if ((paymentList==null) ||(paymentList.isEmpty())) return total;
        for (Payment payment:paymentList){
            if (payment.getType().equals(type))
                total+=payment.getAmount();
        }
        return total;
    }

}