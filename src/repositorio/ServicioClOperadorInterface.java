package repositorio;
import java.rmi.Remote;
import java.rmi.RemoteException;
import common.Fichero;
import java.util.ArrayList;

public interface ServicioClOperadorInterface extends Remote{
	public boolean subirFichero(Fichero fichero) throws RemoteException;
	public boolean borrarFichero(String archivo,String carpeta) throws RemoteException;
	public ArrayList<String> listarFicherosCliente(String nombreCliente) throws RemoteException;
}