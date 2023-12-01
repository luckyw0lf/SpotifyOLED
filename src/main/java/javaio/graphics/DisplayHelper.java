package javaio.graphics;

import java.util.ArrayList;
import java.util.List;

public class DisplayHelper
{
    public static String[] splitIntoLines(String title, int maxLineLength)
    {
        String[] words = title.split("\\s+");
        StringBuilder currentLine = new StringBuilder();
        List<String> lines = new ArrayList<>();

        for (String word : words)
        {
            if (currentLine.length() + word.length() + 1 <= maxLineLength)
            {
                if (currentLine.length() > 0)
                {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
            else
            {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        if (currentLine.length() > 0)
        {
            lines.add(currentLine.toString());
        }

        return lines.toArray(new String[0]);
    }
}
