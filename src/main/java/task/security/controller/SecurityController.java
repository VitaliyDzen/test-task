package task.security.controller;

import static task.constants.AppConstant.CREATE_AUTHENTICATION_TOKEN;
import static task.constants.AppConstant.CREATE_NEW_USER;
import static task.constants.AppConstant.USER_ALREADY_REGISTERED_WITH_THIS_EMAIL;
import static task.constants.ResourceMapping.SECURITY;
import static task.constants.ResourceMapping.SIGN_IN;
import static task.constants.ResourceMapping.SIGN_UP;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.constants.HttpStatuses;
import task.dto.user.UserSaveDto;
import task.security.model.JwtRequest;
import task.security.service.SecurityService;

@RequestMapping(SECURITY)
@RestController
@CrossOrigin
public class SecurityController {

    private final SecurityService userDetailsService;

    @Autowired
    public SecurityController(
        SecurityService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = HttpStatuses.OK),
        @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST)
    })
    @ApiOperation(value = CREATE_AUTHENTICATION_TOKEN)
    @PostMapping(SIGN_IN)
    public ResponseEntity<?> createAuthenticationToken(
        @RequestBody JwtRequest authenticationRequest) throws Exception {
        return userDetailsService.createAuthenticationToken(authenticationRequest);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 201, message = HttpStatuses.CREATED),
        @ApiResponse(code = 400, message = USER_ALREADY_REGISTERED_WITH_THIS_EMAIL)
    })
    @ApiOperation(value = CREATE_NEW_USER)
    @PostMapping(SIGN_UP)
    public ResponseEntity<?> saveUser(@RequestBody UserSaveDto userSaveDto) {
        return ResponseEntity.ok(userDetailsService.saveUser(userSaveDto));
    }
}