package lostKnight;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;


public class Main extends StateBasedGame{

    public Main(String title){
        super(title);
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("Lost Knight"));
        app.setIcon("Images\\icon.png");
        app.setDisplayMode(900, 800, false);
        app.setTargetFrameRate(60);
        app.setShowFPS(false);
        LoadingList.setDeferredLoading(true);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new LoadingScreen());
        this.addState(new MainS());
        this.addState(new Map());
        this.addState(new level1());
        this.addState(new level1room1());
        this.addState(new level1room2());
        this.addState(new level2());
        this.addState(new level3());
        //Create a reset function for each class once it is exited
    }
}