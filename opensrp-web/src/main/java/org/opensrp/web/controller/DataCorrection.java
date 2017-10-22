/**
 * 
 */
package org.opensrp.web.controller;

import org.opensrp.service.Muza;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author proshanto
 *
 */
@Controller
public class DataCorrection {
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateFormView(){
		return new ModelAndView("update","command",new Muza());  
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute("mouza") Muza mouza,Model model){
		model.addAttribute("name", "Tom from Home page");
		 return new ModelAndView("redirect:/update");//will redirect to viewemp request mapping  
	}

}
