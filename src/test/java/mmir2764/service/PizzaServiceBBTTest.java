package mmir2764.service;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.repository.PaymentRepository;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PizzaServiceBBTTest {
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
    @DisplayName("Test valid amount")
    @Order(1)
    void addValidPaymentBVA() throws Exception {
        // act
        service.addPayment(new Payment(1, PaymentType.CARD, 1f));

        // assert
        assertEquals(1, repository.getAll().size());
    }

    @ParameterizedTest
    @ValueSource(floats = {-1, 0})
    @Order(2)
    void addInvalidPaymentAmountBVALowerLimit(float amount) {
        // assert
//        assertThrows(Exception.class, () -> service.addPayment(new Payment(1, PaymentType.CARD, amount)));
        try {
            service.addPayment(new Payment(1, PaymentType.CARD, amount));
            assert false;
        } catch (Exception e) {
            assert true;
        }
        assertEquals(0, repository.getAll().size());
    }

    @Test
    void addInvalidPaymentAmountBVAUpperLimit() {
        // assert
//        assertThrows(Exception.class,() -> service.addPayment(new Payment(1, PaymentType.CASH, Double.MAX_VALUE + 1)));
        try {
            service.addPayment(new Payment(1, null, Double.MAX_VALUE + 1));
            assert false;
        } catch (Exception e) {
            assert true;
        }
        assertEquals(0, repository.getAll().size());
    }

//    @ParameterizedTest
//    @EnumSource(PaymentType.class)
    @Test
    void addValidPaymentTypeECP() throws Exception {
        // act
        service.addPayment(new Payment(1, PaymentType.CASH, 12.4f));
        service.addPayment(new Payment(1, PaymentType.CARD, 12.4f));

        // assert
        assertEquals(2, repository.getAll().size());
    }

//    @Nested
//    class TableNumberTest {
        @Test
        void addValidPaymentTableNumber() throws Exception {
            //act
            for (int i = 1; i <= 8; i++)
                service.addPayment(new Payment(i, PaymentType.CASH, 10f));

            // assert
            assertEquals(8, repository.getAll().size());
        }

        @Test
        void addInvalidPaymentTableNumber() {
            // assert
            try {
                service.addPayment(new Payment(0, PaymentType.CASH, 10f));
                assert false;
                service.addPayment(new Payment(9, PaymentType.CASH, 10f));
                assert false;
            } catch (Exception e) {
                assert true;
            }
//            assertThrows(Exception.class, () -> service.addPayment(new Payment(0, PaymentType.CASH, 10f)));
//            assertThrows(Exception.class, () -> service.addPayment(new Payment(9, PaymentType.CASH, 10f)));
        }
//    }

}