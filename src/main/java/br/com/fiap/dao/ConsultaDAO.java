package br.com.fiap.dao;

import br.com.fiap.excecoes.OdontoClinicException;
import br.com.fiap.excecoes.RecursoNaoEncontradoException;
import br.com.fiap.entities.Consulta;
import br.com.fiap.conexoes.ConexaoFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaDAO {


    @Inject
    ConexaoFactory connectionFactory;


    private static final String INSERT =
            "INSERT INTO TB_CONSULTA (ID, PACIENTE_ID, DENTISTA_ID, DATA_HORA, VALOR, STATUS) " +
                    "VALUES (SEQ_CONSULTA.NEXTVAL, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT * FROM TB_CONSULTA";

    private static final String SELECT_BY_ID =
            "SELECT * FROM TB_CONSULTA WHERE ID = ?";

    private static final String SELECT_BY_PACIENTE =
            "SELECT * FROM TB_CONSULTA WHERE PACIENTE_ID = ?";

    private static final String UPDATE =
            "UPDATE TB_CONSULTA SET DATA_HORA=?, VALOR=?, STATUS=? WHERE ID=?";

    private static final String UPDATE_STATUS =
            "UPDATE TB_CONSULTA SET STATUS=? WHERE ID=?";

    private static final String DELETE =
            "DELETE FROM TB_CONSULTA WHERE ID=?";


    public void inserir(Consulta consulta) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {

            stmt.setLong(1, consulta.getPacienteId());
            stmt.setLong(2, consulta.getDentistaId());
            stmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setDouble(4, consulta.getValor());
            stmt.setString(5, consulta.getStatus().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao inserir consulta", e);
        }
    }


    public List<Consulta> listarTodas() {
        List<Consulta> lista = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao listar consultas", e);
        }

        return lista;
    }

    public Consulta buscarPorId(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao buscar consulta", e);
        }

        throw new RecursoNaoEncontradoException("Consulta", id);
    }

    public List<Consulta> buscarPorPaciente(Long pacienteId) {
        List<Consulta> lista = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PACIENTE)) {

            stmt.setLong(1, pacienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao buscar consultas do paciente", e);
        }

        return lista;
    }


    public void atualizar(Consulta consulta) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setDouble(2, consulta.getValor());
            stmt.setString(3, consulta.getStatus().name());
            stmt.setLong(4, consulta.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao atualizar consulta", e);
        }
    }

    public void atualizarStatus(Long id, Consulta.Status status) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_STATUS)) {

            stmt.setString(1, status.name());
            stmt.setLong(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao atualizar status da consulta", e);
        }
    }


    public void deletar(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao deletar consulta", e);
        }
    }



    private Consulta mapear(ResultSet rs) throws SQLException {
        Consulta c = new Consulta();

        c.setId(rs.getLong("ID"));
        c.setPacienteId(rs.getLong("PACIENTE_ID"));
        c.setDentistaId(rs.getLong("DENTISTA_ID"));
        c.setDataHora(rs.getTimestamp("DATA_HORA").toLocalDateTime());
        c.setValor(rs.getDouble("VALOR"));
        c.setStatus(Consulta.Status.valueOf(rs.getString("STATUS")));

        return c;
    }
}