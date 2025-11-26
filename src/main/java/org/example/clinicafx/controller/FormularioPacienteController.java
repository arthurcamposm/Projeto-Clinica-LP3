package org.example.clinicafx.controller;

import org.example.clinicafx.dao.PacienteDAO;
import org.example.clinicafx.model.Paciente;
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

public class FormularioPacienteController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private DatePicker datePickerNascimento;
    @FXML private TextField txtCep;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;
    @FXML private TextField txtBuscaCpf;
    @FXML private Button btnBuscar;
    @FXML private Label lblStatus;

    // Variável de controle
    private boolean salvo = false;

    // Método para outros controllers saberem se deu certo
    public boolean isSalvo() {
        return salvo;
    }

    private final PacienteDAO pacienteDAO = new PacienteDAO();


    private Paciente pacienteAtual = null;


    @FXML
    void handleSalvarButtonAction(ActionEvent event) {
        try {
            // Coletar dados do formulário
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            LocalDate dataNasc = datePickerNascimento.getValue();
            String cep = txtCep.getText();
            String senha = txtSenha.getText();

            // Validação simples
            if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Campos obrigatórios (Nome, CPF, Senha) não podem estar vazios.");
                return;
            }



            // Criar objeto Paciente
            Paciente paciente = new Paciente(nome, dataNasc, cpf, cep, senha);

            // Chamar o DAO para salvar
            // Se o pacienteAtual existe, significa que estamos atualizando
            if(pacienteAtual != null) {
                paciente.setId(pacienteAtual.getId());
                pacienteDAO.atualizar(paciente);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Paciente atualizado!");
                lblStatus.setText("Paciente atualizado com sucesso!");
            } else {
                // Se não, estamos criando um novo
                pacienteDAO.salvar(paciente);
                this.salvo = true;
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Paciente salvo com sucesso!");
                lblStatus.setText("Paciente salvo com sucesso!");
            }

            // Limpar o formulário e fechar a janela
            limparCampos();
            handleCancelarButtonAction(event); // Fecha a janela

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao salvar: " + e.getMessage());
        }
    }


    @FXML
    void handleBuscarButtonAction(ActionEvent event) {
        String cpf = txtBuscaCpf.getText();
        if (cpf.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Aviso", "Digite um CPF para buscar.");
            return;
        }

        Paciente paciente = pacienteDAO.buscarPorCpf(cpf);

        if (paciente != null) {
            pacienteAtual = paciente;
            txtNome.setText(paciente.getNomeCompleto());
            txtCpf.setText(paciente.getCpf());
            datePickerNascimento.setValue(paciente.getDataNascimento());
            txtCep.setText(paciente.getCep());
            txtSenha.setText("");
            lblStatus.setText("Paciente encontrado. Você pode editar e salvar.");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Não encontrado", "Nenhum paciente encontrado com o CPF informado.");
            limparCampos();
        }
    }

    @FXML
    void handleCancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void limparCampos() {
        pacienteAtual = null;
        txtNome.clear();
        txtCpf.clear();
        datePickerNascimento.setValue(null);
        txtCep.clear();
        txtSenha.clear();
        txtBuscaCpf.clear();
        lblStatus.setText("");
    }

    // Método utilitário para mostrar alertas
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}