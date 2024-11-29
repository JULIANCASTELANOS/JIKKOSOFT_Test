package repositorio;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServicioSrOperadorInterface extends Remote{
	public void crearCarpeta(String nombre) throws RemoteException;
	public void bajarFicheroPaso2(int repositorioCliente, String archivo, String nombreCliente)throws RemoteException;
	public void mostrarListaFicheros(ArrayList<String> ListaFicheros) throws RemoteException;
}
