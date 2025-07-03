package view;

import model.Curso;
import dao.CursoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CursoCRUDView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private CursoDAO cursoDAO = new CursoDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public CursoCRUDView() {
        setTitle("Gerenciar Cursos");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Nome", "Data Processamento", "Período Inicial", "Período Final", "Sequência Arquivo", "Versão Layout"
        }, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        loadCursos();

        // Add action listeners
        btnAdd.addActionListener(e -> showCursoDialog(null));
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Curso curso = getCursoFromRow(row);
                showCursoDialog(curso);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um curso para editar.");
            }
        });
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este curso?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    cursoDAO.deleteCurso(id);
                    loadCursos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um curso para excluir.");
            }
        });
    }

    private void loadCursos() {
        tableModel.setRowCount(0);
        List<Curso> cursos = cursoDAO.getAllCursos();
        for (Curso c : cursos) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    sdf.format(c.getDataProcessamento()),
                    c.getPeriodoInicial(),
                    c.getPeriodoFinal(),
                    c.getSequenciaArquivo(),
                    c.getVersaoLayout()
            });
        }
    }

    private Curso getCursoFromRow(int row) {
        Curso c = new Curso();
        c.setId((int) tableModel.getValueAt(row, 0));
        c.setNome((String) tableModel.getValueAt(row, 1));
        try {
            c.setDataProcessamento(sdf.parse((String) tableModel.getValueAt(row, 2)));
        } catch (Exception ex) { c.setDataProcessamento(new Date()); }
        c.setPeriodoInicial((String) tableModel.getValueAt(row, 3));
        c.setPeriodoFinal((String) tableModel.getValueAt(row, 4));
        c.setSequenciaArquivo((int) tableModel.getValueAt(row, 5));
        c.setVersaoLayout((String) tableModel.getValueAt(row, 6));
        return c;
    }

    private void showCursoDialog(Curso curso) {
        JTextField tfNome = new JTextField();
        JTextField tfData = new JTextField();
        JTextField tfPeriodoInicial = new JTextField();
        JTextField tfPeriodoFinal = new JTextField();
        JTextField tfSequencia = new JTextField();
        JTextField tfVersao = new JTextField();

        if (curso != null) {
            tfNome.setText(curso.getNome());
            tfData.setText(sdf.format(curso.getDataProcessamento()));
            tfPeriodoInicial.setText(curso.getPeriodoInicial());
            tfPeriodoFinal.setText(curso.getPeriodoFinal());
            tfSequencia.setText(String.valueOf(curso.getSequenciaArquivo()));
            tfVersao.setText(curso.getVersaoLayout());
        }

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nome:")); panel.add(tfNome);
        panel.add(new JLabel("Data Processamento (yyyy-MM-dd):")); panel.add(tfData);
        panel.add(new JLabel("Período Inicial:")); panel.add(tfPeriodoInicial);
        panel.add(new JLabel("Período Final:")); panel.add(tfPeriodoFinal);
        panel.add(new JLabel("Sequência Arquivo:")); panel.add(tfSequencia);
        panel.add(new JLabel("Versão Layout:")); panel.add(tfVersao);

        int result = JOptionPane.showConfirmDialog(this, panel, curso == null ? "Adicionar Curso" : "Editar Curso", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nome = tfNome.getText();
                Date data = sdf.parse(tfData.getText());
                String periodoInicial = tfPeriodoInicial.getText();
                String periodoFinal = tfPeriodoFinal.getText();
                int sequencia = Integer.parseInt(tfSequencia.getText());
                String versao = tfVersao.getText();
                if (curso == null) {
                    Curso novo = new Curso(nome, data, periodoInicial, periodoFinal, sequencia, versao);
                    cursoDAO.insertCurso(novo);
                } else {
                    // Implementar update no DAO
                    JOptionPane.showMessageDialog(this, "Edição não implementada.");
                }
                loadCursos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar curso: " + ex.getMessage());
            }
        }
    }
}
