package book.com.entities;

import java.time.LocalDate;

public class Pedido {
    private int numPedido;
    private LocalDate dataPedido;
    private double totalPedido;
    private String clienteCpf;

    public Pedido(int numPedido, LocalDate dataPedido, double totalPedido, String clienteCpf) {
        this.numPedido = numPedido;
        this.dataPedido = dataPedido;
        this.totalPedido = totalPedido;
        this.clienteCpf = clienteCpf;
    }

    // Getters e Setters
    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }
}
