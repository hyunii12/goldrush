package controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import model.TrendKeyword;
import service.IPriceService;
import service.ITrendKeywordService;

@Controller
public class TrendKeywordController {

	@Autowired
	public ITrendKeywordService trendService;

	@RequestMapping("trend.do")
	public ModelAndView trend() {
		ModelAndView mav = new ModelAndView();
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		String lastMonth = format.format(c.getTime());
		mav.addObject("lastMonth", lastMonth);
		mav.setViewName("trend");
		return mav;
	}

	@RequestMapping("trendword.do")
	public @ResponseBody Map<String, Object> getTrendWord(Model model,
			@RequestParam(defaultValue = "0", required = false) int mode,
			@RequestParam(value = "k_year", required = false) String k_year) {
		HashMap<String, Object> trendMap = null;
		if (mode == 0) {
			// default 값 모드 0일 경우 현재 기준 12개월 데이터 전송해줌
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -13);
			String fromMonth = format.format(cal.getTime());
			trendMap = trendService.getRecentTrendKeywordList(fromMonth);
		} else if (mode == 1) {
			// 모드 1일 경우 검색하고자 하는 년도의 데이터 전송해줌
			trendMap = trendService.getTrendKeywordList(k_year);
		}
		return trendMap;
	}

}
