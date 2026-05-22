/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains a Tester for PA6 Simple Reddit. The main method
 * exercises the methods of Post and User and prints results so the
 * behavior can be verified by inspection.
 */

import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) {
        User u1 = new User("alice");
        User u2 = new User("bob");
        User u3 = new User("carol");
        User u4 = new User("dave");

        System.out.println(u1);
        System.out.println("u1 starting karma: " + u1.getKarma());
        System.out.println("u1 starting posts: " + u1.getPosts().size());

        u1.addPost(null);
        System.out.println("u1 posts after null add: "
                + u1.getPosts().size());

        Post p1 = new Post("First Title", "First Content", u1);
        Post p2 = new Post("Second Title", "Second Content", u1);
        Post c1 = new Post("First Comment", p1, u2);
        Post c2 = new Post("Reply To Comment", c1, u3);

        u1.addPost(p1);
        u1.addPost(p2);
        u2.addPost(c1);
        u3.addPost(c2);

        System.out.println("p1 title: " + p1.getTitle());
        System.out.println("c1 title (null expected): " + c1.getTitle());
        System.out.println("p1 replyTo (null expected): " + p1.getReplyTo());
        System.out.println("c1 replyTo title: "
                + c1.getReplyTo().getTitle());
        System.out.println("p1 author: " + p1.getAuthor());
        System.out.println("p1 upvotes: " + p1.getUpvoteCount());
        System.out.println("p1 downvotes: " + p1.getDownvoteCount());

        System.out.println(p1);
        System.out.println(c1);

        ArrayList<Post> threadP1 = p1.getThread();
        System.out.println("p1.getThread size: " + threadP1.size());
        ArrayList<Post> threadC2 = c2.getThread();
        System.out.println("c2.getThread size: " + threadC2.size());
        System.out.println("c2.getThread[0] title: "
                + threadC2.get(0).getTitle());
        System.out.println("c2.getThread[2] is c2: "
                + (threadC2.get(2) == c2));

        p1.updateUpvoteCount(true);
        System.out.println("p1 upvotes after inc: " + p1.getUpvoteCount());
        p1.updateUpvoteCount(false);
        System.out.println("p1 upvotes after dec: " + p1.getUpvoteCount());
        p1.updateDownvoteCount(true);
        System.out.println("p1 downvotes after inc: "
                + p1.getDownvoteCount());
        p1.updateDownvoteCount(false);
        System.out.println("p1 downvotes after dec: "
                + p1.getDownvoteCount());

        u2.upvote(p1);
        System.out.println("p1 upvotes after u2 upvote: "
                + p1.getUpvoteCount());
        System.out.println("u1 karma after u2 upvotes p1: "
                + u1.getKarma());

        u2.upvote(p1);
        System.out.println("p1 upvotes after duplicate upvote: "
                + p1.getUpvoteCount());

        u1.upvote(p1);
        System.out.println("p1 upvotes after self upvote: "
                + p1.getUpvoteCount());

        u2.upvote(null);
        System.out.println("u2 unchanged after null upvote: "
                + p1.getUpvoteCount());

        u3.downvote(p1);
        System.out.println("p1 downvotes after u3 downvote: "
                + p1.getDownvoteCount());
        System.out.println("u1 karma after u3 downvotes p1: "
                + u1.getKarma());

        u3.downvote(p1);
        System.out.println("p1 downvotes after duplicate downvote: "
                + p1.getDownvoteCount());
        u1.downvote(p1);
        System.out.println("p1 downvotes after self downvote: "
                + p1.getDownvoteCount());
        u3.downvote(null);

        u2.upvote(p2);
        System.out.println("p2 upvotes after u2 upvote: "
                + p2.getUpvoteCount());
        u2.downvote(p2);
        System.out.println("p2 upvotes after switch to downvote: "
                + p2.getUpvoteCount());
        System.out.println("p2 downvotes after switch: "
                + p2.getDownvoteCount());
        u2.upvote(p2);
        System.out.println("p2 upvotes after switch back: "
                + p2.getUpvoteCount());
        System.out.println("p2 downvotes after switch back: "
                + p2.getDownvoteCount());

        System.out.println("u1 final karma: " + u1.getKarma());
        System.out.println("u1 top post: " + u1.getTopPost().getTitle());
        System.out.println("u2 top comment: "
                + u2.getTopComment());

        System.out.println("u4 top post (null expected): "
                + u4.getTopPost());
        System.out.println("u4 top comment (null expected): "
                + u4.getTopComment());

        Post p4 = new Post("Dave's Post", "Hi", u4);
        u4.addPost(p4);
        System.out.println("u4 top comment after only post: "
                + u4.getTopComment());
        System.out.println("u4 top post after only post: "
                + u4.getTopPost().getTitle());

        Post c4 = new Post("Dave's Comment", p1, u4);
        u4.addPost(c4);
        System.out.println("u4 top comment after adding comment: "
                + u4.getTopComment().getReplyTo().getTitle());

        System.out.println("u1 posts size: " + u1.getPosts().size());

        User u5 = new User("eve");
        Post p6 = new Post("First Tie", "x", u5);
        Post p7 = new Post("Second Tie", "x", u5);
        u5.addPost(p6);
        u5.addPost(p7);
        System.out.println("u5 top post on tie returns p6: "
                + (u5.getTopPost() == p6));

        Post c5 = new Post("Tie Comment", p1, u2);
        u2.addPost(c5);
        System.out.println("u2 top comment on tie returns c1: "
                + (u2.getTopComment() == c1));
    }
}
