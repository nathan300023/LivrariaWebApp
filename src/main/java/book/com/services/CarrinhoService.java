package book.com.services;

import book.com.entities.Book;
import book.com.entities.Carrinho;
import book.com.entities.Pedido;
import book.com.records.DadosCriacaoCarrinho;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class CarrinhoService {

    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/larissa";
    private String user = "larissa";
    private String password = "bd2024";
    private String schema = "livraria";

    public Carrinho criarCarrinho(Carrinho carrinho) {
        String sql = "INSERT INTO livraria.carrinho (data_criacao, sessao, cliente_cpf) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(carrinho.getDataCriacao()));
            preparedStatement.setInt(2, carrinho.getSessao());
            preparedStatement.setString(3, carrinho.getClienteCpf());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarCarrinho(carrinho.getSessao(), carrinho.getClienteCpf());
    }

    public Carrinho buscarCarrinho(int sessao, String clienteCpf) {
        String sql = "SELECT * FROM " + schema + ".carrinho WHERE sessao = ? AND cliente_cpf = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, sessao);
            preparedStatement.setString(2, clienteCpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Carrinho carrinho = new Carrinho(
                        resultSet.getDate("data_criacao").toLocalDate(),
                        resultSet.getInt("sessao"),
                        resultSet.getString("cliente_cpf")
                );

                return carrinho;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar o livro
    }


}