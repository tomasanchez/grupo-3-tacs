package com.grupo3.meetings.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;

public class OptionTests {
    private Event event;
    private Option option1;
    private Option option2;
    private Option option3;
    private User mario;
    private User luigi;
    private User peach;

    @BeforeEach
    public void setUp() {
        mario = new User("m1", "Mario");
        luigi = new User("l1", "Luigi");
        peach = new User("p1", "Peach");

        option1 = new Option(DayOfWeek.MONDAY, LocalTime.of(10, 0));
        option2 = new Option(DayOfWeek.MONDAY, LocalTime.of(11, 0));
        option3 = new Option(DayOfWeek.MONDAY, LocalTime.of(12, 0));

        event = new Event("Pelicula Mario",
                "Juntada pelicula Mario",
                "Abasto",
                mario,
                new HashSet<>(Arrays.asList(option1, option2, option3))
        );
    }

    @Test
    void userCanVoteMultipleOptions() {
        event.vote(option1, mario);
        event.vote(option2, mario);
        Assertions.assertEquals(1, option1.getVotes());
        Assertions.assertEquals(1, option2.getVotes());
    }

    @Test
    void votingTwiceRemovesTheVote() {
        option1.toggleVote(mario.getId());
        option1.toggleVote(mario.getId());
        Assertions.assertEquals(0, option1.getVotes());
    }
}