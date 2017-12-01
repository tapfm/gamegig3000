package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.GameState;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Font;
import net.industrial.grassland.resources.SpriteSheet;
import net.industrial.grassland.scene.Camera;
import net.industrial.src.objects.BeaconSmoke;
import net.industrial.src.objects.Player;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class GameWorld extends GameState {
    private Font font;
    private ArrayList<BackgroundTiles> backgroundTilesList;
    private int heightLevel;
    private BackgroundTiles worldTiles;
    private Camera camera;

    public static final Vector3f GRAVITY = new Vector3f(0, -0.000075f, 0);
    public static SpriteSheet SMOKE;
    public static Font FONT;

    @Override
    public void init(Game game) throws GrasslandException {

        font = new Font(new SpriteSheet("res/font.png", 10, 11).scale(2f));

        backgroundTilesList = new ArrayList<>();

        backgroundTilesList.add(new BackgroundTiles(0, (new SpriteSheet("res/tiles.png",16,16)).scale(2f)));
        backgroundTilesList.add(new BackgroundTiles(1, (new SpriteSheet("res/tiles.png",16,16)).scale(2f)));
        backgroundTilesList.add(new BackgroundTiles(2, (new SpriteSheet("res/tiles.png",16,16)).scale(2f)));
        backgroundTilesList.add(new BackgroundTiles(3, (new SpriteSheet("res/tiles.png",16,16)).scale(2f)));

        heightLevel = backgroundTilesList.size();

        FONT = new Font(new SpriteSheet("res/font.png", 10, 11).scale(2f));
        SMOKE = new SpriteSheet("res/smoke.png", 16, 16);

        worldTiles = new BackgroundTiles(0, (new SpriteSheet("res/tiles.png",16,16)).scale(2f));
        camera = new GameCamera();
        addCamera(camera);
        activateCamera(camera);
        setLighting(false);
        setPerspective(false);
        addObject(new Player(new Vector3f(0, 1f, 0), this));
    }

    @Override
    public void update(Game game, int delta) throws GrasslandException {
        camera.update(game, delta);
        addObject(new BeaconSmoke(this, new Vector3f()));
        if (camera.getPosition().y > 0.1 + (heightLevel - 3) * 0.4) {
            heightLevel++;
            backgroundTilesList.set((heightLevel - 1) % 4,new BackgroundTiles(heightLevel - 1, (new SpriteSheet("res/tiles.png", 16, 16)).scale(2f)));
        }
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.setBackgroundColour(1f, 1f, 1f);
        graphics.drawString(font, "GAME HERE", 20, 20);
        for(BackgroundTiles b : backgroundTilesList)
            b.render(graphics);
        //worldTiles.render(graphics,2);
        // worldTiles.render(graphics);
        //graphics.fillQuad(new Vector3f(0f, 0f, 0.2f), new Vector3f(0, 0, 1f), new Vector3f(1f, 0f, 0f), 0.05f, 0.05f, new Sprite("res/tiles.png"));
    }

    @Override
    public int getId() {
        return 1;
    }
}
