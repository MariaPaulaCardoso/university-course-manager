package view;

import model.Disciplina;
import dao.DisciplinaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DisciplinaCRUDView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    public DisciplinaCRUDView() {
        setTitle("Gerenciar Disciplinas");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Código"}, 0) {
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

        loadDisciplinas();

        btnAdd.addActionListener(e -> showDisciplinaDialog(null));
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Disciplina d = getDisciplinaFromRow(row);
                showDisciplinaDialog(d);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma disciplina para editar.");
            }
        });
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                JOptionPane.showMessageDialog(this, "Exclusão não implementada.");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma disciplina para excluir.");
            }
        });
    }

    private void loadDisciplinas() {
        tableModel.setRowCount(0);
        List<Disciplina> disciplinas = disciplinaDAO.getAllDisciplinas();
        for (Disciplina d : disciplinas) {
            tableModel.addRow(new Object[]{d.getId(), d.getCodigo()});
        }
    }

    private Disciplina getDisciplinaFromRow(int row) {
        Disciplina d = new Disciplina();
        d.setId((int) tableModel.getValueAt(row, 0));
        d.setCodigo((String) tableModel.getValueAt(row, 1));
        return d;
    }

    private void showDisciplinaDialog(Disciplina disciplina) {
        JTextField tfCodigo = new JTextField();
        if (disciplina != null) {
            tfCodigo.setText(disciplina.getCodigo());
        }
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Código:")); panel.add(tfCodigo);
        int result = JOptionPane.showConfirmDialog(this, panel, disciplina == null ? "Adicionar Disciplina" : "Editar Disciplina", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String codigo = tfCodigo.getText();
                if (disciplina == null) {
                    Disciplina nova = new Disciplina();
                    nova.setCodigo(codigo);
                    disciplinaDAO.insertDisciplina(nova);
                } else {
                    JOptionPane.showMessageDialog(this, "Edição não implementada.");
                }
                loadDisciplinas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar disciplina: " + ex.getMessage());
            }
        }
    }
}
