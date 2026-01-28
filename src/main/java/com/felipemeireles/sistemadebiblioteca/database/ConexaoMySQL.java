// 📁 database.com.felipemeireles.sistemadebiblioteca.ConexaoMySQL.java
package com.felipemeireles.sistemadebiblioteca.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;

public class ConexaoMySQL {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/DB_BIBLIOTECA";
    private static final String USUARIO = "root";
    private static final String SENHA = null; // mantido como null (aceito pelo MySQL Connector)

    // ✅ Helper interno para iniciar MySQL (mantém seu comportamento)
    public static class MySQLHelper {
        private static final String MYSQL_PATH = "C:\\xampp\\mysql\\bin\\mysqld.exe";

        public static void iniciarMySQL() {
            try {
                // Verifica se o mysqld já está rodando
                Process check = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq mysqld.exe\"");
                String output = new String(check.getInputStream().readAllBytes());
                if (output.toLowerCase().contains("mysqld.exe")) {
                    System.out.println("✅ MySQL já está em execução.");
                    return;
                }

                System.out.println("⚙️ Iniciando MySQL...");
                Process process = Runtime.getRuntime().exec(MYSQL_PATH);
                // Não esperamos o processo terminar (ele roda em background)
                Thread.sleep(1500); // Espera ~1.5s para o servidor subir
                System.out.println("✅ MySQL iniciado com sucesso!");

            } catch (IOException | InterruptedException e) {
                System.err.println("⚠️ Erro ao iniciar o MySQL: " + e.getMessage());
            }
        }
    }

    // ✅ Método principal: retorna Connection (lança exceção se falhar)
    public static Connection obterConexao() throws SQLException {
        MySQLHelper.iniciarMySQL(); // Garante que o MySQL está rodando
        System.out.println("🔌 Conectando ao banco de dados...");
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    // ✅ Método alternativo (compatível com seu antigo `conectar()`)
    public static Connection conectar() {
        try {
            return obterConexao();
        } catch (SQLException e) {
            System.err.println("❌ Falha ao conectar ao banco: " + e.getMessage());
            e.printStackTrace();
            return null; // mantém compatibilidade, mas evite usar em produção
        }
    }
}