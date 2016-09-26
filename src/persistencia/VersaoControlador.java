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
import modelos.Modelo;
import modelos.Versao;
import modelos.enums.Cambio;
import modelos.enums.Combustivel;

/**
 *
 * @author Jonathan
 */
public class VersaoControlador extends FileControlador{

    @Override
    public String urlBaseDados() {
        return super.urlBaseDados() + "versao.txt";
    }
    
    public void gravarLista(List<Versao> lista) {
        try {
            FileWriter fw = new FileWriter(urlBaseDados());
            BufferedWriter bw = new BufferedWriter(fw);
            for (Versao versao: lista) {
                String entrada = Integer.toString(versao.getCodigo()) + "``" +
                        versao.getCilindradas()+ "``" +
                        versao.getNomeMotor() + "``" +
                        Integer.toString(versao.getValvulas()) + "``" +
                        Integer.toString(versao.getPortas()) + "``" +
                        versao.getCombustivel() + "``" +
                        versao.getCambio() + "``" +
                        versao.getModelo().getCodigo() + "``" +
                        versao.getModelo().getNome() + "``" +
                        versao.getModelo().getAno() + "``" +
                        versao.getModelo().getMarca().getCodigo() + "``" +
                        versao.getModelo().getMarca().getNome() + "``" +
                        versao.getModelo().getMarca().getPais() + "``" +
                        versao.getModelo().getMarca().getLogo() + ";;\r\n";
                bw.write(entrada);
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo '" + urlBaseDados() + "'");
        } catch (IOException e) {
            System.out.println("Erro durante a leitura de '" + urlBaseDados() + "'");
        }
    }
    
    public List<Versao> recuperarLista() {
        List<Versao> lista = new ArrayList<>();
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
                    
                    Versao versao = new Versao();
                    versao.setCodigo(Integer.parseInt(atributos[0]));
                    versao.setCilindradas(Float.parseFloat(atributos[1]));
                    versao.setNomeMotor(atributos[2]);
                    versao.setValvulas(Integer.parseInt(atributos[3]));
                    versao.setPortas(Integer.parseInt(atributos[4]));
                    versao.setCombustivel(Combustivel.compara(atributos[5]));
                    versao.setCambio(Cambio.compara(atributos[6]));
                    
                    Modelo modelo = new Modelo();
                    modelo.setCodigo(Integer.parseInt(atributos[7]));
                    modelo.setNome(atributos[8]);
                    modelo.setAno(Long.parseLong(atributos[9]));
                    
                    Marca marca = new Marca();
                    marca.setCodigo(Integer.parseInt(atributos[10]));
                    marca.setNome(atributos[11]);
                    marca.setPais(atributos[12]);
                    marca.setLogo(atributos[13]);
                    
                    modelo.setMarca(marca);
                    versao.setModelo(modelo);
                    lista.add(versao);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo '" + urlBaseDados() + "'");
        } catch (IOException e) {
            System.out.println("Erro durante a leitura de '" + urlBaseDados() + "'");
        }
        return lista;
    }
    
    public List<Versao> recuperarLista(Modelo filtro) {
        List<Versao> lista = new ArrayList<>();
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
                    if (Integer.parseInt(atributos[7]) == filtro.getCodigo()) {
                        Versao versao = new Versao();
                        versao.setCodigo(Integer.parseInt(atributos[0]));
                        versao.setCilindradas(Float.parseFloat(atributos[1]));
                        versao.setNomeMotor(atributos[2]);
                        versao.setValvulas(Integer.parseInt(atributos[3]));
                        versao.setPortas(Integer.parseInt(atributos[4]));
                        versao.setCombustivel(Combustivel.compara(atributos[5]));
                        versao.setCambio(Cambio.compara(atributos[6]));

                        Modelo modelo = new Modelo();
                        modelo.setCodigo(Integer.parseInt(atributos[7]));
                        modelo.setNome(atributos[8]);
                        modelo.setAno(Long.parseLong(atributos[9]));

                        Marca marca = new Marca();
                        marca.setCodigo(Integer.parseInt(atributos[10]));
                        marca.setNome(atributos[11]);
                        marca.setPais(atributos[12]);
                        marca.setLogo(atributos[13]);

                        modelo.setMarca(marca);
                        versao.setModelo(modelo);
                        lista.add(versao);
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
