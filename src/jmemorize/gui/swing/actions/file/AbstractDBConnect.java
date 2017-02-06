/*
최우철이가 제작하고 있습니다.
 */
package jmemorize.gui.swing.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import jmemorize.core.Lesson;
import jmemorize.core.Main;
import jmemorize.core.Settings;
import jmemorize.gui.LC;
import jmemorize.gui.Localization;
import jmemorize.gui.swing.actions.AbstractSessionDisabledAction;
import jmemorize.gui.swing.dialogs.ErrorDialog;

/**
 * An abstract action for importing data.
 * @author  djemili
 */
public abstract class AbstractDBConnect extends AbstractSessionDisabledAction
{

    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener
     */
    public void actionPerformed(ActionEvent event)
    {
        Main main = Main.getInstance();

        try
        {
                doImport(main.getLesson());
        } 
        catch (Exception e)
        {

            String msg = "연결오류";
            new ErrorDialog(main.getFrame(), msg, e).setVisible(true);
        }
    }
    
    /**
     * Imports given file contents into given lesson.
     */
    protected abstract void doImport(Lesson lesson) throws IOException;
    
}
