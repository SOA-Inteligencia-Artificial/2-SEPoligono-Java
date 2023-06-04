package sistemaexperto;

import java.util.ArrayList;

public class MotorInferencia {

	private BaseDeHechos bdh;
	private BaseDeReglas bdr;
	private IHM ihm;
	
	private int nivelMaxRegla;
	
	// Constructor
	public MotorInferencia(IHM _ihm) {
		ihm = _ihm;
		bdh = new BaseDeHechos();
		bdr = new BaseDeReglas();
	}
	
	// Pedimos valores enteros (mediante la IHM)
	int PedirValorEntero(String pregunta) {
		return ihm.PedirValorEntero(pregunta);
	}
	
	// Pedimos valores booleanos (mediante la IHM)
	boolean PedirValorBooleano(String pregunta) {
		return ihm.PedirValorBooleano(pregunta);
	}
	
	// Indica si una regla pasada como argumento ss aplicable. 
    // Si lo es, devuelve su nivel, si no devuelve -1
	int EsAplicable(Regla _r) {
        int nivelMax = -1;
        // Se verifica la veracidad de cada premisa
        for (IHecho f : _r.getPremisas()) {
            IHecho hechoEncontrado = bdh.Buscar(f.Nombre());
            if (hechoEncontrado == null) {
                // Este hecho no existe en base de hechos
                if (f.Pregunta() != null) {
                    hechoEncontrado = HechoFactory.Hecho(f, this);
                    bdh.AgregarHecho(hechoEncontrado);
                }
                else {
                    return -1;
                }
            }
            
            // El hecho existe en base (antes o creado), ¿pero con el valor correcto?
            if (!hechoEncontrado.Valor().equals(f.Valor())) {
                return -1;
            }
            else {
                nivelMax = Math.max(nivelMax, hechoEncontrado.Nivel());
            }
        }
        return nivelMax;
    }
	
	
	// Devuelve la primera regla aplicable de la base que se pasa  como argumento
    // Si hay una, rellena también el atributo de la clase (nivelMaxRegla)
    // si no devuelve null
    Regla BuscadorRegla(BaseDeReglas bdrLocale) {
        for(Regla r : bdrLocale.getReglas()) {
            int nivel = EsAplicable(r);
            if (nivel != -1) {
                nivelMaxRegla = nivel;
                return r;
            }
        }
        return null;
    }	
    
 // Algoritmo principal que permtite resolver un caso dado
    public void Resolver() {
        // Se copian todas las reglas
        BaseDeReglas bdrLocale = new BaseDeReglas();
        bdrLocale.setReglas(bdr.getReglas());
        
        // Se vacía la base de hechos
        bdh.Vaciar();
        
        // mientras existan reglas a aplicar
        Regla r = BuscadorRegla(bdrLocale);
        while(r!=null) {
            // Aplicar la regla
            IHecho nuevoHecho = r.conclusion;
            nuevoHecho.setNivel(nivelMaxRegla + 1);
            bdh.AgregarHecho(nuevoHecho);
            // Eliminar la regla de las posibles
            bdrLocale.Eliminar(r);
            // Buscar la siguiente regla aplicable
            r = BuscadorRegla(bdrLocale);
        }
        
        // Visualización de los resultados
        ihm.MostrarHechos(bdh.getHechos());
    }
    // CONTINUAMOS
 // Agregar una regla a la base a partir de su cadena
    // En forma :
    // Nombre : IF premisas THEN conclusion
    public void AgregarRegla(String str) {
        // Separación nombre:regla
        String[] nombreRegla = str.split(":");
        if (nombreRegla.length == 2) {
            String nombre = nombreRegla[0].trim();
            // Separación premisas THEN conclusión
            String regla = nombreRegla[1].trim();
            regla = regla.replaceFirst("^" + "IF", "");
            String[] premisasConclusion = regla.split("THEN");
            if (premisasConclusion.length == 2) {
                // Lectura de las premisas
                ArrayList<IHecho> premisas = new ArrayList();
                String[] premisasStr = premisasConclusion[0].split(" AND ");
                for(String cadena : premisasStr) {
                    IHecho premisa = HechoFactory.Hecho(cadena.trim());
                    premisas.add(premisa);
                }
                // Lectura de la conclusión
                String conclusionStr = premisasConclusion[1].trim();
                IHecho conclusion = HechoFactory.Hecho(conclusionStr);
                // Creación de la regla y adición a la base
                bdr.AgregarRegla(new Regla(nombre, premisas, conclusion));
            }
        }
    }
    // ACABA AQUI.
}
