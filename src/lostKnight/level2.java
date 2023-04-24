package lostKnight;

import java.io.*;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class level2 extends BasicGameState{
    
    private static Input in;
    private static Shape key1Box, key2Box, key3Box;
    private static Animation keys;
    private static SpriteSheet layout, layoutWalls, lock;
    public static Shape top, bottom;
    public static int Y, X;
    private static int mX, mY, time, timeForWalls, timeLeft;
    public static boolean moveU, moveR, moveD, moveL;//Tracks which direction to move
    public static boolean standU, standR, standD, standL;//Tracks the last movement before stoping to track which direction to stand still
    private static boolean settings, openSettings, Exit, openExit, key1, key2, key3, Restart, openRestart, checkLock, win, showWalls, lost;

    public static void restart(){
        key2Box = new Rectangle(567, 252, 32, 32); key1Box = new Rectangle(249, 336, 32, 32); key3Box = new Rectangle(729, 486, 32, 32);
        Restart = true; checkLock = false; time = 0; win = false; timeForWalls = 0;
        Exit = true; key1 = true; key2 = true; key3 = true; timeLeft = 30000;
        settings = true; openSettings = false; showWalls = true;
        Restart = true; Exit = true; openExit = false; openRestart = false;
        Y = 611; X = 174; lost = false;
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = false; standR = false; standD = true; standL = false;//Default is up
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        key2Box = new Rectangle(567, 252, 32, 32); key1Box = new Rectangle(249, 336, 32, 32); key3Box = new Rectangle(729, 486, 32, 32);
        in = gc.getInput(); layout = new SpriteSheet("Images\\level2\\level2.png", 896, 800); layoutWalls = new SpriteSheet("Images\\level2\\level2Walls.png", 896, 800);
        lock = new SpriteSheet("Images\\level1\\lock.png", 12, 12);
        keys = new Animation(new SpriteSheet("Images\\level1\\key.png",32, 32), 300);
        Restart = true; key1 = true; key2 = true; key3 = true; win = false;
        Exit = true; checkLock = false; time = 0; timeForWalls = 0;
        settings = true; openSettings = false; showWalls = true;
        Restart = true; Exit = true; openExit = false; openRestart = false;
        Y = 611; X = 174; timeLeft = 30000; lost = false;
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = false; standR = false; standD = true; standL = false;//Default is up
    }

    public static void nextLevel(){
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("Info\\level.txt")));
            int level = Integer.parseInt(in.readLine());
            in.close();
            BufferedWriter br = new BufferedWriter(new FileWriter(new File("Info\\level.txt"), false));
            if(level == 2){level++;}
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
                sbg.enterState(6, new FadeOutTransition(), new FadeInTransition());
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
        //Each if statement checks for a wall segment
        if((top.getMaxX() >= 142 && top.getMinX() <= 155) && (top.getMinY()-2 >= 141 && top.getMinY()-2 <= 151)){
            return true;
        }
        if((top.getMaxX() >= 156 && top.getMinX() <= 177) && (top.getMinY()-2 >= 145 && top.getMinY()-2 <= 155)){
            return true;
        }
        if((top.getMaxX() >= 178 && top.getMinX() <= 259) && (top.getMinY()-2 >= 141 && top.getMinY()-2 <= 151)){
            return true;
        }
        if((top.getMaxX() >= 298 && top.getMinX() <= 379) && (top.getMinY()-2 >= 141 && top.getMinY()-2 <= 151)){
            return true;
        }
        if((top.getMaxX() >= 380 && top.getMinX() <= 401) && (top.getMinY()-2 >= 145 && top.getMinY()-2 <= 155)){
            return true;
        }
        if((top.getMaxX() >= 402 && top.getMinX() <= 479) && (top.getMinY()-2 >= 141 && top.getMinY()-2 <= 151)){
            return true;
        }
        if((top.getMaxX() >= 480 && top.getMinX() <= 607) && (top.getMinY()-2 >= 237 && top.getMinY()-2 <= 247)){
            return true;
        }
        if((top.getMaxX() >= 668 && top.getMinX() <= 689) && (top.getMinY()-2 >= 465 && top.getMinY()-2 <= 475)){
            return true;
        }
        if((top.getMaxX() >= 608 && top.getMinX() <= 667) && (top.getMinY()-2 >= 461 && top.getMinY()-2 <= 471)){
            return true;
        }
        if((top.getMaxX() >= 690 && top.getMinX() <= 767) && (top.getMinY()-2 >= 461 && top.getMinY()-2 <= 471)){
            return true;
        }
        if((top.getMaxX() >= 288 && top.getMinX() <= 397) && (top.getMinY()-2 >= 525 && top.getMinY()-2 <= 535)){
            return true;
        }
        if((top.getMaxX() >= 142 && top.getMinX() <= 287) && (top.getMinY()-2 >= 461 && top.getMinY()-2 <= 471)){
            return true;
        }
        if((top.getMaxX() >= 247 && top.getMinX() <= 310) && (top.getMinY()-2 >= 141 && top.getMinY()-2 <= 151) && (key1 || key2 || key3)){
            return true;
        }
        if((top.getMaxX() >= 160 && top.getMinX() <= 191) && (top.getMinY()-2 >= 274 && top.getMinY()-2 <= 284)){
            return true;
        }
        if((top.getMaxX() >= 192 && top.getMinX() <= 237) && (top.getMinY()-2 >= 338 && top.getMinY()-2 <= 348)){
            return true;
        }
        if((top.getMaxX() >= 238 && top.getMinX() <= 269) && (top.getMinY()-2 >= 274 && top.getMinY()-2 <= 284)){
            return true;
        }
        if((top.getMaxX() >= 320 && top.getMinX() <= 365) && (top.getMinY()-2 >= 274 && top.getMinY()-2 <= 284)){
            return true;
        }
        if((top.getMaxX() >= 366 && top.getMinX() <= 397) && (top.getMinY()-2 >= 242 && top.getMinY()-2 <= 252)){
            return true;
        }
        if((top.getMaxX() >= 448 && top.getMinX() <= 479) && (top.getMinY()-2 >= 397 && top.getMinY()-2 <= 407)){
            return true;
        }
        if((top.getMaxX() >= 480 && top.getMinX() <= 525) && (top.getMinY()-2 >= 429 && top.getMinY()-2 <= 439)){
            return true;
        }
        if((top.getMaxX() >= 526 && top.getMinX() <= 557) && (top.getMinY()-2 >= 397 && top.getMinY()-2 <= 407)){
            return true;
        }
        if((top.getMaxX() >= 416 && top.getMinX() <= 557) && (top.getMinY()-2 >= 557 && top.getMinY()-2 <= 567)){
            return true;
        }
        if((top.getMaxX() >= 672 && top.getMinX() <= 717) && (top.getMinY()-2 >= 594 && top.getMinY()-2 <= 604)){
            return true;
        }
        if((top.getMaxX() >= 448 && top.getMinX() <= 653) && (top.getMinY()-2 >= 658 && top.getMinY()-2 <= 668)){
            return true;
        }
        if((top.getMaxX() >= 192 && top.getMinX() <= 237) && (top.getMinY()-2 >= 653 && top.getMinY()-2 <= 663)){
            return true;
        }
        if((top.getMaxX() >= 651 && top.getMinX() <= 673) && (top.getMinY()-2 >= 614 && top.getMinY()-2 <= 620)){
            return true;
        }
        return false;
        

    }
    public boolean rightColl(){
        if((bottom.getMinY() >= 151 && bottom.getMinY() <= 154) && (bottom.getMaxX()+2 >= 155 && bottom.getMaxX()+2 <= 165)){
            return true;
        }
        if((bottom.getMinY() >= 151 && bottom.getMinY() <= 154) && (bottom.getMaxX()+2 >= 379 && bottom.getMaxX()+2 <= 389)){
            return true;
        }
        if((bottom.getMinY() >= 151 && bottom.getMinY() <= 246) && (bottom.getMaxX()+2 >= 479 && bottom.getMaxX()+2 <= 489)){
            return true;
        }
        if((bottom.getMinY() >= 247 && bottom.getMinY() <= 470) && (bottom.getMaxX()+2 >= 607 && bottom.getMaxX()+2 <= 617)){
            return true;
        }
        if((bottom.getMinY() >= 471 && bottom.getMinY() <= 474) && (bottom.getMaxX()+2 >= 667 && bottom.getMaxX()+2 <= 677)){
            return true;
        }
        if((bottom.getMinY() >= 471 && bottom.getMinY() <= 696) && (bottom.getMaxX()+2 >= 767 && bottom.getMaxX()+2 <= 777)){
            return true;
        }
        if((bottom.getMinY() >= 615 && bottom.getMinY() <= 630) && (bottom.getMaxX()+2 >= 289 && bottom.getMaxX()+2 <= 299)){
            return true;
        }
        if((bottom.getMinY() >= 631 && bottom.getMinY() <= 696) && (bottom.getMaxX()+2 >= 287 && bottom.getMaxX()+2 <= 297)){
            return true;
        }
        if((bottom.getMinY() >= 471 && bottom.getMinY() <= 534) && (bottom.getMaxX()+2 >= 287 && bottom.getMaxX()+2 <= 297)){
            return true;
        }
        if((bottom.getMinY() >= 327 && bottom.getMinY() <= 342) && (bottom.getMaxX()+2 >= 289 && bottom.getMaxX()+2 <= 299)){
            return true;
        }
        if((bottom.getMinY() >= 343 && bottom.getMinY() <= 376) && (bottom.getMaxX()+2 >= 287 && bottom.getMaxX()+2 <= 297)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 214) && (bottom.getMaxX()+2 >= 161 && bottom.getMaxX()+2 <= 171)){
            return true;
        }
        if((bottom.getMinY() >= 215 && bottom.getMinY() <= 283) && (bottom.getMaxX()+2 >= 159 && bottom.getMaxX()+2 <= 169)){
            return true;
        }
        if((bottom.getMinY() >= 284 && bottom.getMinY() <= 347) && (bottom.getMaxX()+2 >= 191 && bottom.getMaxX()+2 <= 201)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 214) && (bottom.getMaxX()+2 >= 321 && bottom.getMaxX()+2 <= 331)){
            return true;
        }
        if((bottom.getMinY() >= 215 && bottom.getMinY() <= 283) && (bottom.getMaxX()+2 >= 319 && bottom.getMaxX()+2 <= 329)){
            return true;
        }
        if((bottom.getMinY() >= 263 && bottom.getMinY() <= 278) && (bottom.getMaxX()+2 >= 449 && bottom.getMaxX()+2 <= 459)){
            return true;
        }
        if((bottom.getMinY() >= 279 && bottom.getMinY() <= 406) && (bottom.getMaxX()+2 >= 447 && bottom.getMaxX()+2 <= 457)){
            return true;
        }
        if((bottom.getMinY() >= 407 && bottom.getMinY() <= 438) && (bottom.getMaxX()+2 >= 479 && bottom.getMaxX()+2 <= 489)){
            return true;
        }
        if((bottom.getMinY() >= 487 && bottom.getMinY() <= 502) && (bottom.getMaxX()+2 >= 417 && bottom.getMaxX()+2 <= 427)){
            return true;
        }
        if((bottom.getMinY() >= 503 && bottom.getMinY() <= 566) && (bottom.getMaxX()+2 >= 415 && bottom.getMaxX()+2 <= 425)){
            return true;
        }
        if((bottom.getMinY() >= 475 && bottom.getMinY() <= 502) && (bottom.getMaxX()+2 >= 673 && bottom.getMaxX()+2 <= 683)){
            return true;
        }
        if((bottom.getMinY() >= 503 && bottom.getMinY() <= 614) && (bottom.getMaxX()+2 >= 671 && bottom.getMaxX()+2 <= 681)){
            return true;
        }
        if((bottom.getMinY() >= 615 && bottom.getMinY() <= 630) && (bottom.getMaxX()+2 >= 449 && bottom.getMaxX()+2 <= 459)){
            return true;
        }
        if((bottom.getMinY() >= 631 && bottom.getMinY() <= 667) && (bottom.getMaxX()+2 >= 447 && bottom.getMaxX()+2 <= 457)){
            return true;
        }
        if((bottom.getMinY() >= 519 && bottom.getMinY() <= 534) && (bottom.getMaxX()+2 >= 193 && bottom.getMaxX()+2 <= 203)){
            return true;
        }
        if((bottom.getMinY() >= 535 && bottom.getMinY() <= 662) && (bottom.getMaxX()+2 >= 191 && bottom.getMaxX()+2 <= 201)){
            return true;
        }
        if((bottom.getMinY() >= 247 && bottom.getMinY() <= 262) && (bottom.getMaxX()+2 >= 480 && bottom.getMaxX()+2 <= 490)){
            return true;
        }
        return false;
    }
    public boolean downColl(){
        if((bottom.getMaxX() >= 142 && bottom.getMinX() <= 287) && (bottom.getMinY()+2 >= 376 && bottom.getMinY()+2 <= 386)){
            return true;
        }
        if((bottom.getMaxX() >= 288 && bottom.getMinX() <= 289) && (bottom.getMinY()+2 >= 342 && bottom.getMinY()+2 <= 352)){
            return true;
        }
        if((bottom.getMaxX() >= 290 && bottom.getMinX() <= 395) && (bottom.getMinY()+2 >= 326 && bottom.getMinY()+2 <= 336)){
            return true;
        }
        if((bottom.getMaxX() >= 396 && bottom.getMinX() <= 397) && (bottom.getMinY()+2 >= 342 && bottom.getMinY()+2 <= 352)){
            return true;
        }
        if((bottom.getMaxX() >= 398 && bottom.getMinX() <= 767) && (bottom.getMinY()+2 >= 696 && bottom.getMinY()+2 <= 706)){
            return true;
        }
        if((bottom.getMaxX() >= 396 && bottom.getMinX() <= 397) && (bottom.getMinY()+2 >= 630 && bottom.getMinY()+2 <= 640)){
            return true;
        }
        if((bottom.getMaxX() >= 290 && bottom.getMinX() <= 395) && (bottom.getMinY()+2 >= 614 && bottom.getMinY()+2 <= 624)){
            return true;
        }
        if((bottom.getMaxX() >= 288 && bottom.getMinX() <= 289) && (bottom.getMinY()+2 >= 630 && bottom.getMinY()+2 <= 640)){
            return true;
        }
        if((bottom.getMaxX() >= 142 && bottom.getMinX() <= 287) && (bottom.getMinY()+2 >= 696 && bottom.getMinY()+2 <= 706)){
            return true;
        }
        if((bottom.getMaxX() >= 194 && bottom.getMinX() <= 235) && (bottom.getMinY()+2 >= 518 && bottom.getMinY()+2 <= 524)){
            return true;
        }
        if((bottom.getMaxX() >= 192 && bottom.getMinX() <= 193) && (bottom.getMinY()+2 >= 534 && bottom.getMinY()+2 <= 540)){
            return true;
        }
        if((bottom.getMaxX() >= 236 && bottom.getMinX() <= 237) && (bottom.getMinY()+2 >= 534 && bottom.getMinY()+2 <= 540)){
            return true;
        }
        if((bottom.getMaxX() >= 448 && bottom.getMinX() <= 449) && (bottom.getMinY()+2 >= 630 && bottom.getMinY()+2 <= 636)){
            return true;
        }
        if((bottom.getMaxX() >= 450 && bottom.getMinX() <= 671) && (bottom.getMinY()+2 >= 614 && bottom.getMinY()+2 <= 620)){
            return true;
        }
        if((bottom.getMaxX() >= 652 && bottom.getMinX() <= 653) && (bottom.getMinY()+2 >= 630 && bottom.getMinY()+2 <= 636)){
            return true;
        }
        if((bottom.getMaxX() >= 672 && bottom.getMinX() <= 673) && (bottom.getMinY()+2 >= 502 && bottom.getMinY()+2 <= 509)){
            return true;
        }
        if((bottom.getMaxX() >= 674 && bottom.getMinX() <= 715) && (bottom.getMinY()+2 >= 486 && bottom.getMinY()+2 <= 494)){
            return true;
        }
        if((bottom.getMaxX() >= 716 && bottom.getMinX() <= 717) && (bottom.getMinY()+2 >= 502 && bottom.getMinY()+2 <= 509)){
            return true;
        }
        if((bottom.getMaxX() >= 416 && bottom.getMinX() <= 417) && (bottom.getMinY()+2 >= 502 && bottom.getMinY()+2 <= 509)){
            return true;
        }
        if((bottom.getMaxX() >= 418 && bottom.getMinX() <= 555) && (bottom.getMinY()+2 >= 486 && bottom.getMinY()+2 <= 492)){
            return true;
        }
        if((bottom.getMaxX() >= 556 && bottom.getMinX() <= 557) && (bottom.getMinY()+2 >= 502 && bottom.getMinY()+2 <= 509)){
            return true;
        }
        if((bottom.getMaxX() >= 448 && bottom.getMinX() <= 449) && (bottom.getMinY()+2 >= 278 && bottom.getMinY()+2 <= 285)){
            return true;
        }
        if((bottom.getMaxX() >= 450 && bottom.getMinX() <= 555) && (bottom.getMinY()+2 >= 262 && bottom.getMinY()+2 <= 269)){
            return true;
        }
        if((bottom.getMaxX() >= 556 && bottom.getMinX() <= 557) && (bottom.getMinY()+2 >= 278 && bottom.getMinY()+2 <= 285)){
            return true;
        }
        if((bottom.getMaxX() >= 320 && bottom.getMinX() <= 321) && (bottom.getMinY()+2 >= 214 && bottom.getMinY()+2 <= 221)){
            return true;
        }
        if((bottom.getMaxX() >= 322 && bottom.getMinX() <= 395) && (bottom.getMinY()+2 >= 198 && bottom.getMinY()+2 <= 206)){
            return true;
        }
        if((bottom.getMaxX() >= 396 && bottom.getMinX() <= 397) && (bottom.getMinY()+2 >= 214 && bottom.getMinY()+2 <= 221)){
            return true;
        }
        if((bottom.getMaxX() >= 160 && bottom.getMinX() <= 161) && (bottom.getMinY()+2 >= 214 && bottom.getMinY()+2 <= 220)){
            return true;
        }
        if((bottom.getMaxX() >= 162 && bottom.getMinX() <= 267) && (bottom.getMinY()+2 >= 198 && bottom.getMinY()+2 <= 207)){
            return true;
        }
        if((bottom.getMaxX() >= 268 && bottom.getMinX() <= 269) && (bottom.getMinY()+2 >= 214 && bottom.getMinY()+2 <= 220)){
            return true;
        }
        return false;
    }
    public boolean leftColl(){
        //Each if statement checks for a wall segment
        if((bottom.getMinY() >= 151 && bottom.getMinY() <= 154) && (bottom.getMinX()-2 >= 168 && bottom.getMinX()-2 <= 178)){
            return true;
        }
        if((bottom.getMinY() >= 151 && bottom.getMinY() <= 154) && (bottom.getMinX()-2 >= 392 && bottom.getMinX()-2 <= 402)){
            return true;
        }
        if((bottom.getMinY() >= 471 && bottom.getMinY() <= 474) && (bottom.getMinX()-2 >= 680 && bottom.getMinX()-2 <= 690)){
            return true;
        }
        if((bottom.getMinY() >= 631 && bottom.getMinY() <= 696) && (bottom.getMinX()-2 >= 388 && bottom.getMinX()-2 <= 398)){
            return true;
        }
        if((bottom.getMinY() >= 615 && bottom.getMinY() <= 630) && (bottom.getMinX()-2 >= 386 && bottom.getMinX()-2 <= 396)){
            return true;
        }
        if((bottom.getMinY() >= 471 && bottom.getMinY() <= 696) && (bottom.getMinX()-2 >= 132 && bottom.getMinX()-2 <= 142)){
            return true;
        }
        if((bottom.getMinY() >= 343 && bottom.getMinY() <= 534) && (bottom.getMinX()-2 >= 388 && bottom.getMinX()-2 <= 398)){
            return true;
        }
        if((bottom.getMinY() >= 327 && bottom.getMinY() <= 342) && (bottom.getMinX()-2 >= 331 && bottom.getMinX()-2 <= 341)){
            return true;
        }
        if((bottom.getMinY() >= 151 && bottom.getMinY() <= 376) && (bottom.getMinX()-2 >= 132 && bottom.getMinX()-2 <= 142)){
            return true;
        }
        if((bottom.getMinY() >= 327 && bottom.getMinY() <= 342) && (bottom.getMinX()-2 >= 386 && bottom.getMinX()-2 <= 396)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 215) && (bottom.getMinX()-2 >= 262 && bottom.getMinX()-2 <= 268)){
            return true;
        }
        if((bottom.getMinY() >= 215 && bottom.getMinY() <= 283) && (bottom.getMinX()-2 >= 270 && bottom.getMinX()-2 <= 276)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 214) && (bottom.getMinX()-2 >= 389 && bottom.getMinX()-2 <= 396)){
            return true;
        }
        if((bottom.getMinY() >= 284 && bottom.getMinY() <= 347) && (bottom.getMinX()-2 >= 230 && bottom.getMinX()-2 <= 238)){
            return true;
        }
        if((bottom.getMinY() >= 215 && bottom.getMinY() <= 251) && (bottom.getMinX()-2 >= 391 && bottom.getMinX()-2 <= 398)){
            return true;
        }
        if((bottom.getMinY() >= 252 && bottom.getMinY() <= 283) && (bottom.getMinX()-2 >= 359 && bottom.getMinX()-2 <= 366)){
            return true;
        }
        if((bottom.getMinY() >= 248 && bottom.getMinY() <= 278) && (bottom.getMinX()-2 >= 549 && bottom.getMinX()-2 <= 556)){
            return true;
        }
        if((bottom.getMinY() >= 279 && bottom.getMinY() <= 406) && (bottom.getMinX()-2 >= 550 && bottom.getMinX()-2 <= 558)){
            return true;
        }
        if((bottom.getMinY() >= 407 && bottom.getMinY() <= 438) && (bottom.getMinX()-2 >= 520 && bottom.getMinX()-2 <= 526)){
            return true;
        }
        if((bottom.getMinY() >= 615 && bottom.getMinY() <= 630) && (bottom.getMinX()-2 >= 647 && bottom.getMinX()-2 <= 652)){
            return true;
        }
        if((bottom.getMinY() >= 631 && bottom.getMinY() <= 666) && (bottom.getMinX()-2 >= 648 && bottom.getMinX()-2 <= 654)){
            return true;
        }
        if((bottom.getMinY() >= 471 && bottom.getMinY() <= 502) && (bottom.getMinX()-2 >= 710 && bottom.getMinX()-2 <= 716)){
            return true;
        }
        if((bottom.getMinY() >= 503 && bottom.getMinY() <= 615) && (bottom.getMinX()-2 >= 711 && bottom.getMinX()-2 <= 718)){
            return true;
        }
        if((bottom.getMinY() >= 487 && bottom.getMinY() <= 502) && (bottom.getMinX()-2 >= 549 && bottom.getMinX()-2 <= 556)){
            return true;
        }
        if((bottom.getMinY() >= 503 && bottom.getMinY() <= 566) && (bottom.getMinX()-2 >= 550 && bottom.getMinX()-2 <= 558)){
            return true;
        }
        if((bottom.getMinY() >= 519 && bottom.getMinY() <= 534) && (bottom.getMinX()-2 >= 228 && bottom.getMinX()-2 <= 236)){
            return true;
        }
        if((bottom.getMinY() >= 535 && bottom.getMinY() <= 662) && (bottom.getMinX()-2 >= 229 && bottom.getMinX()-2 <= 238)){
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
        //Show walls at start
        if(showWalls){
            timeForWalls += delta;
            if(timeForWalls >= 1000){
                timeForWalls = 0;
                showWalls = false;
            }
        }

        mX = in.getMouseX(); mY = in.getMouseY();
        keys.update(delta);
        //Settings
        if((mX >= 830 && mX <= 890) && (mY >= 730 && mY <= 790) && !win){
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
        if(!openSettings && !win && !showWalls && !lost){
            movement(delta);
        }

        //Checks if keys have been collected
        if(key1Box.contains(top) || key1Box.intersects(top)){

            if(MainS.soundOn){level1.walking.stop(); level1.keyUnlock.play(1f, 0.8f);}

            key1 = false;
            key1Box = new Rectangle(0, 0, 0, 0);
        }
        if(key2Box.contains(top) || key2Box.intersects(top)){

            if(MainS.soundOn){level1.walking.stop(); level1.keyUnlock.play(1f, 0.8f);}

            key2 = false;
            key2Box = new Rectangle(0, 0, 0, 0);
        }
        if(key3Box.contains(top) || key3Box.intersects(top)){

            if(MainS.soundOn){level1.walking.stop(); level1.keyUnlock.play(1f, 0.8f);}

            key3 = false;
            key3Box = new Rectangle(0, 0, 0, 0);
        }
        //Finsishing level, check that all keys have been collected
        if((top.getMaxX() >= 247 && top.getMinX() <= 310) && (top.getMinY()-2 >= 141 && top.getMinY()-2 <= 151) && (!key1 && !key2 && !key3)){
            win = true; checkLock = true;

            if(MainS.soundOn && !level1.levelcomplete.playing()){level1.walking.stop(); level1.levelcomplete.play(1f, 0.8f);}

            moveU = false; moveR = false; moveD = false; moveL = false;
            standU = true; standR = false; standD = false; standL = false;
            time += delta;

            if(time >= 1700){
                time = 0;
                //Write to file new level
                nextLevel();
                restart(); try { Map.restart();} catch (IOException e) {e.printStackTrace();}
                sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
        }

        //Tracks the amount of time left
        if(!showWalls && !win){
            timeLeft -= delta;

            if(timeLeft <= 0){

                timeLeft = 0; lost = true; time += delta;
                moveU = false; moveR = false; moveD = false; moveL = false;
                standU = false; standR = false; standD = true; standL = false;

                if(MainS.soundOn && !level1.lost.playing()){level1.walking.stop(); level1.lost.play(1f, 0.8f);}

                if(time >= 1400){
                    restart();
                    sbg.enterState(6, new FadeOutTransition(), new FadeInTransition());
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setBackground(new Color(47, 47, 46));

        if(!win && !showWalls && !lost){
            layout.draw(0, 0);
        }else{
            //Show out wall map
            layoutWalls.draw(0, 0);
        }

        //Shows out three locks
        if(!checkLock){
            lock.draw(246, 127, 14, 14);
            lock.draw(271, 127, 14, 14);
            lock.draw(296, 127, 14, 14);
        }
        //Shows out keys
        if(key1){
            keys.draw(249, 336);
        }
        if(key2){
            keys.draw(567, 252);
        }
        if(key3){
            keys.draw(729, 486);
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
        
        //Shows time left
        //Find time sounds
        int TIME = timeLeft/1000;
        g.setColor(Color.white);

        if(TIME > 10 && TIME <= 20){g.setColor(Color.yellow);}
        else if(TIME > 5 && TIME <= 10){g.setColor(Color.orange);}
        else if(TIME <= 5){g.setColor(Color.red);}
        g.drawString("Time Left: " + TIME + "s", 390, 750);

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
    
    public int getID() {
        return 6;
    }
    
}