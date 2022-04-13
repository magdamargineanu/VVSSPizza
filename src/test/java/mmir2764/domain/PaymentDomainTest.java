package mmir2764.domain;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PaymentDomainTest {
    @Test
    public void createPaymentTest() {
        Payment payment = new Payment(12, PaymentType.CARD, 26.8);
        assertEquals(payment.getTableNumber(), 12);
        assertEquals(payment.getType(), PaymentType.CARD);
        assertEquals(payment.getAmount(), 26.8);
        assertNotEquals(payment.getAmount(), 6545);

    }

    @Test
    public void createAndChangePaymentTest() {
        Payment payment = new Payment(12, PaymentType.CARD, 26.8);

        payment.setTableNumber(7);
        assertEquals(payment.getTableNumber(), 7);

        payment.setType(PaymentType.CASH);
        assertEquals(payment.getType(), PaymentType.CASH);

        payment.setAmount(654);
        assertEquals(payment.getAmount(), 654);
    }
}
