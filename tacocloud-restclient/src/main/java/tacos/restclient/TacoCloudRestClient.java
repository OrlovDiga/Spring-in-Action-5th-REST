package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.Ingredient;
import tacos.Taco;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Orlov Diga
 */
@Service
@Slf4j
public class TacoCloudRestClient {

    private final RestTemplate rest;
    private final Traverson traverson;

    private final String URL_ID = "http://localhost:8080/ingredients/{id}";
    private final String URL = "http://localhost:8080/ingredients";
    private final String INGRDNTS = "ingredients";

    @Autowired
    public TacoCloudRestClient(RestTemplate rest, Traverson traverson) {
        this.rest = rest;
        this.traverson = traverson;
    }

    /*    public Ingredient getIngredientById(String ingredientId) {
        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("id", ingredientId);

        URI url = UriComponentsBuilder.fromHttpUrl(URL_ID)
                .build(uriVariables);

        return rest.getForObject(url, Ingredient.class);
    }*/

 /*   public Ingredient getIngredientById(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity = rest.getForEntity(
                URL_ID,
                Ingredient.class,
                ingredientId);

        log.info("Fetched time: {}", responseEntity.getHeaders().getDate());

        return responseEntity.getBody();
    }*/

    public Ingredient getIngredientById(String ingredientId) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", ingredientId);

        URI url = UriComponentsBuilder.fromHttpUrl(URL_ID)
                .build(uriVariables);

        ResponseEntity<Ingredient> responseEntity = rest.getForEntity(url, Ingredient.class);

        log.info("Fetched time: {}", responseEntity.getHeaders().getDate());

        return responseEntity.getBody();
    }

    public void updateIngredient(Ingredient ingredient) {
        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("id", ingredient.getId());

        URI url = UriComponentsBuilder.fromHttpUrl(URL_ID)
                .build(uriVariables);

        rest.put(url, Ingredient.class);
    }

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete(URL_ID,
                         ingredient.getId());
    }

    /*public URI createIngredient(Ingredient ingredient) {
        return rest.postForLocation("http://localhost:8080/ingredients", ingredient);
    }*/

    public Ingredient createIngredient(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity = rest.postForEntity(URL,
                ingredient,
                Ingredient.class);

        log.info("New resource created at {}", responseEntity.getHeaders().getLocation());

        return responseEntity.getBody();
    }

    public List<Ingredient> getAllIngredients() {
        return rest.exchange(URL,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Ingredient>>() {}).getBody();
    }

    //
    // Traverson with RestTemplate examples
    //

    public Iterable<Ingredient> getAllIngredientsWithTraverson() {
        ParameterizedTypeReference<Resources<Ingredient>> ingredientType =
                new ParameterizedTypeReference<Resources<Ingredient>>() {};

        Resources<Ingredient> ingredientResources = traverson.follow(INGRDNTS)
                .toObject(ingredientType);

        return ingredientResources.getContent();
    }

    public Iterable<Taco> getRecentTacos() {
        ParameterizedTypeReference<Resources<Taco>> tacoType =
                new ParameterizedTypeReference<Resources<Taco>>() {};

        Resources<Taco> tacoRes = traverson
                .follow("tacos", "recents")
                .toObject(tacoType);

        return tacoRes.getContent();
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        String ingredientsUrl = traverson
                .follow(INGRDNTS)
                .asLink()
                .getHref();

        return rest.postForObject(ingredientsUrl, ingredient, Ingredient.class);
    }



}
