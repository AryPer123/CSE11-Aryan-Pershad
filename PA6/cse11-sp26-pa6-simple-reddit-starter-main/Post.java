/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains the Post class for the Simple Reddit assignment.
 * A Post can be either an original post or a comment reply, depending
 * on which constructor is used.
 */

import java.util.ArrayList;

/**
 * Represents a Reddit post, either an original post or a comment reply
 * to an existing post. Holds the title, content, parent (replyTo),
 * author, and upvote and downvote counts of the post.
 */
public class Post {
    private static final String TO_STRING_POST_FORMAT =
            "[%d|%d]\t%s\n\t%s";
    private static final String TO_STRING_COMMENT_FORMAT =
            "[%d|%d]\t%s";

    private String title;
    private String content;
    private Post replyTo;
    private User author;
    private int upvoteCount;
    private int downvoteCount;

    /**
     * Constructor for an original post. The author's own upvote is
     * counted by default.
     *
     * @param title   the title of the post
     * @param content the content of the post
     * @param author  the User who created this post
     */
    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.replyTo = null;
        this.upvoteCount = 1;
        this.downvoteCount = 0;
    }

    /**
     * Constructor for a comment reply.
     *
     * @param content the content of the comment
     * @param replyTo the Post this comment is replying to
     * @param author  the User who wrote this comment
     */
    public Post(String content, Post replyTo, User author) {
        this.title = null;
        this.content = content;
        this.replyTo = replyTo;
        this.author = author;
        this.upvoteCount = 1;
        this.downvoteCount = 0;
    }

    /**
     * @return the title of this post, or null if this Post is a comment
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the Post this Post is replying to, or null if this Post
     *         is an original post
     */
    public Post getReplyTo() {
        return replyTo;
    }

    /**
     * @return the User who authored this Post
     */
    public User getAuthor() {
        return author;
    }

    /**
     * @return the number of upvotes on this Post
     */
    public int getUpvoteCount() {
        return upvoteCount;
    }

    /**
     * @return the number of downvotes on this Post
     */
    public int getDownvoteCount() {
        return downvoteCount;
    }

    /**
     * Increments or decrements the upvote count by 1.
     *
     * @param isIncrement true to add an upvote, false to remove one
     */
    public void updateUpvoteCount(boolean isIncrement) {
        if (isIncrement) {
            upvoteCount++;
        } else {
            upvoteCount--;
        }
    }

    /**
     * Increments or decrements the downvote count by 1.
     *
     * @param isIncrement true to add a downvote, false to remove one
     */
    public void updateDownvoteCount(boolean isIncrement) {
        if (isIncrement) {
            downvoteCount++;
        } else {
            downvoteCount--;
        }
    }

    /**
     * Returns the chain of posts in this thread, beginning with the
     * original post at index 0 and ending with this Post at the last
     * index.
     *
     * @return an ArrayList of Posts in order from the original post to
     *         this post
     */
    public ArrayList<Post> getThread() {
        ArrayList<Post> thread;
        if (replyTo == null) {
            thread = new ArrayList<Post>();
        } else {
            thread = replyTo.getThread();
        }
        thread.add(this);
        return thread;
    }

    /**
     * Returns a formatted String for this Post. The format differs
     * between original posts and comment replies.
     *
     * @return the String representation of this Post
     */
    public String toString() {
        if (replyTo == null) {
            return String.format(TO_STRING_POST_FORMAT,
                    upvoteCount, downvoteCount, title, content);
        }
        return String.format(TO_STRING_COMMENT_FORMAT,
                upvoteCount, downvoteCount, content);
    }
}
