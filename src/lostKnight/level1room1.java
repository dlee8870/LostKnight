package lostKnight;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
//FOR END GAME WHEN STORY IS CLEAR REVEL THE TRUE TITLE OF THE GAME WHICH IS LOST KING
//MAKE ANIMATION FOR IT

public class level1room1 extends BasicGameState{
    private static Sound cardFlip, cardMatch, cardNotMatch, levelcomplete, lost, spikesDown;
    public static Shape top, bottom;
    private static HashMap<Integer, Integer> pairs;
    private static ArrayList<Integer> findSpot;
    private static boolean[] matches;
    private static SpriteSheet[] cards, redCards;
    private static int[] indexes;
    private static SpriteSheet[] cardsWhite, cardsRed; 
    private static SpriteSheet layoutRoom1, fullHeart, emptyHeart, backOfCard;
    private static Shape cardShape;
    private static Animation cardsFlip;
    private static Input in;
    public static int X, Y, lives, timeToShowCard, numOfMatches;
    private static int mX, mY, time, selectedCard, selectedCard2;
    private static boolean settings, openSettings, dead, playGame, showCards, showLossCards, showCardA, Exit, openExit, Restart, openRestart;
    public static boolean moveU, moveR, moveD, moveL;//Tracks which direction to move
    public static boolean standU, standR, standD, standL;//Tracks the last movement before stoping to track which direction to stand still
    

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        mX = 0; mY = 0; numOfMatches = 0; timeToShowCard = 0;
        showLossCards = false; showCardA = true; Exit = true; Restart = true; openRestart = false; openExit = false;
        lives = 3; selectedCard = -1; selectedCard2 = -1; time = 0;
        playGame = false; showCards = true; settings = true;
        X = 273; Y= 200;
        in = gc.getInput(); pairs = new HashMap<>(); findSpot = new ArrayList<>();
        matches = new boolean[16]; cards = new SpriteSheet[16]; redCards = new SpriteSheet[16]; indexes = new int[8]; cardsRed = new SpriteSheet[40]; cardsWhite = new SpriteSheet[40];
        cardShape = new Rectangle(270, 585, 21, 60);
        cardMatch = new Sound("Sound\\cardMatch.ogg");
        cardNotMatch = new Sound("Sound\\cardNotMatch.ogg");
        lost = new Sound("Sound\\lost.ogg");
        levelcomplete = new Sound("Sound\\levelcomplete.ogg");
        spikesDown = new Sound("Sound\\spikesDown.ogg");
        cardFlip = new Sound("Sound\\cardFlip.ogg");
        backOfCard = new SpriteSheet("Images\\level1\\back.png", 60, 84);
        fullHeart = new SpriteSheet("Images\\level1\\fullHeart.png", 130, 140); emptyHeart = new SpriteSheet("Images\\level1\\emptyHeart.png", 130, 140);
        layoutRoom1 = new SpriteSheet("Images\\level1\\level1room1.png", 896, 800);
        cardsFlip = new Animation(new SpriteSheet("Images\\level1\\cardsFlip.png", 200, 320), 90);
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = false; standR = false; standD = true; standL = false;
        //Gets cards in an array
        for(int i = 1; i <= cardsRed.length; i++){
            cardsRed[i-1] = new SpriteSheet("Images\\level1\\"+i+"r.png", 60, 84);
        }
        for(int i = 1; i <= cardsRed.length; i++){
            cardsWhite[i-1] = new SpriteSheet("Images\\level1\\"+i+"w.png", 60, 84);
        }
        //Random cards
        int min = 0, max = 39;
        for(int i = 0; i < 8; i++){
            indexes[i] = (int)(Math.random() * (max - min + 1) + min);
            for(int j = 0; j < i; j++){
                if(indexes[i] == indexes[j]){
                    i--;
                    break;
                }
            }
        }

        //Fill arrayliost with possible spots
        for(int i = 0; i < 16; i++){
            findSpot.add(i);
        }
        //Assigning their spots
        min = 0; max = 15;
        for(int i = 0; i < 8; i++){
            int lastSpot = 0;
            //Twice for each pair
            for(int j = 0; j < 2; j++){
                int spot = (int)(Math.random() * (max - min + 1) + min);
                if(j == 0){lastSpot = findSpot.get(spot)+1;}
                if(j == 1){pairs.put(lastSpot, findSpot.get(spot)+1); pairs.put(findSpot.get(spot)+1, lastSpot);}
                cards[findSpot.get(spot)] = cardsWhite[indexes[i]];
                redCards[findSpot.get(spot)] = cardsRed[indexes[i]];
                findSpot.remove(spot);
                max--;
                
            }
        }
    }
    
    public static void restartRoom1(){
        openSettings = false; openExit = false; openRestart = false;
        Exit = true; Restart = true;
        X = 273; Y= 200; numOfMatches = 0;
        selectedCard = -1; selectedCard2 = -1;
        playGame = false;
        showCards = true; showCardA = true; cardShape = new Rectangle(270, 585, 21, 60);
        lives = 3;
        matches = new boolean[16];
        top = new Rectangle(X+30, Y+60, 20, 1); bottom = new Rectangle(X+30, Y+65, 20, 1);//For collision detection
        moveU = false; moveR = false; moveD = false; moveL = false;
        standU = false; standR = false; standD = true; standL = false;
        //Random cards
        int min = 0, max = 39;
        for(int i = 0; i < 8; i++){
            indexes[i] = (int)(Math.random() * (max - min + 1) + min);
            for(int j = 0; j < i; j++){
                if(indexes[i] == indexes[j]){
                    i--;
                    break;
                }
            }
        }
        //Fill arraylist with possible spots
        findSpot = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            findSpot.add(i);
        }
        //Assigning their spots
        pairs = new HashMap<>();
        min = 0; max = 15;
        for(int i = 0; i < 8; i++){
            int lastSpot = 0;
            //Twice for each pair
            for(int j = 0; j < 2; j++){
                int spot = (int)(Math.random() * (max - min + 1) + min);
                if(j == 0){lastSpot = findSpot.get(spot)+1;}
                if(j == 1){pairs.put(lastSpot, findSpot.get(spot)+1); pairs.put(findSpot.get(spot)+1, lastSpot);}
                cards[findSpot.get(spot)] = cardsWhite[indexes[i]];
                redCards[findSpot.get(spot)] = cardsRed[indexes[i]];
                findSpot.remove(spot);
                max--;
            }
        }
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


    //Collision detection for 4 directions
    public boolean upColl(){
        //Each if statement checks for a wall segment
        if((top.getMaxX() >= 239 && top.getMinX() <= 252) && (top.getMinY()-2 <= 199)){
            return true;
        }
        if((top.getMaxX() >= 252 && top.getMinX() <= 275) && (top.getMinY()-2 <= 203)){
            return true;
        }
        if((top.getMaxX() >= 275 && top.getMinX() <= 292) && (top.getMinY()-2 <= 199)){
            return true;
        }
        if((top.getMaxX() >= 331 && top.getMinX() <= 348) && (top.getMinY()-2 <= 199)){
            return true;
        }
        if((top.getMaxX() >= 348 && top.getMinX() <= 371) && (top.getMinY()-2 <= 203)){
            return true;
        }
        if((top.getMaxX() >= 371 && top.getMinX() <= 448) && (top.getMinY()-2 <= 199)){
            return true;
        }
        if((top.getMaxX() >= 448 && top.getMinX() <= 495) && (top.getMinY()-2 <= 263)){
            return true;
        }
        if((top.getMaxX() >= 495 && top.getMinX() <= 640) && (top.getMinY()-2 <= 199)){
            return true;
        }
        if((top.getMaxX() >= 239 && top.getMinX() <= 334) && (top.getMinY()-2 >= 575 && top.getMinY()-2 <= 583)){
            return true;
        }
        if((top.getMaxX() >= 335 && top.getMinX() <= 480) && (top.getMinY()-2 >= 480 && top.getMinY()-2 <= 487)){
            return true;
        }
        if((top.getMaxX() >= 480 && top.getMinX() <= 508) && (top.getMinY()-2 >= 545 && top.getMinY()-2 <= 551)){
            return true;
        }
        if((top.getMaxX() >= 509 && top.getMinX() <= 530) && (top.getMinY()-2 >= 550 && top.getMinY()-2 <= 555)){
            return true;
        }
        if((top.getMaxX() >= 531 && top.getMinX() <= 558) && (top.getMinY()-2 >= 545 && top.getMinY()-2 <= 551)){
            return true;
        }
        return false;
    }
    public boolean rightColl(){
        if((bottom.getMinY() >= 172 && bottom.getMinY() <= 198) && (bottom.getMaxX()+2 >= 330 && bottom.getMaxX()+2 <= 340)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 203) && (bottom.getMaxX()+2 >= 348 && bottom.getMaxX()+2 <= 355)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 263) && (bottom.getMaxX()+2 >= 448 && bottom.getMaxX()+2 <= 455)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 648) && (bottom.getMaxX()+2 >= 640)){
            return true;
        }
        if((bottom.getMinY() >= 487 && bottom.getMinY() <= 551) && (bottom.getMaxX()+2 >= 480 && bottom.getMaxX()+2 <= 487)){
            return true;
        }
        if((bottom.getMinY() >= 551 && bottom.getMinY() <= 554) && (bottom.getMaxX()+2 >= 508 && bottom.getMaxX()+2 <= 515)){
            return true;
        }
        if((bottom.getMinY() >= 343 && bottom.getMinY() <= 358) && (bottom.getMaxX()+2 >= 482 && bottom.getMaxX()+2 <= 500)){
            return true;
        }
        if((bottom.getMinY() >= 359 && bottom.getMinY() <= 392) && (bottom.getMaxX()+2 >= 480 && bottom.getMaxX()+2 <= 488)){
            return true;
        }
        if((bottom.getMinY() >= 343 && bottom.getMinY() <= 358) && (bottom.getMaxX()+2 >= 290 && bottom.getMaxX()+2 <= 298)){
            return true;
        }
        if((bottom.getMinY() >= 358 && bottom.getMinY() <= 488) && (bottom.getMaxX()+2 >= 288 && bottom.getMaxX()+2 <= 295)){
            return true;
        }
        return false;
    }
    public boolean downColl(){
        if((bottom.getMaxX() >= 239 && bottom.getMinX() <= 640) && (bottom.getMinY()+2 >= 648)){
            return true;
        }
        if((bottom.getMaxX() >= 483 && bottom.getMinX() <= 556) && (bottom.getMinY()+2 >= 343 && bottom.getMinY()+2 <= 350)){
            return true;
        }
        if((bottom.getMaxX() >= 367 && bottom.getMinX() <= 480) && (bottom.getMinY()+2 >= 391 && bottom.getMinY()+2 <= 400)){
            return true;
        }
        if((bottom.getMaxX() >= 291 && bottom.getMinX() <= 364) && (bottom.getMinY()+2 >= 342 && bottom.getMinY()+2 <= 350)){
            return true;
        }
        if((bottom.getMaxX() >= 239 && bottom.getMinX() <= 288) && (bottom.getMinY()+2 >= 488 && bottom.getMinY()+2 <= 495)){
            return true;
        }
        return false;
    }
    public boolean leftColl(){
        //Each if statement checks for a wall segment
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 203) && (bottom.getMinX()-2 >= 252 && bottom.getMinX()-2 <= 275)){
            return true;
        }
        if((bottom.getMinY() >= 172 && bottom.getMinY() <= 198) && (bottom.getMinX()-2 <= 293)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 203) && (bottom.getMinX()-2 >= 365 && bottom.getMinX()-2 <= 371)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 263) && (bottom.getMinX()-2 >= 488 && bottom.getMinX()-2 <= 495)){
            return true;
        }
        if((bottom.getMinY() >= 583 && bottom.getMinY() <= 648) && (bottom.getMinX()-2 >= 230 && bottom.getMinX()-2 <= 239)){
            return true;
        }
        if((bottom.getMinY() >= 487 && bottom.getMinY() <= 582) && (bottom.getMinX()-2 >= 330 && bottom.getMinX()-2 <= 335)){
            return true;
        }
        if((bottom.getMinY() >= 551 && bottom.getMinY() <= 554) && (bottom.getMinX()-2 >= 525 && bottom.getMinX()-2 <= 531)){
            return true;
        }
        if((bottom.getMinY() >= 359 && bottom.getMinY() <= 550) && (bottom.getMinX()-2 >= 551 && bottom.getMinX()-2 <= 560)){
            return true;
        }
        if((bottom.getMinY() >= 343 && bottom.getMinY() <= 358) && (bottom.getMinX()-2 >= 551 && bottom.getMinX()-2 <= 558)){
            return true;
        }
        if((bottom.getMinY() >= 358 && bottom.getMinY() <= 392) && (bottom.getMinX()-2 >= 360 && bottom.getMinX()-2 <= 367)){
            return true;
        }
        if((bottom.getMinY() >= 343 && bottom.getMinY() <= 358) && (bottom.getMinX()-2 >= 355 && bottom.getMinX()-2 <= 365)){
            return true;
        }
        if((bottom.getMinY() >= 199 && bottom.getMinY() <= 488) && (bottom.getMinX()-2 >= 230 && bottom.getMinX()-2 <= 239)){
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

    public void game(int delta, StateBasedGame sbg){
        //Check for selecting a card
        //Keep track of lives
        //Check for loss/win
        
        //Checking each 16 cards
        int y = 0, counter = 0, x = 0;
        for(int i = 1; i <= 16; i++){
            if(counter == 4){//new row
                x = 0;
                y += 100;
                counter = 0;
            }
            //Checks if mouse is on a card that has not been found and that 2 cards are not currently selected
            if((mX >= x+260 && mX <= x+320) && (mY >= y+210 && mY <= y+294) && selectedCard2 == -1 && !matches[i-1] && in.isMousePressed(0) && selectedCard != i){
                if(MainS.soundOn){cardFlip.play(1f, 0.6f);}
                if(selectedCard == -1){
                    selectedCard = i;
                }else{
                    selectedCard2 = i;
                }
            }
            x += 100;
            counter++;
        }
        //Checks if two selected cards match, Check if selectedcard maps to the other selected card
        if(selectedCard2 != -1 && (pairs.get(selectedCard) == selectedCard2)){
            //Play match sound effect
            if(MainS.soundOn){cardMatch.play(1f, 0.8f);}
            numOfMatches++;
            matches[selectedCard-1] = true; matches[selectedCard2-1] = true;
            selectedCard = -1; selectedCard2 = -1;
        }
        //Checks if two selected don't amtch
        if(selectedCard2 != -1 && (pairs.get(selectedCard) != selectedCard2)){
            //Play not match sound effect
            timeToShowCard += delta;
            if(timeToShowCard >= 1000){
                if(MainS.soundOn){cardNotMatch.play(1f, 0.8f);}
            selectedCard = -1; selectedCard2 = -1;
            timeToShowCard = 0;
            lives--;
            }
        }

        //If lost
        if(lives == 0){
            //Play losing sound
            showLossCards = true;
            timeToShowCard += delta;
            if(timeToShowCard >= 1200){
                if(MainS.soundOn){lost.play(1f, 0.8f);}
                timeToShowCard = 0;
                showLossCards = false;
                level1.restart(); level1room1.restartRoom1(); level1room2.restartRoom2();
                sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
            }
        }

        //If win
        if(numOfMatches == 8){
            //Play winning sound
            timeToShowCard += delta;
            if(timeToShowCard >= 1200){
                if(MainS.soundOn){levelcomplete.play(1f, 0.8f);}
                timeToShowCard = 0;
                cardShape = new Rectangle(0, 0, 0, 0);
                playGame = false;
                showCardA = false;
                //Play spike sound going down
                if(MainS.soundOn){spikesDown.play(1f, 0.7f);}
                level1.spike2 = false;
            }
        }


    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        mX = in.getMouseX(); mY = in.getMouseY();  
        //Counting for 1-2 seconds to show cards at start of game
        if(playGame && showCards){
            if(level1.walking.playing()){level1.walking.stop();}
            moveU = false; moveR = false; moveD = false; moveL = false;
            standU = false; standR = true; standD = false; standL = false;
            time += delta;
            if(time >= 3000){
                time = 0;
                showCards = false;
            }
        }

        if(playGame && !showCards && !openSettings){
            game(delta, sbg);
        }
        if(!dead){
            cardsFlip.update(delta);
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
            if(openSettings){try {buttonCheck(sbg);} catch(IOException e) {e.printStackTrace();}}  
    
            if(!openSettings && !playGame){
            movement(delta);
            }
            //Entering back to mainRoom
            if((top.getMaxX() >= 293 && top.getMinX() <= 330) && (top.getMinY()-2 >= 190 && top.getMinY()-2 <= 198)){
               level1.X = 185; level1.Y = 160;
               if(level1.walking.playing()){level1.walking.stop();}
               level1.moveU = false; level1.standU = false; level1.standD = true;
               level1.top = new Rectangle(185+30, 160+60, 20, 1); level1.bottom = new Rectangle(185+30, 160+65, 20, 1);
               sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
            }
            //Checking if card animation has been run over
            if(cardShape.contains(top) || cardShape.intersects(top) && !playGame){
                //Bring up mini game
                playGame = true;
            }
        }else{
                time += delta;
                if(time >= 1000){
                restartRoom1(); level1.restart(); level1room2.restartRoom2();
                sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
                }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        //Map
        //Must set a background
        g.setBackground(new Color(47, 47, 46));
        layoutRoom1.draw(0, 0);

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

       //Card Flipping
       if(showCardA && !playGame){
        cardsFlip.draw(265, 590, 30, 50);
       }

       //Bring up game
       if(playGame){match(g);}

       //Settings Icon
       if(openSettings){
        MainS.settingPressed.draw(830, 730, 60, 60);
        MainS.border.draw(225, 155, 450, 475);
        SETTINGS();
    }
    else if(settings){MainS.setting.draw(830, 730, 60, 60);}
    else{MainS.settingOn.draw(830, 730, 60, 60);}
    }

    public void match(Graphics g){
        //Border
        g.setColor(Color.black);
        g.fillRect(231, 159, 420, 500);
        int x = 0;
        //Hearts
        for(int i = 1; i <= 3; i++){
            if(i <= lives){
                fullHeart.draw(375+x, 618, 45, 45);
            }else{
                emptyHeart.draw(375+x, 618, 45, 45);
            }
            x+=40;
        }
        //Cards
        int y = 0, counter = 0; x = 0;
        for(int i = 1; i <= 16; i++){
            if(counter == 4){//new row
                x = 0;
                y += 100;
                counter = 0;
            }
            if(showCards || showLossCards){
                cards[i-1].draw(260+x, 210+y, 60, 84);
            }else{
                //Show cards in red when they are correct
                if(matches[i-1]){
                    redCards[i-1].draw(260+x, 210+y, 60, 84);
                }else if(!matches[i-1] && (i == selectedCard || i == selectedCard2)){
                    cards[i-1].draw(260+x, 210+y, 60, 84);
                }else{
                    backOfCard.draw(260+x, 210+y, 60, 84);
                }
            }
            x += 100;
            counter++;
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

    @Override
    public int getID() {
        return 4;
    }
    
}