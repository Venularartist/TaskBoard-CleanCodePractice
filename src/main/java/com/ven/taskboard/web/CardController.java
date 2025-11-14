package com.ven.taskboard.web;

import com.ven.taskboard.card.api.CardAssigner;
import com.ven.taskboard.card.api.CardCreator;
import com.ven.taskboard.card.api.CardMover;
import com.ven.taskboard.web.dto.AssignCardRequest;
import com.ven.taskboard.web.dto.CreateCardRequest;
import com.ven.taskboard.web.dto.MoveCardRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    //segmentation implementation
    private final CardCreator creator;
    private final CardMover mover;
    private final CardAssigner assigner;

    public CardController(CardCreator creator, CardMover mover, CardAssigner assigner) {
        this.creator = creator;
        this.mover = mover;
        this.assigner = assigner;
    }

    // Uses Builder under the hood via CardCreator.create(...)
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateCardRequest req) {
        UUID id = creator.create(req);
        return ResponseEntity.created(URI.create("/api/cards/" + id)).build(); // 201 Created
    }

    @PostMapping("/{cardId}/move")
    public ResponseEntity<Void> move(@PathVariable UUID cardId, @RequestBody MoveCardRequest req) {
        mover.move(cardId, req);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Factory + Bridge/Adapter/Decorator happen inside CardAssigner.assign(...)
    @PostMapping("/{cardId}/assign")
    public ResponseEntity<Void> assign(@PathVariable UUID cardId, @RequestBody AssignCardRequest req) {
        assigner.assign(cardId, req);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
