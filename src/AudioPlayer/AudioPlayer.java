package AudioPlayer;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioPlayer {
    public static int MENU = 0;
    public static int GAME = 1;

    public static int ATTACK = 0;
    public static int ATTACK_2 = 1;
    public static int JUMP = 2;
    public static int DOUBLE_JUMP = 3;
    public static int PICK_ITEM = 4;
    public static int GAME_OVER = 5;

    private Clip[] songs, effects;
    private int currentSongID = 0;
    private float song_volumn = 0.5f;
    private float effect_volumn = 0.5f;
    private Boolean songMuted = false , effectMuted = false ;
    private String[] names;
    private Map<String, List<Clip>> effectPool;
    public AudioPlayer() {
        effectPool = new HashMap<>();
        loadEffect();
        loadSong();
        playSong(MENU);
    }

    private void loadSong(){
        String[] names = {"menuBgm","GameMusic"};
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++) {
            songs[i] = getClip(names[i]);
        }
    }
    private void loadEffect(){
        names = new String[]{"attack", "attack2", "jump", "doublejump", "pickupitem", "gameover"};
        effects = new Clip[names.length];
        for (int i = 0; i < effects.length; i++) {
            effects[i] = getClip(names[i]);
            List<Clip> clips = new ArrayList<>();
            for (int j = 0 ; j < 6; j ++){
                clips.add(cloneClip(names[i]));
            }
            effectPool.put(names[i], clips);
        }
        updateEffectVolume();
    }
    private Clip cloneClip(String name) {
        try {
            InputStream is = getClass().getResourceAsStream("/audio/" + name + ".WAV");
            if (is == null) {
                throw new RuntimeException("Audio file not found: /audio/" + name + ".WAV");
            }

            byte[] audioData = is.readAllBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(bais));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException("Error cloning audio clip: " + name, e);
        }
    }
    private Clip getClip(String name){
        try {
            InputStream is = getClass().getResourceAsStream("/audio/" + name + ".WAV");
            if (is == null) {
                throw new RuntimeException("Audio file not found: /audio/" + name + ".WAV");
            }

            byte[] audioData = is.readAllBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(bais));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException("Error loading audio: " + name, e);
        }
    }
    private void updateSongVolume(){
        FloatControl gainControl = (FloatControl) songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * song_volumn) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }
    private void updateEffectVolume(){
        for (Clip c : effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * effect_volumn) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
        for (Map.Entry<String, List<Clip>> e : effectPool.entrySet()) {
            String key = e.getKey();
            List<Clip> clips = e.getValue();
            for (Clip c : clips) {
                FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * effect_volumn) + gainControl.getMinimum();
                gainControl.setValue(gain);
            }
        }
    }
    public void toggleSongMute(){
        this.songMuted = !this.songMuted;
        for (Clip c : songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMuted);
        }
    }
    public void toggleEffectMute(){
        this.effectMuted = !this.effectMuted;
        for (Clip c : effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMuted);
        }
    }
    public void setSongVolume(float volume){
        this.song_volumn = volume;
        updateSongVolume();
    }
    public void setEffectVolume(float volume){
        this.effect_volumn = volume;
        updateEffectVolume();
    }
    public void stopSong(){
        if (songs[currentSongID].isActive()){
            songs[currentSongID].stop();
        }
    }
    public void playPoolEffect(String name) {
        List<Clip> pool = effectPool.get(name);
        for (Clip clip : pool) {
            if (!clip.isRunning()) {
                clip.setMicrosecondPosition(0);
                clip.start();
                break;
            }
        }
    }
    public void playSong(int song){
        stopSong();
        currentSongID = song;
        updateSongVolume();
        songs[currentSongID].setMicrosecondPosition(0);
        songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void playPoolEffect(int effect){
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }
    public String getName(int index){
        return names[index];
    }
}
