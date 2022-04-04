package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;
import com.example.qrcodeteam30.modelclass.Game;
import org.junit.Test;

public class GameUnitTest {
    @Test
    public void getterAndSetterGameNameTest1() {
        Game game = new Game();
        game.setGameName("Fun Game");
        assertEquals("Fun Game", game.getGameName());
    }

    @Test
    public void getterAndSetterGameNameTest2() {
        Game game = new Game();
        game.setGameName("Default Server");
        assertEquals("Default Server", game.getGameName());
    }

    @Test
    public void getterAndSetterGameNameTest3() {
        Game game = new Game();
        game.setGameName("Pokemon Go");
        assertEquals("Pokemon Go", game.getGameName());
    }

    @Test
    public void getterAndSetterOwnerUsernameTest1() {
        Game game = new Game();
        game.setOwnerUsername("chubbysonnn");
        assertEquals("chubbysonnn", game.getOwnerUsername());
    }

    @Test
    public void getterAndSetterOwnerUsernameTest2() {
        Game game = new Game();
        game.setOwnerUsername("DQM2001");
        assertEquals("DQM2001", game.getOwnerUsername());
    }

    @Test
    public void getterAndSetterOwnerUsernameTest3() {
        Game game = new Game();
        game.setOwnerUsername("Happy Popcorn");
        assertEquals("Happy Popcorn", game.getOwnerUsername());
    }

    @Test
    public void getterAndSetterDateTest1() {
        Game game = new Game();
        game.setDate("Tue Mar  31 01:59:00 PST 2022");
        assertEquals("Tue Mar  31 01:59:00 PST 2022", game.getDate());
    }

    @Test
    public void getterAndSetterDateTest2() {
        Game game = new Game();
        game.setDate("Tue Dec  12 01:03:00 PST 2020");
        assertEquals("Tue Dec  12 01:03:00 PST 2020", game.getDate());
    }

    @Test
    public void getterAndSetterDateTest3() {
        Game game = new Game();
        game.setDate("Wed Apr  16 00:00:00 PST 2020");
        assertEquals("Wed Apr  16 00:00:00 PST 2020", game.getDate());
    }
}
