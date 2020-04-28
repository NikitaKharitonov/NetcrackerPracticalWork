import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {

    public RmiServer() throws RemoteException {
    }

    @Override
    public Task calculate(Task task) {
        task.calculate();
        return task;
    }

    public static void main(String[] args) {
        System.out.println("RMI server started");

        try { // специальный обработчик исключений для создания реестра
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            // ничего не делать, ошибка означает, что реестр уже существует
            System.out.println("java RMI registry already exists.");
        }

        try {
            // Создавать RmiServer
            RmiServer obj = new RmiServer();

            // Привязать этот экземпляр объекта к имени "RmiServer"
            Naming.rebind("//localhost/RmiServer", obj);

            System.out.println("PeerServer bound in registry");
        } catch (Exception e) {
            System.err.println("RMI server exception:" + e);
            e.printStackTrace();
        }
    }
}
