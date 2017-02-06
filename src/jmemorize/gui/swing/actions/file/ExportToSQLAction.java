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
package jmemorize.gui.swing.actions.file;


import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import jmemorize.core.Lesson;
import jmemorize.core.io.SqlBuilder;
import jmemorize.gui.LC;
import jmemorize.gui.Localization;
//import jmemorize.util.ExtensionFileFilter;

/**
 * Exports to comma-separated-values.
 * 
 * @author djemili
 */
public class ExportToSQLAction extends AbstractDBExport
{
    public ExportToSQLAction()
    {
        setValues();
    }
    
    /* (non-Javadoc)
     * @see jmemorize.gui.swing.actions.file.AbstractExportAction
     */
    protected void doExport(Lesson lesson) throws IOException
    {
		String url = "jdbc:mysql://localhost/l2jgs?useUnicode=true&characterEncoding=euckr";
		String id = "root";
		String password = "root";
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이버 적재 성공");
			con = DriverManager.getConnection(url, id, password);
			System.out.println("데이터베이스 연결 성공");
			SqlBuilder.exportLesson(con,lesson);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}
    }

    /* (non-Javadoc)
     * @see jmemorize.gui.swing.actions.file.AbstractExportAction
     */
/*    protected ExtensionFileFilter getFileFilter()
    {
        return new ExtensionFileFilter("csv", Localization.get(LC.EXPORT_MYSQL));
    }*/
    
    private void setValues()
    {
        setName(Localization.get(LC.EXPORT_MYSQL));
        setIcon("/resource/icons/dbedit.png"); //$NON-NLS-1$
    }
    
}
