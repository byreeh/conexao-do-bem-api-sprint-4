package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Dentista;
import br.com.fiap.excecoes.OdontoClinicException;
import br.com.fiap.excecoes.RecursoNaoEncontradoException;
import com.google.inject.Inject;
import io.agroal.pool.ConnectionFactory;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DentistaDAO {

    @Inject
    ConexaoFactory connectionFactory;

    private static final String INSERT =
            "INSERT INTO TB_DENTISTA (ID, NOME, CRO, ESPECIALIDADE, ATIVO) VALUES (SEQ_DENTISTA.NEXTVAL, ?, ?, ?, 1)";

    private static final String SELECT_ALL =
            "SELECT ID, NOME, CRO, ESPECIALIDADE, ATIVO FROM TB_DENTISTA WHERE ATIVO = 1";

    private static final String SELECT_BY_ID =
            "SELECT ID, NOME, CRO, ESPECIALIDADE, ATIVO FROM TB_DENTISTA WHERE ID = ?";

    private static final String UPDATE =
            "UPDATE TB_DENTISTA SET NOME=?, CRO=?, ESPECIALIDADE=? WHERE ID=?";

    private static final String DELETE =
            "UPDATE TB_DENTISTA SET ATIVO = 0 WHERE ID = ?";

    // ================= CREATE =================
    public void inserir(Dentista dentista) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {

            stmt.setString(1, dentista.getNome());
            stmt.setString(2, dentista.getCro());
            stmt.setString(3, dentista.getEspecialidade());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao inserir dentista: " + e.getMessage(), e);
        }
    }

    public List<Dentista> listarTodos() {
        List<Dentista> lista = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao listar dentistas: " + e.getMessage(), e);
        }

        return lista;
    }

    public Dentista buscarPorId(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao buscar dentista: " + e.getMessage(), e);
        }

        throw new RecursoNaoEncontradoException("Dentista", id);
    }

    public void atualizar(Dentista dentista) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, dentista.getNome());
            stmt.setString(2, dentista.getCro());
            stmt.setString(3, dentista.getEspecialidade());
            stmt.setLong(4, dentista.getId());

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new RecursoNaoEncontradoException("Dentista", dentista.getId());
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao atualizar dentista: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setLong(1, id);

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new RecursoNaoEncontradoException("Dentista", id);
            }

        } catch (SQLException e) {
            throw new OdontoClinicException("Erro ao deletar dentista: " + e.getMessage(), e);
        }
    }

    private Dentista mapear(ResultSet rs) throws SQLException {
        Dentista d = new Dentista();
        d.setId(rs.getLong("ID"));
        d.setNome(rs.getString("NOME"));
        d.setCro(rs.getString("CRO"));
        d.setEspecialidade(rs.getString("ESPECIALIDADE"));
        d.setAtivo(rs.getInt("ATIVO") == 1);
        return d;
    }
}