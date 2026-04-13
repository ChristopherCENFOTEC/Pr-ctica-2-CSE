import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArbolBMas arbol = new ArbolBMas(4);

        arbol.insertar(10, new Carro("BYD", "Sealion", 2023));
        arbol.insertar(20, new Carro("Mazda", "RX-7", 1997));
        arbol.insertar(5, new Carro("Toyota", "Supra", 1984));
        arbol.insertar(6, new Carro("Dodge", "Challenger", 1969));
        arbol.insertar(15, new Carro("Range Rover", "Defender", 2020));
        arbol.insertar(30, new Carro("Ford", "Bronco", 2007));
        arbol.insertar(25, new Carro("BYD", "Seagull", 2024));

        Scanner scan = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("Buen día, bienvenido al menú de la agencia Chris Roadster. ");
            System.out.println("Por favor, ingrese un dígito: ");
            System.out.println("1. Ingrese al menú de observación de modelos.(Ver el árbolBMás.) ");
            System.out.println("2. Ingrese al menú recorrido de autos. ");
            System.out.println("3. Ingrese al menú eliminación y observación de autos. ");
            System.out.println("4. Salir del menú. ");

            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {
                case 1 -> {
                    arbol.imprimirArbol();
                }
            case 2 -> {
                System.out.println("Registre el inicio de la búsqueda por favor. ");
                int inicio = scan.nextInt();
                System.out.println("Registre el final de la búsqueda por favor. ");
                int fin = scan.nextInt();
                ArrayList<Carro> resultado = arbol.recorridoPorRango(inicio, fin);
                System.out.println("Los carros hallados son: ");
                for (Carro i : resultado){
                    System.out.println(i);
                }
            }
            case 3 -> {
                System.out.println("Registre la llave a eliminar por favor. ");
                int llave = scan.nextInt();
                arbol.eliminar(llave);
                System.out.println("La llave " + llave + " ha  sido eliminada correctamente del árbol. ");
                arbol.imprimirArbol();
            }
            case 4 -> {
                System.out.println("Ha salido del sistema.");
                break;
            }
            default -> System.out.println("Por favor elija una opción de las anteriormente mostradas.");
        }
    }while (opcion != 4);
    }
}