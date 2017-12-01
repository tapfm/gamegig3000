package net.industrial.src;

import net.industrial.grassland.*;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector2f;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Font;
import net.industrial.grassland.resources.SpriteSheet;
import net.industrial.src.objects.Bat;
import net.industrial.src.objects.BeaconSmoke;
import net.industrial.src.objects.Player;
import net.industrial.src.objects.Tile;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class GameWorld extends GameState {
    private Font font;
    private ArrayList<BackgroundTiles> backgroundTilesList;
    private ArrayList<Tile> tiles;
    private int heightLevel;
    private GameCamera camera;
    private Player player;

    public static final Vector2f GRAVITY = new Vector2f(0, -0.000065f);
    public static SpriteSheet SMOKE, TILES, BAT, EXPLOSION;
    public static Font FONT;

    @Override
    public void init(Game game) throws GrasslandException {

        font = new Font(new SpriteSheet("res/font.png", 10, 11).scale(2f));
        FONT = new Font(new SpriteSheet("res/font.png", 10, 11).scale(2f));
        SMOKE = new SpriteSheet("res/smoke.png", 16, 16);
        TILES = new SpriteSheet("res/tiles.png", 16, 16);
        BAT = new SpriteSheet("res/bat.png", 16, 16);
        EXPLOSION = new SpriteSheet("res/explosion.png", 32, 32);

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
        player = new Player(Main.ORIGIN.add(new Vector3f(0f, 0.2f, 0f)), this);
        addObject(player);
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
        addObject(tile);
    }

    public void genBaseTiles() throws GrasslandException {
        for (int i = 0; i < 9; i++) {
            addTile(new Tile(i,0,9,0,this));
            addTile(new Tile(i,0,0,0,this));
            addTile(new Tile(9,0, i,0,this));
            addTile(new Tile(0,0, i,0,this));
        }
        addTile(new Tile(0,0,0,0,this));
        addTile(new Tile(9,0,0,0,this));
        addTile(new Tile(0,0,9,0,this));
        addTile(new Tile(9,0,9,0,this));
        addTile(new Tile(9,1,9,0,this));
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
        if (game.getInput().isKeyPressed(Keyboard.KEY_B))
            addObject(new Bat(this, new Vector3f(0.25f, 0.05f, 0f)));
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.setBackgroundColour(1f, 1f, 1f);
        for (BackgroundTiles b : backgroundTilesList) b.render(graphics);
        graphics.drawString(font, player.tileX() + " " + player.tileY() + " " + player.tileZ(), 20, 20);
    }

    public boolean locked() {
        return camera.isTurning();
    }

    public GameObject getPlayer() {
        return player;
    }

    public Tile tileAt(int x, int y, int z) {
        for (Tile tile : tiles) {
            if (tile.isAt(x, y, z)) return tile;
        }

        return null;
    }

    public List<Tile> tilesAround(int x, int y, int z) {
        List<Tile> returnList = new ArrayList<Tile>();
        for (Tile tile : tiles) {
            if (tile.isAt(x, y, z) ||
                    tile.isAt(x + 1, y, z) ||
                    tile.isAt(x + 1, y, z + 1) ||
                    tile.isAt(x + 1, y, z - 1) ||
                    tile.isAt(x, y, z + 1) ||
                    tile.isAt(x, y, z - 1) ||
                    tile.isAt(x - 1, y, z + 1) ||
                    tile.isAt(x - 1, y, z) ||
                    tile.isAt(x - 1, y, z - 1)) {
               returnList.add(tile);
            }
        }
        return returnList;
    }

    @Override
    public int getId() {
        return 1;
    }
}
