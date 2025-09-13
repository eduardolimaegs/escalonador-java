public class Scheduler {
        private ListaDeProcesso listaAltaPrioridade;
        private ListaDeProcesso listaMediaPrioridade;
        private ListaDeProcesso listaBaixaPrioridade;
        private ListaDeProcesso listaBloqueados;
        private int contadorCiclosAltaPrioridade;

        public Scheduler() {
            this.listaAltaPrioridade = new ListaDeProcesso();
            this.listaMediaPrioridade = new ListaDeProcesso();
            this.listaBaixaPrioridade = new ListaDeProcesso();
            this.listaBloqueados = new ListaDeProcesso();
            this.contadorCiclosAltaPrioridade = 0;
        }

        public void adicionarProcesso(Processo processo) {
            if (processo.getPrioridade() == 1) {
                listaAltaPrioridade.addFinal(processo);
            } else if (processo.getPrioridade() == 2) {
                listaMediaPrioridade.addFinal(processo);
            } else {
                listaBaixaPrioridade.addFinal(processo);
            }
        }

        public void executarCicloDeCPU() {
            System.out.println("\n--- INICIANDO CICLO ---");

            if (!listaBloqueados.estaVazio()) {
                Processo processoDesbloqueado = listaBloqueados.removerProcesso();
                if (processoDesbloqueado != null) {
                    System.out.println(">>> Desbloqueando processo: " + processoDesbloqueado.getNome() + " (id: " + processoDesbloqueado.getId() + ")");
                    adicionarProcesso(processoDesbloqueado);
                }
            }

            Processo processoEmExecucao = null;

            if (contadorCiclosAltaPrioridade >= 5 && !listaMediaPrioridade.estaVazio()) {
                processoEmExecucao = listaMediaPrioridade.removerProcesso();
                contadorCiclosAltaPrioridade = 0;
                System.out.println("--- REGRA DE ANTI-INANIÇÃO ATIVADA ---");
            } else if (contadorCiclosAltaPrioridade >= 5 && !listaBaixaPrioridade.estaVazio()) {
                processoEmExecucao = listaBaixaPrioridade.removerProcesso();
                contadorCiclosAltaPrioridade = 0;
                System.out.println("--- REGRA DE ANTI-INANIÇÃO ATIVADA ---");
            } else {
                if (!listaAltaPrioridade.estaVazio()) {
                    processoEmExecucao = listaAltaPrioridade.removerProcesso();
                    contadorCiclosAltaPrioridade++;
                } else if (!listaMediaPrioridade.estaVazio()) {
                    processoEmExecucao = listaMediaPrioridade.removerProcesso();
                } else if (!listaBaixaPrioridade.estaVazio()) {
                    processoEmExecucao = listaBaixaPrioridade.removerProcesso();
                }
            }

            if (processoEmExecucao != null) {
                System.out.println(">>> Executando: " + processoEmExecucao.getNome() + " (id: " + processoEmExecucao.getId() + ")");

                if ("DISCO".equals(processoEmExecucao.getRecursosNecessarios()) && !processoEmExecucao.getSolicitouDisco()) {
                    System.out.println("Processo " + processoEmExecucao.getNome() + " bloqueado por recurso 'DISCO'.");
                    processoEmExecucao.setSolicitouDisco(true);
                    listaBloqueados.addFinal(processoEmExecucao);
                } else {
                    processoEmExecucao.decrementarCiclos();

                    if (processoEmExecucao.getCiclosNecessarios() > 0) {
                        adicionarProcesso(processoEmExecucao);
                    } else {
                        System.out.println("Processo " + processoEmExecucao.getNome() + " terminou a execução.");
                    }
                }
            } else {
                System.out.println("Nenhum processo para executar neste ciclo.");
            }

            imprimirEstadoDasListas();
        }

        private void imprimirEstadoDasListas() {
            System.out.println("--- ESTADO DAS FILAS ---");
            System.out.println("Alta Prioridade: " + listaAltaPrioridade);
            System.out.println("Média Prioridade: " + listaMediaPrioridade);
            System.out.println("Baixa Prioridade: " + listaBaixaPrioridade);
            System.out.println("Bloqueados: " + listaBloqueados);
            System.out.println("Contador de Ciclos Alta: " + contadorCiclosAltaPrioridade);
            System.out.println("------------------------");
        }
}
