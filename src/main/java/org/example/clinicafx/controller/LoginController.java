package org.example.clinicafx.controller;

import javafx.stage.Modality;
import org.example.clinicafx.dao.UsuarioDAO;
import org.example.clinicafx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCpf;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnLogin;
    @FXML private Label lblStatus;

    // Instancia o DAO para o login
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Guarda quem está logado
    public static Usuario usuarioLogado;

    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        String cpf = txtCpf.getText();
        String senha = txtSenha.getText();

        if (cpf.isEmpty() || senha.isEmpty()) {
            lblStatus.setText("CPF e Senha são obrigatórios.");
            return;
        }

        // --- LÓGICA DE LOGIN REAL ---
        Usuario usuario = usuarioDAO.validarLogin(cpf, senha);
        boolean loginValido = (usuario != null);

        if (loginValido) {
            usuarioLogado = usuario; // Guarda o usuário logado
            lblStatus.setText("Login com sucesso! Bem-vindo, " + usuario.getNomeCompleto());
            System.out.println("Login bem-sucedido, carregando painel principal...");

            try {
                Stage stageLogin = (Stage) ((Node) event.getSource()).getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clinicafx/Dashboard.fxml"));
                Parent root = loader.load();

                Stage stageDashboard = new Stage();
                stageDashboard.setTitle("Painel Principal - ClinicaFX");
                stageDashboard.setScene(new Scene(root));
                stageDashboard.show();

                stageLogin.close();

            } catch (Exception e) {
                e.printStackTrace();
                lblStatus.setText("Erro ao carregar o painel principal.");
            }

        } else {
            lblStatus.setText("CPF ou Senha inválidos.");
        }
    }

    @FXML
    void handleCadastreSe(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clinicafx/FormularioPaciente.fxml"));
            Parent root = loader.load();

            // Pegamos a instância do Controller que o FXMLLoader criou
            FormularioPacienteController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Novo Cadastro - Paciente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            // O código para aqui e espera a janela fechar
            stage.showAndWait();

            // Agora verificamos: O usuário salvou ou só fechou a janela?
            if (controller.isSalvo()) {
                lblStatus.setText("Cadastro realizado! Tente logar agora.");
                lblStatus.setStyle("-fx-text-fill: green;"); // Opcional: deixa verde
            } else {
                // Se cancelou, apenas limpamos o status ou não fazemos nada
                lblStatus.setText("");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}