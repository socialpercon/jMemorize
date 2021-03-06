/*
 * DBEdit 2
 * Copyright (C) 2006-2012 Jef Van Den Ouweland
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmemorize.gui.swing.actions;

import jmemorize.gui.swing.ApplicationPanel;
import org.hibernate.jdbc.util.FormatStyle;
import org.hibernate.jdbc.util.Formatter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FormatSQLAction extends CustomAction {

    protected FormatSQLAction() {
        super("Format SQL", "format.png",
                KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.SHIFT_DOWN_MASK + KeyEvent.CTRL_DOWN_MASK));
        setEnabled(true);
    }

    @Override
    protected void performThreaded(ActionEvent e) throws Exception {
        Formatter formatter = FormatStyle.BASIC.getFormatter();
        String original = ApplicationPanel.getInstance().getText();
        String formatted = formatter.format(original);
        ApplicationPanel.getInstance().setText(formatted);
    }
}
