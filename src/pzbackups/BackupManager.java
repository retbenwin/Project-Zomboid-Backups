
package pzbackups;

import java.awt.Desktop;
import static java.awt.SystemColor.desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author retbenwin
 */
public class BackupManager {
    
    private SettingsManager settings;
    
    public BackupManager() throws Exception{
        settings = SettingsManager.getInstance();
    }
    
    public Response BackupNow(){
        Response response = new Response();
        return response;
    }
    
    public Response Restore(){       
        Response response = new Response();
        return response;
    }
    
    public void OpenFolderWithExplorer(String folderPath) throws IOException {
        File file = new File (folderPath);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }
    
}


