package livraria.uel.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


@Service
public class RelatorioService {

    private String url = "jdbc:postgresql://sicm.dc.uel.br:5432/nathancs";
    ;
    private String user = "nathancs";
    private String password = "bd2024";
    private String schema = "livraria"; // Adiciona o schema

    public List<Map<String, String>> gerarRelatorioMensal(String mes) {

        List<Map<String, String>> relatorio = new ArrayList<>();
        String sql = "SELECT \n" +
                "    DATE_TRUNC('month', p.data_pedido) AS \"mes_venda\",\n" +
                "    SUM(p.total_pedido) AS \"total_vendas_mensais\",\n" +
                "    l.titulo AS \"livro\",\n" +
                "    SUM(pl.quantidade) AS \"total_vendido\"\n" +
                "FROM \n" +
                "    livraria.pedidos p\n" +
                "JOIN \n" +
                "    livraria.pedidolivro pl ON p.num_pedido = pl.num_pedido\n" +
                "JOIN \n" +
                "    livraria.livros l ON pl.cod_livro = l.cod_livro\n" +
                "WHERE \n" +
                "    EXTRACT(MONTH FROM p.data_pedido) = " + mes + " -- Mês \n" +
                "    AND EXTRACT(YEAR FROM p.data_pedido) = 2024 -- Ano \n" +
                "GROUP BY \n" +
                "    DATE_TRUNC('month', p.data_pedido), l.titulo\n" +
                "ORDER BY \n" +
                "    SUM(pl.quantidade) DESC;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Map<String, String> linhaRelatorio = new HashMap<>();
                linhaRelatorio.put("mes_venda", String.valueOf(resultSet.getDate("mes_venda")));
                linhaRelatorio.put("total_vendas_mensais", String.valueOf(resultSet.getBigDecimal("total_vendas_mensais")));
                linhaRelatorio.put("livro", String.valueOf(resultSet.getString("livro")));
                linhaRelatorio.put("total_vendido", String.valueOf(resultSet.getInt("total_vendido")));
                relatorio.add(linhaRelatorio);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorio;
    }


    public List<Map<String, String>> gerarRelatorioSemanal() {

        List<Map<String, String>> relatorio = new ArrayList<>();
        String sql = "SELECT \n" +
                "    DATE_TRUNC('week', p.data_pedido) AS \"semana_da_venda\",\n" +
                "    SUM(p.total_pedido) AS \"total_vendas_semanais\",\n" +
                "    l.titulo AS \"livro\",\n" +
                "    SUM(pl.quantidade) AS \"quantidade_vendida\"\n" +
                "FROM \n" +
                "    livraria.pedidos p\n" +
                "JOIN \n" +
                "    livraria.pedidolivro pl ON p.num_pedido = pl.num_pedido\n" +
                "JOIN \n" +
                "    livraria.livros l ON pl.cod_livro = l.cod_livro\n" +
                "WHERE \n" +
                "    p.data_pedido >= CURRENT_DATE - INTERVAL '7 days'  -- Últimos 7 dias\n" +
                "GROUP BY \n" +
                "    DATE_TRUNC('week', p.data_pedido), l.titulo\n" +
                "ORDER BY \n" +
                "    SUM(pl.quantidade) DESC;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Map<String, String> linhaRelatorio = new HashMap<>();
                linhaRelatorio.put("semana_da_venda", String.valueOf(resultSet.getDate("semana_da_venda")));
                linhaRelatorio.put("total_vendas_semanais", String.valueOf(resultSet.getBigDecimal("total_vendas_semanais")));
                linhaRelatorio.put("livro", String.valueOf(resultSet.getString("livro")));
                linhaRelatorio.put("quantidade_vendida", String.valueOf(resultSet.getInt("quantidade_vendida")));
                relatorio.add(linhaRelatorio);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorio;
    }



    public List<Map<String, String>> gerarRelatorioDiario(String dia, String mes, String ano) {

        List<Map<String, String>> relatorio = new ArrayList<>();
        String sql = "SELECT \n" +
                "    p.data_pedido AS \"data_da_venda\",\n" +
                "    SUM(p.total_pedido) AS \"total_vendas_diaria\",\n" +
                "    l.titulo AS \"livro\",\n" +
                "    SUM(pl.quantidade) AS \"quantidade_vendida\"\n" +
                "FROM \n" +
                "    livraria.pedidos p\n" +
                "JOIN \n" +
                "    livraria.pedidolivro pl ON p.num_pedido = pl.num_pedido\n" +
                "JOIN \n" +
                "    livraria.livros l ON pl.cod_livro = l.cod_livro\n" +
                "WHERE \n" +
                "    p.data_pedido = '" + ano + "-" + mes + "-" + dia +  "'\n" +
                "GROUP BY \n" +
                "    p.data_pedido, l.titulo\n" +
                "ORDER BY \n" +
                "    SUM(pl.quantidade) DESC;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Map<String, String> linhaRelatorio = new HashMap<>();
                linhaRelatorio.put("data_da_venda", String.valueOf(resultSet.getDate("data_da_venda")));
                linhaRelatorio.put("total_vendas_diaria", String.valueOf(resultSet.getBigDecimal("total_vendas_diaria")));
                linhaRelatorio.put("livro", String.valueOf(resultSet.getString("livro")));
                linhaRelatorio.put("quantidade_vendida", String.valueOf(resultSet.getInt("quantidade_vendida")));
                relatorio.add(linhaRelatorio);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorio;
    }


    public List<Map<String, String>> gerarRelatorioEstoque() {

        List<Map<String, String>> relatorio = new ArrayList<>();
        String sql = "SELECT \n" +
                "    l.titulo AS \"livro\",\n" +
                "    l.autor AS \"autor\",\n" +
                "    l.estoque AS \"quantidade_em_estoque\",\n" +
                "    CASE \n" +
                "        WHEN l.estoque < 10 THEN 'Baixo Estoque'\n" +
                "        ELSE 'Estoque Suficiente'\n" +
                "    END AS \"status_de_estoque\"\n" +
                "FROM \n" +
                "    livraria.livros l\n" +
                "ORDER BY \n" +
                "    l.estoque ASC;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Map<String, String> linhaRelatorio = new HashMap<>();
                linhaRelatorio.put("livro", String.valueOf(resultSet.getString("livro")));
                linhaRelatorio.put("autor", String.valueOf(resultSet.getString("autor")));
                linhaRelatorio.put("quantidade_em_estoque", String.valueOf(resultSet.getInt("quantidade_em_estoque")));
                linhaRelatorio.put("status_de_estoque", String.valueOf(resultSet.getString("status_de_estoque")));
                relatorio.add(linhaRelatorio);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorio;
    }


    public List<Map<String, String>> gerarRelatorioClientesAtivos() {

        List<Map<String, String>> relatorio = new ArrayList<>();
        String sql = "SELECT \n" +
                "    c.cpf AS \"cpf_cliente\",\n" +
                "    c.pnome || ' ' || c.snome AS \"nome_cliente\",\n" +
                "    COUNT(p.num_pedido) AS \"numero_de_compras\",\n" +
                "    SUM(p.total_pedido) AS \"valor_total_gasto\"\n" +
                "FROM \n" +
                "    livraria.cliente c\n" +
                "JOIN \n" +
                "    livraria.pedidos p ON c.cpf = p.cliente_cpf\n" +
                "GROUP BY \n" +
                "    c.cpf, c.pnome, c.snome\n" +
                "ORDER BY \n" +
                "    COUNT(p.num_pedido) DESC, SUM(p.total_pedido) DESC;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Map<String, String> linhaRelatorio = new HashMap<>();
                linhaRelatorio.put("cpf_cliente", String.valueOf(resultSet.getString("cpf_cliente")));
                linhaRelatorio.put("nome_cliente", String.valueOf(resultSet.getString("nome_cliente")));
                linhaRelatorio.put("numero_de_compras", String.valueOf(resultSet.getInt("numero_de_compras")));
                linhaRelatorio.put("valor_total_gasto", String.valueOf(resultSet.getBigDecimal("valor_total_gasto")));
                relatorio.add(linhaRelatorio);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorio;
    }



}
