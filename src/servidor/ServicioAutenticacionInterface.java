package servidor;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
 
public interface ServicioAutenticacionInterface extends Remote {
	 public boolean registraCliente(String nombre) throws RemoteException,MalformedURLException, NotBoundException;
	 public boolean registraRepositorio(String nombreRepositorio) throws RemoteException,MalformedURLException, NotBoundException;
     public int autenticarCliente(String nombre) throws RemoteException, MalformedURLException, NotBoundException;
     public int autenticarRepositorio(String nombre) throws RemoteException,MalformedURLException, NotBoundException;
     public void desAutenticarCliente(int idSesionCliente) throws RemoteException,MalformedURLException, NotBoundException;
     public void desAutenticarRepositorio(int numeroSesionRepositorio) throws RemoteException,MalformedURLException, NotBoundException;
     public int dameRepositorio(int idSesionCliente) throws RemoteException;
     public ArrayList<String> listaClientesDeRepositorio(int idSesionRepositorio) throws RemoteException;
}
