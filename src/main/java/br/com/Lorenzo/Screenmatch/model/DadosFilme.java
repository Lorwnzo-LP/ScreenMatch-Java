package br.com.Lorenzo.Screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFilme(@JsonAlias("Title") String titulo,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Type") String Tipo,
                         @JsonAlias("Runtime") String duracao,
                         @JsonAlias("Director") String diretor,
                         @JsonAlias("Writer") String roterista,
                         @JsonAlias("Actores") String atores,
                         @JsonAlias("Year") Integer Year,
                         @JsonAlias("Released") String dataDeLancamento) {
    @Override
    public String toString() {
        return "Titulo: " + titulo + " Avaliacao " + avaliacao + " Diretor: " + diretor + " roteirista: " + roterista;
    }
}
