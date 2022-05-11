package fit_bracelet.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fit_bracelet.rest.user.UserInfo;
import fit_bracelet.service.UserService;

@RestController
@RequestMapping(("/api"))
@RequiredArgsConstructor
public class ApiController {
    private static final String TOKEN_HEADER = "token";

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody UserInfo userDto) {
        fit_bracelet.model.User user = userService.addUser(userDto);
        return ResponseEntity.ok().body(String.format("token: %s.%s", user.getLogin(), user.getHashPassword()));
    }

    @GetMapping("/steps")
    public ResponseEntity<String> getSteps(@RequestHeader(TOKEN_HEADER) String token) {
        Integer steps = userService.getSteps(token);
        return ResponseEntity.ok().body(String.format("Your steps are %s steps", steps));
    }
    @PutMapping("/steps")
    public ResponseEntity<Void> updateStepsCount(@RequestHeader(TOKEN_HEADER) String token, @RequestParam Integer stepCount) {
        userService.updateStepsCount(token, stepCount);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/moves")
    public ResponseEntity<String> getMoves(@RequestHeader(TOKEN_HEADER) String token) {
        Integer moves = userService.getMoves(token);
        return ResponseEntity.ok().body(String.format("Your moves are %s kcl", moves));
    }
    @PutMapping("/moves")
    public ResponseEntity<Void> updateMovesCount(@RequestHeader(TOKEN_HEADER) String token, @RequestParam Integer moveCount) {
        userService.updateMovesCount(token, moveCount);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@RequestHeader(TOKEN_HEADER) String token) {
        userService.deleteUser(token);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AssertionError.class)
    public ResponseEntity<String> handleAssertionError(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}
