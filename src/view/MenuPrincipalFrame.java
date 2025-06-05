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
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnImportar = new JButton("Importar arquivo de curso");
        JButton btnSair = new JButton("Sair");

        btnImportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aqui você vai abrir a tela de importação (próximo passo)
                JOptionPane.showMessageDialog(MenuPrincipalFrame.this, "[INFO] Opção de importação selecionada.");
            }
        });

        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(btnImportar);
        add(new JLabel()); // Espaço
        add(btnSair);
    }
}
