package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airportapi.dto.UserAuthenticateDTO;
import cz.muni.fi.airportapi.dto.UserDTO;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportapi.facade.UserFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import static cz.muni.fi.mvc.controllers.StewardController.log;
import cz.muni.fi.mvc.security.ProtectFilter;
import javax.resource.spi.work.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/")
@Import({FacadeTestConfiguration.class})
public class HomepageController {

    final static Logger log = LoggerFactory.getLogger(HomepageController.class);

    @Autowired
    private UserFacade userFacade;

//    @RequestMapping(method = RequestMethod.GET)
//    public String home(Model model) {
//        return "home";
//    }
    @RequestMapping(method = RequestMethod.GET, value = "")
    public String displayLogin(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("user");

        if (user == null) {
            UserAuthenticateDTO userAuth = new UserAuthenticateDTO();
            model.addAttribute("valid", false);
            model.addAttribute("userAuth", userAuth);
        } else {
            model.addAttribute("valid", true);
            model.addAttribute("user", user);
        }
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String executeLogin(Model model, HttpServletRequest request,
            @Valid @ModelAttribute("userAuth") UserAuthenticateDTO userAuth,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {

        try {
            if (bindingResult.hasErrors()) {
                for (FieldError fe : bindingResult.getFieldErrors()) {
                    model.addAttribute(fe.getField() + "_error", true);
                    log.trace("FieldError: {}", fe);
                }
                for (ObjectError ge : bindingResult.getGlobalErrors()) {
                    log.trace("ObjectError: {}", ge);
                }
                return "home";
            }

            HttpSession session = request.getSession(true);
            if (session != null) {
                if (authenticate(session, userAuth)) {
                    redirectAttributes.addFlashAttribute("alert_success", "Succesfull login");
                    return "redirect:" + uriBuilder.path("home").toUriString();
                }
            } else {
                redirectAttributes.addFlashAttribute("alert_danger", "login Failed");
                return "redirect:" + uriBuilder.path("home").toUriString();
            }
        } catch (Exception e) {
            log.trace("error in auth page");
        }
        return "home";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String executeLogout(Model model, HttpServletRequest request,
            RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {

        try {
            HttpSession session = request.getSession(true);
            if (session != null) {
                session.setAttribute("user", null);
                session.setAttribute("passHash", null);
                redirectAttributes.addFlashAttribute("alert_success", "Succesfull logout");
                return "redirect:" + uriBuilder.path("home").toUriString();
            }
        } catch (Exception e) {
            log.trace("error in auth page");
            redirectAttributes.addFlashAttribute("alert_danger", "logout Failed");
            return "redirect:" + uriBuilder.path("home").toUriString();
        }
        return "home";
    }

    private boolean authenticate(HttpSession session, UserAuthenticateDTO userAuth) {

        UserDTO matchingUser = userFacade.findUserByUserName(userAuth.getUserName());
        userAuth.setUserId(matchingUser.getId());

        if (matchingUser == null) {
            log.warn("no user with user name {}", userAuth.getUserName());
            return false;
        }
        if (!userFacade.authenticate(userAuth)) {
            log.warn("wrong credentials: user={} password={}", userAuth.getUserName(), userAuth.getPassword());
            session.setAttribute("user", null);
            session.setAttribute("passHash", null);
            return false;
        } else {
            session.setAttribute("user", matchingUser.getUserName());
            session.setAttribute("passHash", matchingUser.getPasswordHash());
        }

        return true;
    }
}
