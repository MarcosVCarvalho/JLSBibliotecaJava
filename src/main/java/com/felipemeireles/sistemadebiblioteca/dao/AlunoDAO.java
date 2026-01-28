package com.felipemeireles.sistemadebiblioteca.dao;

import com.felipemeireles.sistemadebiblioteca.database.ConexaoMySQL;
import com.felipemeireles.sistemadebiblioteca.controller.BibliotecaController;
import com.felipemeireles.sistemadebiblioteca.entity.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

    public void adicionarAluno(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, email, cpf, telefone) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getCpf());
            stmt.setString(4, aluno.getTelefone());

            stmt.executeUpdate();

            BibliotecaController.mostrarSucesso("Sucesso!","Aluno adicionado com sucesso!");

        } catch (SQLException e) {
            BibliotecaController.mostrarAlertar("Erro!","Algum campo preenchido errado.");
        }
    }

    public boolean arquivarAluno(int id) {
        String sql = "UPDATE alunos SET ativo = false WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Aluno> listarAlunos() {
        ObservableList<Aluno> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM alunos WHERE ativo = true ORDER BY nome";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getBoolean("ativo")
                );
                lista.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Aluno buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM alunos WHERE cpf = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getBoolean("ativo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            BibliotecaController.mostrarAlertar("Erro","Erro ao buscar aluno");
        }

        return null; // Retorna null se não encontrar o aluno
    }
    public int contarAlunosAtivos() {
        String sql = "SELECT COUNT(*) FROM alunos WHERE ativo = true";
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
