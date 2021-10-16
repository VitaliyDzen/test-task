import io.swagger.annotations.ApiOperation;
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
@RequestMapping(GROUP)
public class PlaneController {

    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "Get all Plane")
    @GetMapping("/all")
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups();
    }

    @ApiOperation(value = "Get all Plane")
    @GetMapping("/Plane")
    public List<GroupAndroidDTO> getAllGroupNames() {
        return groupService.getAllGroupNames();
    }

    @ApiOperation(value = "Get all Plane by name")
    @GetMapping("/name/{name}")
    public GroupDTO getByName(@PathVariable String name) {
        return groupService.getByName(name);
    }

    //TODO change allDepartmentNames to institute
    @ApiOperation(value = "Get all Plane name")
    @GetMapping("/Plane/{name}")
    public List<GroupAndroidDTO> getAllByInstituteName(@PathVariable String name) {
        return groupService.getAllByInstituteName(name);
    }

    @ApiOperation(value = "Get Plane by id")
    @GetMapping("/{id}")
    public GroupDTO getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @ApiOperation(value = "Update")
    @PutMapping("/{id}")
    public void update(@RequestBody GroupPostDTO group, @PathVariable Long id) {
        groupService.update(id, group);
    }

    @ApiOperation(value = "Save")
    @PostMapping
    public void save(@RequestBody GroupPostDTO group) {
        groupService.save(group);
    }

    @ApiOperation(value = "Delete")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        groupService.remove(id);
    }

}
