package redt.app.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import redt.app.Helpers.BadWords;
import redt.app.exception.BadWordsException;
import redt.app.exception.NotFoundException;
import redt.app.model.Feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import redt.app.service.feedback.FeedbackService;
import redt.app.service.reaction.ReactionService;

import javax.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private ReactionService reactionService;

	@Autowired
	private BadWords badWords;

	@ExceptionHandler(NotFoundException.class)
	public String notFoundPage() {
		return "notfound";
	}

	@ExceptionHandler(BadWordsException.class)
	public String badWordsPage() {
		return "badwords";
	}

	@GetMapping("/")
	public String index(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 2) Pageable pageable, Model model){
		Page<Feedback> pages = feedbackService.findAll(pageable);
		model.addAttribute("pages", pages);
		return "index";
	}

	@PostMapping("/")
	public String indexPost(
			@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 2) Pageable pageable,
			@Valid @ModelAttribute("feedback") Feedback feedback, BindingResult result, Model model
	) throws BadWordsException
	{
		if (result.hasErrors()){
			Page<Feedback> pages = feedbackService.findAll(pageable);
			model.addAttribute("pages", pages);
			return "index";
		}
		boolean checkBad = badWords.contains(feedback.getComment());
		if (checkBad){
			throw new BadWordsException("The content contains bad words.");
		}
		feedbackService.save(feedback);
		Page<Feedback> pages = feedbackService.findAll(pageable);
		model.addAttribute("pages", pages);
		return "index";
	}

	@GetMapping("/reaction")
	public String reaction(@RequestParam String r, @RequestParam Long id,  Model model){
		Feedback feedback = feedbackService.findById(id);
		if (feedback != null){
			switch (r){
				case "Like":
					reactionService.reactions("Like", feedback);
					break;

				case "Love":
					reactionService.reactions("Love", feedback);
					break;

				case "Haha":
					reactionService.reactions("Haha", feedback);
					break;

				case "Angry":
					reactionService.reactions("Angry", feedback);
					break;

				case "Cry":
					reactionService.reactions("Cry", feedback);
					break;
			}
		}

		return "redirect:/";
	}
}
