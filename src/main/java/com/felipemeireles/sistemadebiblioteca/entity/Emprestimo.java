package com.felipemeireles.sistemadebiblioteca.entity;

import java.time.LocalDate;

public class Emprestimo {

    private int id;
    private String alunoCpf;
    private String alunoNome;
    private int livroId;
    private String livroTitulo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private String status;

    public Emprestimo(){

    }

    public Emprestimo(int id, String alunoCpf, String alunoNome, int livroId,String livroTitulo,  LocalDate dataEmprestimo, LocalDate dataDevolucao, String status) {
        this.id = id;
        this.alunoCpf = alunoCpf;
        this.alunoNome = alunoNome;
        this.livroId = livroId;
        this.livroTitulo = livroTitulo;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
    }

    public Emprestimo(String alunoCpf,String alunoNome, int livroId,String livroTitulo, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.alunoCpf = alunoCpf;
        this.alunoNome = alunoNome;
        this.livroId = livroId;
        this.livroTitulo = livroTitulo;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.status = "EM_ABERTO";
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAlunoCpf() {
        return alunoCpf;
    }
    public void setAlunoCpf(String alunoCpf) {
        this.alunoCpf = alunoCpf;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }
    public String getAlunoNome() {
        return alunoNome;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroTitulo(String livroTitulo) {
        this.livroTitulo = livroTitulo;
    }

    public String getLivroTitulo() {
        return livroTitulo;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
