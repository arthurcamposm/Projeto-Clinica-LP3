package org.example.clinicafx.controller;

import org.example.clinicafx.dao.MedicoDAO;
import org.example.clinicafx.model.Medico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class FormularioMedicoController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtCrm;
    @FXML private DatePicker datePickerNascimento;
    @FXML private TextField txtCep;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblStatus;
    @FXML private Button btnCancelar;


    @FXML private TextField txtBuscaCrm;
    @FXML private Button btnBuscar;

    private final MedicoDAO medicoDAO = new MedicoDAO();
    private Medico medicoAtual = null;

    @FXML
    void handleSalvarButtonAction(ActionEvent event) {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String crm = txtCrm.getText();
            LocalDate dataNasc = datePickerNascimento.getValue();
            String cep = txtCep.getText();
            String senha = txtSenha.getText();

            if (nome.isEmpty() || cpf.isEmpty() || crm.isEmpty() || senha.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Preencha os campos obrigatórios.");
                return;
            }

            Medico medico = new Medico(nome, dataNasc, cpf, cep, senha, crm);

            // Lógica de Salvar vs Atualizar
            if (medicoAtual != null) {
                // Estamos Editando
                medico.setId(medicoAtual.getId());
                medicoDAO.atualizar(medico);
                lblStatus.setText("Médico atualizado com sucesso!");
            } else {
                // Estamos Criando Novo
                medicoDAO.salvar(medico);
                lblStatus.setText("Médico salvo com sucesso!");
            }

            limparCampos();
            // handleCancelarButtonAction(event);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    void handleBuscarButtonAction(ActionEvent event) {
        String crmBusca = txtBuscaCrm.getText();

        if (crmBusca.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Aviso", "Digite um CRM para buscar.");
            return;
        }

        Medico medico = medicoDAO.buscarPorCrm(crmBusca);

        if (medico != null) {
            medicoAtual = medico;

            // Preenche os campos
            txtNome.setText(medico.getNomeCompleto());
            txtCpf.setText(medico.getCpf());
            txtCrm.setText(medico.getCrm());
            datePickerNascimento.setValue(medico.getDataNascimento());
            txtCep.setText(medico.getCep());
            txtSenha.setText("");

            lblStatus.setText("Médico encontrado. Editando ID: " + medico.getId());
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Não encontrado", "Nenhum médico com este CRM.");
            limparCampos();
        }
    }

    @FXML
    void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void limparCampos() {
        medicoAtual = null;
        txtNome.clear();
        txtCpf.clear();
        txtCrm.clear();
        datePickerNascimento.setValue(null);
        txtCep.clear();
        txtSenha.clear();
        if(txtBuscaCrm != null) txtBuscaCrm.clear();
        lblStatus.setText("");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}