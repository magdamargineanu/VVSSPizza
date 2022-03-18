package mmir2764.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
      public void showPaymentAlert(int tableNumber, double totalAmount ) {
        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        paymentAlert.setTitle("Payment for Table "+tableNumber);
        paymentAlert.setHeaderText("Total amount: " + totalAmount);
        paymentAlert.setContentText("Please choose payment option");
        ButtonType cardPayment = new ButtonType("Pay by Card");
        ButtonType cashPayment = new ButtonType("Pay Cash");
        ButtonType cancel = new ButtonType("Cancel");
        paymentAlert.getButtonTypes().setAll(cardPayment, cashPayment, cancel);
        Optional<ButtonType> result = paymentAlert.showAndWait();
        if (result.isPresent()) {
            try {
                if (result.get() == cardPayment) {
                    cardPayment();
                    service.addPayment(tableNumber, PaymentType.CARD, totalAmount);
                } else if (result.get() == cashPayment) {
                    cashPayment();
                    service.addPayment(tableNumber, PaymentType.CASH, totalAmount);
                } else if (result.get() == cancel) {
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