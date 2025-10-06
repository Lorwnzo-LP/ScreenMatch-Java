package br.com.Lorenzo.Screenmatch.principal;

import br.com.Lorenzo.Screenmatch.model.DadosFilme;
import br.com.Lorenzo.Screenmatch.model.DadosSerie;
import br.com.Lorenzo.Screenmatch.model.DadosTemporada;
import br.com.Lorenzo.Screenmatch.service.ConsumoApi;
import br.com.Lorenzo.Screenmatch.service.ConverteDados;

import java.util.*;

public class Principal {
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final Scanner input = new Scanner(System.in);
    private final ConverteDados conversor = new ConverteDados();
    private final String endereco = "http://www.omdbapi.com/?apikey=29cf665d&t=";
    private String entrada;
    private Map<Integer, DadosFilme> filmes = new HashMap<>();
    private Map<Integer, DadosSerie> series = new HashMap<>();
    private List<DadosTemporada> temporadas = new ArrayList<>();

    public void exibeMenu() {
        System.out.println("Bem vindo ao Screen Match!\n");
        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 -> Pesquisar um filme\n2 -> Pesquisar uma série\n3 -> Ver filmes/séries pesquisadas\n4 -> Salvar lista em um arquivo .txt\nSair -> Digite 'sair' para sair :)");

            entrada = input.nextLine();

            switch (entrada) {
                case "1":
                    System.out.println("Digite o filme que quer pesquisar");
                    entrada = input.nextLine();
                    DadosFilme filmeBuscado = buscaFilme(entrada);
                    filmes.put(filmes.size() + 1, filmeBuscado);
                    System.out.println("Filme buscado:");
                    System.out.println(filmeBuscado);
                    System.out.println("Digite qualquer tecla para continuar...");
                    entrada = input.nextLine();
                    break;
                case "2":
                    System.out.println("Digite a série que quer pesquisar");
                    entrada = input.nextLine();
                    DadosSerie serieBuscada = buscaSerie(entrada);
                    series.put(series.size() + 1, serieBuscada);
                    System.out.println("Digite qualquer tecla para continuar...");
                    entrada = input.nextLine();
                    break;
                case "3":
                    exibeSerieFilme();
                    System.out.println("Digite qualquer tecla para continuar...");
                    entrada = input.nextLine();
                    break;
                case "4":
                    System.out.println("Estamos trabalhando nisso");
                default:
                    System.out.println("escolha uma opção válida!");
            }
        } while (!entrada.equalsIgnoreCase("sair"));
    }

    private DadosFilme buscaFilme(String entrada) {
        try {
            var json = consumoApi.obterDados(endereco + entrada.replace(" ", "+"));
            DadosFilme filme = conversor.obterDados(json, DadosFilme.class);
            return filme;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DadosSerie buscaSerie(String entrada) {
        try {
            var json = consumoApi.obterDados(endereco + entrada.replace(" ", "+") + "&Season=1");
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            System.out.println(dados + "\n");
            for (int i = 1; i <= dados.totalTemporadas(); i++) {
                json = consumoApi.obterDados("http://www.omdbapi.com/?apikey=29cf665d&t=" + dados.titulo().replace(" ", "+") + "&season=" + i);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);
            return dados;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void exibeSerieFilme() {
        System.out.println("Escolha entre ver: ");
        System.out.println("\n1 -> filmes \n2 -> séries");
        entrada = input.nextLine();
        switch (entrada) {
            case "1":
                System.out.println("Exibindo lista de filmes");
                for (Map.Entry<Integer, DadosFilme> entry : filmes.entrySet()) {
                    System.out.println("Filme " + entry.getKey() + ": " + entry.getValue());
                }
                break;
            case "2":
                exibirSerie();
                break;
            default:
                System.out.println("escolha uma opção válida!");
        }
    }

    private void exibirSerie() {
        System.out.println("Exibindo lista de séries");
        for (Map.Entry<Integer, DadosSerie> entry : series.entrySet()) {
            System.out.println("Serie " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("\nEscolha uma série para ver as temporadas (digitando o número): ");
        entrada = input.nextLine();

        DadosSerie entry = series.get(Integer.parseInt(entrada));
        System.out.println("\nAgora, escolha qual a temporada que deseja ver da série " + entry.titulo() + ":");
        entrada = input.nextLine();

        var json = consumoApi.obterDados(endereco + entry.titulo().replace(" ", "+") + "&Season=" + entrada);
        DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
        System.out.println(dadosTemporada);
    }
}
