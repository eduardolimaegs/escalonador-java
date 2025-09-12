public class ListaDeProcesso {
        private Node cabeca;
        private Node cauda;
        private int tamanho;

        public ListaDeProcesso(){
            this.cabeca = null;
            this.cauda = null;
            this.tamanho = 0;
        }

        public boolean estaVazio(){
            return this.cabeca == null;
        }

        public void addFinal(Processo processo){
            Node novoNo = new Node(processo);

            if(estaVazio()){
                this.cabeca = novoNo;
                this.cauda = novoNo;
            }else{
                this.cauda.setProximo(novoNo);
                this.cauda = novoNo;
            }
        }
    }


