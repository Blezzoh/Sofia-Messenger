package messenger_interface;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by daniel on 6/8/16.
 *
 * @author Daniel Evans
 */
public class Composer extends BorderPane {

    private TextArea bodyText;
    /*
    composerId is set by the manager
     */
    private int composerId;
    private TextField emailAddress;
    private TextField Cc;
    private HBox sendAndExtrasContainer;
    private Button send;
    private Button attachments;
    private TextField subject;
    private int id;
    private VBox vbox;

    public Composer(Emailer emailer) {

        initFields();

        attachments.setOnMouseClicked(e ->
        {
            // open file manager, load attachment into message
        });

        send.setOnMouseClicked(e ->
        {
            // TODO: display a message to user as well as return so that user knows what
            // she needs to do to send the message
            if (bodyText.getText().equals(""))
                return;
            else if (emailAddress.getText().equals(""))
                return;
                // else if check if it is a valid email address
            else {
                // upload attachment if any
                // ...
                // make message and send
                MimeMessage mimeMessage = null;
                try {

                    Properties props = System.getProperties();
                    props.setProperty("mail.smtp.host", "localhost");
                    Session session = Session.getDefaultInstance(props);
                    mimeMessage = emailer
                            .composeMessage(session, emailAddress.getText()
                                    , subject.getText(), bodyText.getText());
                    emailer.sendMessage(mimeMessage);
                } catch (MessagingException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        sendAndExtrasContainer = new HBox(8, send, attachments);
        vbox = new VBox(8);
        vbox.setPadding(new Insets(4));
        vbox.getChildren().addAll(emailAddress, Cc, subject, bodyText, sendAndExtrasContainer);
        this.setCenter(vbox);
        this.setVisible(false);
    }

    public void initFields() {
        subject = new TextField();
        subject.setPromptText("Subject");
        bodyText = new TextArea();
        bodyText.setPromptText("Body Text");
        bodyText.setWrapText(true);
        bodyText.setMaxWidth(200);
        emailAddress = new TextField();
        emailAddress.setPromptText("To: ");
        Cc = new TextField();
        Cc.setPromptText("Cc: ");
        send = new Button("Send");
        attachments = new Button("Attachment");
    }

    public TextField getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(TextField emailAddress) {
        this.emailAddress = emailAddress;
    }

    public TextArea getBodyText() {
        return bodyText;
    }

    public void setBodyText(TextArea bodyText) {
        this.bodyText = bodyText;
    }

    public TextField getCc() {
        return Cc;
    }

    public void setCc(TextField cc) {
        Cc = cc;
    }

    public TextField getSubject() {
        return subject;
    }

    public void setSubject(TextField subject) {
        this.subject = subject;
    }

    public void setComposerId(int composerId) {
        this.composerId = composerId;
    }

    public int getComposerId() {
        return composerId;
    }
}