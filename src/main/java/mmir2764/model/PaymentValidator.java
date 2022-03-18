package mmir2764.model;

public class PaymentValidator implements Validator<Payment> {
    @Override
    public void validate(Payment payment) throws Exception {
        String message = "";
        if (payment.getTableNumber() < 1 || payment.getTableNumber() > 8)
            message += "Index out of bounds\n";
        if (payment.getAmount() <= 0)
            message += "Invalid amount\n";
        if (!message.isEmpty())
            throw new Exception(message);
    }
}
