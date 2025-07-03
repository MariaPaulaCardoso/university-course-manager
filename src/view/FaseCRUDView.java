package view;

import model.Fase;
import dao.FaseDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FaseCRUDView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private FaseDAO faseDAO = new FaseDAO();

    public FaseCRUDView() {
        setTitle("Gerenciar Fases");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Fase", "Qtd Disciplinas", "Qtd Professores"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        loadFases();

        btnAdd.addActionListener(e -> showFaseDialog(null));
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Fase f = getFaseFromRow(row);
                showFaseDialog(f);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma fase para editar.");
            }
        });
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                JOptionPane.showMessageDialog(this, "Exclusão não implementada.");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma fase para excluir.");
            }
        });
    }

    private void loadFases() {
        tableModel.setRowCount(0);
        List<Fase> fases = faseDAO.getAllFases();
        for (Fase f : fases) {
            tableModel.addRow(new Object[]{f.getId(), f.getFase(), f.getQtd_disciplinas(), f.getQtd_professores()});
        }
    }

    private Fase getFaseFromRow(int row) {
        Fase f = new Fase();
        f.setId((int) tableModel.getValueAt(row, 0));
        f.setFase((String) tableModel.getValueAt(row, 1));
        f.setQtd_disciplinas((int) tableModel.getValueAt(row, 2));
        f.setQtd_professores((int) tableModel.getValueAt(row, 3));
        return f;
    }

    private void showFaseDialog(Fase fase) {
        JTextField tfFase = new JTextField();
        JTextField tfQtdDisciplinas = new JTextField();
        JTextField tfQtdProfessores = new JTextField();
        if (fase != null) {
            tfFase.setText(fase.getFase());
            tfQtdDisciplinas.setText(String.valueOf(fase.getQtd_disciplinas()));
            tfQtdProfessores.setText(String.valueOf(fase.getQtd_professores()));
        }
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Fase:")); panel.add(tfFase);
        panel.add(new JLabel("Qtd Disciplinas:")); panel.add(tfQtdDisciplinas);
        panel.add(new JLabel("Qtd Professores:")); panel.add(tfQtdProfessores);
        int result = JOptionPane.showConfirmDialog(this, panel, fase == null ? "Adicionar Fase" : "Editar Fase", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String faseStr = tfFase.getText();
                int qtdDisciplinas = Integer.parseInt(tfQtdDisciplinas.getText());
                int qtdProfessores = Integer.parseInt(tfQtdProfessores.getText());
                if (fase == null) {
                    Fase nova = new Fase();
                    nova.setFase(faseStr);
                    nova.setQtd_disciplinas(qtdDisciplinas);
                    nova.setQtd_professores(qtdProfessores);
                    faseDAO.insertFase(nova);
                } else {
                    JOptionPane.showMessageDialog(this, "Edição não implementada.");
                }
                loadFases();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar fase: " + ex.getMessage());
            }
        }
    }
}
