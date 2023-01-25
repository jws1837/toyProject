package io.spring.api.rest;

import io.spring.application.TagsQueryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "tags")
@AllArgsConstructor
public class TagsApi {

  private TagsQueryService tagsQueryService;

  @GetMapping
  public ResponseEntity getTags() {
    return ResponseEntity.ok(new HashMap<String, Object>() {{
      put("tags", tagsQueryService.allTags());
    }});
  }
}
