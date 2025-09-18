import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String arquivoProcessos = "processos.txt";
        Scheduler scheduler = new Scheduler();

        try {
            File file = new File(arquivoProcessos);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.trim().isEmpty() || linha.startsWith("#")) {
                    continue;
                }
                String[] dados = linha.split(",");

                int id = Integer.parseInt(dados[0].trim());
                String nome = dados[1].trim();
                int prioridade = Integer.parseInt(dados[2].trim());
                int ciclosNecessarios = Integer.parseInt(dados[3].trim());
                String recursosNecessarios = dados.length > 4 ? dados[4].trim() : null;

                Processo processo = new Processo(id, nome, prioridade, ciclosNecessarios, recursosNecessarios);
                scheduler.adicionarProcesso(processo);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo '" + arquivoProcessos + "' não encontrado.");
            System.err.println("Certifique-se de criar o arquivo com os processos no mesmo diretório.");
            return;
        }

        System.out.println(">>> INICIANDO O ESCALONADOR <<<");

        int cicloAtual = 0;
        while (!scheduler.todasAsListasEstaoVazias()) {
            System.out.println("\n=============== CICLO " + (++cicloAtual) + " ===============");
            scheduler.executarCicloDeCPU();

        }

        System.out.println("\n>>> TODOS OS PROCESSOS FORAM EXECUTADOS. SIMULAÇÃO CONCLUÍDA. <<<");
    }
}