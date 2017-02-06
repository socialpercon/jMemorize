package jmemorize.gui.swing.actions.file;

/*import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;*/
import java.sql.*;

import jmemorize.core.Lesson;
import jmemorize.core.io.SqlBuilder;
import jmemorize.gui.LC;
import jmemorize.gui.Localization;

public class ImportSQLAction extends AbstractDBConnect{

    public ImportSQLAction()
    {
    	setValues();
    }
	
    private void setValues()
    {
        setName(Localization.get(LC.IMPORT_MYSQL));
        setMnemonic(1);
        setIcon("/resource/icons/dbedit.png"); //$NON-NLS-1$
    }
	
	protected void doImport(Lesson lesson) {
		String url = "jdbc:mysql://localhost/l2jgs";
		String id = "root";
		String password = "root";
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이버 적재 성공");
			con = DriverManager.getConnection(url, id, password);
			System.out.println("데이터베이스 연결 성공");
			SqlBuilder.importLesson(con,lesson);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}
		//return con;
	}

}
