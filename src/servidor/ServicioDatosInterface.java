package servidor;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.ArrayList;


public interface ServicioDatosInterface extends Remote{
	public boolean rellenarClientesRepositorios() throws RemoteException;
	public boolean altaCliente(String nombreCliente) throws RemoteException;
    public boolean altaRepositorio(String nombreRepositorio) throws RemoteException;
    public boolean guardarAutenticacionRepositorio(int idSesionRepositorio,String nombre) throws RemoteException;
    public boolean guardarAutenticacionCliente(int idSesionCliente,String nombre) throws RemoteException;
    public boolean borrarAutenticacionRepositorio(int cod) throws RemoteException;
    public boolean borrarAutenticacionCliente(int cod) throws RemoteException;
    public Collection<String> listaClientes() throws RemoteException;
    public ArrayList<String> listaClientesAutenticados() throws RemoteException;
    public Collection<String> listaRepositorios() throws RemoteException;
    public void listarRepositoriosClientes() throws RemoteException;
    public int buscaRepositorio(int idSesionCliente) throws RemoteException;
    public ArrayList<String> recogeListaClientesRepo(int idSesionRepositorio) throws RemoteException;
    public boolean comprobarRepositorioCliente(int idSesionRepositorio, String nombreCliente) throws RemoteException;
    public ArrayList<String> extraeListaFiheros(String nombreCliente) throws RemoteException;
}
