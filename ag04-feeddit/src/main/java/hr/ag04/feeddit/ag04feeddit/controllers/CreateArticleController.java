package hr.ag04.feeddit.ag04feeddit.controllers;

import hr.ag04.feeddit.ag04feeddit.dto.ArticleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CreateArticleController {
	
	@RequestMapping(value = "/create_article", method = RequestMethod.GET)
	public ModelAndView initCreateArticle() {
		ModelAndView model = new ModelAndView("create_article");
		model.addObject("article", new ArticleDTO());
		
		return model;
	}
	
	@RequestMapping(value = "/articles", params = {"cancel", "!vote", "!add"}, method = RequestMethod.POST)
	public String cancelCreateArticle(HttpServletRequest request) {
		return "redirect:/articles";
	}
	
}
