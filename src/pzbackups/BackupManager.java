
package pzbackups;

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
        response.Message = "";
        response.Success = false;
        return response;
    }
    
    public Response Restore(){       
        Response response = new Response();
        response.Message = "";
        response.Success = false;
        return response;
    }
    
}


