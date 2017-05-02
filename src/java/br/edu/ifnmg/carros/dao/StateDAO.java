package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.State;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Danilo Souza Almeida
 */
public class StateDAO {
    private State sta = new State();

    public State getEstado(int idestado) throws ErroSistema{
        State sta = null;
        String meusql = "SELECT * FROM states where Id_State = " + idestado;
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(meusql);
            ResultSet rs = ps.executeQuery();
            String ttt=rs.getString("State");
            sta.setId(rs.getInt("Id_State"));
            sta.setName(rs.getString("State"));
            FabricaConexao.fecharConexao();
            return sta;
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os aeroportos!",ex);
        }
    }
}
