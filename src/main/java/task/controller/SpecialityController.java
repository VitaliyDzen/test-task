import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiResponses(value = {
    @ApiResponse(code = 200, message = HttpStatuses.OK),
    @ApiResponse(code = 201, message = HttpStatuses.CREATED),
    @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
    @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
})
@RestController
@RequestMapping(SPECIALTY)
public class SpecialityController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/all")
    public List<SpecialtyDTO> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    @GetMapping("/{id}")
    public SpecialtyDTO getSpecialtyById(@PathVariable Long id) {
        return specialtyService.getSpecialtyById(id);
    }

    @GetMapping("/name/{name}")
    public SpecialtyDTO getByName(@PathVariable String name) {
        return specialtyService.getByName(name);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody SpecialtyPostDTO specialty, @PathVariable Long id) {
        specialtyService.update(id, specialty);
    }

    @PostMapping
    public void save(@RequestBody SpecialtyPostDTO specialty) {
        specialtyService.save(specialty);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        specialtyService.remove(id);
    }

}
