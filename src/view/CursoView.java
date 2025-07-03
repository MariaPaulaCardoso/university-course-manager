package view;

import dao.CursoDAO;
import dao.FaseDAO;
import dao.DisciplinaDAO;
import dao.ProfessorDAO;
import model.Curso;
import model.Fase;
import model.Disciplina;
import model.Professor;
import util.DisciplinaNomeResolver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CursoView extends JFrame {
    private JTable tblCurso;
    private JTable tblFases;
    private JTable tblDisciplinas;
    private JTable tblProfessores;

    public CursoView() {
        setTitle("Visualização do Banco de Dados");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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

        preencherTabelas();
    }

    private void preencherTabelas() {
        DisciplinaNomeResolver.carregarCodigos("codigos.txt");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Cursos
        List<Curso> cursos = new CursoDAO().getAllCursos();
        String[] colCurso = {"Nome", "Data Processamento", "Período Inicial", "Período Final", "Sequência", "Versão"};
        Object[][] dadosCurso = new Object[cursos.size()][6];
        for (int i = 0; i < cursos.size(); i++) {
            Curso c = cursos.get(i);
            dadosCurso[i][0] = c.getNome();
            dadosCurso[i][1] = c.getDataProcessamento() != null ? sdf.format(c.getDataProcessamento()) : "";
            dadosCurso[i][2] = c.getPeriodoInicial();
            dadosCurso[i][3] = c.getPeriodoFinal();
            dadosCurso[i][4] = c.getSequenciaArquivo();
            dadosCurso[i][5] = c.getVersaoLayout();
        }
        tblCurso.setModel(new DefaultTableModel(dadosCurso, colCurso));

        // Fases
        List<Fase> fases = new FaseDAO().getAllFases();
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
        List<Disciplina> disciplinas = new DisciplinaDAO().getAllDisciplinas();
        String[] colDisc = {"Código", "Nome"};
        Object[][] dadosDisc = new Object[disciplinas.size()][2];
        for (int i = 0; i < disciplinas.size(); i++) {
            Disciplina d = disciplinas.get(i);
            dadosDisc[i][0] = d.getCodigo();
            dadosDisc[i][1] = DisciplinaNomeResolver.getNomePorCodigo(d.getCodigo());
        }
        tblDisciplinas.setModel(new DefaultTableModel(dadosDisc, colDisc));

        // Professores
        List<Professor> professores = new ProfessorDAO().getAllProfessores();
        String[] colProf = {"Nome", "Título"};
        Object[][] dadosProf = new Object[professores.size()][2];
        for (int i = 0; i < professores.size(); i++) {
            Professor p = professores.get(i);
            dadosProf[i][0] = p.getNome();
            String titulo;
            switch (p.getTitulo_docente()) {
                case 1: titulo = "Pós-graduação"; break;
                case 2: titulo = "Mestrado"; break;
                case 3: titulo = "Doutorado"; break;
                case 4: titulo = "Pós-doutorado"; break;
                default: titulo = "Desconhecido";
            }
            dadosProf[i][1] = titulo;
        }
        tblProfessores.setModel(new DefaultTableModel(dadosProf, colProf));
    }
}
