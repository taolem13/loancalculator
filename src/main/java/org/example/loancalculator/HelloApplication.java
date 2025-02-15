package org.example.loancalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Input fields for loan amount, annual interest rate, and number of years
        TextField loanamountField = new TextField();
        loanamountField.setPromptText("Enter loan amount");
        TextField interestrateField = new TextField();
        interestrateField.setPromptText("Enter annual interest rate as a decimal");
        TextField yearsField = new TextField();
        yearsField.setPromptText("Enter number of years");

        //Labels for monthly payment and total payment amounts
        Label monthlypaymentLabel = new Label("Monthly payment: ");
        Label totalpaymentLabel = new Label("Total payment: ");

        //Button to calculate
        Button calculateButton = new Button("Compute payment");

        calculateButton.setOnAction(e -> {
            try {
                //Get the input values
                double loanAmount = Double.parseDouble(loanamountField.getText());
                double interestRate = Double.parseDouble(interestrateField.getText());
                int years = Integer.parseInt(yearsField.getText());

                //Calc the monthly and total payment
                double monthlyInterestRate = interestRate / 12;
                int numberOfMonths = years * 12;
                double monthlyPayment = calculateMonthlyPayment(loanAmount, monthlyInterestRate, numberOfMonths);
                double totalPayment = monthlyPayment * numberOfMonths;

                //Display the results of calculation
                monthlypaymentLabel.setText("Monthly Payment: $" + String.format("%.2f", monthlyPayment));
                totalpaymentLabel.setText("Total Payment: $" + String.format("%.2f", totalPayment));
            }  catch (NumberFormatException exception) {
                //For invalid inputs
                showAlert("Invalid input");
            }
        });

        // Vbox layout
        VBox vbox = new VBox(10, loanamountField, interestrateField, yearsField, calculateButton, monthlypaymentLabel, totalpaymentLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefWidth(300);
        vbox.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(vbox, 350, 300);
        stage.setTitle("LoanCalculator");
        stage.setScene(scene);
        stage.show();

    }
    //Calc monthly payment method
    private double calculateMonthlyPayment(double loanAmount, double monthlyInterestRate, int numberOfMonths) {
        if (monthlyInterestRate == 0) {
            return loanAmount / numberOfMonths;
        } else {
            return (loanAmount * monthlyInterestRate) /
                    (1 - Math.pow(1 + monthlyInterestRate, -numberOfMonths));
        }
    }
    //Error message
    private void showAlert(String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}