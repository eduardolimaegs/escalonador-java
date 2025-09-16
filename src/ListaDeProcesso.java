public class ListaDeProcesso {
    private Node cabeca;
    private Node cauda;

    public ListaDeProcesso() {
        this.cabeca = null;
        this.cauda = null;
    }

    public boolean estaVazio() {
        return this.cabeca == null;
    }

    public void addFinal(Processo processo) {
        Node novoNo = new Node(processo);
        if (estaVazio()) {
            this.cabeca = novoNo;
            this.cauda = novoNo;
        } else {
            this.cauda.setProximo(novoNo);
            this.cauda = novoNo;
        }
    }

    public Processo removerProcesso() {
        if (cabeca == null) {
            return null;
        }
        Processo processoRemovido = cabeca.getProcesso();
        cabeca = cabeca.getProximo();
        if (cabeca == null) {
            cauda = null;
        }
        return processoRemovido;
    }

    @Override
    public String toString() {
        if (this.cabeca == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        Node atual = this.cabeca;
        while (atual != null) {
            sb.append(atual.getProcesso().getNome());
            if (atual.getProximo() != null) {
                sb.append(", ");
            }
            atual = atual.getProximo();
        }
        sb.append("]");
        return sb.toString();
    }
}