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
@RequestMapping(SUBJECT)
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/all")
    public List<SubjectDTO> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public SubjectDTO getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/name/{name}")
    public SubjectDTO getByName(@PathVariable String name) {
        return subjectService.getByName(name);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody SubjectPostDTO subject, @PathVariable Long id) {
        subjectService.update(id, subject);
    }

    @PostMapping
    public void save(@RequestBody SubjectPostDTO subject) {
        subjectService.save(subject);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        subjectService.remove(id);
    }

}
