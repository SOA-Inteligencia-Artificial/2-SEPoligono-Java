package sistemaexperto;

import java.util.ArrayList;

public class BaseDeHechos {
	// Lista de los hechos
    protected ArrayList<IHecho> hechos; 
    public ArrayList<IHecho> getHechos () { 
        return hechos; 
    } 
  
    // Constructor
    public BaseDeHechos() { 
        hechos = new ArrayList(); 
    } 

    // Vaciar la base
    public void Vaciar() {
        hechos.clear();
    }
    
    // Agregar un hecho
    public void AgregarHecho(IHecho hecho) {
        hechos.add(hecho);
    }
    
    // Buscar un hecho a partir de su nomnre, null si no existe
    public IHecho Buscar(String nombre) {
        for(IHecho hecho : hechos) {
            if (hecho.Nombre().equals(nombre)) {
                return hecho;
            }
        }
        return null;
    }
    
    // Busca el valor de un hecho, null si el hecho no existe
    public Object RecuperarValorHecho(String nombre) {
        for(IHecho hecho : hechos) {
            if (hecho.Nombre().equals(nombre)) {
                return hecho.Valor();
            }
        }
        return null;
    }
}
