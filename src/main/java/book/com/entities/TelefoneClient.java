package book.com.entities;

public class TelefoneClient {
    private String telefone;
    private String clienteCpf;

    public TelefoneClient(String telefone, String clienteCpf) {
        this.telefone = telefone;
        this.clienteCpf = clienteCpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }
}
