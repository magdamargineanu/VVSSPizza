package mmir2764.service;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import mmir2764.repository.PaymentRepository;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PizzaServiceTest {
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
            e.printStackTrace();
        }
        assertEquals(0, repository.getAll().size());
    }

    @Test
    @Disabled
    void addInvalidPaymentAmountBVAUpperLimit() {
        // assert
//        assertThrows(Exception.class,() -> service.addPayment(new Payment(1, PaymentType.CASH, Double.MAX_VALUE + 1)));
        try {
            service.addPayment(new Payment(1, PaymentType.CASH, Double.MAX_VALUE + 1));
            assert false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(0, repository.getAll().size());
    }

    @ParameterizedTest
    @EnumSource(PaymentType.class)
    void addValidPaymentTypeECP(PaymentType type) throws Exception {
        // act
        service.addPayment(new Payment(1, type, 12.4f));

        // assert
        assertEquals(1, repository.getAll().size());
    }

    @Nested
    class TableNumberTest {
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
                e.printStackTrace();
            }
//            assertThrows(Exception.class, () -> service.addPayment(new Payment(0, PaymentType.CASH, 10f)));
//            assertThrows(Exception.class, () -> service.addPayment(new Payment(9, PaymentType.CASH, 10f)));
        }
    }

    @Nested
    class GetTotalAmountWBTTest {
        @Test
        void invalidPaymentTypeTest() {
            try {
                service.getTotalAmount(null);
                assert false;
            } catch (Exception e) {
                e.printStackTrace();
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
}