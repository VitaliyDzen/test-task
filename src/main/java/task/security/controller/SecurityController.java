package task.security.controller;

import static task.constants.AppConstant.CREATE_AUTHENTICATION_TOKEN;
import static task.constants.AppConstant.CREATE_NEW_USER;
import static task.constants.AppConstant.UPDATING_ACCESS_TOKEN_BY_REFRESH_TOKEN;
import static task.constants.AppConstant.USER_ALREADY_REGISTERED_WITH_THIS_EMAIL;
import static task.constants.ErrorMessage.REFRESH_TOKEN_NOT_VALID;
import static task.constants.ResourceMapping.SECURITY;
import static task.constants.ResourceMapping.SIGN_IN;
import static task.constants.ResourceMapping.SIGN_UP;
import static task.constants.ResourceMapping.UPDATE_ACCESS_TOKEN;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task.constants.HttpStatuses;
import task.dto.user.UserSaveDto;
import task.security.dto.SignInDto;
import task.security.dto.SuccessSignInDto;
import task.security.service.SecurityService;

@RequestMapping(SECURITY)
@RestController
@CrossOrigin
public class SecurityController {

    private final SecurityService service;

    @Autowired
    public SecurityController(
        SecurityService service) {
        this.service = service;
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = HttpStatuses.OK),
        @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST)
    })
    @ApiOperation(value = CREATE_AUTHENTICATION_TOKEN)
    @PostMapping(SIGN_IN)
    public SuccessSignInDto singIn(@Valid @RequestBody SignInDto signInDto) {
        return service.signIn(signInDto);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 201, message = HttpStatuses.CREATED),
        @ApiResponse(code = 400, message = USER_ALREADY_REGISTERED_WITH_THIS_EMAIL)
    })
    @ApiOperation(value = CREATE_NEW_USER)
    @PostMapping(SIGN_UP)
    public ResponseEntity<?> singUp(@Valid @RequestBody UserSaveDto userSaveDto) {
        return ResponseEntity.ok(service.saveUser(userSaveDto));
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = HttpStatuses.OK),
        @ApiResponse(code = 400, message = REFRESH_TOKEN_NOT_VALID)
    })
    @ApiOperation(UPDATING_ACCESS_TOKEN_BY_REFRESH_TOKEN)
    @GetMapping(UPDATE_ACCESS_TOKEN)
    public ResponseEntity<Object> updateAccessToken(@RequestParam @NotBlank String refreshToken) {
        return ResponseEntity.ok().body(service.updateAccessTokens(refreshToken));

    }
}