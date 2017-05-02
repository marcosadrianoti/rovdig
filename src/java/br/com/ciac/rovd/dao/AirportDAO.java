package br.com.ciac.rovd.dao;

import br.com.ciac.rovd.entidade.Airport;
import br.com.ciac.rovd.entidade.State;
import br.com.ciac.rovd.util.FabricaConexao;
import br.com.ciac.rovd.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danilo Souza Almeida
 */
public class AirportDAO {
    
    public void salvar(Airport airp) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(airp.getId() == 0){
                ps = conexao.prepareStatement("INSERT INTO 'airports' ('icao_code','city','id_State') VALUES (?,?,?)");
            } else {
                ps = conexao.prepareStatement("update airports set icao_code=?, city=?, id_State=? where id=?");
                ps.setInt(4, airp.getId());
            }
            ps.setString(1, airp.getIcaoCode());
            ps.setString(2, airp.getCity());
            ps.setInt(3, airp.getState().getId());
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }
    
    public void deletar(Integer idCarro) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps  = conexao.prepareStatement("delete from carro where id = ?");
            ps.setInt(1, idCarro);
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o carro!", ex);
        }
    }
    
    public List<Airport> buscar() throws ErroSistema{
//        StateDAO1 staDAO = new StateDAO1();
        State estado = null;
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from airports");
            ResultSet resultSet = ps.executeQuery();
            List<Airport> airports = new ArrayList<>();
            while(resultSet.next()){
                Airport airp = new Airport();
                airp.setId(resultSet.getInt("Id_Airport"));
                airp.setIcaoCode(resultSet.getString("Icao_Code"));
                airp.setCity(resultSet.getString("City"));
                State sta = new State();
                int idestado = (int) resultSet.getObject("id_State");
                PreparedStatement ps2 = conexao.prepareStatement("SELECT * FROM states where Id_State = " + idestado);
                ResultSet rs = ps2.executeQuery();
                rs.first();
                sta.setId(rs.getInt("Id_State"));
                sta.setName(rs.getString("State"));
//                estado = staDAO.getEstado(teste);
                airp.setState(sta);
//                estado = sta.getState(resultSet.getInt("id_State"));
//                airp.setState((State) resultSet.getObject("id_State"));
                airports.add(airp);
            }
            FabricaConexao.fecharConexao();
            return airports;
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os aeroportos!",ex);
        }
    }
}
