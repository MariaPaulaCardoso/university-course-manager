package util;

import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class TxtParser {
    public static Curso parse(File file) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Curso curso = null;
        Fase currentFase = null; 
        Disciplina currentDisciplina = null;

        List<Fase> fases = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        while ((line = br.readLine()) != null){
            if (line.length() < 1) {
                System.out.println("[ERRO] Linha vazia ou muito curta encontrada, pulando...");
                continue;
            }
            int recordType = Integer.parseInt(line.substring(0, 1));

            switch(recordType) {
                case 0:
                    if (line.length() < 83) {
                        System.out.println("[ERRO] Linha de cabeçalho muito curta (esperado 83, encontrado " + line.length() + "): " + line);
                        continue;
                    }
                    String nomeCurso = line.substring(1, 51).trim();
                    Date dataProc = sdf.parse(line.substring(51, 59));
                    String periodoInicial = line.substring(59, 66).trim();
                    String periodoFinal = line.substring(66, 73).trim();
                    int sequencia = Integer.parseInt(line.substring(73, 80));
                    String versao = line.substring(80, 83);

                    curso = new Curso(nomeCurso, dataProc, periodoInicial, periodoFinal, sequencia, versao);
                    break;

                case 1:
                    if (line.length() < 12) {
                        System.out.println("[ERRO] Linha de fase muito curta (esperado 12, encontrado " + line.length() + "): " + line);
                        continue;
                    }
                    String nomeFase = line.substring(1, 8).trim();
                    int qtdDisciplinas = Integer.parseInt(line.substring(8, 10));
                    int qtdProfessores = Integer.parseInt(line.substring(10, 12));

                    currentFase = new Fase(nomeFase, qtdDisciplinas, qtdProfessores, curso);
                    fases.add(currentFase);
                    break;

                case 2: 
                    if (line.length() < 46) {
                        System.out.println("[ERRO] Linha de disciplina muito curta (esperado 46, encontrado " + line.length() + "): " + line);
                        continue;
                    }
                    int disciplinaId = Integer.parseInt(line.substring(1, 6));
                    String disciplinaNome = line.substring(6, 46).trim();
                    String codigo = ""; 
                    int cargaHoraria = 0;

                    currentDisciplina = new Disciplina(disciplinaId, currentFase, codigo, disciplinaNome, cargaHoraria);
                    if (currentFase != null) {
                        currentFase.addDisciplina(currentDisciplina);
                    }
                    break;

                case 3:
                    if (line.length() < 47) {
                        System.out.println("[ERRO] Linha de professor muito curta (esperado 47, encontrado " + line.length() + "): " + line);
                        continue;
                    }
                    String professorNome = line.substring(1, 46).trim();
                    int tituloDocente = Integer.parseInt(line.substring(46, 47));
                    Professor professor = new Professor(0, curso, currentDisciplina, professorNome, tituloDocente);
                    if (currentDisciplina != null) {
                        currentDisciplina.addProfessor(professor);
                    }
                    break;

                case 9: 
                    if (line.length() < 12) {
                        System.out.println("[ERRO] Linha de trailer muito curta (esperado 12, encontrado " + line.length() + "): " + line);
                        continue;
                    }
                    int totalRegistros = Integer.parseInt(line.substring(1, 12));
                    System.out.println("Trailer found. Total records: " + totalRegistros);
                    break;

            }
        }

        br.close();

        if (curso != null) {
             curso.setFases(fases);
        }

        return curso;
    }
}
