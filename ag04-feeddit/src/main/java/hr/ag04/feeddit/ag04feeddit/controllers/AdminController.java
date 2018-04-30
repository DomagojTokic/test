package hr.ag04.feeddit.ag04feeddit.controllers;

import hr.ag04.feeddit.ag04feeddit.dto.DeleteArticleDTO;
import hr.ag04.feeddit.ag04feeddit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
		model.addObject("size", size == null ? articleService.DEFAULT_PAGE_SIZE : size);
		model.addObject("articleIds", new DeleteArticleDTO());
		
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = {"/admin"}, params = {"delete", "!vote", "!add", "!cancel"}, method = RequestMethod.POST)
	public ModelAndView deleteArticles(@ModelAttribute("Articles") DeleteArticleDTO deleteArticleDTO, @RequestParam
			(required = false) Integer page, @RequestParam(required = false) String sort, @RequestParam(required =
			false) String search, @RequestParam(required = false) Integer size) {
		String[] articleIds = deleteArticleDTO.getArticleIds();
		
		articleService.deleteArticles(articleIds);
		
		return getArticles(page, sort, search, size);
	}
}
