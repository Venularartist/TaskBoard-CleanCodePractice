package com.ven.taskboard.web;

import com.ven.taskboard.card.service.CardService;
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

    private final CardService cards;

    public CardController(CardService cards) {
        this.cards = cards;
    }

    // Uses Builder under the hood (CardBuilder) via CardService.create(...)
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateCardRequest req) {
        UUID id = cards.create(req);
        return ResponseEntity.created(URI.create("/api/cards/" + id)).build(); // 201 Created
    }

    @PostMapping("/{cardId}/move")
    public ResponseEntity<Void> move(@PathVariable UUID cardId, @RequestBody MoveCardRequest req) {
        cards.move(cardId, req);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Factory (strategy selection) + Bridge/Adapter/Decorator in CardService.assign(...)
    @PostMapping("/{cardId}/assign")
    public ResponseEntity<Void> assign(@PathVariable UUID cardId, @RequestBody AssignCardRequest req) {
        cards.assign(cardId, req);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
