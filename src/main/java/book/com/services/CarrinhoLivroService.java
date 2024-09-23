package book.com.services;

import book.com.entities.Book;
import book.com.entities.CarrinhoLivro;
import book.com.records.DadosCarrinhoLivro;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class CarrinhoLivroService {

    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/larissa";
    ;
    private String user = "larissa";
    private String password = "bd2024";
    private String schema = "livraria"; // Adiciona o schema


    public CarrinhoLivro inserirCarrinhoLivro(CarrinhoLivro carrinhoLivro) {
        String sql = "INSERT INTO " + schema + ".carrinholivro (carrinho_sessao, cliente_cpf, cod_livro, quantidade) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, carrinhoLivro.getCarrinhoSessao());
            preparedStatement.setString(2, carrinhoLivro.getClienteCpf());
            preparedStatement.setInt(3, carrinhoLivro.getCodLivro());
            preparedStatement.setInt(4, carrinhoLivro.getQuantidade());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                carrinhoLivro.setCodLivro(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buscarCarrinhoLivro(carrinhoLivro.getCarrinhoSessao(), carrinhoLivro.getClienteCpf(), carrinhoLivro.getCodLivro()); // Chama o método para buscar o livro pelo código
    }

    public CarrinhoLivro buscarCarrinhoLivro(int carrinho_sessao, String cliente_cpf,int cod_livro) {
        String sql = "SELECT * FROM " + schema + ".carrinholivro WHERE carrinho_sessao = ? AND cliente_cpf = ? AND cod_livro = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, carrinho_sessao);
            preparedStatement.setString(2, cliente_cpf);
            preparedStatement.setInt(3, cod_livro);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CarrinhoLivro carrinhoLivro = new CarrinhoLivro(
                        resultSet.getInt("carrinho_sessao"),
                        resultSet.getString("cliente_cpf"),
                        resultSet.getInt("cod_livro"),
                        resultSet.getInt("quantidade")
                );

                return carrinhoLivro;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar o livro
    }


    public boolean deletarCarrinhoLivro(int cod_livro, String cliente_cpf, int carrinho_sessao) {
        String sql = "DELETE FROM " + schema + ".carrinholivro WHERE carrinho_sessao = ? AND cliente_cpf = ? AND cod_livro = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, carrinho_sessao);
            preparedStatement.setString(2, cliente_cpf);
            preparedStatement.setInt(3, cod_livro);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Retorna true se algum registro foi deletado
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se não conseguir deletar
    }

}