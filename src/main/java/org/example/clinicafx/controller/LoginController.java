package org.example.clinicafx.controller; // Pacote 'controller'

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

// import com.example.clinicafx.dao.UsuarioDAO; // Vamos precisar disso em breve

public class LoginController {

    @FXML
    private TextField txtCpf;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblStatus;

    // private UsuarioDAO usuarioDAO = new UsuarioDAO(); // Descomente quando criar o DAO

    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        String cpf = txtCpf.getText();
        String senha = txtSenha.getText();

        if (cpf.isEmpty() || senha.isEmpty()) {
            lblStatus.setText("CPF e Senha são obrigatórios.");
            return;
        }

        // --- LÓGICA DE LOGIN ---
        // (Ainda simulado. Substitua por 'usuarioDAO.validarLogin(cpf, senha)' no futuro)
        boolean loginValido = true;

        if (loginValido) {
            lblStatus.setText("Login com sucesso!");
            System.out.println("Login bem-sucedido, carregando painel principal...");

            try {
                // Pega o "palco" (janela) atual do login
                Stage stageLogin = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Carrega o FXML do Dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clinicafx/Dashboard.fxml"));
                Parent root = loader.load();

                // Cria um novo "palco" (janela) para o Dashboard
                Stage stageDashboard = new Stage();
                stageDashboard.setTitle("Painel Principal - ClinicaFX");
                stageDashboard.setScene(new Scene(root));

                // Exibe o Dashboard
                stageDashboard.show();

                // Fecha a janela de login
                stageLogin.close();

            } catch (Exception e) {
                e.printStackTrace();
                lblStatus.setText("Erro ao carregar o painel principal.");
            }

        } else {
            lblStatus.setText("CPF ou Senha inválidos.");
        }
    }
}