package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Paciente;
import br.com.fiap.excecoes.OdontoClinicException;
import br.com.fiap.excecoes.RecursoNaoEncontradoException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class PacienteDAO {

    @Inject
    private ConexaoFactory conexaoFactory;

    private static final String INSERT =
            "INSERT INTO TB_PACIENTE (ID, NOME, CPF, TELEFONE, EMAIL, DATA_NASCIMENTO, PLANO_ODONTOLOGICO, ATIVO) " +
            "VALUES (SEQ_PACIENTE.NEXTVAL, ?, ?, ?, ?, ?, ?, 1)";

    private static final String SELECT_ALL =
            "SELECT * FROM TB_PACIENTE WHERE ATIVO = 1";

    private static final String SELECT_BY_ID =
            "SELECT * FROM TB_PACIENTE WHERE ID = ?";

    private static final String UPDATE =
            "UPDATE TB_PACIENTE SET NOME=?, TELEFONE=?, EMAIL=?, PLANO_ODONTOLOGICO=? WHERE ID=?";

    private static final String DELETE_LOGICO =
            "UPDATE TB_PACIENTE SET ATIVO = 0 WHERE ID = ?";


    public void inserir(Paciente paciente) {
        try (Connection conn = conexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getTelefone());
            stmt.setString(4, paciente.getEmail());
            stmt.setDate(5, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(6, paciente.getPlanoOdontologico());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao inserir paciente", e);
        }
    }


    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        try (Connection conn = conexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);

             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao listar pacientes", e);
        }

        return lista;
    }


    public Paciente buscarPorId(Long id) {
        try (Connection conn = conexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao buscar paciente", e);
        }

        throw new RecursoNaoEncontradoException("Paciente", id);
    }

    public void atualizar(Paciente paciente) {
        try (Connection conn = conexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getTelefone());
            stmt.setString(3, paciente.getEmail());
            stmt.setString(4, paciente.getPlanoOdontologico());
            stmt.setLong(5, paciente.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao atualizar paciente", e);
        }
    }


    public void deletar(Long id) {
        try (Connection conn = conexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_LOGICO)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao deletar paciente", e);
        }
    }


    private Paciente mapear(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();

        p.setId(rs.getLong("ID"));
        p.setNome(rs.getString("NOME"));
        p.setCpf(rs.getString("CPF"));
        p.setTelefone(rs.getString("TELEFONE"));
        p.setEmail(rs.getString("EMAIL"));

        Date data = rs.getDate("DATA_NASCIMENTO");
        if (data != null) {
            p.setDataNascimento(data.toLocalDate());
        }

        p.setPlanoOdontologico(rs.getString("PLANO_ODONTOLOGICO"));
        p.setAtivo(rs.getInt("ATIVO") == 1);

        return p;
    }

}
