package es.studium.Practica2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientePersistencia 
{
	
	public static int createCliente(String nombre, String apellidos, String email, String dni, String clave) 
	{
		// Conectar a la base de datos
		Connection conect = conectar();
		int respuesta=0;
		try
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement statement = conect.createStatement();
			String cadenaSQL = "INSERT INTO Clientes" + " VALUES (null, '" 	+ nombre +  " ', '" + apellidos + "', '" + email + "', '" + dni + "', '" + clave + "');";
			System.out.println(cadenaSQL);
			statement.executeUpdate(cadenaSQL);	
			// Cerrar la conexión
			//desconectar(con);
			//Connection conn= conectar();
			statement = conect.createStatement();
			String select = "Select idClientes FROM Clientes where dniCliente = '" + dni +"'";
			ResultSet rs = statement.executeQuery(select);
			while(rs.next()) 
			{
				respuesta = rs.getInt("idClientes");
			}

			statement.close();
		} 
		catch (SQLException ex)
		{
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;

		}
		desconectar(conect);
		return respuesta;
	}

	public static String readCliente(int idCliente, String campo) 
	{
		String texto="";
		String statement = "SELECT "+campo+" FROM clientes where idClientes ="+ idCliente ;
		//Crear conexión
		Connection conect = conectar();
		try
		{
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = conect.createStatement();
			ResultSet rs = stmt.executeQuery(statement);

			while(rs.next()) {

				texto = rs.getString(campo);

			}
			rs.close();
			stmt.close();
		} catch (SQLException ex)
		{
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(conect);
		return texto;
		/* Devuelve el valor de la columna "campo" del cliente identificado por "idCliente" */
	}

	public static boolean updateCliente(int idCliente, String campo, String nuevoValor) 
	{

		boolean actualizado = false;


		// Ejecutar el UPDATE
		String sqlUpdate = "UPDATE Clientes SET " + campo 
				+ " = '" + nuevoValor + "'  WHERE idClientes = " + idCliente ;
		//System.out.println(sqlUpdate);

		// Conectar a la base de datos
		Connection conect = conectar();

		try
		{
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = conect.createStatement();
			stmt.executeUpdate(sqlUpdate);
			stmt.close();
			actualizado = true;
		} 
		catch (SQLException ex)
		{
			System.out.println("ERROR:al modificar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(conect);
		return actualizado;
		/* Actualiza el valor de la columna "campo" del cliente identificado por "idCliente". Devuelve true si se ha logrado actualizar. */
	}

	public static boolean deleteCliente(int idCliente) 
	{

		boolean respuesta = false;
		String sql = "DELETE FROM Clientes WHERE idClientes = " + idCliente;
		System.out.println(sql);
		// Conectar a la base de datos
		Connection conect = conectar();

		try
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = conect.createStatement();
			sta.executeUpdate(sql);
			sta.close();
			respuesta = true;
			System.err.println("Borrado realizado con éxito");
		} 
		catch (SQLException ex)
		{
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();
			respuesta = false;
		}

		return respuesta;
		/* Elimina el cliente identificado por "idCliente". Devuelve true si se ha logrado eliminar. */
	}

	public static Connection conectar()
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/hotel_ad?useSSL=false";
		String user = "root";
		String password = "Studium2019;";
		Connection con = null;
		try
		{
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver).newInstance();
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, user, password);
			if (con != null)
			{
				System.out.println("Conectado a la base de datos");
			}
		} 
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException ex)
		{
			System.out.println("ERROR:La dirección no es válida o el usuario y clave");
			ex.printStackTrace();
		} 
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}
	public static void desconectar(Connection conect)
	{
		try
		{
			conect.close();
			System.out.println("Desconectado de la base de datos");
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
