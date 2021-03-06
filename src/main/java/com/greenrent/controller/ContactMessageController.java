package com.greenrent.controller;

import com.greenrent.domain.ContactMessage;
import com.greenrent.service.ContactMessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contactmessage")
@AllArgsConstructor
public class ContactMessageController {

    @Autowired
    private ContactMessageService contactMessageService;

    // http://localhost:8080/contactmessage/visitor
    @PostMapping("/visitors")
    public ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody ContactMessage contactMessage) {
        contactMessageService.createContactMessage(contactMessage);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Contact Message Successfully Created");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    // http://localhost:8080/contactmessage
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactMessage>> getAllContactMessage() {
        List<ContactMessage> list = contactMessageService.getAll();
        return ResponseEntity.ok(list);
    }

    // http://localhost:8080/contactmessage/1
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessage> getContactMessageWithId(@PathVariable("id") Long id) {

        // ContactMessage contactMessage = contactMessageService.getContactMessage(id);
        // return ResponseEntity.ok(contactMessage);
        return ResponseEntity.ok(contactMessageService.getContactMessage(id));
    }

    // http://localhost:8080/contactmessage/mesajigor?id=3
    @GetMapping("/mesajigor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessage> getContactMessageWithRequestParam(@RequestParam("id") Long id) {
        return ResponseEntity.ok(contactMessageService.getContactMessage(id));
    }

    // http://localhost:8080/contactmessage/2

    /*
    {
    "name": "Wart",
    "email": "wart@gmail.com",
    "subject": "Hello",
    "body": "Hello your work is great, thanks for your effor"
    }
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateContactMessage(@PathVariable Long id, @Valid @RequestBody ContactMessage contactMessage) {
        contactMessageService.updateContactMessage(id, contactMessage);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Contact Message successfully updated");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    // http://localhost:8080/contactmessage/2
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteContactMessage(@PathVariable Long id) {
        contactMessageService.deleteContactMessage(id);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Contact Message successfully deleted");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ContactMessage>> getAllWithPage(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("sort") String prop, @RequestParam("direction") Sort.Direction direction) {

        Pageable pageable=PageRequest.of(page, size, Sort.by(direction,prop));
        Page<ContactMessage> contactMessagePage = contactMessageService.getAllWithPage(pageable);

        return ResponseEntity.ok(contactMessagePage);
    }

    // Sample for getting list of contact messages with requested name
    @GetMapping("/isim/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactMessage>> getAllListByName(@PathVariable("name") String name) {
        List<ContactMessage> tumListe = contactMessageService.getByName(name);
        return ResponseEntity.ok(tumListe);
    }


}
