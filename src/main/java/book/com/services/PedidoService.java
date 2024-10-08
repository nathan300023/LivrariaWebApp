package book.com.services;

import book.com.entities.Pedido;
import book.com.records.DadosCriacaoPedido;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class PedidoService {

    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/larissa";
    ;
    private String user = "larissa";
    private String password = "bd2024";
    private String schema = "livraria"; // Adiciona o schema

    public Pedido criarPedido(DadosCriacaoPedido dados) {
        String sql = "INSERT INTO livraria.pedidos (data_pedido, total_pedido, cliente_cpf) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(dados.dataPedido()));
            preparedStatement.setDouble(2, dados.totalPedido());
            preparedStatement.setString(3, dados.clienteCpf());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int numPedido = generatedKeys.getInt("num_pedido");
                return new Pedido(numPedido, dados.dataPedido(), dados.totalPedido(), dados.clienteCpf());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não conseguir criar o pedido
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM livraria.pedidos";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Pedido pedido = new Pedido(
                        resultSet.getInt("num_pedido"),
                        resultSet.getDate("data_pedido").toLocalDate(),
                        resultSet.getDouble("total_pedido"),
                        resultSet.getString("cliente_cpf")
                );
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public Pedido buscarPedido(int numPedido) {
        String sql = "SELECT * FROM livraria.pedidos WHERE num_pedido = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, numPedido);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Pedido(
                        resultSet.getInt("num_pedido"),
                        resultSet.getDate("data_pedido").toLocalDate(),
                        resultSet.getDouble("total_pedido"),
                        resultSet.getString("cliente_cpf")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar o pedido
    }

    public void associaLivroAoPedido(int numPedido, int codLivro, int quantidade) {
        String sql = "INSERT INTO livraria.pedidolivro (num_pedido, cod_livro, quantidade) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Define os parâmetros para o PreparedStatement
            preparedStatement.setInt(1, numPedido); // Passando diretamente o número do pedido
            preparedStatement.setInt(2, codLivro); // Código do livro
            preparedStatement.setInt(3, quantidade); // Quantidade

            // Executa a inserção
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro associado ao pedido com sucesso.");
            } else {
                System.out.println("Erro ao associar o livro ao pedido.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







}