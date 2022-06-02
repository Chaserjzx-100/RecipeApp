package RecipeApp.service.recipeController;

import RecipeApp.service.entity.Recipe;
import RecipeApp.service.recipeDAO.RecipeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class RecipeController {
    @Autowired
    private RecipeDAO recipeDAO;

    /**
     *
     * @return Returns a simple message stating user is on the Home page
     */
    @GetMapping("")
    public String main(){return "You have reached the home page.";}

    /**
     *
     * @return
     */
    @GetMapping("/allRecipes")
    public List<Recipe> getRecipes(){
        return this.recipeDAO.findAll();
    }

    /**
     *
     * @param recipeID
     * @return
     */
    @GetMapping("/{recipeID}")
    public ResponseEntity<Recipe> getById(@PathVariable long recipeID){
        Recipe recipe = recipeDAO.findById(recipeID)
                .orElseThrow(()-> new NoSuchElementException("Recipe does not exit"));
        return ResponseEntity.ok(recipe);
    }

    /**
     *
     * @param recipe
     * @return
     */
    @PostMapping("/allRecipes")
    public Recipe addRecipe(@RequestBody Recipe recipe){
        return recipeDAO.save(recipe);
    }

    /**
     *
     * @param recipeID
     * @param newDish
     * @return
     */
    @PutMapping("/allRecipes/{recipeID}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long recipeID, @RequestBody Recipe newDish) {
        Recipe recipe = recipeDAO.findById(recipeID)
                .orElseThrow(() -> new NoSuchElementException("Recipe does not exist"));

        recipe.setName(newDish.getName());
        recipe.setDescription(newDish.getDescription());

        Recipe updatedRecipe = recipeDAO.save(recipe);
        return ResponseEntity.ok(updatedRecipe);
    }

    /**
     *
     * @param recipeID
     * @return
     * @throws NoSuchElementException
     */
    @DeleteMapping("/allRecipes/{recipeID}")
    public ResponseEntity<Map<String, Boolean>> deleteRecipe(@PathVariable long recipeID) throws NoSuchElementException{
        recipeDAO.deleteById(recipeID);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
