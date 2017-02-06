package jmemorize.gui.swing.actions.file;

import java.awt.event.ActionEvent;
import java.io.IOException;


import jmemorize.core.Lesson;
import jmemorize.core.Main;
import jmemorize.gui.swing.actions.AbstractSessionDisabledAction;
import jmemorize.gui.swing.dialogs.ErrorDialog;

public abstract class AbstractDBExport extends AbstractSessionDisabledAction
{

    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener
     */
    public void actionPerformed(ActionEvent event)
    {
        Main main = Main.getInstance();

        try
        {
                doExport(main.getLesson());
        } 
        catch (Exception e)
        {

            String msg = "연결오류";
            new ErrorDialog(main.getFrame(), msg, e).setVisible(true);
        }
    }
    
    protected abstract void doExport(Lesson lesson) throws IOException;
    
}