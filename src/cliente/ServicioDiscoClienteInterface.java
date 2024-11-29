package cliente;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import common.Fichero;
public interface ServicioDiscoClienteInterface extends Remote{
	public boolean bajarFicheroPaso3(Fichero fichero) throws RemoteException;
	public boolean listarFicherosCliente(int idRepos, String nombreCliente) throws RemoteException,MalformedURLException, NotBoundException;
}
