package com.felipemeireles.sistemadebiblioteca.entity;

public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private int ano;
    private int quantidade;
    private String disponibilidade;
    private boolean ativo;

    public Livro(int id,String titulo, String autor,int ano, int quantidade, boolean ativo) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.quantidade = quantidade;
        this.disponibilidade = "disponivel";
        this.ativo = ativo;
    }

    public Livro(String titulo, String autor, int ano, int quantidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.quantidade = quantidade;
        this.disponibilidade = "disponivel";
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }
    public boolean isAtivo(){return ativo; }
}