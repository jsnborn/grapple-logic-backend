package com.grapplelogic.engine.domain;

public enum GrapplingClass {
    // Numbers in the parentheses are passed to the constructor below
    // Stats: Takedown, TDD (Defense), Top, Bottom, Submission
    BJJ(3, 4, 7, 10, 9),
    WRESTLER(10, 9, 8, 2, 3),
    JUDOKA(9, 7, 5, 4, 6);

    private final int takedown;
    private final int takedownDefense;
    private final int groundTop;
    private final int groundBottom;
    private final int submission;

    GrapplingClass(int td, int tdd, int top, int bottom, int sub) {
        this.takedown = td;
        this.takedownDefense = tdd;
        this.groundTop = top;
        this.groundBottom = bottom;
        this.submission = sub;
    }

    // Getters for the Service Layer to access stats
    public int getTakedown() { return takedown; }
    public int getTakedownDefense() { return takedownDefense; }
    public int getGroundTop() { return groundTop; }
    public int getGroundBottom() { return groundBottom; }
    public int getSubmission() { return submission; }


}
