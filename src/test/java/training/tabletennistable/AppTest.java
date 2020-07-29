package training.tabletennistable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AppTest {
    @Mock
    LeagueRenderer renderer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPrintsCurrentState()
    {
        League league = new League();
        Mockito.when(renderer.render(league)).thenReturn("Rendered League");

        App app = new App(league, renderer, null);

        Assert.assertEquals("Rendered League", app.sendCommand("print"));
    }

    @Test
    public void testAddPlayer()
    {
        League league = Mockito.mock(League.class);
        App app = new App(league, renderer, null);

        Assert.assertEquals("Added player Alice", app.sendCommand("add player Alice"));
        verify(league).addPlayer("Alice");
    }

    @Test
    public void recordWin()
    {
        League league = Mockito.mock(League.class);

        App app = new App(league, renderer, null);

        app.sendCommand("record win Alice Bob");

        verify(league).recordWin("Alice", "Bob");
        Assert.assertEquals("Recorded Alice win against Bob", app.sendCommand("record win Alice Bob"));

    }

    @Test
    public void testWinner()
    {
        League league = Mockito.mock(League.class);
        App app = new App(league, renderer, null);

        when(league.getWinner()).thenReturn("winner of the game");

        Assert.assertEquals("winner of the game", app.sendCommand("winner"));
        verify(league).getWinner();
    }

    @Test
    public void testSave()
    {
        FileService fileService = Mockito.mock(FileService.class);
        League league = Mockito.mock(League.class);
        String filepath = "file/file/file.csv";

        App app = new App(league, renderer, fileService);

        Assert.assertEquals("Saved " + filepath, app.sendCommand("save " + filepath));
        verify(fileService).save(eq(filepath), any(League.class));
    }

    @Test
    public void testLoad()
    {
        FileService fileService = Mockito.mock(FileService.class);
        League league = Mockito.mock(League.class);
        String filepath = "file/file/file.csv";

        App app = new App(league, renderer, fileService);

        Assert.assertEquals("Loaded " + filepath, app.sendCommand("load " + filepath));
        verify(fileService).load(eq(filepath));
    }

    @Test
    public void giveWrongCommand() {
        App app = new App(null, renderer, null);
        String wrongCommand = "THIS IS NOT RIGHT";

        Assert.assertEquals("Unknown command: " + wrongCommand, app.sendCommand(wrongCommand));
    }

    @Test
    public void addingPlayerWithInvalidPlayerNameThrowsException() {
        League league = new League();
        App app = new App(league, renderer, null);
        String invalidPlayerName = "&ASda@s3";
        String commandToAddPlayerWithInvalidPlayerName = "add player " + invalidPlayerName;
        String expectedErrorMessage = "Player name " + invalidPlayerName + " contains invalid";

        Assert.assertEquals(expectedErrorMessage, app.sendCommand(commandToAddPlayerWithInvalidPlayerName));
    }

}
