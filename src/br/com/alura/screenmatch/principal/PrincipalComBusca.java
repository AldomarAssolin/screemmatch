package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excesao.ErrorDeCoversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOMDB;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        String busca = "";
        List<Titulo> titles = new ArrayList<>();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting().create();

        while (!busca.equalsIgnoreCase("sair")) {

            System.out.println("Digite um filme para busca: ");
            busca = leitura.nextLine();

            if(busca.equalsIgnoreCase("sair")){
                break;
            }


            String error = "Opss, ocorreu um erro.";


            String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=c8d95696";

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                System.out.println(json);

                TituloOMDB meuTituloOmdb = gson.fromJson(json, TituloOMDB.class);
                System.out.println(meuTituloOmdb);


                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println("Título convertido");
                System.out.println(meuTitulo);

                titles.add(meuTitulo);

            } catch (NullPointerException n) {
                System.out.println(error);
                System.out.println(STR."Erro: \{n.getMessage()}");
            } catch (NumberFormatException e) {
                System.out.println(error);
                System.out.println(STR."Erro: \{e.getMessage()}");
            } catch (IllegalArgumentException il) {
                System.out.println(error);
                System.out.println(STR."Erro: \{il.getMessage()}");
            } catch (ErrorDeCoversaoDeAnoException err) {
                System.out.println(error);
                System.out.println(STR."Erro: \{err.getMessage()}");
            } finally {
                System.out.println("O programa finalizou!");
            }

            System.out.println(titles);
            FileWriter writer = new FileWriter("filmes.json");
            writer.write(gson.toJson(titles));
            writer.close();
        }

    }
}
