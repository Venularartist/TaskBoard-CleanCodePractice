package com.ven.taskboard.web;

import com.ven.taskboard.board.api.BoardFactory;
//import com.ven.taskboard.board.api.BoardService;
import com.ven.taskboard.board.api.BoardTreeReader;
import com.ven.taskboard.board.api.ColumnManager;
import com.ven.taskboard.web.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardFactory factory;
    private final ColumnManager columns;
    private final BoardTreeReader reader;

    public BoardController(BoardFactory factory, BoardTreeReader reader, ColumnManager columns) { //segregation implementation
        this.factory = factory;
        this.columns = columns;
        this.reader = reader;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateBoardRequest req) {
        UUID id = factory.createBoard(req);
        return ResponseEntity.created(URI.create("/api/boards/" + id)).build();
    }

    @GetMapping("/{boardId}/tree")
    public BoardTreeDto tree(@PathVariable UUID boardId) {
        return reader.getTree(boardId);
    }

    @PostMapping("/{boardId}/columns")
    public ResponseEntity<Void> addColumn(@PathVariable UUID boardId, @RequestBody AddColumnRequest req) {
        UUID id = columns.addColumn(boardId, req);
        return ResponseEntity.created(URI.create("/api/boards/" + boardId + "/columns/" + id)).build();
    }
}
