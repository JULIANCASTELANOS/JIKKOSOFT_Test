package servidor;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

	private static final long serialVersionUID = 291220122019L;
	private Map<Integer, String> sesionCliente = new HashMap<Integer, String>();
	private Map<Integer, String> registroCliente = new HashMap<Integer, String>();
	private Map<Integer, String> sesionRepositorio = new HashMap<Integer, String>();
	private Map<Integer, String> registroRepositorio = new HashMap<Integer, String>();
	private Map<Integer, Integer> clienteRepositorio = new HashMap<Integer, Integer>();

	public ServicioDatosImpl() throws RemoteException {
		super();
	}

	public ServicioDatosImpl(String nombreLog) throws RemoteException {
		super();
	}

	public boolean rellenarClientesRepositorios() throws RemoteException {
		registroCliente.put(1, "JULIAN CASTELLANOS URIBE CACHE1");
		registroCliente.put(2, "CATALINA TABORDA CACHE2");
		registroCliente.put(3, "RONALD PEREZ CACHE3");
		registroCliente.put(4, "DIEGO TRUJILLO CACHE4");
		registroRepositorio.put(1, "R1");
		registroRepositorio.put(2, "R2");
		registroRepositorio.put(3, "R3");

		return true;
	}

	@Override
	public boolean altaCliente(String nombre) throws RemoteException {
		if (registroCliente.containsValue(nombre.toUpperCase())) {
			return false;
		} else {
			int codCliente = 1 + registroCliente.size();
			registroCliente.put(codCliente, nombre.toUpperCase());
			return true;
		}
	}

	@Override
	public boolean altaRepositorio(String nombre) throws RemoteException {
		nombre = nombre.toUpperCase();
		if (registroRepositorio.containsValue(nombre)) {
			return false;
		} else {
			int codRepositorio = 1 + registroRepositorio.size();
			registroRepositorio.put(codRepositorio, nombre.toUpperCase());
			return true;
		}
	}

	public boolean guardarAutenticacionRepositorio(int idSesionRepositorio, String nombre) throws RemoteException {
		nombre = nombre.toUpperCase();

		if (!registroRepositorio.containsValue(nombre)) {
			return false;
		} else {
			if (sesionRepositorio.containsValue(nombre)) {
				return false;
			} else {
				sesionRepositorio.put(idSesionRepositorio, nombre);
				return true;
			}
		}
	}

	public boolean borrarAutenticacionRepositorio(int cod) throws RemoteException {
		sesionRepositorio.remove(cod);
		return true;
	}

	public boolean guardarAutenticacionCliente(int idSesionCliente, String nombre) throws RemoteException {
		nombre = nombre.toUpperCase();
		if (!registroCliente.containsValue(nombre)) {

			return false;
		} else {
			if (sesionCliente.containsValue(nombre)) {
				return false;
			} else {
				ArrayList<Integer> repositoriosLogueados = new ArrayList<Integer>(sesionRepositorio.keySet());

				if (repositoriosLogueados.isEmpty()) {
					return false;
				} else {
					sesionCliente.put(idSesionCliente, nombre);
					int n = (int) (Math.random() * repositoriosLogueados.size());
					@SuppressWarnings("unused")
					int repositorioSeleccionado = repositoriosLogueados.get(n);
					clienteRepositorio.put(idSesionCliente, repositoriosLogueados.get(n));
					return true;
				}
			}
		}
	}

	public boolean borrarAutenticacionCliente(int cod) throws RemoteException {
		sesionCliente.remove(cod);
		return true;
	}

	@Override
	public Collection<String> listaClientes() throws RemoteException {
		Collection<String> clientes = (Collection<String>) registroCliente.values();
		return clientes;
	}

	@Override
	public ArrayList<String> listaClientesAutenticados() throws RemoteException {
		ArrayList<String> clientesA = new ArrayList<String>(sesionCliente.values());
		return clientesA;
	}

	@Override
	public Collection<String> listaRepositorios() throws RemoteException {
		Collection<String> repositorios = (Collection<String>) registroRepositorio.values();
		return repositorios;
	}

	@Override
	public int buscaRepositorio(int idSesionCliente) throws RemoteException {

		int idSesionRepositorio = 0;
		try {
			idSesionRepositorio = clienteRepositorio.get(idSesionCliente);
		} catch (Exception e) {
			System.out.println(clienteRepositorio.get(idSesionCliente));
		}
		return idSesionRepositorio;
	}

	@Override
	public void listarRepositoriosClientes() throws RemoteException {
		ArrayList<Integer> repositorios = new ArrayList<Integer>(sesionRepositorio.keySet());
		ArrayList<Integer> clientes = new ArrayList<Integer>(sesionCliente.keySet());

		if (repositorios.isEmpty()) {
			System.out.println();
			System.out.println("No existen asociaciones Repositorios-Clientes.");
			System.out.println();
		} else if (clientes.isEmpty()) {
			System.out.println();
			System.out.println("No existen asociaciones Repositorios-Clientes.");
			System.out.println();
		} else {
			for (int n : repositorios) {
				System.out.println();
				System.out.println();
				System.out.println("Repositorio: " + sesionRepositorio.get(n));
				System.out.print("Clientes asociados:");
				for (int c : clientes) {
					if (clienteRepositorio.get(c) == n) {
						System.out.print(" " + sesionCliente.get(c));
					}
				}
				System.out.println();
				System.out.println();
			}
		}
	}

	public ArrayList<String> recogeListaClientesRepo(int idSesionRepositorio) throws RemoteException {
		ArrayList<Integer> clientes = new ArrayList<Integer>(sesionCliente.keySet());
		ArrayList<String> listaClientesRepo = new ArrayList<String>();
		for (int c : clientes) {
			if (clienteRepositorio.get(c) == idSesionRepositorio) {

				listaClientesRepo.add(sesionCliente.get(c));
			}
		}
		return listaClientesRepo;
	}

	public boolean comprobarRepositorioCliente(int idSesionRepositorio, String nombreCliente) throws RemoteException {
		ArrayList<String> clientesDeRepositorio = new ArrayList<String>();
		clientesDeRepositorio = recogeListaClientesRepo(idSesionRepositorio);

		if (clientesDeRepositorio.contains(nombreCliente)) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<String> extraeListaFiheros(String nombreCliente) throws RemoteException {
		ArrayList<String> contenidoCarpeta = new ArrayList<String>();
		File carpeta = new File(nombreCliente);
		File[] ficheros = carpeta.listFiles();
		for (int x = 0; x < ficheros.length; x++) {
			contenidoCarpeta.add(ficheros[x].getName());
		}
		return contenidoCarpeta;
	}
}