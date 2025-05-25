package service;

import dao.*;
import model.*;

public class ImportService {
    public void importarCurso(Curso curso) {
        CursoDAO cursoDAO = new CursoDAO();
        FaseDAO faseDAO = new FaseDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        int cursoId = cursoDAO.insertCurso(curso);
        curso.setId(cursoId);

        for (Fase fase : curso.getFases()) {
            fase.setCurso(curso);
            int faseId = faseDAO.insertFase(fase);
            fase.setId(faseId);

            for (Disciplina disciplina : fase.getDisciplinas()) {
                disciplina.setFaseId(fase);
                int disciplinaId = disciplinaDAO.insertDisciplina(disciplina);
                disciplina.setId(disciplinaId);

                for (Professor professor : disciplina.getProfessores()) {
                    professor.setDisciplina(disciplina);
                    professor.setCurso(curso);
                    int professorId = professorDAO.insertProfessor(professor);
                    professor.setId(professorId); 
                }
            }
        }

        System.out.println("Importação concluída com sucesso!");
    }
}
