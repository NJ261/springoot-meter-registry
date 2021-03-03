package com.example.emailservice.rest.controller.email;

import com.example.emailservice.Keymatrics;
import com.example.emailservice.domain.email.service.EmailService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by mtumilowicz on 2018-07-28.
 */
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("emails")
public class EmailController {
    
    EmailService emailService;
    
//    example json
//    {
//            "mtumilowicz": "4mtumilowicz",
//            "hank": "4hank",
//            "fjodor": "4fjodor",
//            "ernie": "4ernie",
//            "non-existing-user": "4non-existing-user"
//    }

    @GetMapping("send/async")
    public ResponseEntity<String> asyncSend() throws InterruptedException {
        long startTime = System.nanoTime();
        MeterRegistry meterRegistry = new SimpleMeterRegistry();
        Keymatrics keymatrics = new Keymatrics();

//        List<CompletableFuture<String>> completableFutures = loginMessageMap
//                .entrySet()
//                .stream()
//                .map(entry -> emailService.asyncSend(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//
//        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[]{})).join();
        Random random = new Random();
        Thread.sleep((random.nextInt(25)) * 100);
//        meterRegistry.timer("request-time").record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
//        System.out.println("\n\n" + meterRegistry.get("request-time").timer().totalTime(TimeUnit.NANOSECONDS) + "\n\n" + meterRegistry.get("request-time").timer().takeSnapshot());
        keymatrics.getTotalTime(new SimpleMeterRegistry(), startTime);
        return ResponseEntity.ok("okay");
    }

    @PostMapping("send")
    public ResponseEntity<List<String>> send(@RequestBody Map<String, String> loginMessageMap) {
        return ResponseEntity.ok(loginMessageMap
                .entrySet()
                .stream()
                .map(entry -> emailService.send(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()));
    }

    @GetMapping("health")
    public void health() {

    }
}
