package br.com.tp1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;

public class Jogador {
    private String nome;
    private String senha;
    private double saldo;
    private static final String BD_PATH = "tp1\\src\\main\\resources\\br\\com\\tp1\\BD.txt";

    public Jogador(String nome, String senha, double saldo) {
        this.nome = nome;
        this.senha = senha;
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public static boolean usuarioExiste(String nome) {
        try (BufferedReader br = new BufferedReader(new FileReader(BD_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.equals(nome)) {
                    return true;
                }
                br.readLine(); // senha
                br.readLine(); // saldo
            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return false;
    }

    public static Jogador efetuarLogin(String nome, String senha) {
        try (BufferedReader br = new BufferedReader(new FileReader(BD_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String nomeArmazenado = linha;
                String senhaArmazenada = br.readLine();
                double saldoArmazenado = Double.parseDouble(br.readLine());

                if (nomeArmazenado.equals(nome) && senhaArmazenada.equals(senha)) {
                    return new Jogador(nome, senha, saldoArmazenado);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return null;
    }

    public void salvarJogador() {
        if (usuarioExiste(this.nome)) {
            System.out.println("Usuário já existe!");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BD_PATH, true))) {
            bw.newLine();
            bw.write(this.nome);
            bw.newLine();
            bw.write(this.senha);
            bw.newLine();
            bw.write("0.0");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public void atualizarSaldoNoArquivo(double novoSaldo) {
        try {
            File inputFile = new File(BD_PATH);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String linha;
            while ((linha = reader.readLine()) != null) {
                writer.write(linha + System.lineSeparator());

                if (linha.equals(this.nome)) {
                    writer.write(this.senha + System.lineSeparator());
                    writer.write(novoSaldo + System.lineSeparator());
                    reader.readLine();
                    reader.readLine();
                } else {
                    writer.write(reader.readLine() + System.lineSeparator());
                    writer.write(reader.readLine() + System.lineSeparator());
                }
            }

            writer.close();
            reader.close();

            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o saldo no arquivo: " + e.getMessage());
        }
    }

    @SuppressWarnings("exports")
    public static Scene getLoginScene(App app) {
        Label lblTitulo = new Label("BRAZZINO888");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        lblTitulo.setStyle("-fx-text-fill: white;");
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);
        VBox.setVgrow(lblTitulo, Priority.ALWAYS);

        Label lblNomeLogin = new Label("Nome:");
        TextField txtNomeLogin = new TextField();
        Label lblSenhaLogin = new Label("Senha:");
        PasswordField txtSenhaLogin = new PasswordField();
        Button btnLogin = new Button("Login");
        Label lblMensagem = new Label();
        lblNomeLogin.setStyle("-fx-text-fill: white;");
        lblSenhaLogin.setStyle("-fx-text-fill: white;");
        lblMensagem.setStyle("-fx-text-fill: white;");

        btnLogin.setOnAction(e -> {
            String nome = txtNomeLogin.getText().trim();
            String senha = txtSenhaLogin.getText().trim();

            if (nome.isEmpty() || senha.isEmpty()) {
                lblMensagem.setText("Preencha todos os campos!");
                return;
            }

            Jogador jogador = Jogador.efetuarLogin(nome, senha);

            if (jogador != null) {
                lblMensagem.setText("Login bem-sucedido! Saldo: " + jogador.getSaldo());
                app.mostrarTelaPrincipal(jogador);
            } else {
                lblMensagem.setText("Nome ou senha incorretos.");
            }
        });

        VBox loginBox = new VBox(10, lblNomeLogin, txtNomeLogin, lblSenhaLogin, txtSenhaLogin, btnLogin);
        loginBox.setAlignment(Pos.CENTER);

        Label lblOu = new Label("OU");
        lblOu.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        VBox ouBox = new VBox(lblOu);
        ouBox.setAlignment(Pos.CENTER);

        Label lblNomeCadastro = new Label("Nome:");
        TextField txtNomeCadastro = new TextField();
        Label lblSenhaCadastro = new Label("Senha:");
        PasswordField txtSenhaCadastro = new PasswordField();
        Button btnCadastro = new Button("Cadastrar");
        lblNomeCadastro.setStyle("-fx-text-fill: white;");
        lblSenhaCadastro.setStyle("-fx-text-fill: white;");
        lblMensagem.setStyle("-fx-text-fill: white;");

        btnCadastro.setOnAction(e -> {
            String nome = txtNomeCadastro.getText().trim();
            String senha = txtSenhaCadastro.getText().trim();

            if (nome.isEmpty() || senha.isEmpty()) {
                lblMensagem.setText("Preencha todos os campos!");
                return;
            }

            if (usuarioExiste(nome)) {
                lblMensagem.setText("Nome de usuário já existe!");
                return;
            }

            Jogador novoJogador = new Jogador(nome, senha, 0.0);
            novoJogador.salvarJogador();
            lblMensagem.setText("Cadastro realizado! Faça login.");
        });

        VBox cadastroBox = new VBox(10, lblNomeCadastro, txtNomeCadastro, lblSenhaCadastro, txtSenhaCadastro, btnCadastro);
        cadastroBox.setAlignment(Pos.CENTER);

        HBox layout = new HBox(50, loginBox, ouBox, cadastroBox);
        layout.setAlignment(Pos.CENTER);

        VBox root = new VBox(30, lblTitulo, layout, lblMensagem);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #121212; -fx-padding: 20px;");
        root.requestLayout();

        return new Scene(root, 800, 400);
    }
}
