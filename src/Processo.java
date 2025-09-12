public class Processo {

       private int id;
       private String nome;
       private int prioridade;
       private int ciclosNecessarios;
       private String recursosNecessarios;

       public Processo(int id, String nome, int prioridade, int ciclosNecessarios, String recursosNecessarios) {
           this.id = id;
           this.nome = nome;
           this.prioridade = prioridade;
           this.ciclosNecessarios = ciclosNecessarios;
           this.recursosNecessarios = recursosNecessarios;
       }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getCiclosNecessarios() {
        return ciclosNecessarios;
    }

    public String getRecursosNecessarios() {
        return recursosNecessarios;
    }
}
