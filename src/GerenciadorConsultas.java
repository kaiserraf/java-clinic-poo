import Models.Consulta;
import Models.Medico;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorConsultas {

    // consultas que já foram confirmadas, sem conflito de horário
    private List<Consulta> consultasAgendadas = new ArrayList<>();

    // consultas que não puderam ser agendadas por causa de conflito de horário
    // com o médico escolhido; ficam esperando aqui até alguém desmarcar
    private List<Consulta> listaEspera = new ArrayList<>();

    // tenta agendar uma consulta; se o médico já tiver outra consulta
    // marcada pro mesmo horário, ela vai pra lista de espera
    public void agendar(Consulta consulta) {
        if (horarioDisponivel(consulta.medico, consulta.data)) {
            consultasAgendadas.add(consulta);
            System.out.println("Consulta agendada: " + consulta.paciente.nome
                    + " com Dr(a). " + consulta.medico.nome + " em " + consulta.data);
        } else {
            consulta.status = "Em espera";
            listaEspera.add(consulta);
            System.out.println("Horário ocupado! " + consulta.paciente.nome
                    + " entrou na lista de espera de Dr(a). " + consulta.medico.nome);
        }
    }

    // verifica se o médico está livre naquele horário exato,
    // olhando só as consultas com status "Agendada"
    private boolean horarioDisponivel(Medico medico, LocalDateTime horario) {
        for (Consulta c : consultasAgendadas) {
            boolean mesmoMedico = c.medico == medico;
            boolean mesmoHorario = c.data.equals(horario);
            boolean estaAgendada = c.status.equals("Agendada");

            if (mesmoMedico && mesmoHorario && estaAgendada) {
                return false; // já tem alguém marcado nesse horário com esse médico
            }
        }
        return true;
    }

    // quando uma consulta é cancelada, tenta puxar o próximo da lista de espera
    // que quer o mesmo médico e o mesmo horário que acabou de abrir
    public void cancelar(Consulta consulta) {
        consulta.status = "Cancelada";
        consultasAgendadas.remove(consulta);
        System.out.println("Consulta cancelada: " + consulta.paciente.nome);

        for (Consulta espera : listaEspera) {
            boolean mesmoMedico = espera.medico == consulta.medico;
            boolean mesmoHorario = espera.data.equals(consulta.data);

            if (mesmoMedico && mesmoHorario) {
                listaEspera.remove(espera);
                espera.status = "Agendada";
                consultasAgendadas.add(espera);
                System.out.println("Vaga liberada! " + espera.paciente.nome
                        + " foi remanejado(a) da lista de espera.");
                break; // remaneja só o primeiro da fila, não todo mundo de uma vez
            }
        }
    }

    public List<Consulta> getConsultasAgendadas() {
        return consultasAgendadas;
    }

    public List<Consulta> getListaEspera() {
        return listaEspera;
    }
}

