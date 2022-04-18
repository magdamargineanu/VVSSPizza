package mmir2764.repository;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentRepositoryUnitTest {
    public PaymentRepository repository;

    @BeforeAll
    static void init() {
        BasicConfigurator.configure();
    }

    @BeforeEach
    public void setUp() {
        repository = new PaymentRepository();
    }

    @Test
    public void getAllPayments() {
        assertEquals(0, repository.getAll().size());
        repository.add(new Payment(8, PaymentType.CASH, 12.3));
        repository.add(new Payment(7, PaymentType.CARD, 21.3));
        repository.add(new Payment(6, PaymentType.CASH, 32.1));
        assertEquals(3, repository.getAll().size());
    }

    @Test
    public void addPayment() {
        assertEquals(0, repository.getAll().size());
        repository.add(new Payment(8, PaymentType.CASH, 12.3));
        assertEquals(1, repository.getAll().size());
        assertEquals(8, repository.getAll().get(0).getTableNumber());
    }
}
