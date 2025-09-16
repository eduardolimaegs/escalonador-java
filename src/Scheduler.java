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
        switch (processo.getPrioridade()) {
            case 1:
                listaAltaPrioridade.addFinal(processo);
                break;
            case 2:
                listaMediaPrioridade.addFinal(processo);
                break;
            default:
                listaBaixaPrioridade.addFinal(processo);
                break;
        }
    }

    public void executarCicloDeCPU() {
        System.out.println("\n--- INICIANDO NOVO CICLO ---");

        // 1. Desbloqueia um processo da fila de bloqueados, se houver.
        if (!listaBloqueados.estaVazio()) {
            Processo processoDesbloqueado = listaBloqueados.removerProcesso();
            if (processoDesbloqueado != null) {
                System.out.println("-> Desbloqueando processo: " + processoDesbloqueado.getNome() + " (id: " + processoDesbloqueado.getId() + ")");
                adicionarProcesso(processoDesbloqueado);
            }
        }

        Processo processoEmExecucao = null;

        // 2. Lógica de seleção de processo com política de anti-inanição.
        // Se 5 processos de alta prioridade executaram seguidamente, dá chance para os de média/baixa.
        boolean antiInanicaoAtivada = false;
        if (contadorCiclosAltaPrioridade >= 5) {
            if (!listaMediaPrioridade.estaVazio()) {
                processoEmExecucao = listaMediaPrioridade.removerProcesso();
                antiInanicaoAtivada = true;
            } else if (!listaBaixaPrioridade.estaVazio()) {
                processoEmExecucao = listaBaixaPrioridade.removerProcesso();
                antiInanicaoAtivada = true;
            }
            if(antiInanicaoAtivada) {
                System.out.println("-> ANTI-INANIÇÃO ATIVADA: Dando chance para outras prioridades.");
                contadorCiclosAltaPrioridade = 0; // Reseta o contador
            }
        }

        // 3. Se a anti-inanição não foi ativada, segue a seleção por prioridade padrão.
        if (processoEmExecucao == null) {
            if (!listaAltaPrioridade.estaVazio()) {
                processoEmExecucao = listaAltaPrioridade.removerProcesso();
            } else if (!listaMediaPrioridade.estaVazio()) {
                processoEmExecucao = listaMediaPrioridade.removerProcesso();
            } else if (!listaBaixaPrioridade.estaVazio()) {
                processoEmExecucao = listaBaixaPrioridade.removerProcesso();
            }
        }


        // 4. Executa o processo selecionado.
        if (processoEmExecucao != null) {
            // Incrementa o contador apenas se o processo executado for de alta prioridade.
            if (processoEmExecucao.getPrioridade() == 1 && !antiInanicaoAtivada) {
                contadorCiclosAltaPrioridade++;
            }

            System.out.println("Executando: " + processoEmExecucao.getNome() + " (Prioridade: " + processoEmExecucao.getPrioridade() + ")");

            // Verifica se o processo precisa de um recurso e o bloqueia.
            if ("DISCO".equals(processoEmExecucao.getRecursosNecessarios()) && !processoEmExecucao.getSolicitouDisco()) {
                System.out.println("  -> Processo " + processoEmExecucao.getNome() + " bloqueado por recurso 'DISCO'.");
                processoEmExecucao.setSolicitouDisco(true);
                listaBloqueados.addFinal(processoEmExecucao);
            } else {
                // Caso contrário, executa normalmente.
                processoEmExecucao.decrementarCiclos();

                if (processoEmExecucao.getCiclosNecessarios() > 0) {
                    System.out.println("  -> Processo " + processoEmExecucao.getNome() + " retornou à fila. Ciclos restantes: " + processoEmExecucao.getCiclosNecessarios());
                    adicionarProcesso(processoEmExecucao);
                } else {
                    System.out.println("  -> Processo " + processoEmExecucao.getNome() + " terminou a execução.");
                }
            }
        } else {
            System.out.println("Nenhum processo para executar neste ciclo.");
        }

        imprimirEstadoDasListas();
    }

    private void imprimirEstadoDasListas() {
        System.out.println("--- ESTADO DAS FILAS --- ");
        System.out.println("Alta Prioridade:  " + listaAltaPrioridade.toString());
        System.out.println("Média Prioridade: " + listaMediaPrioridade.toString());
        System.out.println("Baixa Prioridade: " + listaBaixaPrioridade.toString());
        System.out.println("Bloqueados:       " + listaBloqueados.toString());
        System.out.println("Contador de Ciclos de Alta Prioridade: " + contadorCiclosAltaPrioridade);
        System.out.println("------------------------");
    }

    public boolean todasAsListasEstaoVazias() {
        return this.listaAltaPrioridade.estaVazio() &&
                this.listaMediaPrioridade.estaVazio() &&
                this.listaBaixaPrioridade.estaVazio() &&
                this.listaBloqueados.estaVazio();
    }
}