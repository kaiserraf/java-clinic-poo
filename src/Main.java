import Models.Consulta;
import Models.Medico;
import Models.Paciente;
import Models.Pessoa;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        // cria o médico e os pacientes usando os construtores das
        // subclasses, que repassam o nome para Pessoa via super(...)
        Medico drCarlos = new Medico("Carlos", "12345", "Cardiologia");
        Paciente joao = new Paciente("João", 40, "111.111.111-11", false);
        Paciente maria = new Paciente("Maria", 35, "222.222.222-22", false);

        System.out.println("--- Atividade 9: polimorfismo no Main ---");
        // referências do tipo da SUPERCLASSE apontando para objetos de
        // subclasses diferentes; o método chamado é sempre exibirResumo(),
        // mas o comportamento executado depende do objeto real (Medico ou
        // Paciente), não do tipo da variável
        Pessoa pessoaA = drCarlos;
        Pessoa pessoaB = joao;

        pessoaA.exibirResumo(); // executa a versão sobrescrita em Medico
        pessoaB.exibirResumo(); // executa a versão sobrescrita em Paciente

        System.out.println("\n--- Atividade 5: sobrecarga de exibirResumo ---");
        drCarlos.exibirResumo(true);  // versão detalhada (com disponibilidade)
        joao.exibirResumo(true);      // versão detalhada (com CPF e histórico)

        System.out.println("\n--- Agendamento de consultas ---");
        // os dois pacientes vão tentar marcar no mesmo horário, de
        // propósito, para gerar conflito e testar a lista de espera
        LocalDateTime horario = LocalDateTime.of(2026, 8, 25, 14, 0);

        Consulta consultaJoao = new Consulta(joao, drCarlos, horario);
        Consulta consultaMaria = new Consulta(maria, drCarlos, horario); // mesmo médico, mesmo horário -> conflito

        GerenciadorConsultas gerenciador = new GerenciadorConsultas();

        gerenciador.agendar(consultaJoao);   // consegue, horário estava livre
        gerenciador.agendar(consultaMaria);  // não consegue, cai na lista de espera

        System.out.println("\n--- Cancelando a consulta do João ---");
        gerenciador.cancelar(consultaJoao);  // Maria deve ser remanejada automaticamente

        System.out.println("\n--- Maria realiza a consulta remanejada (via exibirResumo detalhado) ---");
        // consultaJoao foi cancelada; consultaMaria é quem assume o horário
        // liberado e passa a status AGENDADA, então é ela que pode ser
        // realizada agora
        consultaMaria.realizar();
        maria.exibirResumo(true); // totalConsultasRealizadas deve aparecer como 1
    }
}