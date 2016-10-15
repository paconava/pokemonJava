/*
 * Universidad Nacional Autonoma de MExico
 * IngenierIa en computaciOn
 * Integrantes:
 *  MartInez Ortiz SaUl Axel
 *  Nava Luján Francisco José
 * PROTECO, generaciOn 33
 * Java bAsico, proyecto 1
 * DescripciOn: VersiOn en java del juego de pokemon
 *  sin interfaz grafica
 * Recursos o fuentes:

 */

import java.util.Scanner;
/**
 * <descripciOn>
 * @author paco 
 * @author saul
*/
public class Torneo{
    protected Contrincante c1;
    protected Contrincante c2;
    protected Contrincante enTurno;
    protected equipo.Monstruo[] enUso;
    private Scanner sc;

    public Torneo( Contrincante c1, Contrincante c2 ){
	this.c1 = c1;
	this.c2 = c2;
    }

    protected Contrincante jugar(){
		enUso = new equipo.Monstruo[2];
		enUso[0] = enUso[1] = null;

		boolean hayGanador = false;
		sc = new Scanner( System.in );

		System.out.println("Bienvenido al torneo de");
		String combatientes = c1.nombre + " VS " + c2.nombre; 
		System.out.println("\t\t" + combatientes);
		System.out.print("\t\t");
		for( int i = 0; i < combatientes.length(); ++i ){
		    System.out.print("_");
		}
		System.out.println("");
		sc.nextLine();

		int i = 0;
		int opcion = -1;
		int contrincante = 0;
		boolean bandera;
		do{
		    System.out.println(equipo.Monstruo.SALTOS);
		    contrincante = i%2;

		    if( enUso[0] == null ){
			elegirMonstruo(0);
		    }
		    if( enUso[1] == null ){
			elegirMonstruo(1);
		    }

		    imprimirMonstruos();
		    System.out.println("\nEs el turno del jugador " + (1+contrincante) );
		    if( contrincante == 0 ){
			enTurno = c1;
			if( enUso[0].getEstado().compareTo("paralizado") == 0 ){
			    enUso[0].setEstado("ok");
			    bandera = false;
			}else{
			    bandera = true;
			}
		    }else{
			enTurno = c2;
			if( enUso[1].getEstado().compareTo("paralizado") == 0 ){
			    enUso[1].setEstado("ok");
			    bandera = false;
			}else{
			    bandera = true;
			}
		    }
		    
		    if( bandera ){
			System.out.println("¿Qué desea hacer?");
			imprimirMenu();
			do{
			    try{
				opcion = sc.nextInt();
				if( opcion >= 1 && opcion <= 3 ){
				    bandera = false;
				}
			    }catch( Exception ime ){}
			}while( bandera );

			switch( opcion ){
			    case 1:
					if( contrincante == 0 ){
					    enUso[0].ataque1(enUso[1]);
					}else{
					    enUso[1].ataque1(enUso[0]);
					}
				    break;

			    case 2:
					if( contrincante == 0 ){
					    enUso[0].ataque2(enUso[1]);
					}else{
					    enUso[1].ataque2(enUso[0]);
					}
				    break;

			    case 3:
			    	if(checarPocimas(contrincante)){
			    		System.out.println("¿Qué pocima desea usar?");
						imprimirPocimas(contrincante);
						elegirPocima(contrincante);
					    break;
			    	}else{
			    		System.out.println("Ya no tienes más pócimas");
			    		sc.nextLine();
			    		if(contrincante == 0){
			    			contrincante = 1;
			    		}else{
			    			contrincante = 0;
			    		}
			    		break;
			    	}
					
			}
			sc.nextLine();
		    }else{
			System.out.println("No hay turno para tu monstruo paralizado");
		    }

		    if( enUso[0].getEstado().compareTo("fuera de combate") == 0 ){
			System.out.println("El pokemon del jugador 1 ha caido");
			enUso[0] = null;
		    }
		    if( enUso[1].getEstado().compareTo("fuera de combate") == 0 ){
			System.out.println("El pokemon del jugador 2 ha caido");
			enUso[1] = null;
		    }

		    if( c1.monstruos.size() == 0 ){
			contrincante = 1;
			hayGanador = true;
		    }
		    if( c2.monstruos.size() == 0 ){
			contrincante = 1;
			hayGanador = true;
		    }
		    ++i;
		}while( !hayGanador );
		
		// RegresarA al ganador
		if( contrincante == 0 ){
		    return c1;
		}else{
		    return c2;
		}
    }

    private void elegirMonstruo( int contrincante ){
		System.out.println("El contrincante " + (contrincante+1) + " debe elegir otro monstruo para pelear");
		Contrincante c; 
		if( contrincante == 0 ){
		    c = c1;
		}else{
		    c = c2;
		}

		c.mostrarMonstruos();
		System.out.println("¿Qué monstruo desea usar?");
			
		int index = 0;;
		boolean bandera = true;
		do{
		    System.out.print("> ");
		    try{
			index = sc.nextInt();
			if( index >= 1 && index <= c.monstruos.size() ){
			    bandera = false;
			}
		    }catch(Exception ime){}
		}while(bandera);
		enUso[contrincante] = c.monstruos.get(index - 1);
		System.out.println("Has elegido a: \n\n" + enUso[contrincante] + "\n");
    }

    private void imprimirMenu(){
		System.out.println("\t\t 1) Ataque1");
		System.out.println("\t\t 2) Ataque2");
		System.out.println("\t\t 3) Usar pocima");
    }

    private void imprimirPocimas(int contrincante){
		Contrincante c; 
		if( contrincante == 0 ){
		    c = c1;
		}else{
		    c = c2;
		}

		c.mostrarPocimas();
    }

    private void imprimirMonstruos(){
		String cad1, cad2;
		System.out.println("Las estadisticas de los monstruos son:");
		System.out.printf("%-40s%-40s\n","Monstruo 1","Monstruo 2");
		cad1 = enUso[0].getClass().getName();
		cad1 = cad1.substring(cad1.lastIndexOf('.') +1 ); 
		cad2 = enUso[1].getClass().getName();
		cad2 = cad2.substring(cad2.lastIndexOf('.') +1 );
		System.out.printf("%-40s%-40s\n",cad1 + " " + enUso[0].getApodo(),cad2+" " +enUso[1].getApodo());
		System.out.printf("%-40s%-40s\n","HP restante: "+enUso[0].getHp(),"HP restante: "+enUso[1].getHp());
		System.out.printf("%-40s%-40s\n","Nivel: "+enUso[0].getNivel(),"Nivel: "+enUso[1].getNivel());
		System.out.printf("%-40s%-40s\n","Ataque: "+enUso[0].getAtaque(),"Ataque: "+enUso[1].getAtaque());
		System.out.printf("%-40s%-40s\n","Defensa: "+enUso[0].getDefensa(),"Defensa: "+enUso[1].getDefensa());
		System.out.printf("%-40s%-40s\n","Velocidad: "+enUso[0].getVelocidad(),"Velocidad: "+enUso[1].getVelocidad());
    }

    private void elegirPocima( int contrincante ){
		boolean b = true;
		Contrincante c;
		Pocima usable = new PocimaAtaque();

		if( contrincante == 0 ){
		    c = c1;
		}else{
		    c = c2;
		}
		int escogida = 0;
		do{
		    System.out.print("> ");
		    try{
			escogida = sc.nextInt();
			if( escogida >= 1 && escogida <= c2.pocimas.size() ){
			    b = false;
			}
		    }catch(Exception ime){}
		    sc.nextLine();
		}while(b);
		
		int index = 1;
		int remover = 0;
		for( Pocima p: c.pocimas ){
		    if( index == escogida ){
			usable = c.pocimas.get(index -1);
			remover = (index-1);
		    }
		    ++index;
		}

		usable.usarPocima(enUso[contrincante]);
		c.pocimas.remove(remover);
    }

    private boolean checarPocimas(int contrincante){
		Contrincante c;

		if( contrincante == 0 ){
		    c = c1;
		}else{
		    c = c2;
		}

		if(c.pocimas.size() == 0){
			return false;
		}else{
			return true;
		}
    }
}
