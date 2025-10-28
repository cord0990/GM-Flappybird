package puppy.code;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

import puppy.code.Colisiones.Colision;
import puppy.code.Colisiones.Tubo;

public class Obstaculo {
	private Colision[] colisiones;

	public Obstaculo(Asset ast) {
		super();
		this.colisiones = new Colision[] {
				new Tubo(ast.getTuboTex(), 350, 512),
		        new Tubo(ast.getTuboTex(), 550, 512),
		        new Tubo(ast.getTuboTex(), 750, 512)
		};
			
	}
	
	public Colision[] getColisiones() { return this.colisiones;}
	
	public void actualizarColision(float dl) {
		
	}
}
