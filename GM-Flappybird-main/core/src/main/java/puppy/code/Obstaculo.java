package puppy.code;


import com.badlogic.gdx.math.Rectangle;

import puppy.code.Colisiones.Colision;
import puppy.code.Colisiones.Enemigo;
import puppy.code.Colisiones.Tubo;
import puppy.code.Screens.EndScreen;
import puppy.code.Screens.GameScreen;

public class Obstaculo {
	private Colision[] colisiones;

	public Obstaculo(Asset ast) {
		super();
		this.colisiones = new Colision[] {
				new Tubo(ast.getTuboTex(), 350, 512),
		        new Tubo(ast.getTuboTex(), 550, 512),
		        new Enemigo(ast.getEnemyFrames(), ast.getTuboTex().getWidth(), 750, 512)

		};	
	}
	
	public Colision[] getColisiones() { return this.colisiones;}
	
	public void actualizarColision(float dt,FlappyGameMenu game, GameScreen screen) {
		
		boolean colisiona = false;
		
		for (Colision p : this.getColisiones()) {
            p.update(dt);
            if (p.fueraDePantalla()) {
                float max = 0;
                for (Colision other : this.getColisiones()) if (other.getX() > max) max = other.getX();
                p.reposicionar(max + 200);
            }
            for(Rectangle b :p.getBounds()) {
            	if(screen.getBird().getBounds().overlaps(b)) {
            		colisiona = true;
            		break;
            	}
            }
            
            if (colisiona == true) {
            	screen.setGameOver(true);
                if (screen.getScore() > game.getHigherScore()) {game.setHigherScore(screen.getScore());
                }

            }
         // --- PUNTAJE: cuando el obstáculo cruza la X del pájaro (birdX) ---
            if (!screen.getGameOver()) {
                float birdX = screen.getBird().pos.x;

                // Centro del obstáculo AHORA (ya actualizaste p.update(dt))
                float centroAhora = p.getX() + p.getAncho() / 2f;

                // Centro del obstáculo ANTES del update:
                // Como p.x disminuye en update (x -= vel*dt), el valor previo es:
                float centroAntes = centroAhora + p.getVelocidad() * dt;

                // Si el centro del obstáculo pasó de estar a la derecha del pájaro
                // a estar a su izquierda, entonces lo "cruza" y sumamos.
                if (centroAntes >= birdX && centroAhora < birdX) {
                    screen.setScore(screen.getScore() + 1);

                    if (screen.getScore() >= 3) {
                        if (screen.getScore() > game.getHigherScore()) {
                            game.setHigherScore(screen.getScore());
                        	}
                        game.setScreen(new EndScreen(game, screen, screen.getScore()));
                        return;
                    	}
                	}
            	}


		
			}
	
		}
}
