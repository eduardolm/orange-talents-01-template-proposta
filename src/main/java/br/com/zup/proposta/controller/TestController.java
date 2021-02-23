package br.com.zup.proposta.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cartoes")
public class TestController {

    @GetMapping("/agent")
    public ResponseEntity<?> getAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String remoteIp = request.getRemoteAddr();
        Map<String, String> result = new HashMap<>();
        result.put("User-Agent", userAgent);
        result.put("Remote_Addr", remoteIp);
        return ResponseEntity.ok(result);
    }
}
