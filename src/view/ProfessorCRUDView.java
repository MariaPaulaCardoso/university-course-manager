package view;

import model.Professor;
import dao.ProfessorDAO;
import dao.CursoDAO;
import dao.FaseDAO;
import dao.DisciplinaDAO;
import model.Curso;
import model.Fase;
import model.Disciplina;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProfessorCRUDView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ProfessorDAO professorDAO = new ProfessorDAO();
    private CursoDAO cursoDAO = new CursoDAO();
    private FaseDAO faseDAO = new FaseDAO();
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

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
                int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do professor?", "Excluir", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Professor p = getProfessorFromRow(row);
                    professorDAO.deleteProfessor(p.getId());
                    loadProfessores();
                }
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
        JComboBox<Curso> cbCurso = new JComboBox<>();
        for (Curso c : cursoDAO.getAllCursos()) {
            cbCurso.addItem(c);
        }
        JComboBox<Fase> cbFase = new JComboBox<>();
        JComboBox<Disciplina> cbDisciplina = new JComboBox<>();
        // Atualiza Fases ao selecionar Curso
        cbCurso.addActionListener(e -> {
            cbFase.removeAllItems();
            Curso cursoSel = (Curso) cbCurso.getSelectedItem();
            if (cursoSel != null) {
                for (Fase f : faseDAO.getAllFases()) {
                    if (f.getCurso() != null && f.getCurso().getId() == cursoSel.getId()) {
                        cbFase.addItem(f);
                    }
                }
            }
            cbFase.setSelectedIndex(-1);
            cbDisciplina.removeAllItems();
        });
        // Atualiza Disciplinas ao selecionar Fase
        cbFase.addActionListener(e -> {
            cbDisciplina.removeAllItems();
            Fase faseSel = (Fase) cbFase.getSelectedItem();
            if (faseSel != null) {
                for (Disciplina d : disciplinaDAO.getAllDisciplinas()) {
                    if (d.getFaseId() != null && d.getFaseId().getId() == faseSel.getId()) {
                        cbDisciplina.addItem(d);
                    }
                }
            }
            cbDisciplina.setSelectedIndex(-1);
        });
        // Pré-seleção ao editar
        if (professor != null) {
            if (professor.getCurso() != null) {
                for (int i = 0; i < cbCurso.getItemCount(); i++) {
                    if (cbCurso.getItemAt(i).getId() == professor.getCurso().getId()) {
                        cbCurso.setSelectedIndex(i);
                        break;
                    }
                }
            }
            if (professor.getDisciplina() != null && professor.getDisciplina().getFaseId() != null) {
                // Força atualização dos combos
                cbCurso.setSelectedItem(professor.getDisciplina().getFaseId().getCurso());
                cbFase.setSelectedItem(professor.getDisciplina().getFaseId());
                cbDisciplina.setSelectedItem(professor.getDisciplina());
            }
        } else if (cbCurso.getItemCount() > 0) {
            cbCurso.setSelectedIndex(0);
        }
        JTextField tfNome = new JTextField();
        JTextField tfTitulo = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Curso:")); panel.add(cbCurso);
        panel.add(new JLabel("Fase:")); panel.add(cbFase);
        panel.add(new JLabel("Disciplina:")); panel.add(cbDisciplina);
        panel.add(new JLabel("Nome:")); panel.add(tfNome);
        panel.add(new JLabel("Título Docente (número):")); panel.add(tfTitulo);
        int result = JOptionPane.showConfirmDialog(this, panel, professor == null ? "Adicionar Professor" : "Editar Professor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Curso cursoSel = (Curso) cbCurso.getSelectedItem();
                Fase faseSel = (Fase) cbFase.getSelectedItem();
                Disciplina disciplinaSel = (Disciplina) cbDisciplina.getSelectedItem();
                String nome = tfNome.getText();
                int titulo = Integer.parseInt(tfTitulo.getText());
                if (professor == null) {
                    Professor novo = new Professor();
                    novo.setCurso(cursoSel);
                    novo.setDisciplina(disciplinaSel);
                    novo.setNome(nome);
                    novo.setTitulo_docente(titulo);
                    professorDAO.insertProfessor(novo);
                } else {
                    professor.setCurso(cursoSel);
                    professor.setDisciplina(disciplinaSel);
                    professor.setNome(nome);
                    professor.setTitulo_docente(titulo);
                    professorDAO.updateProfessor(professor);
                }
                loadProfessores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar professor: " + ex.getMessage());
            }
        }
    }
}
