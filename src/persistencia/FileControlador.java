package persistencia;

/**
 *
 * @author Jonathan
 */
public class FileControlador {
    
    private static String srcPath = "C:/NewCarro/";
    
    public String urlBaseDados() {
        String dbPath = srcPath + "db/";
        return dbPath;
    }
    
    public static String urlImagens(){
        String imgPath = srcPath + "public/images/";
        return imgPath;
    }
    
    public String urlContratos() {
        String contratoPath = srcPath + "public/contratos/";
        return contratoPath;
    }
}
