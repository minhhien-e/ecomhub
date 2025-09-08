package ecomhub.authservice.infrastructure.inbound.web.view;

import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.request.account.AccountLookupRequest;
import ecomhub.authservice.common.dto.request.account.RegisterBasicRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static ecomhub.authservice.infrastructure.inbound.web.mapper.AccountRequestMapper.toCommand;
import static ecomhub.authservice.infrastructure.inbound.web.mapper.AccountRequestMapper.toQuery;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IQueryBus queryBus;
    private final ICommandBus commandBus;


    @GetMapping
    public String loginPage() {
        return "auth";
    }

    @PostMapping("register")
    public String register(@ModelAttribute RegisterBasicRequest request, Model model) {
        commandBus.dispatch(toCommand(request));
        model.addAttribute("identity", request.email());
        return "redirect:/auth/login";
    }

    @GetMapping("register")
    public String register(@RequestParam String identity, Model model) {
        model.addAttribute("identity", identity);
        model.addAttribute("identityType", detectIdentityType(identity));
        return "register";
    }

    @PostMapping
    public String auth(@RequestParam String identity, Model model) {
        AccountLookupRequest request = new AccountLookupRequest(identity);
        boolean exists = queryBus.dispatch(toQuery(request));
        model.addAttribute("identity", request.identity());
        if (exists) {
            return "login";
        } else {
            return "pre-register";
        }
    }

    private String detectIdentityType(String identity) {
        if (identity.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) return "EMAIL";
        if (identity.matches("^\\+?\\d{8,15}$")) return "PHONE";
        return "USERNAME";
    }
}
