package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import modelos.Carro;
import modelos.Cliente;
import modelos.Marca;
import modelos.Modelo;
import modelos.Versao;
import modelos.enums.Cambio;
import modelos.enums.Combustivel;
import modelos.enums.Sexo;
import modelos.enums.Status;

/**
 *
 * @author Jonathan
 */
public class CarroControlador extends FileControlador {

    @Override
    public String urlBaseDados() {
        return super.urlBaseDados() + "carro.txt";
    }
    
    public void gravarLista(List<Carro> lista) {
        try {
            FileWriter fw = new FileWriter(urlBaseDados());
            BufferedWriter bw = new BufferedWriter(fw);
            for (Carro carro: lista) {
                String entrada = Integer.toString(carro.getCodigo()) + "``" +
                        carro.getKm() + "``" +
                        carro.getCor() + "``" +
                        carro.getFotos()[0] + "``" +
                        carro.getFotos()[1] + "``" +
                        carro.getFotos()[2] + "``" +
                        carro.getDataAnuncio() + "``" +
                        carro.getAnunciante().getCodigo() + "``" +
                        carro.getAnunciante().getNome() + "``" + 
                        carro.getAnunciante().getCpf()+ "``" + 
                        carro.getAnunciante().getDataNascimento() + "``" +
                        carro.getAnunciante().getEmail() + "``" +
                        carro.getAnunciante().getSexo() + "``" +
                        carro.getAnunciante().getEndereco().getRua() + "``" +
                        carro.getAnunciante().getEndereco().getNumero() + "``" +
                        carro.getAnunciante().getEndereco().getCep()+ "``" +
                        carro.getAnunciante().getEndereco().getCidade()+ "``" +
                        carro.getAnunciante().getEndereco().getEstado()+ "``" +
                        carro.getVersao().getCilindradas()+ "``" +
                        carro.getVersao().getNomeMotor() + "``" +
                        Integer.toString(carro.getVersao().getValvulas()) + "``" +
                        Integer.toString(carro.getVersao().getPortas()) + "``" +
                        carro.getVersao().getCombustivel() + "``" +
                        carro.getVersao().getCambio() + "``" +
                        carro.getVersao().getModelo().getCodigo() + "``" +
                        carro.getVersao().getModelo().getNome() + "``" +
                        carro.getVersao().getModelo().getAno() + "``" +
                        carro.getVersao().getModelo().getMarca().getCodigo() + "``" +
                        carro.getVersao().getModelo().getMarca().getNome() + "``" +
                        carro.getVersao().getModelo().getMarca().getPais() + "``" +
                        carro.getVersao().getModelo().getMarca().getLogo() + "``";
                if (carro.getComprador() != null) {
                    entrada += carro.getDataVenda() + "``" +
                        carro.getComprador().getCodigo() + "``" +
                        carro.getComprador().getNome() + "``" + 
                        carro.getComprador().getCpf()+ "``" + 
                        carro.getComprador().getDataNascimento() + "``" +
                        carro.getComprador().getEmail() + "``" +
                        carro.getComprador().getSexo() + "``" +
                        carro.getComprador().getEndereco().getRua() + "``" +
                        carro.getComprador().getEndereco().getNumero() + "``" +
                        carro.getComprador().getEndereco().getCep()+ "``" +
                        carro.getComprador().getEndereco().getCidade()+ "``" +
                        carro.getComprador().getEndereco().getEstado()+ "``" +
                        Status.VENDIDO.toString() + ";;\r\n";
                } else {
                    entrada += Status.A_VENDA.toString() + ";;\r\n";
                }
                bw.write(entrada);
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo '" + urlBaseDados() + "'");
        } catch (IOException e) {
            System.out.println("Erro durante a leitura de '" + urlBaseDados() + "'");
        }
    }
    
    public List<Carro> recuperarLista() {
        List<Carro> lista = new ArrayList<>();
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
                    Carro carro = new Carro();
                    carro.setCodigo(Integer.parseInt(atributos[0]));
                    carro.setKm(Float.parseFloat(atributos[1]));
                    carro.setCor(atributos[2]);
                    String[] fotos = {atributos[3], atributos[4], atributos[5]};
                    carro.setFotos(fotos);
                    carro.setDataAnuncio(LocalDate.parse(atributos[6], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    
                    Cliente anunciante = new Cliente();
                    anunciante.setCodigo(Integer.parseInt(atributos[7]));
                    anunciante.setNome(atributos[8]);
                    anunciante.setCpf(atributos[9]);
                    anunciante.setDataNascimento(LocalDate.parse(atributos[10], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    anunciante.setEmail(atributos[11]);
                    anunciante.setSexo(Sexo.compara(atributos[12]));
                    carro.setAnunciante(anunciante);
                    
                    Versao versao = new Versao();
                    versao.setCodigo(Integer.parseInt(atributos[13]));
                    versao.setCilindradas(Float.parseFloat(atributos[14]));
                    versao.setNomeMotor(atributos[15]);
                    versao.setValvulas(Integer.parseInt(atributos[16]));
                    versao.setPortas(Integer.parseInt(atributos[17]));
                    versao.setCombustivel(Combustivel.compara(atributos[18]));
                    versao.setCambio(Cambio.compara(atributos[19]));
                    
                    Modelo modelo = new Modelo();
                    modelo.setCodigo(Integer.parseInt(atributos[20]));
                    modelo.setNome(atributos[21]);
                    modelo.setAno(Long.parseLong(atributos[22]));
                    
                    Marca marca = new Marca();
                    marca.setCodigo(Integer.parseInt(atributos[23]));
                    marca.setNome(atributos[24]);
                    marca.setPais(atributos[25]);
                    marca.setLogo(atributos[26]);
                    
                    modelo.setMarca(marca);
                    versao.setModelo(modelo);
                    
                    carro.setVersao(versao);
                    
                    if (atributos.length > 27) {
                        Cliente comprador = new Cliente();
                        comprador.setCodigo(Integer.parseInt(atributos[27]));
                        comprador.setNome(atributos[28]);
                        comprador.setCpf(atributos[29]);
                        comprador.setDataNascimento(LocalDate.parse(atributos[30], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        comprador.setEmail(atributos[31]);
                        comprador.setSexo(Sexo.compara(atributos[32]));
                        carro.setComprador(comprador);
                        carro.setStatus(Status.VENDIDO);
                    } else {
                        carro.setStatus(Status.A_VENDA);
                    }
                    
                    lista.add(carro);
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
