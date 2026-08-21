package Models;

public class Medico {

    private String nome;
    private String crm;
    private String especialidade;
    private boolean disponivel;

    public Medico(String nome, String crm, String especialidade) {
        this.setNome(nome);
        this.setCrm(crm);
        this.setEspecialidade(especialidade);
        this.disponivel = true;
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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        if (crm == null || crm.isBlank()) {
            throw new IllegalArgumentException("CRM não pode ser vazio");
        }
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            throw new IllegalArgumentException("Especialidade não pode ser vazia");
        }
        this.especialidade = especialidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    // métodos com intenção clara em vez de um setter genérico
    public void marcarComoOcupado() {
        this.disponivel = false;
    }

    public void marcarComoDisponivel() {
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "nome='" + nome + '\'' +
                ", crm='" + crm + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", disponivel=" + disponivel +
                '}';
    }
}