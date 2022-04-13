package mmir2764.repository;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentRepositoryTest {

    Repository<Payment> repository;

    @Before
    public void setUp() {
        repository = mock(Repository.class);
    }

    @Test
    public void getAllPaymentsTest() {
        Payment payment1 = new Payment(5, PaymentType.CARD, 26.8);
        Payment payment2 = new Payment(23, PaymentType.CARD, 26.8);
        Mockito.when(repository.getAll()).thenReturn(Arrays.asList(payment1, payment2));

        assert true;
        assertEquals(2, repository.getAll().size());
        assert 2 == repository.getAll().size();
        Mockito.verify(repository, times(2)).getAll();
    }

    @Test
    public void addPaymentTest() {
        Payment payment1 = new Payment(5, PaymentType.CARD, 26.8);
        Payment payment2 = new Payment(5, PaymentType.CARD, 226.8);

        Mockito.doNothing().when(repository).add(payment2);


        repository.add(payment1);
        repository.add(payment2);
        repository.add(payment2);
        Mockito.verify(repository, never()).getAll();
        Mockito.verify(repository, times(2)).add(payment2);
        Mockito.verify(repository, times(1)).add(payment1);


    }
}
