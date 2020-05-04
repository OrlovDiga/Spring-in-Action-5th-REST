package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Taco;
import tacos.data.TacoRepo;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


/**
 * @author Orlov Diga
 */
@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {

    private TacoRepo tacoRepo;
    EntityLinks entityLinks;

    @Autowired
    public DesignTacoController(TacoRepo tacoRepo, EntityLinks entityLinks) {
        this.tacoRepo = tacoRepo;
        this.entityLinks = entityLinks;
    }

    @GetMapping("/recent")
    public Resources<TacoResource> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        List<Taco> tacos = tacoRepo.findAll(page).getContent();

        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);

        Resources<TacoResource> recentResources = new Resources<>(tacoResources);

        recentResources.add(linkTo(methodOn(DesignTacoController.class).recentTacos())
                            .withRel("recents"));

        return recentResources;
    }

    @GetMapping("{/id}")
    public ResponseEntity<Taco> findTacoById(@PathVariable("id") Long id) {
        Optional<Taco> optionalTaco = tacoRepo.findById(id);

        if (optionalTaco.isPresent()) {
            return new ResponseEntity<>(optionalTaco.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }



}
