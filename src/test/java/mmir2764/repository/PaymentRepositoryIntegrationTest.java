package mmir2764.repository;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.repository.PaymentRepository;
import mmir2764.repository.Repository;
import mmir2764.service.PizzaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class PaymentRepositoryIntegrationTest {
    public PizzaService service;

    public Repository<Payment> repository;

    @Before
    public void setUp() {
        repository = new PaymentRepository();
        service = new PizzaService(null, repository);
    }

    @Test
    public void getAllPayments() {
        Payment p1 = mock(Payment.class);
        Payment p2 = mock(Payment.class);
        Payment p3 = mock(Payment.class);

        Arrays.asList(p1, p2, p3).forEach(repository::add);

        assertEquals(3, service.getPayments().size());
    }

    @Test
    public void addPayment() throws Exception {
        Payment p = mock(Payment.class);

        Mockito.when(p.getTableNumber()).thenReturn(1);
        Mockito.when(p.getType()).thenReturn(PaymentType.CASH);
        Mockito.when(p.getAmount()).thenReturn(10.2);

        assertEquals(0, service.getPayments().size());
        service.addPayment(p);
        assertEquals(1, service.getPayments().size());

        Mockito.verify(p, times(2)).getTableNumber();
        Mockito.verify(p, times(1)).getAmount();
    }
}
