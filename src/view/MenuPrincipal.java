package view;

import java.util.Scanner;

public class MenuPrincipal {
    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.exibirMenu();
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Importar arquivo de curso");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
            switch (opcao) {
                case 1:
                    // Aqui você vai chamar o método de importação (próximo passo)
                    System.out.println("[INFO] Opção de importação selecionada.");
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        scanner.close();
    }

    public class Menu{

    }
}
