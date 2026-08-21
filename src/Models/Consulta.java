package Models;

import java.time.LocalDateTime;

public class Consulta {

    // enum em vez de String solta: evita valores digitados errado
    // ("agendada", "Agendadaa", etc) e deixa explícito quais estados existem
    public enum Status {
        AGENDADA,
        EM_ESPERA,
        REALIZADA,
        CANCELADA
    }

    private Paciente paciente;
    private Medico medico;
    private LocalDateTime data;
    private Status status;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime data) {
        if (paciente == null) {
            throw new IllegalArgumentException("Consulta precisa de um paciente");
        }
        if (medico == null) {
            throw new IllegalArgumentException("Consulta precisa de um médico");
        }
        this.paciente = paciente;
        this.medico = medico;
        this.setData(data);
        this.status = Status.AGENDADA;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    // sem setStatus() público: a troca de estado só acontece por aqui,
    // então cada método já garante que a transição faz sentido

    public void entrarEmListaEspera() {
        if (status == Status.REALIZADA || status == Status.CANCELADA) {
            throw new IllegalStateException("Consulta " + status + " não pode ir para a lista de espera");
        }
        this.status = Status.EM_ESPERA;
    }

    // usado pelo GerenciadorConsultas quando uma vaga abre e a consulta
    // sai da lista de espera para ser efetivamente agendada
    public void confirmar() {
        if (status == Status.REALIZADA || status == Status.CANCELADA) {
            throw new IllegalStateException("Consulta " + status + " não pode ser confirmada");
        }
        this.status = Status.AGENDADA;
    }

    public void realizar() {
        if (status != Status.AGENDADA) {
            throw new IllegalStateException("Só é possível realizar uma consulta que está agendada");
        }
        this.status = Status.REALIZADA;
        this.paciente.registrarConsultaRealizada();
    }

    public void cancelar() {
        if (status == Status.REALIZADA) {
            throw new IllegalStateException("Não é possível cancelar uma consulta já realizada");
        }
        this.status = Status.CANCELADA;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "paciente=" + paciente.getNome() +
                ", medico=" + medico.getNome() +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}