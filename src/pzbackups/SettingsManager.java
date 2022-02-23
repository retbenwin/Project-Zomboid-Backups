
package pzbackups;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.FileWriter;


/**
 *
 * @author retbenwin
 */
public class SettingsManager {
    
    private final static String versionFile = "1.0";
    private String pzSavesPath;    
    private String pzBackupsPath;
    private String[] pzGameModes;
    private static SettingsManager instance;

    /**
     * @return the PZSavesPath
     */
    public String getPZSavesPath() {
        return pzSavesPath;
    }

    /**
     * @param PZSavesPath the PZSavesPath to set
     */
    public void setPZSavesPath(String pzSavesPath) {
        this.pzSavesPath = pzSavesPath;
    }

    /**
     * @return the PZBackupsPath
     */
    public String getPZBackupsPath() {
        return pzBackupsPath;
    }

    /**
     * @param PZBackupsPath the PZBackupsPath to set
     */
    public void setPZBackupsPath(String pzBackupsPath) {
        this.pzBackupsPath = pzBackupsPath;
    }

    /**
     * @return the PZGameModes
     */
    public String[] getPZGameModes() {
        return pzGameModes;
    }
    
    public static String[] getPZDefalultGameModes() {
        return new String[] {"Apocalypse", "Multiplayer", "Sandbox", "Survivor"};
    }

    /**
     * @param PZGameModes the PZGameModes to set
     */
    public void setPZGameModes(String[] pzGameModes) {
        this.pzGameModes = pzGameModes;
    }
    
    private static String getPZBackupsDefalultPath(){
        return System.getProperty("user.home") + "\\Zomboid\\Backups";
    }

    public static String getFilePath(){
        return System.getProperty("user.home") + "\\Zomboid\\Backups\\PZBSettings" + versionFile + ".json";
    }
    
    public SettingsManager() throws Exception{
        //Public constructor for the serializer
        try {
            this.pzSavesPath = System.getProperty("user.home") + "\\Zomboid\\Saves";
            this.pzBackupsPath = getPZBackupsDefalultPath();
            this.pzGameModes = getPZDefalultGameModes();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public static SettingsManager getInstance() throws IOException, Exception{
        if (instance == null)
        {
            String settingsFile = getFilePath();
            Path path = Paths.get(settingsFile);
            if(Files.exists(path)){
                instance = readSettings(settingsFile);
            }else{
                instance = createSettings(settingsFile);
            }
        }
        return instance;
    }
    
    public static SettingsManager createSettings(String settingsFile) throws IOException, Exception{
        //Create new Settings
        String directoryName = getFilePath().substring(0,getFilePath().lastIndexOf("\\"));
        Path filePath = Paths.get(directoryName);
        Path backupsPath = Paths.get(getPZBackupsDefalultPath());
        if(!Files.exists(filePath)){
            Files.createDirectories(filePath);
        }
        if(!Files.exists(backupsPath)){
            Files.createDirectories(backupsPath);
        }
        for (String gameMode: getPZDefalultGameModes())
        {
            Path gameModeBackupPath = Paths.get(getPZBackupsDefalultPath() + "\\" + gameMode);
            if(!Files.exists(gameModeBackupPath)){
                Files.createDirectories(gameModeBackupPath);
            }
        }
        SettingsManager sm = new SettingsManager();
        // pretty print for readability for users
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonSettings = gson.toJson(sm);  
        BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
        writer.write(jsonSettings);
        writer.close();    
        return sm;
    }

    public static SettingsManager readSettings(String settingsFile) throws Exception{
        try
        {
            Path filePath = Paths.get(settingsFile);
            String jsonString = Files.readString(filePath);  
            Gson gson = new Gson();
            SettingsManager sm = gson.fromJson(jsonString, SettingsManager.class);
            return sm;
        }
        catch(Exception ex)
        {
            return createSettings(settingsFile);
        }
    }
    
}
