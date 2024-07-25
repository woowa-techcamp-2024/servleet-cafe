package com.wootecam.jspcafe.repository;

import com.wootecam.jspcafe.model.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class QuestionRepository {

    private final Map<Long, Question> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Long generateId() {
        return idGenerator.getAndIncrement();
    }

    public void save(final Question question) {
        store.put(question.getId(), question);
    }

    public List<Question> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Question> findById(final Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
