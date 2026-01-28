package com.felipemeireles.sistemadebiblioteca.controller;

import com.felipemeireles.sistemadebiblioteca.dao.AlunoDAO;
import com.felipemeireles.sistemadebiblioteca.dao.EmprestimoDAO;
import com.felipemeireles.sistemadebiblioteca.dao.LivroDAO;
import com.felipemeireles.sistemadebiblioteca.entity.Aluno;
import com.felipemeireles.sistemadebiblioteca.entity.Emprestimo;
import com.felipemeireles.sistemadebiblioteca.entity.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EmprestarLivroController {

    @FXML private TextField campoCPF, campoIdLivro;
    @FXML private Label nomeAluno, cpfAluno, tituloLivro, autorLivro, anoLivro;
    @FXML private DatePicker dataEmprestimo, dataDevolucao;

    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final LivroDAO livroDAO = new LivroDAO();

    @FXML
    private void buscarAluno() {
        String cpf = campoCPF.getText().trim();

        if (cpf.isEmpty()) {
            BibliotecaController.mostrarAlertar( "Atenção", "Digite um CPF para buscar.");
            return;
        }

        Aluno aluno = alunoDAO.buscarPorCpf(cpf);

        if (aluno != null) {
            nomeAluno.setText(aluno.getNome());
            cpfAluno.setText(aluno.getCpf());
            nomeAluno.setVisible(true);
            cpfAluno.setVisible(true);
        } else {
            BibliotecaController.mostrarAlertar("Não encontrado", "Nenhum aluno encontrado com esse CPF.");
        }
    }

    @FXML
    private void buscarLivro() {
        String idTexto = campoIdLivro.getText().trim();

        if (idTexto.isEmpty()) {
            BibliotecaController.mostrarAlertar( "Atenção", "Digite o ID do livro para buscar.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            BibliotecaController.mostrarAlertar( "Erro", "O ID do livro deve ser um número válido.");
            return;
        }

        Livro livro = livroDAO.buscarPorId(id);

        if (livro != null) {
            tituloLivro.setText(livro.getTitulo());
            autorLivro.setText(livro.getAutor());
            anoLivro.setText(String.valueOf(livro.getAno()));

            tituloLivro.setVisible(true);
            autorLivro.setVisible(true);
            anoLivro.setVisible(true);
        } else {
            BibliotecaController.mostrarAlertar( "Não encontrado", "Nenhum livro encontrado com esse ID.");
        }
    }

    @FXML
    private void cancelarEmprestimo() {
        campoCPF.clear();
        campoIdLivro.clear();
        nomeAluno.setText("");
        cpfAluno.setText("");
        tituloLivro.setText("");
        autorLivro.setText("");
        anoLivro.setText("");
        dataEmprestimo.setValue(null);
        dataDevolucao.setValue(null);

        nomeAluno.setVisible(false);
        cpfAluno.setVisible(false);
        tituloLivro.setVisible(false);
        autorLivro.setVisible(false);
        anoLivro.setVisible(false);
    }

    @FXML
    private void confirmarEmprestimo() {
        String cpf = cpfAluno.getText();
        if (cpf == null || cpf.isEmpty()) {
            BibliotecaController.mostrarAlertar("Atenção", "Busque um aluno antes de confirmar o empréstimo.");
            return;
        }
        Aluno aluno = alunoDAO.buscarPorCpf(cpf);
        if (aluno == null || !aluno.isAtivo()) {
            BibliotecaController.mostrarAlertar("Atenção", "Aluno não encontrado ou arquivado.");
            return;
        }
        String idTexto = campoIdLivro.getText();
        if (idTexto == null || idTexto.isEmpty()) {
            BibliotecaController.mostrarAlertar( "Atenção", "Busque um livro antes de confirmar o empréstimo.");
            return;
        }

        int livroId;
        try {
            livroId = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            BibliotecaController.mostrarAlertar( "Erro", "O ID do livro deve ser um número válido.");
            return;
        }

        if (dataEmprestimo.getValue() == null || dataDevolucao.getValue() == null) {
            BibliotecaController.mostrarAlertar( "Atenção", "Selecione as datas de empréstimo e devolução.");
            return;
        }

        if (dataDevolucao.getValue().isBefore(dataEmprestimo.getValue())) {
            BibliotecaController.mostrarAlertar( "Atenção", "A data de devolução não pode ser antes da de empréstimo.");
            return;
        }

        // Cria e salva o empréstimo
        Emprestimo emprestimo = new Emprestimo(
                cpfAluno.getText(),
                nomeAluno.getText(),
                livroId,
                tituloLivro.getText(),
                dataEmprestimo.getValue(),
                dataDevolucao.getValue()
        );

        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        boolean sucesso = emprestimoDAO.adicionarEmprestimo(emprestimo);

        if (sucesso) {
            BibliotecaController.mostrarSucesso( "Sucesso", "Empréstimo realizado com sucesso!");
            cancelarEmprestimo();
        }
    }

}