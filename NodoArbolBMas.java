import java.util.ArrayList;
import java.util.List;

public class NodoArbolBMas {

    // Atributos.
    private boolean esHoja;
    private List<Integer> llaves;
    private List<NodoArbolBMas> hijos; // Vacía si es una hoja.
    private ArrayList<Carro> carros; // Datos. (Puede ser simplemente un ArrayList de Strings. Debe ser null si es un nodo interno.)
    private NodoArbolBMas hojaSig;// Enlace. (Es una instancia de la misma clase. Debe ser null si es un nodo interno.)

    // Métodos.
    // Constructor. Debe modificarse para que el nodo sea capaz de almacenar los datos.
    public NodoArbolBMas(boolean esHoja) {
        this.esHoja = esHoja;
        this.llaves = new ArrayList<>();
        this.hijos = new ArrayList<>();
        if(esHoja == true) { // Se confirma si la ArrayList "carros" es verdadera como hoja, se crean datos.
            this.carros = new ArrayList<>();
        } else { //Si "carros" no es una hoja, no se ingresan datos y es un conector.
            this.carros = null;
        }
        this.hojaSig = null; //Enlace.
    }


    // Getters.
    public boolean esHoja() {
        return esHoja;
    }

    public List<Integer> getLlaves() {
        return llaves;
    }

    public List<NodoArbolBMas> getHijos() {
        return hijos;
    }

    public ArrayList<Carro> getCarros() {
        return carros;
    }

    public NodoArbolBMas getHojaSig() {
        return hojaSig;
    }

    // Setters.
    public void setLlaves(List<Integer> llaves) {
        this.llaves = llaves;
    }

    public void setHijos(List<NodoArbolBMas> hijos) {
        this.hijos = hijos;
    }

    public void setCarros(ArrayList<Carro> carros) {
        this.carros = carros;
    }

    public void setHojaSig(NodoArbolBMas hojaSig) {
        this.hojaSig = hojaSig;
    }
}