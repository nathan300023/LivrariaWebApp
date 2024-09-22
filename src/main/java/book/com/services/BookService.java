package book.com.services;

import book.com.entities.Book;
import book.com.records.DadosCadastroBook;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class BookService {

    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/larissa";;
    private String user = "larissa";
    private String password = "bd2024";
    private String schema = "livraria"; // Adiciona o schema

    public Book inserirBook(Book book) {
        String sql = "INSERT INTO " + schema + ".livros (titulo, autor, editora, preco, estoque) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitulo());
            preparedStatement.setString(2, book.getAutor());
            preparedStatement.setString(3, book.getEditora());
            preparedStatement.setDouble(4, book.getPreco());
            preparedStatement.setInt(5, book.getEstoque());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setCodLivro(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buscarBook(book.getCodLivro()); // Chama o método para buscar o livro pelo código
    }

    public Book buscarBook(int cod_livro) {
        String sql = "SELECT * FROM " + schema + ".livros WHERE cod_livro = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cod_livro);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book livro = new Book(
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getString("editora"),
                        resultSet.getDouble("preco"),
                        resultSet.getInt("estoque")
                );
                livro.setCodLivro(resultSet.getInt("cod_livro")); // Presumindo que você tem um campo para o ID
                return livro;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar o livro
    }


    public boolean deletarLivro(int cod_livro) {
        String sql = "DELETE FROM " + schema + ".livros WHERE cod_livro = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cod_livro);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Retorna true se algum registro foi deletado
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se não conseguir deletar
    }


    public List<Book> listarLivros() {
        List<Book> livros = new ArrayList<>();
        String sql = "SELECT * FROM " + schema + ".livros";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Book livro = new Book(
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getString("editora"),
                        resultSet.getDouble("preco"),
                        resultSet.getInt("estoque")
                );
                livro.setCodLivro(resultSet.getInt("cod_livro")); // Presumindo que tem um ID
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public Book alterarLivro(int cod_livro, DadosCadastroBook dados) {
        String sql = "UPDATE " + schema + ".livros SET titulo = ?, autor = ?, editora = ?, preco = ?, estoque = ? WHERE cod_livro = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, dados.titulo());
            preparedStatement.setString(2, dados.autor());
            preparedStatement.setString(3, dados.editora());
            preparedStatement.setDouble(4, dados.preco());
            preparedStatement.setInt(5, dados.estoque());
            preparedStatement.setInt(6, cod_livro);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return buscarBook(cod_livro); // Retorna o livro atualizado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar o livro
    }


}
