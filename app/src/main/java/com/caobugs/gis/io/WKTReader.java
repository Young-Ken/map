package com.caobugs.gis.io;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.GeometryFactory;
import com.caobugs.gis.geometry.LineString;
import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.geometry.Point;
import com.caobugs.gis.geometry.primary.Geometry;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

public class WKTReader
{
    private static final String EMPTY = "EMPTY";
    private static final String COMMA = ",";
    private static final String L_PAREN = "(";
    private static final String R_PAREN = ")";
    private static final String NAN_SYMBOL = "NaN";
    private StreamTokenizer tokenizer;

    public Geometry read(String wellKnownText) throws Exception
    {
        StringReader reader = new StringReader(wellKnownText);
        try
        {
            return read(reader);
        } finally
        {
            reader.close();
        }
    }

    public Geometry read(Reader reader) throws Exception
    {
        tokenizer = new StreamTokenizer(reader);
        tokenizer.resetSyntax();
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars(128 + 32, 255);
        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('-', '-');
        tokenizer.wordChars('+', '+');
        tokenizer.wordChars('.', '.');
        tokenizer.whitespaceChars(0, ' ');
        tokenizer.commentChar('#');

        try
        {
            return readGeometryTaggedText();
        } catch (IOException e)
        {
            throw new Exception(e.toString());
        }
    }

    private Geometry readGeometryTaggedText() throws Exception
    {
        String type = null;

        try
        {
            type = getNextWord();
        } catch (IOException e)
        {
            return null;
        } catch (Exception e)
        {
            return null;
        }

        if (type.equalsIgnoreCase("POINT"))
        {
            return readPointText();
        } else if (type.equalsIgnoreCase("LINESTRING"))
        {
            return readLineStringText();
        } else if (type.equalsIgnoreCase("POLYGON"))
        {
            return readLinearRingText();
        }
        parseErrorWithLine("Unknown geometry type: " + type);
        // should never reach here
        return null;
    }

    private String getNextWord() throws Exception
    {
        int type = tokenizer.nextToken();
        switch (type)
        {
            case StreamTokenizer.TT_WORD:

                String word = tokenizer.sval;
                if (word.equalsIgnoreCase(EMPTY))
                    return EMPTY;
                return word;

            case '(':
                return L_PAREN;
            case ')':
                return R_PAREN;
            case ',':
                return COMMA;
        }
        parseErrorExpected("word");
        return null;
    }

    private Geometry readLineStringText() throws Exception
    {
        ArrayList<Coordinate> points = getPoints();
        if (points.size() < 2)
        {
            new Exception("readLineStringText() :线的点数少于2" + points.size());
        }

        Coordinate[] coordinates = points.toArray(new Coordinate[points.size()]);
        LineString line = new LineString(coordinates);
        return line;
    }

    private Geometry readLinearRingText() throws Exception
    {

        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY))
        {
            return new LinearRing(new Coordinate[0]);
        }
        // ArrayList holes = new ArrayList();

        LineString line = (LineString) readLineStringText();
        nextToken = getNextCloserOrComma();

        Coordinate[] coordinates = new Coordinate[line.getNumPoints()];


        if (line.getNumPoints() < 3)
        {
            new Exception("readLinearRingText() : 线的点数不能少于3" + line.getNumPoints());
        }

        for (int i = 0; i < line.getNumPoints(); i++)
        {
            coordinates[i] = line.getCoordinateN(i);
        }

        LinearRing ring = new LinearRing(coordinates);

        return ring;
    }

    private Geometry readPointText() throws Exception
    {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY))
        {
            return new Point(null);
        }
        Point point = new Point(getPoint());
        getNextCloser();
        return point;
    }

    private String getNextCloser() throws Exception
    {
        String nextWord = getNextWord();
        if (nextWord.equals(R_PAREN))
        {
            return nextWord;
        }
        parseErrorExpected(R_PAREN);
        return null;
    }

    private Coordinate getPoint() throws Exception
    {
        Coordinate point = new Coordinate();
        point.x = getNextNumber();
        point.y = getNextNumber();
        return point;
    }

    private ArrayList<Coordinate> getPoints() throws Exception
    {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY))
        {
            return new ArrayList<Coordinate>();
        }
        ArrayList points = new ArrayList<Coordinate>();
        points.add(getPoint());
        nextToken = getNextCloserOrComma();
        while (nextToken.equals(COMMA))
        {
            points.add(getPoint());
            nextToken = getNextCloserOrComma();
        }
        return points;
    }

    private double getNextNumber() throws Exception
    {
        int type = tokenizer.nextToken();
        switch (type)
        {
            case StreamTokenizer.TT_WORD:
            {
                if (tokenizer.sval.equalsIgnoreCase(NAN_SYMBOL))
                {
                    return Double.NaN;
                } else
                {
                    try
                    {
                        return Double.parseDouble(tokenizer.sval);
                    } catch (NumberFormatException ex)
                    {
                        parseErrorWithLine("Invalid number: " + tokenizer.sval);
                    }
                }
            }
        }
        parseErrorExpected("number");
        return 0.0;
    }

    private String getNextEmptyOrOpener() throws Exception
    {
        String nextWord = getNextWord();
        if (nextWord.equals(EMPTY) || nextWord.equals(L_PAREN))
        {
            return nextWord;
        }
        parseErrorExpected(EMPTY + " or " + L_PAREN);
        return null;
    }

    private void parseErrorExpected(String expected) throws Exception
    {
        if (tokenizer.ttype == StreamTokenizer.TT_NUMBER)
            new IllegalAccessError("Unexpected NUMBER token");
        if (tokenizer.ttype == StreamTokenizer.TT_EOL)
            new IllegalAccessError("Unexpected EOL token");

        String tokenStr = tokenString();
        parseErrorWithLine("Expected " + expected + " but found " + tokenStr);
    }

    private void parseErrorWithLine(String msg) throws Exception
    {
        throw new Exception(msg + " (line " + tokenizer.lineno() + ")");
    }

    private String getNextCloserOrComma() throws Exception
    {
        String nextWord = getNextWord();
        if (nextWord.equals(COMMA) || nextWord.equals(R_PAREN))
        {
            return nextWord;
        }
        parseErrorExpected(COMMA + " or " + R_PAREN);
        return null;
    }

    private String tokenString()
    {
        switch (tokenizer.ttype)
        {
            case StreamTokenizer.TT_NUMBER:
                return "<NUMBER>";
            case StreamTokenizer.TT_EOL:
                return "End-of-Line";
            case StreamTokenizer.TT_EOF:
                return "End-of-Stream";
            case StreamTokenizer.TT_WORD:
                return "'" + tokenizer.sval + "'";
        }
        return "'" + (char) tokenizer.ttype + "'";
    }
}
