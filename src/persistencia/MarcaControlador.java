/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelos.Marca;
import sun.awt.FwDispatcher;

/**
 *
 * @author Jonathan
 */
public class MarcaControlador extends FileControlador{

    @Override
    public String urlBaseDados() {
        return super.urlBaseDados() + "marca.txt";
    }
    
    public void gravarLista(List<Marca> lista) {
        try {
            FileWriter fw = new FileWriter(urlBaseDados());
            BufferedWriter bw = new BufferedWriter(fw);
            for (Marca m : lista) {
                String entrada = m.getCodigo() + "``" +
                        m.getNome() + "``" + 
                        m.getPais() + "``" + 
                        m.getLogo() + ";;\r\n";
                bw.write(entrada);
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo '" + urlBaseDados() + "'");
        } catch (IOException e) {
            System.out.println("Erro durante a leitura de '" + urlBaseDados() + "'");
        }
    }
    
    public List<Marca> recuperarLista() {
        List<Marca> lista = new ArrayList<>();
        try {
            FileReader fr = new FileReader(urlBaseDados());
            BufferedReader br = new BufferedReader(fr);
            String saida = "", linha = "";
            while ((linha = br.readLine()) != null) {
                saida += linha;
            }
            br.close();
            
            if (saida.equals("")) {
                lista = new ArrayList<>();
            } else {
                String[] registros = saida.split(";;");
                for (String r : registros) {
                    String[] atributos = r.split("``");
                    Marca m = new Marca();
                    m.setCodigo(Integer.parseInt(atributos[0]));
                    m.setNome(atributos[1]);
                    m.setPais(atributos[2]);
                    m.setLogo(atributos[3]);
                    lista.add(m);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo '" + urlBaseDados() + "'");
        } catch (IOException e) {
            System.out.println("Erro durante a leitura de '" + urlBaseDados() + "'");
        }
        return lista;
    }
}
