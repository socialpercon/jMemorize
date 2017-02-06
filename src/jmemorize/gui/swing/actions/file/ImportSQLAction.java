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
			System.out.println("����̹� ���� ����");
			con = DriverManager.getConnection(url, id, password);
			System.out.println("�����ͺ��̽� ���� ����");
			SqlBuilder.importLesson(con,lesson);
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� ã�� �� �����ϴ�.");
		} catch (SQLException e) {
			System.out.println("���ῡ �����Ͽ����ϴ�.");
		}
		//return con;
	}

}
