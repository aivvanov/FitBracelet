package fit_bracelet.service;

import fit_bracelet.model.Move;
import fit_bracelet.model.Steps;
import fit_bracelet.model.User;
import fit_bracelet.repos.MoveRepo;
import fit_bracelet.repos.StepsRepo;
import fit_bracelet.repos.UserRepo;
import fit_bracelet.rest.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepo userRepo;
    private final StepsRepo stepsRepo;
    private final MoveRepo moveRepo;

    public fit_bracelet.model.User addUser(UserInfo userDto) {
        fit_bracelet.model.User user = new fit_bracelet.model.User();
        user.setLogin(userDto.getUserName());
        user.setFirstName(userDto.getFirstName());
        user.setSurName(userDto.getSurName());
        user.setAge(userDto.getAge());
        user.setWeight(userDto.getWeight());
        user.setHeight(userDto.getHeight());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setSex(userDto.getSex());
        user.setHashPassword(userDto.getPassword().hashCode());
        return userRepo.save(user);
    }

    public Integer getSteps(String token) {
        fit_bracelet.model.User user = checkToken(token);
        Optional<Steps> stepData = user.getStepsData().stream().filter(steps -> steps.getDate().equals(LocalDate.now())).findFirst();
        return stepData.isPresent() ? stepData.get().getStepsCount() : 0;
    }

    public Integer getMoves(String token) {
        User user = checkToken(token);
        Optional<Move> moveData = user.getMoveData().stream().filter(move -> move.getDate().equals(LocalDate.now())).findFirst();
        return moveData.isPresent() ? moveData.get().getMovesCount() : 0;
    }

    @Transactional
    public void updateMovesCount(String token, Integer moveAmount) {
        fit_bracelet.model.User user = checkToken(token);
        Optional<Move> moveData = user.getMoveData().stream().filter(move -> move.getDate().equals(LocalDate.now())).findFirst();
        if (moveData.isEmpty()) {
            Move move = new Move();
            move.setDate(LocalDate.now());
            move.setMovesCount(moveAmount);
            user.getMoveData().add(move);
            move.setUser(user);
            moveRepo.save(move);
        } else {
            Integer currentAmount = moveData.get().getMovesCount();
            moveData.get().setMovesCount(currentAmount + moveAmount);
        }
        userRepo.save(user);
    }

    @Transactional
    public void updateStepsCount(String token, Integer stepAmount) {
        fit_bracelet.model.User user = checkToken(token);
        Optional<Steps> stepData = user.getStepsData().stream().filter(steps -> steps.getDate().equals(LocalDate.now())).findFirst();
        if (stepData.isEmpty()) {
            Steps steps = new Steps();
            steps.setDate(LocalDate.now());
            steps.setStepsCount(stepAmount);
            user.getStepsData().add(steps);
            steps.setUser(user);
            stepsRepo.save(steps);
        } else {
            Integer currentAmount = stepData.get().getStepsCount();
            stepData.get().setStepsCount(currentAmount + stepAmount);
        }
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(String token) {
        fit_bracelet.model.User user = checkToken(token);
        userRepo.deleteById(user.getId());
    }


    private fit_bracelet.model.User checkToken(String token) {
        String[] tokenData = token.split("\\.");
        assert tokenData.length == 2 : "Invalid Token";
        String login = tokenData[0];
        fit_bracelet.model.User user = userRepo.findByLogin(login);
        assert user != null : "No such user";
        assert user.getHashPassword() == Integer.parseInt(tokenData[1]) : "Wrong password";
        return user;
    }
}
