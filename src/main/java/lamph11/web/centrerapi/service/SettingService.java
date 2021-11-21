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
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
     * get info setting by code
     *
     * @param code setting code
     * @return setting info
     * @throws LphException
     */
    @Transactional(readOnly = true)
    public SettingDTO infoSetting(String code) throws ResourceNotFoundException {
        Optional<Setting> optionalSetting = settingRepository.findById(code);
        if (!optionalSetting.isPresent())
            throw new ResourceNotFoundException(Setting.class, code);

        return optionalSetting.map(this::toDTO).get();
    }

    /**
     * create new setting
     *
     * @param dto setting info from request
     * @return saved setting
     */
    public SettingDTO create(SettingDTO dto) throws LphException {
        Optional<Setting> optional = settingRepository.findById(dto.getCode());
        if (optional.isPresent())
            throw new LphException("Setting is existed");
        Setting setting = toEntity(dto);
        settingRepository.save(setting);
        return toDTO(setting);
    }

    /**
     * build dynamic query with specification api
     *
     * @param filter query info
     * @return specification
     */
    public Specification buildSpecification(SettingFilter filter) {
        Specification specification = Specification.where(null);

        if (filter.getCode() != null)
            specification = specification.and(buildStringSpecification(filter.getCode(), Setting_.code));

        if (!StringUtils.isEmpty(filter.getKeyword()))
            specification = specification.and((root, query, builder) -> builder.or(
                    builder.like(
                            builder.lower(root.get(Setting_.code)),
                            "%" + StringUtils.lowerCase(filter.getKeyword()).trim() + "%"
                    ),
                    builder.like(
                            builder.lower(root.get(Setting_.description)),
                            "%" + StringUtils.lowerCase(filter.getKeyword()).trim() + "%"
                    ),
                    builder.like(
                            builder.lower(root.get(Setting_.metadata)),
                            "%" + filter.getKeyword() + "%"
                    )
            ));

        return specification;
    }
}
