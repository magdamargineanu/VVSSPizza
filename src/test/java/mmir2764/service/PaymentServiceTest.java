package mmir2764.service;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.repository.Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentServiceTest {

    @Mock
    private Repository<Payment> paymentRepository;

    @InjectMocks
    private PizzaService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addValidPaymentTest() throws Exception {

//        Payment payment2 =  new Payment(5, PaymentType.CARD,226.8);
//
//        Mockito.doNothing().when(paymentRepository).add(payment2);
//
//
//        service.addPayment(5,PaymentType.CARD,26.8);
//
//        Mockito.verify(paymentRepository,times(1)).add(payment2);


    }

    @Test
    public void addInvalidPaymentTest() throws Exception {

        assertThrows(Exception.class, () -> service.addPayment(5, PaymentType.CARD, -26.8));

    }
}
