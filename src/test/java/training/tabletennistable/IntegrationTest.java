package training.tabletennistable;

import org.junit.Assert;
import org.junit.Test;

public class IntegrationTest
{
    @Test
    public void testPrintsEmptyGame()
    {
        var app = CreateApp();

        Assert.assertEquals("No players yet", app.sendCommand("print"));
    }

    private App CreateApp()
    {
        return new App(new League(), new LeagueRenderer(), new FileService());
    }
}
