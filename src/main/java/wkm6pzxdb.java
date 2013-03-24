import com.atolsystems.atolutilities.AFileChooser;
import com.atolsystems.atolutilities.AFileUtilities;
import com.atolsystems.atolutilities.AMessageBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: seb
 * Date: 12/24/12
 * Time: 6:55 PM
 * To change this template use File | Settings | File Templates.
 */



public class wkm6pzxdb {
    final static String PLAYLIST_PATHKEY = "wkm6pzxdbPlaylistPathKey";
    final static String TARGET_PATHKEY = "wkm6pzxdbTargetPathKey";

    public static void main(final String args[])  {
        StringBuffer sb = new StringBuffer();

        try {
            //java.util.List<javax.swing.filechooser.FileNameExtensionFilter> filters;
            File playlist = AFileChooser.askForFile(null,true,"Choose playlist file",PLAYLIST_PATHKEY,true,false,false);
            File targetFolder = AFileChooser.askForFile(null, true, "Choose target folder", TARGET_PATHKEY, false, true, false);//.askForDirectory("Choose target folder",false);
            System.out.println("playlist: "+ playlist.getCanonicalPath());
            System.out.println("target folder: "+ targetFolder.getCanonicalPath());

            int fileCnt=0;
            int srcNotFoundCnt=0;
            int dstExistCnt=0;


            BufferedReader in = new BufferedReader(new FileReader(playlist));

            try{
                while(true){
                    String line = in.readLine();
                    if(null==line){
                        break;
                    }
                    if(line.isEmpty()) continue;
                    if(line.startsWith("#")) continue;
                    System.out.println(line);
                    File src = new File(line);
                    if(!src.exists()){
                        System.out.println("\t-->file not found, skipped.");
                        srcNotFoundCnt++;
                        continue;
                    }
                    File dst = new File(targetFolder,src.getName());
                    if(dst.exists()){
                        System.out.println("\t-->exist already, skipped.");
                        dstExistCnt++;
                        continue;
                    }
                    AFileUtilities.copyFile(dst,src);
                    fileCnt++;
                }
            }finally {
                in.close();
            }
            if((0==srcNotFoundCnt) && (0==dstExistCnt)){
                if(0==fileCnt)
                    sb.append("No file found in the playlist, please use m3u playlist files.");
                else
                    sb.append("All files copied ("+fileCnt+").");
            }else{
                sb.append("Warning, some problem happened:\n");
                sb.append(fileCnt+" files copied.\n");
                if(0!=srcNotFoundCnt){
                    sb.append(srcNotFoundCnt);
                    sb.append(" files not found.\n");
                }
                if(0!=dstExistCnt){
                    sb.append(dstExistCnt);
                    sb.append(" target files were already existing.\n");
                }
            }

        } catch(Throwable e) {
            sb.append("Error happened, please make sure the playlist is valid and enough disk space is available.");
            throw new IllegalArgumentException(e);
        }finally{
            System.out.println(sb.toString());
            AMessageBox.showModalMessageBox(sb.toString());
        }
    }
}
