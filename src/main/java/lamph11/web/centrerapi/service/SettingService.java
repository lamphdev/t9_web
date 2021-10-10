package lamph11.web.centrerapi.service;

import io.github.jhipster.service.QueryService;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.common.utils.UUIDUtils;
import lamph11.web.centrerapi.entity.Setting;
import lamph11.web.centrerapi.entity.Setting_;
import lamph11.web.centrerapi.repository.SettingOptionRepository;
import lamph11.web.centrerapi.repository.SettingRepository;
import lamph11.web.centrerapi.resources.dto.setting.SettingDTO;
import lamph11.web.centrerapi.resources.dto.setting.SettingFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {LphException.class, Exception.class})
public class SettingService extends QueryService {

    private final ModelMapper modelMapper;
    private final SettingRepository settingRepository;
    private final SettingOptionRepository settingOptionRepository;

    public SettingDTO toDTO(Object input) {
        return modelMapper.map(input, SettingDTO.class);
    }

    public Setting toEntity(Object input) {
        return modelMapper.map(input, Setting.class);
    }

    /**
     * query page setting
     *
     * @param filter query info
     * @return page setting
     */
    @Transactional(readOnly = true)
    public Page<SettingDTO> querySetting(SettingFilter filter) {
        Specification<Setting> specification = buildSpecification(filter);
        return settingRepository.findAll(specification, filter.getPageable()).map(this::toDTO);
    }

    /**
     * get info setting by id
     *
     * @param id setting id
     * @return setting info
     * @throws LphException
     */
    @Transactional(readOnly = true)
    public SettingDTO infoSetting(String id) throws ResourceNotFoundException {
        Optional<Setting> optionalSetting = settingRepository.findById(id);
        if (!optionalSetting.isPresent())
            throw new ResourceNotFoundException(Setting.class, id);

        return optionalSetting.map(this::toDTO).get();
    }

    /**
     * create new setting
     *
     * @param dto setting info from request
     * @return saved setting
     */
    public SettingDTO create(SettingDTO dto) {
        Setting setting = toEntity(dto);
        setting.setId(UUIDUtils.generateId());
        settingRepository.save(setting);
        return toDTO(setting);
    }

    /**
     * build dynamic query with specification api
     *
     * @param filter query info
     * @return specification
     */
    private Specification buildSpecification(SettingFilter filter) {
        Specification specification = Specification.where(null);

        if (filter.getId() != null)
            specification = specification.and(buildStringSpecification(filter.getId(), Setting_.id));

        if (filter.getName() != null)
            specification = specification.and(buildStringSpecification(filter.getName(), Setting_.name));

        if (filter.getMetadata() != null)
            specification = specification.and(buildSpecification(filter.getMetadata(), Setting_.metadata));

        return specification;
    }
}
