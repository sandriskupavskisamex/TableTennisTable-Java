package training.tabletennistable;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeagueRenderer {
    private static int MaxNameLength = 17;
    private static int BoxWidth = MaxNameLength + 2;
    private static String Boundary = StringUtils.repeat("-", BoxWidth);
    private static String EmptyName = "|" + StringUtils.repeat(" ", MaxNameLength) + "|";

    public String render(League league)
    {
        List<LeagueRow> rows = league.getRows();

        if (rows.isEmpty())
        {
            return "No players yet";
        }

        List<String> renderedRows = IntStream.range(0, rows.size())
                .mapToObj(index -> renderRow(rows.get(index), index, rows.size()))
                .collect(Collectors.toList());

        System.out.println("X" + String.join("\r\n", renderedRows));
        return String.join("\r\n", renderedRows);
    }

    private String renderRow(LeagueRow row, int rowIndex, int totalRows)
    {
        String rowBoundary = String.join(" ", new ArrayList<>(Collections.nCopies(row.getMaxSize(), Boundary)));
        List<String> formattedNames = row.getPlayers().stream()
                .map(name -> "|" + formatName(name) + "|")
                .collect(Collectors.toList());
        int rowsRemaining = totalRows - rowIndex;
        int paddingLength = (BoxWidth + 1) / 2 * rowsRemaining;
        String padding = StringUtils.repeat(" ", paddingLength);
        List<String> emptyNames = new ArrayList<>(Collections.nCopies(row.getMaxSize() - formattedNames.size(), EmptyName));
        String allNames = String.join(" ", Stream.concat(formattedNames.stream(), emptyNames.stream()).collect(Collectors.toList()));

        return padding + rowBoundary + "\r\n" +
            padding + allNames + "\r\n" +
            padding + rowBoundary;
    }

    private String formatName(String name)
    {
        if (name.length() > MaxNameLength)
        {
            return name.substring(0, MaxNameLength - 3) + "...";
        }

        int leftPad = (MaxNameLength - name.length()) / 2;
        return StringUtils.rightPad(StringUtils.leftPad(name, name.length() + leftPad), MaxNameLength);
    }
}
