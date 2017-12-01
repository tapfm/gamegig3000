package net.industrial.src.objects;

import net.industrial.grassland.Game;
import net.industrial.grassland.GameObject;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Animation;
import net.industrial.src.GameWorld;
import net.industrial.src.Main;

import java.util.Random;

public class BeaconSmoke extends GameObject {
    private GameWorld world;
    private Vector3f velocity;
    private Animation smoke;
    private Random r;

    public BeaconSmoke(GameWorld world, Vector3f source) {
        this.world = world;
        r = new Random();
        int startFrame = r.nextInt(6);
        velocity = new Vector3f(0f, 0.00025f * (float) Math.random(), 0f);
        velocity = velocity.add(world.getCamera().axisVector()
                .scale((0.5f - (float) Math.random()) * 0.0001f));
        setPosition(source.add(world.getCamera().axisVector()
                .scale((0.5f - (float) Math.random()) * 0.05f)));
        smoke = new Animation(GameWorld.SMOKE, startFrame, 1, 7, 1, false, 12);
        smoke.start();

    }
    @Override
    public void update(Game game, int delta) throws GrasslandException {
        smoke.update(delta);
        setPosition(getPosition().add(velocity.scale(delta)));
        if (!smoke.isPlaying()) kill();
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.fillQuad(getPosition().add(world.getCamera().lookVector()
                .scale(-0.5f)),
                world.getCamera().lookVector(),
                world.getCamera().axisVector(),
                Main.BLOCK_SIZE,
                Main.BLOCK_SIZE,
                smoke.currentFrame());
    }
}
