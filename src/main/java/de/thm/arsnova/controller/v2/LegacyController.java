/*
 * This file is part of ARSnova Backend.
 * Copyright (C) 2012-2018 The ARSnova Team and Contributors
 *
 * ARSnova Backend is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ARSnova Backend is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.thm.arsnova.controller.v2;

import de.thm.arsnova.controller.AbstractController;
import de.thm.arsnova.services.CommentService;
import de.thm.arsnova.services.ContentService;
import de.thm.arsnova.web.DeprecatedApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This controller forwards requests from deprecated URLs to their new controller, where the requests are handled.
 */
@Controller("v2LegacyController")
@RequestMapping("/v2")
public class LegacyController extends AbstractController {

	@Autowired
	private ContentService contentService;

	@Autowired
	private CommentService commentService;

	/* specific routes */

	@DeprecatedApi
	@RequestMapping(value = "/session/mysessions", method = RequestMethod.GET)
	public String redirectSessionMy() {
		return "forward:/v2/session/?ownedonly=true";
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/visitedsessions", method = RequestMethod.GET)
	public String redirectSessionVisited() {
		return "forward:/v2/session/?visitedonly=true";
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/question")
	public String redirectQuestionByLecturer(@PathVariable final String shortId) {
		return String.format("forward:/v2/lecturerquestion/?sessionkey=%s", shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/skillquestions", method = RequestMethod.GET)
	public String redirectQuestionByLecturerList(@PathVariable final String shortId) {
		return String.format("forward:/v2/lecturerquestion/?sessionkey=%s", shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/skillquestioncount", method = RequestMethod.GET)
	public String redirectQuestionByLecturerCount(@PathVariable final String shortId) {
		return String.format("forward:/v2/lecturerquestion/count?sessionkey=%s", shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/answercount", method = RequestMethod.GET)
	public String redirectQuestionByLecturerAnswerCount(@PathVariable final String shortId) {
		return String.format("forward:/v2/lecturerquestion/answercount?sessionkey=%s", shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/unanswered", method = RequestMethod.GET)
	public String redirectQuestionByLecturerUnnsweredCount(@PathVariable final String shortId) {
		return String.format("forward:/v2/lecturerquestion/answercount?sessionkey=%s", shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/myanswers", method = RequestMethod.GET)
	public String redirectQuestionByLecturerMyAnswers(@PathVariable final String shortId) {
		return String.format("forward:/v2/lecturerquestion/myanswers?sessionkey=%s", shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/interposed")
	public String redirectQuestionByAudience(@PathVariable final String shortId) {
		return String.format("forward:/v2/audiencequestion/?sessionkey=%s", shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/interposed", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteAllInterposedQuestions(@PathVariable final String shortId) {
		commentService.deleteByRoomId(shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/interposedcount", method = RequestMethod.GET)
	public String redirectQuestionByAudienceCount(@PathVariable final String shortId) {
		return String.format("forward:/v2/audiencequestion/count?sessionkey=%s", shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/interposedreadingcount", method = RequestMethod.GET)
	public String redirectQuestionByAudienceReadCount(@PathVariable final String shortId) {
		return String.format("forward:/v2/audiencequestion/readcount?sessionkey=%s", shortId);
	}

	/* generalized routes */

	@DeprecatedApi
	@RequestMapping(value = { "/session/{shortId}/question/{arg1}", "/session/{shortId}/questions/{arg1}" })
	public String redirectQuestionByLecturerWithOneArgument(
			@PathVariable final String shortId,
			@PathVariable final String arg1
			) {
		return String.format("forward:/v2/lecturerquestion/%s/?sessionkey=%s", arg1, shortId);
	}

	@DeprecatedApi
	@RequestMapping(
			value = { "/session/{shortId}/question/{arg1}/{arg2}", "/session/{shortId}/questions/{arg1}/{arg2}" }
			)
	public String redirectQuestionByLecturerWithTwoArguments(
			@PathVariable final String shortId,
			@PathVariable final String arg1,
			@PathVariable final String arg2
			) {
		return String.format("forward:/v2/lecturerquestion/%s/%s/?sessionkey=%s", arg1, arg2, shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/interposed/{arg1}")
	public String redirectQuestionByAudienceWithOneArgument(
			@PathVariable final String shortId,
			@PathVariable final String arg1
			) {
		return String.format("forward:/v2/audiencequestion/%s/?sessionkey=%s", arg1, shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/session/{shortId}/interposed/{arg1}/{arg2}")
	public String redirectQuestionByAudienceWithTwoArguments(
			@PathVariable final String shortId,
			@PathVariable final String arg1,
			@PathVariable final String arg2
			) {
		return String.format("forward:/v2/audiencequestion/%s/%s/?sessionkey=%s", arg1, arg2, shortId);
	}

	@DeprecatedApi
	@RequestMapping(value = "/whoami")
	public String redirectWhoami() {
		return "forward:/v2/auth/whoami";
	}

	@DeprecatedApi
	@RequestMapping(value = "/doLogin")
	public String redirectLogin() {
		return "forward:/v2/auth/login";
	}
}
