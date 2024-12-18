import java.util.Random;

public class CacaNiquel extends Cassino {
    private char[][] matriz;

    // Construtor
    public CacaNiquel(float saldoInicial, float apostaRetorno) {
        super(saldoInicial, apostaRetorno);
        this.matriz = new char[3][3]; // Matriz 3x3
    }

    // Método para fazer uma aposta
    public void fazerAposta(float valor) {
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
        jogar(); // Executar o jogo
    }

    // Método para jogar o caça-níquel
    private void jogar() {
        Random random = new Random();
        char[] simbolos = {'B', 'R', 'Z', '8'}; // Novos símbolos do caça-níquel

        // Preencher a matriz com símbolos aleatórios
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matriz[i][j] = simbolos[random.nextInt(simbolos.length)];
            }
        }

        // Exibir a matriz
        exibirMatriz();

        // Verificar se o jogador ganhou
        int ocorrencias = verificarGanho();
        if (ocorrencias > 0) {
            float ganho = calcularPremio(ocorrencias);
            aumentarSaldo(ganho); // Aumentar o saldo com o ganho
            System.out.printf("Você ganhou! Ganho: R$ %.2f%n", ganho);
        } else {
            System.out.println("Você perdeu. Tente novamente!");
        }
    }

    // Método para exibir a matriz
    private void exibirMatriz() {
        System.out.println("Resultado do jogo:");
        for (char[] linha : matriz) {
            for (char simbolo : linha) {
                System.out.print(simbolo + " ");
            }
            System.out.println();
        }
    }

    // Método para verificar se o jogador ganhou e contar ocorrências
    private int verificarGanho() {
        int ocorrencias = 0;

        // Verificar linhas
        for (int i = 0; i < 3; i++) {
            if (matriz[i][0] == matriz[i][1] && matriz[i][1] == matriz[i][2]) {
                ocorrencias++; // Ganhou na linha
            }
        }

        // Verificar colunas
        for (int j = 0; j < 3; j++) {
            if (matriz[0][j] == matriz[1][j] && matriz[1][j] == matriz[2][j]) {
                ocorrencias++; // Ganhou na coluna
            }
        }

        // Verificar diagonais
        if (matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2]) {
            ocorrencias++; // Ganhou na diagonal principal
        }
        if (matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0]) {
            ocorrencias++; // Ganhou na diagonal secundária
        }

        return ocorrencias; // Retorna o número de ocorrências
    }

    // Método para calcular o prêmio com base nas ocorrências
    private float calcularPremio(int ocorrencias) {
        float premio;
        switch (ocorrencias) {
            case 1:
                premio = 2 * getAposta();
                break;
            case 2:
                premio = 3 * getAposta();
                break;
            case 3:
                premio = 5 * getAposta();
                break;
            default: // 4 ou mais ocorrências
                premio = 10 * getAposta();
                break;
        }
        return premio;
    }
}