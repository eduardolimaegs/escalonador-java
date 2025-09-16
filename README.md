# Simulador de Escalonador de Processos em Java



Um simulador de escalonador de CPU desenvolvido em Java que implementa um algoritmo de escalonamento por prioridades com uma política de anti-inanição (anti-starvation).

---

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Executar](#como-executar)
    - [Pré-requisitos](#pré-requisitos)
    - [Passo a Passo](#passo-a-passo)
- [Detalhes do Algoritmo de Escalonamento](#detalhes-do-algoritmo-de-escalonamento)
    - [Seleção por Prioridades](#seleção-por-prioridades)
    - [Execução e Quantum](#execução-e-quantum)
    - [Bloqueio por Recurso](#bloqueio-por-recurso)
    - [Política Anti-Inanição](#política-anti-inanição)
- [Formato do Arquivo de Entrada](#formato-do-arquivo-de-entrada-processostxt)

---

## Sobre o Projeto

Este projeto é uma simulação de um escalonador de processos de um sistema operacional. O objetivo é demonstrar como diferentes processos são gerenciados e recebem tempo de CPU com base em um conjunto de regras. A implementação utiliza filas (listas ligadas) para gerenciar processos em diferentes estados (pronto, bloqueado) e prioridades (alta, média, baixa).

O simulador lê uma lista de processos de um arquivo de texto, os carrega nas filas de prioridade apropriadas e executa um loop de simulação, ciclo a ciclo, mostrando qual processo está sendo executado e o estado das filas a cada passo.

---

## Funcionalidades

- **Escalonamento por Prioridades:** Os processos são divididos em três filas: alta, média e baixa prioridade.
- **Política Anti-Inanição:** Para evitar que processos de baixa prioridade nunca sejam executados (inanição), o escalonador garante que eles recebam tempo de CPU após um certo número de ciclos consecutivos de alta prioridade.
- **Simulação de Bloqueio de Recursos:** Processos que necessitam de recursos (ex: "DISCO") são movidos para uma fila de bloqueados por um ciclo antes de retornarem à fila de prontos.
- **Filas FIFO:** Dentro de uma mesma prioridade, os processos são tratados na ordem em que chegaram (First-In, First-Out).
- **Configuração via Arquivo:** Os processos a serem simulados são carregados a partir de um arquivo `processos.txt`, facilitando a criação de diferentes cenários de teste.
- **Log Detalhado:** A cada ciclo de CPU, o estado de todas as filas e a ação realizada são impressos no console, permitindo uma análise clara do comportamento do escalonador.

---


## Como Executar

### Pré-requisitos

- **Java Development Kit (JDK)**: Versão 8 ou superior instalada e configurada no seu sistema.

### Passo a Passo

1.  **Clone ou baixe os arquivos:** Certifique-se de que todos os 5 arquivos `.java` estejam no mesmo diretório.

2.  **Crie o arquivo de configuração:** No mesmo diretório, crie um arquivo chamado `processos.txt` com o conteúdo abaixo ou com os processos que você desejar.

    ```txt
    # Formato: id, nome, prioridade (1=Alta, 2=Média, 3=Baixa), ciclos, recurso (opcional)
    1, ProcessoA, 1, 3
    2, ProcessoB, 1, 4
    3, ProcessoC, 2, 5
    4, ProcessoD, 3, 6
    5, ProcessoE, 1, 2
    6, ProcessoF, 1, 3, DISCO
    7, ProcessoG, 2, 4
    8, ProcessoH, 1, 2
    9, ProcessoI, 1, 3
    10, ProcessoJ, 1, 2
    ```

3.  **Abra um terminal** ou prompt de comando na pasta do projeto.

4.  **Compile o código:** Execute o seguinte comando para compilar todos os arquivos Java.
    ```sh
    javac *.java
    ```

5.  **Execute a simulação:** Após a compilação bem-sucedida, execute o programa com o comando:
    ```sh
    java Main
    ```

O terminal começará a exibir a saída da simulação, mostrando cada ciclo de CPU e o estado das filas de processos.

---

## Detalhes do Algoritmo de Escalonamento

### Seleção por Prioridades

O critério principal para escolher o próximo processo a ser executado é a prioridade. O escalonador sempre verificará as filas na seguinte ordem:
1.  **Fila de Alta Prioridade**
2.  **Fila de Média Prioridade**
3.  **Fila de Baixa Prioridade**

Um processo de prioridade mais baixa só é executado se todas as filas de prioridade mais alta estiverem vazias.

### Execução e Quantum

Cada processo executa por um "quantum" de tempo que, nesta simulação, equivale a 1 **ciclo de CPU**. Após a execução:
- Se o processo ainda precisa de mais ciclos para terminar, ele é reinserido no final da sua respectiva fila de prioridade (comportamento similar ao Round-Robin dentro da mesma prioridade).
- Se os ciclos necessários do processo chegarem a zero, ele é considerado concluído e removido do sistema.

### Bloqueio por Recurso

Se um processo em execução necessita de um recurso (marcado como "DISCO" no arquivo de entrada) e ainda não o solicitou:
1.  O processo é movido para a `listaBloqueados`.
2.  Ele permanece bloqueado por **um ciclo completo de CPU**.
3.  No início do ciclo seguinte, ele é automaticamente "desbloqueado" e reinserido na fila de prioridade correspondente para competir novamente pelo tempo de CPU.

### Política Anti-Inanição

Para evitar que processos de média e baixa prioridade fiquem perpetuamente esperando (inanição) caso haja um fluxo constante de processos de alta prioridade, uma política de anti-inanição foi implementada:
- O escalonador mantém um contador (`contadorCiclosAltaPrioridade`).
- Este contador é incrementado toda vez que um processo de **alta prioridade** é executado.
- Se o contador atinge o valor **5**, o escalonador ignora a fila de alta prioridade e força a execução de um processo da fila de **média prioridade** (ou baixa, se a média estiver vazia).
- Após essa execução "forçada", o contador é resetado para **0**.

---

## Formato do Arquivo de Entrada (`processos.txt`)

O arquivo `processos.txt` deve seguir um formato CSV (valores separados por vírgula), onde cada linha representa um processo.

**Formato:** `id,nome,prioridade,ciclos_necessarios,recurso_opcional`

| Campo                 | Tipo    | Descrição                                                                                             |
| --------------------- | ------- | ------------------------------------------------------------------------------------------------------- |
| `id`                  | Inteiro | Identificador único do processo.                                                                        |
| `nome`                | String  | Nome do processo (ex: "ProcessoA").                                                                     |
| `prioridade`          | Inteiro | Nível de prioridade: **1** (Alta), **2** (Média), **3** (Baixa).                                          |
| `ciclos_necessarios`  | Inteiro | O número total de ciclos de CPU que o processo precisa para ser concluído.                                |
| `recurso_opcional`    | String  | (Opcional) O recurso que o processo irá solicitar. Atualmente, apenas `"DISCO"` ativa o bloqueio. |

*Linhas que começam com `#` ou linhas em branco são ignoradas.*