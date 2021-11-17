package lamph11.web.centrerapi.resources;

import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.resources.dto.role.RoleDTO;
import lamph11.web.centrerapi.resources.dto.role.RoleFilter;
import lamph11.web.centrerapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleResource {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> query(@ModelAttribute RoleFilter filter) {
        return ResponseEntity.ok(roleService.query(filter));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO role) throws LphException {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @PutMapping("/{roleName}")
    public ResponseEntity<RoleDTO> update(
            @PathVariable String roleName, @Valid @RequestBody RoleDTO role) throws LphException {
        role.setRole(roleName);
        return ResponseEntity.ok(roleService.updateRole(role));
    }

    @DeleteMapping("/{roleName}")
    public ResponseEntity<RoleDTO> delete(@PathVariable String roleName) throws ResourceNotFoundException {
        return ResponseEntity.ok(roleService.delete(roleName));
    }

}
