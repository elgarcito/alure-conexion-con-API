package com.alura.screenmatch.principal;

import com.alura.screenmatch.excepcion.ErrorEnConversionDeDuracionException;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
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

public class PrincipalConBusqueda {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner lectura=new Scanner(System.in);
        List<Titulo> listaTitulos= new ArrayList<>();
        Gson gson  = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (true){
            System.out.println("Escriba el nombre de la pelicula");
            String busqueda= lectura.nextLine();

            if (busqueda.equalsIgnoreCase("salir")){
                break;
            }
//        System.out.println("Escriba su clave");
//        String clave= lectura.nextLine();
//        String direccion="http://www.omdbapi.com/?t="+busqueda+"&apikey="+clave;
            String direccion="http://www.omdbapi.com/?t="+busqueda+"&apikey="+"1a58976d";
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json=response.body();
                //System.out.println(json);
                TituloOmdb miTituloOmdb=gson.fromJson(json, TituloOmdb.class);

                Titulo miTitulo=new Titulo(miTituloOmdb);
                //System.out.println("Titulo ya convertido: "+miTitulo);

//                FileWriter escritura= new FileWriter("Peliculas.txt");
//                escritura.write(miTitulo.toString());
//                escritura.close();
                listaTitulos.add(miTitulo);

            }catch (NumberFormatException e){
                System.out.println("Ocurrio un error "+e.getMessage());
            }catch (IllegalArgumentException e){
                System.out.println("Ocurrio un error en la URI verifique la direccion "+e.getMessage());
            }catch (ErrorEnConversionDeDuracionException e){
                System.out.println("Ocurrio un error inesperado: "+e.getMessage());
            }

        }
        System.out.println(listaTitulos);
        FileWriter escritura= new FileWriter("titulos.json");
        escritura.write(gson.toJson(listaTitulos));
        escritura.close();
        System.out.println("Finalizo la ejecucion del programa");
    }
}


//COMO ARREGLAR LA IDENTACION CRTL+A Y luego CTRL+ALT+I SUPER IMPORTANTE
