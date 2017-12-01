package net.industrial.src.objects;

import net.industrial.grassland.Game;
import net.industrial.grassland.GameObject;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Animation;
import net.industrial.src.GameWorld;
import net.industrial.src.Main;

public class Explosion extends GameObject {
    private GameWorld world;
    private Animation explosion;

    public Explosion(GameWorld world, Vector3f position) {
        this.world = world;
        setPosition(position);
        explosion = new Animation(GameWorld.EXPLOSION, 0, 0, 2, 0, false, 12);
        explosion.start();
    }

    @Override
    public void update(Game game, int delta) throws GrasslandException {
        world.getCamera().screenShake(0.03f);
        explosion.update(delta);
        if (!explosion.isPlaying()) kill();
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.fillQuad(getPosition().add(world.getCamera().lookVector().scale(1f)),
                world.getCamera().lookVector(),
                world.getCamera().axisVector(),
                Main.BLOCK_SIZE * 2f,
                Main.BLOCK_SIZE * 2f,
                explosion.currentFrame());
    }
}
