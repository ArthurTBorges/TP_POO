public class Cassino {
    private float saldo;
    protected float apostaEntrada; // Tornar protegido para acesso em subclasses
    protected float apostaRetorno;  // Tornar protegido para acesso em subclasses

    // Construtor
    public Cassino(float saldoInicial, float apostaRetorno) {
        this.saldo = saldoInicial;
        this.apostaRetorno = apostaRetorno;
    }

    // Método para aumentar o saldo
    public void aumentarSaldo(float valor) {
        if (valor > 0) {
            this.saldo += valor;
            System.out.printf("Saldo aumentado em R$ %.2f%n", valor);
        } else {
            System.out.println("Valor para aumento deve ser positivo.");
        }
    }

    // Método para diminuir o saldo
    public void diminuirSaldo(float valor) {
        if (valor > 0) {
            if (valor <= saldo) {
                this.saldo -= valor;
                System.out.printf("Saldo diminuído em R$ %.2f%n", valor);
            } else {
                System.out.println("Saldo insuficiente para a diminuição.");
            }
        } else {
            System.out.println("Valor para diminuição deve ser positivo.");
        }
    }

    // Método para obter o saldo atual
    public float getSaldo() {
        return saldo;
    }

    // Método para exibir o saldo
    public void exibirSaldo() {
        System.out.printf("Saldo atual: R$ %.2f%n", saldo);
    }

    // Método para definir a aposta
    public void setAposta(float valor) {
        if (valor > 0) {
            this.apostaEntrada = valor;
        } else {
            System.out.println("Aposta deve ser um valor positivo.");
        }
    }

    // Método para obter o valor da aposta
    public float getAposta() {
        return apostaEntrada;
    }
}