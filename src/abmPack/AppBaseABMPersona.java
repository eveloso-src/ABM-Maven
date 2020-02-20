package abmPack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AppBaseABMPersona {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection conexion = null;

		try {

			conexion = AdminBD.obtenerConexion();
			Scanner scan = new Scanner(System.in);

			int opcion = mostrarMenu(scan);

			while (opcion != 0) {
				switch (opcion) {
				case 1:
					alta(conexion, scan);
					break;
				case 2:
					modificacion(conexion, scan);
					break;
				case 3:
					baja(conexion, scan);
					break;
				case 4:
					listado(conexion);
					break;
				case 5:
					buscar(conexion, scan);
					break;
				case 6:
					venta(conexion, scan);
					break;
				case 7:
					registroVenta(conexion, scan);
					break;

				}

				opcion = mostrarMenu(scan);
			}

			conexion.close();
		}

		catch (SQLException e) {
			error();
		}
	}

	private static void registroVenta(Connection conexion, Scanner scan) {
		
		Statement stmt;

		System.out.println("REGISTRO DE VENTAS POR CLIENTE");
		System.out.println();

		try {

			stmt = conexion.createStatement();
			
			System.out.println("Ingrese ID de cliente ");
			int idPersona = scan.nextInt();
			
			ResultSet rsListado = stmt.executeQuery("select * from VENTA WHERE ID_PERSONA = '"+idPersona+"'");

			boolean encabezado = false;

			while (rsListado.next()) {
				
				if (!encabezado) {
					System.out.println(" ID | FECHA | IMPORTE | ID_PERSONA ");
					encabezado = true;
				}
			
				System.out.println(rsListado.getInt(1) + "  " + rsListado.getDate(2) + "  " + rsListado.getInt(3) + " " + rsListado.getInt(4) );

			}

		}

		catch (Exception e) {
			error();
		}

	}

	private static void venta(Connection conexion, Scanner scan) {

		Statement stmt;

		try {

			System.out.println("BIENVENIDO AL SISTEMA DE VENTAS");
			System.out.println();

			System.out.println("Ingrese ID de cliente ");
			int idPersona = scan.nextInt();

			stmt = conexion.createStatement();
			ResultSet rsListado = stmt.executeQuery("select * from PERSONA WHERE ID = '" + idPersona + "' ");

			boolean sinRegistro = true;
			boolean encabezado = false;

			while (rsListado.next()) {
				sinRegistro = false;

				if (!encabezado) {
					System.out.println(" ID | NOMBRE ");
					encabezado = true;
				}

				System.out.println(rsListado.getInt(1) + "  " + rsListado.getString(2));

			}
			
			System.out.println();
			System.out.println("Ingrese nuevo importe");
			float importe = scan.nextFloat();

			// te falta agregar fecha
			SimpleDateFormat fechaVenta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date fechaActual = new Date();
			String strFechaVenta = fechaVenta.format(fechaActual);

			String venta = "INSERT INTO VENTA (FECHA, IMPORTE, ID_PERSONA) VALUES ('" + strFechaVenta + "','" + importe
					+ "', '" + idPersona + "')";
			stmt.executeUpdate(venta);

			System.out.println("Venta registrada correctamente");

			if (sinRegistro) {
				System.out.println("No hay registros con ese nombre ");
			}

		}

		catch (Exception e) {
			error();
		}
	}

	private static void buscar(Connection conexion, Scanner scan) {

		System.out.println("Ingrese nombre que desea buscar: ");
		String buscarNombre = scan.next();

		Statement stmt;

		try {
			stmt = conexion.createStatement();
			ResultSet rsListado = stmt.executeQuery("select * from PERSONA WHERE NOMBRE LIKE '" + buscarNombre + "%' ");

			boolean sinRegistro = true;
			boolean encabezado = false;

			while (rsListado.next()) {
				sinRegistro = false;
				if (!encabezado) {
					System.out.println("NOMBRE | EDAD | FECHA_NACIMIENTO");
					encabezado = true;
				}

				Date stringFecha = rsListado.getDate(4);
				System.out.println(rsListado.getInt(1) + "  " + rsListado.getString(2) + "  " + rsListado.getString(3)
						+ " " + stringFecha);

			}
			if (sinRegistro) {
				System.out.println("No hay registros con ese nombre :( ");
			}
		} catch (SQLException e) {

			error();
		}

	}

	private static void listado(Connection conexion) {

		Statement stmt;

		System.out.println("LISTADO DE PERSONAS REGISTRADAS");
		System.out.println();

		try {

			stmt = conexion.createStatement();
			ResultSet rsListado = stmt.executeQuery("select * from PERSONA");

			boolean encabezado = false;

			while (rsListado.next()) {
				if (!encabezado) {
					System.out.println("NOMBRE | EDAD | FECHA_NACIMIENTO");
					encabezado = true;
				}
				Date stringFecha = rsListado.getDate(4);
				System.out.println(rsListado.getInt(1) + "  " + rsListado.getString(2) + "  " + rsListado.getString(3)
						+ " " + stringFecha);

			}

		}

		catch (Exception e) {
			error();
		}

	}

	private static void baja(Connection conexion, Scanner scan) {
		// TODO Auto-generated method stub
		Statement stmt;

		try {

			System.out.println("BAJA DE PERSONA");
			System.out.println();
			System.out.println("Ingrese ID de persona: ");
			int ID = scan.nextInt();
			stmt = conexion.createStatement();

			String delete = "DELETE FROM PERSONA WHERE ID = " + ID + " ";
			stmt.executeUpdate(delete);
			System.out.println("La operacion se realizo correctamente");
		}

		catch (Exception e) {
			error();
		}
	}

	private static void modificacion(Connection conexion, Scanner scan) {

		Statement stmt;

		try {

			System.out.println("MODIFICACION DE REGISTRO");
			System.out.println();

			System.out.println("Ingrese ID: ");
			int ID = scan.nextInt();

			System.out.println("Ingrese nuevo nombre: ");
			String nombreNuevo = scan.next();
			System.out.println("Ingrese nueva fecha: ");
			String stringFechaNueva = scan.next();

			stmt = conexion.createStatement();

			String modificacion = "UPDATE PERSONA SET NOMBRE = '" + nombreNuevo + "', FECHA_NACIMIENTO = '"
					+ stringFechaNueva + "' WHERE ID = " + ID + " ";

			stmt.executeUpdate(modificacion);

		}

		catch (Exception e) {
			error();
		}
	}

	private static void alta(Connection conexion, Scanner scan) {

		Statement stmt;

		try {

			System.out.println("REGISTRO DE ALTA");
			System.out.println();
			System.out.println("Ingrese nombre: ");
			String nombre = scan.next();
			System.out.println("Ingrese edad: ");
			int edad = scan.nextInt();
			System.out.println("Ingrese fecha de nacimiento: ");
			String stringFecha = scan.next();

			stmt = conexion.createStatement();

			String insert = "INSERT INTO PERSONA (NOMBRE, EDAD, FECHA_NACIMIENTO) VALUES ('" + nombre + "', '" + edad
					+ "' , '" + stringFecha + "')";
			stmt.executeUpdate(insert);

			System.out.println("Registro realizado correctamente");
		}

		catch (Exception e) {
			error();
		}
	}

	private static int mostrarMenu(Scanner scan) {

		System.out.println();
		System.out.println("SISTEMA REGISTO DE PERSONAS");
		System.out.println("---------------------------");
		System.out.println("Ingrese: ");
		System.out.println("1. ALTA");
		System.out.println("2. MODIFICACION");
		System.out.println("3. BAJA");
		System.out.println("4. LISTADO");
		System.out.println("5. BUSCAR");
		System.out.println("6. VENTA");
		System.out.println("7. REGISTRO DE VENTAS");
		System.out.println("0. SALIR");

		int opcion = scan.nextInt();

		return opcion;
	}

	private static void error() {

		System.out.println("Lamentablemente la operacion no pudo realizarce correctamente");
	}
}
