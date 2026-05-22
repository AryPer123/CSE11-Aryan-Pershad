/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the User class for the Simple Reddit assignment.
 * A User has a username, a karma score, and lists of posts they have
 * authored, upvoted, and downvoted.
 */

import java.util.ArrayList;

/**
 * Represents a user of the Simple Reddit program. Each User has a
 * username, a karma score, and lists of posts they have authored,
 * upvoted, and downvoted.
 */
public class User {
    private static final String USER_FORMAT = "u/%s Karma: %d";

    private int karma;
    private String username;
    private ArrayList<Post> posts;
    private ArrayList<Post> upvoted;
    private ArrayList<Post> downvoted;

    /**
     * Constructor for a User. The vote and post lists begin empty and
     * karma starts at 0.
     *
     * @param username the username for this User
     */
    public User(String username) {
        this.username = username;
        this.karma = 0;
        this.posts = new ArrayList<Post>();
        this.upvoted = new ArrayList<Post>();
        this.downvoted = new ArrayList<Post>();
    }

    /**
     * Adds a post to this User's list of posts and refreshes karma. If
     * the post is null, the list is not changed.
     *
     * @param post the Post authored by this User to record
     */
    public void addPost(Post post) {
        if (post == null) {
            return;
        }
        posts.add(post);
        updateKarma();
    }

    /**
     * Recalculates this User's karma as the sum of
     * (upvoteCount - downvoteCount) across every post they have made.
     */
    public void updateKarma() {
        int total = 0;
        for (int i = 0; i < posts.size(); i++) {
            Post p = posts.get(i);
            total += p.getUpvoteCount() - p.getDownvoteCount();
        }
        karma = total;
    }

    /**
     * @return the current karma value for this User
     */
    public int getKarma() {
        return karma;
    }

    /**
     * Upvotes a post on behalf of this User. The call is ignored if the
     * post is null, this User is the author, or this User already
     * upvoted it. A previous downvote by this User is removed first.
     *
     * @param post the Post to upvote
     */
    public void upvote(Post post) {
        if (post == null) {
            return;
        }
        if (upvoted.contains(post)) {
            return;
        }
        if (post.getAuthor() == this) {
            return;
        }
        if (downvoted.contains(post)) {
            downvoted.remove(post);
            post.updateDownvoteCount(false);
        }
        upvoted.add(post);
        post.updateUpvoteCount(true);
        post.getAuthor().updateKarma();
    }

    /**
     * Downvotes a post on behalf of this User. The call is ignored if
     * the post is null, this User is the author, or this User already
     * downvoted it. A previous upvote by this User is removed first.
     *
     * @param post the Post to downvote
     */
    public void downvote(Post post) {
        if (post == null) {
            return;
        }
        if (downvoted.contains(post)) {
            return;
        }
        if (post.getAuthor() == this) {
            return;
        }
        if (upvoted.contains(post)) {
            upvoted.remove(post);
            post.updateUpvoteCount(false);
        }
        downvoted.add(post);
        post.updateDownvoteCount(true);
        post.getAuthor().updateKarma();
    }

    /**
     * Finds the original post with the greatest
     * (upvoteCount - downvoteCount) score among this User's posts.
     * Ties are broken by the lowest index in posts.
     *
     * @return the top original post, or null if none exists
     */
    public Post getTopPost() {
        Post top = null;
        int topScore = 0;
        for (int i = 0; i < posts.size(); i++) {
            Post p = posts.get(i);
            if (p.getReplyTo() != null) {
                continue;
            }
            int score = p.getUpvoteCount() - p.getDownvoteCount();
            if (top == null || score > topScore) {
                top = p;
                topScore = score;
            }
        }
        return top;
    }

    /**
     * Finds the comment with the greatest
     * (upvoteCount - downvoteCount) score among this User's posts.
     * Ties are broken by the lowest index in posts.
     *
     * @return the top comment, or null if none exists
     */
    public Post getTopComment() {
        Post top = null;
        int topScore = 0;
        for (int i = 0; i < posts.size(); i++) {
            Post p = posts.get(i);
            if (p.getReplyTo() == null) {
                continue;
            }
            int score = p.getUpvoteCount() - p.getDownvoteCount();
            if (top == null || score > topScore) {
                top = p;
                topScore = score;
            }
        }
        return top;
    }

    /**
     * @return the list of all posts (originals and comments) authored
     *         by this User
     */
    public ArrayList<Post> getPosts() {
        return posts;
    }

    /**
     * @return a formatted String with this User's username and karma
     */
    public String toString() {
        return String.format(USER_FORMAT, username, karma);
    }
}
