package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.GameState;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.resources.Sprite;

public class IndustrialGames extends GameState {
    Sprite logo;

    @Override
    public void init(Game game) throws GrasslandException {
        logo = new Sprite("res/logo.png").scale(4f);
    }

    @Override
    public void update(Game game, int i) throws GrasslandException {

    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.setBackgroundColour(0.8f, 0.8f, 0.8f);
        graphics.drawImage(logo, game.getWidth() / 2 - logo.getWidth() / 2,
                game.getHeight() / 2 - logo.getHeight() / 2);
    }

    @Override
    public int getId() {
        return 0;
    }
}
