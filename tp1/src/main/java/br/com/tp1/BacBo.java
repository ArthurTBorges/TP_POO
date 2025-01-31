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
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class BacBo {
    private Cassino cassino;
    private App app;
    private double aposta;
    private Label lblResultado;
    private Label lblSaldo; // Novo Label para exibir o saldo

    public BacBo(Cassino cassino, App app) {
        this.cassino = cassino;
        this.app = app;
    }

    @SuppressWarnings("exports")
    public Scene getBacBoScene() {
        // Label para exibir o resultado do jogo
        lblResultado = new Label("Insira o valor da aposta e clique em Confirmar.");
        lblResultado.setFont(new Font(20));
        lblResultado.setTextFill(Color.WHITE);

        // Label para exibir o saldo do jogador
        lblSaldo = new Label("Saldo atual: R$" + cassino.getSaldoJogador());
        lblSaldo.setFont(new Font(20));
        lblSaldo.setTextFill(Color.WHITE);

        // Campo de texto para a aposta
        TextField txtAposta = new TextField();
        txtAposta.setPromptText("Valor da aposta");
        txtAposta.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-prompt-text-fill: gray;");

        // Botão para confirmar a aposta
        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16;");
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

        // Botão para voltar ao cassino
        Button btnVoltar = new Button("Voltar ao Cassino");
        btnVoltar.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16;");
        btnVoltar.setOnAction(e -> {
            app.mostrarTelaPrincipal(cassino.getJogador());
        });

        // Layout da aposta
        HBox apostaLayout = new HBox(10, txtAposta, btnConfirmar);
        apostaLayout.setAlignment(Pos.CENTER);

        // Layout principal
        VBox layout = new VBox(20, lblSaldo, lblResultado, apostaLayout, btnVoltar);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #222;");

        return new Scene(layout, 800, 400);
    }

    private void iniciarJogo() {
        // Labels para exibir os resultados dos dados
        Label dadoEsquerdo = new Label();
        Label dadoDireito = new Label();
        dadoEsquerdo.setFont(new Font(30));
        dadoEsquerdo.setTextFill(Color.WHITE);
        dadoDireito.setFont(new Font(30));
        dadoDireito.setTextFill(Color.WHITE);

        // Botões para escolher o lado
        Button btnEsquerda = new Button("Esquerda");
        Button btnDireita = new Button("Direita");
        btnEsquerda.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16;");
        btnDireita.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16;");

        // Ação do botão de escolha da esquerda
        btnEsquerda.setOnAction(e -> {
            jogarBacBo("esquerda", dadoEsquerdo, dadoDireito);
        });

        // Ação do botão de escolha da direita
        btnDireita.setOnAction(e -> {
            jogarBacBo("direita", dadoEsquerdo, dadoDireito);
        });

        // Botão para voltar à tela de apostas
        Button btnVoltarAposta = new Button("Voltar");
        btnVoltarAposta.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16;");
        btnVoltarAposta.setOnAction(e -> {
            // Recria a tela de apostas
            VBox layout = (VBox) lblResultado.getParent();
            layout.getChildren().clear();

            // Recria os componentes da tela de apostas
            TextField txtAposta = new TextField();
            txtAposta.setPromptText("Valor da aposta");
            txtAposta.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-prompt-text-fill: gray;");

            Button btnConfirmar = new Button("Confirmar");
            btnConfirmar.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16;");
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

            Button btnVoltar = new Button("Voltar ao Cassino");
            btnVoltar.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16;");
            btnVoltar.setOnAction(event -> {
                app.mostrarTelaPrincipal(cassino.getJogador());
            });

            // Layout da aposta
            HBox apostaLayout = new HBox(10, txtAposta, btnConfirmar);
            apostaLayout.setAlignment(Pos.CENTER);

            // Layout principal
            layout.getChildren().addAll(lblSaldo, lblResultado, apostaLayout, btnVoltar);
        });

        // Layout dos dados
        HBox dadosLayout = new HBox(20, dadoEsquerdo, dadoDireito);
        dadosLayout.setAlignment(Pos.CENTER);

        // Layout dos botões
        HBox botoesLayout = new HBox(20, btnEsquerda, btnDireita);
        botoesLayout.setAlignment(Pos.CENTER);

        // Atualiza a cena para mostrar o jogo
        VBox layout = (VBox) lblResultado.getParent();
        layout.getChildren().clear();
        layout.setStyle("-fx-background-color: #222;");
        layout.getChildren().addAll(lblSaldo, lblResultado, dadosLayout, botoesLayout, btnVoltarAposta);
    }

    private void jogarBacBo(String escolha, Label dadoEsquerdo, Label dadoDireito) {
        // Gera dois números aleatórios entre 1 e 6 (simulando dados)
        int resultadoEsquerdo = (int) (Math.random() * 6) + 1;
        int resultadoDireito = (int) (Math.random() * 6) + 1;

        // Atualiza os labels dos dados
        dadoEsquerdo.setText(String.valueOf(resultadoEsquerdo));
        dadoDireito.setText(String.valueOf(resultadoDireito));

        // Determina o vencedor
        String vencedor;
        if (resultadoEsquerdo > resultadoDireito) {
            vencedor = "esquerda";
        } else if (resultadoDireito > resultadoEsquerdo) {
            vencedor = "direita";
        } else {
            vencedor = "empate";
        }

        // Verifica se o jogador ganhou
        if (vencedor.equals(escolha)) {
            double premio = aposta; // Prêmio é igual à aposta
            cassino.aumentarSaldo(premio);
            lblResultado.setText("Você ganhou! Prêmio: R$" + premio);
        } else if (vencedor.equals("empate")) {
            lblResultado.setText("Empate! Nenhum prêmio ou custo.");
        } else {
            cassino.diminuirSaldo(aposta); // Perde a aposta
            lblResultado.setText("Você perdeu! Custo: R$" + aposta);
        }

        // Atualiza o saldo do jogador
        lblSaldo.setText("Saldo atual: R$" + cassino.getSaldoJogador());
    }
}