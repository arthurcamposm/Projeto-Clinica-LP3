package org.example.clinicafx.controller;

import org.example.clinicafx.dao.PlanoDeSaudeDAO;
import org.example.clinicafx.model.PlanoDeSaude;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormularioPlanoController {

    @FXML private TextField txtNomePlano;
    @FXML private TextField txtOperadora;
    @FXML private Button btnCancelar;

    private final PlanoDeSaudeDAO planoDAO = new PlanoDeSaudeDAO();

    @FXML
    void handleSalvar(ActionEvent event) {
        String nome = txtNomePlano.getText();
        String operadora = txtOperadora.getText();

        if (nome.isEmpty() || operadora.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos.");
            return;
        }

        PlanoDeSaude plano = new PlanoDeSaude(nome, operadora);
        planoDAO.salvar(plano);

        showAlert("Sucesso", "Plano salvo com sucesso!");
        txtNomePlano.clear();
        txtOperadora.clear();
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}