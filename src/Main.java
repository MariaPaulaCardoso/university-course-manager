import java.io.File;

import model.Curso;
import service.ImportService;
import util.TxtParser;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("importacao.txt");
            
            if (!file.exists()) {
                System.out.println("[ERRO] Arquivo não encontrado: " + file.getAbsolutePath());
                return;
            }
            System.out.println("[INFO] Começando a ler!");
            System.out.println("[INFO] Iniciando a leitura do arquivo: " + file.getAbsolutePath());
            Curso curso = TxtParser.parse(file);
            System.out.println("[INFO] Leitura concluída com sucesso!");

            System.out.println("\n📄 Pré-visualização:");
            System.out.println(curso); 
            System.out.print("\nDeseja importar para o banco de dados? (s/n): ");
            char escolha = (char) System.in.read();
            if (escolha != 's' && escolha != 'S') {
                System.out.println("Importação cancelada.");
                return;
            }

            // 4. Import into database
            ImportService service = new ImportService();
            service.importarCurso(curso);
            System.out.println("🎉 Dados importados com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro durante importação:");
            e.printStackTrace();
        }
    }
    
}
