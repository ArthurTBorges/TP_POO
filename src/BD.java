import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BD {
    private String arquivo;

    public BD(String arquivo) {
        this.arquivo = arquivo;
    }

    public List<Jogador> carregarJogadores() {
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
}