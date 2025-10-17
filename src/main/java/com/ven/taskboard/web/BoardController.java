package com.ven.taskboard.web;

import com.ven.taskboard.board.api.BoardService;
import com.ven.taskboard.web.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boards;

    public BoardController(BoardService boards) { this.boards = boards; }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateBoardRequest req) {
        UUID id = boards.createBoard(req);
        return ResponseEntity.created(URI.create("/api/boards/" + id)).build();
    }

    @GetMapping("/{boardId}/tree")
    public BoardTreeDto tree(@PathVariable UUID boardId) {
        return boards.getTree(boardId);
    }

    @PostMapping("/{boardId}/columns")
    public ResponseEntity<Void> addColumn(@PathVariable UUID boardId, @RequestBody AddColumnRequest req) {
        UUID id = boards.addColumn(boardId, req);
        return ResponseEntity.created(URI.create("/api/boards/" + boardId + "/columns/" + id)).build();
    }
}
