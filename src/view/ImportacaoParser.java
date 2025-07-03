package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Curso;
import model.Fase;
import model.Disciplina;
import model.Professor;

public class ImportacaoParser {
    public static Curso parse(String caminhoArquivo) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        String linha;
        Curso curso = null;
        List<Fase> fases = new ArrayList<>();
        Fase faseAtual = null;
        Disciplina disciplinaAtual = null;
        int linhasLidas = 0;
        int linhasTrailer = 0;
        int linhaNum = 0;
        while ((linha = br.readLine()) != null) {
            linhaNum++;
            linha = linha.trim();
            if (linha.isEmpty()) continue;
            char tipo = linha.charAt(0);
            try {
                switch (tipo) {
                    case '0': // Header - Curso
                        if (linha.length() < 55) throw new RuntimeException("Linha " + linhaNum + ": Header muito curta");
                        curso = parseCurso(linha);
                        break;
                    case '1': // Fase
                        if (linha.length() < 15) throw new RuntimeException("Linha " + linhaNum + ": Fase muito curta");
                        faseAtual = parseFase(linha, curso);
                        fases.add(faseAtual);
                        break;
                    case '2': // Disciplina
                        if (linha.length() < 44) throw new RuntimeException("Linha " + linhaNum + ": Disciplina muito curta");
                        disciplinaAtual = parseDisciplina(linha, faseAtual);
                        if (faseAtual != null) faseAtual.addDisciplina(disciplinaAtual);
                        break;
                    case '3': // Professor
                        if (linha.length() < 42) throw new RuntimeException("Linha " + linhaNum + ": Professor muito curta");
                        Professor prof = parseProfessor(linha, curso, disciplinaAtual);
                        if (disciplinaAtual != null) disciplinaAtual.addProfessor(prof);
                        break;
                    case '9': // Trailer
                        linhasTrailer = Integer.parseInt(linha.substring(1).trim());
                        break;
                    default:
                        // Ignorar linhas desconhecidas
                }
            } catch (Exception ex) {
                throw new RuntimeException("Erro ao processar linha " + linhaNum + ": '" + linha + "'\n" + ex.getMessage(), ex);
            }
            linhasLidas++;
        }
        br.close();
        if (curso != null) {
            curso.setFases(fases);
        }
        // Validação do trailer
        if (linhasTrailer != linhasLidas) {
            throw new RuntimeException("Trailer não confere: esperado " + linhasTrailer + ", lido " + linhasLidas);
        }
        return curso;
    }

    private static Curso parseCurso(String linha) throws ParseException {
        // Exemplo: 0NOME DO CURSO   20230101 20231231 001 1.0
        String nome = linha.substring(1, 31).trim();
        String dataStr = linha.substring(31, 39).trim();
        String periodoInicial = linha.substring(39, 45).trim();
        String periodoFinal = linha.substring(45, 51).trim();
        int sequencia = Integer.parseInt(linha.substring(51, 54).trim());
        String versao = linha.substring(54).trim();
        Date data = new SimpleDateFormat("yyyyMMdd").parse(dataStr);
        return new Curso(nome, data, periodoInicial, periodoFinal, sequencia, versao);
    }

    private static Fase parseFase(String linha, Curso curso) {
        // Exemplo: 1FASE 01 03 02
        String fase = linha.substring(1, 11).trim();
        int qtdDisciplinas = Integer.parseInt(linha.substring(11, 13).trim());
        int qtdProfessores = Integer.parseInt(linha.substring(13, 15).trim());
        return new Fase(fase, qtdDisciplinas, qtdProfessores, curso);
    }

    private static Disciplina parseDisciplina(String linha, Fase fase) {
        // Exemplo: 2CODIGO123NOME DA DISCIPLINA   060
        String codigo = linha.substring(1, 11).trim();
        String nome = linha.substring(11, 41).trim();
        int cargaHoraria = Integer.parseInt(linha.substring(41, 44).trim());
        Disciplina d = new Disciplina();
        d.setCodigo(codigo);
        d.setNome(nome);
        d.setCarga_horaria(cargaHoraria);
        d.setFaseId(fase);
        return d;
    }

    private static Professor parseProfessor(String linha, Curso curso, Disciplina disciplina) {
        // Exemplo: 3NOME DO PROFESSOR     1
        String nome = linha.substring(1, 41).trim();
        int titulo = Integer.parseInt(linha.substring(41, 42).trim());
        Professor p = new Professor();
        p.setNome(nome);
        p.setTitulo_docente(titulo);
        p.setCurso(curso);
        p.setDisciplina(disciplina);
        return p;
    }
} 