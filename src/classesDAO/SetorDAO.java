package classesDAO;

import entidades.Ideia;
import entidades.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {

    public static List<Setor> setorList = new ArrayList<>();

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/sgdibd";
        Connection connection = DriverManager.getConnection(url, "root", "");

        return connection;
    }

    public static void salvarSetor(Setor setor){
        setorList.add(setor);
    }

    public static void salvarBD(Setor setor) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into setor(nome) values (?)");
        stmt.setString(1, setor.getNomeSetor());

        int x = stmt.executeUpdate();
        System.out.println(x + " Linhas inseridas");
        connection.close();
    }

    public static void remover(Setor setor){
        setorList.remove(setor);
    }

    public static void excluirBD(Integer id) throws  SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("delete from setor where idsetor = ?");
        stmt.setInt(1, id);

        try {
            stmt.executeUpdate();
        }
        catch(SQLIntegrityConstraintViolationException e) {
            System.out.println("não é possível apagar este setor pois ele tem ideias e colaboradores que o utilizam");
        }
        connection.close();
    }

    public static void editarBD(Setor setor) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("update setor set nome =? where idsetor=?");

        stmt.setString(1, setor.getNomeSetor());
        stmt.setInt(2, setor.getIdSetor());

        stmt.executeUpdate();
        connection.close();
    }
    public static List<Setor> buscarTodos(){
        return setorList;
    }

    public static List<Setor> BuscarTodos() throws  SQLException, ClassNotFoundException {
        List<Setor> setore = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from setor");

        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            setore.add(new Setor(resultSet.getInt(1), resultSet.getString(2)));
        }

        System.out.println(setore);
        return setore;
    }
    public static Object[] findSetorInArray(){
        List<String> setorTitulo = new ArrayList<>();

        for (Setor setor : setorList) {
            setorTitulo.add(setor.getNomeSetor());
        }
        return setorTitulo.toArray();
    }

    public static List<Setor> buscarPorTitulo(Object titulo) {
        List<Setor> setorFiltrado = new ArrayList<>();
        for (Setor busca : setorList){
            if(busca.getNomeSetor().equals(titulo)){
                setorFiltrado.add(busca);
            }
        }
        return setorFiltrado;
    }

    public static Setor BuscarPorId(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from setor where idsetor=?");
        stmt.setInt(1, id);

        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        Setor setor = new Setor();
        setor.setIdSetor(resultSet.getInt(1));
        setor.setNomeSetor(resultSet.getString(2));
        System.out.println(setor);
        return setor;
    }

    public static Integer BuscarIdPorNome(String nome) throws  SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from setor where nome = ?");
        stmt.setString(1, nome);

        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        Integer id = resultSet.getInt(1);

        return id;
    }

    public static Object[] BuscarNomes() throws SQLException, ClassNotFoundException {

        List<String> nomeSetores = new ArrayList<>();

        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select nome from setor");


        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            nomeSetores.add(resultSet.getString(1));
        };


        return nomeSetores.toArray();

    }
}
