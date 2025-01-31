package br.com.tp1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Paint;

public class JogoSlots {
    private Cassino cassino;
    private App app;
    private double aposta;
    private Label lblResultado;
    private Label lblSaldo;

    public JogoSlots(Cassino cassino, App app) {
        this.cassino = cassino;
        this.app = app;
    }

    @SuppressWarnings("exports")
    public Scene getJogoSlotsScene() {
        // Configuração do fundo escuro
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 30), CornerRadii.EMPTY, null)));

        // Labels estilizados
        lblResultado = new Label("Insira o valor da aposta e clique em Confirmar.");
        lblResultado.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblResultado.setTextFill(Color.WHITE);

        lblSaldo = new Label("Saldo atual: R$" + cassino.getSaldoJogador());
        lblSaldo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblSaldo.setTextFill(Color.LIMEGREEN);

        // Campo de texto para a aposta
        TextField txtAposta = new TextField();
        txtAposta.setPromptText("Valor da aposta");
        txtAposta.setStyle("-fx-background-color: #222; -fx-text-fill: white; -fx-prompt-text-fill: #888; -fx-border-color: #555; -fx-border-radius: 5px;");

        // Botão de confirmar aposta
        Button btnConfirmar = criarBotao("Confirmar");
        btnConfirmar.setOnAction(e -> {
            try {
                double valor = Double.parseDouble(txtAposta.getText());
                if (valor <= 0) {
                    lblResultado.setText("O valor da aposta deve ser positivo.");
                } else if (valor > cassino.getSaldoJogador()) {
                    lblResultado.setText("Saldo insuficiente para essa aposta.");
                } else {
                    this.aposta = valor;
                    iniciarJogo();
                }
            } catch (NumberFormatException ex) {
                lblResultado.setText("Entrada inválida. Insira um número válido.");
            }
        });

        // Botão de voltar
        Button btnVoltar = criarBotao("Voltar ao Cassino");
        btnVoltar.setOnAction(e -> app.mostrarTelaPrincipal(cassino.getJogador()));

        // Layout da aposta
        HBox apostaLayout = new HBox(10, txtAposta, btnConfirmar);
        apostaLayout.setAlignment(Pos.CENTER);

        // Montando a cena
        layout.getChildren().addAll(lblSaldo, lblResultado, apostaLayout, btnVoltar);
        return new Scene(layout, 800, 400);
    }

    private void iniciarJogo() {
        // Labels dos slots
        Label slot1 = criarLabelSlot();
        Label slot2 = criarLabelSlot();
        Label slot3 = criarLabelSlot();

        // Botão para girar os slots
        Button btnGirar = criarBotao("Girar Slots");
        btnGirar.setOnAction(e -> {
            String[] simbolos = {"B", "Z", "8"};
            String resultado1 = simbolos[(int) (Math.random() * simbolos.length)];
            String resultado2 = simbolos[(int) (Math.random() * simbolos.length)];
            String resultado3 = simbolos[(int) (Math.random() * simbolos.length)];

            slot1.setText(resultado1);
            slot2.setText(resultado2);
            slot3.setText(resultado3);

            if (resultado1.equals(resultado2) && resultado2.equals(resultado3)) {
                double premio = aposta * 2;
                cassino.aumentarSaldo(premio);
                lblResultado.setText("Você ganhou! Prêmio: R$" + premio);
            } else {
                cassino.diminuirSaldo(aposta);
                lblResultado.setText("Você perdeu! Custo: R$" + aposta);
            }

            lblSaldo.setText("Saldo atual: R$" + cassino.getSaldoJogador());
        });

        // Botão para voltar
        Button btnVoltarAposta = criarBotao("Voltar");
        btnVoltarAposta.setOnAction(e -> recriarTelaAposta());

        // Layout dos slots
        HBox slotsLayout = new HBox(20, slot1, slot2, slot3);
        slotsLayout.setAlignment(Pos.CENTER);

        // Atualiza a tela
        VBox layout = (VBox) lblResultado.getParent();
        layout.getChildren().clear();
        layout.getChildren().addAll(lblSaldo, lblResultado, slotsLayout, btnGirar, btnVoltarAposta);
    }

    private void recriarTelaAposta() {
        VBox layout = (VBox) lblResultado.getParent();
        layout.getChildren().clear();

        TextField txtAposta = new TextField();
        txtAposta.setPromptText("Valor da aposta");
        txtAposta.setStyle("-fx-background-color: #222; -fx-text-fill: white; -fx-prompt-text-fill: #888; -fx-border-color: #555; -fx-border-radius: 5px;");

        Button btnConfirmar = criarBotao("Confirmar");
        btnConfirmar.setOnAction(event -> {
            try {
                double valor = Double.parseDouble(txtAposta.getText());
                if (valor <= 0) {
                    lblResultado.setText("O valor da aposta deve ser positivo.");
                } else if (valor > cassino.getSaldoJogador()) {
                    lblResultado.setText("Saldo insuficiente para essa aposta.");
                } else {
                    this.aposta = valor;
                    iniciarJogo();
                }
            } catch (NumberFormatException ex) {
                lblResultado.setText("Entrada inválida. Insira um número válido.");
            }
        });

        Button btnVoltar = criarBotao("Voltar ao Cassino");
        btnVoltar.setOnAction(event -> app.mostrarTelaPrincipal(cassino.getJogador()));

        HBox apostaLayout = new HBox(10, txtAposta, btnConfirmar);
        apostaLayout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(lblSaldo, lblResultado, apostaLayout, btnVoltar);
    }

    private Button criarBotao(String texto) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #666; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        return btn;
    }

    private Label criarLabelSlot() {
        Label lbl = new Label();
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        lbl.setTextFill(Color.ORANGE);
        return lbl;
    }
}
