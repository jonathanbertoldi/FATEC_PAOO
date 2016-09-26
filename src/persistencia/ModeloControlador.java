package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import modelos.Marca;
import modelos.Modelo;

/**
 *
 * @author Jonathan
 */
public class ModeloControlador extends FileControlador{

    @Override
    public String urlBaseDados() {
        return super.urlBaseDados() + "modelo.txt";
    }
    
    public void gravarLista(List<Modelo> lista) {
        try {
            FileWriter fw = new FileWriter(urlBaseDados());
            BufferedWriter bw = new BufferedWriter(fw);
            for (Modelo m : lista) {
                String entrada = Integer.toString(m.getCodigo()) + "``" +
                        m.getNome() + "``" +
                        m.getAno().toString() + "``" +
                        Integer.toString(m.getMarca().getCodigo()) + "``" +
                        m.getMarca().getNome() + "``" +
                        m.getMarca().getPais() + "``" +
                        m.getMarca().getLogo() + ";;\r\n";
                bw.write(entrada);
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo '" + urlBaseDados() + "'");
        } catch (IOException e) {
            System.out.println("Erro durante a leitura de '" + urlBaseDados() + "'");
        }
    }
    
    public List<Modelo> recuperarLista() {
        List<Modelo> lista = new ArrayList<>();
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
                    
                    Modelo modelo = new Modelo();
                    modelo.setCodigo(Integer.parseInt(atributos[0]));
                    modelo.setNome(atributos[1]);
                    modelo.setAno(Long.parseLong(atributos[2]));
                    
                    Marca marca = new Marca();
                    marca.setCodigo(Integer.parseInt(atributos[3]));
                    marca.setNome(atributos[4]);
                    marca.setPais(atributos[5]);
                    marca.setLogo(atributos[6]);
                    
                    modelo.setMarca(marca);
                    lista.add(modelo);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo '" + urlBaseDados() + "'");
        } catch (IOException e) {
            System.out.println("Erro durante a leitura de '" + urlBaseDados() + "'");
        }
        return lista;
    }
    
    public List<Modelo> recuperarLista(Marca filtro) {
        List<Modelo> lista = new ArrayList<>();
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
                    if (Integer.parseInt(atributos[3]) == filtro.getCodigo()) {
                        Modelo modelo = new Modelo();
                        modelo.setCodigo(Integer.parseInt(atributos[0]));
                        modelo.setNome(atributos[1]);
                        modelo.setAno(Long.parseLong(atributos[2]));

                        Marca marca = new Marca();
                        marca.setCodigo(Integer.parseInt(atributos[3]));
                        marca.setNome(atributos[4]);
                        marca.setPais(atributos[5]);
                        marca.setLogo(atributos[6]);

                        modelo.setMarca(marca);
                        lista.add(modelo);
                    }
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
