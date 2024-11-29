package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import common.Herramienta;

public class Servidor {
	private static int nRegistro = 2000;

	public static void main(String[] args) throws Exception {
		BufferedReader lecturaConsola = new BufferedReader(new InputStreamReader(System.in));
		arrancarRegistro(nRegistro);
		ServicioDatosImpl datos = new ServicioDatosImpl();
		Naming.rebind("rmi://localhost:" + nRegistro + "/datos", datos);

		if (!datos.rellenarClientesRepositorios())
			System.out.println("ERROR AL RELLENAR LOS REGISTROS HASHMAP");
		ServicioAutenticacionImpl autenticacion = new ServicioAutenticacionImpl();
		Naming.rebind("rmi://localhost:" + nRegistro + "/autenticacion", autenticacion);
		ServicioGestorImpl gestor = new ServicioGestorImpl();
		Naming.rebind("rmi://localhost:" + nRegistro + "/gestor", gestor);
		System.out.println("Services OK.");
		System.out.println();
		String recogeOpcion;
		int opcion = 0;
		do {
			System.out.println("---------------------------");
			System.out.println("JIKKO SERVER MENU");
			System.out.println("---------------------------");
			System.out.println("1-List Jikko clients");
			System.out.println("2-List repositories");
			System.out.println("3-List Repositories-Clients");
			System.out.println("4-Exit");
			System.out.print("Choose option: ");
			recogeOpcion = lecturaConsola.readLine();
			if (!Herramienta.esNumero(recogeOpcion)) {
				System.out.println();
				System.out.println("Introduce un valor de 1 a 4");
				System.out.println();
			} else {
				opcion = Integer.parseInt(recogeOpcion);
				switch (opcion) {
				case 1:
					System.out.println();
					System.out.println("Mostrando clientes registrados: ");
					Collection<String> clientes = datos.listaClientes();

					if (!clientes.isEmpty()) {
						System.out.print("[");
						for (String nombre : clientes) {
							System.out.print(" " + nombre + " ");
						}
						System.out.println("]");
						System.out.println();
					} else {
						System.out.println("==================================");
						System.out.println("No hay clientes registrados.");
						System.out.println("==================================");
					}
					break;
				case 2:
					System.out.println();
					System.out.println("Mostrando los repositorios registrados: ");
					Collection<String> repositorios = datos.listaRepositorios();

					if (!repositorios.isEmpty()) {
						System.out.print("[");

						for (String nombre : repositorios) {
							System.out.print(" " + nombre + " ");
						}
						System.out.println("]");
						System.out.println();

					} else {
						System.out.println("==================================");
						System.out.println("No hay repositorios registrados.");
						System.out.println("==================================");
					}
					break;

				case 3:
					datos.listarRepositoriosClientes();
					break;
				case 4:
					Naming.unbind("rmi://localhost:" + nRegistro + "/autenticacion");
					Naming.unbind("rmi://localhost:" + nRegistro + "/datos");
					Naming.unbind("rmi://localhost:" + nRegistro + "/gestor");
					System.out.println("Servicios finalizados. Saliendo del sistema.");
					System.out.println();
					System.exit(4);
					break;
				default:
					System.out.println("Opci�n no correcta");
					break;
				}
			}
		} while (opcion != 4);
	}

	private static void arrancarRegistro(int numPuertoRMI) throws RemoteException {
		try {
			Registry registro = LocateRegistry.getRegistry(numPuertoRMI);
			registro.list();
		} catch (RemoteException e) {
			System.out.println("El registro no se puede localizar en el puerto: " + numPuertoRMI);
			System.out.println();
			@SuppressWarnings("unused")
			Registry registro = LocateRegistry.createRegistry(numPuertoRMI);
			System.out.println("Registro RMI creado en el puerto: " + numPuertoRMI);
			System.out.println();
		}
	}
}