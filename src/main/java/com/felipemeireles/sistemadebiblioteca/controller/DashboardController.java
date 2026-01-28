package com.felipemeireles.sistemadebiblioteca.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.felipemeireles.sistemadebiblioteca.dao.LivroDAO;
import com.felipemeireles.sistemadebiblioteca.dao.EmprestimoDAO;
import com.felipemeireles.sistemadebiblioteca.dao.AlunoDAO;

public class DashboardController {
    @FXML
    private Label numLivros;
    @FXML
    private Label numEstudantes;
    @FXML
    private Label numPendencias;


    @FXML
    public void initialize() {
        atualizarDashboard();
    }

    private void atualizarDashboard() {
        LivroDAO livroDAO = new LivroDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

        numLivros.setText(String.valueOf(livroDAO.contarLivrosAtivos()));
        numEstudantes.setText(String.valueOf(alunoDAO.contarAlunosAtivos()));
        numPendencias.setText(String.valueOf(emprestimoDAO.contarPendencias()));
    }
}
