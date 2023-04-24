package lostKnight;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class LoadingScreen extends BasicGameState{
    private Shape loadingBar;
    private Shape outline;
    private double X;


    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        X = 0;
        loadingBar = new Rectangle(275, 395, (int)X, 10);
        outline = new Rectangle(275, 395, 350, 10);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        X = (((double)LoadingList.get().getTotalResources() - (double)LoadingList.get().getRemainingResources())/(double)LoadingList.get().getTotalResources()) * 350;
        loadingBar = new Rectangle(275, 395, (int)X, 10);
        if (LoadingList.get().getRemainingResources() > 0) { 
            DeferredResource nextResource = LoadingList.get().getNext(); 
            try {nextResource.load();} 
            catch (IOException e) {e.printStackTrace();}
        }else{ 
            sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.draw(outline);
        g.fill(loadingBar);
        g.drawString("Loading...", 392, 365);
    }

    @Override
    public int getID() {
        return 0;
    }
    
}