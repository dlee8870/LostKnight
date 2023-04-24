package lostKnight;

import java.io.*;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class level3 extends BasicGameState{

    private static ArrayList<SpriteSheet> text;
    private static ArrayList<Integer> fireballsLY, fireballsRY;
    private static Sound crownCollect;
    private static ArrayList<SpriteSheet> fireballsL, fireballsR;
    private static ArrayList<Shape> fireballBoxL, fireballBoxR;
    private static Input in;
    private Animation upDyingA, downDyingA, rightDyingA, leftDyingA, crown, king;
    private static SpriteSheet layout, fireball, spikeOn, spikeOff, lostKing, toBeContinued;
    public static Shape top, bottom, playerBox, crownBox;
    public static Shape spike1, spike2, spike3, spike4, spike5, spike6, spike7, spike8, spike9, spike10, spike11, spike12, spike13;
    public static int Y, X;
    private static int mX, mY, time, timeForSpike, counter;
    private static boolean deadU, deadR, deadD, deadL;
    private static boolean spikes1, spikes2, dead;
    public static boolean moveU, moveR, moveD, moveL;//Tracks which direction to move
    public static boolean standU, standR, standD, standL;//Tracks the last movement before stoping to track which direction to stand still
    private static boolean settings, openSettings, Exit, openExit, Restart, openRestart, win, winscreen;

    public static void restart(){
        fireballsLY = new ArrayList<>(); time = 0; win = false; winscreen = false; crownBox = new Rectangle(419, 72, 60, 51); timeForSpike = 0;
        fireballsL = new ArrayList<>(); fireballBoxL = new ArrayList<>(); fireballsR = new ArrayList<>(); fireballsRY = new ArrayList<>(); fireballBoxR = new ArrayList<>();
        fireballsL.add(fireball); fireballsR.add(fireball); fireballBoxL.add(new Rectangle(190, 295, 32, 32)); fireballBoxR.add(new Rectangle(670, 295, 32, 32)); fireballsLY.add(295); fireballsRY.add(295);
        settings = true; openSettings = false; dead = false;
        counter = 0;
        Restart = true; Exit = true; openExit = false; openRestart = false;
        Y = 532; X = 403;  deadU = false; deadR = false; deadD = false; deadL = false;
        top = new Rectangle(X+30, Y+58, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = false; standR = false; standD = true; standL = false;//Default is up
        spikes1 = true; spikes2 = false;

    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        crownCollect = new Sound("Sound\\fullGameWin.ogg"); timeForSpike = 0;
        toBeContinued = new SpriteSheet("Images\\level3\\continue.png", 500, 500);
        text = new ArrayList<>();
        text.add(new SpriteSheet("Images\\text\\text4line1.png", 500, 500)); text.add(new SpriteSheet("Images\\text\\text4line2.png", 500, 500)); text.add(new SpriteSheet("Images\\text\\text4line3.png", 500, 500)); 
        upDyingA = new Animation(new SpriteSheet("Images\\KnightUp\\UDeath.png", 48, 48), 200); rightDyingA = new Animation(new SpriteSheet("Images\\KnightRight\\RDeath.png", 48, 48), 200);
        downDyingA = new Animation(new SpriteSheet("Images\\KnightDown\\DDeath.png", 48, 48), 200); leftDyingA = new Animation(new SpriteSheet("Images\\KnightLeft\\LDeath.png", 48, 48), 200);
        fireballsL = new ArrayList<>(); fireballBoxL = new ArrayList<>(); fireballsLY = new ArrayList<>(); fireballsR = new ArrayList<>(); fireballsRY = new ArrayList<>(); fireballBoxR = new ArrayList<>();
        fireball = new SpriteSheet("Images\\level3\\fireball.png", 32, 80); crownBox = new Rectangle(419, 72, 60, 55);
        crown = new Animation(new SpriteSheet("Images\\level3\\crown.png", 1000, 1000), 300);
        lostKing = new SpriteSheet("Images\\level3\\lostKing.png", 334, 49);
        fireballsLY.add(295); fireballsRY.add(295); fireballsR.add(fireball); time = 0;
        fireballsL.add(fireball); fireballBoxL.add(new Rectangle(190, 295, 32, 32)); fireballBoxR.add(new Rectangle(670, 295, 32, 32));
        layout = new SpriteSheet("Images\\level3\\level3.png", 896, 800); king = new Animation(new SpriteSheet("Images\\level3\\king.png", 48, 48), 290);
        spikeOn = new SpriteSheet("Images\\level1\\spikeOn.png", 26, 27); spikeOff = new SpriteSheet("Images\\level1\\spikeOff.png", 26, 24);
        in = gc.getInput();
        settings = true; openSettings = false; win = false; winscreen = false; dead = false;
        Restart = true; Exit = true; openExit = false; openRestart = false;
        Y = 532; X = 403; counter = 0;
        playerBox = new Rectangle(X, Y+6, 40, 60);
        spikes1 = true; spikes2 = false;
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = false; standR = false; standD = true; standL = false;//Default is up
        spike1 = new Rectangle(320, 287, 63, 32); spike2 = new Rectangle(511, 287, 64, 32); spike3 = new Rectangle(255, 316, 32, 141); spike4 = new Rectangle(607, 316, 32, 141); spike5 = new Rectangle(703, 383, 32, 32);
        spike6 = new Rectangle(159, 383, 32, 32); spike7 = new Rectangle(127, 479, 96, 32); spike8 = new Rectangle(671, 479, 96, 32); spike9 = new Rectangle(159, 607, 32, 32); spike10 = new Rectangle(703, 607, 32, 32);
        spike11 = new Rectangle(255, 639, 32, 93); spike12 = new Rectangle(607, 639, 32, 93); spike13 = new Rectangle(383, 607, 128, 32);

    }

    public static void nextLevel(){

        try {

            BufferedReader in = new BufferedReader(new FileReader(new File("Info\\level.txt")));
            int level = Integer.parseInt(in.readLine());
            in.close();
            BufferedWriter br = new BufferedWriter(new FileWriter(new File("Info\\level.txt"), false));

            if(level == 3){level++;}
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
                restart();
                sbg.enterState(7, new FadeOutTransition(), new FadeInTransition());
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

    public void fireballMove(int delta){
        //Moves fireball down
        for(int i = 0; i < fireballsLY.size(); i++){
            int y = fireballsLY.get(i);
            fireballsLY.set(i, y+4); fireballsRY.set(i, y+4);
            fireballBoxL.get(i).setY(y+4); fireballBoxR.get(i).setY(y+4);
        }

        for(int i = fireballsL.size() - 1; i >= 0; i--){
            Shape c = fireballBoxL.get(i), c2 = fireballBoxR.get(i);
            //Check for adding new fireball
            if(c.getMaxY() > 550 && fireballsL.size() < 2){
                fireballsLY.add(295); fireballsL.add(fireball); fireballBoxL.add(new Rectangle(190, 295, 32, 32));
                fireballsRY.add(295); fireballsR.add(fireball); fireballBoxR.add(new Rectangle(670, 295, 32, 32));
            }
            //Out of bounds
            if(c.getMaxY() > 732){
                fireballBoxL.remove(i); fireballsL.remove(i); fireballsLY.remove(i);
                fireballBoxR.remove(i); fireballsR.remove(i); fireballsRY.remove(i);
            }else if(c.intersects(playerBox) || c.contains(playerBox) || c2.intersects(playerBox) || c2.contains(playerBox)){//Add shape around character
                if(moveU || standU){deadU = true;}
                else if(moveR || standR){deadR = true;}
                else if(moveD || standD){deadD = true;}
                else if(moveL || standL){deadL = true;}
                dead = true;
            }
        }
    }
    public boolean spikeCollision(){
        if((spike1.contains(bottom) || spike1.intersects(bottom) || spike1.contains(top) || spike1.intersects(top)) && spikes2){
            return true;
        }
        if((spike2.contains(bottom) || spike2.intersects(bottom) || spike2.contains(top) || spike2.intersects(top)) && spikes2){
            return true;
        }
        if((spike3.contains(bottom) || spike3.intersects(bottom) || spike3.contains(top) || spike3.intersects(top)) && spikes1){
            return true;
        }
        if((spike4.contains(bottom) || spike4.intersects(bottom) || spike4.contains(top) || spike4.intersects(top)) && spikes1){
            return true;
        }
        if(spike5.contains(bottom) || spike5.intersects(bottom) || spike5.contains(top) || spike5.intersects(top)){
            return true;
        }
        if(spike6.contains(bottom) || spike6.intersects(bottom) || spike6.contains(top) || spike6.intersects(top)){
            return true;
        }
        if((spike7.contains(bottom) || spike7.intersects(bottom) || spike7.contains(top) || spike7.intersects(top)) && spikes2){
            return true;
        }
        if((spike8.contains(bottom) || spike8.intersects(bottom) || spike8.contains(top) || spike8.intersects(top)) && spikes2){
            return true;
        }
        if(spike9.contains(bottom) || spike9.intersects(bottom) || spike9.contains(top) || spike9.intersects(top)){
            return true;
        }
        if(spike10.contains(bottom) || spike10.intersects(bottom) || spike10.contains(top) || spike10.intersects(top)){
            return true;
        }
        if((spike11.contains(bottom) || spike11.intersects(bottom) || spike11.contains(top) || spike11.intersects(top)) && spikes1){
            return true;
        }
        if((spike12.contains(bottom) || spike12.intersects(bottom) || spike12.contains(top) || spike12.intersects(top)) && spikes1){
            return true;
        }
        if((spike13.contains(bottom) || spike13.intersects(bottom) || spike13.contains(top) || spike13.intersects(top))  && spikes1){
            return true;
        }
        return false;
    }


    public boolean upColl(){

        //Each if statement checks for a wall segment
        if((top.getMaxX() >= 119 && top.getMinX() <= 311) && (top.getMinY()-2 >= 305 && top.getMinY()-2 <= 315)){
            return true;
        }
        if((top.getMaxX() >= 311 && top.getMinX() <= 411) && (top.getMinY()-2 >= 241 && top.getMinY()-2 <= 251)){
            return true;
        }
        if((top.getMaxX() >= 407 && top.getMinX() <= 488) && (top.getMinY()-2 >= 49 && top.getMinY()-2 <= 59)){
            return true;
        }
        if((top.getMaxX() >= 483 && top.getMinX() <= 584) && (top.getMinY()-2 >= 241 && top.getMinY()-2 <= 251)){
            return true;
        }
        if((top.getMaxX() >= 584 && top.getMinX() <= 776) && (top.getMinY()-2 >= 305 && top.getMinY()-2 <= 315)){
            return true;
        }
        if((top.getMaxX() >= 392 && top.getMinX() <= 503) && (top.getMinY()-2 >= 401 && top.getMinY()-2 <= 411)){
            return true;
        }
        if((top.getMaxX() >= 232 && top.getMinX() <= 375) && (top.getMinY()-2 >= 625 && top.getMinY()-2 <= 635)){
            return true;
        }
        if((top.getMaxX() >= 375 && top.getMinX() <= 520) && (top.getMinY()-2 >= 529 && top.getMinY()-2 <= 539)){
            return true;
        }
        if((top.getMaxX() >= 520 && top.getMinX() <= 663) && (top.getMinY()-2 >= 625 && top.getMinY()-2 <= 635)){
            return true;
        }
        
        return false;
        

    }

    public boolean rightColl(){

        if((bottom.getMinY() >= 251 && bottom.getMinY() <= 315) && (bottom.getMaxX()+2 >= 584 && bottom.getMaxX()+2 <= 594)){
            return true;
        }
        if((bottom.getMinY() >= 59 && bottom.getMinY() <= 184) && (bottom.getMaxX()+2 >= 488 && bottom.getMaxX()+2 <= 498)){
            return true;
        }
        if((bottom.getMinY() >= 186 && bottom.getMinY() <= 251) && (bottom.getMaxX()+2 >= 483 && bottom.getMaxX()+2 <= 493)){
            return true;
        }
        if((bottom.getMinY() >= 299 && bottom.getMinY() <= 314) && (bottom.getMaxX()+2 >= 394 && bottom.getMaxX()+2 <= 404)){
            return true;
        }
        if((bottom.getMinY() >= 315 && bottom.getMinY() <= 411) && (bottom.getMaxX()+2 >= 392 && bottom.getMaxX()+2 <= 402)){
            return true;
        }
        if((bottom.getMinY() >= 315 && bottom.getMinY() <= 732) && (bottom.getMaxX()+2 >= 776 && bottom.getMaxX()+2 <= 786)){
            return true;
        }
        if((bottom.getMinY() >= 539 && bottom.getMinY() <= 635) && (bottom.getMaxX()+2 >= 520 && bottom.getMaxX()+2 <= 530)){
            return true;
        }
        if((bottom.getMinY() >= 459 && bottom.getMinY() <= 474) && (bottom.getMaxX()+2 >= 234 && bottom.getMaxX()+2 <= 244)){
            return true;
        }
        if((bottom.getMinY() >= 475 && bottom.getMinY() <= 635) && (bottom.getMaxX()+2 >= 232 && bottom.getMaxX()+2 <= 242)){
            return true;
        }
        
        return false;
    }

    public boolean downColl(){
        if((bottom.getMaxX() >= 408 && bottom.getMinX() <= 410) && (bottom.getMinY()+2 >= 186 && bottom.getMinY()+2 <= 196)){
            return true;
        }
        if((bottom.getMaxX() >= 485 && bottom.getMinX() <= 487) && (bottom.getMinY()+2 >= 185 && bottom.getMinY()+2 <= 195)){
            return true;
        }
        if((bottom.getMaxX() >= 483 && bottom.getMinX() <= 484) && (bottom.getMinY()+2 >= 186 && bottom.getMinY()+2 <= 196)){
            return true;
        }
        if((bottom.getMaxX() >= 395 && bottom.getMinX() <= 500) && (bottom.getMinY()+2 >= 298 && bottom.getMinY()+2 <= 308)){
            return true;
        }
        if((bottom.getMaxX() >= 501 && bottom.getMinX() <= 502) && (bottom.getMinY()+2 >= 314 && bottom.getMinY()+2 <= 324)){
            return true;
        }
        if((bottom.getMaxX() >= 393 && bottom.getMinX() <= 394) && (bottom.getMinY()+2 >= 314 && bottom.getMinY()+2 <= 324)){
            return true;
        }
        if((bottom.getMaxX() >= 235 && bottom.getMinX() <= 660) && (bottom.getMinY()+2 >= 458 && bottom.getMinY()+2 <= 468)){
            return true;
        }
        if((bottom.getMaxX() >= 119 && bottom.getMinX() <= 776) && (bottom.getMinY()+2 >= 732 && bottom.getMinY()+2 <= 742)){
            return true;
        }
        if((bottom.getMaxX() >= 408 && bottom.getMinX() <= 410) && (bottom.getMinY()+2 >= 186 && bottom.getMinY()+2 <= 196)){
            return true;
        }

        return false;
    }

    public boolean leftColl(){
        if((bottom.getMinY() >= 251 && bottom.getMinY() <= 315) && (bottom.getMinX()-2 >= 301 && bottom.getMinX()-2 <= 311)){
            return true;
        }
        if((bottom.getMinY() >= 188 && bottom.getMinY() <= 251) && (bottom.getMinX()-2 >= 401 && bottom.getMinX()-2 <= 411)){
            return true;
        }
        if((bottom.getMinY() >= 186 && bottom.getMinY() <= 187) && (bottom.getMinX()-2 >= 400 && bottom.getMinX()-2 <= 410)){
            return true;
        }
        if((bottom.getMinY() >= 59 && bottom.getMinY() <= 185) && (bottom.getMinX()-2 >= 397 && bottom.getMinX()-2 <= 407)){
            return true;
        }
        if((bottom.getMinY() >= 315 && bottom.getMinY() <= 732) && (bottom.getMinX()-2 >= 109 && bottom.getMinX()-2 <= 119)){
            return true;
        }
        if((bottom.getMinY() >= 315 && bottom.getMinY() <= 411) && (bottom.getMinX()-2 >= 493 && bottom.getMinX()-2 <= 503)){
            return true;
        }
        if((bottom.getMinY() >= 299 && bottom.getMinY() <= 314) && (bottom.getMinX()-2 >= 491 && bottom.getMinX()-2 <= 501)){
            return true;
        }
        if((bottom.getMinY() >= 539 && bottom.getMinY() <= 635) && (bottom.getMinX()-2 >= 365 && bottom.getMinX()-2 <= 375)){
            return true;
        }
        if((bottom.getMinY() >= 475 && bottom.getMinY() <= 635) && (bottom.getMinX()-2 >= 653 && bottom.getMinX()-2 <= 663)){
            return true;
        }
        if((bottom.getMinY() >= 459 && bottom.getMinY() <= 474) && (bottom.getMinX()-2 >= 651 && bottom.getMinX()-2 <= 661)){
            return true;
        }

        return false;
    }

    public void movement(int delta){

        //Stop soundtrack and play sound
        //Moves up by 2 Px approx every 20 miliseconds (Approx 100 px per second)
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

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        //Time for turning spikes on and off
        if(!winscreen){
        timeForSpike += delta;
        if(timeForSpike >= 1500){
            timeForSpike = 0;
            if(spikes1){
                spikes2 = true;
                spikes1 = false;
            }else{
                spikes1 = true;
                spikes2 = false;
            }
        }
    }


        if(!dead && !win && !winscreen){
        mX = in.getMouseX(); mY = in.getMouseY();
        playerBox = new Rectangle(X+27, Y+18, 23, 50);

        //Check for win
        if(crownBox.contains(top) || crownBox.intersects(top)){
            if(MainS.soundOn && !crownCollect.playing()){level1.walking.stop(); crownCollect.play(1f, 0.9f);}
            win = true;
        }else{
            crown.update(delta);
        }
        
        //Settings
        if((mX >= 830 && mX <= 890) && (mY >= 730 && mY <= 790)){

            settings = false;
            if(in.isMousePressed(0)){

                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                if(openSettings){openSettings = false;}
                else{openSettings = true;}
            }
        }else{settings = true;}
        
        //Icons in settings
        if(openSettings){

            try { buttonCheck(sbg); } 
            catch (IOException e) { e.printStackTrace(); }
        }  
        if(!openSettings){
            movement(delta);
        }

        fireballMove(delta);

        if(spikeCollision()){
            if(moveU || standU){deadU = true;}
                else if(moveR || standR){deadR = true;}
                else if(moveD || standD){deadD = true;}
                else if(moveL || standL){deadL = true;}
                dead = true;
        }
        
        }else if(dead){
            //For losing play for the same amount of time as the dying animation
            time += delta;
            if(MainS.soundOn && !level1.lost.playing()){level1.walking.stop(); level1.lost.play(1f, 0.8f);}
                level1.walking.stop();
                if(time >= 1100){
                    restart();
                    sbg.enterState(7, new FadeOutTransition(), new FadeInTransition());
            }
        }else if(win){
            level1.walking.stop();
            moveU = false; moveR = false; moveD = false; moveL = false;
            standU = true; standR = false; standD = false; standL = false;
            time += delta;
            if(time >= 1200){
                win = false;
                winscreen = true;
            }
        }else if(winscreen){
            king.update(delta);
            if(in.isMousePressed(0) && counter <= text.size()){
                counter++;
            }
            if(counter == text.size()+1){
                nextLevel(); restart(); try { Map.restart();} catch (IOException e) {e.printStackTrace();}
                sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
        }
    }

    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if(!winscreen){
        g.setBackground(new Color(47, 47, 46));
        layout.draw(0, 0);
        
        //Showing crown
        if(!win){crown.draw(419, 72, 60, 51);}

        //Spikes
        spikes();
        
        //For standing stance
        if(!dead){
        if(standU){level1.upS.draw(X, Y, 80, 80);}
        if(standD){level1.downS.draw(X, Y, 80, 80);}
        if(standR){level1.rightS.draw(X, Y, 80, 80);}
        if(standL){level1.leftS.draw(X, Y, 80, 80);}
        //For movement
        if(moveU){level1.upA.draw(X, Y, 80, 80);}
        if(moveR){level1.rightA.draw(X, Y, 80, 80);}
        if(moveD){level1.downA.draw(X, Y, 80, 80);}
        if(moveL){level1.leftA.draw(X, Y, 80, 80);}
        }
        //For death
        if(dead){
        if(deadU){upDyingA.draw(X, Y, 80, 80);}
        if(deadR){rightDyingA.draw(X, Y, 80, 80);}
        if(deadD){downDyingA.draw(X, Y, 80, 80);}
        if(deadL){leftDyingA.draw(X, Y, 80, 80);}
        }
        //Fireballs
        if(!win){
        fireballsL();
        fireballsR();
        }

        //Settings Icon
    if(openSettings){
        MainS.settingPressed.draw(830, 730, 60, 60);
        MainS.border.draw(225, 155, 450, 475);
        SETTINGS();
    }
    else if(settings){MainS.setting.draw(830, 730, 60, 60);}
    else{MainS.settingOn.draw(830, 730, 60, 60);}

    }else{
        DisplayWinScreen(g);
    }
}


    public void DisplayWinScreen(Graphics g){ 
    //todo
        MainS.back.draw(0, 0, 900, 800);
        king.draw(265, 200, 350, 350);
        lostKing.draw(175, 50);

        if(counter < text.size()){
            Map.textBox.draw(150, 570, 600, 160);
            text.get(counter).draw(185, 605); 
        }else{
            toBeContinued.draw(160, 570);
        }
    }    

    public void fireballsR(){
        //Right side
        for(int i=0; i < fireballsR.size(); i++){
            fireballsR.get(i).draw(670, fireballsRY.get(i), 32, 32);
        }
    }

    public void fireballsL(){
        //Left side
        for(int i = 0; i < fireballsL.size(); i++){
            fireballsL.get(i).draw(190, fireballsLY.get(i), 32, 32);
        }
    }

    public void spikes(){
        
        spikeOn.draw(160, 384, 32, 32);
        spikeOn.draw(704, 384, 32, 32);
        spikeOn.draw(704, 608, 32, 32);
        spikeOn.draw(160, 608, 32, 32);

        if(spikes1){
            spikeOn.draw(384, 608, 32, 32);spikeOn.draw(416, 608, 32, 32);
            spikeOn.draw(448, 608, 32, 32);spikeOn.draw(480, 608, 32, 32);
            
            spikeOn.draw(256, 320, 32, 32);spikeOn.draw(256, 352, 32, 32);
            spikeOn.draw(256, 384, 32, 32);spikeOn.draw(256, 416, 32, 32);
            
            spikeOn.draw(608, 320, 32, 32);spikeOn.draw(608, 352, 32, 32);
            spikeOn.draw(608, 384, 32, 32);spikeOn.draw(608, 416, 32, 32);

            spikeOn.draw(608, 640, 32, 32);spikeOn.draw(608, 671, 32, 32);
            spikeOn.draw(608, 704, 30, 30);

            spikeOn.draw(256, 640, 32, 32);spikeOn.draw(256, 672, 32, 32);
            spikeOn.draw(256, 704, 30, 30);
        }
        else{
            spikeOff.draw(384, 608, 32, 32);spikeOff.draw(416, 608, 32, 32);
            spikeOff.draw(448, 608, 32, 32);spikeOff.draw(480, 608, 32, 32);

            spikeOff.draw(256, 320, 32, 32);spikeOff.draw(256, 352, 32, 32);
            spikeOff.draw(256, 384, 32, 32);spikeOff.draw(256, 416, 32, 32);
            
            spikeOff.draw(608, 320, 32, 32);spikeOff.draw(608, 352, 32, 32);
            spikeOff.draw(608, 384, 32, 32);spikeOff.draw(608, 416, 32, 32);

            spikeOff.draw(608, 640, 32, 32);spikeOff.draw(608, 671, 32, 32);
            spikeOff.draw(608, 704, 30, 30);

            spikeOff.draw(256, 640, 32, 32);spikeOff.draw(256, 672, 32, 32);
            spikeOff.draw(256, 704, 30, 30);
        }
        
        if(spikes2){
            spikeOn.draw(672, 480, 32, 32);spikeOn.draw(704, 480, 32, 32);
            spikeOn.draw(736, 480, 32, 32);

            spikeOn.draw(128, 480, 32, 32);spikeOn.draw(160, 480, 32, 32);
            spikeOn.draw(192, 480, 32, 32);
            
            spikeOn.draw(320, 288, 32, 32);spikeOn.draw(352, 288, 32, 32);

            spikeOn.draw(512, 288, 32, 32);spikeOn.draw(544, 288, 32, 32);

        }
        else{
            spikeOff.draw(672, 480, 32, 32);spikeOff.draw(704, 480, 32, 32);
            spikeOff.draw(736, 480, 32, 32);

            spikeOff.draw(128, 480, 32, 32);spikeOff.draw(160, 480, 32, 32);
            spikeOff.draw(192, 480, 32, 32);

            spikeOff.draw(320, 288, 32, 32);spikeOff.draw(352, 288, 32, 32);

            spikeOff.draw(512, 288, 32, 32);spikeOff.draw(544, 288, 32, 32);

        }
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
    
    public int getID() {
        return 7;
    }
    
}
