package tobyspring.splearn.adapter;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tobyspring.splearn.domain.member.DuplicateEmailException;
import tobyspring.splearn.domain.member.DuplicateProfileException;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    /**
     * @param exception
     * @return
     * MockHttpServletResponse:
     *            Status = 409
     *     Error message = null
     *           Headers = [Content-Type:"application/problem+json"]
     *      Content type = application/problem+json
     *              Body = {"type":"about:blank","title":"Conflict","status":409,"detail":"이미 사용중인 이메일입니다: toby@splearn.app","instance":"/api/members","exception":"DuplicateEmailException","timestamp":"2025-09-07T16:10:44.224625"}
     *     Forwarded URL = null
     *    Redirected URL = null
     *           Cookies = []
     */
    @ExceptionHandler({DuplicateEmailException.class, DuplicateProfileException.class})
    public ProblemDetail emailExceptionHandler(DuplicateEmailException exception) {
        return getProblemDetail(HttpStatus.CONFLICT, exception);
    }

    private static ProblemDetail getProblemDetail(HttpStatus status, Exception exception) {
        // HTTP 응답코드만 가지고는 여러 표현들을 나타낼 방법이 없어!
        // {status:, error:, data:, ...} 같은 형식이 이제 필요가 없어.
        // RFC9457 표준

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setProperty("exception", exception.getClass().getSimpleName());
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }
}
