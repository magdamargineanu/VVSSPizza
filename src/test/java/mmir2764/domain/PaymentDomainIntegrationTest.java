package mmir2764.domain;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.repository.PaymentRepository;
import mmir2764.service.PizzaService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentDomainIntegrationTest {
    public PizzaService service;

    public PaymentRepository repository;

    @Before
    public void setUp() {
        repository = new PaymentRepository();
        service = new PizzaService(null, repository);
    }

    @Test
    public void getAllPayments() {
        Payment p1 = new Payment(1, PaymentType.CARD, 12.3);
        Payment p2 = new Payment(2, PaymentType.CARD, 21.3);
        Payment p3 = new Payment(3, PaymentType.CASH, 31.3);

        repository.add(p1);
        repository.add(p2);
        repository.add(p3);

        assertEquals(3, service.getPayments().size());
    }

    @Test
    public void addPayment() throws Exception {
        Payment p = new Payment(1, PaymentType.CARD, 12.3);

        assertEquals(0, service.getPayments().size());
        service.addPayment(p);
        assertEquals(1, service.getPayments().size());
    }
}
