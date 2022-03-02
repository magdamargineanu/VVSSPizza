package mmir2764.repository;

import mmir2764.model.Payment;
import mmir2764.model.PaymentType;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentRepository {
    private static String filename = "src/main/resources/data/payments.txt";
    private List<Payment> paymentList;

    private final Logger logger = Logger.getLogger(this.getClass());

    public PaymentRepository(){
        this.paymentList = new ArrayList<>();
    }

    public void add(Payment payment){
        paymentList.add(payment);
        writeAll();
    }

    public List<Payment> getAll(){
        return paymentList;
    }

    public void writeAll(){

        ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(filename);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {
            for (Payment payment:paymentList) {
                logger.info(payment.toString());
                bw.write(payment.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}