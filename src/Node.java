public class Node {
    private Processo processo;
    private Node proximo;

    public Node(Processo processo) {
        this.processo = processo;
        this.proximo = null;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Node getProximo() {
        return proximo;
    }

    public void setProximo(Node proximo) {
        this.proximo = proximo;
    }
}
