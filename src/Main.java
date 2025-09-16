import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
public class Main { public static void main(String[] args) {
    String arquivoProcessos = "processos.txt";

                Scheduler scheduler = new Scheduler();

                try {
                    File file = new File(arquivoProcessos);
                    Scanner scanner = new Scanner(file);

                    while (scanner.hasNextLine()) {
                        String linha = scanner.nextLine();
                        String[] dados = linha.split(",");

                        // O formato do arquivo pode ser definido pelo seu grupo
                        // Exemplo: id,nome,prioridade,ciclos,recurso
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
                    System.err.println("Erro: Arquivo não encontrado.");
                    return;
                }

                System.out.println("Iniciando o escalonador");

                // Simulação dos ciclos de CPU
                int cicloAtual = 0;
                while (!scheduler.todasAsListasEstaoVazias()) {
                    System.out.println("\n Ciclo " + (++cicloAtual) + " ---");
                    scheduler.executarCicloDeCPU();
                }

                System.out.println("\nTodos os processos foram executados. \n Simulação concluída.");
            }
        }

