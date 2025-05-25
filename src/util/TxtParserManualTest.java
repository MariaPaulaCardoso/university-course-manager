package util;

import java.io.File;
import model.Curso;

public class TxtParserManualTest {
    public static void main(String[] args) throws Exception {
        System.out.println("[LOG] Iniciando teste manual do TxtParser...");
        File file = new File("c:/Users/unesc/Documents/java/POO/importacao.txt"); // Make sure the path is correct
        System.out.println("[LOG] Arquivo a ser importado: " + file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("[ERRO] Arquivo não encontrado!");
            return;
        }
        System.out.println("[LOG] Chamando TxtParser.parse...");
        Curso curso = TxtParser.parse(file);
        System.out.println("[LOG] Retorno do parser recebido.");

        if (curso != null) {
            System.out.println("[LOG] Curso importado com sucesso!");
            System.out.println("Nome: " + curso.getNome());
            System.out.println("Periodo Inicial: " + curso.getPeriodoInicial());
            System.out.println("Periodo Final: " + curso.getPeriodoFinal());
            System.out.println("Sequencia: " + curso.getSequenciaArquivo());
            System.out.println("Versao: " + curso.getVersaoLayout());
            System.out.println("Fases: " + curso.getFases().size());
        } else {
            System.out.println("[ERRO] Curso não encontrado!");
        }
        System.out.println("[LOG] Fim do teste manual do TxtParser.");
    }
}