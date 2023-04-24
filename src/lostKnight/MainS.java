package lostKnight;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainS extends BasicGameState{
    //ADD LATER: Have an icon to choose the stance in the pre game lobby, others unlocked as higher levels achieved
    public static Animation charac;
    private int mX, mY;
    public static SpriteSheet back;
    private SpriteSheet title, playButton, playButtonClicked;
    private boolean play, settings, openSettings, Info, openInfo;
    private Input in;
    private SpriteSheet info, infoOn, infoPressed, howToPlay;
    private Animation upKey, rightKey, leftKey, downKey;
    private Animation upA, rightA, downA, leftA;
    //Used by all classes, so when volume changes it sticks
    public static Music soundtrack; 
    public static Sound buttonClick;
    public static boolean soundOn, musicOn;
    public static SpriteSheet setting, settingOn, settingPressed, settingTitle, border, music, musicPressed, sound, soundPressed;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        in = gc.getInput();
        soundtrack = new Music("Sound\\soundtrack.ogg"); 
        buttonClick = new Sound("Sound\\buttonClick.ogg");
        play = true; settings = true; openSettings = false; Info = true; openInfo = false; musicOn = true; soundOn = true;
        back = new SpriteSheet("Images\\MainMenu.png", 900, 800);
        border = new SpriteSheet("Images\\border.png", 42, 42);
        charac = new Animation(new SpriteSheet("Images\\KnightDown\\DownStill.png", 48, 48), 300);
        title = new SpriteSheet("Images\\title.png", 663, 155);
        playButton = new SpriteSheet("Images\\playButton.png", 212, 124); playButtonClicked = new SpriteSheet("Images\\playButtonClicked.png", 212, 124);
        setting = new SpriteSheet("Images\\Icons\\setting.png", 16, 16); settingOn = new SpriteSheet("Images\\Icons\\settingTop.png", 16, 16); settingPressed = new SpriteSheet("Images\\Icons\\settingPressed.png", 16, 16);
        info = new SpriteSheet("Images\\Icons\\info.png", 16, 16); infoOn = new SpriteSheet("Images\\Icons\\infoTop.png", 16, 16); infoPressed = new SpriteSheet("Images\\Icons\\infoPressed.png", 16, 16);
        sound = new SpriteSheet("Images\\Icons\\sound.png", 16, 16); soundPressed = new SpriteSheet("Images\\Icons\\soundPressed.png", 16, 16);
        music = new SpriteSheet("Images\\Icons\\music.png", 16, 16); musicPressed = new SpriteSheet("Images\\Icons\\musicPressed.png", 16, 16);
        settingTitle = new SpriteSheet("Images\\settings.png", 316, 120);
        howToPlay = new SpriteSheet("Images\\howToPlay.png", 325, 121);
        upKey = new Animation(new SpriteSheet("Images\\Icons\\ARROWUP.png", 19, 21), 300); rightKey = new Animation(new SpriteSheet("Images\\Icons\\ARROWRIGHT.png", 19, 21), 300);
        downKey = new Animation(new SpriteSheet("Images\\Icons\\ARROWDOWN.png", 19, 21), 300); leftKey = new Animation(new SpriteSheet("Images\\Icons\\ARROWLEFT.png", 19, 21), 300);
        upA = new Animation(new SpriteSheet("Images\\KnightUp\\up.png", 48, 48), 110); rightA = new Animation(new SpriteSheet("Images\\KnightRight\\right.png", 48, 48), 110);
        downA = new Animation(new SpriteSheet("Images\\KnightDown\\down.png", 48, 48), 110); leftA = new Animation(new SpriteSheet("Images\\KnightLeft\\left.png", 48, 48), 110);
    }

    public void buttonCheck(){
        //Music
        if((mX >= 407 && mX <= 467) && (mY >= 350 && mY <= 410) && in.isMousePressed(0)){
            //Turning music off
            if(musicOn){
                if(soundOn){buttonClick.play(1f, 0.5f);}
                musicOn = false;
                soundtrack.setVolume(0f);
            }
            else{//Turning music on
                if(soundOn){buttonClick.play(1f, 0.5f);}
                musicOn = true;
                soundtrack.setVolume(1f);
            }
        }
        //Sound
        if((mX >= 407 && mX <= 467) && (mY >= 430 && mY <= 490) && in.isMousePressed(0)){
            //Turning sound off
            if(soundOn){
                soundOn = false;
            }
            else{//Turning sound on
                soundOn = true;
                buttonClick.play(1f, 0.5f);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException { 

        if(!soundtrack.playing()){soundtrack.loop(1f, 0.5f);}

        charac.update(delta);
        mX = in.getMouseX(); mY = in.getMouseY();

        //Checks for if a button is hovered over and if it is clicked
        //Proccess will be to check MouseX and MouseY is in the area of the play button

        //Play Button
        if((mX >= 360 && mX <= 520) && (mY >= 555 && mY <= 620) && !openSettings && !openInfo) {

            play = false;
            //When mouse is clicked move over to Map screen
            if(in.isMousePressed(0)){
                if(soundOn){buttonClick.play(1f, 0.5f);}
                sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
        }else{play = true;}

        //Settings Icon
        if((mX >= 830 && mX <= 890) && (mY >= 730 && mY <= 790)){

            settings = false;
            if(in.isMousePressed(0)){
                if(soundOn){buttonClick.play(1f, 0.5f);}
                if(openSettings){openSettings = false;}
                else{openSettings = true; openInfo = false;}
            }
        }else{settings = true;}
        
        //Icons in settings
        if(openSettings){buttonCheck();}

        //Info Icon
        if((mX >= 10 && mX <= 70) && (mY >= 730 && mY <= 790)){
            Info = false;

            if(in.isMousePressed(0)){
                if(soundOn){buttonClick.play(1f, 0.5f);}
                if(openInfo){openInfo = false;}
                else{openInfo = true; openSettings = false;}
            }
        }else{Info = true;}
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        back.draw(0, 0, 900, 800);
        charac.draw(265, 200, 350, 350);
        title.draw(100, 80);

        if(play){playButton.draw(332, 525);}
            else{playButtonClicked.draw(332, 525);}

        if(openSettings){
            settingPressed.draw(830, 730, 60, 60);
            border.draw(225, 230, 450, 475);
            SETTINGS();
        }
        else if(settings){setting.draw(830, 730, 60, 60);}
        else{settingOn.draw(830, 730, 60, 60);}

        if(openInfo){
            infoPressed.draw(10, 730, 60, 60);
            border.draw(225, 230, 450, 475);
            INFO();
        }
        else if(Info){info.draw(10, 730, 60, 60);}
        else{infoOn.draw(10, 730, 60, 60);}
    }

    public void INFO(){

        howToPlay.draw(288, 236);
        upKey.draw(370, 335, 57, 66);
        upA.draw(460, 320, 100, 100);
        rightKey.draw(370, 415, 57, 66);
        rightA.draw(460, 395, 100, 100);
        downKey.draw(370, 495, 57, 66);
        downA.draw(460, 480, 100, 100);
        leftKey.draw(370, 575, 57, 66);
        leftA.draw(460, 555, 100, 100);
        
    }

    public void SETTINGS(){
        settingTitle.draw(290, 236);

        if(musicOn){
            music.draw(415, 350, 60, 60);
        }else{
            musicPressed.draw(415, 350, 60, 60);
        }

        if(soundOn){
            sound.draw(415, 430, 60, 60);
            }else{
                soundPressed.draw(415, 430, 60, 60);
        }   

    }

    @Override
    public int getID() {
        return 1;
    }
}