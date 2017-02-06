package jmemorize.gui.swing;

import java.awt.Font;

import javax.swing.text.StyleConstants;

public class CardFont
{
    /**
	 * @author   S
	 */
    public enum FontAlignment {/**
	 * @uml.property  name="lEFT"
	 * @uml.associationEnd  
	 */
    LEFT, /**
	 * @uml.property  name="cENTER"
	 * @uml.associationEnd  
	 */
    CENTER, /**
	 * @uml.property  name="rIGHT"
	 * @uml.associationEnd  
	 */
    RIGHT};
    /**
	 * @author   S
	 */
    public enum FontType {/**
	 * @uml.property  name="cARD_FRONT"
	 * @uml.associationEnd  
	 */
    CARD_FRONT, /**
	 * @uml.property  name="cARD_FLIP"
	 * @uml.associationEnd  
	 */
    CARD_FLIP, /**
	 * @uml.property  name="tABLE_FRONT"
	 * @uml.associationEnd  
	 */
    TABLE_FRONT, 
        /**
		 * @uml.property  name="tABLE_FLIP"
		 * @uml.associationEnd  
		 */
        TABLE_FLIP, /**
		 * @uml.property  name="lEARN_FRONT"
		 * @uml.associationEnd  
		 */
        LEARN_FRONT, /**
		 * @uml.property  name="lEARN_FLIP"
		 * @uml.associationEnd  
		 */
        LEARN_FLIP};
    
    /**
	 * @uml.property  name="m_font"
	 */
    private Font          m_font;
    /**
	 * @uml.property  name="m_alignment"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private FontAlignment m_alignment;
    /**
	 * @uml.property  name="m_isVerticallyCentered"
	 */
    private boolean       m_isVerticallyCentered;
    
    public CardFont(Font font, FontAlignment alignment, boolean verticallyCentered)
    {
        m_font = font;
        m_alignment = alignment;
        m_isVerticallyCentered = verticallyCentered;
    }

    public Font getFont()
    {
        return m_font;
    }

    public void setFont(Font font)
    {
        m_font = font;
    }

    public FontAlignment getAlignment()
    {
        return m_alignment;
    }

    public void setAlignment(FontAlignment alignment)
    {
        m_alignment = alignment;
    }

    public boolean isVerticallyCentered()
    {
        return m_isVerticallyCentered;
    }

    public void setVerticallyCentered(boolean isVerticallyCentered)
    {
        m_isVerticallyCentered = isVerticallyCentered;
    }
    
    public int getSwingAlign()
    {
        switch (m_alignment)
        {
            case CENTER: return StyleConstants.ALIGN_CENTER;
            case RIGHT: return StyleConstants.ALIGN_RIGHT;
            case LEFT: 
            default: return StyleConstants.ALIGN_LEFT;
        }
    }
}
