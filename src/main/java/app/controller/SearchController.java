package app.controller;

import app.dto.search.SearchDTO;
import app.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchDTO> search(@RequestParam(required = false) UUID user_id, @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(searchService.searchRoomsAndUsers(user_id, keyword));
    }
}
