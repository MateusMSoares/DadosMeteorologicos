package com.example.dadosmeteorologicos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.dadosmeteorologicos.model.RegistroDto;

public class IniciaBanco {

    private String url = "jdbc:postgresql://localhost/ApiFatec";
    private String user = "postgres";
    private String password = "root";
    private Connection conn;

    public IniciaBanco() {
        this.conn = conectarBanco();
    }

    public Connection conectarBanco() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL Stateee: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void fecharConexao() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
    }

    public void iniciarBanco(){
        try {
            if (conn != null) {
                System.out.println("Banco Iniciado");
    
                String sql = "CREATE TABLE IF NOT EXISTS Registro (" +
                    "id SERIAL PRIMARY KEY," +
                    "cidade VARCHAR(255)," +
                    "estacao VARCHAR(255)," +
                    "data DATE," +
                    "hora TIME," +
                    "temperaturaMedia DECIMAL(5,2)," +
                    "umidadeMedia DECIMAL(5,2)," +
                    "velVento DECIMAL(5,2)," +
                    "dirVento DECIMAL(5,2)," +
                    "chuva DECIMAL(5,2)," +
                    "temperaturaSuspeita BOOLEAN," +
                    "umidadeSuspeita BOOLEAN," +
                    "velocidadeVentoSuspeita BOOLEAN," +
                    "direcaoVentoSuspeita BOOLEAN," +
                    "chuvaSuspeita BOOLEAN" +
                    ")";
    
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
    
            } else {
                System.out.println("Falha ao conectar no banco!");
            }
        } catch (SQLException e) {
            System.err.format("SQL Stateee: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RegistroDto> selecionarTodosRegistros() {
        List<RegistroDto> registros = new ArrayList<>();

        try {
            if (conn != null) {
                String sql = "SELECT * FROM Registro";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String cidade = rs.getString("cidade");
                    String estacao = rs.getString("estacao");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime hora = rs.getTime("hora").toLocalTime();
                    Double temperaturaMedia = rs.getDouble("temperaturaMedia");
                    Double umidadeMedia = rs.getDouble("umidadeMedia");
                    Double velVento = rs.getDouble("velVento");
                    Double dirVento = rs.getDouble("dirVento");
                    Double chuva = rs.getDouble("chuva");
                    boolean temperaturaSuspeita = rs.getBoolean("temperaturaSuspeita");
                    boolean umidadeSuspeita = rs.getBoolean("umidadeSuspeita");
                    boolean velocidadeVentoSuspeita = rs.getBoolean("velocidadeVentoSuspeita");
                    boolean direcaoVentoSuspeita = rs.getBoolean("direcaoVentoSuspeita");
                    boolean chuvaSuspeita = rs.getBoolean("chuvaSuspeita");

                    RegistroDto registro = new RegistroDto(id, cidade, estacao, data, hora, temperaturaMedia, umidadeMedia, velVento, dirVento, chuva, temperaturaSuspeita, umidadeSuspeita, velocidadeVentoSuspeita, direcaoVentoSuspeita, chuvaSuspeita);
                    registros.add(registro);
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return registros;
    }   

    public List<RegistroDto> selecionarTodosRegistrosSuspeitos(){
        List<RegistroDto> registros = new ArrayList<>();
        try {
            if (conn != null) {
                String sql = "SELECT * FROM Registro WHERE temperaturaSuspeita = true OR umidadeSuspeita = true OR velocidadeVentoSuspeita = true OR direcaoVentoSuspeita = true OR chuvaSuspeita = true";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int ide = rs.getInt("id");
                    String cidade = rs.getString("cidade");
                    String estacao = rs.getString("estacao");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime hora = rs.getTime("hora").toLocalTime();
                    Double temperaturaMedia = rs.getDouble("temperaturaMedia");
                    Double umidadeMedia = rs.getDouble("umidadeMedia");
                    Double velVento = rs.getDouble("velVento");
                    Double dirVento = rs.getDouble("dirVento");
                    Double chuva = rs.getDouble("chuva");
                    Boolean temperaturaSuspeita = rs.getBoolean("temperaturaSuspeita");
                    Boolean umidadeSuspeita = rs.getBoolean("umidadeSuspeita");
                    Boolean velocidadeVentoSuspeita = rs.getBoolean("velocidadeVentoSuspeita");
                    Boolean direcaoVentoSuspeita = rs.getBoolean("direcaoVentoSuspeita");
                    Boolean chuvaSuspeita = rs.getBoolean("chuvaSuspeita");

                    RegistroDto registro = new RegistroDto(ide, cidade, estacao, data, hora, temperaturaMedia, umidadeMedia, velVento, dirVento, chuva, temperaturaSuspeita, umidadeSuspeita, velocidadeVentoSuspeita, direcaoVentoSuspeita, chuvaSuspeita);
                    System.out.println(registro);
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return registros;
    }

//     CREATE TABLE cidade (
//     id SERIAL PRIMARY KEY,
//     nome VARCHAR(255),
//     sigla CHARACTER(4)
// );

// CREATE TABLE estacao (
//     id SERIAL PRIMARY KEY,
//     numero VARCHAR(255),
//     idCidade INT
// );
   
}