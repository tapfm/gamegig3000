package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.GameState;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector2f;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Font;
import net.industrial.grassland.resources.SpriteSheet;
import net.industrial.grassland.scene.Camera;
import net.industrial.src.objects.BeaconSmoke;
import net.industrial.src.objects.Player;
import net.industrial.src.objects.Tile;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class GameWorld extends GameState {
    private Font font;
    private ArrayList<BackgroundTiles> backgroundTilesList;
    private ArrayList<Tile> tiles;
    private int heightLevel;
    private GameCamera camera;

    public static final Vector2f GRAVITY = new Vector2f(0, -0.000075f);
    public static SpriteSheet SMOKE, TILES;
    public static Font FONT;

    @Override
    public void init(Game game) throws GrasslandException {

        font = new Font(new SpriteSheet("res/font.png", 10, 11).scale(2f));
        FONT = new Font(new SpriteSheet("res/font.png", 10, 11).scale(2f));
        SMOKE = new SpriteSheet("res/smoke.png", 16, 16);
        TILES = new SpriteSheet("res/tiles.png", 16, 16);

        backgroundTilesList = new ArrayList<>();

        backgroundTilesList.add(new BackgroundTiles(0));
        backgroundTilesList.add(new BackgroundTiles(1));
        backgroundTilesList.add(new BackgroundTiles(2));
        backgroundTilesList.add(new BackgroundTiles(3));

        heightLevel = backgroundTilesList.size();

        tiles = new ArrayList<>();
        genBaseTiles();



        camera = new GameCamera(this);
        addCamera(camera);
        activateCamera(camera);
        setLighting(false);
        setPerspective(false);
        addObject(new Player(new Vector3f(0, 1f, 0), this));
    }

    public void genBaseTiles() throws GrasslandException {
        for (int i = 0; i < 9; i++) {
            tiles.add(new Tile(i,0,9,0,this));
            tiles.add(new Tile(i,0,0,0,this));
            tiles.add(new Tile(9,0, i,0,this));
            tiles.add(new Tile(0,0, i,0,this));
        }
        tiles.add(new Tile(0,0,0,0,this));
        tiles.add(new Tile(9,0,0,0,this));
        tiles.add(new Tile(0,0,9,0,this));
        tiles.add(new Tile(9,0,9,0,this));
        tiles.add(new Tile(9,1,9,0,this));
    }

    public void genTiles() throws GrasslandException {

    }

    @Override
    public void update(Game game, int delta) throws GrasslandException {
        camera.update(game, delta);
        addObject(new BeaconSmoke(this, new Vector3f()));
        if (camera.getPosition().y > 0.1 + (heightLevel - 3) * 0.4) {
            heightLevel++;
            backgroundTilesList.set((heightLevel - 1) % 4,new BackgroundTiles(heightLevel - 1));
        }
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.setBackgroundColour(1f, 1f, 1f);
        for(BackgroundTiles b : backgroundTilesList)
            b.render(graphics);
        for(Tile t : tiles)
            t.render(game,graphics);
    }

    public boolean locked() {
        return camera.isTurning();
    }

    @Override
    public int getId() {
        return 1;
    }
}
