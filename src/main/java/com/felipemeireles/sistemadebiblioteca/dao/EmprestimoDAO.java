package com.felipemeireles.sistemadebiblioteca.dao;

import com.felipemeireles.sistemadebiblioteca.database.ConexaoMySQL;
import com.felipemeireles.sistemadebiblioteca.controller.BibliotecaController;
import com.felipemeireles.sistemadebiblioteca.entity.Emprestimo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

     // Adiciona um novo empréstimo ao banco.
     // Retorna true se inseriu com sucesso, false caso contrário.

    public boolean adicionarEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimos (aluno_cpf, aluno_nome, livro_id, livro_titulo, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emprestimo.getAlunoCpf());
            stmt.setString(2, emprestimo.getAlunoNome());
            stmt.setInt(3, emprestimo.getLivroId());
            stmt.setString(4, emprestimo.getLivroTitulo());

            LocalDate dataEmp = emprestimo.getDataEmprestimo();
            LocalDate dataDev = emprestimo.getDataDevolucao();

            if (dataEmp != null) {
                stmt.setDate(5, Date.valueOf(dataEmp));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            if (dataDev != null) {
                stmt.setDate(6, Date.valueOf(dataDev));
            } else {
                stmt.setNull(6, Types.DATE);
            }

            stmt.setString(7, emprestimo.getStatus());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            BibliotecaController.mostrarAlertar("Erro ao adicionar empréstimo", e.getMessage());
            return false;
        }
    }

    // Retorna todos os empréstimos cujo status é 'EM_ABERTO'.
    public List<Emprestimo> listarEmprestimosEmAberto() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE status = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "EM_ABERTO");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Emprestimo e = new Emprestimo();
                    e.setId(rs.getInt("id"));
                    e.setAlunoCpf(rs.getString("aluno_cpf"));
                    e.setAlunoNome(rs.getString("aluno_nome"));
                    e.setLivroId(rs.getInt("livro_id"));
                    e.setLivroTitulo(rs.getString("livro_titulo"));

                    Date dEmp = rs.getDate("data_emprestimo");
                    if (dEmp != null) {
                        e.setDataEmprestimo(dEmp.toLocalDate());
                    }

                    Date dDev = rs.getDate("data_devolucao");
                    if (dDev != null) {
                        e.setDataDevolucao(dDev.toLocalDate());
                    }

                    e.setStatus(rs.getString("status"));
                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            BibliotecaController.mostrarAlertar("Erro ao listar empréstimos", e.getMessage());
        }
        return lista;
    }

    public List<Emprestimo> listarEmprestimosFechados() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE status != ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Concluido");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Emprestimo e = new Emprestimo();
                    e.setId(rs.getInt("id"));
                    e.setAlunoCpf(rs.getString("aluno_cpf"));
                    e.setAlunoNome(rs.getString("aluno_nome"));
                    e.setLivroId(rs.getInt("livro_id"));
                    e.setLivroTitulo(rs.getString("livro_titulo"));

                    Date dEmp = rs.getDate("data_emprestimo");
                    if (dEmp != null) {
                        e.setDataEmprestimo(dEmp.toLocalDate());
                    }

                    Date dDev = rs.getDate("data_devolucao");
                    if (dDev != null) {
                        e.setDataDevolucao(dDev.toLocalDate());
                    }

                    e.setStatus(rs.getString("status"));
                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            BibliotecaController.mostrarAlertar("Erro ao listar empréstimos", e.getMessage());
        }
        return lista;
    }

    /**
     * Marca o empréstimo como devolvido (status = 'DEVOLVIDO').
     * Retorna true se atualizou ao menos uma linha.
     */
    public boolean devolverLivro(int idEmprestimo) {
        String sql = "UPDATE emprestimos SET data_devolucao = CURDATE(), status = 'DEVOLVIDO' WHERE id = ? AND status = 'EM_ABERTO'";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmprestimo);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            BibliotecaController.mostrarAlertar("Erro ao devolver livro", e.getMessage());
            return false;
        }
    }

    /**
     * Remove todos os empréstimos relacionados a um dado livro.
     * Útil para quando você precisa apagar um livro e quer primeiro remover
     * (ou migrar) os empréstimos associados.
     */
    public boolean excluirEmprestimosPorLivro(int livroId) {
        String sql = "DELETE FROM emprestimos WHERE livro_id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, livroId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            BibliotecaController.mostrarAlertar("Erro ao excluir empréstimos do livro", e.getMessage());
            return false;
        }
    }

    public int contarPendencias() {
        String sql = "SELECT COUNT(*) FROM emprestimos WHERE status = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "EM_ABERTO");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            BibliotecaController.mostrarAlertar("Erro ao contar", e.getMessage());
        }

        return 0;
    }
}