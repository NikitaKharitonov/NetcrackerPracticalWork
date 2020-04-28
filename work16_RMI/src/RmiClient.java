import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RmiClient {
    // ссылка на удалённый объект
    static RmiServerIntf obj = null;

    public static void main(String[] args) throws InterruptedException {
        boolean cont;
        Scanner scanner = new Scanner(System.in);

        do {
            cont = false;
            boolean success = false;
            System.out.println("Connecting to Server...");

            // пытаться получить удалённый объект
            do {
                try {
                    obj = (RmiServerIntf) Naming.lookup("//localhost/RmiServer");
                    success = true;
                } catch (NotBoundException | MalformedURLException | RemoteException e) {
                    Thread.sleep(3000);
                }
            } while (!success);

            System.out.print("Input first operand: ");
            double a = scanner.nextDouble();
            System.out.print("Input second operand: ");
            double b = scanner.nextDouble();

            // вызвать метод удалённого объекта
            try {
                Task task = new Task(a, b, Task.Operation.SUM);
                Task completedTask = obj.calculate(task);
                System.out.println("Operation: " + completedTask.getOperation());
                System.out.println("Result: " + completedTask.getResult());
            } catch (RemoteException e) {
                System.err.println("Failed to invoke a remote method: " + e);
            }

            System.out.print("Continue? (y/n): ");
            String ans = scanner.next();
            if (ans.charAt(0) == 'y')
                cont = true;
        } while(cont);
    }
}
