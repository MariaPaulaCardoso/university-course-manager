package view;

import model.Curso;
import model.Fase;
import model.Disciplina;
import model.Professor;
import service.ImportService;
import util.DisciplinaNomeResolver;
import util.TxtParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class ImportacaoView extends JFrame {
    private JTextField txtArquivo;
    private JButton btnSelecionar;
    private JButton btnImportar;
    private JTable tblCurso;
    private JTable tblFases;
    private JTable tblDisciplinas;
    private JTable tblProfessores;
    private Curso curso;

    public ImportacaoView() {
        setTitle("Importação de Dados do Curso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtArquivo = new JTextField(40);
        txtArquivo.setEditable(false);
        btnSelecionar = new JButton("Selecionar arquivo");
        btnImportar = new JButton("Importar");
        btnImportar.setEnabled(false);
        pnlTopo.add(new JLabel("Arquivo:"));
        pnlTopo.add(txtArquivo);
        pnlTopo.add(btnSelecionar);
        pnlTopo.add(btnImportar);
        add(pnlTopo, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tblCurso = new JTable();
        tblFases = new JTable();
        tblDisciplinas = new JTable();
        tblProfessores = new JTable();
        tabbedPane.addTab("Curso", new JScrollPane(tblCurso));
        tabbedPane.addTab("Fases", new JScrollPane(tblFases));
        tabbedPane.addTab("Disciplinas", new JScrollPane(tblDisciplinas));
        tabbedPane.addTab("Professores", new JScrollPane(tblProfessores));
        add(tabbedPane, BorderLayout.CENTER);

        // Ações
        btnSelecionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                int result = chooser.showOpenDialog(ImportacaoView.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    txtArquivo.setText(file.getAbsolutePath());
                    carregarArquivo(file.getAbsolutePath());
                }
            }
        });

        btnImportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImportService service = new ImportService();
                    service.importarCurso(curso);
                    JOptionPane.showMessageDialog(ImportacaoView.this, "Importação realizada com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ImportacaoView.this, "Erro ao importar para o banco: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void carregarArquivo(String caminho) {
        try {
            curso = TxtParser.parse(new File(caminho));
            preencherTabelas();
            btnImportar.setEnabled(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao importar arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            btnImportar.setEnabled(false);
        }
    }

    private void preencherTabelas() {
        // Carregar nomes das disciplinas por código
        DisciplinaNomeResolver.carregarCodigos("codigos.txt");

        // Curso
        String[] colCurso = {"Nome", "Data Processamento", "Período Inicial", "Período Final", "Sequência", "Versão"};
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Object[][] dadosCurso = {
            {
                curso.getNome(),
                curso.getDataProcessamento() != null ? sdf.format(curso.getDataProcessamento()) : "",
                curso.getPeriodoInicial(),
                curso.getPeriodoFinal(),
                curso.getSequenciaArquivo(),
                curso.getVersaoLayout()
            }
        };
        tblCurso.setModel(new DefaultTableModel(dadosCurso, colCurso));

        // Fases
        List<Fase> fases = curso.getFases();
        String[] colFase = {"Fase", "Qtd Disciplinas", "Qtd Professores"};
        Object[][] dadosFase = new Object[fases.size()][3];
        for (int i = 0; i < fases.size(); i++) {
            Fase f = fases.get(i);
            dadosFase[i][0] = f.getFase();
            dadosFase[i][1] = f.getQtd_disciplinas();
            dadosFase[i][2] = f.getQtd_professores();
        }
        tblFases.setModel(new DefaultTableModel(dadosFase, colFase));

        // Disciplinas
        List<Disciplina> todasDisciplinas = new java.util.ArrayList<>();
        for (Fase f : fases) {
            todasDisciplinas.addAll(f.getDisciplinas());
        }
        String[] colDisc = {"Código", "Nome", "Dia da Semana", "Fase"};
        Object[][] dadosDisc = new Object[todasDisciplinas.size()][4];
        for (int i = 0; i < todasDisciplinas.size(); i++) {
            Disciplina d = todasDisciplinas.get(i);
            dadosDisc[i][0] = d.getCodigo();
            dadosDisc[i][1] = DisciplinaNomeResolver.getNomePorCodigo(d.getCodigo());
            // Traduzir dia da semana
            String diaSemana;
            switch (d.getDia_semana()) {
                case "01": diaSemana = "Domingo"; break;
                case "02": diaSemana = "Segunda"; break;
                case "03": diaSemana = "Terça"; break;
                case "04": diaSemana = "Quarta"; break;
                case "05": diaSemana = "Quinta"; break;
                case "06": diaSemana = "Sexta"; break;
                case "07": diaSemana = "Sábado"; break;
                default: diaSemana = "Desconhecido";
            }
            dadosDisc[i][2] = diaSemana;
            dadosDisc[i][3] = d.getFaseId() != null ? d.getFaseId().getFase() : "";
        }
        tblDisciplinas.setModel(new DefaultTableModel(dadosDisc, colDisc));

        // Professores
        List<Professor> todosProfs = new java.util.ArrayList<>();
        for (Disciplina d : todasDisciplinas) {
            todosProfs.addAll(d.getProfessores());
        }
        String[] colProf = {"Nome", "Título", "Disciplina"};
        Object[][] dadosProf = new Object[todosProfs.size()][3];
        for (int i = 0; i < todosProfs.size(); i++) {
            Professor p = todosProfs.get(i);
            dadosProf[i][0] = p.getNome();
            // Traduzir título docente
            String titulo;
            switch (String.format("%02d", p.getTitulo_docente())) {
                case "01": titulo = "Pós-graduação"; break;
                case "02": titulo = "Mestrado"; break;
                case "03": titulo = "Doutorado"; break;
                case "04": titulo = "Pós-doutorado"; break;
                default: titulo = "Desconhecido";
            }
            dadosProf[i][1] = titulo;
            dadosProf[i][2] = p.getDisciplina() != null ? p.getDisciplina().getNome() : "";
        }
        tblProfessores.setModel(new DefaultTableModel(dadosProf, colProf));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImportacaoView().setVisible(true);
        });
    }
}