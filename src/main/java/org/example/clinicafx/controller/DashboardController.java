package org.example.clinicafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private MenuItem menuItemCadPaciente;

    @FXML
    void handleMenuCadastrarPaciente(ActionEvent event) {
        try {
            // Carrega o FXML do formulário de paciente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clinicafx/FormularioPaciente.fxml"));
            Parent root = loader.load();

            // Cria uma nova janela (Stage) para o formulário
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Paciente");
            stage.setScene(new Scene(root));

            // Define como modal (bloqueia a janela principal)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Exibe a janela
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            // Adicione um Alert aqui se desejar
        }
    }
}
