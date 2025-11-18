package org.example.clinicafx.controller;

import org.example.clinicafx.dao.UsuarioDAO; // IMPORTAR O DAO
import org.example.clinicafx.model.Usuario;   // IMPORTAR O MODEL
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

public class LoginController {

    @FXML private TextField txtCpf;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnLogin;
    @FXML private Label lblStatus;

    // Instancia o DAO para o login
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // (Opcional) Guarda quem está logado
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
}