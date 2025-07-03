package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalFrame extends JFrame {
    public MenuPrincipalFrame() {
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        JButton btnVerBanco = new JButton("Ver tudo no banco");
        JButton btnImportar = new JButton("Importar arquivo de curso");
        JButton btnGerenciarCursos = new JButton("Gerenciar Cursos");
        JButton btnGerenciarDisciplinas = new JButton("Gerenciar Disciplinas");
        JButton btnGerenciarProfessores = new JButton("Gerenciar Professores");
        JButton btnGerenciarFases = new JButton("Gerenciar Fases");
        JButton btnSair = new JButton("Sair");

        btnVerBanco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre a janela para ver tudo no banco
                new view.CursoView().setVisible(true);
            }
        });

        btnImportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de importação
                new view.ImportacaoView().setVisible(true);
            }
        });

        btnGerenciarCursos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de gerenciamento de cursos
                new view.CursoCRUDView().setVisible(true);
            }
        });

        btnGerenciarDisciplinas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de gerenciamento de disciplinas
                new view.DisciplinaCRUDView().setVisible(true);
            }
        });

        btnGerenciarProfessores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de gerenciamento de professores
                new view.ProfessorCRUDView().setVisible(true);
            }
        });

        btnGerenciarFases.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de gerenciamento de fases
                new view.FaseCRUDView().setVisible(true);
            }
        });

        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(btnVerBanco);
        add(btnImportar);
        add(btnGerenciarCursos);
        add(btnGerenciarDisciplinas);
        add(btnGerenciarProfessores);
        add(btnGerenciarFases);
        add(new JLabel()); // Espaço
        add(btnSair);
    }
}
