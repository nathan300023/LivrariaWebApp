package book.com.services;

import book.com.entities.TelefoneClient;
import book.com.records.DadosTelefonesClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class TelefoneClientService {
    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/larissa";
    ;
    private String user = "larissa";
    private String password = "bd2024";
    private String schema = "livraria"; // Adiciona o schema


    public void inserirTelefoneClient(TelefoneClient telefones){
        String sql = "INSERT INTO" + schema + ".telefones(num_telefone,cliente_cpf) VALUES(?,?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1,telefones.getTelefone());
                preparedStatement.setString(2, telefones.getClienteCpf());
                preparedStatement.executeUpdate();

                }
                catch (SQLException e) {
                    e.printStackTrace();
        }
    }

    public List<TelefoneClient> listarTelefones(String cpf) {
        List<TelefoneClient> telefones = new ArrayList<>();
        String sql = "SELECT * FROM livraria.telefones WHERE cliente_cpf = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                TelefoneClient telefone = new TelefoneClient(
                        resultSet.getString("num_telefone"),
                        resultSet.getString("cliente_cpf")
                );
                telefones.add(telefone);
            }
        } catch (SQLException e){
            e.printStackTrace();;
            }
        return telefones;

        }

        public TelefoneClient buscarTelefone(String num_telefone,String cliente_cpf){
            String sql = "SELECT * FROM livraria.telefones WHERE num_telefone = ? AND cliente_cpf = ?";
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, num_telefone);
                preparedStatement.setString(2, cliente_cpf);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    TelefoneClient telefoneClient = new TelefoneClient(
                            resultSet.getString("num_telefone"),
                            resultSet.getString("cliente_cpf")
                    );
                    return telefoneClient;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
            }


    public TelefoneClient atualizarTelefone(String num_telefone, String cliente_cpf, DadosTelefonesClient dados) {
        String sql = "UPDATE livraria.telefones SET num_telefone = ? , cliente_cpf = ? WHERE num_telefone = ? AND cliente_cpf = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            preparedStatement.setString(1, dados.numTelefone());
            preparedStatement.setString(2, dados.clienteCpf());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return buscarTelefone(num_telefone, cliente_cpf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletarTelefoneClient(String num_telefone, String cliente_cpf) {
        String sql = "DELETE FROM livraria.telefones WHERE num_telefone = ? AND cliente_cpf = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, num_telefone);
            preparedStatement.setString(2, cliente_cpf);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    }

