package mmir2764.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mmir2764.model.MenuDataModel;
import mmir2764.service.PaymentAlert;
import mmir2764.service.PizzaService;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrdersGUIController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @FXML
    private ComboBox<Integer> orderQuantity;
    @FXML
    private TableView<MenuDataModel> orderTable;
    @FXML
    private TableColumn<MenuDataModel,Integer> tableQuantity;
    @FXML
    protected TableColumn<MenuDataModel,String> tableMenuItem;
    @FXML
    private TableColumn<MenuDataModel,Double> tablePrice;
    @FXML
    private Label pizzaTypeLabel;
    @FXML
    private Button addToOrder;
    @FXML
    private Label orderStatus;
    @FXML
    private Button placeOrder;
    @FXML
    private Button orderServed;
    @FXML
    private Button payOrder;
    @FXML
    private Button newOrder;

    private List<String> orderList = FXCollections.observableArrayList();
    private List<Double> orderPaymentList = FXCollections.observableArrayList();

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmountValue) {
        totalAmount = totalAmountValue;
    }

    private PizzaService service;
    private int tableNumber;


    private TableView<MenuDataModel> table = new TableView<>();
    private Calendar now = Calendar.getInstance();
    private double totalAmount;

    public void setService(PizzaService service, int tableNumber) {
        this.service = service;
        this.tableNumber = tableNumber;
        initData();

    }

    private void initData() {
        ObservableList<MenuDataModel> menuData = FXCollections.observableArrayList(service.getMenuData());
        menuData.setAll(service.getMenuData());
        orderTable.setItems(menuData);

        //Controller for Place Order Button
        placeOrder.setOnAction(event -> {
            orderList = menuData.stream()
                    .filter(x -> x.getQuantity() > 0)
                    .map(menuDataModel -> menuDataModel.getQuantity() + " " + menuDataModel.getMenuItem())
                    .collect(Collectors.toList());
            KitchenGUIController.order.add("Table" + tableNumber + " " + orderList.toString());
            orderStatus.setText("Order placed at: " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE));
        });

        //Controller for Order Served Button
        orderServed.setOnAction(event -> orderStatus.setText("Served at: " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE)));
        //Controller for Pay Order Button
        payOrder.setOnAction(event -> {
            orderPaymentList = menuData.stream()
                    .filter(x -> x.getQuantity() > 0)
                    .map(menuDataModel -> menuDataModel.getQuantity() * menuDataModel.getPrice())
                    .collect(Collectors.toList());
            setTotalAmount(orderPaymentList.stream().mapToDouble(Double::doubleValue).sum());
            orderStatus.setText("Total amount: " + getTotalAmount());
            logger.info("--------------------------");
            logger.info("Table: " + tableNumber);
            logger.info("Total: " + getTotalAmount());
            logger.info("--------------------------");
            PaymentAlert pay = new PaymentAlert(service);
            pay.showPaymentAlert(tableNumber, getTotalAmount());
        });
    }

    public void initialize() {

        //populate table view with menuData from OrderGUI
        table.setEditable(true);
        tableMenuItem.setCellValueFactory(
                new PropertyValueFactory<>("menuItem"));
        tablePrice.setCellValueFactory(
                new PropertyValueFactory<>("price"));
        tableQuantity.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        //bind pizzaTypeLabel and quantity combo box with the selection on the table view
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> pizzaTypeLabel.textProperty().bind(newValue.menuItemProperty()));

        //Populate Combo box for Quantity
        ObservableList<Integer> quantityValues = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);
        orderQuantity.getItems().addAll(quantityValues);
        orderQuantity.setPromptText("Quantity");

        //Controller for Add to order Button
        addToOrder.setOnAction(event -> orderTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MenuDataModel>() {
            @Override
            public void changed(ObservableValue<? extends MenuDataModel> observable, MenuDataModel oldValue, MenuDataModel newValue) {
                oldValue.setQuantity(orderQuantity.getValue());
                orderTable.getSelectionModel().selectedItemProperty().removeListener(this);
            }
        }));

        //Controller for Exit table Button
        newOrder.setOnAction(event -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Exit table?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Stage stage = (Stage) newOrder.getScene().getWindow();
                stage.close();
            }
        });
    }
}
