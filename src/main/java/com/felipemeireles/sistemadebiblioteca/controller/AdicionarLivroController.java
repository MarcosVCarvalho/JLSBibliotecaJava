package com.felipemeireles.sistemadebiblioteca.controller;

import com.felipemeireles.sistemadebiblioteca.dao.LivroDAO;
import com.felipemeireles.sistemadebiblioteca.entity.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class AdicionarLivroController {

    @FXML private TextField campoTitulo, campoAutor, campoAno, campoQuantidade;

    int anoAtual = LocalDate.now().getYear();

    @FXML
    private void salvarLivro() {

        if (campoTitulo.getText().trim().isEmpty() || campoAutor.getText().trim().isEmpty() || campoAno.getText().trim().isEmpty() || campoQuantidade.getText().trim().isEmpty()) {
            BibliotecaController.mostrarAlertar("Campos vazios!","Por favor, preencha todos os campos.");
            return;
        }
        String titulo = campoTitulo.getText();
        String autor = campoAutor.getText();
        String anoTexto = campoAno.getText();
        String quantidadeTexto = campoQuantidade.getText();

        anoTexto = anoTexto.trim();

        if (!anoTexto.matches("\\d+") || Integer.parseInt(anoTexto) > anoAtual) {
            BibliotecaController.mostrarAlertar("Ano inválido","O ano deve conter apenas números e ser válido");
            return;
        }
        if (!quantidadeTexto.matches("\\d+") || Integer.parseInt(quantidadeTexto) <= 0) {
            BibliotecaController.mostrarAlertar("Quantidade inválida","A quantidade deve conter apenas números e ser maior que 0");
            return;
        }


        int ano = Integer.parseInt(anoTexto);
        int quantidade = Integer.parseInt(quantidadeTexto);
        Livro livro = new Livro(titulo, autor, ano, quantidade);
        LivroDAO dao = new LivroDAO();
        dao.adicionarLivro(livro);
        inserirDadosTabela();
        limparCampos();
    }

    @FXML
    private void limparCampos(){
        campoTitulo.setText(null);
        campoAutor.setText(null);
        campoAno.setText(null);
        campoQuantidade.setText(null);
    }

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private TableColumn<Livro, Integer> colunaId;
    @FXML
    private TableColumn<Livro, String> colTitulo;
    @FXML
    private TableColumn<Livro, String> colAutor;
    @FXML
    private TableColumn<Livro, Integer> colAno;
    @FXML
    private TableColumn<Livro, Integer> colQuantidade;

    private LivroDAO livroDAO = new LivroDAO();

    @FXML
    private void voltarTela() {
        Navegador.trocarTela("/com/felipemeireles/sistemadebiblioteca/view/Livros.fxml");
    }

    @FXML
    public void initialize() {

        inserirDadosTabela();
    }

    private void inserirDadosTabela(){
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        ObservableList<Livro> livros = FXCollections.observableArrayList(livroDAO.listarLivros());
        tabelaLivros.setItems(livros);
    }
}
