FLAPPY ASSETS + CLEANUP
========================
Se han agregado assets básicos para tu Flappy Bird:
- assets/flappy/background.png
- assets/flappy/ground.png
- assets/flappy/pipe.png
- assets/flappy/bird0.png
- assets/flappy/bird1.png
- assets/flappy/bird2.png
- assets/flappy/digits/0..9.png

También se eliminaron referencias al módulo de 'lluvia' comentando las líneas en GameScreen.java
y se borraron archivos del juego de lluvia (drop.png, bucket.png, drop.wav, dropBad.png, rain.mp3).
Si algún import o variable de 'Lluvia' persiste, el compilador lo indicará; remuévelo.

Para integrar los assets en LibGDX (sin atlas):
----------------------------------------------
Texture bg = new Texture(Gdx.files.internal("flappy/background.png"));
Texture ground = new Texture(Gdx.files.internal("flappy/ground.png"));
Texture pipeTex = new Texture(Gdx.files.internal("flappy/pipe.png"));
Texture[] birdFrames = new Texture[]{
    new Texture("flappy/bird0.png"),
    new Texture("flappy/bird1.png"),
    new Texture("flappy/bird2.png")
};

