package br.com.sp.fatec.javamotors.controller;

public class IOController {
	private static final String SRC_PATH = "D:/Development/Programming/Java/eclipse/FATEC_PAOO/";
	
	public static String urlImagens() {
		String imgPath = SRC_PATH + "public/images/";
        return imgPath;
	}
	
	public static String urlContratos() {
        String contratoPath = SRC_PATH + "public/contratos/";
        return contratoPath;
    }
}
