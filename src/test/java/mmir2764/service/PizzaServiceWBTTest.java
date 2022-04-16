package mmir2764.service;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.repository.PaymentRepository;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PizzaServiceWBTTest {
    private PaymentRepository repository;
    private PizzaService service;

    @BeforeAll
    static void init() {
        BasicConfigurator.configure();
    }

    @BeforeEach
    void setUp() {
        // arrange
        repository = new PaymentRepository();
        service = new PizzaService(null, repository);
    }

    @Test
    void invalidPaymentTypeTest() {
        try {
            service.getTotalAmount(null);
            assert false;
        } catch (Exception e) {
            assert true;
        }
//            assertThrows(Exception.class, () -> service.getTotalAmount(null));
    }

    @Test
    void emptyListTest() throws Exception {
        assertEquals(0, service.getTotalAmount(PaymentType.CASH));
    }

    @Test
    void cashTypeTest1() throws Exception {
        service.addPayment(new Payment(2, PaymentType.CASH, 12.7));
        service.addPayment(new Payment(1, PaymentType.CASH, 22.7));
        assertEquals(35.4, service.getTotalAmount(PaymentType.CASH));
    }

    @Test
    void cashTypeTest2() throws Exception {
        service.addPayment(new Payment(2, PaymentType.CARD, 12.7));
        service.addPayment(new Payment(1, PaymentType.CARD, 10.7));
        assertEquals(0, service.getTotalAmount(PaymentType.CASH));
    }

    @Test
    void allTypes() throws Exception {
        service.addPayment(new Payment(2, PaymentType.CASH, 12.7));
        service.addPayment(new Payment(1, PaymentType.CASH, 22.7));
        service.addPayment(new Payment(2, PaymentType.CARD, 12.7));
        service.addPayment(new Payment(1, PaymentType.CARD, 10.7));
        assertEquals(23.4, service.getTotalAmount(PaymentType.CARD));
    }
}
