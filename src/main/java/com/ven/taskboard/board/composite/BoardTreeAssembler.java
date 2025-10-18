package com.ven.taskboard.board.composite;

import com.ven.taskboard.persistence.*;
import com.ven.taskboard.web.dto.BoardTreeDto;

import java.util.Arrays;
import java.util.List;

// Composite (read side) — we treat Board → Columns → Cards as a tree and

// The UI needs the whole board at once: Board → Columns → Cards

// instead of making the caller stitch them together we make the nececarry calls and return a single object

// toDto(BoardEntity board)

// GET /api/boards/{boardId}/tree → BoardService#getTree (assembler)

public class BoardTreeAssembler {

    // Composite traversal — assemble a tree of immutable DTOs from the aggregate.

    public static BoardTreeDto toDto(BoardEntity board) {
        var cols = board.getColumns().stream().map(col ->
                new BoardTreeDto.ColumnDto(
                        col.getId(),
                        col.getName(),
                        col.getWipLimit(),
                        col.getOrderIndex(),
                        col.getCards().stream().map(card ->
                                new BoardTreeDto.CardDto(
                                        card.getId(),
                                        card.getTitle(),
                                        card.getStatus(),
                                        card.getAssignee(),
                                        card.getDueDate(),
                                        csvToList(card.getLabelsCsv())
                                )
                        ).toList()
                )
        ).toList();

        return new BoardTreeDto(board.getId(), board.getName(), cols);
    }

    private static List<String> csvToList(String csv) {
        if (csv == null || csv.isBlank()) return List.of();
        return Arrays.stream(csv.split(",")).map(String::trim).filter(s -> !s.isBlank()).toList();
    }
}
