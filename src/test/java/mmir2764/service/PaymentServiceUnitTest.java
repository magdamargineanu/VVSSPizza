package mmir2764.service;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.repository.PaymentRepository;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class PaymentServiceUnitTest {
    private PaymentRepository paymentRepository;

    private PizzaService service;

    @BeforeAll
    static void init() {
        BasicConfigurator.configure();
    }

    @BeforeEach
    public void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        service = new PizzaService(null, paymentRepository);
    }

    @Test
    public void getAllPayments() {
        Payment p1 = new Payment(5, PaymentType.CARD,26.8);
        Payment p2 = new Payment(7, PaymentType.CASH,126.8);
        Payment p3 = new Payment(8, PaymentType.CASH,226.8);

        Mockito.when(paymentRepository.getAll()).thenReturn(Arrays.asList(p1, p2, p3));

        assertEquals(3, service.getPayments().size());

        Mockito.verify(paymentRepository, times(1)).getAll();
    }

    @Test
    public void addValidPayment() throws Exception {
        Payment p = new Payment(5, PaymentType.CARD,26.8);
        Mockito.doNothing().when(paymentRepository).add(p);

        service.addPayment(p);

        Mockito.verify(paymentRepository,times(1)).add(p);
    }
}
