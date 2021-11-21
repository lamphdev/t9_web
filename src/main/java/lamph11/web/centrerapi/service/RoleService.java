package lamph11.web.centrerapi.service;

import io.github.jhipster.service.QueryService;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.entity.Role;
import lamph11.web.centrerapi.entity.Role_;
import lamph11.web.centrerapi.repository.ApiRuleMapRoleRepository;
import lamph11.web.centrerapi.repository.RoleRepository;
import lamph11.web.centrerapi.resources.dto.role.RoleDTO;
import lamph11.web.centrerapi.resources.dto.role.RoleFilter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {LphException.class, Exception.class})
public class RoleService extends QueryService<Role> {

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final ApiRuleMapRoleRepository apiRuleMapRoleRepository;

    public RoleDTO toDTO(Object source) {
        return modelMapper.map(source, RoleDTO.class);
    }

    public Role toEntity(Object source) {
        return modelMapper.map(source, Role.class);
    }

    /**
     * query role
     *
     * @param filter query params
     * @return role matched
     */
    public List<RoleDTO> query(RoleFilter filter) {
        Specification<Role> specification = buildSpecification(filter);
        return roleRepository.findAll(specification).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * create new role
     *
     * @param dto request payload
     * @return role created
     * @throws LphException
     */
    public RoleDTO createRole(RoleDTO dto) throws LphException {
        Optional<Role> optional = roleRepository.findById(StringUtils.upperCase(dto.getRole()));
        if (optional.isPresent())
            throw new LphException("Resource existed");
        Role role = toEntity(dto);
        roleRepository.save(role);
        return toDTO(role);
    }


    /**
     * update role
     *
     * @param dto request payload
     * @return role updated
     * @throws LphException
     */
    public RoleDTO updateRole(RoleDTO dto) throws LphException {
        Optional<Role> optional = roleRepository.findById(StringUtils.upperCase(dto.getRole()));
        if (!optional.isPresent())
            throw new ResourceNotFoundException(Role.class, dto.getRole());

        Role role = optional.get();
        modelMapper.map(dto, role);
        roleRepository.save(role);
        return toDTO(role);
    }


    /**
     * delete existed role
     *
     * @param roleName role name to delete
     * @return role deleted
     * @throws ResourceNotFoundException not found role existed
     */
    public RoleDTO delete(String roleName) throws ResourceNotFoundException {
        Optional<Role> optional = roleRepository.findById(StringUtils.upperCase(roleName));
        if (!optional.isPresent())
            throw new ResourceNotFoundException(Role.class, roleName);

        apiRuleMapRoleRepository.deleteAll(
                apiRuleMapRoleRepository.findByRole(StringUtils.upperCase(roleName))
        );

        Role role = optional.get();
        roleRepository.delete(role);
        return toDTO(role);
    }

    private Specification<Role> buildSpecification(RoleFilter filter) {
        Specification specification = Specification.where(null);
        if (null == filter)
            return specification;

        if (filter.getName() != null)
            specification = specification.and((root, query, builder) -> builder.like(
                    root.get(Role_.ROLE), StringUtils.join("%", filter.getName(), "%")
            ));

        if (filter.getKeyword() != null)
            specification = specification.and((root, query, builder) -> builder.or(
                    builder.like(
                            builder.lower(root.get(Role_.ROLE)),
                            StringUtils.join("%", filter.getName().toLowerCase(), "%")
                    ),
                    builder.like(
                            builder.lower(root.get(Role_.DESCRIPTION)),
                            StringUtils.join("%", filter.getName().toLowerCase(), "%")
                    ),
                    builder.like(
                            builder.lower(root.get(Role_.METADATA)),
                            StringUtils.join("%", filter.getName().toLowerCase(), "%")
                    )
            ));

        return specification;
    }

}
