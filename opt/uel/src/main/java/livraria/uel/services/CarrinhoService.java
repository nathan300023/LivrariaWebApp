package livraria.uel.services;

import livraria.uel.model.Book;

import livraria.uel.model.Carrinho;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class CarrinhoService {

    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/nathancs";
    private String user = "nathancs";
    private String password = "bd2024";
    private String schema = "livraria";

    public Carrinho criarCarrinho(Carrinho carrinho) {
        String sql = "INSERT INTO livraria.carrinho (data_criacao,cliente_cpf) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(carrinho.getDataCriacao()));
            preparedStatement.setString(2, carrinho.getClienteCpf());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarCarrinho(carrinho.getClienteCpf());
    }

    public Carrinho buscarCarrinho(String clienteCpf) {
        String sql = "SELECT * FROM " + schema + ".carrinho WHERE cliente_cpf = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, clienteCpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Carrinho carrinho = new Carrinho(
                        resultSet.getDate("data_criacao").toLocalDate(),
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
