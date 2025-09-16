public class Processo {

       private int id;
       private String nome;
       private int prioridade;
       private int ciclosNecessarios;
       private String recursosNecessarios;
       private boolean solicitouDisco;


       public Processo(int id, String nome, int prioridade, int ciclosNecessarios, String recursosNecessarios) {
           this.id = id;
           this.nome = nome;
           this.prioridade = prioridade;
           this.ciclosNecessarios = ciclosNecessarios;
           this.recursosNecessarios = recursosNecessarios;
           this.solicitouDisco = solicitouDisco = false;
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

    public void setSolicitouDisco(boolean solicitouDisco) {
        this.solicitouDisco = solicitouDisco;
    }

    public boolean getSolicitouDisco() {
        return solicitouDisco;
    }

    public void decrementarCiclos(){
        if(this.ciclosNecessarios > 0) {
            this.ciclosNecessarios--;
        }
    }
    @Override
    public String toString(){
        return String.format("[ID: %d, Nome: %s, Prioridade: %d, Ciclos: %d]", id, nome, prioridade, ciclosNecessarios);
    }
}

