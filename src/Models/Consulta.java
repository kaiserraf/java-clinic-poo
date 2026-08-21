package Models;

import java.time.LocalDateTime;

public class Consulta {
    public Paciente paciente;
    public Medico medico;
    public LocalDateTime data;
    public String status = "Agendada";
}