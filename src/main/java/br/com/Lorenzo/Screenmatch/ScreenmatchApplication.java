package br.com.Lorenzo.Screenmatch;

import br.com.Lorenzo.Screenmatch.model.DadosSerie;
import br.com.Lorenzo.Screenmatch.service.ConsumoApi;
import br.com.Lorenzo.Screenmatch.service.ConverteDados;
import com.fasterxml.jackson.annotation.JacksonAnnotation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//http://www.omdbapi.com/?i=tt3896198&apikey=29cf665d

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ConsumoApi consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados("http://www.omdbapi.com/?apikey=29cf665d&t=gilmore+girls");
        ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

    }
}
