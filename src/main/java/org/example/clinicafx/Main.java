package org.example.clinicafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.clinicafx.util.ConexaoMySQL;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Tenta conectar ao banco de dados ao iniciar
            ConexaoMySQL.getConexao();

            // Carrega a primeira tela (Login.fxml)

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            primaryStage.setTitle("ClinicaFX - Sistema de Agendamento");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar o FXML do Login:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro geral ao iniciar a aplicação:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}