package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.GrasslandException;

public class Main extends Game {
    public Main() {
        super("Game Gig 3000", 800, 600, false);
    }

    @Override
    public void initStates() throws GrasslandException {
        addState(new IndustrialGames());
        enterState(0);
    }

    public static void main(String args[]) throws GrasslandException {
        Game game = new Main();
        game.init();
    }
}
