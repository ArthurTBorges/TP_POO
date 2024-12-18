import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogador {
    private String nome;
    private String senha;
    private float saldo;

    public Jogador(String nome, String senha, float saldo) {
        this.nome = nome;
        this.senha = senha;
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public float getSaldo() {
        return saldo;
    }

    public boolean login(String nome, String senha) {
        return this.nome.equals(nome) && this.senha.equals(senha);
    }

    public void atualizarSaldo(float valor) {
        this.saldo += valor;
    }

    public static List<Jogador> carregarJogadores(String arquivo) {
        List<Jogador> jogadores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String user = linha; // Nome do usuário
                String senha = br.readLine(); // Senha
                String saldoLinha = br.readLine(); // Saldo
                if (senha == null || saldoLinha == null) {
                    System.out.println("Dados incompletos para o usuário: " + user);
                    continue; // Pula para o próximo usuário
                }
                try {
                    float saldo = Float.parseFloat(saldoLinha); // Saldo
                    jogadores.add(new Jogador(user, senha, saldo));
                } catch (NumberFormatException e) {
                    System.out.println("Saldo inválido para o usuário: " + user);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return jogadores;
    }

    public static void salvarJogador(String arquivo, Jogador jogador) {
        try (FileWriter fw = new FileWriter(arquivo, true)) {
            fw.write(jogador.getNome() + "\n");
            fw.write(jogador.getSenha() + "\n");
            fw.write(jogador.getSaldo() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o jogador: " + e.getMessage());
        }
    }

    public static void cadastrarJogador(String arquivo) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome de usuário: ");
        String nome = scanner.next();
        System.out.print("Digite a senha: ");
        String senha = scanner.next();
        System.out.print("Digite o saldo inicial: R$ ");
        float saldo = scanner.nextFloat();

        // Verificar se o usuário já existe
        List<Jogador> jogadores = carregarJogadores(arquivo);
        for (Jogador jogador : jogadores) {
            if (jogador.getNome().equals(nome)) {
                System.out.println("Nome de usuário já está em uso. Tente outro.");
                return;
            }
        }

        // Salvar o novo jogador
        Jogador novoJogador = new Jogador(nome, senha, saldo);
        salvarJogador(arquivo, novoJogador);
        System.out.println("Cadastro realizado com sucesso!");
    }
}