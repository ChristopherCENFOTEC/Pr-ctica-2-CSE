public class Carro {
        //Atributos carros
        private String marca;
        private String modelo;
        private int anio;

    //Métodos carros
        //Constructor carros
        public Carro(String marca, String modelo, int anio) {
            this.marca = marca;
            this.modelo = modelo;
            this.anio = anio;
        }

        //Getters
    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnio() {
        return anio;
    }

    //Setters
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public String toString(){
            return "marca: " + marca +
                    "\n" + "modelo: " + modelo +
                    "\n" + "anio: " + anio;
    }
}