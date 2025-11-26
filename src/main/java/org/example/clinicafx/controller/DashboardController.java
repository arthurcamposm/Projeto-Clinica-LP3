package org.example.clinicafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        }
    }

    @FXML
    void handleMenuCadastrarMedico(ActionEvent event) {
        try {
            // Carrega o FXML do formulário de paciente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clinicafx/FormularioMedico.fxml"));
            Parent root = loader.load();

            // Cria uma nova janela (Stage) para o formulário
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Médico");
            stage.setScene(new Scene(root));

            // Define como modal (bloqueia a janela principal)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Exibe a janela
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void handleMenuAgendarConsulta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clinicafx/Agendamento.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agendar Consulta");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- CADASTROS > PLANO DE SAÚDE ---
    @FXML
    void handleMenuCadastrarPlano(ActionEvent event) {
        abrirJanela("/org/example/clinicafx/FormularioPlano.fxml", "Cadastro de Plano de Saúde");
    }

    // --- AGENDAMENTO > VISUALIZAR AGENDA ---
    @FXML
    void handleMenuVisualizarAgenda(ActionEvent event) {
        abrirJanela("/org/example/clinicafx/Agenda.fxml", "Agenda de Consultas");
    }

    // --- AJUDA > SOBRE ---
    @FXML
    void handleMenuSobre(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre o ClinicaFX");
        alert.setHeaderText("Sistema de Gestão de Clínicas");
        alert.setContentText("Desenvolvido como projeto acadêmico.\nVersão 1.0\nTecnologias: JavaFX + MySQL");
        alert.showAndWait();
    }

    private void abrirJanela(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
