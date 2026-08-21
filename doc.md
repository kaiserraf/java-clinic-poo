# Sistema de Consultas — Clínica (Grupo 3)

Documentação do módulo de agendamento com lista de espera por médico.

## Visão geral

O sistema é composto por 3 entidades de domínio (`Paciente`, `Medico`, `Consulta`) e
uma classe de serviço (`GerenciadorConsultas`) responsável pela regra de negócio:
evitar que um médico fique com duas consultas marcadas no mesmo horário.

Quando há conflito de horário, a consulta não é perdida — ela entra numa **lista de
espera** e é remanejada automaticamente caso a vaga conflitante seja cancelada.

## Classes

### `Paciente`
Dados cadastrais do paciente: nome, idade, CPF, se possui convênio e quantas
consultas já realizou. Sem lógica própria — é uma classe de dados.

### `Medico`
Dados do médico: nome, CRM, especialidade e uma flag `disponivel` (herdada da
versão original do projeto, não usada pela lógica de agenda atual).

### `Consulta`
Liga um `Paciente` a um `Medico` num horário (`LocalDateTime`), com um `status`
que pode ser:

| Status       | Significado                                              |
|--------------|-----------------------------------------------------------|
| `Agendada`   | Consulta confirmada, ocupa o horário na agenda do médico  |
| `Em espera`  | Aguardando vaga liberada naquele horário/médico           |
| `Cancelada`  | Consulta desmarcada, não ocupa mais o horário              |

> **Por que `LocalDateTime` e não `String`?** Comparar strings de data/hora
> (`"25/08/2026 14:00"`) exige que o formato esteja sempre certinho e não permite
> operações como "esse horário é depois daquele?". `LocalDateTime` já resolve
> isso com métodos prontos (`equals`, `isBefore`, `isAfter`).

### `GerenciadorConsultas`
Classe de serviço que mantém duas listas internas:

- `consultasAgendadas: List<Consulta>` — consultas confirmadas
- `listaEspera: List<Consulta>` — consultas que bateram conflito de horário

#### Métodos

**`agendar(Consulta consulta)`**
Verifica se o médico está livre naquele horário (via `horarioDisponivel`).
- Livre → adiciona em `consultasAgendadas`.
- Ocupado → muda `status` para `"Em espera"` e adiciona em `listaEspera`.

**`horarioDisponivel(Medico medico, LocalDateTime horario)`** *(privado)*
Percorre `consultasAgendadas` e retorna `false` se encontrar uma consulta com:
- o mesmo médico (comparação por referência, `==`);
- o mesmo horário exato (`.equals()`);
- status `"Agendada"`.

**`cancelar(Consulta consulta)`**
Marca a consulta como `"Cancelada"` e remove de `consultasAgendadas`. Em seguida,
varre `listaEspera` procurando a primeira consulta que queria o mesmo médico e o
mesmo horário que acabou de abrir — se achar, promove para `"Agendada"` e move
para `consultasAgendadas`. Só a primeira da fila é remanejada por cancelamento
(`break` interrompe a busca).

## Fluxo de exemplo (`Main.java`)

1. João marca com Dr. Carlos às 25/08/2026 14:00 → **agendado**.
2. Maria tenta marcar com Dr. Carlos no mesmo horário → **conflito**, vai para a
   lista de espera.
3. Consulta do João é cancelada → Maria é **remanejada automaticamente** para o
   horário liberado.

## Limitações conhecidas / próximos passos

- O conflito hoje só é detectado quando o horário é **idêntico** (14:00 vs
  14:00). Duas consultas às 14:00 e 14:15 não são detectadas como conflito,
  mesmo que uma consulta dure mais que 15 minutos. Para tratar isso, seria
  necessário guardar a duração da consulta e comparar intervalos de tempo em
  vez de instantes exatos.
- A comparação de médico usa `==` (referência de objeto), então dois objetos
  `Medico` com os mesmos dados mas instâncias diferentes seriam tratados como
  médicos diferentes. Funciona neste projeto porque cada médico é criado uma
  única vez, mas não seria seguro num sistema com banco de dados.
- A lista de espera é atendida em ordem de inserção (FIFO), mas não há
  priorização por convênio, urgência, etc.