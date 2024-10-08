package livraria.uel.services;



import livraria.uel.model.CarrinhoLivro;
import livraria.uel.records.DadosCarrinhoLivro;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class CarrinhoLivroService {

    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/nathancs";
    ;
    private String user = "nathancs";
    private String password = "bd2024";
    private String schema = "livraria"; // Adiciona o schema


    public CarrinhoLivro inserirCarrinhoLivro(CarrinhoLivro carrinhoLivro) {
        String sql = "INSERT INTO " + schema + ".carrinholivro (cliente_cpf, isbn, quantidade) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, carrinhoLivro.getClienteCpf());
            preparedStatement.setInt(2, carrinhoLivro.getCodLivro());
            preparedStatement.setInt(3, carrinhoLivro.getQuantidade());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                carrinhoLivro.setCodLivro(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buscarCarrinhoLivro(carrinhoLivro.getClienteCpf(), carrinhoLivro.getCodLivro()); // Chama o método para buscar o livro pelo código
    }

    public List<CarrinhoLivro> buscarCarrinhoPorCliente(String clienteCpf) {
        List<CarrinhoLivro> itens = new ArrayList<>();
        String sql = "SELECT * FROM " + schema + ".carrinholivro WHERE cliente_cpf = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, clienteCpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CarrinhoLivro carrinhoLivro = new CarrinhoLivro(
                        resultSet.getString("cliente_cpf"),
                        resultSet.getInt("isbn"),
                        resultSet.getInt("quantidade")
                );
                itens.add(carrinhoLivro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itens;
    }


    public CarrinhoLivro buscarCarrinhoLivro(String cliente_cpf,int isbn) {
        String sql = "SELECT * FROM " + schema + ".carrinholivro WHERE  cliente_cpf = ? AND isbn = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cliente_cpf);
            preparedStatement.setInt(2, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CarrinhoLivro carrinhoLivro = new CarrinhoLivro(
                        resultSet.getString("cliente_cpf"),
                        resultSet.getInt("isbn"),
                        resultSet.getInt("quantidade")
                );

                return carrinhoLivro;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar o livro
    }


    public boolean deletarCarrinhoLivro(int isbn, String cliente_cpf) {
        String sql = "DELETE FROM " + schema + ".carrinholivro WHERE cliente_cpf = ? AND isbn = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cliente_cpf);
            preparedStatement.setInt(2, isbn);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Retorna true se algum registro foi deletado
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se não conseguir deletar
    }

}