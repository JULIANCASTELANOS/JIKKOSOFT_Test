package repositorio;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import common.Fichero;
import common.Herramienta;
import cliente.ServicioDiscoClienteInterface;

public class ServicioSrOperadorImpl extends UnicastRemoteObject implements ServicioSrOperadorInterface {
	private static final long serialVersionUID = 1L;
	private static ServicioDiscoClienteInterface discoCliente;

	protected ServicioSrOperadorImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void crearCarpeta(String nombre) throws RemoteException {
		File nombreCarpeta = new File(nombre);
		nombreCarpeta.mkdir();
	}

	@Override
	public void bajarFicheroPaso2(int repositorioCliente, String archivo, String nombreCliente) throws RemoteException {
		int tamanoRutaTemporal = 0;
		URL rutaca = ServicioSrOperadorImpl.class.getProtectionDomain().getCodeSource().getLocation();
		String[] rutaTemporal = rutaca.toString().split("/");
		StringBuilder rutaFinal = new StringBuilder();

		if (Herramienta.comprobarSufijoCadena(rutaTemporal))
			tamanoRutaTemporal = rutaTemporal.length - 1;
		else
			tamanoRutaTemporal = rutaTemporal.length;

		for (int i = 1; i < tamanoRutaTemporal; i++) {
			rutaFinal.append(rutaTemporal[i]);
			rutaFinal.append("/");
		}

		String ruta = rutaFinal.toString() + nombreCliente;
		Fichero fichero = new Fichero(ruta, archivo, nombreCliente);

		try {
			discoCliente = (ServicioDiscoClienteInterface) Naming
					.lookup("rmi://localhost:2000/DiscoCliente/" + repositorioCliente);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		discoCliente.bajarFicheroPaso3(fichero);
	}

	public void mostrarListaFicheros(ArrayList<String> ListaFicheros) throws RemoteException {
		System.out.println();
		System.out.println("Mostrando ficheros: ");
		for (int contador = 0; contador < ListaFicheros.size(); contador++) {
			System.out.println(ListaFicheros.get(contador));
		}
		System.out.println();
		System.out.println("Fin de listado.");
		System.out.println("--------------------------");
	}
}