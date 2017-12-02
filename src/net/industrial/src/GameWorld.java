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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.List;

public class GameWorld extends GameState {
    private ArrayList<BackgroundTiles> backgroundTilesList;
    private ArrayList<Tile> tiles;
    private int heightLevel;
    private GameCamera camera;
    private Player player;
    private int score = 0;
    private List<Bat> bats = new ArrayList<>();

    public static final Vector2f GRAVITY = new Vector2f(0, -0.000060f);
    public static SpriteSheet SMOKE, TILES, BAT, EXPLOSION;
    public static Font FONT, FONT_LARGE;

    private boolean init = false;

    @Override
    public void init(Game game) throws GrasslandException {

        FONT = new Font(new SpriteSheet("res/font.png", 10, 11).scale(2f));
        FONT_LARGE = new Font(new SpriteSheet("res/font.png", 10, 11).scale(12f));
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
        //genTiles();
        heightLevel--;
        //genTiles();


        heightLevel--;

        camera = new GameCamera(this);
        addCamera(camera);
        activateCamera(camera);
        setLighting(false);
        setPerspective(false);

    }

    public void addTile(Tile tile) {
        addObject(tile);
        tiles.add(tile);
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
    }

    public boolean getTileAt(int x, int y, int z){
        boolean flag = false;
        for (Tile t : tiles) {
            if (t.getTileX() == x && t.getTileY() == y && t.getTileZ() == z) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void genTiles() throws GrasslandException {
        Random r = new Random();
        int side = heightLevel % 4;
        for (int i = 1; i < 9; i++) {
            for(int j = 1; j < 9; j++) {
                if (r.nextFloat() > 0.75f) {
                    if (side == 0) {
                        if(!(getTileAt(i, j + (heightLevel - 4) * 8 - 1, 9)))
                            addTile((new Tile(i, j + (heightLevel - 4) * 8, 9, heightLevel, this)));
                    }
                    if (side == 1) {
                        if(!(getTileAt(9, j + (heightLevel - 4) * 8 - 1, i)))
                            addTile((new Tile(9, j + (heightLevel - 4) * 8, i, heightLevel, this)));
                    }
                    if (side == 2) {
                        if(!(getTileAt(i, j + (heightLevel - 4) * 8 - 1, 0)))
                            addTile((new Tile(i, j + (heightLevel - 4) * 8, 0, heightLevel, this)));
                    }
                    if (side == 3) {
                        if(!(getTileAt(0, j + (heightLevel - 4) * 8 - 1, i)))
                            addTile((new Tile(0, j + (heightLevel - 4) * 8, i, heightLevel, this)));
                    }
                }
            }
        }
        if (side == 0)
            addTile(new Tile(9,(heightLevel - 3) * 8 + 1,9,heightLevel + 1, this));
        if (side == 1)
            addTile(new Tile(9,(heightLevel - 3) * 8 + 1,0,heightLevel + 1, this));
        if (side == 2)
            addTile(new Tile(0,(heightLevel - 3) * 8 + 1,0,heightLevel + 1, this));
        if (side == 3)
            addTile(new Tile(0,(heightLevel - 3) * 8 + 1,9,heightLevel + 1, this));
    }

    @Override
    public void update(Game game, int delta) throws GrasslandException {
        if (!init) {
            player = new Player(Main.ORIGIN.add(new Vector3f(0f, 0.2f, 9f * Main.BLOCK_SIZE)), this);
            addObject(player);
            init = true;
        }
        camera.update(game, delta);
        addObject(new BeaconSmoke(this, new Vector3f()));
        if (camera.getPosition().y > 0.1 + (heightLevel - 7) * 0.4) {
            heightLevel++;
            backgroundTilesList.set((heightLevel - 1) % 4,new BackgroundTiles(heightLevel - 5));
            genTiles();

            Iterator<Tile> it = tiles.iterator();
            while (it.hasNext()) {
                Tile tile = it.next();
                if (tile.getHeightLevel() < heightLevel - 7) {
                    tile.kill();
                    it.remove();
                }
            }
        }

        if (player.getY() < camera.getPosition().y - Main.BLOCK_SIZE * 30) {
            game.addState(new ScoreState(score));
            game.enterState(2);
        }
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.setBackgroundColour(1f, 1f, 1f);
        for (BackgroundTiles b : backgroundTilesList) b.render(graphics);
        String scoreString = Integer.toString(score);
        if (scoreString.length() == 1) scoreString = "0" + scoreString;
        graphics.drawString(FONT_LARGE, scoreString, 20, 20);
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

    public void incrementScore() {
        score++;
    }

    @Override
    public int getId() {
        return 1;
    }

    public void addBat(Bat bat) {
        bats.add(bat);
        addObject(bat);
    }

    public void resetBats() {
        for (Bat bat : bats) {
            if (!bat.willDie()) bat.kill();
        }
        bats = new ArrayList<>();
    }

    public void decrementScore() {
        score--;
        if (score < 0) score = 0;
    }
}

