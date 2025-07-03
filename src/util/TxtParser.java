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
                    // Disciplina: 2 + 5 chars (codigo) + 2 chars (dia semana)
                    if (line.length() < 8) {
                        System.out.println("[ERRO] Linha de disciplina muito curta (esperado 8, encontrado " + line.length() + "): " + line);
                        continue;
                    }
                    String codigoDisciplina = line.substring(1, 6); // 5 chars
                    String diaSemana = line.substring(6, 8); // 2 chars, keep as string
                    System.out.println("DEBUG: codigoDisciplina=" + codigoDisciplina + ", diaSemana='" + diaSemana + "'");
                    String detailsLine = br.readLine();
                    if (detailsLine == null || !detailsLine.startsWith("3")) {
                        System.out.println("[ERRO] Esperado linha de detalhes de disciplina/professor após código: " + codigoDisciplina);
                        continue;
                    }
                    String disciplinaNome = detailsLine.substring(1, 44).trim();
                    int disciplinaId = 0; // Not present, set to 0 or parse if available
                    currentDisciplina = new Disciplina(disciplinaId, currentFase, codigoDisciplina, disciplinaNome, diaSemana);
                    currentDisciplina.setDia_semana(diaSemana); // Assumes you have this setter
                    if (currentFase != null) {
                        currentFase.addDisciplina(currentDisciplina);
                    }
                    // Professor info (in same line)
                    String tituloDocenteStr = detailsLine.substring(44, 46).trim();
                    int tituloDocente = 0;
                    try {
                        tituloDocente = Integer.parseInt(tituloDocenteStr);
                    } catch (Exception e) {
                        // ignore, keep as 0
                    }
                    Professor professor = new Professor(0, curso, currentDisciplina, disciplinaNome, tituloDocente);
                    if (currentDisciplina != null) {
                        currentDisciplina.addProfessor(professor);
                    }
                    break;

                case 3:
                    // This case is not used in your file format, skip
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
