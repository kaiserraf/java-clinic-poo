package Models;

public class Paciente {

    private String nome;
    private int idade;
    private String cpf;
    private boolean possuiConvenio;
    private int totalConsultasRealizadas;

    public Paciente(String nome, int idade, String cpf, boolean possuiConvenio) {
        this.setNome(nome);
        this.setIdade(idade);
        this.setCpf(cpf);
        this.possuiConvenio = possuiConvenio;
        this.totalConsultasRealizadas = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0 || idade > 130) {
            throw new IllegalArgumentException("Idade inválida");
        }
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF inválido");
        }
        // aceita tanto "11111111111" quanto "111.111.111-11": remove
        // tudo que não é dígito antes de validar
        String somenteDigitos = cpf.replaceAll("\\D", "");
        if (!somenteDigitos.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido, deve conter 11 dígitos");
        }
        this.cpf = somenteDigitos;
    }

    public boolean isPossuiConvenio() {
        return possuiConvenio;
    }

    public void setPossuiConvenio(boolean possuiConvenio) {
        this.possuiConvenio = possuiConvenio;
    }

    public int getTotalConsultasRealizadas() {
        return totalConsultasRealizadas;
    }

    // sem setter público: esse número só sobe através deste método,
    // chamado pela própria Consulta quando ela é marcada como realizada
    public void registrarConsultaRealizada() {
        this.totalConsultasRealizadas++;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", cpf='" + cpf + '\'' +
                ", possuiConvenio=" + possuiConvenio +
                ", totalConsultasRealizadas=" + totalConsultasRealizadas +
                '}';
    }
}