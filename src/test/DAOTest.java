package test;

import dao.CursoDAO;
import dao.FaseDAO;
import dao.DisciplinaDAO;
import dao.ProfessorDAO;
import model.Curso;
import model.Fase;
import model.Disciplina;
import model.Professor;
import java.util.List;

public class DAOTest {
    public static void main(String[] args) {
        // Cursos
        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> cursos = cursoDAO.getAllCursos();
        System.out.println("Cursos cadastrados:");
        for (Curso c : cursos) {
            System.out.println("ID: " + c.getId() + ", Nome: " + c.getNome());
        }
        System.out.println();

        // Fases
        FaseDAO faseDAO = new FaseDAO();
        List<Fase> fases = faseDAO.getAllFases();
        System.out.println("Fases cadastradas:");
        for (Fase f : fases) {
            System.out.println("ID: " + f.getId() + ", CursoID: " + f.getCurso().getId() + ", Fase: " + f.getFase());
        }
        System.out.println();

        // Disciplinas
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        List<Disciplina> disciplinas = disciplinaDAO.getAllDisciplinas();
        System.out.println("Disciplinas cadastradas:");
        for (Disciplina d : disciplinas) {
            System.out.println("ID: " + d.getId() + ", FaseID: " + d.getFaseId() + ", Código: " + d.getCodigo());
        }
        System.out.println();

        // Professores
        ProfessorDAO professorDAO = new ProfessorDAO();
        List<Professor> professores = professorDAO.getAllProfessores();
        System.out.println("Professores cadastrados:");
        for (Professor p : professores) {
            System.out.println("ID: " + p.getId() + ", DisciplinaID: " + p.getDisciplina().getId() + ", Nome: " + p.getNome());
        }
    }
}
