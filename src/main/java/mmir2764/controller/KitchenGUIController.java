package mmir2764.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.apache.log4j.Logger;

import java.util.Calendar;

public class KitchenGUIController {
    private final Logger logger = Logger.getLogger(this.getClass());

    @FXML
    private ListView<String> kitchenOrdersList;
    @FXML
    public Button cook;
    @FXML
    public Button ready;

    protected static ObservableList<String> order = FXCollections.observableArrayList();
    private Object selectedOrder;
    private final Calendar now = Calendar.getInstance();
    private String extractedTableNumberString = "";
    private int extractedTableNumberInteger;
    //thread for adding data to kitchenOrderList
    protected Thread addOrders = new Thread(() -> {
        while (true) {
            Platform.runLater(() -> kitchenOrdersList.setItems(order));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    });

    public void initialize() {
        //starting thread for adding data to kitchenOrderList
        addOrders.setDaemon(true);
        addOrders.start();
        //Controller for Cook Button
        cook.setOnAction(event -> {
            selectedOrder = kitchenOrdersList.getSelectionModel().getSelectedItem();
            kitchenOrdersList.getItems().remove(selectedOrder);
            kitchenOrdersList.getItems().add(selectedOrder.toString()
                     .concat(" Cooking started at: ").toUpperCase()
                     .concat(now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE)));
        });
        //Controller for Ready Button
        ready.setOnAction(event -> {
            selectedOrder = kitchenOrdersList.getSelectionModel().getSelectedItem();
            kitchenOrdersList.getItems().remove(selectedOrder);
            extractedTableNumberString = selectedOrder.toString().subSequence(5, 6).toString();
            extractedTableNumberInteger = Integer.parseInt(extractedTableNumberString);
            logger.info("--------------------------");
            logger.info("Table " + extractedTableNumberInteger +" ready at: " + now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
            logger.info("--------------------------");
        });
    }
}