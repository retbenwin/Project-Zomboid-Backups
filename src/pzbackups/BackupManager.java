
package pzbackups;

import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import static java.awt.SystemColor.desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import static pzbackups.SettingsManager.getPZDefalultGameModes;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author retbenwin
 */
public class BackupManager {
    
    private SettingsManager settings;
    
    public BackupManager() throws Exception{
        settings = SettingsManager.getInstance();
    }
    
    public Response BackupNow(String save){
        Response response = new Response();
        try{
            if(save.length() == 0){
                response.setMessage("You must select a savegame.");
                return response;
            }
            
            String sourceFolder = settings.getPZSavesPath() + "\\" + settings.getPZGameModes()[settings.getPosGamemodeSelected()] + "\\" + save;
            Path sourceFolderPath = Paths.get(sourceFolder);    
            if(!Files.exists(sourceFolderPath)){
                response.setMessage("savegame selected does not exist on disk.");
                return response;
            }
            
            String gameModeBackup = settings.getPZBackupsPath()+ "\\" + settings.getPZGameModes()[settings.getPosGamemodeSelected()];
            Path gameModeBackupPath = Paths.get(gameModeBackup);
            if(!Files.exists(gameModeBackupPath)){
                Files.createDirectories(gameModeBackupPath);
            }
            
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            
            String resultFileZip = gameModeBackup + "\\" + save + "_backup-" + formatter.format(date) + ".zip";

            ZipManager.zipDirectory(sourceFolder, resultFileZip);
            
            response.setSuccess(true);
            response.setMessage("Successful backup.");
        }catch(Exception ex){
            response.setMessage(ex.getMessage());
        }
        return response;
    }
    
    public Response Restore(Component parent){       
        Response response = new Response();
        try{
            String gameModeBackup = settings.getPZBackupsPath()+ "\\" + settings.getPZGameModes()[settings.getPosGamemodeSelected()];
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(gameModeBackup));
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Restore backup to " + settings.getPZGameModes()[settings.getPosGamemodeSelected()]);
            fileChooser.setApproveButtonText("Restore");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("backup files", "zip");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            
            //disableButton(fileChooser, "FileChooser.homeFolderIcon");
            //disableButton(fileChooser, "FileChooser.upFolderIcon");
            //disableButton(fileChooser, "FileChooser.newFolderIcon");
            
            int result = fileChooser.showOpenDialog(parent);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                
                String savedGameName = ZipManager.getFirstElementZip(selectedFile.getAbsolutePath());
                response.setExtra(savedGameName);
                String savedGamePath = settings.getPZSavesPath()+ "\\" + settings.getPZGameModes()[settings.getPosGamemodeSelected()] + "\\" + savedGameName;
                File currentSaveGame = new File(savedGamePath);
                if(currentSaveGame.exists()){
                    ZipManager.deleteDir(currentSaveGame);
                }
                
                String gameMode = settings.getPZSavesPath()+ "\\" + settings.getPZGameModes()[settings.getPosGamemodeSelected()];
                
                Path gameModePath = Paths.get(gameMode);
                if(!Files.exists(gameModePath)){
                    Files.createDirectories(gameModePath);
                }
                
                ZipManager.unZip(selectedFile.getAbsolutePath(), gameMode);
                
                response.setMessage("Restored: " + selectedFile.getName());
                response.setSuccess(true);
            }
        }catch(Exception ex){
            response.setMessage(ex.getMessage());
        }
        return response;
    }
    
    public String[] getSaves(String gameMode){
        String path = this.settings.getPZSavesPath() + "\\" + gameMode;
        File file = new File(path);
        String[] savesDirectories = file.list((File current, String name) -> new File(current, name).isDirectory());
        return savesDirectories;
    }
    
    public void OpenFolderWithExplorer(String folderPath) throws IOException {
        File file = new File (folderPath);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }
    
    private void disableButton(final Container c, final String iconString) {
        int len = c.getComponentCount();
        for (int i = 0; i < len; i++) {
            Component comp = c.getComponent(i);
            if (comp instanceof JButton) {
                JButton b = (JButton) comp;
                Icon icon = b.getIcon();
                if (icon != null
                        && icon == UIManager.getIcon(iconString)) {
                    b.setEnabled(false);
                }
            } else if (comp instanceof Container) {
                disableButton((Container) comp, iconString);
            }
        }
    }
    
}


