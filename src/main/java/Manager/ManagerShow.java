package Manager;

import DataBase.DataBase;
import component.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManagerShow {

    private static int num = 0;

    public static void closeUpLine(String edgeCharacter, int lengthOfDisplay) {
        for (int i = 0; i < lengthOfDisplay; i++) {
            System.out.print(edgeCharacter);
        }
        System.out.println("");
    }
    public static void inMiddleOfLine(String content, String edgeCharacter, int lengthOfDisplay) { //baraye dorost namayesh dadan
        System.out.print(edgeCharacter);
        for (int i = 0; i < (lengthOfDisplay - 2) / 2 - content.length() / 2; i++) {
            System.out.print(" ");
        }
        System.out.print(content);
        for (int i = 0; i < (lengthOfDisplay - 2) / 2 - Math.round(content.length() / 2.0); i++) {
            System.out.print(" ");
        }
        System.out.print(edgeCharacter + "\n");
    }
    public static void inLeftOfLine(String content, String edgeCharacter, int lengthOfDisplay) { //baraye dorost namayesh dadan
        String[] words = content.split(" ");
        String output = "";
        int wordPut = 0;
//        System.out.println("1");
        while (wordPut != words.length) { //khat ha ra tey mikonad
//            System.out.println("2");
            int numOfWordsInThisLine = 0;
            int numOfCharacterInThisLine = 4;  // | ..... |
            output += edgeCharacter + " ";
            while (numOfCharacterInThisLine < lengthOfDisplay) {
//                System.out.println("3");
//                System.out.print(output);
                if (wordPut == words.length) {
                    while (numOfCharacterInThisLine != lengthOfDisplay) {
                        output += " ";
                        numOfCharacterInThisLine++;
                    }
                } else if (words[wordPut].length() <= lengthOfDisplay - numOfCharacterInThisLine) {
                    output += words[wordPut];
                    numOfCharacterInThisLine += words[wordPut].length();
                    if (numOfCharacterInThisLine != lengthOfDisplay) {
                        output += " ";
                        numOfCharacterInThisLine++;
                    }
                    wordPut++;
                } else if (words[wordPut].length() <= lengthOfDisplay - 4) {
                    while (numOfCharacterInThisLine != lengthOfDisplay) {
                        output += " ";
                        numOfCharacterInThisLine++;
                    }
                } else if (words[wordPut].length() > lengthOfDisplay - 4) { //big String
                    if (lengthOfDisplay - numOfCharacterInThisLine > 7) {
//                        System.out.println(4);
                        String big = words[wordPut];
                        int indexOfBig = 0;
                        boolean firstline = true;
//                        System.out.println("big.length() : "+big.length());
                        while (indexOfBig != big.length()) {
                            int BigInThisLine = 0;
//                            System.out.println(indexOfBig);
                            if ((big.length() - indexOfBig > lengthOfDisplay - 4 && firstline) || (big.length() - indexOfBig > lengthOfDisplay - 9) && !firstline) {
                                BigInThisLine = lengthOfDisplay - numOfCharacterInThisLine - 5;
                                output += big.substring(indexOfBig, indexOfBig + BigInThisLine);
                                numOfCharacterInThisLine = 9;
                                indexOfBig += BigInThisLine;
                                output += "(...) " + edgeCharacter + "\n" + edgeCharacter + " (...)";
                            } else {
                                BigInThisLine = big.length() - indexOfBig;
                                output += big.substring(indexOfBig, indexOfBig + BigInThisLine) + " ";
                                indexOfBig += BigInThisLine;
                                numOfCharacterInThisLine = 10 + BigInThisLine;
                            }
                            firstline = false;
                        }
                        wordPut++;
                    } else {
                        while (numOfCharacterInThisLine != lengthOfDisplay) {
                            output += " ";
                            numOfCharacterInThisLine++;
                        }
                    }
                }
            }
            output += " " + edgeCharacter + "\n";
        }
        System.out.print(output);
    }
    public static void inRightOfLine(String content, String edgeCharacter, int lengthOfDisplay) {
        int fromLeft = lengthOfDisplay - content.length() - 4;
        for (int i = 0; i < fromLeft; i++) {
            content = " " + content;
        }
        inLeftOfLine(content, edgeCharacter, lengthOfDisplay);
    }
    public static void closeDownLine(String edgeCharacter, String betweenCharacter, int lengthOfDisplay) {
        System.out.print(edgeCharacter);
        for (int i = 0; i < lengthOfDisplay - 2; i++) {
            System.out.print(betweenCharacter);
        }
        System.out.println(edgeCharacter);
    }
    public static void boxInMiddle(String content, String edgeCharacter, String betweenCharacter, int lengthOfDisplay) {
        closeDownLine(edgeCharacter, betweenCharacter, lengthOfDisplay);
        inMiddleOfLine(content, edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, betweenCharacter, lengthOfDisplay);
    }
    public static void boxInLeft(String content, String edgeCharacter, String betweenCharacter, int lengthOfDisplay) {
        closeDownLine(edgeCharacter,betweenCharacter,lengthOfDisplay);
        inLeftOfLine(content, edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter,betweenCharacter,lengthOfDisplay);
    }
    public static void showDate(LocalDateTime Date) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inMiddleOfLine(Date.format(DateTimeFormatter.ofPattern("| LLLL d |")), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
    }
    public static void showEmpty(String edgeCharacter, int lengthOfDisplay) {
        inLeftOfLine("", edgeCharacter, lengthOfDisplay);
    }
    public static void showHomePageMain(String userName, String type, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        if (type.equalsIgnoreCase("normal")) {
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inLeftOfLine("*" + userName + "*", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("pls select your choice:", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("1-Home", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-MainPage(following)", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Group (" + totalNotReadGroup(user) + ")", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("4-Pv (" + totalNotReadPv(user) + ")", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Explore", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("6-followers", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("7-followings", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("8-setting", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("9-logout", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("10-saved Message", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        } else if (type.equalsIgnoreCase("Business")) {
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inLeftOfLine("$$" + userName + "$$", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("pls select your choice:", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("1-Home", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-MainPage(following)", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Group (" + totalNotReadGroup(user) + ")", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("4-Pv ("+totalNotReadPv(user)+")", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Explore", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("6-followers", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("7-followings", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("8-setting", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("9-Analyse Page", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("10-logout", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("11-saved Message", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        System.out.println("input: ");
    }

    private static int totalNotReadGroup(User user) {
        int n = 0;
        for (int i = 0; i < user.getGroups().size(); i++) {
            n += user.getGroups().get(i).getMessages().size() - user.getReadMessageGroup().get(i);
        }
        return n;
    }
    public static void showPvs(User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("*My Pvs*", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("pls select your choice:", edgeCharacter, lengthOfDisplay);
        showEmpty(edgeCharacter, lengthOfDisplay);
        if (user.getLinkedPvs().size() == 0) {
            inMiddleOfLine("you have'nt Pvs :(", edgeCharacter, lengthOfDisplay);
            showEmpty(edgeCharacter, lengthOfDisplay);
        } else {
            for (int i = 0; i < user.getLinkedPvs().size(); i++) {
                if (user.getLinkedPvs().get(user.getPvs().get(i)) == user) {
                    inLeftOfLine("" + 0 + "-" + "Saved Message", edgeCharacter, lengthOfDisplay);
                }
            }
            int q = 0;
            for (int i = 0; i < user.getLinkedPvs().size(); i++) {
                if (user.getLinkedPvs().get(user.getPvs().get(i)) == user) {
                    q = -1;
                } else {
                    inLeftOfLine("" + (i + 1 + q) + "-" + user.getLinkedPvs().get(user.getPvs().get(i)).getUserName() + " |" + (user.getPvs().get(i).getMessages().size() - user.getReadMessagePv().get(i)) + "|", edgeCharacter, lengthOfDisplay);
                }
            }
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        showPvsChoicesIn(user);
    }
    private static void showPvsChoicesIn(User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Select :", edgeCharacter, lengthOfDisplay);
        if (user.getPvs().size() == 0) {
            inLeftOfLine("1-New Pv", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        } else {
            inLeftOfLine("1-Select a Pv", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-New Pv ((Or Find Pv))", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Back", edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input :");
    }
    public static void showExploreOtherPageChoicesIn(Boolean Follow, boolean showUserRecommendation, ArrayList<User> recommendedUsers) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inLeftOfLine("1-Select (ie: select post To like it, to leave down a comment,...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Next Page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-previous page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check follower users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("5-Check following users", edgeCharacter, lengthOfDisplay);
        if (!Follow) {
            inLeftOfLine("6-Follow this user", edgeCharacter, lengthOfDisplay);
        } else {
            inLeftOfLine("6-UnFollow this user", edgeCharacter, lengthOfDisplay);
        }
        inLeftOfLine("7-Send Message", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("8-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (showUserRecommendation){
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inMiddleOfLine("Recommended Users", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);

            if (recommendedUsers.size() == 0) {
                inMiddleOfLine("There is no another User", edgeCharacter, lengthOfDisplay);
            } else {
                for (int i = 0; i < recommendedUsers.size(); i++) {
                    inLeftOfLine("User#" + (i + 1) + " : "+ recommendedUsers.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
                }
                closeDownLine(edgeCharacter, "_", lengthOfDisplay);
            }
            inLeftOfLine("9-Select (ie: select a recommended User to visit this User's page)", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }


        System.out.print("input: ");
    }
    public static void showExploreOtherPageTop(User user, boolean showUserRecommendation, ArrayList<User> recommendedUsers) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Another User Page", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("UserName : " + user.getUserName() + "    @" + user.getId(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Bio : " + user.getBio(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Num of followers : " + user.getFollowers().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Num of followinngs : " + user.getFollowings().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.println("");
    }
    public static void showMypageSetting(User user, ArrayList<Post> posts) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("My Setting Page", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("UserName : " + user.getUserName() + "    @" + user.getId(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Bio : " + user.getBio(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        inLeftOfLine("Password : " + user.getPassword(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Password Hint : " + user.getPasswordHint(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Password Question : " + user.getQuestion(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Answer of Password Question : " + user.getAnsQuestion(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("User Type : " + user.getType(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        System.out.println("");
    }
    public static void showSettingPageChoicesIn() {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inLeftOfLine("1-Change User Name", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Change ID", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-change Bio", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Change Password", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("5-Change Password Hint", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Change Password Question", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("7-Change Answer of Password Question", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("8-Change User Type", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("9-Change Profile Image (coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("10-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input: ");
    }
    public static void showPost(Post post) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        inLeftOfLine("Likes:" + post.getLikes().size() + "   views:" + post.getViews().size() + "       " + post.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm a")), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
    }
    public static void showPv(Pv pv, User user, int nPage, int postInOnePage) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inLeftOfLine(ManagerPv.setName(pv, user), edgeCharacter, lengthOfDisplay);
        ManagerPv.showMessages(pv, user, nPage, postInOnePage);
    }
    public static void showMessage(Message message, Boolean me) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        String o = message.getSender().getUserName();
        if (me) {
            o = "[" + o + "]";
        }
        o += ": ";
        if (message.getReply()) {
//            System.out.println("show reply");
            String reply = message.getReplyMessage().getSender().getUserName() + ": " + message.getReplyMessage().getContent().substring(0, Math.min(10, message.getReplyMessage().getContent().length()));
            if (reply.length() < message.getReplyMessage().getContent().length()) {
                reply += "...";
            }
            inLeftOfLine("Reply too " + reply, edgeCharacter, lengthOfDisplay);
        }
        if (message.getForward()) {
            inLeftOfLine("Forwarded From { " + message.getForwardFrom().getUserName() + " } :", edgeCharacter, lengthOfDisplay);
        }
        inLeftOfLine(o, edgeCharacter, lengthOfDisplay);
        inLeftOfLine(message.getContent(), edgeCharacter, lengthOfDisplay);
        String edited = "";
        if (message.getEdited()) {
            edited = "edited";
        }
        inRightOfLine(edited + " " + message.getDate().format(DateTimeFormatter.ofPattern("HH:mm")), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
    }
    public static void showPvChoicesIn(Pv pv, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        if (pv.getBlock()) {
            String Block = new String();
            if (pv.getBlocker() == user) {
                Block = "* you Blocked This User :(";
            } else {
                Block = "* this user Blocked you :((";
            }
            inMiddleOfLine(Block, edgeCharacter, lengthOfDisplay);
            inLeftOfLine("1-Next Page", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-previous page", edgeCharacter, lengthOfDisplay);
            if (pv.getBlocker() == user) {
                inLeftOfLine("3-UnBlock", edgeCharacter, lengthOfDisplay);
            } else {
                inLeftOfLine("3-UnBlock(Disable)", edgeCharacter, lengthOfDisplay);
            }
            inLeftOfLine("4-Search", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Delete Chat", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("6-Back", edgeCharacter, lengthOfDisplay);
        } else {
            inLeftOfLine("1-Send Message", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-Select (ie: select message To forward it, to reply a message,...)", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Next Page", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("4-previous page", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Block", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("6-Search", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("7-Delete Chat", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("8-Back", edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input: ");
    }

    public static void showSendMessagePage() {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("SendMessage", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("1-Text", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Image(Coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Video(Coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.println("input: ");
    }
    public static void showSelectMessage() {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Select", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("Well,Please enter Message number to select this Message directly :", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
    }
    public static void showSelectMessageChoices(Boolean forMe,Boolean forwarded) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        if (forMe) {
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inMiddleOfLine("select your message", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("1-forward", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-reply", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-delete", edgeCharacter, lengthOfDisplay);
            if (!forwarded) {
                inLeftOfLine("4-edit", edgeCharacter, lengthOfDisplay);
                inLeftOfLine("5-Back", edgeCharacter, lengthOfDisplay);
            }else {
                inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        } else {
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inMiddleOfLine("select other message", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("1-forward", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-reply", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Back", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
    }
    public static void showSendReplyMessagePage() {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("*SendReplyMessage*", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("1-Text", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Image(Coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Video(Coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
    }
    public static void showEditMessagePage(Message message) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Edit Message", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("Message: " + message.getContent(), edgeCharacter, lengthOfDisplay);
        inLeftOfLine("1-Text", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Image(Coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Video(Coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
//        System.out.println("input: ");
    }
    public static int totalNotReadPv(User user) {
        int n = 0;
        for (int i = 0; i < user.getPvs().size(); i++) {
            n += user.getPvs().get(i).getMessages().size() - user.getReadMessagePv().get(i);
        }
        return n;
    }
    public static void showForward(User forwarder, Message message) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Select Forward To ...", edgeCharacter, lengthOfDisplay);
        showEmpty(edgeCharacter, lengthOfDisplay);
        inLeftOfLine("1-SavedMessage", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Pvs", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Group", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine("_", edgeCharacter, lengthOfDisplay);
        System.out.println("Select Index Of your Choice:");
        System.out.print("input:");
    }
    public static void showExplorePage(int num, ArrayList<Post> posts, User user, ArrayList<Post> recommendedAds) throws SQLException, ClassNotFoundException {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Explore", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (posts.size() == 0) {
            inMiddleOfLine("There is no post", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i = num; i < num + 10 && i <posts.size(); i++) {
                posts.get(i).addViewToTable(user, posts.get(i).getPostId(), LocalDateTime.now());
                posts.get(i).getViews().put(user, LocalDateTime.now());
                //System.out.println("*****man nashod ba to bemonam ****");
                inLeftOfLine("Post#" + (i + 1) + " : "+" ( By "+ posts.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                showPost(posts.get(i));
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("1-Select (ie: select post To like it, to leave down a comment,...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Next Page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Search", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Recommended Ads", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (recommendedAds.size() == 0) {
            inMiddleOfLine("There is no Business Post", edgeCharacter, lengthOfDisplay);
        } else {
            for (int i = 0; i < recommendedAds.size(); i++) {
                recommendedAds.get(i).addViewToTable(user,  recommendedAds.get(i).getPostId(), LocalDateTime.now());
                recommendedAds.get(i).getViews().put(user, LocalDateTime.now());
                inLeftOfLine("Post#" + (i + 1) +" ( By "+ recommendedAds.get(i).getSender().getUserName()+" )" + " : "+ recommendedAds.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("5-Select (ie: select a recommended User to visit this User's page)", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        System.out.print("input : ");
    }
    public static void showHomePage(int num, DataBase dataBase, User user, ArrayList<User> recommendedUsers, ArrayList<Post> posts) throws SQLException, ClassNotFoundException {
        System.out.println("****bego chera nashod ba to bemonam****");
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("My Home Page", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (posts.size() == 0) {
            inMiddleOfLine("You haven't launched any post yet", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i = num; i < num + 10 && i < posts.size(); i++) {
                posts.get(i).addViewToTable(user,posts.get(i).getPostId(),LocalDateTime.now());
                posts.get(i).getViews().put(user, LocalDateTime.now());
                inLeftOfLine("Post#" + (i + 1)  , edgeCharacter, lengthOfDisplay);
                showPost(posts.get(i));
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("1-Select (ie: select post To like it, to leave down a comment,...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Next Page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Create post", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Recommended Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (recommendedUsers.size() == 0) {
            inMiddleOfLine("There is no another User", edgeCharacter, lengthOfDisplay);
        } else {
            for (int i = 0; i < recommendedUsers.size(); i++) {
                inLeftOfLine("User#" + (i + 1) + " : "+ recommendedUsers.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("5-Select (ie: select a recommended User to visit this User's page)", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        System.out.print("input : ");
    }
    public static void showMainFollowingPage(int num, ArrayList<Post> posts, User user) throws SQLException, ClassNotFoundException {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("My View.Main Page", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        if (posts.size() == 0) {
            inMiddleOfLine("There isn't any post", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i = num; i < num + 10 && i < posts.size(); i++) {
                posts.get(i).addViewToTable(user, posts.get(i).getPostId(), LocalDateTime.now());
                posts.get(i).getViews().put(user, LocalDateTime.now());
                inLeftOfLine("Post#" + (i + 1) + " : "+" ( By "+ posts.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                showPost(posts.get(i));
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("1-Select (ie: select post To like it, to leave down a comment,...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Next Page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Search", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showCreatePost() {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Create Post", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("1-Add Text", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Add Image(Coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Add Video(Coming soon...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showPostDetailsInHomePage(Post post) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post"+" ( By "+ post.getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        showPost(post);
        ArrayList<Comment> comments = new ArrayList<>();
        comments = post.getComments();
        if (comments.size() == 0) {
            System.out.println("There is no comment");
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : " +" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName() + " : " + comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.println("Num of likes : " + post.getLikes().size());
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.println("Num of views : " + post.getViews().size());
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like post", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Ban comments", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("7-Delete Post", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("8-Forward", edgeCharacter, lengthOfDisplay);
        if (post.getType().equals("Normal")) {
            inLeftOfLine("9-Back", edgeCharacter, lengthOfDisplay);

        } else {
            inLeftOfLine("9-Analyze Post", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("0-Back", edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showAnalyzePost(int num, Post post) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Analyze Post"+" ( By "+ post.getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        LinkedHashMap<User, LocalDateTime> viewsFromPost = post.getViews();
        List<User> keyList = new ArrayList(viewsFromPost.keySet());
        List<LocalDateTime> valueList = new ArrayList(viewsFromPost.values());
        Collections.reverse(keyList);
        Collections.reverse(valueList);
        if (keyList.size() > 0) {
            inMiddleOfLine("Viewed User", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
            inLeftOfLine("User Number  |  User Name  |  User Type  |  Date&Time  ", edgeCharacter, lengthOfDisplay);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            for (int i = 0; i <= num + 10 && i < keyList.size(); i++) {
                inLeftOfLine((i + 1) + " | " + keyList.get(i).getUserName() + " | " + keyList.get(i).getType() + " | " + dtf.format(valueList.get(i)), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        } else {
            inMiddleOfLine("No user has visited your post yet", edgeCharacter, lengthOfDisplay);
        }


        LinkedHashMap<User, LocalDateTime> likesToPost = post.getLikes();
        List<User> keyList2 = new ArrayList(likesToPost.keySet());
        List<LocalDateTime> valueList2 = new ArrayList(likesToPost.values());
        Collections.reverse(keyList2);
        Collections.reverse(valueList2);
        if (keyList2.size() > 0) {
            inMiddleOfLine("Liked User", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
            inLeftOfLine("User Number  |  User Name  |  User Type  |  Date&Time  ", edgeCharacter, lengthOfDisplay);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            for (int i = 0; i <= num + 10 && i < keyList2.size(); i++) {
                inLeftOfLine((i + 1) + " | " + keyList2.get(i).getUserName() + " | " + keyList2.get(i).getType() + " | " + dtf.format(valueList2.get(i)), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        } else {
            inMiddleOfLine("No user has liked your post yet", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }



        inLeftOfLine("1-Select (ie: select user To visit it's page and communicate with this user)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Next Page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showAnalyzePage(int num, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Analyze Page", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        LinkedHashMap<User, LocalDateTime> viewsFromPage = user.getViewsFromPage();
        List<User> keyList = new ArrayList(viewsFromPage.keySet());
        List<LocalDateTime> valueList = new ArrayList(viewsFromPage.values());
        Collections.reverse(keyList);
        Collections.reverse(valueList);
        if (keyList.size() > 0) {
            inLeftOfLine("User Number  |  User Name  |  User Type  |  Date&Time  ", edgeCharacter, lengthOfDisplay);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            for (int i = 0; i <= num + 10 && i < keyList.size(); i++) {
                inLeftOfLine((i + 1) + " | " + keyList.get(i).getUserName() + " | " + keyList.get(i).getType() + " | " + dtf.format(valueList.get(i)), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        } else {
            inMiddleOfLine("No user has visited your page yet", edgeCharacter, lengthOfDisplay);
        }
        inLeftOfLine("1-Select (ie: select user To visit it's page and communicte with this user)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Next Page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showPostInExplore(String index, DataBase dataBase, User user, Post post, User user2, boolean showUserRecommendation, ArrayList<User> recommendedUsers) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post"+" ( By "+ post.getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        ArrayList<Comment> comments = post.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }

        inLeftOfLine("Num of likes : "+post.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : "+post.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like post", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Visit this user page", edgeCharacter, lengthOfDisplay);

        if (!user.getFollowings().contains(user2)){
            inLeftOfLine("7-Follow this user", edgeCharacter, lengthOfDisplay);
        }else{
            inLeftOfLine("7-UnFollow this user", edgeCharacter, lengthOfDisplay);
        }
        inLeftOfLine("8-Forward", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("9-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        if (showUserRecommendation){
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inMiddleOfLine("Recommended Users", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);

            if (recommendedUsers.size() == 0) {
                inMiddleOfLine("There is no another User", edgeCharacter, lengthOfDisplay);
            } else {
                for (int i = 0; i < recommendedUsers.size(); i++) {
                    inLeftOfLine("User#" + (i + 1) + " : "+ recommendedUsers.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
                }
                closeDownLine(edgeCharacter, "_", lengthOfDisplay);
            }
            inLeftOfLine("10-Select (ie: select a recommended User to visit this User's page)", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        System.out.print("input : ");

    }
    public static void showPostInExplore2(String index, User user1, User user, DataBase dataBase, Post post) {


        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post"+" ( By "+ post.getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        ArrayList<Comment> comments = new ArrayList<>();
        comments = post.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }

        inLeftOfLine("Num of likes : " + post.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : " + post.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like post", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Forward", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("7-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showCommentInExplore(DataBase dataBase, Comment comment, User user2, User user, boolean showUserRecommendation, ArrayList<User> recommendedUsers) {


        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Comment" + " ( By "+ comment.getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine(comment.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);



        ArrayList<Comment> comments = new ArrayList<>();
        comments=comment.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }



        inLeftOfLine("Num of likes : " + comment.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : " + comment.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like comment", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Visit this user page", edgeCharacter, lengthOfDisplay);
        if (!user.getFollowings().contains(user2)){
            inLeftOfLine("7-Follow this user", edgeCharacter, lengthOfDisplay);
        }else{
            inLeftOfLine("7-UnFollow this user", edgeCharacter, lengthOfDisplay);

        }
        inLeftOfLine("8-back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (showUserRecommendation){
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inMiddleOfLine("Recommended Users", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);

            if (recommendedUsers.size() == 0) {
                inMiddleOfLine("There is no another User", edgeCharacter, lengthOfDisplay);
            } else {
                for (int i = 0; i < recommendedUsers.size(); i++) {
                    inLeftOfLine("User#" + (i + 1) + " : "+ recommendedUsers.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
                }
                closeDownLine(edgeCharacter, "_", lengthOfDisplay);
            }
            inLeftOfLine("9-Select (ie: select a recommended User to visit this User's page)", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }


        System.out.print("input : ");

    }
    public static void showCommentInExplore2(DataBase dataBase, User user, User user1, Comment comment) {


        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Comment", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine(comment.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        ArrayList<Comment> comments = new ArrayList<>();
        comments=comment.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
             }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("Num of likes : " + comment.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : " + comment.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like comment", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("6-Back", edgeCharacter, lengthOfDisplay);


        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
         System.out.print("input : ");

    }
    public static void showViewedUsersInExplore(List<User> keyList, Post post) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of Views : "+post.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showLikedUsersInExplore(List<User> keyList, Post post) {


        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of Likes : "+post.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");

    }
    public static void showFollowingsInExplore(User user1, List<User> keyList) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Following Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(user1.getUserName(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of followings : "+user1.getFollowings().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showFollowersInExplore(User user1, List<User> keyList) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Follower Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(user1.getUserName(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of followers : "+user1.getFollowers().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showFollowingsInFollowersFollowing(User user1, List<User> keyList) {


        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Following Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

         inLeftOfLine(user1.getUserName(), edgeCharacter, lengthOfDisplay);
         closeDownLine(edgeCharacter, "_", lengthOfDisplay);

         inLeftOfLine("Num of followings : "+user1.getFollowings().size(), edgeCharacter, lengthOfDisplay);
         closeDownLine(edgeCharacter, "_", lengthOfDisplay);

         for (int i=0;i<keyList.size();i++) {
             inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
         }
         closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");



    }
    public static void showFollowersInFollowersFollowings(User user1, List<User> keyList) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Follower Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(user1.getUserName(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of followers : "+user1.getFollowers().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showPostInShowFollowingPost(Post post, User user1, User user, boolean showUserRecommendation, ArrayList<User> recommendedUsers) {


        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post"+" ( By "+ post.getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        ArrayList<Comment> comments = new ArrayList<>();
        comments=post.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }

        inLeftOfLine("Num of likes : "+post.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : "+post.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like post", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Visit this user page", edgeCharacter, lengthOfDisplay);

        if (!user.getFollowings().contains(user1)){
            inLeftOfLine("7-Follow this user", edgeCharacter, lengthOfDisplay);
        }else{
            inLeftOfLine("7-UnFollow this user", edgeCharacter, lengthOfDisplay);
        }
        inLeftOfLine("8-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (showUserRecommendation){
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inMiddleOfLine("Recommended Users", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);

            if (recommendedUsers.size() == 0) {
                inMiddleOfLine("There is no another User", edgeCharacter, lengthOfDisplay);
            } else {
                for (int i = 0; i < recommendedUsers.size(); i++) {
                    inLeftOfLine("User#" + (i + 1) + " : "+ recommendedUsers.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
                }
                closeDownLine(edgeCharacter, "_", lengthOfDisplay);
            }
            inLeftOfLine("9-Select (ie: select a recommended User to visit this User's page)", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }


        System.out.print("input : ");

    }
    public static void showCommentInShowFollowingPost(Comment comment, User user1, User user, boolean showUserRecommendation, ArrayList<User> recommendedUsers) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Comment", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(comment.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        ArrayList<Comment> comments = new ArrayList<>();
        comments=comment.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("Num of likes : "+comment.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : "+comment.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like post", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Visit this user page", edgeCharacter, lengthOfDisplay);

        if (!user.getFollowings().contains(user1)){
            inLeftOfLine("7-Follow this user", edgeCharacter, lengthOfDisplay);
        }else{
            inLeftOfLine("7-UnFollow this user", edgeCharacter, lengthOfDisplay);
        }
        inLeftOfLine("8-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (showUserRecommendation){
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inMiddleOfLine("Recommended Users", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);

            if (recommendedUsers.size() == 0) {
                inMiddleOfLine("There is no another User", edgeCharacter, lengthOfDisplay);
            } else {
                for (int i = 0; i < recommendedUsers.size(); i++) {
                    inLeftOfLine("User#" + (i + 1) + " : "+ recommendedUsers.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
                }
                closeDownLine(edgeCharacter, "_", lengthOfDisplay);
            }
            inLeftOfLine("9-Select (ie: select a recommended User to visit this User's page)", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }


        System.out.print("input : ");

    }
    public static void showViewedUsersInShowFollowingPost(Post post, User user, List<User> keyList) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of Views : "+post.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showLikedUsersInShowFollowingPost(Post post, User user, List<User> keyList) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of Likes : "+post.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");    }
    public static void showOthersPageHelpInShowFollowingPost(User user1, User user, ArrayList<Post> posts, boolean Follow, boolean showUserRecommendation, ArrayList<User> recommendedUsers) throws SQLException, ClassNotFoundException {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Another User Page", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("UserName : " + user.getUserName() + "    @" + user.getId(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Bio : " + user.getBio(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Num of followers : " + user.getFollowers().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Num of followinngs : " + user.getFollowings().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.println("");
        if (posts.size() == 0) {
            System.out.println("this user haven't launched any post yet");
        } else {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if(o1.getLocalDateTime().isAfter(o2.getLocalDateTime())){
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i=num;i<num+5 && i<posts.size();i++){
                posts.get(i).addViewToTable(user, posts.get(i).getPostId(), LocalDateTime.now());
                posts.get(i).getViews().put(user1, LocalDateTime.now());
                boxInLeft("Post#"+(i+1)+" : ","|","_",80);
                showPost(posts.get(i));
            }
            closeDownLine("|","*",80);

        }
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inLeftOfLine("1-Select (ie: select post To like it, to leave down a comment,...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Next Page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-previous page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check follower users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("5-Check following users", edgeCharacter, lengthOfDisplay);
        if (!Follow) {
            inLeftOfLine("6-Follow this user", edgeCharacter, lengthOfDisplay);
        } else {
            inLeftOfLine("6-UnFollow this user", edgeCharacter, lengthOfDisplay);
        }
        inLeftOfLine("7-Send Message", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("8-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        if (showUserRecommendation){
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inMiddleOfLine("Recommended Users", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);

            if (recommendedUsers.size() == 0) {
                inMiddleOfLine("There is no another User", edgeCharacter, lengthOfDisplay);
            } else {
                for (int i = 0; i < recommendedUsers.size(); i++) {
                    inLeftOfLine("User#" + (i + 1) + " : "+ recommendedUsers.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
                }
                closeDownLine(edgeCharacter, "_", lengthOfDisplay);
            }
            inLeftOfLine("9-Select (ie: select a recommended User to visit this User's page)", edgeCharacter, lengthOfDisplay);
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }


        System.out.print("input: ");
    }
    public static void showFollowingsInShowFollowingPost(User user1, User user, List<User> keyList) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Following Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(user1.getUserName(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of followings : "+user1.getFollowings().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showFollowersInShowFollowingPost(User user1, User user, List<User> keyList) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Follower Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(user1.getUserName(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of followers : " + user1.getFollowers().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i = 0; i < keyList.size(); i++) {
            inLeftOfLine("User#" + (i + 1) + " : " + keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showPostInShowFollowingPost2(User user1, User user, Post post) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post"+" ( By "+ post.getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        ArrayList<Comment> comments = new ArrayList<>();
        comments = post.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }

        inLeftOfLine("Num of likes : " + post.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : " + post.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like post", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showCommentInShowFollowingPost2(User user1, User user, Comment comment) {



        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Comment", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine(comment.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        ArrayList<Comment> comments = new ArrayList<>();
        comments=comment.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("Num of likes : " + comment.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : " + comment.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like comment", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("6-Back", edgeCharacter, lengthOfDisplay);


        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showPostInMyHomePost(User user, User user1, Post post) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        ArrayList<Comment> comments = new ArrayList<>();
        comments=post.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }

        inLeftOfLine("Num of likes : "+post.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : "+post.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like post", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showCommentInMyHomePost(User user, Comment comment) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Comment", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine(comment.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        ArrayList<Comment> comments = new ArrayList<>();
        comments=comment.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }



        inLeftOfLine("Num of likes : " + comment.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : " + comment.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like comment", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("6-Visit this user page", edgeCharacter, lengthOfDisplay);
        if (!user.getFollowings().contains(comment.getSender())){
            inLeftOfLine("7-Follow this user", edgeCharacter, lengthOfDisplay);
        }else{
            inLeftOfLine("7-UnFollow this user", edgeCharacter, lengthOfDisplay);

        }
        inLeftOfLine("8-back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showCommentInMyHomePost2(User user, User user1, Comment comment) {


        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Comment", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine(comment.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        ArrayList<Comment> comments = new ArrayList<>();
        comments=comment.getComments();
        if (comments.size() == 0) {
            inMiddleOfLine("There is no comment", edgeCharacter, lengthOfDisplay);
        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                inLeftOfLine("Comment#" + (i + 1) + " : "+" ( By "+ comments.get(i).getSender().getUserName()+" )", edgeCharacter, lengthOfDisplay);
                inLeftOfLine(comments.get(i).getSender().getUserName()+" : " +comments.get(i).getContent(), edgeCharacter, lengthOfDisplay);
            }
            closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        }
        inLeftOfLine("Num of likes : " + comment.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of views : " + comment.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Add Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Like comment", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("3-Select Comment", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check Liked users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("5-Check viewed users", edgeCharacter, lengthOfDisplay);

        inLeftOfLine("6-Back", edgeCharacter, lengthOfDisplay);


        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showViewedUsersInMyHomePost2(User user, Post post, List<User> keyList) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of Views : "+post.getViews().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showOthersPageHelpInMyHomePost2(User user, User user1, boolean Follow, ArrayList<Post> posts) throws SQLException, ClassNotFoundException {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Another User Page", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("UserName : " + user1.getUserName() + "    @" + user1.getId(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Bio : " + user1.getBio(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Num of followers : " + user1.getFollowers().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        inLeftOfLine("Num of followinngs : " + user1.getFollowings().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.println("");
        if (posts.size() == 0) {
            System.out.println("this user haven't launched any post yet");
        } else {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if(o1.getLocalDateTime().isAfter(o2.getLocalDateTime())){
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i=num;i<num+5 && i<posts.size();i++){
                posts.get(i).addViewToTable(user, posts.get(i).getPostId(), LocalDateTime.now());
                posts.get(i).getViews().put(user, LocalDateTime.now());
                ManagerShow.boxInLeft("Post#"+(i+1)+" : ","|","_",80);
                ManagerShow.showPost(posts.get(i));
            }
            ManagerShow.closeDownLine("|","*",80);

        }
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inLeftOfLine("1-Select (ie: select post To like it, to leave down a comment,...)", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Next Page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("3-previous page", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("4-Check follower users", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("5-Check following users", edgeCharacter, lengthOfDisplay);
        if (!Follow) {
            inLeftOfLine("6-Follow this user", edgeCharacter, lengthOfDisplay);
        } else {
            inLeftOfLine("6-UnFollow this user", edgeCharacter, lengthOfDisplay);
        }
        inLeftOfLine("7-Send Message", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("8-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input: ");
    }
    public static void showLikedUsersInMyHomePost2(User user, List<User> keyList, Post post) {


        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Post", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(post.getContent(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of Likes : "+post.getLikes().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");

    }
    public static void showFollowingsInMyHomePost2(User user, List<User> keyList, User user1) {

        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Following Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(user1.getUserName(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of followings : "+user1.getFollowings().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");


    }
    public static void showFollowersInMyHomePost2(User user, List<User> keyList, User user1) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Follower Users", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine(user1.getUserName(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("Num of followers : "+user1.getFollowers().size(), edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);


        for (int i=0;i<keyList.size();i++) {
            inLeftOfLine("User#"+(i+1)+" : "+keyList.get(i).getUserName(), edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);

        inLeftOfLine("1-Select User", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input : ");
    }
    public static void showGroups(User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("*My Groups*", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("pls select your choice:", edgeCharacter, lengthOfDisplay);
        showEmpty(edgeCharacter, lengthOfDisplay);
        if (user.getGroups().size() == 0) {
            inMiddleOfLine("you have'nt Groups :(", edgeCharacter, lengthOfDisplay);
            showEmpty(edgeCharacter, lengthOfDisplay);
        } else {
            for (int i = 0; i < user.getGroups().size(); i++) {
                inLeftOfLine("" + (i + 1 ) + "-" + user.getGroups().get(i).getName() + " |" + (user.getGroups().get(i).getMessages().size() - user.getReadMessageGroup().get(i)) + "|", edgeCharacter, lengthOfDisplay);
            }
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        showGroupsChoicesIn(user);
    }
    public static void showGroupsForward(User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("*My Groups*", edgeCharacter, lengthOfDisplay);
        inLeftOfLine("pls select your choice:", edgeCharacter, lengthOfDisplay);
        showEmpty(edgeCharacter, lengthOfDisplay);
        if (user.getGroups().size() == 0) {
            inMiddleOfLine("you have'nt Groups :(", edgeCharacter, lengthOfDisplay);
            showEmpty(edgeCharacter, lengthOfDisplay);
        } else {
            for (int i = 0; i < user.getGroups().size(); i++) {
                inLeftOfLine("" + (i + 1 ) + "-" + user.getGroups().get(i).getName() + " |" + (user.getGroups().get(i).getMessages().size() - user.getReadMessageGroup().get(i)) + "|", edgeCharacter, lengthOfDisplay);
            }
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        showGroupsForwardChoicesIn(user);
    }
    private static void showGroupsChoicesIn(User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Select :", edgeCharacter, lengthOfDisplay);
        if (user.getGroups().size() == 0) {
            inLeftOfLine("1-New Group", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-Back",edgeCharacter,lengthOfDisplay);
        } else {
            inLeftOfLine("1-Select a Group", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-New Group", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Back",edgeCharacter,lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input :");
    }
    private static void showGroupsForwardChoicesIn(User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inMiddleOfLine("Select :", edgeCharacter, lengthOfDisplay);
        if (user.getGroups().size() == 0) {
            inLeftOfLine("1-Back",edgeCharacter,lengthOfDisplay);
        } else {
            inLeftOfLine("1-Select a Group", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-Back",edgeCharacter,lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input :");
    }

    public static void makeNewGroup() {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter,lengthOfDisplay);
        inMiddleOfLine("Make New Group",edgeCharacter,lengthOfDisplay);
        showEmpty(edgeCharacter,lengthOfDisplay);
        inLeftOfLine("GroupName:",edgeCharacter,lengthOfDisplay);
        inLeftOfLine("GroupId:",edgeCharacter,lengthOfDisplay);
        closeDownLine(edgeCharacter,"_",lengthOfDisplay);
    }
    public static void showGroup(Group group, User user, int nPage, int postInOnePage) {
            String edgeCharacter = "|";
            int lengthOfDisplay = 80;
            closeUpLine(edgeCharacter, lengthOfDisplay);
            inLeftOfLine(group.getName()+"("+group.getMembers().size()+")", edgeCharacter, lengthOfDisplay);
            ManagerGroup.showMessages(group, user, nPage, postInOnePage);
    }
    public static void showGroupChoicesIn(Group group, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        if (group.getLinkedMembers().get(user) ||(group.getBanGroup()&&(!group.getAdmins().contains(user)))) { //ban
            inMiddleOfLine("*** you banned from this group :( ***", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("1-Next Page", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-previous page", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Setting", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("4-Search", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Back", edgeCharacter, lengthOfDisplay);
        } else {
            inLeftOfLine("1-Send Message", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-Select (ie: select message To forward it, to reply a message,...)", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Next Page", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("4-previous page", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Setting", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("6-Search", edgeCharacter, lengthOfDisplay);
            if (group.getOwner()==user){
                if(group.getBanGroup()) {
                    inLeftOfLine("7-UnBan Group", edgeCharacter, lengthOfDisplay);
                }else {
                    inLeftOfLine("7-Ban Group", edgeCharacter, lengthOfDisplay);
                }
                inLeftOfLine("8-Back", edgeCharacter, lengthOfDisplay);
            }else {
                inLeftOfLine("7-Back", edgeCharacter, lengthOfDisplay);
            }
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input: ");
    }

    public static void showSettingGroup(Group group, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter,lengthOfDisplay);
        inMiddleOfLine("* Group Details *",edgeCharacter,lengthOfDisplay);
        boxInLeft("Group Name: "+group.getName(),edgeCharacter,"_",lengthOfDisplay);
        boxInLeft("Group ID: "+group.getGroupId(),edgeCharacter,"_",lengthOfDisplay);
        boxInLeft("Group Bio: "+group.getBio(),edgeCharacter,"_",lengthOfDisplay);
        showSettingGroupChoiceIn(group,user);
    }

    private static void showSettingGroupChoiceIn(Group group, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter,lengthOfDisplay);
        inMiddleOfLine("Select your Choice :",edgeCharacter,lengthOfDisplay);
        inLeftOfLine("1-Show Members",edgeCharacter,lengthOfDisplay);
        inLeftOfLine("2-Leave Group",edgeCharacter,lengthOfDisplay);
        if (group.getAdmins().contains(user)) {
            inLeftOfLine("3-Change Group Name", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("4-Change Group ID", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Change Group Bio", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("6-Add New Member", edgeCharacter, lengthOfDisplay);
        }
        if (group.getAdmins().contains(user) && group.getOwner()!=user) {
            inLeftOfLine("7-Back",edgeCharacter,lengthOfDisplay);
        }else if (group.getOwner()==user) {
//            inLeftOfLine("7-Add NEW Admin", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("7-Back",edgeCharacter,lengthOfDisplay);
        }else {
            inLeftOfLine("3-Back",edgeCharacter,lengthOfDisplay);
        }
        closeDownLine(edgeCharacter,"_",lengthOfDisplay);
        System.out.print("input: ");
    }
    public static void showMembers(Group group, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter,lengthOfDisplay);
        inMiddleOfLine("{ Group Members }",edgeCharacter,lengthOfDisplay);
        showEmpty(edgeCharacter,lengthOfDisplay);
        for (int i=0;i<group.getMembers().size();i++){
            String name=(i+1)+"- ";
            User useri=group.getMembers().get(i);
            if (user==useri){
                name+="["+user.getUserName()+"]";
            }else {
                name+=useri.getUserName();
            }
            if (group.getOwner()==useri){
                name+=" (Owner)";
            }else if (group.getAdmins().contains(useri)){
                name+=" (Admin)";
            }
            if (group.getLinkedMembers().get(useri)){
                name+=" {Banned}";
            }
            inLeftOfLine(name,edgeCharacter,lengthOfDisplay);
        }
        closeDownLine(edgeCharacter,"_",lengthOfDisplay);
        showMembersChoice(group,user);
    }
    private static void showMembersChoice(Group group, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter,lengthOfDisplay);
        inMiddleOfLine("Select your Choice:",edgeCharacter,lengthOfDisplay);
        inLeftOfLine("1-Select a Member",edgeCharacter,lengthOfDisplay);
        inLeftOfLine("2-Back",edgeCharacter,lengthOfDisplay);
        closeDownLine(edgeCharacter,"_",lengthOfDisplay);
        System.out.print("input:");
    }
    public static void selectMember(Group group, User user, User select) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter,lengthOfDisplay);
        inLeftOfLine("Members Name: "+select.getUserName(),edgeCharacter,lengthOfDisplay);
        inLeftOfLine("Members Id: "+select.getId(),edgeCharacter,lengthOfDisplay);
        inLeftOfLine("Members Bio: "+select.getBio(),edgeCharacter,lengthOfDisplay);
        closeDownLine(edgeCharacter,"_",lengthOfDisplay);
        inMiddleOfLine("Select your Choice:",edgeCharacter,lengthOfDisplay);
        inLeftOfLine("1-send Message",edgeCharacter,lengthOfDisplay);
        inLeftOfLine("2-visit Page",edgeCharacter,lengthOfDisplay);
        if (!group.getAdmins().contains(user) || user==select){
            inLeftOfLine("3-Back",edgeCharacter,lengthOfDisplay);
        }else if (group.getAdmins().contains(user) && group.getOwner()!=user){
            if (group.getAdmins().contains(select)){
                inLeftOfLine("3-Back",edgeCharacter,lengthOfDisplay);
            }else {
                if (group.getLinkedMembers().get(select)){
                    inLeftOfLine("3-UnBan Member From Group",edgeCharacter,lengthOfDisplay);
                }else {
                    inLeftOfLine("3-Ban Member From Group",edgeCharacter,lengthOfDisplay);
                }
                inLeftOfLine("4-remove From Group",edgeCharacter,lengthOfDisplay);
                inLeftOfLine("5-Back",edgeCharacter,lengthOfDisplay);
            }
        }else if (group.getOwner()==user){
            if (group.getLinkedMembers().get(select)){
                inLeftOfLine("3-UnBan Member From Group",edgeCharacter,lengthOfDisplay);
            }else {
                inLeftOfLine("3-Ban Member From Group",edgeCharacter,lengthOfDisplay);
            }
            inLeftOfLine("4-remove From Group",edgeCharacter,lengthOfDisplay);
            if (group.getAdmins().contains(select)){
                inLeftOfLine("5-set Normal Member",edgeCharacter,lengthOfDisplay);
            }else {
                inLeftOfLine("5-Set Admin Of Group",edgeCharacter,lengthOfDisplay);
            }
            inLeftOfLine("6-Back",edgeCharacter,lengthOfDisplay);
        }
        closeDownLine(edgeCharacter,"_",lengthOfDisplay);
        System.out.print("input: ");

    }

    public static void showPvInSearchMode(Pv pv, User user, int selectIndex, int postInOnePage,ArrayList<Integer> indexFindMessages,int nPage,String key) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        inLeftOfLine("Search : "+key, edgeCharacter, lengthOfDisplay);
        inRightOfLine("("+selectIndex+")"+" From ("+indexFindMessages.size()+")",edgeCharacter,lengthOfDisplay);
        ManagerPv.showMessagesInSearchMode(pv, user, nPage, postInOnePage,selectIndex,indexFindMessages);
        showPvChoicesInSearchMode(pv,user);
    }
    public static void showPvChoicesInSearchMode(Pv pv, User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 80;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        if (pv.getBlock()) {
            String Block = new String();
            if (pv.getBlocker() == user) {
                Block = "* you Blocked This User :(";
            } else {
                Block = "* this user Blocked you :((";
            }
            inMiddleOfLine(Block, edgeCharacter, lengthOfDisplay);
            inLeftOfLine("1-Next", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
            if (pv.getBlocker() == user) {
                inLeftOfLine("3-UnBlock", edgeCharacter, lengthOfDisplay);
            } else {
                inLeftOfLine("3-UnBlock(Disable)", edgeCharacter, lengthOfDisplay);
            }
            inLeftOfLine("4-Change Search Key", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Back", edgeCharacter, lengthOfDisplay);
        } else {
            inLeftOfLine("1-Send Message", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-Select (ie: select message To forward it, to reply a message,...)", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Next", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("4-Back", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("5-Block", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("6-Change Search Key", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("7-Back", edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter, "_", lengthOfDisplay);
        System.out.print("input: ");
    }

    public static void showNewPv(User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter, lengthOfDisplay);
        showEmpty(edgeCharacter,lengthOfDisplay);
        inMiddleOfLine("Followers :",edgeCharacter,lengthOfDisplay);
        if (user.getFollowers().size()>0){
            for (int i=0;i<user.getFollowers().size();i++){
                inLeftOfLine((i+1)+"- "+user.getFollowers().get(i).getUserName(),edgeCharacter,lengthOfDisplay);
            }
        }else {
            inMiddleOfLine("you have'nt any followwers :(",edgeCharacter,lengthOfDisplay);
        }
        showEmpty(edgeCharacter,lengthOfDisplay);
        closeDownLine(edgeCharacter,"_",lengthOfDisplay);
        showNewPvChoicesIn(user);
    }

    private static void showNewPvChoicesIn(User user) {
        String edgeCharacter = "|";
        int lengthOfDisplay = 40;
        closeUpLine(edgeCharacter,lengthOfDisplay);
        inMiddleOfLine("Select your Choice:",edgeCharacter,lengthOfDisplay);
        if (user.getFollowers().size()>0) {
            inLeftOfLine("1-select a User From Followers", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-select a User From DataBase With UserId", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("3-Back", edgeCharacter, lengthOfDisplay);
        }else {
            inLeftOfLine("1-select a User From DataBase With UserId", edgeCharacter, lengthOfDisplay);
            inLeftOfLine("2-Back", edgeCharacter, lengthOfDisplay);
        }
        closeDownLine(edgeCharacter,"_",lengthOfDisplay);
        System.out.print("input: ");
    }

    public static void showGroupInSearchMode(Group group, User user, int nPage, int postInOnePage) {
    }
}

