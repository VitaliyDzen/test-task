import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyService {

    private final ModelMapper modelMapper;

    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyService(ModelMapper modelMapper,
        SpecialtyRepository specialtyRepository) {
        this.modelMapper = modelMapper;
        this.specialtyRepository = specialtyRepository;
    }

    public SpecialtyDTO getByName(String name) {
        return new SpecialtyDTO(specialtyRepository.findByName(name));
    }

    public List<SpecialtyDTO> getAllSpecialties() {
        return specialtyRepository.findAll().stream().map(SpecialtyDTO::new)
            .collect(Collectors.toList());
    }

    public SpecialtyDTO getSpecialtyById(Long id) {
        return new SpecialtyDTO(specialtyRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Element with id - " + id + " not found")));
    }

    public void remove(Long id) {
        specialtyRepository.deleteById(id);
    }

    public Specialty update(Long id, SpecialtyPostDTO specialty) {
        return specialtyRepository.findById(id)
            .map(employee -> {
                employee.setName(specialty.getName());
                return specialtyRepository.save(employee);
            })
            .orElseThrow(
                () -> new ResourceNotFoundException("Specialty with id - " + id + " not found"));
    }

    public Specialty save(SpecialtyPostDTO specialty) {
        return specialtyRepository.save(modelMapper.map(specialty, Specialty.class));
    }
}
