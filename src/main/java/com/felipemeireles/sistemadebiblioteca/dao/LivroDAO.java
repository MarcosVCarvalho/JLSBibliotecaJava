package com.felipemeireles.sistemadebiblioteca.dao;


import com.felipemeireles.sistemadebiblioteca.database.ConexaoMySQL;
import com.felipemeireles.sistemadebiblioteca.entity.Livro;
import com.felipemeireles.sistemadebiblioteca.controller.BibliotecaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivroDAO {

    public void adicionarLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, ano, quantidade, disponibilidade) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAno());
            stmt.setInt(4, livro.getQuantidade());
            stmt.setString(5, livro.getDisponibilidade());

            stmt.executeUpdate();

            BibliotecaController.mostrarSucesso("Sucesso!","Livro adicionado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean arquivarLivro(int id) {
        String sql = "UPDATE livros SET ativo = false WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Livro buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano"),
                        rs.getInt("Quantidade"),
                        rs.getBoolean("ativo")
                );
                livro.setDisponibilidade(rs.getString("disponibilidade"));
                return livro;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            BibliotecaController.mostrarAlertar("Erro","Erro ao buscar livro");
        }

        return null; // Retorna null caso não encontre
    }


    public ObservableList<Livro> listarLivros() {
        ObservableList<Livro> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM livros WHERE ativo = true ORDER BY titulo";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano"),
                        rs.getInt("quantidade"),
                        rs.getBoolean("ativo")
                );
                lista.add(livro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public int contarLivrosAtivos() {
        String sql = "SELECT COUNT(*) FROM livros WHERE ativo = true";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}