import java.util.Random;
import java.util.Scanner;

public class JogoDosDados extends Cassino {
    private Random random;

    // Construtor
    public JogoDosDados(float saldoInicial) {
        super(saldoInicial, 0); // Não usamos apostaRetorno neste jogo
        this.random = new Random();
    }

    // Método para fazer uma aposta
    public void fazerAposta(float valor, String escolha) {
        if (valor <= 0) {
            System.out.println("Aposta deve ser um valor positivo.");
            return;
        }
        if (valor > getSaldo()) {
            System.out.println("Saldo insuficiente para a aposta.");
            return;
        }

        setAposta(valor); // Usar o método da classe pai para definir a aposta
        diminuirSaldo(valor); // Diminuir o saldo pela aposta
        jogar(escolha); // Executar o jogo
    }

    // Método para jogar o Jogo dos Dados
    private void jogar(String escolha) {
        int dado1 = random.nextInt(6) + 1; // Dado 1 (1 a 6)
        int dado2 = random.nextInt(6) + 1; // Dado 2 (1 a 6)
        int soma = dado1 + dado2;

        System.out.printf("Você rolou os dados: %d e %d. Soma: %d%n", dado1, dado2, soma);

        // Verificar se o jogador ganhou
        if ((escolha.equals("6") && soma <= 6) || (escolha.equals("7") && soma >= 7)) {
            float ganho = calcularPremio();
            aumentarSaldo(ganho); // Aumentar o saldo com o ganho
            System.out.printf("Você ganhou! Ganho: R$ %.2f%n", ganho);
        } else {
            System.out.println("Você perdeu. Tente novamente!");
        }
    }

    // Método para calcular o prêmio
    private float calcularPremio() {
        return 2 * getAposta(); // Exemplo: o prêmio é o dobro da aposta
    }
}