import java.util.Scanner;

public class Jogador extends Cassino {
    private String user; // Nome do usuário
    private String senha; // Senha do usuário
    private boolean autenticado; // Estado de autenticação

    // Construtor
    public Jogador(String user, String senha, float saldoInicial) {
        super(saldoInicial, 0); // Chama o construtor da classe pai
        this.user = user;
        this.senha = senha;
        this.autenticado = false; // Inicialmente não autenticado
    }

    // Método para efetuar login
    public boolean login(String user, String senha) {
        if (this.user.equals(user) && this.senha.equals(senha)) {
            this.autenticado = true; // Autenticação bem-sucedida
            System.out.println("Login bem-sucedido! Bem-vindo, " + this.user + "!");
            return true;
        } else {
            System.out.println("Usuário ou senha incorretos. Tente novamente.");
            return false;
        }
    }

    // Método para verificar se o jogador está autenticado
    public boolean isAutenticado() {
        return autenticado;
    }

    // Método para efetuar logout
    public void logout() {
        this.autenticado = false;
        System.out.println("Você foi desconectado.");
    }
}