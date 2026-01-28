package com.felipemeireles.sistemadebiblioteca.controller;

import com.felipemeireles.sistemadebiblioteca.dao.AlunoDAO;
import com.felipemeireles.sistemadebiblioteca.entity.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdicionarAlunoController {

    @FXML
    private TextField campoNome, campoCPF, campoEmail, campoTelefone;

    @FXML private void salvarAluno() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String cpf = campoCPF.getText();
        String telefone = campoTelefone.getText();

        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || telefone.isEmpty()){
            BibliotecaController.mostrarAlertar("Campos vazios!","Por favor, preencha todos os campos.");
            return;
        }

        if (!cpf.matches("\\d+")) {
            BibliotecaController.mostrarAlertar("CPF inválido","O CPF deve conter apenas números.");
            return;
        }

        if (!telefone.matches("\\d+")) {
            BibliotecaController.mostrarAlertar("Telefone inválido","O telefone deve conter apenas números.");
            return;
        }

        AlunoDAO dao = new AlunoDAO();
        Aluno alunoExistente = dao.buscarPorCpf(cpf);

        if (alunoExistente != null){
            BibliotecaController.mostrarAlertar("CPF duplicado","Já existe um aluno cadastrado com esse CPF");
            return;
        }

        Aluno aluno = new Aluno(nome,email,cpf,telefone);
        dao.adicionarAluno(aluno);
        inserirDadosTabela();
        limparCampos();
    }

    @FXML
    private void limparCampos(){
        campoNome.setText(null);
        campoEmail.setText(null);
        campoTelefone.setText(null);
        campoCPF.setText(null);
    }

    @FXML
    private void voltarTela() {
        Navegador.trocarTela("/com/felipemeireles/sistemadebiblioteca/view/Alunos.fxml");
    }

    @FXML
    private TableView<Aluno> tabelaAlunos;

    @FXML
    private TableColumn<Aluno, Integer> colunaId;
    @FXML
    private TableColumn<Aluno, String> colNome;
    @FXML
    private TableColumn<Aluno, String> colEmail;
    @FXML
    private TableColumn<Aluno, String> colCPF;
    @FXML
    private TableColumn<Aluno, String> colTelefone;

    private AlunoDAO alunoDAO = new AlunoDAO();

    @FXML
    public void initialize() {
        inserirDadosTabela();
    }

    private void inserirDadosTabela(){
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunoDAO.listarAlunos());
        tabelaAlunos.setItems(alunos);
    }
}
