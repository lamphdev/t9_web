package lamph11.web.centrerapi.resources;

import io.github.jhipster.service.filter.StringFilter;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.common.io.CookieUtils;
import lamph11.web.centrerapi.common.io.PageResponse;
import lamph11.web.centrerapi.common.validate.ICreate;
import lamph11.web.centrerapi.resources.dto.setting.SettingDTO;
import lamph11.web.centrerapi.resources.dto.setting.SettingFilter;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionFilter;
import lamph11.web.centrerapi.service.SettingOptionService;
import lamph11.web.centrerapi.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting")
public class SettingResource {

    private final SettingService settingService;
    private final SettingOptionService settingOptionService;

    @RequestMapping
    public ResponseEntity querySetting(@ModelAttribute SettingFilter filter) {
        return ResponseEntity.ok(PageResponse.from(settingService.querySetting(filter)));
    }

    @PostMapping
    public ResponseEntity createSetting(@Validated(Default.class) @RequestBody SettingDTO dto) {
        return ResponseEntity.ok(settingService.create(dto));
    }

    @RequestMapping("/{settingId}")
    public ResponseEntity infoSetting(@PathVariable("settingId") String settingId) throws ResourceNotFoundException {
        return ResponseEntity.ok(settingService.infoSetting(settingId));
    }

    @GetMapping("/{settingId}/options")
    public ResponseEntity getOptions(
            @PathVariable("settingId") String settingId, @ModelAttribute SettingOptionFilter filter
    ) {
        if (filter.getSetting() == null)
            filter.setSetting(new SettingFilter());

        StringFilter settingIdFilter = new StringFilter();
        settingIdFilter.setEquals(settingId);
        filter.getSetting().setId(settingIdFilter);
        return ResponseEntity.ok(
                PageResponse.from(settingOptionService.getPageBySetting(filter))
        );
    }

}
