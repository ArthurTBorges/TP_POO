import java.util.Random;
import java.util.Scanner;

public class JogoDaBolinha extends Cassino {
    private int slotComBolinha; // Slot onde a bolinha está escondida

    // Construtor
    public JogoDaBolinha(float saldoInicial) {
        super(saldoInicial, 0); // Não usamos apostaRetorno neste jogo
    }

    // Método para jogar o Jogo da Bolinha
    public void jogar() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Esconder a bolinha em um dos slots (1, 2, 3 ou 4)
        slotComBolinha = random.nextInt(4) + 1; // Gera um número entre 1 e 4
        System.out.println("\n=== Jogo da Bolinha ===");
        System.out.println("A bolinha está escondida em um dos quatro slots: 1, 2, 3 ou 4.");
        System.out.println("Tente adivinhar onde ela está!");

        // Solicitar ao jogador que adivinhe o slot
        System.out.print("Digite o número do slot que você acha que contém a bolinha: ");
        int palpite = scanner.nextInt();

        // Verificar se o palpite está correto
        if (palpite < 1 || palpite > 4) {
            System.out.println("Palpite inválido! Escolha um slot entre 1 e 4.");
        } else {
            System.out.println("Você escolheu o slot " + palpite + ".");
            if (palpite == slotComBolinha) {
                System.out.println("🎉 Parabéns! Você adivinhou corretamente! 🎉");
                aumentarSaldo(10); // Aumentar o saldo em R$ 10,00
            } else {
                System.out.println("😢 Que pena! A bolinha estava no slot " + slotComBolinha + ".");
                diminuirSaldo(5); // Diminuir o saldo em R$ 5,00
            }
        }

        // Exibir saldo após o jogo
        System.out.println("\n=== Saldo Atual ===");
        exibirSaldo();
        System.out.println("====================\n");
    }
}