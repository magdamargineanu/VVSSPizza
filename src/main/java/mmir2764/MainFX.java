package mmir2764;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mmir2764.controller.MainGUIController;
import mmir2764.gui.KitchenGUI;
import mmir2764.model.PaymentType;
import mmir2764.repository.MenuRepository;
import mmir2764.repository.PaymentRepository;
import mmir2764.service.PizzaService;
import org.apache.log4j.Logger;

import java.util.Optional;

public class MainFX extends Application {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void start(Stage primaryStage) throws Exception{

        MenuRepository repoMenu=new MenuRepository();
        PaymentRepository payRepo= new PaymentRepository();
        PizzaService service = new PizzaService(repoMenu, payRepo);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainFXML.fxml"));
        Parent box = loader.load();
        MainGUIController ctrl = loader.getController();
        ctrl.setService(service);
        primaryStage.setTitle("PizeriaX");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setOnCloseRequest(event -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit the Main window?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.get() == ButtonType.YES){
                logger.info("Incasari cash: "+service.getTotalAmount(PaymentType.CASH));
                logger.info("Incasari card: "+service.getTotalAmount(PaymentType.CARD));
                primaryStage.close();
            }
            // consume event
            else if (result.get() == ButtonType.NO){
                event.consume();
            }
            else {
                event.consume();

            }

        });
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
        KitchenGUI kitchenGUI = new KitchenGUI();
        kitchenGUI.init();
    }

    public static void main(String[] args) { launch(args);
    }
}