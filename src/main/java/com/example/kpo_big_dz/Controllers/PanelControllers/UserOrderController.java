package com.example.kpo_big_dz.Controllers.PanelControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.kpo_big_dz.Models.Order;
import com.example.kpo_big_dz.Models.OrderStatus;
import com.example.kpo_big_dz.Threads.CookingThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static com.example.kpo_big_dz.DataBase.SQLite.getUserIdByOrderId;
import static com.example.kpo_big_dz.DataBase.SQLite.updateOrderStatus;
import static com.example.kpo_big_dz.TempData.CurrentCookingThreads.currentCookingThreads;
import static com.example.kpo_big_dz.Services.WindowServices.openNewWindow;

public class UserOrderController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button cookButton;

    @FXML
    private Label statusLabel;

    @FXML
    private VBox infoVBox;

    @FXML
    public void onCookButtonClick(ActionEvent e) {
        int orderId = Integer.parseInt(orderNumberLabel.getText().replace("Order №", ""));
        int userId = getUserIdByOrderId(orderId);
        updateOrderStatus(orderId, OrderStatus.Cooking, userId);
        CookingThread thread = new CookingThread(orderId, userId);
        currentCookingThreads.put(orderId, thread);
        thread.start();
    }

    public void onPurchaseButtonClick() {
        try {
            FeedbackPanelController controller = openNewWindow("user/review_panel.fxml", "Feedback");
            controller.setUserIdOrderId(userId, orderId);
        } catch (IOException exception) {
            System.out.println("onPurchaseButtonClick() exception: " + exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void initialize() {

    }

    private int userId;
    private int orderId;
    private int totalPrice;
    private OrderStatus orderStatus;

    public void setData(Order order) {
        this.orderId = order.getOrderId();
        this.totalPrice = order.getTotalPrice();
        this.orderStatus = order.getOrderStatus();
        this.userId = order.getUserId();

        orderNumberLabel.setText("Order №" + orderId);
        statusLabel.setText(orderStatus.value());
        totalPriceLabel.setText("Total price: $" + totalPrice);
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setNoButtonsView() {
        cookButton.setVisible(false);
        infoVBox.setPrefWidth(197);
    }

    public void setCancelButtonView() {
        cookButton.setVisible(true);
        cookButton.setText("Cancel");
        cookButton.setLayoutX(256);
        infoVBox.setPrefWidth(139);
    }

    public void setPurchaseButtonView() {
        cookButton.setVisible(true);
        cookButton.setText("Purchase");
        cookButton.setLayoutX(248);
        infoVBox.setPrefWidth(139);
    }

    public Button getCookButton() {
        return cookButton;
    }
}
