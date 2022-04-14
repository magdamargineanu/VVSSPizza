package mmir2764.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import org.apache.log4j.Logger;

import java.util.Optional;

public class PaymentAlert{
    private final Logger logger = Logger.getLogger(this.getClass());

    private final PizzaService service;

    public PaymentAlert(PizzaService service){
        this.service=service;
    }


    public void cardPayment() {
        logger.info("--------------------------");
        logger.info("Paying by card...");
        logger.info("Please insert your card!");
        logger.info("--------------------------");
    }

    public void cashPayment() {
        logger.info("--------------------------");
        logger.info("Paying cash...");
        logger.info("Please show the cash...!");
        logger.info("--------------------------");
    }

    public void cancelPayment() {
        logger.info("--------------------------");
        logger.info("Payment choice needed...");
        logger.info("--------------------------");
    }
      public void payOrderOrCancel(int tableNumber, double totalAmount , Optional<ButtonType> result) {

        if (result.isPresent()) {
            try {
                if (result.get().getText().equals("Pay by Card")) {
                    cardPayment();
                    service.addPayment(new Payment(tableNumber, PaymentType.CARD, totalAmount));
                } else if (result.get().getText().equals("Pay Cash")) {
                    cashPayment();
                    service.addPayment(new Payment(tableNumber, PaymentType.CASH, totalAmount));
                } else if (result.get().getText().equals("Cancel")) {
                    cancelPayment();
                } else {
                    cancelPayment();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}