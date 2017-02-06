/*
 * jMemorize - Learning made easy (and fun) - A Leitner flashcards tool
 * Copyright(C) 2004-2006 Riad Djemili
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 1, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package jmemorize.core.io;

import java.sql.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmemorize.core.Card;
import jmemorize.core.Category;
import jmemorize.core.FormattedText;
import jmemorize.core.Lesson;



/**
 * A class for importing and exporting character-separated-values (CSV).
 * 
 * @author djemili
 */
public class SqlBuilder
{
    public static final String FRONTSIDE_COL = "Frontside";
    public static final String FLISIDE_COL   = "Flipside";
    public static final String CATEGORY_COL  = "Category";
    public static final String LEVEL_COL     = "Level";
    
    /**
     * Is thrown when the header is missing or malformatted.
     */
    public static class BadHeaderException extends Exception
    {
        public BadHeaderException(String message)
        {
            super(message);
        }
    }
    
    /**
     * Exports the given lesson to a CSV-file with given delimiter and given
     * character set.
     */
    public static String SepcialChange(String card){
    	card=card.replaceAll("'", "\\\\'");
    	card=card.replaceAll("~", "\\\\~");
    	card=card.replaceAll(",", "\\\\,");
    	return card;
    }
    
    public static void exportLesson(Connection in, Lesson lesson) throws SQLException
    {
        try
        {
    		Statement stmt = in.createStatement();
    		//ResultSet rs = stmt.executeQuery("SELECT * FROM memorize");
            //CsvWriter writer = new CsvWriter(out, delimiter, charset);
            //writeHeader(writer);
            int i=1;
            stmt.executeQuery("truncate memorize");
            List<Card> cards = lesson.getRootCategory().getCards();
            for (Card card : cards)
            {
            	String query="INSERT INTO memorize(id,Question,Anwser,explanation,category,Level) VALUES (";
            	query=query+i+", ";
            	query=query+"'"+SepcialChange(card.getFrontSide().getText().getFormatted())+"', ";
            	query=query+"'"+SepcialChange(card.getBackSide().getText().getFormatted())+"', ";
                query=query+"'"+"', ";
                if (lesson.getRootCategory() == card.getCategory())
                	query=query+"'"+""+"', ";
                else
                	query=query+"'"+card.getCategory().getName()+"', ";
                
                	query=query+"'"+Integer.toString(card.getLevel())+"')";
                
                System.out.println("query까진 왔음");
                System.out.println(query);
                stmt.executeUpdate(query);
                i++;
                System.out.println(i+"증가");
            }
            
            System.out.println("for문 ");
            
            //writer.close();
        }
        catch (Exception e)
        {
            System.out.println("DB Export 실패"+e.getMessage());
        }
    }
    
    
    /**
     * Parses the given file that holds text values that are delimited by given
     * delimiter. The values are used to contruct a lesson.
     * @param delimiter the delimiter that is used to separate values in the
     * file.
     * @param charset the character set that the file contents are using.
     * @param file the file that should be read.
     * @param charset the character set that is used in the file. Use
     * <code>null</code> to use the default charset.
     * @param the lesson to which the contents of the file will be added.
     * 
     * @throws IOException if the file couldn't be read or the values are
     * malformatted.
     */
    public static void importLesson(Connection in, Lesson lesson) throws SQLException
    {
        //CsvReader reader = new CsvReader(in, delimiter, charset);
		Statement stmt = in.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM memorize");
		Category rootCategory = lesson.getRootCategory();

        
        Map<String, Category> categories = new HashMap<String, Category>();
        
        List<Category> childCategories = rootCategory.getChildCategories();
        for (Category category : childCategories)
        {
            categories.put(category.getName(), category);
        }
        

/*            reader.readHeaders();
            
            String[] headers = reader.getHeaders();
            validateHeader(headers);*/
            
            while (rs.next())
            {
            	System.out.println("가져오고있습니다");
                FormattedText frontSide = FormattedText.formatted(rs.getString("Question"));
                FormattedText flipSide = FormattedText.formatted(rs.getString("Anwser"));
                
/*                if (frontSide.getUnformatted().length() == 0 || flipSide.getUnformatted().length() == 0)
                    throw new IOException("You have to specify at least a front " +
                        "side and flip side for every card.");
                */
                Card card = new Card(frontSide, flipSide);
                
                Category category;
                String categoryName = rs.getString("category");
                if (categoryName.length() == 0 || 
                    categoryName.equalsIgnoreCase(rootCategory.getName()))
                {
                    category = rootCategory;
                }
                else
                {
                   if (categories.containsKey(categoryName))
                    {
                        category = (Category)categories.get(categoryName);
                    }
                    else
                    {
                        category = new Category(categoryName);
                        rootCategory.addCategoryChild(category);
                        categories.put(categoryName, category);
                    }
                }
                
                String level = rs.getString("Level");
                if (level.length() > 0)
                {
                    category.addCard(card, Integer.parseInt(level));
                }
                else
                {
                    category.addCard(card);
                }
                
            }
        }

            //rs.close();

/*        catch (FinalizedException e)
        {
            throw new IOException(e.toString());
        } 
        catch (CatastrophicException e)
        {
            throw new IOException(e.toString());
        }*/
    

/*    private static void writeHeader(CsvWriter writer) throws IOException, 
        com.csvreader.CsvWriter.FinalizedException
    {
        writer.write(FRONTSIDE_COL);
        writer.write(FLISIDE_COL);
        writer.write(CATEGORY_COL);
        writer.write(LEVEL_COL);
        writer.endRecord();
    }*/


    private static void validateHeader(String[] headers) throws BadHeaderException
    {
        boolean hasFront = false;
        boolean hasFlip = false;
        for (int i = 0; i < headers.length; i++)
        {
            if (headers[i].equalsIgnoreCase(FRONTSIDE_COL))
            {
                hasFront = true;
                continue;
            }
                
            if (headers[i].equalsIgnoreCase(FLISIDE_COL))
            {
                hasFlip = true;
                continue;
            }
            
            if (!headers[i].equalsIgnoreCase(CATEGORY_COL) && 
                !headers[i].equalsIgnoreCase(LEVEL_COL))
                    throw new BadHeaderException("Unknown header column: "+headers[i]);
        }
        
        if (!hasFront || !hasFlip)
            throw new BadHeaderException("The first line needs to specify "+
                "the header, which must have at least contain the columns '"+
                FRONTSIDE_COL+ "' and '"+FLISIDE_COL+"'.");

    }
    
/*    private static String getLineString(CsvReader reader)
    {
        return "(line "+ reader.getCurrentRecord() +")";
    }*/
}
