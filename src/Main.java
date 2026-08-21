import Models.Consulta;
import Models.Medico;
import Models.Paciente;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        // cria o médico
        Medico drCarlos = new Medico();
        drCarlos.nome = "Carlos";
        drCarlos.crm = "12345";
        drCarlos.especialidade = "Cardiologia";

        // cria dois pacientes
        Paciente joao = new Paciente();
        joao.nome = "João";
        joao.idade = 40;
        joao.cpf = "111.111.111-11";
        joao.possuiConvenio = false;

        Paciente maria = new Paciente();
        maria.nome = "Maria";
        maria.idade = 35;
        maria.cpf = "222.222.222-22";
        maria.possuiConvenio = false;

        // os dois vão tentar marcar no mesmo horário, de propósito, pra gerar conflito
        LocalDateTime horario = LocalDateTime.of(2026, 8, 25, 14, 0);

        Consulta consultaJoao = new Consulta();
        consultaJoao.paciente = joao;
        consultaJoao.medico = drCarlos;
        consultaJoao.data = horario;

        Consulta consultaMaria = new Consulta(); // mesmo médico, mesmo horário -> vai gerar conflito
        consultaMaria.paciente = maria;
        consultaMaria.medico = drCarlos;
        consultaMaria.data = horario;

        GerenciadorConsultas gerenciador = new GerenciadorConsultas();

        gerenciador.agendar(consultaJoao);   // consegue, horário estava livre
        gerenciador.agendar(consultaMaria);  // não consegue, cai na lista de espera

        System.out.println("\n--- Cancelando a consulta do João ---");
        gerenciador.cancelar(consultaJoao);  // Maria deve ser remanejada automaticamente
    }
}