package lamph11.web.centrerapi.service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import io.github.jhipster.service.QueryService;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.entity.SettingOption;
import lamph11.web.centrerapi.entity.SettingOption_;
import lamph11.web.centrerapi.entity.Setting_;
import lamph11.web.centrerapi.repository.SettingOptionRepository;
import lamph11.web.centrerapi.resources.dto.setting.SettingFilter;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionDTO;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.JoinType;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {LphException.class, Exception.class})
public class SettingOptionService extends QueryService<SettingOption> {

    private final ModelMapper modelMapper;
    private final SettingOptionRepository settingOptionRepository;

    Function<Object, SettingOption> toSettingOption;
    Function<Object, SettingOptionDTO> toSettingOptionDTO;

    @PostConstruct
    public void init() {
        toSettingOption = obj -> modelMapper.map(obj, SettingOption.class);
        toSettingOptionDTO = obj -> modelMapper.map(obj, SettingOptionDTO.class);
    }

    /**
     * get page setting option
     *
     * @param filter query info
     * @return page setting option
     */
    public Page<SettingOptionDTO> getPageBySetting(SettingOptionFilter filter) {
        Page<SettingOption> settingOptions = settingOptionRepository.findAll(
                buildSpecification(filter),
                filter.getPageable(),
                EntityGraphUtils.fromAttributePaths(SettingOption_.SETTING)
        );
        return settingOptions.map(toSettingOptionDTO);
    }

    /**
     * build setting option specification
     *
     * @param filter query info
     * @return specification
     */
    public Specification buildSpecification(SettingOptionFilter filter) {
        Specification specification = Specification.where((root, query, builder) -> {
            if (filter.getSetting() != null) {
                root.join(SettingOption_.SETTING, JoinType.INNER);
            }
            return null;
        });

        if (filter.getId() != null)
            specification = specification.and(
                    buildStringSpecification(filter.getId(), SettingOption_.id)
            );

        if (filter.getName() != null)
            specification = specification.and(
                    buildStringSpecification(filter.getName(), SettingOption_.name)
            );

        if (filter.getMetadata() != null)
            specification = specification.and(
                    buildStringSpecification(filter.getMetadata(), SettingOption_.metadata)
            );

        if (filter.getValue() != null)
            specification.and(
                    buildStringSpecification(filter.getValue(), SettingOption_.value)
            );

        if (filter.getSetting() != null) {
            SettingFilter settingFilter = filter.getSetting();

            if (settingFilter.getId() != null)
                specification = specification.and(
                        buildReferringEntitySpecification(settingFilter.getId(), SettingOption_.setting, Setting_.id)
                );

            if (settingFilter.getName() != null)
                specification = specification.and(
                        buildReferringEntitySpecification(
                                settingFilter.getName(), SettingOption_.setting, Setting_.name
                        )
                );
        }

        return specification;
    }

}
