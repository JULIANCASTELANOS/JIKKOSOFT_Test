package servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

import servidor.ServicioAutenticacionInterface;
import servidor.ServicioDatosInterface;
import servidor.ServicioGestorInterface;
import repositorio.ServicioSrOperadorInterface;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {

	private static final long serialVersionUID = 1007197529122012L;
	private static int sesion = new Random().nextInt();
	private static ServicioDatosInterface datos;
	private static ServicioGestorInterface servicioGestor;
	private static ServicioSrOperadorInterface accesoMetodosSrOp;

	protected ServicioAutenticacionImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	protected ServicioAutenticacionImpl(String nombreLog) throws RemoteException {
		super();
	}

	// @Override
	public int autenticarCliente(String nombre) throws RemoteException, MalformedURLException, NotBoundException {

		int idSesionCliente = getSesion();
		try {
			datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:2000/datos");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (datos.guardarAutenticacionCliente(idSesionCliente, nombre)) {
			servicioGestor = (ServicioGestorInterface) Naming.lookup("rmi://localhost:2000/gestor");
			String direccionServicioSrOp = servicioGestor.obtenerServicioServidorOperador(idSesionCliente);
			accesoMetodosSrOp = (ServicioSrOperadorInterface) Naming.lookup(direccionServicioSrOp);
			accesoMetodosSrOp.crearCarpeta(nombre);
			return idSesionCliente;
		} else {
			return 0;
		}
	}

	// @Override
	public void desAutenticarCliente(int idSesionCliente)
			throws RemoteException, MalformedURLException, NotBoundException {

		try {
			datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:2000/datos");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		datos.borrarAutenticacionCliente(idSesionCliente);
	}

	public int autenticarRepositorio(String nombre) throws RemoteException {

		int idSesionRepositorio = getSesion();

		try {
			datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:2000/datos");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (datos.guardarAutenticacionRepositorio(idSesionRepositorio, nombre)) {
			return idSesionRepositorio;
		} else {
			return 0;
		}
	}

	public static int getSesion() {
		if (sesion < 0) {
			return (sesion = -1 * sesion);
		} else {
			return ++sesion;
		}
	}

	public int dameRepositorio(int idSesionCliente) throws RemoteException {
		return datos.buscaRepositorio(idSesionCliente);
	}

	public ArrayList<String> listaClientesDeRepositorio(int idSesionRepositorio) throws RemoteException {
		ArrayList<String> listaClientesRepo = new ArrayList<String>();
		listaClientesRepo = datos.recogeListaClientesRepo(idSesionRepositorio);
		return listaClientesRepo;
	}

	public boolean registraCliente(String nombre) throws RemoteException, MalformedURLException, NotBoundException {

		datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:2000/datos");
		if (datos.altaCliente(nombre))
			return true;
		else
			return false;
	}

	public boolean registraRepositorio(String nombreRepositorio)
			throws RemoteException, MalformedURLException, NotBoundException {
		datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:2000/datos");
		if (datos.altaRepositorio(nombreRepositorio))
			return true;
		else
			return false;
	}

	public void desAutenticarRepositorio(int numeroSesionRepositorio)
			throws RemoteException, MalformedURLException, NotBoundException {
		datos.borrarAutenticacionRepositorio(numeroSesionRepositorio);
	}
}
