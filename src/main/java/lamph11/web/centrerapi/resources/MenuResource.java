package lamph11.web.centrerapi.resources;

import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.resources.dto.menu.MenuDTO;
import lamph11.web.centrerapi.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuResource {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuDTO> createMenu(@Validated @RequestBody MenuDTO menu) throws ResourceNotFoundException {
        return ResponseEntity.ok(
                menuService.createMenu(menu)
        );
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuDTO> updateMenu(
            @PathVariable String menuId,
            @Validated @RequestBody MenuDTO menu) throws ResourceNotFoundException {
        menu.setId(menuId);
        return ResponseEntity.ok(
                menuService.updateMenu(menu)
        );
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getMenu() {
        return ResponseEntity.ok(
                menuService.getMenuTree()
        );
    }
}
