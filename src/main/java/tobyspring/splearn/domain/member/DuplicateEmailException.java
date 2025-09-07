package tobyspring.splearn.domain.member;

// @ResponseStatus(HttpStatus.CONFLICT) // 이것도 가능! 하지만 메세지같은 것을 담을 수 없어
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
