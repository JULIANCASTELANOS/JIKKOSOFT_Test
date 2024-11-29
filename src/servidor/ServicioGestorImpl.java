package servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import servidor.ServicioDatosInterface;
import repositorio.ServicioSrOperadorInterface;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

	private static final long serialVersionUID = 1L;
	private static ServicioDatosInterface datos;
	private static ServicioSrOperadorInterface servicioSrOp;

	protected ServicioGestorImpl() throws RemoteException {
		super();
	}

	protected ServicioGestorImpl(String nombreLog) throws RemoteException {
		super();
	}

	@Override
	public String obtenerServicioClienteOperador(int id)
			throws RemoteException, NotBoundException, MalformedURLException {

		datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:2000/datos");
		int repositorio = datos.buscaRepositorio(id);
		String URL_repositorio = "rmi://localhost:2000/ServicioClOperador/" + repositorio;
		return URL_repositorio;
	}

	@Override
	public String obtenerServicioServidorOperador(int idSesionCliente)
			throws RemoteException, NotBoundException, MalformedURLException {
		datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:2000/datos");
		int repositorio = datos.buscaRepositorio(idSesionCliente);
		String direccionRepositorioCliente = "rmi://localhost:2000/ServicioSrOperador/" + repositorio;
		return direccionRepositorioCliente;
	}

	@Override
	public void bajarFicheroPaso1(int SesionCliente, String archivo, String nombreCliente)
			throws RemoteException, NotBoundException, MalformedURLException {
		String direccionRepositorio = obtenerServicioServidorOperador(SesionCliente);
		datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:2000/datos");
		int repositorio = datos.buscaRepositorio(SesionCliente);
		servicioSrOp = (ServicioSrOperadorInterface) Naming.lookup(direccionRepositorio);
		servicioSrOp.bajarFicheroPaso2(repositorio, archivo, nombreCliente);
	}

	public ArrayList<String> listarClientesSistema() throws RemoteException, NotBoundException, MalformedURLException {
		ArrayList<String> listaClientes = new ArrayList<String>();
		listaClientes = datos.listaClientesAutenticados();
		return listaClientes;
	}

	public boolean buscarFicherosClientes(int idSesionRepositorio, String nombreCliente)
			throws RemoteException, MalformedURLException, NotBoundException {
		ArrayList<String> ListaFicheros = new ArrayList<String>();
		if (datos.comprobarRepositorioCliente(idSesionRepositorio, nombreCliente)) {

			ListaFicheros = datos.extraeListaFiheros(nombreCliente);
			servicioSrOp = (ServicioSrOperadorInterface) Naming
					.lookup("rmi://localhost:2000/ServicioSrOperador/" + idSesionRepositorio);
			servicioSrOp.mostrarListaFicheros(ListaFicheros);
			return true;
		} else {
			return false;
		}
	}
}