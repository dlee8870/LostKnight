package lostKnight;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class level1room2 extends BasicGameState{
    //Make all variables static
    private static Circle mouseBall;
    private static ArrayList<Circle> circles;
    private static ArrayList<SpriteSheet> shields;
    private static ArrayList<Integer> shieldsY, shieldsX;
    private static int score, lives, nextShield, time;
    private static Animation shield, fire;
    public static Shape top, bottom, shieldCheck;
    private static Sound levelcomplete, Lost, shieldFallsFire, shieldCollected;
    private static SpriteSheet layoutRoom2, fullHeart, emptyHeart, shieldImage;
    public static int X, Y;
    private static int mX, mY, min, max, start;
    private static Input in;
    private static boolean settings, openSettings, Exit, openExit, Restart, openRestart, playGame, lost, win, showShield;
    public static boolean moveU, moveR, moveD, moveL;//Tracks which direction to move
    public static boolean standU, standR, standD, standL;//Tracks the last movement before stoping to track which direction to stand still

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        lives = 3; min = 0; max = 73; lost = false; time = 0; start = 0; win = false; showShield = true;
        shields = new ArrayList<>(); shieldsY = new ArrayList<>(); shieldsX = new ArrayList<>(); circles = new ArrayList<>();
        shieldImage = new SpriteSheet("Images\\level1\\shield.png", 190, 190); fire = new Animation(new SpriteSheet("Images\\level1\\fire.png", 181, 188), 125);
        fullHeart = new SpriteSheet("Images\\level1\\fullHeart.png", 130, 140); emptyHeart = new SpriteSheet("Images\\level1\\emptyHeart.png", 130, 140);
        layoutRoom2 =  new SpriteSheet("Images\\level1\\level1room2.png", 896, 800);
        shield = new Animation(new SpriteSheet("Images\\level1\\shieldAni.png", 100, 120), 190);
        shieldFallsFire = new Sound("Sound\\shieldFallsFire.ogg");
        shieldCollected = new Sound("Sound\\shieldCollected.ogg");
        levelcomplete = new Sound("Sound\\levelcomplete.ogg");
        Lost = new Sound("Sound\\lost.ogg");
        Exit = true; Restart = true; openRestart = false; openExit = false;
        X = 120; Y = 115; mX = 0; mY = 0;
        settings = true; playGame = false;
        in = gc.getInput();
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = false; standR = false; standD = true; standL = false;
        shieldCheck = new Rectangle(668, 137, 38, 40);
        mouseBall = new Circle(0, 0, (float)6.5);
    }

    public static void restartRoom2() {
        shields = new ArrayList<>(); shieldsY = new ArrayList<>(); shieldsX = new ArrayList<>(); circles = new ArrayList<>();
        playGame = false; score = 0; lives = 3; time = 0; start = 0; win = false;
        X = 120; Y = 115; mX = 0; mY = 0; lost = false; showShield = true;
        settings = true; Exit = true; Restart = true; openRestart = false; openExit = false; openSettings = false;
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = false; standR = false; standD = true; standL = false;
        shieldCheck = new Rectangle(668, 137, 38, 40);

    }

    public void buttonCheck(StateBasedGame sbg) throws FileNotFoundException, IOException{
        //Music
        if((mX >= 415 && mX <= 475) && (mY >= 275 && mY <= 335) && in.isMousePressed(0)){
            //Turning music off
            if(MainS.musicOn){
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                MainS.musicOn = false;
                MainS.soundtrack.setVolume(0f);
            }
            else{//Turning music on
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                MainS.musicOn = true;
                MainS.soundtrack.setVolume(0.2f);
            }
        }
        
        //Sound
        if((mX >= 415 && mX <= 475) && (mY >= 355 && mY <= 415) && in.isMousePressed(0)){
            //Turning sound off
            if(MainS.soundOn){
                MainS.soundOn = false;
            }
            else{//Turning sound on
                MainS.soundOn = true;
                MainS.buttonClick.play(1f, 0.5f);
            }
        }
        //restart
        if((mX >= 415 && mX <= 475) && (mY >= 435 && mY <= 495)){
            Restart = false;
            if(in.isMousePressed(0)){
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                openRestart = true;
                level1.restart(); level1room1.restartRoom1(); level1room2.restartRoom2();
                sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
            }
        }else{Restart = true;}
        //exit
        if((mX >= 415 && mX <= 475) && (mY >= 515 && mY <= 575)){
           Exit = false;
           if(in.isMousePressed(0)){
            if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
            openExit = true;
            level1.restart(); level1room1.restartRoom1(); level1room2.restartRoom2(); Map.restart();
            sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }
        }else{Exit = true;}
    }

    public boolean upColl(){ // done
        //Each if statement checks for a wall segment
        if((top.getMaxX() >= 631 && top.getMinX() <= 744) && (top.getMinY()-2 >= 118 && top.getMinY()-2 <= 123)){
            return true;
        }
        if((top.getMaxX() >= 439 && top.getMinX() <= 630) && (top.getMinY()-2 >= 278 && top.getMinY()-2 <= 283)){
            return true;
        }
        if((top.getMaxX() >= 361 && top.getMinX() <= 438) && (top.getMinY()-2 >= 341 && top.getMinY()-2 <= 347)){
            return true;
        }
        if((top.getMaxX() >= 119 && top.getMinX() <= 278) && (top.getMinY()-2 >= 501 && top.getMinY()-2 <= 507)){
            return true;
        }
        if((top.getMaxX() >= 553 && top.getMinX() <= 662) && (top.getMinY()-2 >= 533 && top.getMinY()-2 <= 539)){
            return true;
        }
        if((top.getMaxX() >= 361 && top.getMinX() <= 470) && (top.getMinY()-2 >= 533 && top.getMinY()-2 <= 539)){
            return true;
        }
        if((top.getMaxX() >= 219 && top.getMinX() <= 360) && (top.getMinY()-2 >= 117 && top.getMinY()-2 <= 123)){
            return true;
        }
        if((top.getMaxX() >= 197 && top.getMinX() <= 218) && (top.getMinY()-2 >= 122 && top.getMinY()-2 <= 127)){
            return true;
        }
        if((top.getMaxX() >= 179 && top.getMinX() <= 196) && (top.getMinY()-2 >= 117 && top.getMinY()-2 <= 123)){
            return true;
        }
        if((top.getMaxX() >= 123 && top.getMinX() <= 140) && (top.getMinY()-2 >= 117 && top.getMinY()-2 <= 123)){
            return true;
        }
        if((top.getMaxX() >= 101 && top.getMinX() <= 122) && (top.getMinY()-2 >= 122 && top.getMinY()-2 <= 127)){
            return true;
        }
        if((top.getMaxX() >= 87 && top.getMinX() <= 100) && (top.getMinY()-2 >= 117 && top.getMinY()-2 <= 123)){
            return true;
        }
        return false;
    }
    public boolean rightColl(){ // done

        if((bottom.getMinY() >= 347 && bottom.getMinY() <= 412) && (bottom.getMaxX()+2 >= 200 && bottom.getMaxX()+2 <= 208)){
            return true;
        }
        if((bottom.getMinY() >= 331 && bottom.getMinY() <= 346) && (bottom.getMaxX()+2 >= 202 && bottom.getMaxX()+2 <= 208)){
            return true;
        }
        if((bottom.getMinY() >= 123 && bottom.getMinY() <= 346) && (bottom.getMaxX()+2 >= 360 && bottom.getMaxX()+2 <= 366)){
            return true;
        }
        if((bottom.getMinY() >= 123 && bottom.getMinY() <= 604) && (bottom.getMaxX()+2 >= 744 && bottom.getMaxX()+2 <= 751)){
            return true;
        }
        if((bottom.getMinY() >= 443 && bottom.getMinY() <= 548) && (bottom.getMaxX()+2 >= 360 && bottom.getMaxX()+2 <= 367)){
            return true;
        }
        if((bottom.getMinY() >= 443 && bottom.getMinY() <= 548) && (bottom.getMaxX()+2 >= 552 && bottom.getMaxX()+2 <= 558)){
            return true;
        }
        if((bottom.getMinY() >= 427 && bottom.getMinY() <= 442) && (bottom.getMaxX()+2 >= 362 && bottom.getMaxX()+2 <= 368)){
            return true;
        }
        if((bottom.getMinY() >= 427 && bottom.getMinY() <= 442) && (bottom.getMaxX()+2 >= 554 && bottom.getMaxX()+2 <= 560)){
            return true;
        }
        if((bottom.getMinY() >= 123 && bottom.getMinY() <= 126) && (bottom.getMaxX()+2 >= 100 && bottom.getMaxX()+2 <= 106)){
            return true;
        }
        if((bottom.getMinY() >= 123 && bottom.getMinY() <= 126) && (bottom.getMaxX()+2 >= 196 && bottom.getMaxX()+2 <= 202)){
            return true;
        }
        return false;
    }
    public boolean downColl(){ // done

        if((bottom.getMaxX() >= 119 && bottom.getMinX() <= 744) && (bottom.getMinY()+2 >= 604 && bottom.getMinY()+2 <= 610)){
            return true; 
        }
        if((bottom.getMaxX() >= 363 && bottom.getMinX() <= 468) && (bottom.getMinY()+2 >= 426 && bottom.getMinY()+2 <= 430)){
            return true; 
        }
        if((bottom.getMaxX() >= 555 && bottom.getMinX() <= 660) && (bottom.getMinY()+2 >= 426 && bottom.getMinY()+2 <= 430)){
            return true; 
        }
        if((bottom.getMaxX() >= 203 && bottom.getMinX() <= 276) && (bottom.getMinY()+2 >= 330 && bottom.getMinY()+2 <= 334)){
            return true; 
        }
        if((bottom.getMaxX() >= 87 && bottom.getMinX() <= 200) && (bottom.getMinY()+2 >= 412 && bottom.getMinY()+2 <= 418)){
            return true; 
        }
        if((bottom.getMaxX() >= 201 && bottom.getMinX() <= 202) && (bottom.getMinY()+2 >= 346 && bottom.getMinY()+2 <= 351)){
            return true; 
        }
        if((bottom.getMaxX() >= 277 && bottom.getMinX() <= 278) && (bottom.getMinY()+2 >= 346 && bottom.getMinY()+2 <= 351)){
            return true; 
        }
        if((bottom.getMaxX() >= 361 && bottom.getMinX() <= 362) && (bottom.getMinY()+2 >= 442 && bottom.getMinY()+2 <= 450)){
            return true; 
        }
        if((bottom.getMaxX() >= 469 && bottom.getMinX() <= 470) && (bottom.getMinY()+2 >= 442 && bottom.getMinY()+2 <= 450)){
            return true; 
        }
        if((bottom.getMaxX() >= 553 && bottom.getMinX() <= 554) && (bottom.getMinY()+2 >= 442 && bottom.getMinY()+2 <= 450)){
            return true; 
        }
        if((bottom.getMaxX() >= 661 && bottom.getMinX() <= 662) && (bottom.getMinY()+2 >= 442 && bottom.getMinY()+2 <= 450)){
            return true; 
        }
        return false;
    }
    public boolean leftColl(){ // done
        //Each if statement checks for a wall segment
        if((bottom.getMinY() >= 123 && bottom.getMinY() <= 282) && (bottom.getMinX()-2 >= 624 && bottom.getMinX()-2 <= 631)){
            return true;
        }
        if((bottom.getMinY() >= 283 && bottom.getMinY() <= 346) && (bottom.getMinX()-2 >= 431 && bottom.getMinX()-2 <= 439)){
            return true;
        }
        if((bottom.getMinY() >= 507 && bottom.getMinY() <= 604) && (bottom.getMinX()-2 >= 113 && bottom.getMinX()-2 <= 119)){
            return true;
        }
        if((bottom.getMinY() >= 123 && bottom.getMinY() <= 412) && (bottom.getMinX()-2 >= 81 && bottom.getMinX()-2 <= 87)){
            return true;
        }
        if((bottom.getMinY() >= 347 && bottom.getMinY() <= 506) && (bottom.getMinX()-2 >= 273 && bottom.getMinX()-2 <= 279)){
            return true;
        }
        if((bottom.getMinY() >= 123 && bottom.getMinY() <= 126) && (bottom.getMinX()-2 >= 119 && bottom.getMinX()-2 <= 123)){
            return true;
        }
        if((bottom.getMinY() >= 123 && bottom.getMinY() <= 126) && (bottom.getMinX()-2 >= 214 && bottom.getMinX()-2 <= 219)){
            return true;
        }
        if((bottom.getMinY() >= 331 && bottom.getMinY() <= 346) && (bottom.getMinX()-2 >= 271 && bottom.getMinX()-2 <= 277)){
            return true;
        }
        if((bottom.getMinY() >= 427 && bottom.getMinY() <= 442) && (bottom.getMinX()-2 >= 463 && bottom.getMinX()-2 <= 469)){
            return true;
        }
        if((bottom.getMinY() >= 427 && bottom.getMinY() <= 442) && (bottom.getMinX()-2 >= 655 && bottom.getMinX()-2 <= 661)){
            return true;
        }
        if((bottom.getMinY() >= 443 && bottom.getMinY() <= 538) && (bottom.getMinX()-2 >= 465 && bottom.getMinX()-2 <= 471)){
            return true;
        }
        if((bottom.getMinY() >= 443 && bottom.getMinY() <= 538) && (bottom.getMinX()-2 >= 656 && bottom.getMinX()-2 <= 663)){
            return true;
        }
        return false;
    }

    public void movement(int delta){

        //Stop soundtrack and play sound
        //Moves up by 3 Px approx every 20 miliseconds (Approx 150 px per second)
        if(in.isKeyDown(Input.KEY_UP) && !moveR && !moveD && !moveL){
            if(!level1.walking.playing() && MainS.soundOn){level1.walking.loop(1f, 0.8f);}
            standU = false; standR = false; standD = false; standL = false;
            moveU = true; moveR = false; moveD = false; moveL = false;
            level1.upA.update(delta);
            if(!upColl()){
                Y -= 2; 
                top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);
            }
        }else if(moveU){
            standU = true; moveU = false;
            if(level1.walking.playing()){level1.walking.stop();}
        }
        else if(in.isKeyDown(Input.KEY_RIGHT) && !moveD && !moveL){
            if(!level1.walking.playing() && MainS.soundOn){level1.walking.loop(1f, 0.8f);}
            standU = false; standR = false; standD = false; standL = false;
            moveU = false; moveR = true; moveD = false; moveL = false;
            level1.rightA.update(delta);
            if(!rightColl()){
                X += 2; 
                top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);
            }
        }else if(moveR){
            standR = true; moveR = false;
            if(level1.walking.playing()){level1.walking.stop();} 
        }
        else if(in.isKeyDown(Input.KEY_DOWN) && !moveL){
            if(!level1.walking.playing() && MainS.soundOn){level1.walking.loop(1f, 0.8f);}
            standU = false; standR = false; standD = false; standL = false;
            moveU = false; moveR = false; moveD = true; moveL = false;
            level1.downA.update(delta);
            if(!downColl()){
                Y += 2; 
                top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);
            }
        }else if(moveD){
            standD = true; moveD = false;
            if(level1.walking.playing()){level1.walking.stop();}
        }
        else if(in.isKeyDown(Input.KEY_LEFT)){
            if(!level1.walking.playing() && MainS.soundOn){level1.walking.loop(1f, 0.8f);}
            standU = false; standR = false; standD = false; standL = false;
            moveU = false; moveR = false; moveD = false; moveL = true;
            level1.leftA.update(delta);
            if(!leftColl()){
                X -= 2; 
                top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);
            }
        }else if(moveL){
            standL = true; moveL = false;
            if(level1.walking.playing()){level1.walking.stop();}
        }
        //For idle 
        if(standU){level1.upS.update(delta);}
        else if(standR){level1.rightS.update(delta);}
        else if(standD){level1.downS.update(delta);}
        else if(standL){level1.leftS.update(delta);}
    }

    public void game(int delta) throws SlickException{
        mouseBall.setCenterX(in.getMouseX());
        mouseBall.setCenterY(in.getMouseY()+5);
        //Wait for around 1 second to start game
        start += delta;
        
        if(start >= 1250){
        start = 1250;    
        nextShield += delta;
        if(nextShield >= 450){
            int random = (int)(Math.random() * (max - min + 1) + min);
            nextShield = 0;
            shields.add(shieldImage);
            circles.add(new Circle(240 + (5 * (random)), 160+15, 15));
            shieldsX.add(225 + (5 * (random)));
            shieldsY.add(160);
        }

        for(int i = 0; i < shieldsY.size(); i++){
            int y = shieldsY.get(i), y2 = (int)circles.get(i).getCenterY();
            shieldsY.set(i, y+4);
            circles.get(i).setCenterY(y2+4);
        }
        
        for(int i = shields.size() - 1; i >= 0; i--){
            Circle c = circles.get(i);
            //Out of bounds
            if(c.getCenterY() > 610){
                //Play sound for shield hitting fire
                if(MainS.soundOn){shieldFallsFire.play(1f, 0.7f);}
                circles.remove(i); shields.remove(i); shieldsX.remove(i); shieldsY.remove(i);
                lives--;
            }else if(c.intersects(mouseBall) || c.contains(mouseBall)){
                //Play sound for collecting shield
                if(MainS.soundOn){shieldCollected.play(1f, 0.5f);}
                circles.remove(i); shields.remove(i); shieldsX.remove(i); shieldsY.remove(i);
                score++;
            }
        }
        //Checks for loss
        if(lives <= 0){
            //Play losing sound
            if(MainS.soundOn){Lost.play(1f, 0.8f);}
            lost = true;
        }
        //Checks for win
        if(score >= 35){
            if(MainS.soundOn){levelcomplete.play(1f, 0.8f);}
            win = true;
        }
    }

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        mX = in.getMouseX(); mY = in.getMouseY();
        if(playGame){
            if(level1.walking.playing()){level1.walking.stop();}
            moveU = false; moveR = false; moveD = false; moveL = false;
            standU = false; standR = false; standD = true; standL = false;
            //Start balls coming down after certain amount of time
        }
        if(!playGame){
        shield.update(delta);
        }
        //Settings Icon
        if((mX >= 830 && mX <= 890) && (mY >= 730 && mY <= 790)){
            settings = false;
            if(in.isMousePressed(0)){
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                if(openSettings){openSettings = false;}
                else{openSettings = true;}
            }
        }else{settings = true;}
         //Icons in settings
        if(openSettings){try {buttonCheck(sbg);} catch (IOException e) {e.printStackTrace();}}  

        if(!openSettings && !playGame){
            movement(delta);
            }

        //Entering back to mainRoom
        if((top.getMaxX() >= 141 && top.getMinX() <= 178) && (top.getMinY()-2 >= 115 && top.getMinY()-2 <= 122)){
            level1.X = 630; level1.Y = 160;
            if(level1.walking.playing()){level1.walking.stop();}
            level1.moveU = false; level1.standU = false; level1.standD = true;
            level1.top = new Rectangle(630+30, 160+60, 20, 1); level1.bottom = new Rectangle(630+30, 160+65, 20, 1);
            sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
         }
         //Checking for when to start mini game
        if(shieldCheck.contains(top) || shieldCheck.intersects(top)){
            playGame = true;
        }
        if(playGame && !lost && !win){
            //Game
            game(delta);
        }
        if(lost){
            time += delta;
            if(time >= 1300){
                time = 0;
                level1.restart(); level1room1.restartRoom1(); level1room2.restartRoom2();
                sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
            }
        }
        if(win){
            time += delta;
            if(time >= 1800){
            time = 0;
            playGame = false;
            showShield = false;
            shieldCheck = new Rectangle(0, 0, 0, 0);
            level1.spike1 = false;
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setBackground(new Color(47, 47, 46));
        layoutRoom2.draw(0, 0);

        //Shield Animation
        if(!playGame && showShield){
        shield.draw(670, 135, 35, 45);
        }

       //For standing stance
       if(standU){level1.upS.draw(X, Y, 80, 80);}
       if(standD){level1.downS.draw(X, Y, 80, 80);}
       if(standR){level1.rightS.draw(X, Y, 80, 80);}
       if(standL){level1.leftS.draw(X, Y, 80, 80);}
       //For movement
       if(moveU){level1.upA.draw(X, Y, 80, 80);}
       if(moveR){level1.rightA.draw(X, Y, 80, 80);}
       if(moveD){level1.downA.draw(X, Y, 80, 80);}
       if(moveL){level1.leftA.draw(X, Y, 80, 80);}
        //Mini-game
       if(playGame){gameScreen(g);}

        //Settings Icon
       if(openSettings){
        MainS.settingPressed.draw(830, 730, 60, 60);
        MainS.border.draw(225, 155, 450, 475);
        SETTINGS();
        }
        else if(settings){MainS.setting.draw(830, 730, 60, 60);}
        else{MainS.settingOn.draw(830, 730, 60, 60);}
    }

    public void SETTINGS(){
        MainS.settingTitle.draw(290, 161);
        if(MainS.musicOn){
            MainS.music.draw(415, 275, 60, 60);
        }else{
            MainS.musicPressed.draw(415, 275, 60, 60);
        }

        if(MainS.soundOn){
            MainS.sound.draw(415, 355, 60, 60);
            }else{
                MainS.soundPressed.draw(415, 355, 60, 60);
        }
        
        if(openRestart){
            level1.restartPressed.draw(415, 435, 60, 60);
        }
        else if(Restart){level1.restart.draw(415, 435, 60, 60);}
        else{level1.restartOn.draw(415, 435, 60, 60);}

        if(openExit){
            level1.exitPressed.draw(415, 515, 60, 60);
        }
        else if(Exit){level1.exit.draw(415, 515, 60, 60);}
        else{level1.exitOn.draw(415, 515, 60, 60);}

    }

    public void gameScreen(Graphics g){
        //Border
        g.setColor(Color.black);
        g.fillRect(200, 159, 500, 500);
        g.setColor(Color.white);
        g.fillRect(620, 159, 2, 500);
        //Hearts
        int y = 0;
        for(int i = 1; i <= 3; i++){
            if(i <= lives){
                fullHeart.draw(638, 615-y, 45, 45);
            }else{
                emptyHeart.draw(638, 615-y, 45, 45);
            }
            y += 35;
        }
        //Show out the score and target score
        g.drawString("Target", 635, 170);
        g.setColor(Color.yellow);
        g.drawString("35", 652, 190);
        g.setColor(Color.white);
        g.drawString("Score", 637, 230);
        if(score >= 15 && score < 25){g.setColor(Color.red);}
        if(score >= 25 && score < 35){g.setColor(Color.orange);}
        if(score >= 35){g.setColor(Color.yellow);}
        g.drawString(""+ score, 653, 250);

        //Fire
        int x = 197;
        for(int i = 0; i < 10; i++){
        fire.draw(x, 605, 63, 60);
        x+=40;
        }

        //Show out the shields coming down
        for(int i = 0; i < shields.size(); i++){
            shields.get(i).draw(shieldsX.get(i), shieldsY.get(i), 30, 30);
        }
        
    }

    @Override
    public int getID() {
        return 5;
    }
    
}
