package AppOutput;

import Models.Consulta;
import Models.Medico;
import Models.Paciente;
import java.util.Scanner;

public class Menu {
    public static Scanner scannIn = new Scanner(System.in);
    public static Medico doctor = new Medico();
    public static Consulta consultation = new Consulta();
    public static Paciente patient = new Paciente();

    public static void MenuMain(){
        System.out.println("SISTEMA DE CLINICA");
        System.out.println("\n");
        System.out.println("1 - NOVO MÉDICO");
        System.out.println("2 - NOVO PACIENTE");
        System.out.println("3 - NOVA CONSULTA");
        System.out.println("4 - LISTA DE ESPERA");
        System.out.println("\n");
        System.out.print("DIGITE UMA OPÇÃO:");
        int optionMenu = scannIn.nextInt();

        switch (optionMenu) {
            case 1 -> newDoctor();
            case 2 -> newPatient();
            case 3 -> newConsultation();
            case 4 -> waitingList();
            default -> throw new Error();
        }
    }

    public static void newDoctor(){
        System.out.println("NOME: ");
        doctor.nome = scannIn.nextLine();
        System.out.println("CRM: ");
        doctor.crm = scannIn.nextLine();
        System.out.println("ESPECIALIDADE: ");
        doctor.especialidade = scannIn.nextLine();
        System.out.println("\n");
        System.out.println("CADASTRADO");
    }

    public static void newPatient(){
        System.out.println("NOME: ");
        patient.nome = scannIn.nextLine();
        System.out.println("IDADE: ");
        patient.idade = scannIn.nextInt();
        System.out.println("CPF: ");
        patient.cpf = scannIn.nextLine();
        System.out.println("CONVENIADO: ");
        patient.possuiConvenio = scannIn.nextBoolean();
        System.out.println("\n");
        System.out.println("CADASTRADO");
    }

    public static void newConsultation(){
        System.out.println("ID PACIENTE: ");
        int idPatient = scannIn.nextInt();
        System.out.println("ID MEDICO: ");
        int idDoctor = scannIn.nextInt();
        System.out.println("DATA: ");
        String dateConsultation = scannIn.nextLine();
    }

    public static void waitingList(){

    }
}