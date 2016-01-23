package cz.muni.fi.mvc.controllers;

import cz.muni.fi.airportapi.dto.StewardAuthDTO;
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
    private StewardFacade stewardFacade;

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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String logingGet(Model model
    ) {
        model.addAttribute("authSteward", new StewardAuthDTO());
        return "login";
    }
    
    @RequestMapping(value = "/loged", method = RequestMethod.POST)
    public String loginPost(@Valid
            @ModelAttribute("authSteward") StewardAuthDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {

            return "login";
        }
        if (!stewardFacade.authentication(formBean)) {
            model.addAttribute("alert_warning", "authentication failed");
            return "login";
        }
        
        redirectAttributes.addFlashAttribute("alert_success", "Succesfully loged in");
        request.getSession().setAttribute("authenticated", stewardFacade.getStewardWithUsername(formBean.getUsername()));
        return "redirect:/";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request
    ) {
        request.getSession().removeAttribute("authenticated");
        model.addAttribute("alert_success", "loged out");
        model.addAttribute("authSteward", new StewardAuthDTO());
        return "/login";
    }
    
}
