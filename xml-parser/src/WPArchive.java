import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

/*
 * Created by kari.thrastarson on 22-09-2016.
 * Copyleft
 */

public class WPArchive {

    //Full path of XML export file from WordPress
    private String xmlFile;
    private ArrayList<Post> archive;

    public  WPArchive(String path) {
        xmlFile = path;
        init();
    }

    public void init() {

        try {
            File file = new File(xmlFile);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            archive = new ArrayList<Post>();

            if (doc.hasChildNodes()) {
                NodeList nodeList = doc.getChildNodes();
                //Let's find all nodes that are the actual blogs:
                NodeList blogList = doc.getElementsByTagName("item");
                extractInfo(blogList);
              }
            }
         catch (Exception e) {
            System.out.println("Cause: " + e.getCause() + "\nMessage: " + e.getMessage());
        }
    }


    private void extractInfo(NodeList nodeList) throws ParserConfigurationException {


        boolean isPost = false;
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node tempNode = nodeList.item(i);

            //Is this node a post?
            Element postType = (Element) tempNode;
            NodeList pt = postType.getElementsByTagName("wp:post_type");

            isPost = (pt.item(0).getTextContent().equals("post"));

            if (isPost) {

                Post currentPost = new Post();

                Element titleElement = (Element) tempNode;
                NodeList te = titleElement.getElementsByTagName("title");

                Element authorElement = (Element) tempNode;
                NodeList ae = authorElement.getElementsByTagName("dc:creator");

                Element dateElement = (Element) tempNode;
                NodeList de = dateElement.getElementsByTagName("pubDate");

                Element contentElement = (Element) tempNode;
                NodeList ce = contentElement.getElementsByTagName("content:encoded");

                Element comments = (Element) tempNode;
                NodeList commentList = contentElement.getElementsByTagName("wp:comment");
                //Process Comments
                currentPost = processComments(currentPost, commentList);

                currentPost.setAuthor(ae.item(0).getTextContent());
                currentPost.setTitle(te.item(0).getTextContent());
                currentPost.setContent(ce.item(0).getTextContent());

                if (!(currentPost.getAuthor().isEmpty() && currentPost.getContent().isEmpty() && currentPost.getTitle().isEmpty()))
                    archive.add(currentPost);
            }
        }
    }

    private Post processComments(Post currentPost, NodeList comments) {

        for (int i = 0; i < comments.getLength(); i++) {
            Node currentComment = comments.item(i);

            Element author = (Element) currentComment;
            NodeList ae = author.getElementsByTagName("wp:comment_author");

            Element date = (Element) currentComment;
            NodeList de = author.getElementsByTagName("wp:comment_date");

            Element content = (Element) currentComment;
            NodeList ce = content.getElementsByTagName("wp:comment_content");


            currentPost.addComment(ae.item(0).getTextContent(), ce.item(0).getTextContent(), de.item(0).getTextContent());
        }

        return currentPost;
    }
    private boolean isValidBlog(NodeList nodeList) {

        boolean returnValue = true;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tempNode = nodeList.item(i);

            if (tempNode.getNodeName() == "content:encoded") {
                if (tempNode.getTextContent().isEmpty() ) {
                    returnValue = false;
                }
            }

            if (tempNode.getNodeName() == "title") {
                if (tempNode.getTextContent().isEmpty()) {
                    returnValue = false;
                }
            }

            if(nodeList.item(i).hasChildNodes()) {
                return (isValidBlog(nodeList.item(i).getChildNodes()) && returnValue);
            }
            else {
                return returnValue;
            }
        }
        return false;
    }


    private void printArchive() {
        System.out.println(archive.size());
        for (Post p : archive) {
            System.out.println("Title " + p.getTitle());
            System.out.println("Author: " + p.getAuthor());
            System.out.println("Content: " + p.getContent());
            p.printComments();
        }
    }

    public static void main(String args[]){
        WPArchive wpTest = new WPArchive("novoblog.xml");
        wpTest.printArchive();
    }


}
