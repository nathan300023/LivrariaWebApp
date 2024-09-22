package book.com.services;

import book.com.entities.Client;
import book.com.records.DadosCadastroClient;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ClientService {

    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/larissa";;
    private String user = "larissa";
    private String password = "bd2024";
    private String schema = "livraria"; // Adiciona o schema

    public void inserirClient(Client client) {
        String sql = "INSERT INTO livraria.cliente (cpf, pnome, snome, email, endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getCpf());
            preparedStatement.setString(2, client.getPnome());
            preparedStatement.setString(3, client.getSnome());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getEndereco());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> listarClientes() {
        List<Client> clientes = new ArrayList<>();
        String sql = "SELECT * FROM livraria.cliente";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Client cliente = new Client(
                        resultSet.getString("cpf"),
                        resultSet.getString("pnome"),
                        resultSet.getString("snome"),
                        resultSet.getString("email"),
                        resultSet.getString("endereco")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }


    public Client buscarClient(String cpf) {
        String sql = "SELECT * FROM livraria.cliente WHERE cpf = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Client(
                        resultSet.getString("cpf"),
                        resultSet.getString("pnome"),
                        resultSet.getString("snome"),
                        resultSet.getString("email"),
                        resultSet.getString("endereco")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar
    }

    public Client atualizarClient(String cpf, DadosCadastroClient dados) {
        String sql = "UPDATE livraria.cliente SET pnome = ?, snome = ?, email = ?, endereco = ? WHERE cpf = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, dados.pnome());
            preparedStatement.setString(2, dados.snome());
            preparedStatement.setString(3, dados.email());
            preparedStatement.setString(4, dados.endereco());
            preparedStatement.setString(5, cpf);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return buscarClient(cpf); // Retorna o cliente atualizado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar
    }

    public boolean deletarClient(String cpf) {
        String sql = "DELETE FROM livraria.cliente WHERE cpf = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cpf);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Retorna true se o cliente foi deletado
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se não conseguir deletar
    }


}
