package lamph11.web.centrerapi.service;

import io.github.jhipster.service.QueryService;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.common.utils.UUIDUtils;
import lamph11.web.centrerapi.entity.Menu;
import lamph11.web.centrerapi.repository.MenuRepository;
import lamph11.web.centrerapi.repository.MenuSpecifications;
import lamph11.web.centrerapi.resources.dto.menu.MenuDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {LphException.class, Exception.class})
public class MenuService extends QueryService {

    private final ModelMapper modelMapper;
    private final MenuRepository menuRepository;

    public Menu toEntity(Object obj) {
        return modelMapper.map(obj, Menu.class);
    }

    public MenuDTO toDTO(Object obj) {
        return modelMapper.map(obj, MenuDTO.class);
    }

    public MenuDTO createMenu(MenuDTO dto) throws ResourceNotFoundException {
        Menu parent = null;
        String path = "DEFAULT";

        if (dto.getParentId() != null) {
            Optional<Menu> optionalParent = menuRepository.findById(dto.getParentId());
            if (!optionalParent.isPresent())
                throw new ResourceNotFoundException(Menu.class, dto.getParentId());
            parent = optionalParent.get();
            path = String.join("/", parent.getPath(), parent.getId());
        }

        Menu menu = toEntity(dto);
        menu.setId(UUIDUtils.generateId());
        menu.setPath(path);
        menuRepository.save(menu);
        return toDTO(menu);
    }

    public MenuDTO updateMenu(MenuDTO dto) throws ResourceNotFoundException {
        Optional<Menu> optionalMenu = menuRepository.findById(dto.getId());
        if (!optionalMenu.isPresent())
            throw new ResourceNotFoundException(Menu.class, dto.getId());

        Menu menu = optionalMenu.get();
        Menu parent = null;
        String oldPath = menu.getPath();
        String newPath = "DEFAULT";
        List<Menu> deepChildren = menuRepository.findAll(MenuSpecifications.findDeepChildren(menu));

        Optional<Menu> optionalParent = menuRepository.findById(dto.getParentId());
        if (optionalParent.isPresent()) {
            parent = optionalParent.get();
            newPath = String.join("/", parent.getPath(), parent.getId());
        }

        modelMapper.map(dto, menu);
        menu.setPath(newPath);
        menuRepository.save(menu);

        for (Menu item : deepChildren) {
            item.setPath(item.getPath().replace(oldPath, newPath));
        }
        menuRepository.saveAll(deepChildren);
        return toDTO(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuDTO> getMenuTree() {
        List<MenuDTO> menus = menuRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
        return buildTree(menus);
    }

    private List<MenuDTO> buildTree(List<MenuDTO> flatList) {
        Collections.sort(
                flatList,
                Comparator.<MenuDTO>comparingInt(el -> el.getPath().length()).reversed()
        );
        LinkedList<MenuDTO> result = new LinkedList<>();
        for (MenuDTO menu : flatList) {
            String[] pathArr = menu.getPath().split("/");
            if (pathArr.length == 1) { // root menu
                result.add(menu);
                continue;
            }
            String parentId = pathArr[pathArr.length - 1];
            MenuDTO parent = flatList.get( flatList.indexOf(new MenuDTO(parentId)) );
            parent.getMenu().add(menu);
        }
        return result;
    }

}
