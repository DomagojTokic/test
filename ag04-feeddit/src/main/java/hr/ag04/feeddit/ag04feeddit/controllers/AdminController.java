package hr.ag04.feeddit.ag04feeddit.controllers;

import hr.ag04.feeddit.ag04feeddit.enums.ArticleSortEnum;
import hr.ag04.feeddit.ag04feeddit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
	public ModelAndView getArticles(@RequestParam(required = false) Integer page, @RequestParam(required = false)
			String sort, @RequestParam(required = false) String search, @RequestParam(required = false)
			                                Integer size) {
		ModelAndView model = new ModelAndView("admin");
		model.addObject("articleList", articleService.getPage(page, size, sort, search));
		model.addObject("page", page == null ? 0 : page.intValue());
		model.addObject("numOfPages", articleService.getNumberOfPages(size));
		model.addObject("sort", sort == null ? "" : sort);
		model.addObject("sortList", articleService.getSortsMap(sort));
		model.addObject("search", search == null ? "" : search);
		model.addObject("pageSizes", articleService.getPageSizes());
		model.addObject("size", size == null ? 0 : size);
		
		return model;
	}
	@ResponseBody
	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	public void deleteArticles(@RequestParam(value = "articles") List<String> articles) {
		//articleService.deleteArticles(articles);
		
		System.out.println("Success");
	}
}
