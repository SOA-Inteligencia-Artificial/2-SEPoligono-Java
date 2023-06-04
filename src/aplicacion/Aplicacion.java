package aplicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import sistemaexperto.IHecho;
import sistemaexperto.IHM;
import sistemaexperto.MotorInferencia;
import sistemaexperto.Regla;

public class Aplicacion implements IHM {
	
	public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.Run();
    }

    // Funcionamiento del programa, con el ejemplo de las polígonos
    public void Run() {
        // Creación del motor
        System.out.println("** Creación del motor **");
        MotorInferencia m = new MotorInferencia(this);
        
        // Agregar las reglas
        System.out.println("** Agregar las reglas **");
        m.AgregarRegla("R1 : IF (Orden=3(¿Cuál es el orden?)) THEN  Triángulo");
        m.AgregarRegla("R2 : IF (Triángulo AND Ángulo Recto(¿La figura tiene al menos un ángulo recto?)) THEN Triángulo Rectángulo");
        m.AgregarRegla("R3 : IF (Triángulo AND Lados Iguales=2(¿Cuántos lados iguales tiene la figura?)) THEN Triángulo Isósceles");
        m.AgregarRegla("R4 : IF (Triángulo rectángulo AND Triángulo Isósceles) THEN Triángulo Rectángulo Isósceles");
        m.AgregarRegla("R5 : IF (Triángulo AND Lados Iguales=3(¿Cuántos lados iguales tiene la figura?)) THEN Triángulo Equilátero");
        m.AgregarRegla("R6 : IF (Orden=4(¿Cuál es el orden?)) THEN Cuadrilátero");
        m.AgregarRegla("R7 : IF (Cuadrilátero AND Lados Paralelos=2(¿Cuántos lados paralelos entre sí - 0, 2 o 4?)) THEN Trapecio");
        m.AgregarRegla("R8 : IF (Cuadrilátero AND Lados Paralelos=4(¿Cuántos lados paralelos entre sí - 0, 2 o 4?)) THEN Paralelogramo");
        m.AgregarRegla("R9 : IF (Paralelogramo AND Ángulo Recto(¿La figura tiene al menos un ángulo recto?)) THEN Rectángulo");
        m.AgregarRegla("R10 : IF (Paralelogramo AND Lados Iguales=4(¿Cuántos lados iguales tiene la figura?)) THEN Rombo");
        m.AgregarRegla("R11 : IF (Rectángulo AND Rombo THEN Cuadrado");
         
        // Resolución
        while(true) {
            System.out.println("\n** Resolución **");
            m.Resolver();
        }
    }
	
	// Solicita un valor entero al usuario
	@Override
	public int PedirValorEntero(String pregunta) {
		System.out.println(pregunta);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			return Integer.decode(br.readLine());
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	// Solicita un valor booleano, con si (verdadero) o no (falso)
	// Los errores se ignoran (devuelve falso)
	@Override
	public boolean PedirValorBooleano(String pregunta) {
		try {
			System.out.println(pregunta + " (si, no)");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String res = br.readLine();
			return (res.equals("si"));
		}
		catch (Exception e) {
			return false;
		}
	}
	
	 // Muestra la lista de hechos de nivel >0 y por orden decreciente de nivel
    @Override
    public void MostrarHechos(ArrayList<IHecho> hechos) {
        String res = "Solución(s) encontrada(s) : \n"; 
        Collections.sort(hechos,(IHecho f1, IHecho f2) -> {
            return Integer.compare(f2.Nivel(), f1.Nivel());
        });
        for(IHecho f : hechos) {
            if (f.Nivel() != 0) {
                res += f.toString() + "\n";
            }
        }
        System.out.println(res);
    }

    // Muestra las reglas contenidas en la base
    @Override
    public void MostrarReglas(ArrayList<Regla> reglas) {
        String res = "";
        for(Regla r : reglas) {
            res += r.toString() + "\n";
        }
        System.out.println(res);
    }
}
