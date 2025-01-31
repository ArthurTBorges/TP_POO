package br.com.tp1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;
    private Cassino cassino;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mostrarTelaLogin();
        primaryStage.setTitle("Cassino Online");
        primaryStage.show();
    }

    public void mostrarTelaLogin() {
        Scene loginScene = Jogador.getLoginScene(this); // Passa a referência do App
        primaryStage.setScene(loginScene);
    }

    public void mostrarTelaPrincipal(Jogador jogador) {
        this.cassino = new Cassino(jogador, this); // Passa a referência do App
        Scene cassinoScene = cassino.getCassinoScene();
        primaryStage.setScene(cassinoScene);
    }

    public void mostrarJogoSlots() {
        JogoSlots jogoSlots = new JogoSlots(cassino, this); // Passa a referência do App
        Scene jogoSlotsScene = jogoSlots.getJogoSlotsScene();
        primaryStage.setScene(jogoSlotsScene);
    }

    public void mostrarBacBo() {
        BacBo bacBo = new BacBo(cassino, this); // Passa a referência do App
        Scene bacBoScene = bacBo.getBacBoScene();
        primaryStage.setScene(bacBoScene);
    }

    public void mostrarMines() {
        Mines mines = new Mines(cassino, this); // Passa a referência do App
        Scene minesScene = mines.getMinesScene();
        primaryStage.setScene(minesScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}