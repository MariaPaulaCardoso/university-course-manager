package view;

import model.Professor;
import dao.ProfessorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProfessorCRUDView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ProfessorDAO professorDAO = new ProfessorDAO();

    public ProfessorCRUDView() {
        setTitle("Gerenciar Professores");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Título Docente"}, 0) {
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

        loadProfessores();

        btnAdd.addActionListener(e -> showProfessorDialog(null));
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Professor p = getProfessorFromRow(row);
                showProfessorDialog(p);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor para editar.");
            }
        });
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                JOptionPane.showMessageDialog(this, "Exclusão não implementada.");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor para excluir.");
            }
        });
    }

    private void loadProfessores() {
        tableModel.setRowCount(0);
        List<Professor> professores = professorDAO.getAllProfessores();
        for (Professor p : professores) {
            tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getTitulo_docente()});
        }
    }

    private Professor getProfessorFromRow(int row) {
        Professor p = new Professor();
        p.setId((int) tableModel.getValueAt(row, 0));
        p.setNome((String) tableModel.getValueAt(row, 1));
        p.setTitulo_docente((int) tableModel.getValueAt(row, 2));
        return p;
    }

    private void showProfessorDialog(Professor professor) {
        JTextField tfNome = new JTextField();
        JTextField tfTitulo = new JTextField();
        if (professor != null) {
            tfNome.setText(professor.getNome());
            tfTitulo.setText(String.valueOf(professor.getTitulo_docente()));
        }
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nome:")); panel.add(tfNome);
        panel.add(new JLabel("Título Docente (número):")); panel.add(tfTitulo);
        int result = JOptionPane.showConfirmDialog(this, panel, professor == null ? "Adicionar Professor" : "Editar Professor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nome = tfNome.getText();
                int titulo = Integer.parseInt(tfTitulo.getText());
                if (professor == null) {
                    Professor novo = new Professor();
                    novo.setNome(nome);
                    novo.setTitulo_docente(titulo);
                    professorDAO.insertProfessor(novo);
                } else {
                    JOptionPane.showMessageDialog(this, "Edição não implementada.");
                }
                loadProfessores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar professor: " + ex.getMessage());
            }
        }
    }
}
