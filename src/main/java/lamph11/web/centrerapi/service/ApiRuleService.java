package lamph11.web.centrerapi.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import io.github.jhipster.service.QueryService;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.common.utils.UUIDUtils;
import lamph11.web.centrerapi.entity.*;
import lamph11.web.centrerapi.repository.ApiRuleMapRoleRepository;
import lamph11.web.centrerapi.repository.ApiRuleRepository;
import lamph11.web.centrerapi.repository.RoleRepository;
import lamph11.web.centrerapi.resources.dto.api_rule.ApiRuleDTO;
import lamph11.web.centrerapi.resources.dto.api_rule.ApiRuleFilter;
import lamph11.web.centrerapi.resources.dto.api_rule.CreateApiRule;
import lamph11.web.centrerapi.resources.dto.api_rule.UpdateApiRule;
import lombok.RequiredArgsConstructor;
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
public class ApiRuleService extends QueryService<ApiRule> {

    private final ApiRuleMapRoleRepository apiRuleMapRoleRepository;
    private final ApiRuleRepository apiRuleRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ApiRuleDTO> query(ApiRuleFilter filter) {
        return apiRuleRepository.findAll(
                        buildSpecification(filter), EntityGraphUtils.fromAttributePaths("mapRoles", "mapRoles.role")
                ).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    /**
     * create new api rule
     *
     * @param request request payload
     * @return api rule saved
     */
    public ApiRuleDTO create(CreateApiRule request) {
        ApiRule apiRule = toEntity(request);

        String id = UUIDUtils.generateId();
        apiRule.setId(id);

        apiRuleRepository.save(apiRule);
        List<ApiRuleMapRole> mapRoles = saveMapper(apiRule, request.getRoles());
        apiRule.setMapRoles(mapRoles);
        return toDTO(apiRule);
    }


    /**
     * update api rule
     *
     * @param payload request payload
     * @return api rule saved
     * @throws ResourceNotFoundException not found api rule to update
     */
    public ApiRuleDTO update(UpdateApiRule payload) throws ResourceNotFoundException {
        Optional<ApiRule> optional = apiRuleRepository.findById(payload.getId());
        if (!optional.isPresent())
            throw new ResourceNotFoundException(ApiRule.class, payload.getId());

        ApiRule apiRule = optional.get();
        modelMapper.map(payload, apiRule);
        apiRuleMapRoleRepository.deleteAll(apiRule.getMapRoles());
        apiRuleMapRoleRepository.flush();
        List<ApiRuleMapRole> mapRoles = saveMapper(apiRule, payload.getRoles());
        apiRule.setMapRoles(mapRoles);
        return toDTO(apiRule);
    }

    /**
     * delete api rule
     *
     * @param id api rule id
     * @return api rule deleted
     * @throws ResourceNotFoundException not found api rule to delete
     */
    public ApiRuleDTO delete(String id) throws ResourceNotFoundException {
        Optional<ApiRule> optional = apiRuleRepository.findById(id);
        if (!optional.isPresent())
            throw new ResourceNotFoundException(ApiRule.class, id);

        ApiRule apiRule = optional.get();
        apiRuleMapRoleRepository.deleteAll(apiRule.getMapRoles());
        apiRuleRepository.delete(apiRule);
        return toDTO(apiRule);
    }

    /**
     * save role for api rule
     *
     * @param apiRule api rule
     * @param roleIds list of role id
     * @return list mapper saved
     */
    public List<ApiRuleMapRole> saveMapper(ApiRule apiRule, List<String> roleIds) {
        Specification<Role> specification = Specification.where(
                (root, query, builder) -> root.get(Role_.ROLE).in(roleIds)
        );
        return roleRepository.findAll(specification).stream()
                .map(item -> {
                    ApiRuleMapRole mapRole = new ApiRuleMapRole();
                    mapRole.setId(UUIDUtils.generateId());
                    mapRole.setRole(item);
                    mapRole.setApiRule(apiRule);
                    return apiRuleMapRoleRepository.save(mapRole);
                })
                .collect(Collectors.toList());
    }

    /**
     * create specification for query
     *
     * @param filter query param
     * @return specification
     */
    private Specification<ApiRule> buildSpecification(ApiRuleFilter filter) {
        Specification specification = Specification.where(null);
        if (null == filter)
            return specification;

        if (null != filter.getId())
            specification = specification.and(
                    buildStringSpecification(filter.getId(), ApiRule_.id)
            );
        if (null != filter.getUrlPattern())
            specification = specification.and(
                    buildStringSpecification(filter.getUrlPattern(), ApiRule_.urlPattern)
            );
        if (null != filter.getMethod())
            specification = specification.and(
                    buildStringSpecification(filter.getMethod(), ApiRule_.method)
            );
        return specification;
    }

    public ApiRuleDTO toDTO(Object input) {
        ApiRuleDTO apiRuleDTO = modelMapper.map(input, ApiRuleDTO.class);
        if (input != null && input instanceof ApiRule) {
            List<String> roles = ((ApiRule) input).getMapRoles().stream()
                    .map(ApiRuleMapRole::getRole)
                    .map(Role::getRole)
                    .collect(Collectors.toList());
            apiRuleDTO.setRoles(roles);
        }
        return apiRuleDTO;
    }

    public ApiRule toEntity(Object input) {
        return modelMapper.map(input, ApiRule.class);
    }

}
