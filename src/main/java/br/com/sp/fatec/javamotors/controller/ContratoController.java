package br.com.sp.fatec.javamotors.controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.sp.fatec.javamotors.model.Carro;

public class ContratoController {
	
	public String nomeArquivo(Carro carro) {
        String retorno = IOController.urlContratos() + carro.getAnunciante().getNome() + "_" +
                carro.getVersao().getModelo().getNome() + "-" + carro.getVersao().getModelo().getAno() + "_" +
                carro.getComprador().getNome() + ".html";
        return retorno;
    }
    
    public String gravarContrato(Carro carro) {
        try {
            FileWriter fw = new FileWriter(nomeArquivo(carro));
            BufferedWriter bw = new BufferedWriter(fw);
            String contrato = "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <title>Contrato</title>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <style>\n" +
                "        .main {\n" +
                "            margin: 0 auto;\n" +
                "            width: 800px;\n" +
                "            text-align: center;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"main\">\n" +
                "        <h1>CONTRATO DE VENDA</h1>\n" +
                "        <p><b>VENDEDOR</b>: "+carro.getAnunciante().getNome()+" , inscrito no C.P.F. nº "+carro.getAnunciante().getCpf()+" , residente e domiciliado em "+carro.getAnunciante().getEndereco().getEndereco()+", nº "+carro.getAnunciante().getEndereco().getNumero()+", Cep "+carro.getAnunciante().getEndereco().getCep()+", no Estado "+carro.getAnunciante().getEndereco().getEstado().toString()+", cidade de "+carro.getAnunciante().getEndereco().getCidade()+";</p>\n" +
                "\n" +
                "        <p><b>COMPRADOR</b>: "+carro.getComprador().getNome()+" , inscrito no C.P.F. nº "+carro.getComprador().getCpf()+" , residente e domiciliado em "+carro.getComprador().getEndereco().getEndereco()+", nº "+carro.getComprador().getEndereco().getNumero()+", Cep "+carro.getComprador().getEndereco().getCep()+", no Estado "+carro.getComprador().getEndereco().getEstado().toString()+", cidade de "+carro.getComprador().getEndereco().getCidade()+";</p>\n" +
                "\n" +
                "        <p>As partes acima identificadas têm, entre si, justo e acertado o presente Contrato de Compra e Venda de Automóvel, que se regerá pelas cláusulas seguintes e pelas condições descritas no presente.</p>\n" +
                "\n" +
                "        <h2>DO OBJETO DO CONTRATO</h2>\n" +
                "\n" +
                "        <p><b>Cláusula 1ª.</b> O presente contrato tem como objeto, o veículo automotor "+carro.getVersao().getModelo().getNome()+", marca "+carro.getVersao().getModelo().getMarca().getNome()+", versão "+carro.getVersao().toString()+", ano de fabricação "+carro.getVersao().getModelo().getAno()+", cor "+carro.getCor()+" registrado no DETRAN/DUT em nome de "+carro.getAnunciante().getNome()+".</p>\n" +
                "\n" +
                "        <h2>DAS OBRIGAÇÕES</h2>\n" +
                "\n" +
                "        <p><b>Cláusula 2ª.</b> O VENDEDOR se obriga a entregar ao COMPRADOR o Documento Único de Transferência (DUT), assinado e a este reconhecido firma.</p>\n" +
                "\n" +
                "        <p><b>Cláusula 3ª.</b> O VENDEDOR se responsabilizará pela entrega do automóvel ao COMPRADOR, livre de qualquer ônus ou encargo.</p>\n" +
                "\n" +
                "        <p><b>Cláusula 4ª.</b> O COMPRADOR se responsabilizará, após a assinatura deste instrumento, pelos impostos e taxas que incidirem sobre o automóvel adquirido.</p>\n" +
                "        <h2>CONDIÇÕES GERAIS</h2>\n" +
                "\n" +
                "        <p><b>Cláusula 5ª.</b> Caso o automóvel apresente algum vício redibitório, o VENDEDOR se responsabilizará pelo conserto ou pela devolução dos valores pagos a título de conserto pelo COMPRADOR.</p>\n" +
                "\n" +
                "        <p><b>Cláusula 6ª.</b> O presente contrato passa a valer a partir da assinatura pelas partes, obrigando-se a ele os herdeiros ou sucessores das mesmas.</p>\n" +
                "\n" +
                "        <p>Por estarem assim justos e contratados, firmam o presente instrumento, em duas vias de igual teor.</p>\n" +
                "\n" +
                "        São Paulo, "+LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+".\n" +
                "\n" +
                "        <h2>PARTES</h2>\n" +
                "        <br><br>\n" +
                "        <p>_______________________________________________</p>\n" +
                "        (Assinatura do Comprador)<br><br><br><br>\n" +
                "        <p>_______________________________________________</p>\n" +
                "        (Assinatura do Vendedor)<br><br>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
            bw.write(contrato);
            bw.close();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Deu bosta no contrato");
        }
        return nomeArquivo(carro);
    }
}
