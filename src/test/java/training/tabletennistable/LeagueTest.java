package training.tabletennistable;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LeagueTest {

    @Test
    public void testAddPlayer()
    {
        // Given
        League league = new League();

        // When
        league.addPlayer("Bob");

        // Then
        List<LeagueRow> rows = league.getRows();
        Assert.assertEquals(1, rows.size());
        List<String> firstRowPlayers = rows.get(0).getPlayers();
        Assert.assertEquals(1, firstRowPlayers.size());
        Assert.assertThat(firstRowPlayers, IsCollectionContaining.hasItem("Bob"));
    }

    @Test
    public void testGetRows() {

        // Given
        League league = new League();

        // When
        league.addPlayer("Bob");

        // Then
        Assert.assertEquals(1, league.getRows().size());

        // Then
        league.addPlayer("Stacy");

        // Then
        Assert.assertEquals(2, league.getRows().size());
    }

    @Test
    public void testRecordWin() {
        // Given
        League league = new League();

        league.addPlayer("Bob");
        league.addPlayer("Alice");

        // Whe
        league.recordWin("Alice", "Bob");

        // Then
        Assert.assertEquals("Alice", league.getWinner());
        Assert.assertEquals("Bob", league.getRows().get(1).getPlayers().get(0));
    }

    @Test
    public void getWinner() {

        // Given
        League league = new League();

        league.addPlayer("Brian");
        league.addPlayer("Tom");
        league.addPlayer("Yorkie");

        // When
        league.recordWin("Yorkie", "Brian");

        // Then
        Assert.assertEquals("Yorkie", league.getWinner());

    }
}
