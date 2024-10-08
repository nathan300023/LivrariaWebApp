package livraria.uel.model;

public class Cliente {
    private String cpf;
    private String pnome;
    private String snome;
    private String email;

    private String senha;
    private String endereco;

    public Cliente(String cpf, String pnome, String snome, String email,String senha, String endereco) {
        this.cpf = cpf;
        this.pnome = pnome;
        this.snome = snome;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
    }
    // Getters
    public String getCpf() {
        return cpf;
    }

    public String getPnome() {
        return pnome;
    }

    public String getSnome() {
        return snome;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getSenha(){
        return senha;
    }

    // Setters
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPnome(String pnome) {
        this.pnome = pnome;
    }

    public void setSnome(String snome) {
        this.snome = snome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
}
