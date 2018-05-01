package hr.ag04.feeddit.ag04feeddit.controllers;

import hr.ag04.feeddit.ag04feeddit.dto.ArticleDTO;
import hr.ag04.feeddit.ag04feeddit.dto.VoteDTO;
import hr.ag04.feeddit.ag04feeddit.service.ArticleService;
import hr.ag04.feeddit.ag04feeddit.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controls main page where user can give votes to articles and view them in chosen order and page size.
 */
@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private VoteService voteService;
	
	private static final String headlineErrorMsg = "Molimo unesite naziv članka.";
	private static final String authorErrorMsg = "Molimo unesite ime autora.";
	private static final String urlErrorMsg = "Uneseni link nije ispravan.";
	
	@RequestMapping(value = {"/articles", "/"}, method = RequestMethod.GET)
	public ModelAndView getArticles(@RequestParam(required = false) Integer page, @RequestParam(required = false)
			String sort, @RequestParam(required = false) String search, @RequestParam(required = false)
			                                Integer size) {
		ModelAndView model = new ModelAndView("articles");
		model.addObject("articleList", articleService.getPage(page, size, sort, search));
		model.addObject("page", page == null ? 0 : page);
		model.addObject("numOfPages", articleService.getNumberOfPages(size));
		model.addObject("vote", new VoteDTO());
		model.addObject("sort", sort == null ? "" : sort);
		model.addObject("sortList", articleService.getSortsMap(sort));
		model.addObject("search", search == null ? "" : search);
		model.addObject("pageSizes", articleService.getPageSizes());
		model.addObject("size", size == null ? ArticleService.DEFAULT_PAGE_SIZE : size);
		
		return model;
	}
	
	@RequestMapping(value = "/articles", params = {"vote", "!add", "!cancel"}, method = RequestMethod.POST)
	public ModelAndView vote(@ModelAttribute("Vote") VoteDTO vote, @RequestParam Integer page, @RequestParam(required
			= false) String sort, @RequestParam(required = false) String search, @RequestParam(required = false)
			                         Integer size) {
		voteService.vote(vote);
		
		return getArticles(page, sort, search, size);
	}
	
	@RequestMapping(value = "/articles", params = {"add", "!vote", "!cancel"}, method = RequestMethod.POST)
	public ModelAndView createArticle(@ModelAttribute("Article") ArticleDTO article, BindingResult binding, ModelAndView mav,
	                                  final RedirectAttributes redirectAttributes) {
		if (article.getAuthor() == null || article.getAuthor().trim() == "" || article.getHeadline() == null ||
				article.getHeadline().trim() == "" || article.getUrl() == null || article.getUrl().trim() == "") {
			if (article.getAuthor() == null || article.getAuthor().trim() == "") {
				redirectAttributes.addFlashAttribute("authorErrorMsg", authorErrorMsg);
			}
			if (article.getHeadline() == null || article.getHeadline().trim() == "") {
				redirectAttributes.addFlashAttribute("headlineErrorMsg", headlineErrorMsg);
			}
			if (article.getUrl() == null || article.getUrl().trim() == "") {
				redirectAttributes.addFlashAttribute("urlErrorMsg", urlErrorMsg);
			}
			mav.setViewName("redirect:/create_article");
			return mav;
		} else {
			articleService.createArticle(article);
			
			return getArticles(0, null, null, null);
		}
	}
}
