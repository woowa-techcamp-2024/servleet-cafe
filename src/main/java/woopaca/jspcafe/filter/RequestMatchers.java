package woopaca.jspcafe.filter;

import java.util.HashSet;
import java.util.Set;

public class RequestMatchers {

    private final Set<RequestMatcher> includeMatchers = new HashSet<>();

    public void addMatcher(String method, String path) {
        includeMatchers.add(new RequestMatcher(method, path));
    }

    public boolean contains(String method, String uri) {
        return includeMatchers.stream()
                .anyMatch(matcher -> matcher.matches(method, uri));
    }
}
