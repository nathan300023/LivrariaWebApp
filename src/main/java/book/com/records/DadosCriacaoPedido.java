package book.com.records;
import java.time.LocalDate;

public record DadosCriacaoPedido(int numPedido, LocalDate dataPedido, double totalPedido, String clienteCpf) {
}


