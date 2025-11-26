package org.example.clinicafx.controller;

import org.example.clinicafx.dao.ConsultaDAO;
import org.example.clinicafx.model.Consulta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AgendaController implements Initializable {

    @FXML private TableView<Consulta> tabelaConsultas;
    @FXML private TableColumn<Consulta, String> colData;
    @FXML private TableColumn<Consulta, String> colHora;
    @FXML private TableColumn<Consulta, String> colPaciente;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private TableColumn<Consulta, String> colEstado;

    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarDados();
    }

    private void configurarColunas() {
        // --- Configurar DATA (Com formatação BR) ---
        // Cria um formatador de dia/mês/ano
        java.time.format.DateTimeFormatter formatoData = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

        colData.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDataConsulta() != null) {
                // Pega a data, formata para String e devolve para a tabela
                return new SimpleStringProperty(cellData.getValue().getDataConsulta().format(formatoData));
            } else {
                return new SimpleStringProperty("");
            }
        });

        // --- Configurar HORA ---
        colHora.setCellValueFactory(cellData -> {
            if (cellData.getValue().getHorarioConsulta() != null) {
                return new SimpleStringProperty(cellData.getValue().getHorarioConsulta().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });

        // --- Configurar ESTADO ---
        colEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado()));

        // --- Configurar PACIENTE e MÉDICO (Como já estava) ---
        colPaciente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPaciente().getNomeCompleto()));

        colMedico.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMedico().getNomeCompleto()));
    }

    private void carregarDados() {
        tabelaConsultas.setItems(FXCollections.observableArrayList(consultaDAO.listarTodasComDetalhes()));
    }

    @FXML
    void handleAtualizar() {
        carregarDados();
    }
}