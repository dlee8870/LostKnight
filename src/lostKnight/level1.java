package lostKnight;

import java.io.*;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class level1 extends BasicGameState{

        public static Shape top, bottom;
        public static Animation upS, rightS, downS, leftS;//When image is still
        public static Animation upA, rightA, downA, leftA, upDyingA;//The animation of the frames
        public static Sound walking, keyUnlock, levelcomplete, lost;
        public static int Y, X;
        public static boolean spike1, spike2, locked, dead, Restart, Exit, openRestart, openExit;
        public static SpriteSheet restart, restartOn, restartPressed, exit, exitOn, exitPressed;
        private static Input in;
        private static SpriteSheet lock, spikeOn, spikeOff, layout;
        private static Shape keyBox;
        private static Animation key;
        private static int time, mX, mY;
        private static boolean showKey, settings, openSettings;
        public static boolean moveU, moveR, moveD, moveL;//Tracks which direction to move
        public static boolean standU, standR, standD, standL;//Tracks the last movement before stoping to track which direction to stand still

    public static void restart(){
        keyBox = new Rectangle(437, 354, 27, 27);
        Restart = true;
        Exit = true;
        settings = true; openSettings = false;
        Restart = true; Exit = true; openExit = false; openRestart = false;
        time = 0;
        dead = false;
        spike1 = true; spike2 = true; locked = true; showKey = true;
        Y = 608; X = 410;
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = true; standR = false; standD = false; standL = false;//Default is up

    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        time = 0; mX = 0; mY = 0;
        Restart = true; Exit = true; openExit = false; openRestart = false;
        settings = true;
        in = gc.getInput();
        restart = new SpriteSheet("Images\\Icons\\restart.png", 16, 16); restartOn = new SpriteSheet("Images\\Icons\\restartTop.png", 16, 16); restartPressed = new SpriteSheet("Images\\Icons\\restartPressed.png", 16, 16);
        exit = new SpriteSheet("Images\\Icons\\exit.png", 16, 16); exitOn = new SpriteSheet("Images\\Icons\\exitTop.png", 16, 16); exitPressed= new SpriteSheet("Images\\Icons\\exitPressed.png", 16, 16);
        layout = new SpriteSheet("Images\\level1\\mainLayout.png", 896, 800);
        lock = new SpriteSheet("Images\\level1\\lock.png", 12, 12); spikeOn = new SpriteSheet("Images\\level1\\spikeOn.png", 26, 27); spikeOff = new SpriteSheet("Images\\level1\\spikeOff.png", 26, 24);
        key = new Animation(new SpriteSheet("Images\\level1\\key.png", 32, 32), 300);
        upDyingA = new Animation(new SpriteSheet("Images\\KnightUp\\UDeath.png", 48, 48), 190);
        upS = new Animation(new SpriteSheet ("Images\\KnightUp\\upStill.png", 48, 48), 250); upA = new Animation(new SpriteSheet("Images\\KnightUp\\up.png", 48, 48), 150);//The number is the duration of each frame
        rightS = new Animation(new SpriteSheet ("Images\\KnightRight\\rightStill.png", 48, 48), 250); rightA = new Animation(new SpriteSheet("Images\\KnightRight\\right.png", 48, 48), 150);
        downS = new Animation(new SpriteSheet ("Images\\KnightDown\\downStill.png", 48, 48), 250); downA = new Animation(new SpriteSheet("Images\\KnightDown\\down.png", 48, 48), 150);
        leftS = new Animation(new SpriteSheet ("Images\\KnightLeft\\leftStill.png", 48, 48), 250); leftA = new Animation(new SpriteSheet("Images\\KnightLeft\\left.png", 48, 48), 150);
        keyBox = new Rectangle(437, 354, 27, 27);
        walking = new Sound("Sound\\walking.ogg"); 
        keyUnlock = new Sound("Sound\\keyUnlock.ogg");
        levelcomplete = new Sound("Sound\\levelcomplete.ogg");
        lost = new Sound("Sound\\lost.ogg");
        spike1 = true; spike2 = true; locked = true; showKey = true;
        Y = 608; X = 410;
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = true; standR = false; standD = false; standL = false;//Default is up

    }

    public static void nextLevel(){

        try {

            BufferedReader in = new BufferedReader(new FileReader(new File("Info\\level.txt")));
            int level = Integer.parseInt(in.readLine());
            in.close();

            BufferedWriter br = new BufferedWriter(new FileWriter(new File("Info\\level.txt"), false));

            if(level == 1){level++;}
            br.write(String.valueOf(level));
            br.close();

        } catch (IOException e) {e.printStackTrace();}
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
                restart(); level1room1.restartRoom1(); level1room2.restartRoom2();
                sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
            }
        }else{Restart = true;}
        //exit
        if((mX >= 415 && mX <= 475) && (mY >= 515 && mY <= 575)){
            Exit = false;

            if(in.isMousePressed(0)){
             
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                    openExit = true;
                    restart(); level1room1.restartRoom1(); level1room2.restartRoom2(); Map.restart();
                    sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
                }

        }else{Exit = true;}
    }

    //Collision detection for 4 directions
    public boolean upColl(){

        if((top.getMaxX() >= 152 && top.getMinX() <= 173) && (top.getMinY()-2 >= 165 && top.getMinY()-2 <= 171)){
            return true;
        }
        if((top.getMaxX() >= 174 && top.getMinX() <= 204) && (top.getMinY()-2 >= 145 && top.getMinY()-2 <= 155)){
            return true;
        }
        if((top.getMaxX() >= 243 && top.getMinX() <= 652) && (top.getMinY()-2 >= 145 && top.getMinY()-2 <= 155)){
            return true;
        }
        if((top.getMaxX() >= 691 && top.getMinX() <= 720) && (top.getMinY()-2 >= 145 && top.getMinY()-2 <= 155)){
            return true;
        }
        if((top.getMaxX() >= 722 && top.getMinX() <= 743) && (top.getMinY()-2 >= 160 && top.getMinY()-2 <= 169)){
            return true;
        }
        if((top.getMaxX() >= 489 && top.getMinX() <= 598) && (top.getMinY()-2 >= 596 && top.getMinY()-2 <= 603)){
            return true;
        }
        if((top.getMaxX() >= 485 && top.getMinX() <= 488) && (top.getMinY()-2 >= 505 && top.getMinY()-2 <= 511)){
            return true;
        }
        if((top.getMaxX() >= 375 && top.getMinX() <= 520) && (top.getMinY()-2 >= 340 && top.getMinY()-2 <= 347)){
            return true;
        }
        if((top.getMaxX() >= 407 && top.getMinX() <= 409) && (top.getMinY()-2 >= 505 && top.getMinY()-2 <= 511)){
            return true;
        }
        if((top.getMaxX() >= 297 && top.getMinX() <= 406) && (top.getMinY()-2 >= 595 && top.getMinY()-2 <= 603)){
            return true;
        }
        //Check for first row of spike
        if((top.getMaxX() >= 383 && top.getMinX() <= 511) && (top.getMinY()-2 >= 405 && top.getMinY()-2 <= 415) && spike1){
            //user dies
            //Play game over sound restart all room
            moveU = false; moveR = false; moveD = false; moveL = false;
            standU = false; standR = false; standD = false; standL = false;
             dead = true;
             return true;
        }
        //Check for second row of spike
        if((top.getMaxX() >= 383 && top.getMinX() <= 511) && (top.getMinY()-2 >= 440 && top.getMinY()-2 <= 447) && spike2){
            //user dies
            moveU = false; moveR = false; moveD = false; moveL = false;
            standU = false; standR = false; standD = false; standL = false;
            dead = true;
        }
        return false;
    }

    public boolean rightColl(){

        if((bottom.getMinY() >= 155 && bottom.getMinY() <= 168) && (bottom.getMaxX()+2 >= 720 && bottom.getMaxX()+2 <= 730)){
            return true;
        }
        if((bottom.getMinY() >= 169 && bottom.getMinY() <= 750) && (bottom.getMaxX()+2 >= 744 && bottom.getMaxX()+2 <= 750)){
            return true;
        }
        if((bottom.getMinY() >= 511 && bottom.getMinY() <= 602) && (bottom.getMaxX()+2 >= 488 && bottom.getMaxX()+2 <= 495)){
            return true;
        }
        if((bottom.getMinY() >= 476 && bottom.getMinY() <= 510) && (bottom.getMaxX()+2 >= 484 && bottom.getMaxX()+2 <= 490)){
            return true;
        }
        if((bottom.getMinY() >= 347 && bottom.getMinY() <= 476) && (bottom.getMaxX()+2 >= 520 && bottom.getMaxX()+2 <= 530)){
            return true;
        }
        if((bottom.getMinY() >= 283 && bottom.getMinY() <= 602) && (bottom.getMaxX()+2 >= 296 && bottom.getMaxX()+2 <= 305)){
            return true;
        }
        if((bottom.getMinY() >= 267 && bottom.getMinY() <= 282) && (bottom.getMaxX()+2 >= 298 && bottom.getMaxX()+2 <= 305)){
            return true;
        }
        return false;
    }

    public boolean downColl(){

        if((bottom.getMaxX() >= 491 && bottom.getMinX() <= 744) && (bottom.getMinY()+2 >= 700 && bottom.getMinY()+2 <= 710)){
            return true;
        }
        if((bottom.getMaxX() >= 151 && bottom.getMinX() <= 404) && (bottom.getMinY()+2 >= 700 && bottom.getMinY()+2 <= 710)){
            return true;
        }
        if((bottom.getMaxX() >= 299 && bottom.getMinX() <= 596) && (bottom.getMinY()+2 >= 266 && bottom.getMinY()+2 <= 275)){
            return true;
        }
        if((bottom.getMaxX() >= 484 && bottom.getMinX() <= 520) && (bottom.getMinY()+2 >= 476 && bottom.getMinY()+2 <= 485)){
            return true;
        }
        if((bottom.getMaxX() >= 375 && bottom.getMinX() <= 409) && (bottom.getMinY()+2 >= 476 && bottom.getMinY()+2 <= 485)){
            return true;
        }
        if((bottom.getMaxX() >= 415 && bottom.getMinX() <= 479) && (bottom.getMinY()+2 >= 703 && bottom.getMinY()+2 <= 710) && locked){
            return true;
        }
        return false;
    }

    public boolean leftColl(){

        //Each if statement checks for a wall segment
        if((bottom.getMinY() >= 155 && bottom.getMinY() <= 171) && (bottom.getMinX()-2 >= 165 && bottom.getMinX()-2 <= 174)){
            return true;
        }
        if((bottom.getMinY() >= 172 && bottom.getMinY() <= 750) && (bottom.getMinX()-2 >= 145 && bottom.getMinX()-2 <= 151)){
            return true;
        }
        if((bottom.getMinY() >= 267 && bottom.getMinY() <= 282) && (bottom.getMinX()-2 >= 590 && bottom.getMinX()-2 <= 597)){
            return true;
        }
        if((bottom.getMinY() >= 283 && bottom.getMinY() <= 602) && (bottom.getMinX()-2 >= 590 && bottom.getMinX()-2 <= 599)){
            return true;
        }
        if((bottom.getMinY() >= 347 && bottom.getMinY() <= 476) && (bottom.getMinX()-2 >= 365 && bottom.getMinX()-2 <= 375)){
            return true;
        }
        if((bottom.getMinY() >= 476 && bottom.getMinY() <= 511) && (bottom.getMinX()-2 >= 400 && bottom.getMinX()-2 <= 409)){
            return true;
        }
        if((bottom.getMinY() >= 511 && bottom.getMinY() <= 602) && (bottom.getMinX()-2 >= 400 && bottom.getMinX()-2 <= 407)){
            return true;
        }
        return false;
    }
    /*
        Makes changes based on keyboard input
        Changes characters motion
        Reset all other possible previous movement, once movement in direction has been setopped reassign the stand still image
        Note: delta is the amount that has passed since the last update, there is approx 20 milliseconds between every update
    */

    public void movement(int delta){
        //Stop soundtrack and play sound
        //Moves up by 3 Px approx every 20 miliseconds (Approx 150 px per second)
        if(in.isKeyDown(Input.KEY_UP) && !moveR && !moveD && !moveL){
            if(!walking.playing() && !keyUnlock.playing() && MainS.soundOn){walking.loop(1f, 0.8f);}
            standU = false; standR = false; standD = false; standL = false;
            moveU = true; moveR = false; moveD = false; moveL = false;
            upA.update(delta);
            if(!upColl()){
                Y -= 2; 
                top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);
            }
        }else if(moveU){
            standU = true; moveU = false;
            if(walking.playing()){walking.stop();}
        }
        else if(in.isKeyDown(Input.KEY_RIGHT) && !moveD && !moveL){
            if(!walking.playing() && !keyUnlock.playing() && MainS.soundOn){walking.loop(1f, 0.8f);}
            standU = false; standR = false; standD = false; standL = false;
            moveU = false; moveR = true; moveD = false; moveL = false;
            rightA.update(delta);
            if(!rightColl()){
                X += 2; 
                top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);
            }
        }else if(moveR){
            standR = true; moveR = false;
            if(walking.playing()){walking.stop();} 
        }
        else if(in.isKeyDown(Input.KEY_DOWN) && !moveL){
            if(!walking.playing() && !keyUnlock.playing() && MainS.soundOn){walking.loop(1f, 0.8f);}
            standU = false; standR = false; standD = false; standL = false;
            moveU = false; moveR = false; moveD = true; moveL = false;
            downA.update(delta);
            if(!downColl()){
                Y += 2; 
                top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);
            }
        }else if(moveD){
            standD = true; moveD = false;
            if(walking.playing()){walking.stop();}
        }
        else if(in.isKeyDown(Input.KEY_LEFT)){
            if(!walking.playing() && !keyUnlock.playing() && MainS.soundOn){walking.loop(1f, 0.8f);}
            standU = false; standR = false; standD = false; standL = false;
            moveU = false; moveR = false; moveD = false; moveL = true;
            leftA.update(delta);
            if(!leftColl()){
                X -= 2; 
                top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);
            }
        }else if(moveL){
            standL = true; moveL = false;
            if(walking.playing()){walking.stop();}
        }
        //For idle 
        if(standU){upS.update(delta);}
        else if(standR){rightS.update(delta);}
        else if(standD){downS.update(delta);}
        else if(standL){leftS.update(delta);}
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        if(!dead){
        mX = in.getMouseX(); mY = in.getMouseY();  

        //Settings Icon
        if((mX >= 830 && mX <= 890) && (mY >= 730 && mY <= 790) && locked){
            settings = false;
            if(in.isMousePressed(0)){
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                if(openSettings){openSettings = false;}
                else{openSettings = true;}
            }
        }else{settings = true;}
        
        //Icons in settings
        if(openSettings){
        try {buttonCheck(sbg);} 
        catch (IOException e) {e.printStackTrace();}
        }  
        //key
        key.update(delta);

        if(!openSettings && locked){
        movement(delta);
        }
        //Check for entering room 1 or room 2
        if((top.getMaxX() >= 205 && top.getMinX() <= 242) && (top.getMinY()-2 >= 145 && top.getMinY()-2 <= 154)){
            level1room1.X = 273; level1room1.Y = 200;
            level1room1.moveU = false; level1room1.standU = false; level1room1.standD = true;
            level1room1.top = new Rectangle(273+30, 200+60, 20, 1); level1room1.bottom = new Rectangle(273+30, 200+65, 20, 1);
            if(walking.playing()){walking.stop();}
            sbg.enterState(4, new FadeOutTransition(), new FadeInTransition());
        }
        if((top.getMaxX() >= 653 && top.getMinX() <= 690) && (top.getMinY()-2 >= 145 && top.getMinY()-2 <= 154)){
            level1room2.X = 120; level1room2.Y = 115;
            level1room2.moveU = false; level1room2.standU = false; level1room2.standD = true;
            level1room2.top = new Rectangle(120+30, 115+60, 20, 1); level1room2.bottom = new Rectangle(120+30, 115+65, 20, 1);
            if(walking.playing()){walking.stop();}
            sbg.enterState(5, new FadeOutTransition(), new FadeInTransition());
        }
        //Check for collecting key use shapes
        if(keyBox.contains(top) || keyBox.intersects(top)){
            //play key collect
            if(MainS.soundOn){walking.stop(); keyUnlock.play(1f, 0.8f);}
            showKey = false;
            keyBox = new Rectangle(0, 0, 0, 0);
        }
        //Check for completing level door
        if((bottom.getMaxX() >= 415 && bottom.getMinX() <= 479) && (bottom.getMinY()+2 >= 703 && bottom.getMinY()+2 <= 710) && !showKey){
            //user wins
            locked = false;
            //Play winning sound and head back to the map screen
            if(MainS.soundOn && !levelcomplete.playing()){walking.stop(); levelcomplete.play(1f, 0.8f);}
            moveU = false; moveR = false; moveD = false; moveL = false;
            standU = false; standR = false; standD = true; standL = false;
            time += delta;
            if(time >= 1700){
                time = 0;
                //Write to file new level
                nextLevel();
                level1.restart(); level1room1.restartRoom1(); level1room2.restartRoom2(); try { Map.restart();} catch (IOException e) { e.printStackTrace();}
                sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
        }
    
    }else{
            time += delta;
            if(time >= 1000){
                //pLAY DYING SOUND
                if(MainS.soundOn){lost.play(1f, 0.8f);}
            restart(); level1room1.restartRoom1(); level1room2.restartRoom2();
            sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
            }
    }



    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        //Map
        //Must set a background
        g.setBackground(new Color(47, 47, 46));
        layout.draw(0, 0);
        //Lock on door
        if(locked){
        lock.draw(438, 716, 18, 18);
        }
        //spikes
        if(spike1){
            spikeOn.draw(384, 384, 32, 32);spikeOn.draw(416, 384, 32, 32);spikeOn.draw(448, 384, 32, 32);spikeOn.draw(480, 384, 32, 32);
        }else{
            spikeOff.draw(384, 384, 32, 32);spikeOff.draw(416, 384, 32, 32);spikeOff.draw(448, 384, 32, 32);spikeOff.draw(480, 384, 32, 32);
        }
        if(spike2){
            spikeOn.draw(384, 416, 32, 32);spikeOn.draw(416, 416, 32, 32);spikeOn.draw(448, 416, 32, 32);spikeOn.draw(480, 416, 32, 32);
        }else{
            spikeOff.draw(384, 416, 32, 32);spikeOff.draw(416, 416, 32, 32);spikeOff.draw(448, 416, 32, 32);spikeOff.draw(480, 416, 32, 32);
        }
            
        //key
        if(showKey){
        key.draw(432, 349);
        }

       //Dying
       if(dead){
           moveU = false; standU = false;
           upDyingA.draw(X, Y, 80, 80);
        }

       //For standing stance
       if(standU){upS.draw(X, Y, 80, 80);}
       if(standD){downS.draw(X, Y, 80, 80);}
       if(standR){rightS.draw(X, Y, 80, 80);}
       if(standL){leftS.draw(X, Y, 80, 80);}
       //For movement
       if(moveU){upA.draw(X, Y, 80, 80);}
       if(moveR){rightA.draw(X, Y, 80, 80);}
       if(moveD){downA.draw(X, Y, 80, 80);}
       if(moveL){leftA.draw(X, Y, 80, 80);}

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

    @Override
    public int getID() {
        return 3;
    }
    
}
