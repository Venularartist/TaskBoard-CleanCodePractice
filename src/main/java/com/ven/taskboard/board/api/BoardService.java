package com.ven.taskboard.board.api;

import com.ven.taskboard.board.iterator.IterableBoard;
import com.ven.taskboard.board.iterator.IterableColumn;
import com.ven.taskboard.board.iterator.Iterator;
import com.ven.taskboard.board.template.TemplateRegistry;
import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.persistence.*;
import com.ven.taskboard.web.dto.AddColumnRequest;
import com.ven.taskboard.web.dto.BoardTreeDto;
import com.ven.taskboard.web.dto.CreateBoardRequest;
import com.ven.taskboard.board.composite.BoardTreeAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

// Prototype — this template acts as a prototype for new boards.
// If no template is provided, fall back to a plain BoardEntity.

@Service
public class BoardService {

    private final BoardRepository boards;
    private final ColumnRepository columns;
    private final TemplateRegistry templates;

    public BoardService(BoardRepository boards, ColumnRepository columns, TemplateRegistry templates) {
        this.boards = boards;
        this.columns = columns;
        this.templates = templates;
    }

    @Transactional
    public UUID createBoard(CreateBoardRequest req) {
        BoardEntity board = templates.get(req.templateKey() == null ? "" : req.templateKey())
                .map(t -> t.create(req.name()))
                .orElseGet(() -> new BoardEntity(req.name()));
        boards.save(board);
        return board.getId();
    }

    @Transactional(readOnly = true)
    public BoardTreeDto getTree(UUID boardId) {
        BoardEntity board = boards.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board not found: " + boardId));
        // force-load collections if LAZY (optional; JPA/Hibernate usually loads on access)
        board.getColumns().forEach(c -> c.getCards().size());
        return BoardTreeAssembler.toDto(board); //Użycie Composite
    }

    @Transactional
    public UUID addColumn(UUID boardId, AddColumnRequest req) {
        BoardEntity board = boards.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board not found: " + boardId));
        int order = board.getColumns().size() + 1;
        ColumnEntity col = board.addColumn(req.name(), req.wipLimit() != null ? req.wipLimit() : 0, order);
        boards.save(board);
        return col.getId();
    }

    @Transactional(readOnly = true)
    public long countBoards() { return boards.count(); }

    //Iterator użycie
    @Transactional(readOnly = true)
    public void printAllCards(UUID boardId) {
        BoardEntity board = boards.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board not found: " + boardId));

        //IterableBoard → BoardIterator
        IterableBoard iterableBoard = new IterableBoard(board);
        Iterator<ColumnEntity> colIter = iterableBoard.createIterator();

        while (colIter.hasNext()) {
            ColumnEntity column = colIter.next();
            System.out.println("Column: " + column.getName());

            //IterableColumn → ColumnIterator
            IterableColumn iterableColumn = new IterableColumn(column);
            Iterator<CardEntity> cardIter = iterableColumn.createIterator();

            while (cardIter.hasNext()) {
                CardEntity card = cardIter.next();
                System.out.println("   - Card: " + card.getTitle());
            }
        }
    }

}
