package br.com.tp1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Cassino {
    private double saldoJogador;
    private Jogador jogador;
    private App app;

    public Cassino(Jogador jogador, App app) {
        this.jogador = jogador;
        this.saldoJogador = jogador.getSaldo();
        this.app = app;
    }

    public double getSaldoJogador() {
        return saldoJogador;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void aumentarSaldo(double valor) {
        saldoJogador += valor;
        jogador.setSaldo(saldoJogador);
        jogador.atualizarSaldoNoArquivo(saldoJogador);
    }

    public void diminuirSaldo(double valor) {
        if (saldoJogador >= valor) {
            saldoJogador -= valor;
            jogador.setSaldo(saldoJogador);
            jogador.atualizarSaldoNoArquivo(saldoJogador);
        }
    }

    @SuppressWarnings("exports")
    public Scene getCassinoScene() {
        Label lblNomeSaldo = new Label(jogador.getNome() + " - Saldo: R$" + saldoJogador);
        lblNomeSaldo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblNomeSaldo.setStyle("-fx-text-fill: #FFD700; -fx-background-color: #333333; -fx-padding: 10px; -fx-border-radius: 5px;");

        Button btnDeposito = new Button("Depósito");
        estilizarBotao(btnDeposito, "#4CAF50", 200, 50);
        
        Button btnJogo1 = new Button("Jogo de Slots");
        estilizarBotao(btnJogo1, "#008CBA", 250, 80);
        
        Button btnJogo2 = new Button("Bac Bo");
        estilizarBotao(btnJogo2, "#FF5733", 250, 80);
        
        Button btnJogo3 = new Button("Mines");
        estilizarBotao(btnJogo3, "#9C27B0", 250, 80);

        btnDeposito.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Depósito");
            dialog.setHeaderText("Insira o valor do depósito:");
            dialog.setContentText("Valor:");

            dialog.showAndWait().ifPresent(valor -> {
                try {
                    double deposito = Double.parseDouble(valor);
                    if (deposito > 0) {
                        aumentarSaldo(deposito);
                        lblNomeSaldo.setText(jogador.getNome() + " - Saldo: R$" + saldoJogador);
                    } else {
                        System.out.println("O valor do depósito deve ser positivo.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Entrada inválida. Insira um número válido.");
                }
            });
        });

        btnJogo1.setOnAction(e -> app.mostrarJogoSlots());
        btnJogo2.setOnAction(e -> app.mostrarBacBo());
        btnJogo3.setOnAction(e -> app.mostrarMines());

        VBox saldoDepositoBox = new VBox(10, lblNomeSaldo, btnDeposito);
        saldoDepositoBox.setAlignment(Pos.TOP_CENTER);
        saldoDepositoBox.setPadding(new Insets(20, 20, 20, 20));

        HBox gameButtons = new HBox(30, btnJogo1, btnJogo2, btnJogo3);
        gameButtons.setAlignment(Pos.CENTER);
        
        VBox layout = new VBox(40, saldoDepositoBox, gameButtons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40, 40, 40, 40));
        layout.setStyle("-fx-background-color: #282C34;");

        return new Scene(layout, 900, 500);
    }

    private void estilizarBotao(Button botao, String cor, int largura, int altura) {
        botao.setStyle("-fx-background-color: " + cor + "; " +
                       "-fx-text-fill: white; " +
                       "-fx-font-size: 18px; " +
                       "-fx-padding: 10px; " +
                       "-fx-background-radius: 10px;");
        botao.setPrefSize(largura, altura);
        
        botao.setOnMouseEntered(e -> botao.setStyle("-fx-background-color: derive(" + cor + ", -20%); -fx-text-fill: white;"));
        botao.setOnMouseExited(e -> botao.setStyle("-fx-background-color: " + cor + "; -fx-text-fill: white;"));
    }
}