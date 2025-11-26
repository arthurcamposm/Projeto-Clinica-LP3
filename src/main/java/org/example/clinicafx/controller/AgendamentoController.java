package org.example.clinicafx.controller;

import org.example.clinicafx.dao.ConsultaDAO;
import org.example.clinicafx.dao.MedicoDAO;
import org.example.clinicafx.dao.PacienteDAO;
import org.example.clinicafx.model.Consulta;
import org.example.clinicafx.model.Medico;
import org.example.clinicafx.model.Paciente;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class AgendamentoController implements Initializable {

    @FXML private TextField txtCpfPaciente;
    @FXML private Label lblNomePaciente; // Para confirmar quem é o paciente
    @FXML private ComboBox<Medico> cmbMedicos; // Lista de médicos
    @FXML private DatePicker dateData;
    @FXML private TextField txtHora;
    @FXML private TextField txtValor;
    @FXML private Button btnAgendar;
    @FXML private Label lblStatus;

    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    private Paciente pacienteSelecionado = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarMedicos();
    }

    private void carregarMedicos() {
        // Preenche o ComboBox com os médicos do banco
        cmbMedicos.setItems(FXCollections.observableArrayList(medicoDAO.listarTodos()));

        // Define como o médico aparece na lista
        cmbMedicos.setConverter(new StringConverter<Medico>() {
            @Override
            public String toString(Medico m) {
                return (m == null) ? "" : m.getNomeCompleto();
            }
            @Override
            public Medico fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void handleBuscarPaciente(ActionEvent event) {
        String cpf = txtCpfPaciente.getText();
        Paciente p = pacienteDAO.buscarPorCpf(cpf);
        if (p != null) {
            pacienteSelecionado = p;
            lblNomePaciente.setText(p.getNomeCompleto());
            lblNomePaciente.setStyle("-fx-text-fill: green;");
        } else {
            pacienteSelecionado = null;
            lblNomePaciente.setText("Paciente não encontrado");
            lblNomePaciente.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    void handleAgendar(ActionEvent event) {
        try {
            if (pacienteSelecionado == null) {
                lblStatus.setText("Busque um paciente primeiro.");
                return;
            }
            Medico medico = cmbMedicos.getValue();
            if (medico == null || dateData.getValue() == null || txtHora.getText().isEmpty()) {
                lblStatus.setText("Preencha todos os campos.");
                return;
            }

            // Criar objeto Consulta
            Consulta consulta = new Consulta();
            consulta.setPaciente(pacienteSelecionado);
            consulta.setMedico(medico);
            consulta.setDataConsulta(dateData.getValue());
            consulta.setHorarioConsulta(LocalTime.parse(txtHora.getText()));
            consulta.setValorConsulta(Float.parseFloat(txtValor.getText()));

            // Salvar no Banco
            consultaDAO.agendar(consulta);

            lblStatus.setText("Consulta agendada com sucesso!");

        } catch (DateTimeParseException e) {
            lblStatus.setText("Hora inválida. Use HH:mm (ex: 14:30)");
        } catch (Exception e) {
            e.printStackTrace();
            lblStatus.setText("Erro ao agendar: " + e.getMessage());
        }
    }
}
