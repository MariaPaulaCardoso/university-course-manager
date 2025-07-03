package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DisciplinaNomeResolver {
    private static Map<String, String> codigoParaNome = new HashMap<>();
    private static boolean carregado = false;

    public static void carregarCodigos(String caminhoArquivo) {
        if (carregado) return;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split("\t");
                if (partes.length >= 2 && partes[0].matches("\\d+")) {
                    codigoParaNome.put(partes[0].trim(), partes[1].trim());
                }
            }
            carregado = true;
        } catch (Exception e) {
            System.out.println("[ERRO] Falha ao carregar codigos.txt: " + e.getMessage());
        }
    }

    public static String getNomePorCodigo(String codigo) {
        return codigoParaNome.getOrDefault(codigo, "");
    }
}
