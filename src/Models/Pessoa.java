package Models;

// Superclasse do domínio: representa o que é comum entre qualquer pessoa
// que circula pela clínica (quem atende e quem é atendido). A relação
// "é um" faz sentido nos dois sentidos: um Medico é uma Pessoa, um
// Paciente é uma Pessoa — ambos têm nome e podem se identificar perante
// o sistema, mas cada um tem dados e comportamentos próprios que não
// fazem sentido no outro (CRM não existe para paciente, CPF não existe
// para médico nesta modelagem).
//
// É abstract porque "Pessoa" sozinha não representa ninguém real da
// clínica: só existe como Medico ou como Paciente.
public abstract class Pessoa {

    private String nome;

    protected Pessoa(String nome) {
        this.setNome(nome);
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

    // Método comum a toda Pessoa: versão simples, sem parâmetros.
    // Cada subclasse sobrescreve para mostrar seus próprios dados além
    // do nome (ver @Override em Medico e Paciente).
    public void exibirResumo() {
        System.out.println(nome);
    }

    @Override
    public String toString() {
        return "Pessoa{nome='" + nome + "'}";
    }
}