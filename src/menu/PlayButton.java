package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import map.GameFrame;

/**
 *
 * @author pawel
 */
class PlayButton extends JButton implements ActionListener{

    private GameFrame gameFrameMap;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        gameFrameMap = new GameFrame();
        
    }
    
}
