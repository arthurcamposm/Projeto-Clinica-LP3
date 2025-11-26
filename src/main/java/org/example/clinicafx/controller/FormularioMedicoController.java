package org.example.clinicafx.controller;


import org.example.clinicafx.dao.EspecialidadeDAO;
import org.example.clinicafx.dao.MedicoDAO;
import org.example.clinicafx.model.Especialidade;
import org.example.clinicafx.model.Medico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

// --- Implementar "Initializable" para rodar código ao abrir a janela ---
public class FormularioMedicoController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtCrm;
    @FXML private DatePicker datePickerNascimento;
    @FXML private TextField txtCep;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblStatus;
    @FXML private Button btnCancelar;

    // Campos de Busca
    @FXML private TextField txtBuscaCrm;
    @FXML private Button btnBuscar;

    // NOVO: O ComboBox da Especialidade
    @FXML private ComboBox<Especialidade> cmbEspecialidade;

    // DAOs
    private final MedicoDAO medicoDAO = new MedicoDAO();
    // DAO para buscar as especialidades
    private final EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

    private Medico medicoAtual = null;

    // --- Método que roda automaticamente ao abrir a tela ---
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarEspecialidades();
    }

    // Método auxiliar para preencher o ComboBox
    private void carregarEspecialidades() {
        // Busca a lista do banco e coloca no ComboBox
        cmbEspecialidade.getItems().addAll(especialidadeDAO.listarTodas());

        // Ensina o ComboBox a mostrar apenas o NOME (texto) e não o objeto inteiro
        cmbEspecialidade.setConverter(new StringConverter<Especialidade>() {
            @Override
            public String toString(Especialidade e) {
                return (e == null) ? "" : e.getNome();
            }

            @Override
            public Especialidade fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void handleSalvarButtonAction(ActionEvent event) {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String crm = txtCrm.getText();
            LocalDate dataNasc = datePickerNascimento.getValue();
            String cep = txtCep.getText();
            String senha = txtSenha.getText();
            // Pegar a especialidade selecionada
            Especialidade especialidadeSelecionada = cmbEspecialidade.getValue();

            // Validar se selecionou a especialidade
            if (nome.isEmpty() || cpf.isEmpty() || crm.isEmpty() || senha.isEmpty() || especialidadeSelecionada == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Preencha todos os campos e selecione uma especialidade.");
                return;
            }

            Medico medico = new Medico(nome, dataNasc, cpf, cep, senha, crm);

            // Adiciona a especialidade ao objeto médico
            medico.getEspecialidades().add(especialidadeSelecionada);

            if (medicoAtual != null) {
                medico.setId(medicoAtual.getId());
                medicoDAO.atualizar(medico);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Dados do médico atualizados com sucesso!");
                lblStatus.setText("Médico atualizado com sucesso!");
            } else {
                medicoDAO.salvar(medico);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Médico salvo com sucesso!");
                lblStatus.setText("Médico salvo com sucesso!");
            }

            limparCampos();

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
        // Limpar seleção do combo
        cmbEspecialidade.getSelectionModel().clearSelection();
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