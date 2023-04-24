package lostKnight;

import java.io.*;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Map extends BasicGameState{
    private static ArrayList<SpriteSheet> text1, text2, text3, text;
    private Input in;
    private static int mX, mY, level, levelChoice, doorX, counter;
    private static boolean settings, openSettings;
    public static SpriteSheet background, door, lockedDoor, openDoor, oneDone, one, two, twoDone, twoLocked, three, threeDone, threeLocked, textBox;
    private static Animation doorAFull, doorAHalf, torch;
    private static Sound doorSound;
    private static boolean enterLevel, showDialogue;

    public static int checkLevel() throws IOException, FileNotFoundException{

        BufferedReader br = new BufferedReader(new FileReader("Info\\level.txt"));
        level = Integer.parseInt(br.readLine());
        br.close();
        return level;
    }


    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        text1 = new ArrayList<>(); text2 = new ArrayList<>(); text3 = new ArrayList<>(); text = new ArrayList<>();
        text1.add(new SpriteSheet("Images\\text\\text1line1.png", 349, 56)); text1.add(new SpriteSheet("Images\\text\\text1line2.png", 374, 99)); text1.add(new SpriteSheet("Images\\text\\text1line3.png", 624, 23)); text1.add(new SpriteSheet("Images\\text\\text1line4.png", 624, 23));
        text2.add(new SpriteSheet("Images\\text\\text2line1.png", 386, 61)); text2.add(new SpriteSheet("Images\\text\\text2line2.png", 273, 26)); text2.add(new SpriteSheet("Images\\text\\text2line3.png", 497, 61)); text2.add(new SpriteSheet("Images\\text\\text2line4.png", 587, 23)); text2.add(new SpriteSheet("Images\\text\\text2line5.png", 587, 23));
        text3.add(new SpriteSheet("Images\\text\\text3line1.png", 614, 61)); text3.add(new SpriteSheet("Images\\text\\text3line2.png", 27, 10)); text3.add(new SpriteSheet("Images\\text\\text3line3.png", 439, 59)); text3.add(new SpriteSheet("Images\\text\\text3line4.png", 411, 61));
        MainS.soundtrack.fade(500, 0.5f, false);
        try {level = checkLevel();} 
        catch (IOException e1) {e1.printStackTrace();}
        settings = true;
        enterLevel = false; showDialogue = false;
        in = gc.getInput();
        mX = 0; mY = 0; counter = 0;
        textBox = new SpriteSheet("Images\\textBox.png", 269, 94);
        one = new SpriteSheet("Images\\levels\\on1.png", 93, 105); oneDone = new SpriteSheet("Images\\levels\\done1.png", 93, 105);
        two = new SpriteSheet("Images\\levels\\on2.png", 93, 105); twoDone = new SpriteSheet("Images\\levels\\done2.png", 93, 105); twoLocked = new SpriteSheet("Images\\levels\\locked2.png", 93, 105);
        three = new SpriteSheet("Images\\levels\\on3.png", 93, 105); threeDone = new SpriteSheet("Images\\levels\\done3.png", 93, 105); threeLocked = new SpriteSheet("Images\\levels\\locked3.png", 93, 105);
        doorSound = new Sound("Sound\\doors.ogg");
        torch = new Animation(new SpriteSheet("Images\\torch.png", 320, 320), 200);
        background = new SpriteSheet("Images\\wall.png", 480, 270);
        door = new SpriteSheet("Images\\door.png", 96, 64); lockedDoor = new SpriteSheet("Images\\lockedDoor.png", 96, 64); openDoor = new SpriteSheet("Images\\doorComplete.png", 96, 64);
        doorAFull = new Animation(new SpriteSheet("Images\\doorAniFull.png", 96, 64), 175); doorAHalf = new Animation(new SpriteSheet("Images\\doorAniHalf.png", 96, 64), 175);
    }

    public static void restart() throws FileNotFoundException, IOException{
        try {level = checkLevel();} 
        catch (IOException e) {e.printStackTrace();}
        enterLevel = false;
        settings = true;
        levelChoice = 0;
        showDialogue = false;
        counter = 0;
        text = new ArrayList<>();
    }

    public void buttonCheck(){
        //Music
        if((mX >= 407 && mX <= 467) && (mY >= 275 && mY <= 335) && in.isMousePressed(0)){
            //Turning music off
            if(MainS.musicOn){
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                MainS.musicOn = false;
                MainS.soundtrack.setVolume(0f);
            }
            else{//Turning music on
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                MainS.musicOn = true;
                MainS.soundtrack.setVolume(0.5f);
            }
        }
        //Sound
        if((mX >= 407 && mX <= 467) && (mY >= 355 && mY <= 515) && in.isMousePressed(0)){
            //Turning sound off
            if(MainS.soundOn){
                MainS.soundOn = false;
            }
            else{//Turning sound on
                MainS.soundOn = true;
                MainS.buttonClick.play(1f, 0.5f);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        mX = in.getMouseX(); mY = in.getMouseY();
        torch.update(delta);
        //Settings Icon
        if((mX >= 830 && mX <= 890) && (mY >= 10 && mY <= 70)){
            settings = false;
            if(in.isMousePressed(0)){
                if(MainS.soundOn){MainS.buttonClick.play(1f, 0.5f);}
                if(openSettings){openSettings = false;}
                else{openSettings = true;}
            }
        }else{settings = true;}
        
        //Icons in settings
        if(openSettings){buttonCheck();}

        if(!openSettings){
        //Checks for mouse click on doors
        if((mX >= 60 && mX <= 228) && (mY >= 414 && mY <= 606) && level >= 1 && in.isMousePressed(0) && !showDialogue){
            showDialogue = true;
            levelChoice = 1;
            doorX = 0;
            text = new ArrayList<>(text1);
        }

        if((mX >= 360 && mX <= 528) && (mY >= 414 && mY <= 606) && level >= 2  && in.isMousePressed(0) && !showDialogue){
            showDialogue = true;    
            levelChoice = 2;
            doorX = 300;
            text = new ArrayList<>(text2);
        }

        if((mX >= 660 && mX <= 832) && (mY >= 414 && mY <= 606) && level >= 3  && in.isMousePressed(0) && !showDialogue){
            showDialogue = true;
            levelChoice = 3;
            doorX = 600;
            text = new ArrayList<>(text3);
        }

        if(showDialogue){
            level1.rightS.update(delta);
            //Run through the dialogue textboxes, each time they click on the textbox counter increases
            //When the counter is equal to the length of the array do door animation and enter the level
            if(in.isMousePressed(0) && counter < text.size()){
                counter++;
            }
            if(counter == text.size()){
                showDialogue = false;
                doorAFull.update(delta);
                doorAHalf.update(delta);
                enterLevel = true;
            }

        }
    }

        //Entering level
        if((doorAFull.isStopped() || doorAHalf.isStopped()) && enterLevel && levelChoice == 1){

            sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }
        if((doorAFull.isStopped() || doorAHalf.isStopped()) && enterLevel && levelChoice == 2){

            sbg.enterState(6, new FadeOutTransition(), new FadeInTransition());
        }
        if((doorAFull.isStopped() || doorAHalf.isStopped()) && enterLevel && levelChoice == 3){

            sbg.enterState(7, new FadeOutTransition(), new FadeInTransition());
        }
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        background.draw(0, 0, 900, 800);
        torch.draw(248, 446, 80, 120);
        torch.draw(552, 446, 80, 120);

        //Doors
        //Level 1
        if(level > 1){
            openDoor.draw(0, 414, 288, 192);
            oneDone.draw(98, 347);
        }else{
            door.draw(0, 414, 288, 192);
            one.draw(98, 347);
        }
        //Level 2
        if(2 > level){

            lockedDoor.draw(300, 414, 288, 192);
            twoLocked.draw(395, 345);
        }else if(level > 2){

            openDoor.draw(300, 414, 288, 192);
            twoDone.draw(395, 345);
        }else{

            door.draw(300, 414, 288, 192);
            two.draw(395, 345);
        }
        
        //Level 3
        if(3 > level){

            lockedDoor.draw(600, 414, 288, 192);
            threeLocked.draw(696, 343);
        }else if(level > 3){

            openDoor.draw(600, 414, 288, 192);
            threeDone.draw(696, 343);
        }else{

            door.draw(600, 414, 288, 192);
            three.draw(696, 343);
        }

        //Shows out dialogue
        if(showDialogue){
            level1.rightS.draw(70, 615, 170, 170);
            textBox.draw(200, 625, 500, 160);
            text.get(counter).draw(235, 660);
        }


        //Checks for door animation
        if(enterLevel){

            if(level > levelChoice){
                //Find Door Sound
                if(MainS.musicOn){MainS.soundtrack.fade(750, 0.2f, false);}
                if(MainS.soundOn && !doorSound.playing()){doorSound.play(1f, 1f);}
                doorAHalf.draw(doorX, 414, 288, 192);
                doorAHalf.setLooping(false);
            }else{

                if(MainS.musicOn){MainS.soundtrack.fade(750, 0.2f, false);}
                if(MainS.soundOn && !doorSound.playing()){doorSound.play(1f, 1f);}
                doorAFull.draw(doorX, 414, 288, 192);
                doorAFull.setLooping(false);
            }
        }
        
        //Settings Icon
        if(openSettings){

            MainS.settingPressed.draw(830, 10, 60, 60);
            MainS.border.draw(225, 155, 450, 475);
            SETTINGS();
        }
        else if(settings){MainS.setting.draw(830, 10, 60, 60);}
        else{MainS.settingOn.draw(830, 10, 60, 60);}
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

    }

    @Override
    public int getID() {
        return 2;
    }

}