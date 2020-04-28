import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerIntf extends Remote {
    Task calculate(Task task) throws RemoteException;
}
