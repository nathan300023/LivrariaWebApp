package livraria.uel.model;

    import java.time.LocalDate;

    public class Carrinho {
        private LocalDate dataCriacao;
        private String clienteCpf;
        private Integer numPedido; // Usar Integer para permitir nulo

        public Carrinho(LocalDate dataCriacao,String clienteCpf) {
            this.dataCriacao = dataCriacao;
            this.clienteCpf = clienteCpf;
        }

        // Getters e Setters
        public LocalDate getDataCriacao() {
            return dataCriacao;
        }

        public void setDataCriacao(LocalDate dataCriacao) {
            this.dataCriacao = dataCriacao;
        }


        public  void setNumPedido(int numPedido) {
            this.numPedido = numPedido;
        }

        public String getClienteCpf() {
            return clienteCpf;
        }

        public void setClienteCpf(String clienteCpf) {
            this.clienteCpf = clienteCpf;
        }

        public Integer getNumPedido() {
            return numPedido;
        }

        public void setNumPedido(Integer numPedido) {
            this.numPedido = numPedido;
        }
}
