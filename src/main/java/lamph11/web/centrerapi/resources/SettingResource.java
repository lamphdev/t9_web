package lamph11.web.centrerapi.resources;

import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.common.io.PageResponse;
import lamph11.web.centrerapi.resources.dto.setting.SettingDTO;
import lamph11.web.centrerapi.resources.dto.setting.SettingFilter;
import lamph11.web.centrerapi.service.SettingOptionService;
import lamph11.web.centrerapi.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity createSetting(@Valid @RequestBody SettingDTO dto) {
        return ResponseEntity.ok(settingService.create(dto));
    }

    @RequestMapping("/{settingId}")
    public ResponseEntity infoSetting(@PathVariable("settingId") String settingId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(settingService.infoSetting(settingId));
    }

}
