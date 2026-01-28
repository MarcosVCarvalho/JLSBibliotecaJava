package com.felipemeireles.sistemadebiblioteca.controller;

import com.felipemeireles.sistemadebiblioteca.dao.EmprestimoDAO;
import com.felipemeireles.sistemadebiblioteca.entity.Emprestimo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class HistoricoEmprestimosController {

    @FXML private TableView<Emprestimo> tabelaEmprestimos;
    @FXML private TableColumn<Emprestimo, Integer> colunaId;
    @FXML private TableColumn<Emprestimo, String> colunaAlunoNome;
    @FXML private TableColumn<Emprestimo, String> colunaLivroTitulo;
    @FXML private TableColumn<Emprestimo, LocalDate> colunaDataEmprestimo;
    @FXML private TableColumn<Emprestimo, LocalDate> colunaDataDevolucao;
    @FXML private TableColumn<Emprestimo, String> colunaStatus;

    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    @FXML
    public void initialize() {
        configurarColunas();
        carregarEmprestimos();
    }

    private void configurarColunas() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaAlunoNome.setCellValueFactory(new PropertyValueFactory<>("alunoNome"));
        colunaLivroTitulo.setCellValueFactory(new PropertyValueFactory<>("livroTitulo"));
        colunaDataEmprestimo.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        colunaDataDevolucao.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void carregarEmprestimos() {
        ObservableList<Emprestimo> emprestimos =
                FXCollections.observableArrayList(emprestimoDAO.listarEmprestimosFechados());
        tabelaEmprestimos.setItems(emprestimos);
    }


}
