package mmir2764.repository;

import mmir2764.model.Payment;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    public static String filename = "src/main/resources/data/payments.txt";
    private List<Payment> paymentList;

    private final Logger logger = Logger.getLogger(this.getClass());

    public PaymentRepository() {
        this.paymentList = new ArrayList<>();
    }

    public void add(Payment payment) {
        paymentList.add(payment);
        writeToFile();
    }

    public List<Payment> getAll() {
        return paymentList;
    }

    public void writeToFile() {
        File file = new File(filename);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            for (Payment payment : paymentList) {
                logger.info(payment.toString());
                bw.write(payment.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}