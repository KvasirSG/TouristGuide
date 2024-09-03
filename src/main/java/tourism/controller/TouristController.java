package tourism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/attractions")
public class TouristController {

    private final TouristService touristService;

    @Autowired
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    // GET /attractions - Get all attractions
    @GetMapping
    public ResponseEntity<List<TouristAttraction>> getAllAttractions() {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    // GET /attractions/{name} - Get attraction by name
    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name) {
        Optional<TouristAttraction> attraction = touristService.getAttractionByName(name);
        if (attraction.isPresent()) {
            return new ResponseEntity<>(attraction.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST /attractions/add - Add a new attraction (expects JSON input)
    @PostMapping("/add")
    public ResponseEntity<String> addAttraction(@RequestBody TouristAttraction attraction) {
        touristService.addAttraction(attraction);
        return new ResponseEntity<>("Attraction added successfully", HttpStatus.CREATED);
    }

    // POST /attractions/update - Update an existing attraction (expects JSON input)
    @PostMapping("/update")
    public ResponseEntity<String> updateAttraction(@RequestBody TouristAttraction updatedAttraction) {
        boolean isUpdated = touristService.updateAttraction(updatedAttraction.getName(), updatedAttraction);
        if (isUpdated) {
            return new ResponseEntity<>("Attraction updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Attraction not found", HttpStatus.NOT_FOUND);
        }
    }

    // POST /attractions/delete/{name} - Delete an attraction by name
    @PostMapping("/delete/{name}")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name) {
        boolean isDeleted = touristService.deleteAttraction(name);
        if (isDeleted) {
            return new ResponseEntity<>("Attraction deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Attraction not found", HttpStatus.NOT_FOUND);
        }
    }
}
