package com.example.hackernews.controller;

import com.example.hackernews.services.secviceImp.SearchServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    SearchServiceImp searchServiceImp;

    @Autowired
    public void setSearchServiceImp(SearchServiceImp searchServiceImp) {
        this.searchServiceImp = searchServiceImp;
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchKeyword") String searchKeyword,
                             Model model){
        model.addAttribute("posts",searchServiceImp.searchResult(searchKeyword,model));
        model.addAttribute("sNo",1);

        return "html/searchResult";
    }
}
