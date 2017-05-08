package a.controller;

import a.dto.ProfileFormDTO;
import a.usLocalDateFormater.USLocalDateFormater;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class ProfileContoller {

    @RequestMapping(value = "/profile")
    public String diplayProfile() {
        return "profilePage";
    }

    @ModelAttribute("dateFormat")
    public String locateForemat(Locale locale) {
        return USLocalDateFormater.getPattern(locale);
    }

    @RequestMapping(value = "/profile",params = {"save"}, method = RequestMethod.POST)
    public String saveProfile(@Valid ProfileFormDTO profileFormDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/profilePage";
        }
        System.out.println("pomysnie zapisono profil " + profileFormDTO.getTwitterHandle());
        return "redirect:/profile";
    }

    @RequestMapping(value = "/profile", params = {"addTaste"})
    public String addRow(ProfileFormDTO profileFormDTO) {
        profileFormDTO.getTastes().add(null);
        return "/profilePage";
    }

    @RequestMapping(value = "/profile", params = {"removeTaste"})
    public String removeRow(ProfileFormDTO profileFormDTO, HttpServletRequest request) {
        Integer rowId = Integer.valueOf(request.getParameter("removeTaste"));
        profileFormDTO.getTastes().remove(rowId.intValue());
        return "/profilePage";
    }


}
