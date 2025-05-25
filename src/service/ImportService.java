package service;

import dao.*;
import model.*;

public class ImportService {
    public void importarCurso(Curso curso) {
            CursoDAO cursoDAO = new CursoDAO();
            FaseDAO faseDAO = new FaseDAO();
            DisciplinaDAO disciplinaDAO= new DisciplinaDAO();
            ProfessorDAO professorDAO = new ProfessorDAO();

            int cursoId = cursoDAO.insertCurso(curso);

            for (Fase fase : curso.getFases()) {
                fase.setCurso(curso);
                int faseId = faseDAO.insertFase(fase);

                for (Disciplina disciplina : fase.getDisciplinas()) {
                    disciplina.setFaseId(fase);
                    int disciplinaId = disciplinaDAO.insertDisciplina(disciplina);

                    for (Professor professor : disciplina.getProfessores()) {
                        professor.setDisciplina(disciplina);
                        professor.setCurso(curso);
                        professorDAO.insertProfessor(professor);
                    }
                }
            }

            System.out.println("Importação concluída com sucesso!");
    }
    
}
