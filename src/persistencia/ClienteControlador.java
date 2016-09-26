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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import modelos.Cliente;
import modelos.Endereco;
import modelos.enums.Estado;
import modelos.enums.Sexo;

/**
 *
 * @author Jonathan
 */
public class ClienteControlador extends FileControlador{

    @Override
    public String urlBaseDados() {
        return super.urlBaseDados() + "cliente.txt";
    }
    
    public void gravarLista(List<Cliente> lista) {
        try {
            FileWriter fw = new FileWriter(urlBaseDados());
            BufferedWriter bw = new BufferedWriter(fw);
            for (Cliente cliente : lista) {
                String entrada = cliente.getCodigo() + "``" +
                        cliente.getNome() + "``" + 
                        cliente.getCpf()+ "``" + 
                        cliente.getDataNascimento() + "``" +
                        cliente.getEmail() + "``" +
                        cliente.getSexo() + "``" +
                        cliente.getEndereco().getRua() + "``" +
                        cliente.getEndereco().getNumero() + "``" +
                        cliente.getEndereco().getCep()+ "``" +
                        cliente.getEndereco().getCidade()+ "``" +
                        cliente.getEndereco().getEstado()+ ";;\r\n";
                bw.write(entrada);
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível abrir o arquivo '" + urlBaseDados() + "'");
        } catch (IOException e) {
            System.out.println("Erro durante a leitura de '" + urlBaseDados() + "'");
        }
    }
    
    public List<Cliente> recuperarLista() {
        List<Cliente> lista = new ArrayList<>();
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
                    Cliente cliente = new Cliente();
                    cliente.setCodigo(Integer.parseInt(atributos[0]));
                    cliente.setNome(atributos[1]);
                    cliente.setCpf(atributos[2]);
                    cliente.setDataNascimento(LocalDate.parse(atributos[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    cliente.setEmail(atributos[4]);
                    cliente.setSexo(Sexo.compara(atributos[5]));

                    Endereco endereco = new Endereco();
                    endereco.setRua(atributos[6]);
                    endereco.setNumero(Long.parseLong(atributos[7]));
                    endereco.setCep(atributos[8]);
                    endereco.setCidade(atributos[9]);
                    endereco.setEstado(Estado.compara(atributos[10]));
                    
                    cliente.setEndereco(endereco);
                    lista.add(cliente);
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
