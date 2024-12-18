import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Carregar jogadores do arquivo BD.txt
        BD bancoDeDados = new BD("BD.txt");
        List<Jogador> jogadores = bancoDeDados.carregarJogadores();

        // Tentar efetuar login
        Jogador jogadorLogado = null;
        while (true) {
            System.out.print("Por favor, faça login.\nNome de usuário: ");
            String loginUser  = scanner.next();
            System.out.print("Senha: ");
            String loginSenha = scanner.next();

            // Verificar se o jogador existe
            boolean loginValido = false;
            for (Jogador jogador : jogadores) {
                if (jogador.login(loginUser , loginSenha)) {
                    jogadorLogado = jogador; // Armazena o jogador logado
                    loginValido = true;
                    break;
                }
            }

            if (loginValido) {
                break; // Sai do loop se o login for bem-sucedido
            } else {
                System.out.println("Usuário ou senha incorretos. Tente novamente.");
            }
        }

        // Inicializa os jogos
        CacaNiquel cacaNiquel = new CacaNiquel(jogadorLogado.getSaldo(), 2.0f);
        JogoDaBolinha jogoDaBolinha = new JogoDaBolinha(jogadorLogado.getSaldo());
        JogoDosDados jogoDosDados = new JogoDosDados(jogadorLogado.getSaldo());

        while (true) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Jogo da Bolinha");
            System.out.println("2. Caça-Níquel");
            System.out.println("3. Jogo dos Dados");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    // Submenu para o Jogo da Bolinha
                    while (true) {
                        jogoDaBolinha.jogar();
                        System.out.print("Deseja jogar novamente no Jogo da Bolinha? (1 - Sim, 0 - Não): ");
                        int jogarNovamente = scanner.nextInt();
                        if (jogarNovamente == 0) {
                            break; // Volta ao menu principal
                        }
                    }
                    break;
                case 2:
                    // Submenu para o Caça-Níquel
                    while (true) {
                        System.out.print("Digite o valor da aposta: R$ ");
                        float valorAposta = scanner.nextFloat();
                        cacaNiquel.fazerAposta(valorAposta);
                        System.out.print("Deseja jogar novamente no Caça-Níquel? (1 - Sim, 0 - Não): ");
                        int jogarNovamente = scanner.nextInt();
                        if (jogarNovamente == 0) {
                            break; // Volta ao menu principal
                        }
                    }
                    break;
                case 3:
                    // Submenu para o Jogo dos Dados
                    while (true) {
                        System.out.print("Adivinhe a soma dos dados (6 ou menos / 7 ou mais): ");
                        String escolhaDados = scanner.next();
                        System.out.print("Digite o valor da aposta: R$ ");
                        float valorAposta = scanner.nextFloat();
                        jogoDosDados.fazerAposta(valorAposta, escolhaDados);
                        System.out.print("Deseja jogar novamente no Jogo dos Dados? (1 - Sim, 0 - Não): ");
                        int jogarNovamente = scanner.nextInt();
                        if (jogarNovamente == 0) {
                            break; // Volta ao menu principal
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saindo do jogo. Até logo!");
                    scanner.close();
                    return; // Encerra o programa
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}