import java.util.ArrayList;

public class ArbolBMas {

    // Atributos.
    private final int orden;
    private NodoArbolBMas raiz;

    // Métodos.
    // Constructor.
    public ArbolBMas(int orden) {
        this.orden = orden;
        raiz = new NodoArbolBMas(true);
    }

    // Inserción de una llave en el árbol B+.
    public void insertar(int llave, Carro carro) {
        if(this.raiz.getLlaves().size() == orden - 1) {
            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(false);
            nuevaRaiz.getHijos().add(this.raiz);
            dividirHijo(nuevaRaiz, 0);
            this.raiz = nuevaRaiz;
        }
        insertarNoLleno(this.raiz, llave, carro);
    }

    // Inserción privada de una llave en un nodo que no está lleno.
    // Si el nodo no es una hoja, la inserción de una nueva llave puede implicar la división de uno de sus hijos.
    private void insertarNoLleno(NodoArbolBMas nodo, int llave, Carro carro) {
        int i = nodo.getLlaves().size() - 1;
            if(nodo.esHoja()) {
                while(i >= 0 && llave < nodo.getLlaves().get(i)) {
                    i--;
                }
                nodo.getLlaves().add(i + 1, llave);
                nodo.getCarros().add(i + 1, carro);
        } else {
            while(i >= 0 && llave < nodo.getLlaves().get(i)) {
                i--;
            }
            i++;
            NodoArbolBMas hijo = nodo.getHijos().get(i);
            if(hijo.getLlaves().size() == orden - 1) {
                dividirHijo(nodo, i);
                if(llave > nodo.getLlaves().get(i)) {
                    i++;
                }
            }
            insertarNoLleno(nodo.getHijos().get(i), llave, carro);
        }
    }

    // Dividir un nodo que está lleno.
    private void dividirHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas nodoLleno = padre.getHijos().get(indice);
        NodoArbolBMas nuevoNodo = new NodoArbolBMas(nodoLleno.esHoja());
        int mitad = orden / 2;

        if (nodoLleno.esHoja()) {
            // En nodos hoja, se mantiene la llave media en ambos lados.
            nuevoNodo.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(mitad, nodoLleno.getLlaves().size())));
            nodoLleno.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(0, mitad)));
            
            // Dividir los carros con sus respectivas claves.
            nuevoNodo.setCarros(new ArrayList<>(nodoLleno.getCarros().subList(mitad, nodoLleno.getCarros().size())));
            nodoLleno.setCarros(new ArrayList<>(nodoLleno.getCarros().subList(0, mitad)));

            //Pasar a la siguiente hoja por medio del enlace creado en el nodo.
            nuevoNodo.setHojaSig(nodoLleno.getHojaSig());
            nodoLleno.setHojaSig(nuevoNodo);

            // El padre recibe como separador la primera llave del nuevo nodo.
            int llaveMedia = nuevoNodo.getLlaves().get(0);
            padre.getLlaves().add(indice, llaveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
        else {
            // En nodos internos, la llave media sube y se elimina del hijo.
            int claveMedia = nodoLleno.getLlaves().get(mitad);
            nuevoNodo.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(mitad + 1, nodoLleno.getLlaves().size())));
            nodoLleno.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(0, mitad)));

            // Mover los hijos del nodo dividido.
            nuevoNodo.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(mitad + 1, nodoLleno.getHijos().size())));
            nodoLleno.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(0, mitad + 1)));

            padre.getLlaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
    }

    // Impresión del árbol como parte de la interfaz pública del árbol.
    public void imprimirArbol() {
        imprimirNodo(raiz, "", true);
    }

    // Impresión recursiva privada de los nodos a partir de uno inicial.
    private void imprimirNodo(NodoArbolBMas nodo, String indentacion, boolean esUltimo) {
        System.out.println(indentacion + "+- " + (nodo.esHoja() ? "Hoja" : "Interno") + " Nodo: " + nodo.getLlaves());
        indentacion += esUltimo ? "   " : "|  ";
        for(int i = 0; i < nodo.getHijos().size(); i++) {
            imprimirNodo(nodo.getHijos().get(i), indentacion, i == (nodo.getHijos().size() - 1));
        }
    }

    // Los métodos de búsqueda pueden retornar el dato en caso de que la búsqueda sea exitosa.
    // Búsqueda de un nodo como parte de la interfaz pública del árbol.
    public boolean buscar(int llave) {
        return buscarNodo(raiz, llave);
    }

    // Búsqueda recursiva privada de un nodo a partir de uno inicial.
    private boolean buscarNodo(NodoArbolBMas nodo, int llave) {
        int i = 0;
        while(i < nodo.getLlaves().size() && llave > nodo.getLlaves().get(i)) {
            i++;
        }
        if(i < nodo.getLlaves().size() && llave == nodo.getLlaves().get(i)) {
            return true;
        }
        if(nodo.esHoja()) {
            return false;
        } else {
            return buscarNodo(nodo.getHijos().get(i), llave);
        }
    }

    // Eliminar. Eliminación sencilla que no implica fusión de los nodos.

    // Busca el padre para poder balancear el árbol correctamente despúes de eliminar una llave o combinar nodos.
    private NodoArbolBMas encontrarPadre(NodoArbolBMas subRaiz, NodoArbolBMas hijo){
        // Busca si el nodo es hoja dentro del árbol.
        if (subRaiz.esHoja())
            return null;

        // Se revisan los hijos de los nodos(padre).
        for (NodoArbolBMas k : subRaiz.getHijos()){
            // Si se encuentra el nodo, el nodo en el cuál iniciaste la búsqueda es el padre del objetivo.
            if (k == hijo)
                return subRaiz;

            // Si no se encuentra, se sigue buscando.
            NodoArbolBMas posible = encontrarPadre(k, hijo);

            // Si lo hallan, envían el resultado.
            if (posible != null)
                return posible;
        }
        // No se encontró ningún padre.
        return null;
    }

    // Su función es complementaria, combina los nodos para el balanceo.
    private void combinar(NodoArbolBMas izquierda, NodoArbolBMas derecha, NodoArbolBMas padre, int indicePadre) {
        // Combina las llaves y los carros de ls llaves en los nodos.
        izquierda.getLlaves().addAll(derecha.getLlaves());
        izquierda.getCarros().addAll(derecha.getCarros());

        // Procura que el enlace se mantenga en pie al combinar los nodos.
        izquierda.setHojaSig(derecha.getHojaSig());

        // Elimina la llave del padre debido al cambio.
        padre.getLlaves().remove(indicePadre);
        padre.getHijos().remove(derecha);

        // Si el padre no funciona como nodo suficiente, se repite el proceso escalando 1 escalón.
        int min = (int) Math.ceil((orden - 1) / 2.0);
        if (padre != raiz && padre.getLlaves().size() < min){
            ajustarNodo(padre);
        }
    }

    // Método para rebalancear el árbol.
    private void ajustarNodo(NodoArbolBMas nodo) {
        // Busca el nodo padre(la raiz del subarbol) de la llave eliminada o a eliminar.
        NodoArbolBMas padre = encontrarPadre(raiz, nodo);

        // Si es la raiz y no hay padre, retorna un null.
        if (padre == null)
            return;

        // Muestra la posición de nodo dentro del árbol.
        int indice = padre.getHijos().indexOf(nodo);

        // Verifica si el nodo que se encontró previamente tiene nodos a su lado dentro del subarbol.
        NodoArbolBMas izquierdo = (indice > 0) ? padre.getHijos().get(indice - 1) : null;
        NodoArbolBMas derecho = (indice < padre.getHijos().size() - 1) ? padre.getHijos().get(indice + 1) : null;
        // El número mínimo aceptado de llaves por nodo, el método "Math. ceil" redondea hacia el mayor.
        int min = (int) Math.ceil((orden - 1) / 2.0);

        // Si del lado izquierdo hay nodos para balancear disponibles, se utilizan.
        if (izquierdo != null && izquierdo.getLlaves().size() > min) {
            // Busca la llave y el carro del final del nodo del lado izquierdo, la mueve al inicio del nodo a balancear.
            nodo.getLlaves().add(0, izquierdo.getLlaves().remove(izquierdo.getLlaves().size() - 1));
            nodo.getCarros().add(0, izquierdo.getCarros().remove(izquierdo.getCarros().size() - 1));
            // Actualiza la clave debido al balanceo del árbol.
            padre.getLlaves().set(indice - 1, nodo.getLlaves().get(0));
            return;
        }
        // Si del lado derecho hay nodos para balancear disponibles, se utilizan.
        if (derecho != null && derecho.getLlaves().size() > min) {
            // Busca la llave y el carro del final del nodo del lado derecho, la mueve al inicio del nodo a balancear.
            nodo.getLlaves().add(0, derecho.getLlaves().remove(0));
            nodo.getCarros().add(0, derecho.getCarros().remove(0));
            // Actualiza la clave debido al balanceo del árbol.
            padre.getLlaves().set(indice, derecho.getLlaves().get(0));
            return;
        }
        // Si no se pueden mover nodos, fusiona los nodos compatibles entre si desde el lado izquierdo.
        if (izquierdo != null){
            combinar(izquierdo, nodo, padre, indice -1);
        } else if (derecho != null) { // Si no se puede combinar con el izquierdo, los combina con nodos del lado derecho.
            combinar(nodo, derecho, padre, indice);
        }
    }

    // Método para eliminar una llave.
    public boolean eliminar(int llave){
        // Busca la hoja a eliminar.
        NodoArbolBMas nodo = buscarHoja(raiz, llave);
        if (nodo == null) return false;
        int posicionLlave = -1;
        for(int i = 0; i < nodo.getLlaves().size(); i++){
            if (nodo.getLlaves().get(i) == llave){
                posicionLlave = i;
                break;
            }
        }
        // Si la hoja no se encuentra, retorna un "false" y no se elimina nada.
        if (posicionLlave == -1) {
            System.out.println("La hoja no se encuentra dentro del árbol.");
            return false;
        }
        // Si encuentra la llave y el carro asociado, los elimina del árbol.
        nodo.getLlaves().remove(posicionLlave);
        nodo.getCarros().remove(posicionLlave);

        //Si el eliminado es una raiz:
        if (nodo == raiz){

            // Reinicia la raíz del árbol por medio de un enlace a una hoja de referencia.
            if(nodo.getLlaves().isEmpty()){
                raiz = new NodoArbolBMas(true);
            }
            // Si se elimina la raiz exitosmente.
            System.out.println("El carro ha sido eliminado.");
            return true;
        }
        // El número mínimo aceptado de llaves por nodo, el método "Math. ceil" redondea hacia el mayor.
        int min = (int) Math.ceil((orden-1) / 2.0);
        // Si no hay suficientes llaves, se reforma el árbol para que quede balanceado.
        if (nodo.getLlaves().size() < min){
            ajustarNodo(nodo);
        }
        return true;
    }

    // Recorrido por rango. Muestra los datos correspondientes al rango recuperado.
    //Método de lógica para el reccorrido.
    private NodoArbolBMas buscarHoja(NodoArbolBMas nodo, int llave){
        while (!nodo.esHoja()){
            int i = 0;
            while(i < nodo.getLlaves().size() && llave >= nodo.getLlaves() .get(i)){
                i++;
            }
            nodo = nodo.getHijos().get(i);
        }
        return nodo;
    }

    //    Método de recorrer rangos.
    public ArrayList<Carro> recorridoPorRango(int inicio, int fin){
        ArrayList<Carro> resultado = new ArrayList<>();
        NodoArbolBMas nodo = buscarHoja(raiz, inicio);
        while (nodo != null){
            for (int i = 0; i < nodo.getLlaves().size(); i++){
                int llave = nodo.getLlaves().get(i);
                if (llave >= inicio && llave <= fin){
                    resultado.add(nodo.getCarros().get(i));
                }
                if (llave > fin){
                    return resultado;
                }
            }
            nodo = nodo.getHojaSig();
        }
        return resultado;
    }
}