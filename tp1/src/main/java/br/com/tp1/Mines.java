package br.com.tp1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Mines {
    private Cassino cassino;
    private App app;
    private Button[][] grade; // Grade 3x3 de botões
    private boolean[][] bombas; // Matriz que indica onde estão as bombas
    private double aposta;
    private Label lblResultado;
    private double premioAcumulado; // Valor acumulado dos acertos
    private Label lblSaldo; // Label para exibir o saldo do jogador

    public Mines(Cassino cassino, App app) {
        this.cassino = cassino;
        this.app = app;
        this.grade = new Button[3][3];
        this.bombas = new boolean[3][3];
        this.premioAcumulado = 0; // Inicializa o prêmio acumulado
    }

    @SuppressWarnings("exports")
    public Scene getMinesScene() {
        // Label para exibir o resultado do jogo
        lblResultado = new Label("Insira o valor da aposta e clique em Confirmar.");
        lblResultado.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblResultado.setTextFill(Color.WHITE);

        // Label para exibir o saldo do jogador
        lblSaldo = new Label("Saldo: R$" + cassino.getSaldoJogador());
        lblSaldo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblSaldo.setTextFill(Color.WHITE);

        // Campo de texto para a aposta
        TextField txtAposta = new TextField();
        txtAposta.setPromptText("Valor da aposta");
        txtAposta.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-prompt-text-fill: #888888;");

        // Botão para confirmar a aposta
        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-weight: bold;");
        btnConfirmar.setOnAction(e -> {
            try {
                double valor = Double.parseDouble(txtAposta.getText());
                if (valor <= 0) {
                    lblResultado.setText("O valor da aposta deve ser positivo.");
                } else if (valor > cassino.getSaldoJogador()) {
                    lblResultado.setText("Saldo insuficiente para essa aposta.");
                } else {
                    this.aposta = valor;
                    this.premioAcumulado = 0; // Reinicia o prêmio acumulado
                    iniciarJogo();
                }
            } catch (NumberFormatException ex) {
                lblResultado.setText("Entrada inválida. Insira um número válido.");
            }
        });

        // Botão para voltar ao cassino
        Button btnVoltar = new Button("Voltar ao Cassino");
        btnVoltar.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-weight: bold;");
        btnVoltar.setOnAction(e -> {
            app.mostrarTelaPrincipal(cassino.getJogador());
        });

        // Layout da aposta
        HBox apostaLayout = new HBox(10, txtAposta, btnConfirmar);
        apostaLayout.setAlignment(Pos.CENTER);

        // Layout principal
        VBox layout = new VBox(20, lblSaldo, lblResultado, apostaLayout, btnVoltar);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #222222;");

        return new Scene(layout, 800, 400);
    }

    private void iniciarJogo() {
        // Inicializa a grade de botões e as bombas
        inicializarGrade();
        colocarBombas();

        // Layout da grade
        GridPane gradeLayout = new GridPane();
        gradeLayout.setAlignment(Pos.CENTER);
        gradeLayout.setHgap(10);
        gradeLayout.setVgap(10);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grade[i][j] = new Button();
                grade[i][j].setPrefSize(80, 80);
                grade[i][j].setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-weight: bold;");
                int finalI = i;
                int finalJ = j;
                grade[i][j].setOnAction(e -> {
                    clicarBotao(finalI, finalJ);
                });
                gradeLayout.add(grade[i][j], j, i);
            }
        }

        // Botão para jogar novamente
        Button btnJogarNovamente = new Button("Jogar Novamente");
        btnJogarNovamente.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-weight: bold;");
        btnJogarNovamente.setOnAction(e -> {
            this.premioAcumulado = 0; // Reinicia o prêmio acumulado
            iniciarJogo();
        });

        // Botão para parar e retirar o valor acumulado
        Button btnPararRetirar = new Button("Parar e Retirar");
        btnPararRetirar.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-weight: bold;");
        btnPararRetirar.setOnAction(e -> {
            if (premioAcumulado > 0) {
                cassino.aumentarSaldo(premioAcumulado); // Adiciona o prêmio acumulado ao saldo
                lblResultado.setText("Você parou e retirou R$" + premioAcumulado + ".");
                atualizarSaldo(); // Atualiza o saldo exibido
            } else {
                lblResultado.setText("Você não tem prêmio acumulado para retirar.");
            }
            desabilitarGrade(); // Desabilita a grade após parar
        });

        // Botão para voltar ao cassino
        Button btnVoltar = new Button("Voltar ao Cassino");
        btnVoltar.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-weight: bold;");
        btnVoltar.setOnAction(e -> {
            app.mostrarTelaPrincipal(cassino.getJogador());
        });

        // Layout dos botões de controle
        HBox controleLayout = new HBox(10, btnJogarNovamente, btnPararRetirar, btnVoltar);
        controleLayout.setAlignment(Pos.CENTER);

        // Atualiza a cena para mostrar o jogo
        VBox layout = (VBox) lblResultado.getParent();
        layout.getChildren().clear();
        layout.getChildren().addAll(lblSaldo, lblResultado, gradeLayout, controleLayout);
        layout.setStyle("-fx-background-color: #222222;");
    }

    private void inicializarGrade() {
        // Inicializa a grade de botões
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grade[i][j] = new Button();
                grade[i][j].setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-weight: bold;");
                bombas[i][j] = false; // Inicialmente, não há bombas
            }
        }
    }

    private void colocarBombas() {
        // Coloca duas bombas aleatórias na grade
        int bombasColocadas = 0;
        while (bombasColocadas < 2) {
            int i = (int) (Math.random() * 3);
            int j = (int) (Math.random() * 3);
            if (!bombas[i][j]) {
                bombas[i][j] = true;
                bombasColocadas++;
            }
        }
    }

    private void clicarBotao(int i, int j) {
        Button botao = grade[i][j];
        if (bombas[i][j]) {
            // O jogador clicou em uma bomba
            botao.setText("💣");
            botao.setDisable(true);
            cassino.diminuirSaldo(aposta); // Perde a aposta
            lblResultado.setText("Você perdeu! Custo: R$" + aposta);
            atualizarSaldo(); // Atualiza o saldo exibido
            desabilitarGrade(); // Desabilita todos os botões após perder
        } else {
            // O jogador clicou em um local seguro
            botao.setText("✅");
            botao.setDisable(true);
            double premio = aposta / 2; // Ganha 1/2 da aposta
            premioAcumulado += premio; // Acumula o prêmio
            lblResultado.setText("Você ganhou! Prêmio acumulado: R$" + premioAcumulado);
        }
    }

    private void desabilitarGrade() {
        // Desabilita todos os botões da grade
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grade[i][j].setDisable(true);
                if (bombas[i][j]) {
                    grade[i][j].setText("💣"); // Revela as bombas
                }
            }
        }
    }

    private void atualizarSaldo() {
        // Atualiza o label do saldo com o valor atual do saldo do jogador
        lblSaldo.setText("Saldo: R$" + cassino.getSaldoJogador());
    }
}